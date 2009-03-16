package org.kde9.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;

public class Imenubar 
extends JMenuBar {
	private JButton button1;
	private JButton button2;
	private JTextArea textArea;
	
	Imenubar() {
		button1 = new JButton("<");
		button2 = new JButton(">");
		textArea = new JTextArea();
		add(button1);
		add(button2);
		add(new JLabel("      "));
		add(new JLabel("Search"));
		add(textArea);
	}
}
