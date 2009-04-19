package org.kde9.view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.TreeModel;

import org.kde9.model.TreeNode;

public class BrowerComponent extends JPanel implements ListSelectionListener,
		KeyListener {
	private GroupComponent group;
	private NameComponent name;
	private ViewerComponent viewer;
	JSplitPane groupSplitPane;
	JSplitPane nameSplitPane;
	TreeModel treeModel;
	TreeNode node;

	public BrowerComponent(TreeModel treeModel) {
		group = Factory.createGroup();
		group.getTable().getSelectionModel().addListSelectionListener(this);
		group.getTable().addKeyListener(this);
		name = Factory.creatName();
		name.getTable().getSelectionModel().addListSelectionListener(this);
		name.getTable().addKeyListener(this);
		viewer = Factory.createViewer();
		groupSplitPane = new JSplitPane();
		groupSplitPane.setLeftComponent(group);
		groupSplitPane.setRightComponent(name);
		groupSplitPane.setDividerLocation(200);
		groupSplitPane.setResizeWeight(0);
		groupSplitPane.setDividerSize(2);
		nameSplitPane = new JSplitPane();
		nameSplitPane.setLeftComponent(groupSplitPane);
		nameSplitPane.setRightComponent(viewer);
		nameSplitPane.setDividerLocation(400);
		nameSplitPane.setResizeWeight(0.5);
		nameSplitPane.setDividerSize(2);
		this.treeModel = treeModel;

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		add(nameSplitPane);
		// groupSplitPane.setDividerLocation(0.5d);
		// add(nameSplitPane);
		init();
	}

	private void init() {
		showAllGroups(0, 0);
		// showGroupMembers(0, 0, 0);
	}

	public void showAllGroups(int begin, int end) {
		Object root = treeModel.getRoot();
		int sum = treeModel.getChildCount(root);
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		for (int i = 0; i < sum; i++) {
			// System.out.println(i);
			TreeNode item = (TreeNode) treeModel.getChild(root, i);
			// System.out.println(item);
			map.put(item.getId(), item.getName());
		}
		group.setGroups(map);
		group.setSelected(begin, end);
	}

	public void showGroupMembers(int index, int begin, int end) {
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		if (index < treeModel.getChildCount(treeModel.getRoot())) {
			node = (TreeNode) treeModel.getChild(treeModel.getRoot(), index);
			int sum = treeModel.getChildCount(node);
			for (int i = 0; i < sum; i++) {
				// System.out.println(i);
				TreeNode item = (TreeNode) treeModel.getChild(node, i);
				// System.out.println(item);
				map.put(item.getId(), item.getName());
			}
		}
		name.setMembers(map);
		name.setSelected(begin, end);
	}

	public void showItem(int index) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		if (index < treeModel.getChildCount(node)) {
			Object person = treeModel.getChild(node, index);
			viewer.setName(((TreeNode) person).getName());
			int sum = treeModel.getChildCount(person);
			for (int i = 0; i < sum; i++) {
				// System.out.println(i);
				TreeNode item = (TreeNode) treeModel.getChild(person, i);
				// System.out.println(item);
				map.put(item.getName(), item.getContent());
			}
		}
		viewer.setItems(map);
	}

	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(group.getSelectionModel())) {
			if (group.getSelected() != -1)
				showGroupMembers(group.getSelected(), 0, 0);
		} else if (e.getSource().equals(name.getSelectionModel())) {
			if (name.getSelected() != -1)
				showItem(name.getSelected());
		}
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (group.getTable().hasFocus()) {
				name.getTable().requestFocusInWindow();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (name.getTable().hasFocus()) {
				group.getTable().requestFocusInWindow();
			}
		}
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
