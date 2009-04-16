package org.kde9.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


public class GroupComponent 
extends JPanel {
	private JLabel label;
	private JTable table;
	private JButton buttonAdd;
	private JButton buttonSub;
	private DefaultTableModel model;
	
	private HashMap<Integer, String> groups;
	
	GroupComponent() {
		table = new JTable(0, 1);
		model = (DefaultTableModel)table.getModel();
		label = new JLabel("group");
		buttonAdd = new JButton("+");
		buttonSub = new JButton("-");
		setLayout(new BorderLayout());
		add("North", label);
		label.setBorder(BorderFactory.createRaisedBevelBorder());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		add("Center", table);
		JPanel panel = new JPanel();
		panel.add(buttonAdd);
		panel.add(buttonSub);
		add("South", panel);
		table.setBorder(BorderFactory.createEtchedBorder());
	}
	
	public void buttonAddListener(ActionListener al) {
		buttonAdd.addActionListener(al);
	}
	
	public void buttonSubListener(ActionListener al) {
		buttonSub.addActionListener(al);
	}
	
	public void tableListener(KeyListener kl, ListSelectionListener lsl) {
		table.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION );
		table.getSelectionModel().addListSelectionListener(lsl);
		table.addKeyListener(kl);
	}
	
	public void setGroups(HashMap<Integer, String> groups) {
		this.groups = groups;
		while(model.getRowCount() != 0)
			model.removeRow(0);
		if(groups.size() != 0) {
			for(String name : groups.values())
				model.addRow(new Object[]{name});
		}
	}
	
	public void addGroup(int id, String name) {
		groups.put(id, name);
		model.addRow(new Object[]{name});
	}
	
	public void deleteGroup() {
		groups.remove(groups.keySet().toArray()[table.getSelectedRow()]);
		model.removeRow(table.getSelectedRow());
	}
	
	public DefaultTableModel getModel() {
		return model;
	}

	public JButton getButtonAdd() {
		return buttonAdd;
	}

	public JButton getButtonSub() {
		return buttonSub;
	}

	public JTable getTable() {
		return table;
	}
}
