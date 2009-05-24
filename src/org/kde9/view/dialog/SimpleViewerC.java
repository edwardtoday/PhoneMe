package org.kde9.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.EventObject;
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
import org.kde9.view.dialog.SelectPanel;
import org.kde9.view.listener.EditListener;

import ch.randelshofer.quaqua.JSheet;
import ch.randelshofer.quaqua.SheetEvent;
import ch.randelshofer.quaqua.SheetListener;

public class SimpleViewerC 
extends JPanel 
implements Constants {
	private JLabel photo;
	private JLabel name = new JLabel("姓名：");
	private JLabel nameText;
	private JLabel item = new JLabel("信息：");
	private JLabel firstItem;
	private JLabel firstValue;
	private JLabel secondItem;
	private JLabel secondValue;
	private ConstCard card;
	
	public ConstCard getCard() {
		return card;
	}

	public void setCard(ConstCard card) {
		this.card = card;
	}

	private Configuration configuration;
	
	SimpleViewerC() {	
		configuration = ConfigFactory.creatConfig();
		
		photo = new JLabel();
		//photo.setIcon(new ImageIcon("./img/nullImag.gif"));
		photo.setPreferredSize(new Dimension(120,120));
		name.setHorizontalAlignment(JLabel.LEFT);
		nameText = new JLabel("");
		nameText.setHorizontalAlignment(JLabel.LEFT);
		firstItem = new JLabel("");
		firstItem.setHorizontalAlignment(JLabel.LEFT);
		firstValue = new JLabel("");
		firstValue.setHorizontalAlignment(JLabel.LEFT);
		secondItem = new JLabel("");
		secondItem.setHorizontalAlignment(JLabel.LEFT);
		secondValue = new JLabel("");
		secondValue.setHorizontalAlignment(JLabel.LEFT);
		
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(grid);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.gridheight = 4;
		c.weightx = 0;
		c.weighty = 1.0;
		grid.setConstraints(photo, c);
		add(photo);
		
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		grid.setConstraints(name, c);
		add(name);
		
		c.weightx = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		grid.setConstraints(nameText, c);
		add(nameText);
		
		c.weightx = 0;
		c.gridwidth = 1;
		grid.setConstraints(item, c);
		add(item);
		
		c.weightx = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		JLabel empty1 = new JLabel();
		grid.setConstraints(empty1, c);
		add(empty1);
		
		c.weightx = 0;
		c.gridwidth = 1;
		grid.setConstraints(firstItem, c);
		add(firstItem);
		
		c.weightx = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		grid.setConstraints(firstValue, c);
		add(firstValue);
		
		c.weightx = 0;
		c.gridwidth = 1;
		grid.setConstraints(secondItem, c);
		add(secondItem);
		
		c.weightx = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		grid.setConstraints(secondValue, c);
		add(secondValue);
	}
	
	public void setName(String name) {
		this.nameText.setText(name);
	}

	public void setItem(LinkedHashMap<String, Vector<String>> items) {
		firstItem.setText("");
		firstValue.setText("");
		secondItem.setText("");
		secondValue.setText("");
		int i = 0;
		for(String key : items.keySet()) {
			if(i == 0) {
				firstItem.setText(key);
				if(items.get(key) != null && items.get(key).size() > 0)
					firstValue.setText(items.get(key).get(0));
			} else if(i == 1) {
				secondItem.setText(key);
				if(items.get(key) != null && items.get(key).size() > 0)
					secondValue.setText(items.get(key).get(0));
			} else
				break;
			i++;
		}
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
//		System.out.println(")))))))))))))))))))))))))");
		if(card == null || card.getScaleImage() == null || card.getId() != id) {
			return;
		} else {
//			System.out.println("{{{{{{{{{{{{{{{{{{{");
			photo.setIcon(new ImageIcon(card.getScaleImage()));
		}
	}
}