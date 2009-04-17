package org.kde9.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


public class GroupComponent 
extends JPanel {
	private JList list;
	private JButton buttonAdd;
	private JButton buttonSub;
	private TitledBorder border;
	
	private LinkedHashMap<Integer, String> groups;
	
	GroupComponent() {
		list = new JList();
		buttonAdd = new JButton("+");
		buttonSub = new JButton("-");
		border = new TitledBorder("group");
		border.setTitleJustification(TitledBorder.CENTER);
		
		setLayout(new BorderLayout());
		add("Center", list);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout());
		panel.add(buttonAdd);
		panel.add(buttonSub);
		add("South", panel);
		setBorder(border);
	}
	
	public void buttonAddListener(ActionListener al) {
		buttonAdd.addActionListener(al);
	}
	
	public void buttonSubListener(ActionListener al) {
		buttonSub.addActionListener(al);
	}
	
//	public void tableListener(KeyListener kl, ListSelectionListener lsl) {
//		table.getSelectionModel().setSelectionMode(
//				ListSelectionModel.SINGLE_SELECTION );
//		table.getSelectionModel().addListSelectionListener(lsl);
//		table.addKeyListener(kl);
//	}
//	
//	public void setGroups(HashMap<Integer, String> groups) {
//		this.groups = groups;
//		while(model.getRowCount() != 0)
//			model.removeRow(0);
//		if(groups.size() != 0) {
//			for(String name : groups.values())
//				model.addRow(new Object[]{name});
//		}
//	}
//	
//	public void addGroup(int id, String name) {
//		groups.put(id, name);
//		model.addRow(new Object[]{name});
//	}
//	
//	public void deleteGroup() {
//		groups.remove(groups.keySet().toArray()[table.getSelectedRow()]);
//		model.removeRow(table.getSelectedRow());
//	}
//	
//	public DefaultTableModel getModel() {
//		return model;
//	}

	public JButton getButtonAdd() {
		return buttonAdd;
	}

	public JButton getButtonSub() {
		return buttonSub;
	}

//	public JTable getTable() {
//		return table;
//	}
}
