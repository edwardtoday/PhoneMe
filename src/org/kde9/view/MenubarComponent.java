package org.kde9.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenubarComponent extends JMenuBar {
	private JButton button1;
	private JButton button2;
	private JTextField textField;

	MenubarComponent() {
		button1 = new JButton("<<");
		button1.putClientProperty("Quaqua.Button.style", "toggleWest");
		button2 = new JButton(">>");
		button2.putClientProperty("Quaqua.Button.style", "toggleEast");
		textField = new JTextField();

		// textField.putClientProperty("JComponent.sizeVariant","small");
		textField.putClientProperty("Quaqua.TextField.style", "search");
		// textField.setOpaque(true);

		GridBagLayout upLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(upLayout);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.0;
		c.gridwidth = 1;
		upLayout.setConstraints(button1, c);
		add(button1);
		upLayout.setConstraints(button2, c);
		add(button2);
		JLabel blank = new JLabel();
		c.weightx = 1.0;
		upLayout.setConstraints(blank, c);
		add(blank);
		JLabel search = new JLabel("Search");
		c.weightx = 0.0;
		upLayout.setConstraints(search, c);
		add(search);
		// JButton button = new JButton("dd");
		// button.putClientProperty("Quaqua.Button.style", "toggleWest");
		// button.add(textField);
		// textField.setBorder(BorderFactory.createEmptyBorder());
		// JPanel panel = new JPanel();
		// panel.setLayout(new BorderLayout());
		// panel.setBorder(BorderFactory.createCompoundBorder(
		// BorderFactory.createEmptyBorder(3, 3, 3, 3),
		// BorderFactory.createLineBorder(Color.BLACK)));
		// panel.add("West",button);
		// panel.add("Center",textField);
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weightx = 0.35;
		upLayout.setConstraints(textField, c);
		add(textField);
	}
}
