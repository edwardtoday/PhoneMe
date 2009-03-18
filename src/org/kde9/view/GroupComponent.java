package org.kde9.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;

import org.kde9.control.ButtonAddListener;
import org.kde9.control.Icontroller;

public class GroupComponent 
extends JPanel {
	private JTable table;
	private JButton buttonAdd;
	private JButton buttonSub;
	private DefaultTableModel model;
	
	GroupComponent() {
		table = new JTable(0, 1);
		model = (DefaultTableModel)table.getModel();
		buttonAdd = new JButton("+");
		buttonSub = new JButton("-");
		setLayout(new BorderLayout());
		add("North", new JLabel("group"));
		add("Center", table);
		JPanel panel = new JPanel();
		panel.add(buttonAdd);
		panel.add(buttonSub);
		add("South", panel);
		Icontroller.initGroup(this);
	}
	
	public DefaultTableModel getModel() {
		return model;
	}

	public void setModel(DefaultTableModel model) {
		this.model = model;
	}

	public void addListener(ActionListener bal, ActionListener bsl) {
		buttonAdd.addActionListener(bal);
		buttonSub.addActionListener(bsl);
	}

	public JTable getTable() {
		return table;
	}
}
