package org.kde9.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

public class Iviewer 
extends JPanel {
	private JTable table;
	private JToggleButton button;
	
	Iviewer() {
		table = new JTable(0, 2);
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.addRow(new String[]{"1234"});
		model.addRow(new String[]{"2345"});
		button = new JToggleButton("Edit");
		setLayout(new BorderLayout());
		add("Center", table);
		JPanel panel = new JPanel();
		panel.add(button);
		add("South", panel);
	}
}
