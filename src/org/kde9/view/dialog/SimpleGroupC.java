package org.kde9.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.kde9.control.Kernel;
import org.kde9.view.ComponentPool;
import org.kde9.view.listener.AddGroupListener;
import org.kde9.view.listener.EditListener;

public class SimpleGroupC 
extends JPanel {
	private JTable table;
	private JScrollPane pane;
	private DefaultTableModel model;
	private TitledBorder border;
	
	private Kernel kernel;

	private LinkedHashMap<Integer, String> groups;

	SimpleGroupC() {
		kernel = ComponentPool.getComponent().getKernel();
		
		groups = new LinkedHashMap<Integer, String>();
		table = new JTable(0, 2) {
			public boolean isCellEditable(int i, int j) {
				return false;
			}
		};
		table.getColumnModel().getColumn(1).setMaxWidth(3);
		table.setTableHeader(null);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		model = (DefaultTableModel) table.getModel();

		border = new TitledBorder("group");
		border.setTitleJustification(TitledBorder.CENTER);

		setLayout(new BorderLayout());
		pane = new JScrollPane(table);
		pane.setBorder(BorderFactory.createEmptyBorder());
		add("Center", pane);

		Color color = getBackground();
		// table.setBackground(color);

		setBorder(border);
		dispatchEvent(new FocusEvent(table, FocusEvent.FOCUS_GAINED, true));

		// dispatchEvent(new FocusEvent(table,FocusEvent.FOCUS_GAINED, true));
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
			for (String name : groups.values()) {
				model.addRow(new Object[] { name, ">" });
			}
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
		if(table.getSelectedRow() != -1) {
			groups.remove(groups.keySet().toArray()[table.getSelectedRow()]);
			model.removeRow(table.getSelectedRow());
		}
	}
	
	public void renameGroup(String newName) {
		groups.put(getSelectedGroupId(), newName);
		model.setValueAt(newName, getSelected(), 0);
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public ListSelectionModel getSelectionModel() {
		return table.getSelectionModel();
	}

	public JTable getTable() {
		return table;
	}
}
