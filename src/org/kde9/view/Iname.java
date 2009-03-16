package org.kde9.view;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Iname 
extends JPanel {
	private JEditorPane editorPane;
	private JButton buttonAdd;
	private JButton buttonSub;
	
	Iname() {
		editorPane = new JEditorPane();
		buttonAdd = new JButton("+");
		buttonSub = new JButton("-");
		setLayout(new BorderLayout());
		add("North", new JLabel("name"));
		add("Center", editorPane);
		JPanel panel = new JPanel();
		panel.add(buttonAdd);
		panel.add(buttonSub);
		add("South", panel);
	}
}
