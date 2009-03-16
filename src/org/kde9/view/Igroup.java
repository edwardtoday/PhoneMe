package org.kde9.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.kde9.control.ButtonAddListener;

public class Igroup 
extends JPanel {
	private JEditorPane editorPane;
	private JButton buttonAdd;
	private JButton buttonSub;
	
	Igroup() {
		editorPane = new JEditorPane();
		buttonAdd = new JButton("+");
		buttonSub = new JButton("-");
		setLayout(new BorderLayout());
		add("North", new JLabel("group"));
		add("Center", editorPane);
		JPanel panel = new JPanel();
		panel.add(buttonAdd);
		panel.add(buttonSub);
		add("South", panel);
	}
	
	public void addListener(ActionListener al) {
		buttonAdd.addActionListener(al);
	}
}
