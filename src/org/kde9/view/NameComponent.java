package org.kde9.view;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;


public class NameComponent
extends JPanel {
	private JLabel label;
	private JTable table;
	private JButton buttonAdd;
	private JButton buttonSub;
	private DefaultTableModel model;
	
	private HashMap<Integer, String> members;

	NameComponent() {
		table = new JTable(0, 1);
		model = (DefaultTableModel)table.getModel();
		label = new JLabel("name");
		buttonAdd = new JButton("+");
		buttonSub = new JButton("-");
		//buttonAdd.putClientProperty("Quaqua.Button.style", "toolBarTab");
		setLayout(new BorderLayout());
		add("North", label);
		label.setBorder(BorderFactory.createEtchedBorder());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		add("Center", table);
		JPanel panel = new JPanel();
		panel.add(buttonAdd);
		panel.add(buttonSub);
		add("South", panel);
		table.setBorder(BorderFactory.createEtchedBorder());
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
