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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
import org.kde9.view.ComponentPool;
import org.kde9.view.dialog.CoolInfoBox;
import org.kde9.view.dialog.PhotoBox;
import org.kde9.view.listener.EditListener;

public class ViewerComponent 
extends JPanel 
implements ActionListener, Constants {
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
	private Vector<Integer> relationId;
	private Vector<String> relationContent;
	
	private Vector<ButtonUnit> buttons;
	private Vector<ButtonUnit> buttonsLow;
	private Vector<ButtonUnit> relationButtons;
	private HashMap<Integer, Integer> cantSelect;
	
	private Configuration configuration;
	
	private static boolean TRUE = true;
	private static boolean FALSE = false;

	private void setTabel(JTable table, final Vector<ButtonUnit> b,
			int i, final boolean addOrSub) {
		TableColumnModel columnModel = table.getColumnModel();
		TableColumn column = columnModel.getColumn(i);
		column.setCellEditor(new DataTableCellEditor(b, addOrSub));
		column.setCellRenderer(new TableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				if(editable && row != table.getRowCount()-1) {
					if(addOrSub)
						return b.get(row).getButtonAdd();
					else
						return b.get(row).getButtonSub();
				}
				else 
					return new JLabel();
			}
		});
	}
	
	ViewerComponent(Kernel kernel) {
		ComponentPool.setViewerComponent(this);
		
		configuration = ConfigFactory.creatConfig();
		this.kernel = kernel;
		cantSelect = new LinkedHashMap<Integer, Integer>();
		buttons = new Vector<ButtonUnit>();
		buttonsLow = new Vector<ButtonUnit>();
		itemTable = new JTable(0, 8) {
			public boolean isCellEditable(int i, int j) {
				if(i == 0 || j == 0 || j == 5) 
					return false;
				if(cantSelect.get(i) != null && j == 6)
					return false;
				return editable;
			}
		};
		setTabel(itemTable, buttons, 1, TRUE);
		setTabel(itemTable, buttons, 2, FALSE);
		setTabel(itemTable, buttonsLow, 3, TRUE);
		setTabel(itemTable, buttonsLow, 4, FALSE);
		itemTable.setFocusable(false);
		itemTable.setCellEditor(null);
		itemTable.setCellSelectionEnabled(false);
		itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		itemTable.getColumnModel().getColumn(0).setMaxWidth(15);
		itemTable.getColumnModel().getColumn(1).setMaxWidth(45);
		itemTable.getColumnModel().getColumn(2).setMaxWidth(45);
		itemTable.getColumnModel().getColumn(3).setMaxWidth(45);
		itemTable.getColumnModel().getColumn(4).setMaxWidth(45);
		itemTable.getColumnModel().getColumn(5).setMaxWidth(30);
		itemTable.getColumnModel().getColumn(5).setMaxWidth(80);
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

		relationButtons = new Vector<ButtonUnit>();
		relationTable = new JTable(0, 6) {
			public boolean isCellEditable(int i, int j) {
				if(i == 0 || j == 0 || j == 3) 
					return false;
				return editable;
			}
		};
		setTabel(relationTable, relationButtons, 1, TRUE);
		setTabel(relationTable, relationButtons, 2, FALSE);
		relationTable.setFocusable(false);
		relationTable.setCellEditor(null);
		relationTable.setCellSelectionEnabled(false);
		relationTable.getColumnModel().getColumn(0).setMaxWidth(15);
		relationTable.getColumnModel().getColumn(1).setMaxWidth(45);
		relationTable.getColumnModel().getColumn(2).setMaxWidth(45);
		relationTable.getColumnModel().getColumn(3).setMaxWidth(30);
		relationTable.getColumnModel().getColumn(4).setMaxWidth(85);
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
		GridBagLayout photoLayout = new GridBagLayout();
		GridBagConstraints photoc = new GridBagConstraints();
		photoPanel.setLayout(photoLayout);
		photo = new JButton();
		photo.putClientProperty("Quaqua.Button.style", "colorWell");
		photo.setPreferredSize(new Dimension(140, 140));
		photo.addActionListener(this);
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
		photoc.fill = GridBagConstraints.BOTH;
		photoc.weightx = 0;
		photoc.gridwidth = 1;
		photoLayout.setConstraints(photo, photoc);
		photoPanel.add(photo);
		photoc.weightx = 1;
		photoLayout.setConstraints(namePanel, photoc);
		photoPanel.add(namePanel);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0;
		c.gridwidth = GridBagConstraints.REMAINDER; // end row
		upLayout.setConstraints(photoPanel, c);
		upPanel.add(photoPanel);
		c.fill = GridBagConstraints.BOTH;
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
		pane.setPreferredSize(new Dimension(100, 150));
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
		itemTable.setCellSelectionEnabled(true);
		relationTable.setFocusable(true);
		relationTable.requestFocus();
		relationTable.setCellSelectionEnabled(true);
		setEditable(true);
		buttonEdit.setSelected(true);
		if(relationId.size() == 0) {
			ButtonUnit unit = new ButtonUnit(3, 1, 0, this);
			relationButtons.add(unit);
			relationModel.insertRow(1, new Object[] {
					"","","",""});
		}
		repaint();
	}
	
	public void stopEditModel() {
		itemTable.setFocusable(false);
		itemTable.setCellSelectionEnabled(false);
		itemTable.removeEditor();
		relationTable.setFocusable(false);
		relationTable.setCellSelectionEnabled(false);
		relationTable.removeEditor();
		setEditable(false);
		buttonEdit.setSelected(false);
outer:
		for(int i = 0; ; i++) {
			if(i >= relationId.size())
				break;
			while(relationId.get(i) == null) {
				relationId.remove(i);
				relationContent.remove(i);
				relationButtons.remove(i+1);
				relationModel.removeRow(i+1);
				if(i >= relationId.size())
					break outer;
			}
			relationButtons.get(i+1).setLocation(i+1);
		}
		if(relationId.size() == 0) {
			relationButtons.removeAllElements();
			relationButtons.add(new ButtonUnit(""));
			if(relationTable.getRowCount() > 2)
				relationModel.removeRow(1);
		}
		System.out.println(relationButtons.size() + " " + relationId.size()
				+ " " + relationModel.getRowCount());
		repaint();
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

	public void setImage(final ConstCard card) {
		photo.setIcon(new ImageIcon(IMGPATH + NULLIMAGE));
		if (card != null) {
			final int id = card.getId();
			new Thread() {
				public void run() {
					while (!card.isImageRafdy())
						try {
							if (card.getId() != id)
								return;
							sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					showImage(id);
				}
			}.start();
		}
	}
	
	synchronized private void showImage(int id) {
		if(card.getId() != id)
			return;
		if(card.getScaleImage() != null) {
			photo.setIcon(new ImageIcon(card.getScaleImage()));
		}
	}
	
	public void setRelations(LinkedHashMap<Integer, String> rel) {
		relationId = new Vector<Integer>();
		relationContent = new Vector<String>();
		while(relationModel.getRowCount() != 0)
			relationModel.removeRow(0);
		relationButtons.removeAllElements();
		relationModel.addRow(new Object[] {"","","","关系",""});
		if(rel != null) {
			//System.out.println(rel);
			relationButtons.add(new ButtonUnit(""));
			for(int id : rel.keySet()) {
				relationId.add(id);
				relationContent.add(rel.get(id));
				if((Integer)configuration.getConfig(NAME_FOMAT, CONFIGINT) == 0) {
					relationModel.addRow(new Object[] {"","","","",
						rel.get(id), kernel.getFirstName(id) + ' ' + kernel.getLastName(id)});
				}
				else {
					relationModel.addRow(new Object[] {"","","","",
							rel.get(id), kernel.getLastName(id) + ' ' + kernel.getFirstName(id)});
				}
				relationButtons.add(new ButtonUnit(3, relationButtons.size(), 0, this));
			}
		}
		relationModel.addRow(new Object[]{});
	}
	
	public void setItems(LinkedHashMap<String, Vector<String>> items) {
		//this.items = items;
		while (itemModel.getRowCount() != 0)
			itemModel.removeRow(0);
		buttons.removeAllElements();
		buttonsLow.removeAllElements();
		itemModel.addRow(new Object[] {"","","","","","信息",""});
		buttons.add(new ButtonUnit(""));
		buttonsLow.add(new ButtonUnit(""));
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
						itemModel.addRow(new Object[] {
								"", "", "", "", "", "", name, value });
						buttons.add(new ButtonUnit(1, itemKeys.size(),
								buttons.size(), this));
						ButtonUnit unit = new ButtonUnit(2, itemKeys.size(),
								buttonsLow.size(), this);
						buttonsLow.add(unit);
						flag = false;
					} else {
						itemModel.addRow(new Object[] { 
								"" ,"" ,"", "", "", "", "", value });
						cantSelect.put(buttons.size(), 4);
						buttons.add(new ButtonUnit(9, itemKeys.size(),
								buttons.size(), this));
						buttonsLow.add(new ButtonUnit(2, itemKeys.size(),
								buttonsLow.size(), this));
					}
			}
		}
		itemModel.addRow(new Object[]{});
	}

	public void ready() {
		buttonEdit.setEnabled(true);
		photo.setEnabled(true);
	}
	
	public void clear() {
		while(itemModel.getRowCount() != 0)
			itemModel.removeRow(0);
		while(relationModel.getRowCount() != 0)
			relationModel.removeRow(0);
		buttonEdit.setEnabled(false);
		photo.setEnabled(false);
		setName("");
		setPinYin("");
		setImage(null);
	}
	
	public void changeItems(ButtonUnit b, ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getActionCommand());
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
				ButtonUnit unitx = new ButtonUnit(
						2, b.getLocation() + 1, b.getIndex()
						+ itemValues.get(b.getLocation() - 1).size(), this);
				buttonsLow.add(b.getIndex()
						+ itemValues.get(b.getLocation() - 1).size(), unitx);
				itemModel.insertRow(b.getIndex()
						+ itemValues.get(b.getLocation() - 1).size(), new Object[] {
					"", "", "", "", "", "", NULLITEMCONTENT, NULLITEMCONTENT });
				
				for (int i = b.getIndex() + itemValues.get(b.getLocation() - 1).size() + 1;
						i < buttons.size(); i++) {
					buttons.get(i).setLocation(buttons.get(i).getLocation() + 1);
					buttons.get(i).setIndex(i);
					buttonsLow.get(i).setIndex(i);
					buttonsLow.get(i).setLocation(buttonsLow.get(i).getLocation() + 1);
					if(cantSelect.get(i) != null) {
						int a = cantSelect.get(i);
						cantSelect.remove(i);
						cantSelect.put(i+1, a);
					}
//					System.out.println(cantSelect);///////////////////////////////////////////////////
				}
