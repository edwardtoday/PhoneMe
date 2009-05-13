package org.kde9.view.component.brower;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.kde9.control.Kernel;
import org.kde9.model.card.ConstCard;
import org.kde9.util.ConfigFactory;
import org.kde9.util.Configuration;
import org.kde9.util.Constants;
import org.kde9.view.dialog.CoolInfoBox;
import org.kde9.view.listener.EditListener;

public class ViewerComponent 
extends JPanel 
implements Constants {
	private JTable itemTable;
	private JTable relationTable;
	private JPanel upPanel;
	private JScrollPane pane;
	private JToggleButton buttonEdit;
	private DefaultTableModel itemModel;
	private DefaultTableModel relationModel;
	private TitledBorder border;
	private JPanel photoPanel;
	private JPanel namePanel;
	private JLabel name;
	private JLabel pinyin;
	private JButton photo;
	private Kernel kernel;
	private ConstCard card;
	private boolean editable;

	private Vector<String> itemKeys;
	private Vector<Vector<String>> itemValues;
	private LinkedHashMap<Integer, String> relation;
	
	private Vector<ButtonUnit> buttons;
	private HashMap<Integer, Integer> cantSelect;
	
	private Configuration configuration;

	ViewerComponent(Kernel kernel) {
		configuration = ConfigFactory.creatConfig();
		this.kernel = kernel;
		cantSelect = new LinkedHashMap<Integer, Integer>();
		buttons = new Vector<ButtonUnit>();
		itemTable = new JTable(0, 6) {
			public boolean isCellEditable(int i, int j) {
				if(i == 0 || j == 0 || j == 3) 
					return false;
				if(cantSelect.get(i) != null && j == 4)
					return false;
				return editable;
			}
		};
		TableColumnModel columnModel = itemTable.getColumnModel();
		TableColumn column = columnModel.getColumn(1);
		column.setCellEditor(new DataTableCellEditor(buttons));
		column.setCellRenderer(new TableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				if(editable) {
					return buttons.get(row).getButtonAdd();
				}
				else 
					return new JLabel();
			}
		});
		column = columnModel.getColumn(2);
		column.setCellEditor(new DataTableCellEditor(buttons));
		column.setCellRenderer(new TableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				if(editable) {
					return buttons.get(row).getButtonSub();
				}
				else 
					return new JLabel();
			}
		});
		itemTable.setFocusable(false);
		itemTable.setCellEditor(null);
		itemTable.setCellSelectionEnabled(false);
		itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		itemTable.getColumnModel().getColumn(0).setMaxWidth(15);
		itemTable.getColumnModel().getColumn(1).setMaxWidth(25);
		itemTable.getColumnModel().getColumn(2).setMaxWidth(25);
		itemTable.getColumnModel().getColumn(3).setMaxWidth(30);
		itemTable.getColumnModel().getColumn(4).setMaxWidth(85);
		// JTableHeader header = new JTableHeader();
		// header.setName("group");
		itemTable.setTableHeader(null);
		// table.putClientProperty("Quaqua.Table.style", "striped");
		itemModel = (DefaultTableModel) itemTable.getModel();
		buttonEdit = new JToggleButton("Edit");
		buttonEdit.putClientProperty("Quaqua.Button.style", "toolBarRollover");
		// buttonSub = new JButton("-");
		// buttonSub.putClientProperty("Quaqua.Button.style",
		// "toolBarRollover");

		relationTable = new JTable(0, 8);
		relationTable.getColumnModel().getColumn(0).setMaxWidth(15);
		relationTable.getColumnModel().getColumn(1).setMaxWidth(25);
		relationTable.getColumnModel().getColumn(2).setMaxWidth(25);
		relationTable.getColumnModel().getColumn(3).setMaxWidth(30);
		relationTable.getColumnModel().getColumn(4).setMaxWidth(85);
		relationTable.getColumnModel().getColumn(5).setMaxWidth(40);
		relationTable.setTableHeader(null);
		relationModel = (DefaultTableModel) relationTable.getModel();
		
		border = new TitledBorder("view");
		border.setTitleJustification(TitledBorder.CENTER);

		setLayout(new BorderLayout());

		upPanel = new JPanel();
		GridBagLayout upLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		upPanel.setLayout(upLayout);
		photoPanel = new JPanel();
		photoPanel.setBorder(BorderFactory.createEtchedBorder());
		photoPanel.setLayout(new GridLayout(0, 3));
		photo = new JButton("X");
		photo.putClientProperty("Quaqua.Button.style", "colorWell");
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
		c.weighty = 0.3;
		c.gridwidth = GridBagConstraints.REMAINDER; // end row
		upLayout.setConstraints(photoPanel, c);
		upPanel.add(photoPanel);
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		JPanel tablePanel = new JPanel();
		
		GridBagLayout tableLayout = new GridBagLayout();
		GridBagConstraints cc = new GridBagConstraints();
		tablePanel.setLayout(tableLayout);
		cc.fill = GridBagConstraints.BOTH;
		cc.weightx = 1.0;
		cc.weighty = 0.1;
		cc.gridwidth = GridBagConstraints.REMAINDER;
		tableLayout.setConstraints(itemTable, cc);
		cc.gridheight = GridBagConstraints.REMAINDER;
		cc.weighty = 1.0;
		tableLayout.setConstraints(relationTable, cc);
		tablePanel.add(itemTable);
		tablePanel.add(relationTable);
		
		pane = new JScrollPane(tablePanel);
		pane.setBorder(BorderFactory.createEmptyBorder());
		pane.setPreferredSize(new Dimension(100, 180));
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

	private boolean isEditable() {
		return editable;
	}

	private void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	public JTable getTable() {
		return itemTable;
	}

	public void addEditListener(EditListener editListener) {
		editListener.setComponent(this);
		buttonEdit.addActionListener(editListener);
	}
	
	public void startEditModel() {
		itemTable.setFocusable(true);
		itemTable.requestFocus();
//		table.setColumnSelectionAllowed(false);
//		table.setRowSelectionAllowed(false);
		itemTable.setCellSelectionEnabled(true);
		setEditable(true);
		updateUI();
	}
	
	public void stopEditModel() {
		itemTable.setFocusable(false);
		setEditable(false);
		itemTable.setCellSelectionEnabled(false);
		itemTable.removeEditor();
		updateUI();
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

	public void setRelations(LinkedHashMap<Integer, String> rel) {
		relation = new LinkedHashMap<Integer, String>();
		while(relationModel.getRowCount() != 0)
			relationModel.removeRow(0);
		if(rel != null && rel.size() != 0) {
			//System.out.println(rel);
			relation = rel;
			relationModel.addRow(new Object[] {"","","","关系",""});
			for(int id : rel.keySet()) {
				if((Integer)configuration.getConfig(NAME_FOMAT, CONFIGINT) == 0)
					relationModel.addRow(new Object[] {"","","","",
						rel.get(id), kernel.getFirstName(id), kernel.getLastName(id)});
				else
					relationModel.addRow(new Object[] {"","","","",
							rel.get(id), kernel.getLastName(id), kernel.getFirstName(id)});
			}
		}
	}
	
	public void setItems(LinkedHashMap<String, Vector<String>> items) {
		//this.items = items;
		while (itemModel.getRowCount() != 0)
			itemModel.removeRow(0);
		buttons.removeAllElements();
		itemModel.addRow(new Object[] {"","","","信息",""});
		buttons.add(new ButtonUnit(""));
		itemKeys = new Vector<String>();
		itemValues = new Vector<Vector<String>>();
		cantSelect = new LinkedHashMap<Integer, Integer>();
		if (items != null) {
			for (String name : items.keySet()) {
				//model.addRow(new Object[] { "", name});
				boolean flag = true;
				itemKeys.add(name);
				itemValues.add(items.get(name));
				for(String value : items.get(name))
					if(flag) {
						itemModel.addRow(new Object[] { "", "", "", "", name, value });
						buttons.add(new ButtonUnit(1, itemKeys.size(),
								buttons.size(), this));
						flag = false;
					} else {
						itemModel.addRow(new Object[] { "", "", "", "", "", value });
						cantSelect.put(buttons.size(), 4);
						buttons.add(new ButtonUnit(2, itemKeys.size(),
								buttons.size(), this));
					}
			}
		}
	}

	public void actionPerformed(ButtonUnit b, ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == b.getButtonAdd()) {
			if (b.getType() == 1) {
				itemKeys.add(b.getLocation(), NULLITEMCONTENT);
				Vector<String> s = new Vector<String>();
				s.add(NULLITEMCONTENT);
				itemValues.add(b.getLocation(), s);
				ButtonUnit unit = new ButtonUnit(
						1, b.getLocation() + 1, b.getIndex()
						+ itemValues.get(b.getLocation() - 1).size(), this);
				buttons.add(b.getIndex()
						+ itemValues.get(b.getLocation() - 1).size(), unit);
				itemModel.insertRow(b.getIndex()
						+ itemValues.get(b.getLocation() - 1).size(), new Object[] {
						"", "", "", "", NULLITEMCONTENT, NULLITEMCONTENT });
				
				for (int i = b.getIndex() + itemValues.get(b.getLocation() - 1).size() + 1;
						i < buttons.size(); i++) {
					buttons.get(i).setLocation(buttons.get(i).getLocation() + 1);
					buttons.get(i).setIndex(i);
					if(cantSelect.get(i) != null) {
						int a = cantSelect.get(i);
						cantSelect.remove(i);
						cantSelect.put(i+1, a);
					}
//					System.out.println(cantSelect);///////////////////////////////////////////////////
				}
	//			updateUI();
			} else if (b.getType() == 2) {
				int size = itemValues.get(b.getLocation()-1).size();
				int index = b.getIndex();
				for(; size > 0; size--) {
					if(index >= buttons.size() || buttons.get(index).getType() == 1)
						break;
					index++;
				}
				itemValues.get(b.getLocation()-1).add(size+1, NULLITEMCONTENT);
				ButtonUnit unit = new ButtonUnit(
						2, b.getLocation(), b.getIndex()+1, this);
				buttons.add(b.getIndex()+1, unit);
				itemModel.insertRow(b.getIndex()+1, new Object[] {
						"", "", "", "", "", NULLITEMCONTENT });
				for (int i = b.getIndex()+1; i < buttons.size(); i++) {
					buttons.get(i).setIndex(i);
					if(cantSelect.get(i) != null) {
						int a = cantSelect.get(i);
						cantSelect.remove(i);
						cantSelect.put(i+1, a);
			}
//			System.out.println(cantSelect);///////////////////////////////////////////////////
		}
			}
		} else if(e.getSource() == b.getButtonSub()) {
			new CoolInfoBox(itemTable, "名片内容不能为空哦！", Color.YELLOW);
		}
		System.out.println(itemKeys);//////////////////////////////////////////////////////////////
		System.out.println(itemValues);////////////////////////////////////////////////////////////
		for (int i = 0; i < buttons.size(); i++)
			buttons.get(i).update();
	}

