package org.kde9.view;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class Iname
extends JPanel {
	private JList list;
	private JButton buttonAdd;
	private JButton buttonSub;
	
	Iname() {
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		list = new JList(model);
		model.addElement("3");
		model.addElement("4");
		buttonAdd = new JButton("+");
		buttonSub = new JButton("-");
		setLayout(new BorderLayout());
		add("North", new JLabel("name"));
		add("Center", list);
		JPanel panel = new JPanel();
		panel.add(buttonAdd);
		panel.add(buttonSub);
		add("South", panel);
	}
}
