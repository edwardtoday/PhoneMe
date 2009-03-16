package org.kde9.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class Iviewer 
extends JPanel {
	private JEditorPane editorPane;
	private JToggleButton button;
	
	Iviewer() {
		editorPane = new JEditorPane();
		button = new JToggleButton("Edit");
		setLayout(new BorderLayout());
		add("Center", editorPane);
		JPanel panel = new JPanel();
		panel.add(button);
		add("South", panel);
	}
}
