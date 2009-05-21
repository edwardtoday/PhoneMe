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
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
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
//import org.kde9.view.dialog.SearchPanel;
import org.kde9.view.listener.EditListener;
import org.w3c.dom.events.MouseEvent;

import ch.randelshofer.quaqua.JSheet;
import ch.randelshofer.quaqua.SheetEvent;
import ch.randelshofer.quaqua.SheetListener;

public class ViewerComponent 
extends JPanel 
implements ActionListener, DropTargetListener, SheetListener, 
		TableModelListener, MouseListener, Constants {
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
	private JTextField nameOne;
	private JTextField nameTwo;
	private JLabel pinyinOne;
	private JLabel pinyinTwo;
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
//	private HashMap<Integer, Integer> cantSelect;
	
	private Configuration configuration;
	
	private DropTarget dropTarget;
	
	private boolean setting = false;
//	static DataFlavor urlFlavor, urlListFlavor;
//	static {
//		try {
//			urlFlavor = new DataFlavor(
//					"application/x-java-url; class=java.net.URL");
//			urlListFlavor = new DataFlavor(
//					"text/url-list; class=java.lang.String");
//		} catch (ClassNotFoundException e) {}
//	}
	
	public boolean isSetting() {
		return setting;
	}

	public void setSetting(boolean setting) {
		this.setting = setting;
	}

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
	
	private void setRelationTabel(final JTable table, int i) {
		TableColumnModel columnModel = table.getColumnModel();
		TableColumn column = columnModel.getColumn(i);
		column.setCellEditor(new TableCellEditor() {
			public Component getTableCellEditorComponent(JTable table, Object value,
					boolean isSelected, int row, int vColIndex) {
				if(row > 0 && row != table.getRowCount()-1) {
					if(row-1 >= relationId.size())
						return new JLabel();
					Integer id = relationId.get(row-1);
					if(id != null) {
						id = relationId.get(row-1);
						JLabel label = new JLabel(kernel.getName(id));
						label.addMouseListener(ViewerComponent.this);
						return label;
					}
					else {
						JLabel label = new JLabel("点击此处添加联系人");
						label.addMouseListener(ViewerComponent.this);
						return label;
					}
				}
				else 
					return new JLabel();
//				return null;
			}

			public void addCellEditorListener(CellEditorListener arg0) {
				// TODO Auto-generated method stub
				
			}

			public void cancelCellEditing() {
				// TODO Auto-generated method stub
				
			}

			public Object getCellEditorValue() {
				// TODO Auto-generated method stub
				return null;
			}

			public boolean isCellEditable(EventObject arg0) {
				// TODO Auto-generated method stub
				return true;
			}

			public void removeCellEditorListener(CellEditorListener arg0) {
				// TODO Auto-generated method stub
				
			}

			public boolean shouldSelectCell(EventObject arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean stopCellEditing() {
				// TODO Auto-generated method stub
				table.removeAll();
				table.setEditingRow(0);
				table.setEditingColumn(1);
				return true;
			}
		});
		column.setCellRenderer(new TableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				if(row > 0 && row != table.getRowCount()-1) {
					if(row-1 >= relationId.size())
						return new JLabel();
					Integer id = relationId.get(row-1);
					if(id != null) {
						JLabel label = new JLabel(kernel.getName(id));
						label.addMouseListener(ViewerComponent.this);
						return label;
					}
					else {
						JLabel label = new JLabel("点击此处添加联系人");
						label.addMouseListener(ViewerComponent.this);
						return label;
					}
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
//		cantSelect = new LinkedHashMap<Integer, Integer>();
		buttons = new Vector<ButtonUnit>();
		buttonsLow = new Vector<ButtonUnit>();
		itemTable = new JTable(0, 8) {
			public boolean isCellEditable(int i, int j) {
				if(i == 0 || j == 0 || j == 5) 
					return false;
//				if(cantSelect.get(i) != null && j == 6)
//					return false;
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
		itemTable.getColumnModel().getColumn(0).setMaxWidth(5);
		itemTable.getColumnModel().getColumn(1).setMaxWidth(25);
		itemTable.getColumnModel().getColumn(2).setMaxWidth(25);
		itemTable.getColumnModel().getColumn(3).setMaxWidth(25);
		itemTable.getColumnModel().getColumn(4).setMaxWidth(25);
		itemTable.getColumnModel().getColumn(5).setMaxWidth(30);
		itemTable.getColumnModel().getColumn(6).setMaxWidth(80);
		// JTableHeader header = new JTableHeader();
		// header.setName("group");
		itemTable.setTableHeader(null);
//		itemTable.putClientProperty("Quaqua.Table.style", "striped");
		itemModel = (DefaultTableModel) itemTable.getModel();
		itemModel.addTableModelListener(this);
		ExcelAdapter myAd1 = new ExcelAdapter(itemTable);

		
		buttonEdit = new JToggleButton("Edit");
		buttonEdit.putClientProperty("Quaqua.Button.style", "toolBarRollover");

		relationButtons = new Vector<ButtonUnit>();
		relationTable = new JTable(0, 7) {
			public boolean isCellEditable(int i, int j) {
				if(i == 0 || j == 0 || j == 3) 
					return false;
				return editable;
			}
		};
		setTabel(relationTable, relationButtons, 1, TRUE);
		setTabel(relationTable, relationButtons, 2, FALSE);
		setRelationTabel(relationTable, 6);
		relationTable.setFocusable(false);
		relationTable.setCellEditor(null);
		relationTable.setCellSelectionEnabled(false);
		relationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		relationTable.getColumnModel().getColumn(0).setMaxWidth(5);
		relationTable.getColumnModel().getColumn(1).setMaxWidth(25);
		relationTable.getColumnModel().getColumn(2).setMaxWidth(25);
		relationTable.getColumnModel().getColumn(3).setMaxWidth(50);
		relationTable.getColumnModel().getColumn(4).setMaxWidth(30);
		relationTable.getColumnModel().getColumn(5).setMaxWidth(80);
		relationTable.setTableHeader(null);
		relationModel = (DefaultTableModel) relationTable.getModel();
		relationModel.addTableModelListener(this);
		ExcelAdapter myAd2 = new ExcelAdapter(relationTable);
//		relationTable.putClientProperty("Quaqua.Table.style", "striped");
		
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
		
		dropTarget = new DropTarget(photo, DnDConstants.ACTION_COPY_OR_MOVE, this);
		
		nameOne = new JTextField();
		nameOne.setEnabled(false);
		nameOne.setFont(new Font("HeiTi", 1, 30));
		nameOne.setHorizontalAlignment(JLabel.RIGHT);
		nameTwo = new JTextField();
		nameTwo.setEnabled(false);
		nameTwo.setFont(new Font("HeiTi", 1, 30));
		nameTwo.setHorizontalAlignment(JLabel.LEFT);
		pinyinOne = new JLabel();
		pinyinOne.setFont(new Font("Serif", 1, 15));
		pinyinOne.setHorizontalAlignment(JLabel.RIGHT);
		pinyinTwo = new JLabel();
		pinyinTwo.setFont(new Font("Serif", 1, 15));
		pinyinTwo.setHorizontalAlignment(JLabel.LEFT);
		namePanel = new JPanel();
		nameOne.setDisabledTextColor(Color.BLACK);
		//nameOne.setBackground(namePanel.getBackground());
		nameOne.setBorder(BorderFactory.createEmptyBorder());
		nameTwo.setDisabledTextColor(Color.BLACK);
		//nameTwo.setBackground(namePanel.getBackground());
		nameTwo.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints namec = new GridBagConstraints();
		namec.fill = GridBagConstraints.BOTH;
		namec.weightx = 1.0;
		JPanel fullName = new JPanel(grid);
		grid.setConstraints(nameOne, namec);
		fullName.add(nameOne);
		grid.setConstraints(nameTwo, namec);
		fullName.add(nameTwo);
		JPanel fullPinYin = new JPanel(grid);
		fullPinYin.add(pinyinOne);
		fullPinYin.add(pinyinTwo);
		namePanel.setLayout(new BorderLayout());
		namePanel.add("North", fullPinYin);
		namePanel.add("Center", fullName);
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

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
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
//		itemTable.putClientProperty("Quaqua.Table.style", "plain");
//		relationTable.putClientProperty("Quaqua.Table.style", "plain");
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
			relationModel.insertRow(1, new Object[] {});
		}
		nameOne.setEnabled(true);
		nameOne.setBorder(BorderFactory.createBevelBorder(1));
		nameTwo.setEnabled(true);
		nameTwo.setBorder(BorderFactory.createBevelBorder(1));
		repaint();
	}
	
	public void stopEditModel() {
//		itemTable.putClientProperty("Quaqua.Table.style", "striped");
//		relationTable.putClientProperty("Quaqua.Table.style", "striped");
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
		itemModel.removeRow(itemTable.getRowCount()-1);
		itemModel.addRow(new Object[]{});
		relationModel.removeRow(relationModel.getRowCount()-1);
		relationModel.addRow(new Object[]{});
		System.out.println(relationButtons.size() + " " + relationId.size()
				+ " " + relationModel.getRowCount());
		nameOne.setEnabled(false);
		nameOne.setBorder(BorderFactory.createEmptyBorder());
		nameTwo.setEnabled(false);
		nameTwo.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
		repaint();
	}
	
	public String getNewNameOne() {
		return nameOne.getText();
	}
	
	public String getNewNameTwo() {
		return nameTwo.getText();
	}
	
	public LinkedHashMap<String, Vector<String>> getNewItems() {
		LinkedHashMap<String, Vector<String>> temp = new LinkedHashMap<String, Vector<String>>(); 
		for(int i = 0; i < itemKeys.size(); i++) {
			temp.put(itemKeys.get(i), itemValues.get(i));
		}
		return temp;
	}
	
	public LinkedHashMap<Integer, String> getNewRelation() {
		LinkedHashMap<Integer, String> temp = new LinkedHashMap<Integer, String>();
		for(int i = 0; i < relationId.size(); i++)
			temp.put(relationId.get(i), relationContent.get(i));
		return temp;
	}
	
	public int getCardId() {
		return card.getId();
	}
	
	/**
	 * @return the photo
	 */
	public JButton getPhoto() {
		return photo;
	}
	
	public void setCard(ConstCard card) {
		this.card = card;
	}
	
	public void setName(String nameOne, String nameTwo) {
		this.nameOne.setText(nameOne);
		this.nameTwo.setText(nameTwo);
	}
	
	public void setPinYin(String pinyinOne, String pinyinTwo) {
		this.pinyinOne.setText(pinyinOne);
		this.pinyinTwo.setText(pinyinTwo);
	}

	synchronized public void setImage(final ConstCard card) {
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
		if(card == null || card.getScaleImage() == null || card.getId() != id) {
			return;
		} else {
			photo.setIcon(new ImageIcon(card.getScaleImage()));
		}
	}
	
	public void setRelations(LinkedHashMap<Integer, String> rel) {
		relationId = new Vector<Integer>();
		relationContent = new Vector<String>();
		while(relationModel.getRowCount() != 0)
			relationModel.removeRow(0);
		relationButtons.removeAllElements();
		relationModel.addRow(new Object[] {"","","","","关系",""});
		if(rel != null) {
			//System.out.println(rel);
			relationButtons.add(new ButtonUnit(""));
			for(int id : rel.keySet()) {
				if (kernel.getCard(id) != null) {
					relationId.add(id);
					relationContent.add(rel.get(id));
					if ((Integer) configuration
							.getConfig(NAME_FOMAT, CONFIGINT) == 0) {
						relationModel.addRow(new Object[] {
								"",
								"",
								"",
								"",
								"",
								rel.get(id),
								kernel.getFirstName(id) + ' '
										+ kernel.getLastName(id) });
					} else {
						relationModel.addRow(new Object[] {
								"",
								"",
								"",
								"",
								"",
								rel.get(id),
								kernel.getLastName(id) + ' '
										+ kernel.getFirstName(id) });
					}
					relationButtons.add(new ButtonUnit(3, relationButtons
							.size(), 0, this));
				}
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
//		cantSelect = new LinkedHashMap<Integer, Integer>();
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
//						cantSelect.put(buttons.size(), 4);
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
//		photo.setEnabled(true);
	}
	
	public void clear() {
		setSetting(true);
		while(itemModel.getRowCount() != 0)
			itemModel.removeRow(0);
		while(relationModel.getRowCount() != 0)
			relationModel.removeRow(0);
		buttonEdit.setEnabled(false);
//		photo.setEnabled(false);
		setCard(null);
		setName("", "");
		setPinYin("","");
		setImage(null);
		setSetting(false);
	}
	
	public void changeItems(ButtonUnit b, ActionEvent e) {
		// TODO Auto-generated method stub
		setSetting(true);
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
//					if(cantSelect.get(i) != null) {
//						int a = cantSelect.get(i);
//						cantSelect.remove(i);
//						cantSelect.put(i+1, a);
//					}
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
//					if (cantSelect.get(i) != null) {
//						int a = cantSelect.get(i);
//						cantSelect.remove(i);
//						cantSelect.put(i + 1, a);
//					}
					// System.out.println(cantSelect);///////////////////////////////////////////////////
				}
			} else if (b.getType() == 3) {
				if(relationId.size() == 0) {
					relationContent.add(NULLITEMCONTENT);
					relationId.add(null);
					relationModel.removeRow(1);
					relationModel.insertRow(1, new Object[] {
						"","","","","",NULLITEMCONTENT,"点击这里添加联系人"});
				} else {
					relationContent.add(b.getLocation(), NULLITEMCONTENT);
					relationId.add(b.getLocation(),	null);
					ButtonUnit unit = new ButtonUnit(3, b.getLocation()+1, 0, this);
					relationButtons.add(unit);
					relationModel.insertRow(b.getLocation()+1, new Object[] {
							"","","","","",NULLITEMCONTENT,"点击这里添加联系人"});
					for (int i = b.getLocation()+1; i < relationButtons.size(); i++) {
						relationButtons.get(i).setLocation(i);
					}
				}
			}
		} else if(e.getSource() == b.getButtonSub()) {
			if (b.getType() == 1) {
				if (itemKeys.size() < 2) {
					new CoolInfoBox(this, "\n 表项部分不能为空哦！", Color.YELLOW,
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
					new CoolInfoBox(this, "\n       要删除整个 " + itemKeys.get(loc-1) +  
							" 表项\n  按按前面的删除按钮试试吧！",
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
				int loc = b.getLocation();
				if(relationId.size() != 0) {
					relationId.remove(loc-1);
					relationContent.remove(loc-1);
					relationModel.removeRow(loc);
					if(relationTable.getRowCount() < 3)
						relationModel.addRow(new Object[]{});
				} else {
//					if(relationTable.getRowCount() < 1)
//						relationModel.addRow(new Object[]{});
					new CoolInfoBox(this, "\n 已经没有关系了哦！", Color.YELLOW, 200, 100);
				}
				if(relationId.size() != 0) {
					relationButtons.get(loc).getButtonSub().setVisible(false);
					relationButtons.remove(loc);
				}
				relationTable.setEditingRow(0);
				relationTable.setEditingColumn(1);
				for (int i = b.getLocation(); i < relationButtons.size(); i++) {
					relationButtons.get(i).setLocation(i);
				}
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
		setSetting(false);
	}

	public void actionPerformed(ActionEvent arg0) {
		if(card == null)
			return;
		BufferedImage image = card.getImage();
		if(image != null && !editable)
			new PhotoBox(photo, image, 20);
		else if(editable) {
			// TODO
			JFileChooser chooser = new JFileChooser();
			FileFilter filter = new FileFilter() {
				@Override
				public boolean accept(File f) {
					String name = f.getName();
					if(name.endsWith(".jpg") || name.endsWith(".bmp") ||
							name.endsWith(".png") || name.endsWith(".jpeg") ||
							name.endsWith(".JPG") || name.endsWith(".PNG") ||
							name.endsWith(".gif"))
						return true;
					return false;
				}
				@Override
				public String getDescription() {
					return "图片文件";
				}
			};
			chooser.setFileFilter(filter);
			chooser.setPreferredSize(new Dimension(700, 400));
			JSheet.showOpenSheet(chooser, this, this);
		}
	}

	/**
	 * @return the buttonEdit
	 */
	public JToggleButton getButtonEdit() {
		return buttonEdit;
	}
	
	public void dragEnter(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void dragOver(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void drop(DropTargetDropEvent d) {
		System.out.println(d.getSource());
		d.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
	}

	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void optionSelected(SheetEvent e) {
		// TODO Auto-generated method stub
		File file = e.getFileChooser().getSelectedFile();
		if(file != null) {
			kernel.setCardImage(card.getId(), file);
			setImage(card);
		}
	}

	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		if(!isSetting()) {
			if(e.getSource() == itemModel) {
				System.out.println("item changed!" + e.getFirstRow());
				int changed = e.getFirstRow();
				if(changed < buttons.size() && changed > 0) {
					System.out.println("item change catched!"+changed);
					int itemLoc = buttons.get(changed).getLocation();
					int itemIndex = buttons.get(changed).getIndex();
					int lastIndex = 0;
					System.out.println(itemLoc + " " + itemIndex);
					for(; changed > 0; changed--) {
						if(buttons.get(changed).getLocation() != itemLoc) {
							lastIndex = buttons.get(changed).getIndex();
							break;
						}
					}
					System.out.println(changed);
					if(changed == 0) {
						if(itemLoc == itemIndex)
							itemKeys.set(itemLoc-1, 
									(String)itemTable.getValueAt(itemIndex, 6));
						itemValues.get(itemLoc-1).set(itemIndex-1,
								(String)itemTable.getValueAt(itemIndex, 7));
					} else {
						try {
							if(itemIndex == changed+1)
								itemKeys.set(itemLoc-1, 
										(String)itemTable.getValueAt(itemIndex, 6));
							itemValues.get(itemLoc-1).set(itemIndex-1-lastIndex,
									(String)itemTable.getValueAt(itemIndex, 7));
						} catch (ArrayIndexOutOfBoundsException ee) {}
					}
				}
			} else {
				System.out.println("relation changed!" + e.getFirstRow());
				int changed = e.getFirstRow();
				if(changed < relationButtons.size() && changed > 0) {
					try {
						System.out.println("relation change catched!"+changed);
						int itemLoc = relationButtons.get(changed).getLocation();
						relationContent.set(itemLoc-1, 
									(String)relationTable.getValueAt(itemLoc, 5));
					} catch (ArrayIndexOutOfBoundsException ee) {}
				}
			}
		}
			
	}

	public void mouseClicked(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void mouseEntered(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("relation selected" + relationTable.getSelectedRow());
		//new SearchPanel(ComponentPool.getComponent(), Color.black, 400, 400);
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