//	public void addItem(String name, String content) {
//		items.put(name, content);
//		model.addRow(new Object[] { "", name, content });
//	}
}

class ButtonUnit
implements ActionListener {
	private JButton buttonAdd;
	private JButton buttonSub;
	private int type;
	private int location;
	private int index;
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	private ViewerComponent component;
	
	public ButtonUnit(int type, int location, int index, ViewerComponent a) {
		this.buttonAdd = new JButton();
		buttonAdd.putClientProperty("Quaqua.Button.style", "toolBarTab");
		buttonAdd.setOpaque(true);
		buttonAdd.setText("＋");
		buttonAdd.setBackground(Color.GREEN);
		buttonAdd.addActionListener(this);
		this.buttonSub = new JButton("－");
		buttonSub.putClientProperty("Quaqua.Button.style", "toolBarTab");
		buttonSub.addActionListener(this);
		buttonSub.setOpaque(true);
		buttonSub.setBackground(Color.RED);
		this.type = type;
		this.location = location;
		this.index = index;
		this.component = a;
	}
	
	public void update() {
		if(buttonAdd != null) {
			buttonAdd.setText(String.valueOf(type));
			buttonSub.setText(String.valueOf(location) + ' ' + String.valueOf(index));
		}
	}
	
	public ButtonUnit(String s) {}

	public JButton getButtonAdd() {
		return buttonAdd;
	}

	public void setButtonAdd(JButton buttonAdd) {
		this.buttonAdd = buttonAdd;
	}

	public JButton getButtonSub() {
		return buttonSub;
	}

	public void setButtonSub(JButton buttonSub) {
		this.buttonSub = buttonSub;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		component.actionPerformed(this, e);
	}
}

class DataTableCellEditor 
extends AbstractCellEditor 
implements TableCellEditor {
	private Vector<ButtonUnit> unit;

	public DataTableCellEditor(Vector<ButtonUnit> unit) {
		this.unit = unit;
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int rowIndex, int vColIndex) {
		if (vColIndex == 1)
			return unit.get(rowIndex).getButtonAdd();
		else
			return unit.get(rowIndex).getButtonSub();
	}

	public boolean stopCellEditing() {
		//super.stopCellEditing();
		return true;
	}

	public boolean isCellEditable(EventObject evt) {
		return true;
	}

	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}
}
