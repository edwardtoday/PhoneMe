package org.kde9.view.component.toolbar;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JTextField;

import org.kde9.control.Kernel;
import org.kde9.view.ComponentPool;

public class ToolbarComponent 
extends JMenuBar 
implements KeyListener {
	private JButton button1;
	private JButton button2;
	private JTextField textField;
	
	private Kernel kernel;
	
	private int flag = 0;
	
	private String text = "";

	public ToolbarComponent() {
		ComponentPool.setToolbarComponent(this);
		
		button1 = new JButton("<<");
		button1.putClientProperty("Quaqua.Button.style", "toggleWest");
		button2 = new JButton(">>");
		button2.putClientProperty("Quaqua.Button.style", "toggleEast");
		textField = new JTextField();
		textField.addKeyListener(this);

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
		
		kernel = ComponentPool.getComponent().getKernel();
	}

	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	synchronized void setSearchResult(int current) {
		if(current == flag && textField.getText().length() != 0) {
			System.out.println(textField.getText());//////////////////////////////
			LinkedHashMap<Integer, String> result = 
				kernel.find(textField.getText());
			System.out.println(result);//////////////////////////////////////////
			ComponentPool.getNameComponent().setMembers(result);
			ComponentPool.getGroupComponent().getTable().clearSelection();
		}
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		flag = (flag + 1)%100;
		final int current = flag;
		if(text.equals(textField.getText()))
			return;
		text = textField.getText();
		new Thread() {
			public void run() {
				try {
					sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setSearchResult(current);
			}
		}.start();
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
