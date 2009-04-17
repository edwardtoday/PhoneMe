package org.kde9.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


public class GroupComponent 
extends JPanel {
	private JTable table;
	private JScrollPane pane;
	private JButton buttonAdd;
	private JButton buttonSub;
	private DefaultTableModel model;
	private TitledBorder border;
	
	private LinkedHashMap<Integer, String> groups;
	
	GroupComponent() {
		groups = new LinkedHashMap<Integer, String>();
		table = new JTable(0, 1);
		model = (DefaultTableModel)table.getModel();
		buttonAdd = new JButton("+");
		buttonSub = new JButton("-");
		pane = new JScrollPane();

		border = new TitledBorder("group");
		border.setTitleJustification(TitledBorder.CENTER);
		
		setLayout(new BorderLayout());
		pane.add(table);
		add("Center", pane);
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
	
	public void tableListener(KeyListener kl, ListSelectionListener lsl) {
		table.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION );
		table.getSelectionModel().addListSelectionListener(lsl);
		table.addKeyListener(kl);
	}
	
	public void setGroups(LinkedHashMap<Integer, String> groups) {
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
