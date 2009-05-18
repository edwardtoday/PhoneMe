﻿package org.kde9.view.component.brower;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.kde9.view.ComponentPool;
import org.kde9.view.listener.AddGroupListener;
import org.kde9.view.listener.AddNameListener;

public class NameComponent extends JPanel {
	private JTable table;
	private JScrollPane pane;
	private JButton buttonAdd;
	// private JButton buttonSub;
	private DefaultTableModel model;
	private TitledBorder border;
	
	private int threadId = 0;
	
	private boolean ready = true;

	private LinkedHashMap<Integer, String> members;

	public void nextThread() {
		threadId = (threadId + 1)%100;
	}
	
	NameComponent() {
		ComponentPool.setNameComponent(this);
		
		members = new LinkedHashMap<Integer, String>();
		table = new JTable(0, 1) {
			public boolean isCellEditable(int i, int j) {
				return false;
			}
		};
		// JTableHeader header = new JTableHeader();
		// header.setName("group");
		table.setTableHeader(null);
		// table.putClientProperty("Quaqua.Table.style", "striped");
		model = (DefaultTableModel) table.getModel();
		buttonAdd = new JButton("＋");
		buttonAdd.putClientProperty("Quaqua.Button.style", "toolBarRollover");
		// buttonSub = new JButton("-");
		// buttonSub.putClientProperty("Quaqua.Button.style",
		// "toolBarRollover");

		border = new TitledBorder("name");
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
	}

	public void buttonAddListener(ActionListener al) {
		buttonAdd.addActionListener(al);
	}

	public void addNameListener(AddNameListener addNameListener) {
		addNameListener.setComponent(this);
//		addNameListener.setMainComponent(component);
		buttonAdd.addActionListener(addNameListener);
	}

	public void tableListener(KeyListener kl, ListSelectionListener lsl) {
		table.setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.getSelectionModel().addListSelectionListener(lsl);
		table.addKeyListener(kl);
	}

	public void setMembers(final LinkedHashMap<Integer, String> members) {
		this.members = members;
		final int id = threadId;
		while (model.getRowCount() != 0) {
			model.removeRow(0);
		}
		if (members.size() != 0) {
			new Thread() {
				public void run() {
					String name;
					int i = 0;
					while(true) {
//						System.err.print(threadId);
						if(i < members.size()) {
							name = members.get(members.keySet().toArray()[i]);
							i++;
							if (!addRow(id, threadId, name))
								return;
						} else if(ready)
							break;
						else
							try {
								sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}
			}.start();
		}
//		System.err.println('\n');
	}
	
	synchronized private boolean addRow(int id, int thread, String name) {
		if (id == threadId) {
			model.addRow(new Object[] { name });
			return true;
		}
		else
			return false;
	}

	public void setSelected(final int indexs, final int indexe) {
//		System.out.print(":"+indexs);
		if (indexs != -1 && members.size() > 0)
//			new Thread() {
//				public void run() {
			while(!selectSet(indexs, indexe)) {}
//				}
//			}.start();
		//table.getSelectionModel().clearSelection();
//		if (indexs != -1)
//			table.getSelectionModel().setSelectionInterval(indexs, indexe);
	}
	
	synchronized private boolean selectSet(int indexs, int indexe) {
		if(indexs <= indexe && indexe < table.getRowCount() && indexs >=0) {
			table.getSelectionModel().clearSelection();
			table.getSelectionModel().setSelectionInterval(indexs, indexe);
			return true;
		}
		return false;
	}

	public int getSelected() {
		int selected = table.getSelectedRow();
		if(members.size() == 0 || selected >= members.size())
			return -1;
		else
			return selected;
	}

	public int getSelectedMemberId() {
		int id;
		int temp = getSelected();
		if(temp != -1)
			id = (Integer) members.keySet().toArray()[temp];
		else
			id = -1;
		return id;
	}
	
	public void addMember(int id, String name) {
		members.put(id, name);
		model.addRow(new Object[] { name });
	}

	public void deleteMember() {
		members.remove(members.keySet().toArray()[table.getSelectedRow()]);
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
