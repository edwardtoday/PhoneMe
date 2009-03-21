package org.kde9.view;

import java.awt.BorderLayout;

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
