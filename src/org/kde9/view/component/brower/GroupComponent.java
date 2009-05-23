package org.kde9.view.component.brower;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
import org.kde9.util.Constants;
import org.kde9.view.ComponentPool;
import org.kde9.view.listener.AddGroupListener;
import org.kde9.view.listener.EditListener;

public class GroupComponent 
extends JPanel 
implements DropTargetListener, Constants {
	private JTable table;
	private JScrollPane pane;
	private JButton buttonAdd;
	// private JButton buttonSub;
	private DefaultTableModel model;
	private TitledBorder border;
	private JLabel sumLabel;
	
	private Kernel kernel;

	private LinkedHashMap<Integer, String> groups;
	
	private DropTarget dropTarget;
	private int loc = -1;
	private Set<Integer> selectedId;
	private int preGroupId;
	private Object flag;
	
	private HashSet<Integer> highLight;

	GroupComponent() {
		ComponentPool.setGroupComponent(this);
		kernel = ComponentPool.getComponent().getKernel();
		
		groups = new LinkedHashMap<Integer, String>();
		table = new JTable(0, 2) {
			public boolean isCellEditable(int i, int j) {
				return false;
			}
			
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(new Color(100,225,5,100));
				if(highLight != null && highLight.size() != 0)
					for(int i : highLight)
						g.fillRect(this.getColumnModel().getColumn(0).getWidth(), 
								this.getRowHeight()*(i) + 2,
								this.getColumnModel().getColumn(1).getWidth()/2 + 2,
								this.getRowHeight() - 5);
			}
		};
		//table.setDragEnabled(true);
		table.getColumnModel().getColumn(1).setMaxWidth(3);
		// dispatchEvent(new
		// FocusEvent(table.getComponent(0),FocusEvent.FOCUS_GAINED, true));
		// JTableHeader header = new JTableHeader();
		// header.setName("group");
		table.setTableHeader(null);
		//table.setCellSelectionEnabled(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		dropTarget = new DropTarget(table, DnDConstants.ACTION_COPY_OR_MOVE, this);
		
		// table.putClientProperty("Quaqua.Table.style", "striped");
		model = (DefaultTableModel) table.getModel();
		buttonAdd = new JButton("＋");
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
		BorderLayout layout = new BorderLayout();
		panel.setLayout(layout);
		panel.add("West", buttonAdd);
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		sumLabel = new JLabel();
		sumLabel.setForeground(Color.GRAY);
		panel.add("East", sumLabel);
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
//		addGroupListener.setMainComponent(component);
		buttonAdd.addActionListener(addGroupListener);
	}

	public void highLightGroup(HashSet<Integer> ids) {
		highLight = new LinkedHashSet<Integer>();
		System.out.println("selectId : " + ids);
		if(ids == null || ids.size() == 0) {
			repaint();
			return;
		}
		for(int i = 0; i < groups.size(); i++) {
			boolean flag = true;
			int groupId = (Integer) groups.keySet().toArray()[i];
			for(int cardId : ids) {	
				if(!kernel.getGroup(groupId).getGroupMembers().contains(cardId)) {
					flag = false;
					break;
				}
			}
			if(groupId != GROUPALLID &&
					groupId != getSelectedGroupId() &&
					kernel.getGroup(groupId) != null &&
					flag)
				highLight.add(i);
		}
		System.out.println("grouphighlight "+highLight);
		repaint();
	}
	
	public void tableListener(KeyListener kl, ListSelectionListener lsl) {
		table.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(lsl);
		table.addKeyListener(kl);
	}

	public void setGroups(LinkedHashMap<Integer, String> groups) {
		sumLabel.setText(String.valueOf(groups.size()));
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
		sumLabel.setText(String.valueOf(groups.size()));
	}

	public void deleteGroup() {
		if(table.getSelectedRow() != -1) {
			groups.remove(groups.keySet().toArray()[table.getSelectedRow()]);
			model.removeRow(table.getSelectedRow());
			sumLabel.setText(String.valueOf(groups.size()));
		}
	}
	
	public void renameGroup(String newName) {
		groups.put(getSelectedGroupId(), newName);
		model.setValueAt(newName, getSelected(), 0);
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

	public void dragEnter(DropTargetDragEvent e) {
		// TODO Auto-generated method stub
		Transferable transferable = e.getTransferable();
		DataFlavor d = e.getTransferable().getTransferDataFlavors()[0];
		try {
			if (transferable.getTransferData(d) != flag) {
				flag = transferable.getTransferData(d);
				System.out.println("记录选定");
				preGroupId = getSelectedGroupId();
				selectedId = 
					ComponentPool.getNameComponent().getSelectedMemberIds();
			}
		} catch (UnsupportedFlavorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void dragOver(DropTargetDragEvent e) {
		// TODO Auto-generated method stub
		Point p = MouseInfo.getPointerInfo().getLocation();
		Point pp = table.getLocationOnScreen();
		table.scrollRectToVisible(new Rectangle(
				pane.getWidth(), 
				table.getRowHeight()*table.getSelectedRow() - 15,
				0, 0));
		table.scrollRectToVisible(new Rectangle(
				pane.getWidth(), 
				table.getRowHeight()*(table.getSelectedRow()+1) + 15,
				0, 0));
		int loctemp = (p.y-pp.y)/table.getRowHeight();
		if(loc != loctemp) {
			loc = loctemp;
			System.out.println("设置选定");
			System.out.println(loctemp);
			setSelected(loc, loc);
		}
	}

	public void drop(DropTargetDropEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("drop!");
		if(preGroupId != -1)
			kernel.deleteGroupMember(preGroupId, selectedId);
		kernel.addGroupMember(getSelectedGroupId(), selectedId);
		ComponentPool.getBrowerComponent().showGroupMembers();
	}

	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
