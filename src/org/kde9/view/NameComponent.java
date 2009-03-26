package org.kde9.view;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class NameComponent
extends JPanel {
	private JTable table;
	private JButton buttonAdd;
	private JButton buttonSub;
	private DefaultTableModel model;
	
	private HashMap<Integer, String> members;

	NameComponent() {
		table = new JTable(0, 1);
		model = (DefaultTableModel)table.getModel();
		buttonAdd = new JButton("+");
		buttonSub = new JButton("-");
		setLayout(new BorderLayout());
		add("North", new JLabel("name"));
		add("Center", table);
		JPanel panel = new JPanel();
		panel.add(buttonAdd);
		panel.add(buttonSub);
		add("South", panel);
	}

	public void setMember(HashMap<Integer, String> members) {
		this.members = members;
		while(model.getRowCount() != 0)
			model.removeRow(0);
		if(members.size() != 0) {
			for(String name : members.values())
				model.addRow(new Object[]{name});
		}
	}
	
	public void addMember(int id, String name) {
		members.put(id, name);
		model.addRow(new Object[]{name});
	}
	
	public void deleteMember() {
		members.remove(members.keySet().toArray()[table.getSelectedRow()]);
		model.removeRow(table.getSelectedRow());
	}
	
	public JTable getTable() {
		return table;
	}

	public JButton getButtonAdd() {
		return buttonAdd;
	}

	public JButton getButtonSub() {
		return buttonSub;
	}
	
	public DefaultTableModel getModel() {
		return model;
	}
}
