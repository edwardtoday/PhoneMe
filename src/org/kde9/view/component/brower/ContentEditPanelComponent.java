package org.kde9.view.component.brower;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.kde9.control.Kernel;
import org.kde9.model.card.ConstCard;

public class ContentEditPanelComponent
extends JPanel
implements ActionListener {
	private Kernel kernel;
	private LinkedHashMap<JTextField, Vector<JTextArea>> contents;
	private Vector<JTextField> keys;
	private HashMap<JButton, Integer> index;
	
	private void initContents(ConstCard card) {
		LinkedHashMap<String, Vector<String>> items = card.getAllItems();
		contents = new LinkedHashMap<JTextField, Vector<JTextArea>>();
		keys = new Vector<JTextField>();
		for(String key : items.keySet()) {
			JPanel oneItemPanel = new JPanel();
			JTextField keyField = new JTextField(key);
			Vector<JTextArea> temp = new Vector<JTextArea>();
						
			for(String value : items.get(key)) {
				JTextArea valueArea = new JTextArea(value);
				temp.add(valueArea);
			}
			contents.put(keyField, temp);
			keys.add(keyField);
		}
	}
	
	private void initItemPanel() {
		index = new LinkedHashMap<JButton, Integer>();
		removeAll();
		int i = 0;
		for(JTextField key : contents.keySet()) {
			JPanel oneItemPanel = new JPanel();
			oneItemPanel.setBorder(BorderFactory.createEmptyBorder());
			JButton keybuttonAdd = new JButton("гл");
			keybuttonAdd.putClientProperty("Quaqua.Button.style", "toolBarRollover");
			keybuttonAdd.addActionListener(this);
			index.put(keybuttonAdd, ++i);
			oneItemPanel.add(keybuttonAdd);
			JButton keybuttonSub = new JButton("гн");
			keybuttonSub.putClientProperty("Quaqua.Button.style", "toolBarRollover");
			keybuttonSub.addActionListener(this);
			index.put(keybuttonSub, ++i);
			oneItemPanel.add(keybuttonSub);
			oneItemPanel.add(key);
			for(JTextArea value : contents.get(key)) {
				oneItemPanel.add(value);
			}
			JButton valuebuttonAdd = new JButton("гл");
			valuebuttonAdd.putClientProperty("Quaqua.Button.style", "toolBarRollover");
			oneItemPanel.add(valuebuttonAdd);
			valuebuttonAdd.addActionListener(this);
			index.put(valuebuttonAdd, ++i);
			JButton valuebuttonSub = new JButton("гн");
			valuebuttonSub.putClientProperty("Quaqua.Button.style", "toolBarRollover");
			oneItemPanel.add(valuebuttonSub);
			valuebuttonSub.addActionListener(this);
			index.put(valuebuttonSub, ++i);
			add(oneItemPanel);
		}
		repaint();
	}
	
	public ContentEditPanelComponent(Kernel kernel) {
		this.kernel = kernel;
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING, 0, 0);
		setLayout(flowLayout);
	}
	
	public void startEdit(ConstCard card) {
		removeAll();
		
		setLayout(new GridLayout(0,1));
		initContents(card);
		initItemPanel();
		updateUI();
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
