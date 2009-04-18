package org.kde9.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;


public class ViewerComponent 
extends JPanel {
	private JTable table;
	private JScrollPane pane;
	private JToggleButton buttonEdit;
	private DefaultTableModel model;
	private TitledBorder border;
	private JPanel photoPanel;
	private JLabel name;
	private JButton photo;
	
	private LinkedHashMap<Integer, String> items;
	
	ViewerComponent() {
		items = new LinkedHashMap<Integer, String>();
		table = new JTable(0, 1);
//		JTableHeader header = new JTableHeader();
//		header.setName("group");
		table.setTableHeader(null);
		//table.putClientProperty("Quaqua.Table.style", "striped");
		model = (DefaultTableModel)table.getModel();
		buttonEdit = new JToggleButton("Edit");
		buttonEdit.putClientProperty("Quaqua.Button.style", "toolBarRollover");
//		buttonSub = new JButton("-");
//		buttonSub.putClientProperty("Quaqua.Button.style", "toolBarRollover");

		border = new TitledBorder("view");
		border.setTitleJustification(TitledBorder.CENTER);
		
		setLayout(new BorderLayout());
		
		JPanel upPanel = new JPanel();
		GridBagLayout upLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		
		upPanel.setLayout(upLayout);
		photoPanel = new JPanel();
		photoPanel.setBorder(BorderFactory.createEtchedBorder());
		//photoPanel.setPreferredSize(new Dimension(200, 30));
		photoPanel.setLayout(new GridLayout(0, 3));
		photo = new JButton("X");
		photo.putClientProperty("Quaqua.Button.style", "colorWell");
		name = new JLabel();
		name.setFont(new Font("Serif", 1, 20));
		name.setHorizontalAlignment(JLabel.CENTER);
		name.setBackground(Color.red);
		photoPanel.add(photo);
		photoPanel.add(name);
		
        c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
        c.weighty = 0.4;
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
		upLayout.setConstraints(photoPanel, c); 
		upPanel.add(photoPanel);
        c.weighty = 1.0;
		pane = new JScrollPane(table);
		pane.setBorder(BorderFactory.createEmptyBorder());
		c.gridheight = GridBagConstraints.RELATIVE;
		upLayout.setConstraints(pane, c);
		upPanel.add(pane);
		add("Center", upPanel);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setLayout(flowLayout);
		panel.add(buttonEdit);
//		panel.add(buttonSub);
		add("South", panel);
		
		Color color = getBackground();
		//table.setBackground(color);
		
		setBorder(border);
	}
	
	public void setName(String name) {
		this.name.setText(name);
	}
}
