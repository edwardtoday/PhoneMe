package org.kde9.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Imenubar 
extends JMenuBar {
	private JButton button1;
	private JButton button2;
	private JTextField textField;
	
	Imenubar() {
		button1 = new JButton("<");
		button2 = new JButton(">");
		textField = new JTextField();
		add(button1);
		add(button2);
		add(new JLabel("      "));
		add(new JLabel("Search"));
		add(textField);
	}
}
