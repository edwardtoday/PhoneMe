package org.kde9.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;


public class NameComponent
extends JPanel {
	private JList list;
	private JButton buttonAdd;
	private JButton buttonSub;
	private TitledBorder border;
	
	private LinkedHashMap<Integer, String> members;

	NameComponent() {
		list = new JList();
		buttonAdd = new JButton("+");
		buttonSub = new JButton("-");
		border = new TitledBorder("name");
		border.setTitleJustification(TitledBorder.CENTER);
		
		setLayout(new BorderLayout());
		add("Center", list);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout());
		panel.add(buttonAdd);
		panel.add(buttonSub);
		add("South", panel);
		setBorder(border);
		//list.setBorder(BorderFactory.createEtchedBorder());
	}

//	public void setMember(LinkedHashMap<Integer, String> members) {
//		this.members = members;
//		while(model.getRowCount() != 0)
//			model.removeRow(0);
//		if(members.size() != 0) {
//			for(String name : members.values())
//				model.addRow(new Object[]{name});
//		}
//	}
//	
//	public void addMember(int id, String name) {
//		members.put(id, name);
//		model.addRow(new Object[]{name});
//	}
//	
//	public void deleteMember() {
//		members.remove(members.keySet().toArray()[list.getSelectedIndex()]);
//		model.removeRow(list.getSelectedIndex());
//	}
	
	public JList getList() {
		return list;
	}

	public JButton getButtonAdd() {
		return buttonAdd;
	}

	public JButton getButtonSub() {
		return buttonSub;
	}
	
//	public DefaultTableModel getModel() {
//		return model;
//	}
}
