package org.kde9.view;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.tree.TreeModel;

import org.kde9.model.Main;

import ch.randelshofer.quaqua.JBrowser;
import ch.randelshofer.quaqua.JBrowserViewport;


public class BrowerComponent
extends JPanel {
	private JList groupList;
	private JList nameList;
    JScrollPane groupScrollPane;
    JScrollPane nameScrollPane;
    JSplitPane splitPane;
    TreeModel treeModel;
    
    public BrowerComponent(TreeModel treeModel) {
    	this.treeModel = treeModel;
    	;
    }
    
    
    public static void main(String args[]) {
    	//BrowerComponent comp = new BrowerComponent();
    	JFrame frame = new JFrame();
    	//frame.add(comp.scrollPane);
    	frame.setSize(200, 200);
    	frame.setVisible(true);
    }
}
