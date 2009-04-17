package org.kde9.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.tree.TreeModel;


public class BrowerComponent
extends JPanel {
	private GroupComponent group;
	private NameComponent name;
    JSplitPane groupSplitPane;
    //JSplitPane nameSplitPane;
    TreeModel treeModel;
    
    public BrowerComponent(TreeModel treeModel) {
    	group = new GroupComponent();
    	name = new NameComponent();
    	groupSplitPane = new JSplitPane();
    	groupSplitPane.setLeftComponent(group);
    	groupSplitPane.setRightComponent(name);
    	groupSplitPane.setDividerLocation(0.5d);
    	groupSplitPane.setResizeWeight(0.5);
    	groupSplitPane.setDividerSize(2);
    	//nameSplitPane = new JSplitPane();
    	this.treeModel = treeModel;
    	
    	setLayout(new BorderLayout());
    	add(groupSplitPane);
    	//groupSplitPane.setDividerLocation(0.5d);
    	//add(nameSplitPane);
    	init();
    }
    
    private void init() {
    	Object root = treeModel.getRoot();
    	int sum = treeModel.getChildCount(root);
    	for(int i = 0; i < sum; i++) {
    		System.out.println(i);
    		Object item = treeModel.getChild(root, i);
    		System.out.println(item);
    		group.addGroup(0, item.toString());
    	}
    }
    
    
    public static void main(String args[]) {
    	BrowerComponent comp = new BrowerComponent(null);
    	JFrame frame = new JFrame();
    	frame.add(comp);
    	frame.setSize(200, 200);
    	frame.setVisible(true);
    }
}
