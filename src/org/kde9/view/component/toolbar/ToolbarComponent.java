﻿package org.kde9.view.component.toolbar;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;

import javax.naming.CompoundName;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.kde9.control.Kernel;
import org.kde9.util.Constants;
import org.kde9.view.ComponentPool;
import org.kde9.view.component.brower.BrowerComponent;
import org.kde9.view.component.cube.Cube;

public class ToolbarComponent 
extends JMenuBar 
implements KeyListener, ActionListener, Constants {
	private JButton button1;
	private JButton button2;
	private JTextField textField;
	
	private boolean cube = false;
	
	private Kernel kernel;
	
	private int flag = 0;
	
	private String text = "";
	
	private searchThread thread;

	/**
	 * 
	 */
	public ToolbarComponent() {
		ComponentPool.setToolbarComponent(this);
		
		button1 = new JButton(new ImageIcon(getClass().getResource(IMGPATH + "brower.png")));
		button1.putClientProperty("Quaqua.Button.style", "toggleWest");
		button1.addActionListener(this);
		button2 = new JButton(new ImageIcon(getClass().getResource(IMGPATH + "cube.png")));
		button2.putClientProperty("Quaqua.Button.style", "toggleEast");
		button2.addActionListener(this);
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
		
		thread = new searchThread();
		thread.start();
	}

	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	synchronized private void setSearchResult(int current) {
		if(current == flag && text.length() != 0) {
//			System.out.println(text);//////////////////////////////
			LinkedHashMap<Integer, String> result = 
				kernel.find(text);
//			System.out.println(result);//////////////////////////////////////////
			ComponentPool.getNameComponent().setMembers(result);
//			System.err.println(current + " setMember end!");
			ComponentPool.getGroupComponent().getTable().clearSelection();
//			System.err.println(current + " clearSelection end!");
			ComponentPool.getViewerComponent().clear();
//			System.err.println(current + " clearVierer end!");
			ComponentPool.getNameComponent().setSelected(0, 0);
//			System.err.println(current + " setSelection end!");
		}
	}

	public void keyReleased(KeyEvent e) {
		ComponentPool.getViewerComponent().setHighlight(true);
//		System.out.println(textField.getText().length() + " textfield content");
		// TODO Auto-generated method stub
		flag = (flag + 1)%100;
		final int current = flag;
		if(e.getKeyCode() != KeyEvent.VK_ENTER && text.equals(textField.getText()))
			return;
		if(textField.getText().length() == 0) {
			ComponentPool.getGroupComponent().setSelected(0, 0);
			text = textField.getText();
			return;
		}
		else {
			if ((textField.getText().charAt(textField.getText().length() - 1) == ' ' &&
					e.getKeyCode() == KeyEvent.VK_SPACE) ||
					(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && 
							text.charAt(text.length() - 1) == ' ')) {
				text = textField.getText();
				return;
			}
			ComponentPool.getNameComponent().getButtonAdd().setEnabled(false);
			text = textField.getText();
		}
		//ComponentPool.getNameComponent().
		synchronized (thread) {
//			System.err.println(flag + " notify");
			thread.notify();
		}
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private class searchThread 
	extends Thread {
		private int current = 0;

		public void run() {
			while (true) {
				current = flag;
//				System.err.println(current + " begin!");
				synchronized (this) {
					try {
						wait(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (current == flag) {
					setSearchResult(current);
					if(current != flag)
						continue;
					synchronized (this) {
						try {
							wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
//				System.err.println(current + " end!");
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == button1 && cube) {
			ComponentPool.getComponent().resetCenterComponent();
			cube = false;
			textField.setEnabled(true);
			ComponentPool.getViewerComponent().getButtonEdit().setEnabled(true);
			ComponentPool.getComponent().repaint();
		} else if(e.getSource() == button2 && !cube) {
			int a = ComponentPool.getBrowerComponent().getLeftW();
			int b = ComponentPool.getBrowerComponent().getLeftH();
			JPanel panel = Cube.getCube(a, b);
			ComponentPool.getComponent().setCenterComponent(panel);
			cube = true;
			textField.setEnabled(false);
			ComponentPool.getViewerComponent().getButtonEdit().setEnabled(false);
			ComponentPool.getComponent().repaint();
		}
	}
}

