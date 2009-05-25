package org.kde9.view.component.brower;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.kde9.control.Kernel;
import org.kde9.util.Constants;
import org.kde9.view.ComponentPool;
import org.kde9.view.listener.AddNameListener;

public class NameComponent 
extends JPanel
implements MouseListener, Constants {
	private JTable table;
	private JScrollPane pane;
	private JButton buttonAdd;
	// private JButton buttonSub;
	private DefaultTableModel model;
	private TitledBorder border;
	private JLabel sumLabel;
	private JPopupMenu menu;
	private JMenu item;
	
	private static int threadId = 0;
	
	private boolean ready = true;
	
	private final Kernel kernel;

	private LinkedHashMap<Integer, String> members;
	
	public NameComponent() {
		ComponentPool.setNameComponent(this);
		
		members = new LinkedHashMap<Integer, String>();
		table = new JTable(0, 1) {
			public boolean isCellEditable(int i, int j) {
				return false;
			}
		};
		// JTableHeader header = new JTableHeader();
		// header.setName("group");
		table.setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setTableHeader(null);
		table.setDragEnabled(true);
		table.addMouseListener(this);
		
		menu = new JPopupMenu();
		menu.add(new JMenuItem("Export Card(s)"));
		menu.add(new JMenuItem("Remove From Group"));
		menu.add(new JMenuItem("Delete Card(s)"));
		menu.add(new JPopupMenu.Separator());
		item = new JMenu("Move To Group ..");
		menu.add(item);
		menu.setInvoker(table);
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
		
		kernel = ComponentPool.getComponent().getKernel();
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
		//table.setCellSelectionEnabled(true);
		table.getSelectionModel().addListSelectionListener(lsl);
		table.addKeyListener(kl);
	}

	private void nextThread() {
		threadId = (threadId + 1)%100;
	}
	
	public void setMembers(final LinkedHashMap<Integer, String> members) {
		sumLabel.setText(String.valueOf(members.size()));
		nextThread();
		this.members = members;
		final int id = threadId;
		while (model.getRowCount() != 0) {
			model.removeRow(0);
		}
		//if (members.size() != 0) {
			new Thread() {
				public void run() {
					String name;
					int i = 0;
					while(true) {
//						System.err.print(threadId);
						if(id != threadId)
							return;
						if((name = getNamenext(i, members)) != null) {
							i++;
							if (!addRow(id, name))
								return;
						} else if(kernel.isSearchFinish())
							break;
						else
							try {
								sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
					//NameComponent.this.members = members;
//					System.out.println(NameComponent.this.members = members);
//					System.out.println("!!!!!!!!!!!" + members);
				}
			}.start();
		//}
//		System.err.println('\n');
	}
	
	synchronized private String getNamenext(int i, LinkedHashMap<Integer, String> members) {
		if(i < members.size())
			return members.get(members.keySet().toArray()[i]);
		return null;
	}
	
	synchronized private boolean addRow(int id, String name) {
		if (id == threadId) {
			model.addRow(new Object[] { name });
			sumLabel.setText(String.valueOf(members.size()));
			return true;
		}
		else
			return false;
	}

	public void setSumLabel(String str) {
		sumLabel.setText(str);
	}
	
	public void setSelected(final int indexs, final int indexe) {
		if (indexs != -1 && (members.size() > 0 || !kernel.isSearchFinish()))
//			new Thread() {
//				public void run() {
			while(!selectSet(indexs, indexe)) {}
//				}
//			}.start();
		//table.getSelectionModel().clearSelection();
//		if (indexs != -1)
//			table.getSelectionModel().setSelectionInterval(indexs, indexe);
		ComponentPool.getGroupComponent().highLightGroup(getSelectedMemberIds());
	}
	
	public void setSelectedById(int id) {
		int i = 0;
		for(int cardId : members.keySet()) {
			if(cardId == id)
				break;
			i++;
		}
		setSelected(i, i);
	}
	
	synchronized private boolean selectSet(int indexs, int indexe) {
		if(kernel.isSearchFinish() && members.size() == 0)
			return true;
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
	
	public LinkedHashSet<Integer> getSelectedMemberIds() {
		LinkedHashSet<Integer> temp = new LinkedHashSet<Integer>();
		for(int count : table.getSelectedRows())
			temp.add((Integer) members.keySet().toArray()[count]);
		return temp;
	}
	
	public void addMember(int id, String name) {
		members.put(id, name);
		model.addRow(new Object[] { name });
		sumLabel.setText(String.valueOf(members.size()));
	}

	public void deleteMember() {
		int i = 0;
		for(int row : table.getSelectedRows()) {
			members.remove(members.keySet().toArray()[row-i]);
			model.removeRow(row-i);
			i++;	
		}
		sumLabel.setText(String.valueOf(members.size()));
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
	
	public LinkedHashMap<Integer, String> getMembers() {
		return members;
	}

	// public JButton getButtonSub() {
	// return buttonSub;
	// }

	public JTable getTable() {
		return table;
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		Point p = MouseInfo.getPointerInfo().getLocation();
		Point pp = table.getLocationOnScreen();
		int row = (p.y - pp.y)/table.getRowHeight();
		if (!table.isRowSelected(row) &&
				(e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
			MouseEvent ne = new MouseEvent(e.getComponent(), e.getID(), 
					e.getWhen(), MouseEvent.BUTTON1_MASK, e.getX(), e.getY(), 
					e.getClickCount(), false);
			System.out.println("pressed");
			table.dispatchEvent(ne);
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
			item.removeAll();
			for(String group : kernel.getAllGroups().values())
				item.add(new JMenuItem(group));
			menu.show(table, e.getX(), e.getY());
		}
	}
}
