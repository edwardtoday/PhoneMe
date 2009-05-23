package org.kde9.view.component.cube;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.kde9.control.Kernel;
import org.kde9.model.card.ConstCard;
import org.kde9.view.ComponentPool;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.GroupAction;
import prefuse.action.ItemAction;
import prefuse.action.RepaintAction;
import prefuse.action.animate.ColorAnimator;
import prefuse.action.animate.PolarLocationAnimator;
import prefuse.action.animate.QualityControlAnimator;
import prefuse.action.animate.VisibilityAnimator;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.FontAction;
import prefuse.action.layout.CollapsedSubtreeLayout;
import prefuse.action.layout.graph.RadialTreeLayout;
import prefuse.activity.SlowInSlowOutPacer;
import prefuse.controls.ControlAdapter;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.HoverActionControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Schema;
import prefuse.data.Table;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.query.SearchQueryBinding;
import prefuse.data.search.PrefixSearchTupleSet;
import prefuse.data.search.SearchTupleSet;
import prefuse.data.tuple.DefaultTupleSet;
import prefuse.data.tuple.TupleSet;
import prefuse.render.AbstractShapeRenderer;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.ui.JFastLabel;
import prefuse.util.ui.JSearchPanel;
import prefuse.util.ui.UILib;
import prefuse.visual.VisualItem;
import prefuse.visual.expression.InGroupPredicate;
import prefuse.visual.sort.TreeDepthItemSorter;


