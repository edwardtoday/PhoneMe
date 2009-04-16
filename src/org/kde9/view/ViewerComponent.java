package org.kde9.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;


public class ViewerComponent 
extends JPanel {
	private JTable table;
	private JToggleButton button;
	DefaultTableModel model;
	
	ViewerComponent() {
		table = new JTable(0, 2);
		model = (DefaultTableModel)table.getModel();
		button = new JToggleButton("Edit");
		setLayout(new BorderLayout());
		add("Center", table);
		JPanel panel = new JPanel();
		panel.add(button);
		add("South", panel);
		table.setBorder(BorderFactory.createEtchedBorder());
	}
}