//				itemTable.setEditingColumn(b.getIndex()+1);
//				itemTable.setEditingRow(4);
			} else if (b.getType() == 2) {
				int size = itemValues.get(b.getLocation()-1).size();
				int index = b.getIndex();
				for(; size > 0; size--) {
					index++;
					if(index >= buttons.size() || buttons.get(index).getType() == 1)
						break;
				}
				itemValues.get(b.getLocation()-1).add(size, NULLITEMCONTENT);
				ButtonUnit unit = new ButtonUnit(
						9, b.getLocation(), b.getIndex()+1, this);
				buttons.add(b.getIndex()+1, unit);
				ButtonUnit unitx = new ButtonUnit(
						2, b.getLocation(), b.getIndex()+1, this);
				buttonsLow.add(b.getIndex()+1, unitx);
				itemModel.insertRow(b.getIndex()+1, new Object[] {
						"", "", "", "", "", "", "", NULLITEMCONTENT });
				for (int i = b.getIndex()+1; i < buttons.size(); i++) {
					buttons.get(i).setIndex(i);
					buttonsLow.get(i).setIndex(i);
					if (cantSelect.get(i) != null) {
						int a = cantSelect.get(i);
						cantSelect.remove(i);
						cantSelect.put(i + 1, a);
					}
					// System.out.println(cantSelect);///////////////////////////////////////////////////
				}
			} else if (b.getType() == 3) {
				if(relationId.size() == 0) {
					relationContent.add(NULLITEMCONTENT);
					relationId.add(null);
					relationModel.removeRow(1);
					relationModel.insertRow(1, new Object[] {
						"","","","",NULLITEMCONTENT,"点这里选择联系人"});
				} else {
					relationContent.add(b.getLocation(), NULLITEMCONTENT);
					relationId.add(b.getLocation(),	null);
					ButtonUnit unit = new ButtonUnit(3, b.getLocation()+1, 0, this);
					relationButtons.add(unit);
					relationModel.insertRow(b.getLocation()+1, new Object[] {
							"","","","",NULLITEMCONTENT,"点这里选择联系人"});
					for (int i = b.getLocation()+1; i < relationButtons.size(); i++) {
						relationButtons.get(i).setLocation(i);
					}
				}
			}
		} else if(e.getSource() == b.getButtonSub()) {
			if (b.getType() == 1) {
				if (itemKeys.size() < 2) {
					new CoolInfoBox(this, "\n信息部分不能为空哦！", Color.YELLOW,
							200, 100);
					return;
				} else {
					int index = b.getIndex();
					int loc = b.getLocation();
					int sum = itemValues.get(loc-1).size();
					for(int i = 0; i < sum; i++) {
						//if(index == buttons.size()-sum) {
						buttons.get(index).getButtonSub().setVisible(false);
						buttonsLow.get(index).getButtonSub().setVisible(false);
						System.out.println(i+"_"+index+"_"+buttons.size());////////////////////
						//}
						itemTable.setEditingRow(0);
						itemTable.setEditingColumn(1);
						itemModel.removeRow(index);
						buttons.remove(index);
						buttonsLow.remove(index);
					}
					itemKeys.remove(loc-1);
					itemValues.remove(loc-1);
					for(int i = index; i < buttons.size(); i++) {
						buttons.get(i).setLocation(buttons.get(i).getLocation()-1);
						buttons.get(i).setIndex(i);
						buttonsLow.get(i).setLocation(buttonsLow.get(i).getLocation()-1);
						buttonsLow.get(i).setIndex(i);
					}
				}
			} else if (b.getType() == 2) {
				int index = b.getIndex();
				int loc = b.getLocation();
				System.out.println(loc + "((((((((((((");
				int sum = itemValues.get(loc-1).size();
				if(sum == 1)
					new CoolInfoBox(this, "\n     要删除整个 " + itemKeys.get(loc-1) +  
							" 表项，\n  按按前面的删除按钮试试吧！",
							Color.YELLOW, 200, 100);
				else {
					for (; sum > 0; sum--) {
						if (index <= buttons.size() - 1
								&& buttons.get(index).getLocation() == loc)
							index++;
						else
							break;
					}
//					System.out.println(index + "sdfdsfsfds" + sum + "sdds" + buttons.size());
					itemValues.get(loc - 1).remove(sum);
					itemModel.removeRow(b.getIndex());
					if(sum == 0) {
						itemModel.removeRow(b.getIndex());
						itemModel.insertRow(b.getIndex(), new Object[] {
							"","","","","","",itemKeys.get(loc-1),
							itemValues.get(loc-1).get(0)});
						buttons.get(b.getIndex()+1).getButtonSub().setVisible(false);
						buttons.remove(b.getIndex()+1);
					} else {
						buttons.get(b.getIndex()).getButtonSub().setVisible(false);
						buttons.remove(b.getIndex());
					}
					buttonsLow.get(b.getIndex()).getButtonSub().setVisible(
							false);
					buttonsLow.remove(b.getIndex());
					itemTable.setEditingRow(0);
					itemTable.setEditingColumn(1);
					for (int i = b.getIndex(); i < buttons.size(); i++) {
						buttonsLow.get(i).setIndex(i);
						buttons.get(i).setIndex(i);
					}
				}
			} else if (b.getType() == 3) {
				
			}
			System.out.println(buttons.size());
		}
		System.out.println(itemKeys);//////////////////////////////////////////////////////////////
		System.out.println(itemValues);////////////////////////////////////////////////////////////
		System.out.println(relationId);///////////////////////////////////////////
		System.out.println(relationContent);////////////////////////////////////////
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).update();
			buttonsLow.get(i).update();
		}
		for (int i = 0; i < relationButtons.size(); i++)
			relationButtons.get(i).update();
		repaint();
		System.out.println(itemTable.getRowCount());
	}

	public void actionPerformed(ActionEvent arg0) {
		BufferedImage image = card.getImage();
		if(image != null && !editable)
			new PhotoBox(photo, image);
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
		if(type != 2 && type < 4)
			buttonAdd.setBackground(Color.GREEN);
		else if(type < 4)
			buttonAdd.setBackground(Color.blue);
		else
			buttonAdd.setBackground(Color.WHITE);
		if(type == 1 || type == 2 || type == 3) {
			buttonAdd.setText("＋");
			buttonAdd.addActionListener(this);
		}
		this.buttonSub = new JButton();
		if(type == 1 || type == 2 || type == 3) {
			buttonSub.setText("－");
			buttonSub.addActionListener(this);
		}
		buttonSub.setOpaque(true);
		if(type != 2 && type < 4)
			buttonSub.setBackground(Color.RED);
		else if(type < 4)
			buttonSub.setBackground(Color.cyan);
		else
			buttonSub.setBackground(Color.WHITE);
		buttonSub.putClientProperty("Quaqua.Button.style", "toolBarTab");
		this.type = type;
		this.location = location;
		this.index = index;
		this.component = a;
	}
	
	public void update() {
		if(buttonAdd != null)
			buttonAdd.setText(String.valueOf(type));
		if(buttonSub != null)
			buttonSub.setText(String.valueOf(location) + ' ' + String.valueOf(index));
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
		component.changeItems(this, e);
	}
}

class DataTableCellEditor 
extends AbstractCellEditor 
implements TableCellEditor {
	private Vector<ButtonUnit> unit;
	private boolean addOrSub; 

	public DataTableCellEditor(Vector<ButtonUnit> unit, boolean addOrSub) {
		this.unit = unit;
		this.addOrSub = addOrSub;
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int rowIndex, int vColIndex) {
		if (rowIndex == table.getRowCount()-1)
			return new JLabel();
		if (addOrSub)
			return unit.get(rowIndex).getButtonAdd();
		else
			return unit.get(rowIndex).getButtonSub();
	}

	public boolean stopCellEditing() {
		super.stopCellEditing();
		return true;
	}

	public boolean isCellEditable(EventObject evt) {
		return true;
	}

	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return this;
	}
}