/**
 * Demonstration of a node-link tree viewer
 *
 * @version 1.0
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class Cube extends Display {

    public static final String DATA_FILE = "/socialnet.xml";
    
    private static final String tree = "tree";
    private static final String treeNodes = "tree.nodes";
    private static final String treeEdges = "tree.edges";
    private static final String linear = "linear";
    
    private static final int a = 200, b = 200;
    private static Kernel kernel = ComponentPool.getComponent().getKernel();
    private static int id;
    
    private LabelRenderer m_nodeRenderer;
    private EdgeRenderer m_edgeRenderer;
    
    private String m_label = "label";
    
    public Cube(Graph g, String label) {
        super(new Visualization());
        m_label = label;

        // -- set up visualization --
        m_vis.add(tree, g);
        m_vis.setInteractive(treeEdges, null, false);
        
        // -- set up renderers --
        m_nodeRenderer = new LabelRenderer(m_label);
        m_nodeRenderer.setRenderType(AbstractShapeRenderer.RENDER_TYPE_FILL);
        m_nodeRenderer.setHorizontalAlignment(Constants.CENTER);
        m_nodeRenderer.setRoundedCorner(8,8);
        m_edgeRenderer = new EdgeRenderer();
        
        DefaultRendererFactory rf = new DefaultRendererFactory(m_nodeRenderer);
        rf.add(new InGroupPredicate(treeEdges), m_edgeRenderer);
        m_vis.setRendererFactory(rf);
               
        // -- set up processing actions --
        
        // colors
        ItemAction nodeColor = new NodeColorAction(treeNodes);
        ItemAction textColor = new TextColorAction(treeNodes);
        m_vis.putAction("textColor", textColor);
        
        ItemAction edgeColor = new ColorAction(treeEdges,
                VisualItem.STROKECOLOR, ColorLib.rgb(90,200,200));
        
        FontAction fonts = new FontAction(treeNodes, 
                FontLib.getFont("", 12));
        fonts.add("ingroup('_focus_')", FontLib.getFont("", 13));
        
        // recolor
        ActionList recolor = new ActionList();
        recolor.add(nodeColor);
        recolor.add(textColor);
        m_vis.putAction("recolor", recolor);
        
        // repaint
        ActionList repaint = new ActionList();
        repaint.add(recolor);
        repaint.add(new RepaintAction());
        m_vis.putAction("repaint", repaint);
        
        // animate paint change
        ActionList animatePaint = new ActionList(400);
        animatePaint.add(new ColorAnimator(treeNodes));
        animatePaint.add(new RepaintAction());
        m_vis.putAction("animatePaint", animatePaint);
        
        // create the tree layout action
        RadialTreeLayout treeLayout = new RadialTreeLayout(tree);
        //treeLayout.setAngularBounds(-Math.PI/2, Math.PI);
        m_vis.putAction("treeLayout", treeLayout);
        
        CollapsedSubtreeLayout subLayout = new CollapsedSubtreeLayout(tree);
        m_vis.putAction("subLayout", subLayout);
        
        // create the filtering and layout
        ActionList filter = new ActionList();
        filter.add(new TreeRootAction(tree));
        filter.add(fonts);
        filter.add(treeLayout);
        filter.add(subLayout);
        filter.add(textColor);
        filter.add(nodeColor);
        filter.add(edgeColor);
        m_vis.putAction("filter", filter);
        
        // animated transition
        ActionList animate = new ActionList(1250);
        animate.setPacingFunction(new SlowInSlowOutPacer());
        animate.add(new QualityControlAnimator());
        animate.add(new VisibilityAnimator(tree));
        animate.add(new PolarLocationAnimator(treeNodes, linear));
        animate.add(new ColorAnimator(treeNodes));
        animate.add(new RepaintAction());
        m_vis.putAction("animate", animate);
        m_vis.alwaysRunAfter("filter", "animate");
        
        // ------------------------------------------------
        
        // initialize the display
        setSize(600,600);
        setItemSorter(new TreeDepthItemSorter());
        addControlListener(new DragControl());
        addControlListener(new ZoomToFitControl());
        addControlListener(new ZoomControl());
        addControlListener(new PanControl());
        addControlListener(new FocusControl(1, "filter"));
        addControlListener(new HoverActionControl("repaint"));
        
        // ------------------------------------------------
        
        // filter graph and perform layout
        m_vis.run("filter");
        
        // maintain a set of items that should be interpolated linearly
        // this isn't absolutely necessary, but makes the animations nicer
        // the PolarLocationAnimator should read this set and act accordingly
        m_vis.addFocusGroup(linear, new DefaultTupleSet());
        m_vis.getGroup(Visualization.FOCUS_ITEMS).addTupleSetListener(
            new TupleSetListener() {
                public void tupleSetChanged(TupleSet t, Tuple[] add, Tuple[] rem) {
                    TupleSet linearInterp = m_vis.getGroup(linear);
                    if ( add.length < 1 ) return; linearInterp.clear();
                    for ( Node n = (Node)add[0]; n!=null; n=n.getParent() )
                        linearInterp.addTuple(n);
                    if(add[0] != null) {
                    	System.out.println(((Node)add[0]).get("node"));
                        ComponentPool.getGroupComponent().setSelected(0, 0);
                        ComponentPool.getNameComponent().setSelectedById(
                        		Integer.valueOf(((Node)add[0]).getString("realid")));
                    }
                }
            }
        );
        
        SearchTupleSet search = new PrefixSearchTupleSet();
        m_vis.addFocusGroup(Visualization.SEARCH_ITEMS, search);
        search.addTupleSetListener(new TupleSetListener() {
            public void tupleSetChanged(TupleSet t, Tuple[] add, Tuple[] rem) {
                m_vis.cancel("animatePaint");
                m_vis.run("recolor");
                m_vis.run("animatePaint");
            }
        });
    }
    
    // ------------------------------------------------------------------------
    
//    public static void main(String argv[]) {
//        String infile = DATA_FILE;
//        String label = "name";
//        
//        if ( argv.length > 1 ) {
//            infile = argv[0];
//            label = argv[1];
//        }
//        
//        UILib.setPlatformLookAndFeel();
//        
//        JFrame frame = new JFrame("p r e f u s e  |  r a d i a l g r a p h v i e w");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setContentPane(demo(infile, label));
//        frame.pack();
//        frame.setVisible(true);
//    }
    
    public static JPanel getCube(int a, int b) {
        return demo(DATA_FILE, "name");
    }
    
    public static JPanel demo(String datafile, final String label) {
        Graph g = null;
        try {
        	Schema card = new Schema();
        	card.addColumn("node", int.class);
        	card.addColumn("name", String.class);
        	card.addColumn("realid", int.class);
        	Table node = card.instantiate();
        	Schema relation = new Schema();
        	relation.addColumn("source", int.class);
        	relation.addColumn("target", int.class);
        	Table edge = relation.instantiate();
        	initTable(node, edge);
        	
            g = new Graph(node, edge, false);
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(1);
        }
        return demo(g, label);
    }
    
    private static void initTable(Table node, Table edge) {
    	int realid = ComponentPool.getNameComponent().getSelectedMemberId();
    	id = 0;
    	System.err.println("+++++++++++" + id + "+++++++++++");
    	HashMap<Integer, Integer> ids = new HashMap<Integer, Integer>();
    	addTableRow(node, edge, realid, ids);
    	System.err.println(ids);
    }
    
    private static void addTableRow(Table node, Table edge, int realid,
    		HashMap<Integer, Integer> ids) {
    	ConstCard card = kernel.getCard(realid);
    	System.out.println("-----------"+card.getAllShowRelationship());
    	if(card != null) {
    		if(ids.containsKey(realid))
    			return;
    		int row = node.addRow();
    		node.set(row, "node", id);
    		node.set(row, "name", kernel.getName(realid, false));
    		node.set(row, "realid", realid);
    		ids.put(realid, id);
    		int temp = id;
    		id++;
    		if(card.getAllShowRelationship().size() == 0)
    			return;
    		for(int rid : card.getAllShowRelationship().keySet()) {
    			//if(!ids.contains(rid)) {
    				row = edge.addRow();
    				edge.set(row, "source", temp);
    				if(ids.get(rid) != null)
    					edge.set(row, "target", ids.get(rid));
    				else
    					edge.set(row, "target", id);
    				System.err.println(temp + "----" + id);
    				addTableRow(node, edge, rid, ids);
    			//}
    		}
    	}
    }
    
    public static JPanel demo(Graph g, final String label) {        
        // create a new radial tree view
        final Cube gview = new Cube(g, label);
        gview.setBounds(0, 0, a, b);
        Visualization vis = gview.getVisualization();
        
        // create a search panel for the tree map
        SearchQueryBinding sq = new SearchQueryBinding(
             (Table)vis.getGroup(treeNodes), label,
             (SearchTupleSet)vis.getGroup(Visualization.SEARCH_ITEMS));
        JSearchPanel search = sq.createSearchPanel();
        search.setShowResultCount(true);
        search.setBorder(BorderFactory.createEmptyBorder(5,5,4,0));
        search.setFont(FontLib.getFont("", Font.PLAIN, 12));
        
        gview.addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {
				// TODO Auto-generated method stub
				Point p = e.getPoint();
				if(e.getWheelRotation() > 0)
					gview.zoom(p, 0.9);
				else
					gview.zoom(p, 1.1);
				gview.repaint();
			}
        	
        });
        
        final JLabel title = new JLabel();
        title.setPreferredSize(new Dimension(60, 20));
        title.setVerticalAlignment(SwingConstants.BOTTOM);
        title.setBorder(BorderFactory.createEmptyBorder(3,0,5,0));
        title.setFont(FontLib.getFont("", Font.PLAIN, 16));
        
        gview.addControlListener(new ControlAdapter() {
			public void itemEntered(VisualItem item, MouseEvent e) {
                //this.e = e;
				if ( item.canGetString(label) ) {
                    title.setText(item.getString(label));
                    System.out.println(":::" + item.getString("node"));
                }
            }
            public void itemExited(VisualItem item, MouseEvent e) {
                title.setText(null);
            }
        });
        
        Box box = new Box(BoxLayout.X_AXIS);
        box.add(Box.createHorizontalStrut(10));
        box.add(title);
        box.add(Box.createHorizontalGlue());
        box.add(search);
        box.add(Box.createHorizontalStrut(3));
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gview, BorderLayout.CENTER);
        panel.add(box, BorderLayout.SOUTH);
        
        Color BACKGROUND = Color.WHITE;
        Color FOREGROUND = Color.DARK_GRAY;
        UILib.setColor(panel, BACKGROUND, FOREGROUND);
        
        TitledBorder border = new TitledBorder("cube");
		border.setTitleJustification(TitledBorder.CENTER);
		panel.setPreferredSize(new Dimension(a, b));
		panel.setBorder(border);
        
        return panel;
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * Switch the root of the tree by requesting a new spanning tree
     * at the desired root
     */
    public static class TreeRootAction extends GroupAction {
        public TreeRootAction(String graphGroup) {
            super(graphGroup);
        }
        public void run(double frac) {
            TupleSet focus = m_vis.getGroup(Visualization.FOCUS_ITEMS);
            if ( focus==null || focus.getTupleCount() == 0 ) return;
            
            Graph g = (Graph)m_vis.getGroup(m_group);
            Node f = null;
            Iterator tuples = focus.tuples();
            while (tuples.hasNext() && !g.containsTuple(f=(Node)tuples.next()))
            {
                f = null;
            }
            if ( f == null ) return;
            g.getSpanningTree(f);
        }
    }
    
    /**
     * Set node fill colors
     */
    public static class NodeColorAction extends ColorAction {
        public NodeColorAction(String group) {
            super(group, VisualItem.FILLCOLOR, ColorLib.rgba(255,255,255,0));
            add("_hover", ColorLib.gray(220,230));
            add("ingroup('_search_')", ColorLib.rgb(255,190,190));
            add("ingroup('_focus_')", ColorLib.rgb(198,229,229));
        }
                
    } // end of inner class NodeColorAction
    
    /**
     * Set node text colors
     */
    public static class TextColorAction extends ColorAction {
        public TextColorAction(String group) {
            super(group, VisualItem.TEXTCOLOR, ColorLib.gray(0));
            add("_hover", ColorLib.rgb(255,0,0));
        }
    } // end of inner class TextColorAction
    
    
} // end of class RadialGraphView
