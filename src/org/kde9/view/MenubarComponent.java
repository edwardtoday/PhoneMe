package org.kde9.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class MenubarComponent 
extends JMenuBar {
	private JButton button1;
	private JButton button2;
	private JTextField textField;
	
	MenubarComponent() {
		button1 = new JButton("<");
		button1.putClientProperty("Quaqua.Button.style", "toggleWest");
		button2 = new JButton(">");
		button2.putClientProperty("Quaqua.Button.style", "toggleEast");
		textField = new JTextField();
		
		textField.putClientProperty("JComponent.sizeVariant","small");
		textField.putClientProperty("Quaqua.TextField.style","search");
		//textField.setOpaque(true);
		
		add(button1);
		add(button2);
		add(new JLabel("      "));
		add(new JLabel("Search"));
		add(textField);
	}
}
