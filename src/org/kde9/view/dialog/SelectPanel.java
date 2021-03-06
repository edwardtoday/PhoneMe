package org.kde9.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.kde9.control.Kernel;
import org.kde9.model.card.ConstCard;
import org.kde9.model.group.ConstGroup;
import org.kde9.util.ConfigFactory;
import org.kde9.util.Configuration;
import org.kde9.util.Constants;
import org.kde9.view.ComponentPool;
import org.kde9.view.component.brower.BrowerComponent;
import org.kde9.view.component.brower.GroupComponent;

import ch.randelshofer.quaqua.JSheet;

import com.sun.java.swing.plaf.windows.resources.windows;
import com.sun.jna.examples.WindowUtils;

public class SelectPanel 
implements KeyListener, ListSelectionListener,ActionListener, 
		Constants {
	private static JDialog frame = 
		new JDialog(ComponentPool.getComponent(), true);
	private Container father;
	private JSheet sheet;
	private JPanel container;
	private Color color;
	private Kernel kernel;
	private SimpleGroupC group;
	private SimpleNameC name;
	private SimpleViewerC viewer;
	private JSplitPane pane;
	private JTextField search;
	private JButton confirm;
	private JButton cancel;
	
	private int flag = 0;
	
	private String text = "";
	
	private searchThread thread = new searchThread();
	
	private Configuration config = ConfigFactory.creatConfig();
	
	private int w;
	private int h;
	private boolean closing = false;
	private int index;
	private Vector<Integer> ids;
	

	public SelectPanel(Container father,Color color, int w, int h,
			int index, Vector<Integer> ids) {
		this.father = father;
		this.color = color;
		this.w = w;
		this.h = h;
		this.kernel = ComponentPool.getComponent().getKernel();
		this.index = index;
		this.ids = ids;
		thread.start();
		launch();
	}

	private void launch() {
		createUI();
		launchUI();
	}

	private void launchUI() {
		sheet.setUndecorated(true);
		sheet.setVisible(true);
		sheet.getRootPane().putClientProperty("Quaqua.RootPane.isVertical", Boolean.TRUE);
	}

	private void createUI() {
		sheet = new JSheet(frame);
		sheet.setSize(w, h);
		sheet.setLayout(new BorderLayout());
		
		group = new SimpleGroupC();
		LinkedHashMap<Integer, String> map = kernel.getAllGroups();
		group.setGroups(map);
		group.setSelected(0, 0);
		
		name = new SimpleNameC();
		
		viewer = new SimpleViewerC();
		
		showGroupMembers();
		
		pane = new JSplitPane();
		pane.setLeftComponent(group);
		pane.setRightComponent(name);
		pane.setDividerLocation(180);
		pane.setDividerSize(2);

		search = new JTextField();
		
		TitledBorder border = new TitledBorder("");
		border.setTitleJustification(TitledBorder.CENTER);
		
		container = new JPanel(new BorderLayout());
		container.add("Center", pane);
		container.add("North", search);
		container.add("South", viewer);
		container.setBorder(border);
		sheet.add("Center", container);

		confirm = new JButton("[    OK   ]");
		cancel = new JButton("[Cancel]");
		confirm.addActionListener(this);
		cancel.addActionListener(this);
		confirm.putClientProperty("Quaqua.Button.style", "toolBarRollover");
		cancel.putClientProperty("Quaqua.Button.style", "toolBarRollover");
		JPanel button = new JPanel();
		button.add(confirm);
		button.add(cancel);
		sheet.add("South", button);
		
		container.setOpaque(true);
		//container.setBackground(color);
		
		search.addKeyListener(this);
		group.getTable().addKeyListener(this);
		group.getTable().getSelectionModel().addListSelectionListener(this);
		name.getTable().addKeyListener(this);
		name.getTable().getSelectionModel().addListSelectionListener(this);

		RoundRectangle2D.Float mask = new RoundRectangle2D.Float(1, 1, 
				sheet.getWidth()-2, sheet.getHeight()-2, 20, 20);
		WindowUtils.setWindowMask(sheet, mask);
		if(father != null)
			centerWindow(father, sheet);
		else
			centerWindow(sheet, sheet);
		sheet.setBackground(Color.WHITE);
		WindowUtils.setWindowAlpha(sheet, 1);
		changeAlphaDown(800, 0.8f, ComponentPool.getComponent(), false);
	}

	public void showGroupMembers() {
		int id = group.getSelectedGroupId();
		LinkedHashMap<Integer, String> members = 
			new LinkedHashMap<Integer, String>();
		for(int current : kernel.getGroup(id).getGroupMembers()) {
			String name;
			if((Integer)config.getConfig(NAME_FOMAT, CONFIGINT) == 0)
				name = 
					kernel.getFirstName(current) + ' ' + kernel.getLastName(current);
			else
				name = 
					kernel.getLastName(current) + ' ' + kernel.getFirstName(current);
			members.put(current, name);
		}
		name.setMembers(members);
		name.setSelected(0, 0);
		showCard();
	}
	
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource().equals(group.getSelectionModel())) {
			if (group.getSelected() != -1) {
				showGroupMembers();
				name.setSelected(0, 0);
				showCard();
			}
		} else if (e.getSource().equals(name.getSelectionModel())) {
			if (name.getSelected() != -1) {
				showCard();
			}
		}
	}
	
	public void showCard() {
		ConstCard card = kernel.getCard(name.getSelectedMemberId());
		if(card != null) {
			viewer.setCard(card);
			viewer.setName(kernel.getName(card.getId()));
			viewer.setImage(card);
			viewer.setItem(card.getAllItems());
		}
	}
	
	private void changeAlphaUp(final int a, final float s, final Window window) {
		Thread thread = new Thread() {
			public void run() {
				for (float i = s; i < 1; i += 0.02) {
					try {
						sleep((long) (a/((1-s)*50)));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					WindowUtils.setWindowAlpha(window, i);
				}
				WindowUtils.setWindowAlpha(window, 1);
			}
		};
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}
	
	private void changeAlphaDown(final int a, final float s, final Window window,
			final boolean close) {
		Thread thread = new Thread() {
			public void run() {
				if(close)
					SelectPanel.this.closing = true;
				for (float i = 1; i > s; i -= 0.02) {
					try {
						sleep((long) (a/((1-s)*50)));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(!SelectPanel.this.closing)
						WindowUtils.setWindowAlpha(window, i);
				}
				if(close) {
					window.dispose();
//					ComponentPool.getComponent().setAlwaysOnTop(true);
//					ComponentPool.getComponent().setAlwaysOnTop(false);
				}
			}
		};
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}
	
	private static void centerWindow(Container window, Container sheet) {
		int xx = (int) window.getLocationOnScreen().getX();
		int yy = (int) window.getLocationOnScreen().getY();
		int w = window.getWidth();
		int h = window.getHeight();
		int x = xx + w/2;
		int y = yy + (h-sheet.getHeight())/2;
		frame.setLocation(x, y);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			changeAlphaUp(300, 0.8f, ComponentPool.getComponent());
			changeAlphaDown(300, 0, sheet, true);
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (group.getTable().hasFocus()) {
				name.getTable().requestFocusInWindow();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (name.getTable().hasFocus()) {
				group.getTable().requestFocusInWindow();
			}
		} else if (e.getSource() != search &&
				e.getKeyCode() == KeyEvent.VK_ENTER) {
			setIdAndClose();
		}
	}

	public void setIdAndClose() {
		group.getTable().setEnabled(false);
		name.getTable().setEnabled(false);
		if(!ids.contains(name.getSelectedMemberId())) {
			ids.set(index-1, name.getSelectedMemberId());
			changeAlphaUp(300, 0.8f, ComponentPool.getComponent());
			changeAlphaDown(300, 0, sheet, true);
	//		ComponentPool.getViewerComponent().startEditModel();
			ComponentPool.getViewerComponent().getRelationTable().editCellAt(index, 6);
	//		ComponentPool.getViewerComponent().get.setEditingColumn(6);
		} else {
			new CoolInfoBox(sheet, "\n 这个联系人已经在关系中存在了哦！", Color.YELLOW, 250, 100);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == search) {
			ComponentPool.getViewerComponent().setHighlight(true);
			System.out.println(search.getText().length() + " search content");
			// TODO Auto-generated method stub
			flag = (flag + 1)%100;
			final int current = flag;
			if(e.getKeyCode() != KeyEvent.VK_ENTER && text.equals(search.getText()))
				return;
			if(search.getText().length() == 0) {
				ComponentPool.getGroupComponent().setSelected(0, 0);
				text = search.getText();
				return;
			}
			else {
				if ((search.getText().charAt(search.getText().length() - 1) == ' ' &&
						e.getKeyCode() == KeyEvent.VK_SPACE) ||
						(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && 
								text.charAt(text.length() - 1) == ' ')) {
					text = search.getText();
					return;
				}
				ComponentPool.getNameComponent().getButtonAdd().setEnabled(false);
				text = search.getText();
			}
			//ComponentPool.getNameComponent().
			synchronized (thread) {
				System.err.println(flag + " notify");
				thread.notify();
			}
		}
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == confirm) {
			setIdAndClose();
		} else {
			confirm.setEnabled(false);
			changeAlphaUp(300, 0.8f, ComponentPool.getComponent());
			changeAlphaDown(300, 0, sheet, true);
		}
	}

	synchronized private void setSearchResult(int current) {
		if(current == flag && text.length() != 0) {
			System.out.println(text);//////////////////////////////
			LinkedHashMap<Integer, String> result = 
				kernel.find(text);
			System.out.println(result);//////////////////////////////////////////
			name.setMembers(result);
			System.err.println(current + " setMember end!");
			group.getTable().clearSelection();
			System.err.println(current + " clearSelection end!");
			//viewer.clear();
			System.err.println(current + " clearVierer end!");
			name.setSelected(0, 0);
			System.err.println(current + " setSelection end!");
		}
	}
	
	private class searchThread 
	extends Thread {
		private int current = 0;

		public void run() {
			while (true) {
				current = flag;
				System.err.println(current + " begin!");
				synchronized (this) {
					try {
						wait(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (current == flag) {
					setSearchResult(current);
					if(current != flag)
						continue;
					synchronized (this) {
						try {
							wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				System.err.println(current + " end!");
			}
		}
	}
}
