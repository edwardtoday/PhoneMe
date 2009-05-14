package org.kde9.view.component.brower;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.kde9.view.listener.AddGroupListener;
import org.kde9.view.listener.EditListener;

public class GroupComponent extends JPanel {
	private JTable table;
	private JScrollPane pane;
	private JButton buttonAdd;
	// private JButton buttonSub;
	private DefaultTableModel model;
	private TitledBorder border;

	private LinkedHashMap<Integer, String> groups;

	GroupComponent() {
		groups = new LinkedHashMap<Integer, String>();
		table = new JTable(0, 2) {
			public boolean isCellEditable(int i, int j) {
				if (j == 0)
					return true;
				return false;
			}
		};
		table.setDragEnabled(true);
		table.getColumnModel().getColumn(1).setMaxWidth(3);
		// dispatchEvent(new
		// FocusEvent(table.getComponent(0),FocusEvent.FOCUS_GAINED, true));
		// JTableHeader header = new JTableHeader();
		// header.setName("group");
		table.setTableHeader(null);
		// table.putClientProperty("Quaqua.Table.style", "striped");
		model = (DefaultTableModel) table.getModel();
		buttonAdd = new JButton("гл");
		buttonAdd.putClientProperty("Quaqua.Button.style", "toolBarRollover");
		// buttonSub = new JButton("-");
		// buttonSub.putClientProperty("Quaqua.Button.style",
		// "toolBarRollover");

		border = new TitledBorder("group");
		border.setTitleJustification(TitledBorder.CENTER);

		setLayout(new BorderLayout());
		pane = new JScrollPane(table);
		pane.setBorder(BorderFactory.createEmptyBorder());
		add("Center", pane);
		JPanel panel = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setLayout(flowLayout);
		panel.add(buttonAdd);
		// panel.add(buttonSub);
		add("South", panel);

		Color color = getBackground();
		// table.setBackground(color);

		setBorder(border);
		dispatchEvent(new FocusEvent(table, FocusEvent.FOCUS_GAINED, true));

		// dispatchEvent(new FocusEvent(table,FocusEvent.FOCUS_GAINED, true));
	}

	public void buttonAddListener(ActionListener al) {
		buttonAdd.addActionListener(al);
	}

	public void addGroupListener(AddGroupListener addGroupListener) {
		addGroupListener.setComponent(this);
		buttonAdd.addActionListener(addGroupListener);
	}

	public void tableListener(KeyListener kl, ListSelectionListener lsl) {
		table.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(lsl);
		table.addKeyListener(kl);
	}

	public void setGroups(LinkedHashMap<Integer, String> groups) {
		this.groups = groups;
		while (model.getRowCount() != 0)
			model.removeRow(0);
		if (groups.size() != 0) {
			for (String name : groups.values())
				model.addRow(new Object[] { name, ">" });
		}
	}

	public void setSelected(int indexs, int indexe) {
		if (indexs == -1)
			table.getSelectionModel().clearSelection();
		else
			table.getSelectionModel().setSelectionInterval(indexs, indexe);
	}

	public int getSelected() {
		int selected = table.getSelectedRow();
		if(groups.size() == 0 || selected >= groups.size())
			return -1;
		else
			return selected;
	}
	
	public int getSelectedGroupId() { 
		int id;
		if(getSelected() != -1)
			id = (Integer) groups.keySet().toArray()[getSelected()];
		else
			id = -1;
		return id;
	}

	public void addGroup(int id, String name) {
		groups.put(id, name);
		model.addRow(new Object[] { name, ">" });
	}

	public void deleteGroup() {
		groups.remove(groups.keySet().toArray()[table.getSelectedRow()]);
		model.removeRow(table.getSelectedRow());
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public JButton getButtonAdd() {
		return buttonAdd;
	}

	public ListSelectionModel getSelectionModel() {
		return table.getSelectionModel();
	}

	// public JButton getButtonSub() {
	// return buttonSub;
	// }

	public JTable getTable() {
		return table;
	}
}
