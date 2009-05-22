package org.kde9.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
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
import org.kde9.view.ComponentPool;
import org.kde9.view.listener.AddNameListener;

public class SimpleNameC 
extends JPanel {
	private JTable table;
	private JScrollPane pane;
	private DefaultTableModel model;
	private TitledBorder border;
	
	private static int threadId = 0;
	
	private final Kernel kernel;

	private LinkedHashMap<Integer, String> members;
	
	public SimpleNameC() {
		
		members = new LinkedHashMap<Integer, String>();
		table = new JTable(0, 1) {
			public boolean isCellEditable(int i, int j) {
				return false;
			}
		};
		table.setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setTableHeader(null);
		model = (DefaultTableModel) table.getModel();

		border = new TitledBorder("name");
		border.setTitleJustification(TitledBorder.CENTER);

		setLayout(new BorderLayout());
		pane = new JScrollPane(table);
		pane.setBorder(BorderFactory.createEmptyBorder());
		add("Center", pane);

		setBorder(border);
		
		kernel = ComponentPool.getComponent().getKernel();
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
					System.out.println(SimpleNameC.this.members = members);
					System.out.println("!!!!!!!!!!!" + members);
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
			return true;
		}
		else
			return false;
	}

	public void setSelected(final int indexs, final int indexe) {
		if (indexs != -1 && (members.size() > 0 || !kernel.isSearchFinish()))
			while(!selectSet(indexs, indexe)) {}
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
	}

	public void deleteMember() {
		int i = 0;
		for(int row : table.getSelectedRows()) {
			members.remove(members.keySet().toArray()[row-i]);
			model.removeRow(row-i);
			i++;	
		}
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public ListSelectionModel getSelectionModel() {
		return table.getSelectionModel();
	}
	
	public LinkedHashMap<Integer, String> getMembers() {
		return members;
	}

	public JTable getTable() {
		return table;
	}
}
