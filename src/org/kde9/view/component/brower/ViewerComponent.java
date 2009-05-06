package org.kde9.view.component.brower;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.kde9.control.Kernel;
import org.kde9.model.card.ConstCard;
import org.kde9.view.listener.EditListener;

public class ViewerComponent extends JPanel {
	private JTable table;
	private JPanel upPanel;
	private JScrollPane pane;
	private JToggleButton buttonEdit;
	private DefaultTableModel model;
	private TitledBorder border;
	private JPanel photoPanel;
	private JPanel namePanel;
	private JLabel name;
	private JLabel pinyin;
	private JButton photo;
	private Kernel kernel;
	private ContentEditPanelComponent edit;
	private ConstCard card;

	private LinkedHashMap<String, Vector<String>> items;

	ViewerComponent(Kernel kernel) {
		this.kernel = kernel;
		edit = new ContentEditPanelComponent(kernel);
		items = new LinkedHashMap<String, Vector<String>>();
		table = new JTable(0, 3);
		table.setFocusable(false);
		table.setCellEditor(null);
		table.getColumnModel().getColumn(0).setMaxWidth(80);
		table.getColumnModel().getColumn(1).setMaxWidth(80);
		// JTableHeader header = new JTableHeader();
		// header.setName("group");
		table.setTableHeader(null);
		// table.putClientProperty("Quaqua.Table.style", "striped");
		model = (DefaultTableModel) table.getModel();
		buttonEdit = new JToggleButton("Edit");
		buttonEdit.putClientProperty("Quaqua.Button.style", "toolBarRollover");
		// buttonSub = new JButton("-");
		// buttonSub.putClientProperty("Quaqua.Button.style",
		// "toolBarRollover");

		border = new TitledBorder("view");
		border.setTitleJustification(TitledBorder.CENTER);

		setLayout(new BorderLayout());

		upPanel = new JPanel();
		GridBagLayout upLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		upPanel.setLayout(upLayout);
		photoPanel = new JPanel();
		photoPanel.setBorder(BorderFactory.createEtchedBorder());
		// photoPanel.setPreferredSize(new Dimension(200, 30));
		photoPanel.setLayout(new GridLayout(0, 3));
		photo = new JButton("X");
		photo.putClientProperty("Quaqua.Button.style", "colorWell");
		//photo.setSize(200, 200);
		name = new JLabel();
		name.setFont(new Font("HeiTi", 1, 30));
		name.setHorizontalAlignment(JLabel.CENTER);
		pinyin = new JLabel();
		pinyin.setFont(new Font("Serif", 1, 15));
		pinyin.setHorizontalAlignment(JLabel.CENTER);
		namePanel = new JPanel();
		namePanel.setLayout(new BorderLayout());
		namePanel.add("North", pinyin);
		namePanel.add("Center", name);
		photoPanel.add(photo);
		photoPanel.add(namePanel);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 0.30;
		c.gridwidth = GridBagConstraints.REMAINDER; // end row
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
		// panel.add(buttonSub);
		add("South", panel);

		Color color = getBackground();
		// table.setBackground(color);

		setBorder(border);
	}

	public void addEditListener(EditListener editListener) {
		editListener.setComponent(this);
		buttonEdit.addActionListener(editListener);
	}
	
	public void startEditModel() {
		//pane.removeAll();
		edit.startEdit(card);
		pane.setViewportView(edit);
		//pane.updateUI();
		updateUI();
	}
	
	public void stopEditModel() {
		
	}
	
	public void setCard(ConstCard card) {
		this.card = card;
	}
	
	public void setName(String name) {
		this.name.setText(name);
	}
	
	public void setPinYin(String pinyin) {
		this.pinyin.setText(pinyin);
	}

	public void setItems(LinkedHashMap<String, Vector<String>> items) {
		this.items = items;
		while (model.getRowCount() != 0)
			model.removeRow(0);
		model.addRow(new Object[] { "" });
		if (items.size() != 0) {
			for (String name : items.keySet()) {
				//model.addRow(new Object[] { "", name});
				boolean flag = true;
				for(String value : items.get(name))
					if(flag) {
						model.addRow(new Object[] { "", name, value });
						flag = false;
					} else
						model.addRow(new Object[] { "", "", value });
			}
		}
	}

//	public void addItem(String name, String content) {
//		items.put(name, content);
//		model.addRow(new Object[] { "", name, content });
//	}
}
