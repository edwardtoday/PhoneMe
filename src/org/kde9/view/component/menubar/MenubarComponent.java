package org.kde9.view.component.menubar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuListener;

import org.kde9.control.ImportAndExport.MyImportAndExport;
import org.kde9.util.Constants;
import org.kde9.view.Component;
import org.kde9.view.ComponentPool;
import org.kde9.view.dialog.AddGroupInfoBox;
import org.kde9.view.dialog.AddNameInfoBox;
import org.kde9.view.dialog.CardSumInfoBox;
import org.kde9.view.dialog.CoolInfoBox;
import org.kde9.view.dialog.DeleteInfoBox;
import org.kde9.view.dialog.RenameGroupBox;

import ch.randelshofer.quaqua.colorchooser.ColorChooserMainPanel;

public class MenubarComponent 
extends JMenuBar 
implements ActionListener {
	private JMenu file;
	private JMenu edit;
	private JMenu card;
	private JMenu statistics;
	private JMenu window;
	private JMenu sync;
	private JMenu help;
	
	private JMenuItem newCard;
	private JMenuItem newGroup;
	private JMenuItem newGroupfromSelection;
	private JMenuItem newGroupfromSearchResult;
	private JMenuItem Import;
	private JMenuItem Export;
	private JMenuItem quit;
	
	private JMenuItem deletegroup;
	private JMenuItem deletecard;
	private JMenuItem removefromgroup;
	private JMenuItem selectall;
	private JMenuItem renamegroup;
	private JMenuItem editsmartgroup;
	private JMenuItem editcard;
	
	private JMenuItem cardsum;
	private JMenuItem birthdaystatistic;
	
	private JMenuItem gotonextcard;
	private JMenuItem gotoprewcard;
	private JMenuItem choosecustomimage;
	private JMenuItem clearcustomimage;
	 
	private JMenuItem about;
	
	public MenubarComponent() {
		setOpaque(true);
		file = buildFileMenu();
		edit = buildEditMenu();
		card = buildCardMenu();
		statistics = buildStatisticsMenu();
		window = buildWindowMenu();
		sync = buildSyncMenu();
		help = buildHelpMenu();

		add(file);
		add(edit);
		add(card);
		add(statistics);
		//add(window);
		//add(sync);
		add(help);
		
		//setMenubarState();
	}
	
	public void setMenubarState() {
		if(ComponentPool.getViewerComponent().isEditable()) {
			newCard.setEnabled(false);
			newGroup.setEnabled(false);
			newGroupfromSelection.setEnabled(false);
			newGroupfromSearchResult.setEnabled(false);
			Import.setEnabled(false);
			Export.setEnabled(false);
			
			deletegroup.setEnabled(false);
			deletecard.setEnabled(false);
			removefromgroup.setEnabled(false);
			selectall.setEnabled(false);
			renamegroup.setEnabled(false);
			editsmartgroup.setEnabled(false);
			editcard.setEnabled(false);
			
			gotonextcard.setEnabled(false);
			gotoprewcard.setEnabled(false);
			choosecustomimage.setEnabled(false);
			clearcustomimage.setEnabled(false);
		}else {
			newCard.setEnabled(true);
			newGroup.setEnabled(true);
			newGroupfromSelection.setEnabled(true);
			newGroupfromSearchResult.setEnabled(true);
			Import.setEnabled(true);
			Export.setEnabled(true);
			
			deletegroup.setEnabled(true);
			deletecard.setEnabled(true);
			removefromgroup.setEnabled(true);
			selectall.setEnabled(true);
			renamegroup.setEnabled(true);
			editsmartgroup.setEnabled(true);
			editcard.setEnabled(true);
			
			gotonextcard.setEnabled(true);
			gotoprewcard.setEnabled(true);
			choosecustomimage.setEnabled(true);
			clearcustomimage.setEnabled(true);
		}
	}

	protected JMenu buildFileMenu() {
		file = new JMenu("File");
		newCard = new JMenuItem("New Card");		
		newGroup = new JMenuItem("New Group");		
		newGroupfromSelection = new JMenuItem(
				"New Group from Selection");
		newGroupfromSearchResult = new JMenuItem(
				"New Group from Search Results");
		Import = new JMenuItem("Import");
		Export = new JMenuItem("Export");
		quit = new JMenuItem("Quit");
		
		file.add(newCard);
		file.add(newGroup);
		file.add(newGroupfromSelection);
		file.add(newGroupfromSearchResult);
		file.addSeparator();
		file.add(Import);
		file.add(Export);
		file.addSeparator();
		file.add(quit);
		
		newCard.addActionListener(this);
		newGroup.addActionListener(this);
		newGroupfromSelection.addActionListener(this);
		newGroupfromSearchResult.addActionListener(this);
		Import.addActionListener(this);
		Export.addActionListener(this);
		quit.addActionListener(this);
		return file;
	}

	protected JMenu buildEditMenu() {
		edit = new JMenu("Edit");
		JMenuItem undo = new JMenuItem("Undo");
		JMenuItem redo = new JMenuItem("Redo");
		JMenuItem cut = new JMenuItem("Cut");
		JMenuItem copy = new JMenuItem("Copy");
		JMenuItem paste = new JMenuItem("Paste");
		deletegroup = new JMenuItem("Delete Group");
		deletecard = new JMenuItem("Delete Card");
		removefromgroup = new JMenuItem("Remove from Group");
		selectall = new JMenuItem("Select All");
		renamegroup = new JMenuItem("Rename Group");
		editsmartgroup = new JMenuItem("Edit Smart Group");
		editcard = new JMenuItem("Edit Card");

		//edit.add(undo);
		//edit.add(redo);
		//edit.addSeparator();
//		edit.add(cut);
//		edit.add(copy);
//		edit.add(paste);
		edit.add(deletegroup);
		edit.add(deletecard);
		edit.add(removefromgroup);
		edit.add(selectall);
		edit.addSeparator();
		edit.add(renamegroup);
		edit.add(editsmartgroup);
		edit.add(editcard);
		deletegroup.addActionListener(this);
		deletecard.addActionListener(this);
		removefromgroup.addActionListener(this);
		selectall.addActionListener(this);
		renamegroup.addActionListener(this);
		editsmartgroup.addActionListener(this);
		editcard.addActionListener(this);
		return edit;
	}

	protected JMenu buildCardMenu() {
		card = new JMenu("Card");
		gotonextcard = new JMenuItem("Goto Next Card");
		gotoprewcard = new JMenuItem("Goto Prew Card");
		choosecustomimage = new JMenuItem("Choose Custom Image");
		clearcustomimage = new JMenuItem("Clear Custom Image");

		card.add(gotonextcard);
		card.add(gotoprewcard);
		card.addSeparator();
		card.add(choosecustomimage);
		card.add(clearcustomimage);
		gotonextcard.addActionListener(this);
		gotoprewcard.addActionListener(this);
		choosecustomimage.addActionListener(this);
		clearcustomimage.addActionListener(this);
		return card;
	}
	
	protected JMenu buildStatisticsMenu() {
		statistics = new JMenu("Statistics");
		cardsum = new JMenuItem("Card Sum");
		birthdaystatistic = new JMenuItem("Birthday Statistics");
		
		statistics.add(cardsum);
		statistics.add(birthdaystatistic);
		cardsum.addActionListener(this);
		birthdaystatistic.addActionListener(this);
		return statistics;
	}

	protected JMenu buildWindowMenu() {
		window = new JMenu("Window");
		ButtonGroup group = new ButtonGroup();
		JRadioButton contacts = new JRadioButton("Contacts");
		JRadioButton renlifang = new JRadioButton("Ren Li Fang");
		group.add(contacts);
		group.add(renlifang);

		window.add(contacts);
		window.add(renlifang);
		return window;
	}

	protected JMenu buildSyncMenu() {
		sync = new JMenu("Sync");
		JMenuItem syncwithgoogle = new JMenuItem("Sync with Google");

		sync.add(syncwithgoogle);
		return sync;
	}

	protected JMenu buildHelpMenu() {
		help = new JMenu("Help");
		about = new JMenuItem("About");
		JMenuItem birthdayReminder = new JMenuItem("Birthday Reminder");

		help.add(about);
		about.addActionListener(this);
		//help.add(birthdayReminder);
		return help;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == newCard) {
			ComponentPool.getComponent().setEnabled(false);
			new AddNameInfoBox(ComponentPool.getNameComponent(),new Color(102,255,153),200, 100);
		}else if(e.getSource() == newGroup) {
			ComponentPool.getComponent().setEnabled(false);
			new AddGroupInfoBox(ComponentPool.getGroupComponent(), new Color(102,255,153),200, 100);	
		}else if(e.getSource() == newGroupfromSelection) {
			System.out.println("newGroupfromSelection");
		}else if(e.getSource() == newGroupfromSearchResult){
			ComponentPool.getComponent().setEnabled(false);
			new AddGroupInfoBox(ComponentPool.getGroupComponent(), 
					new Color(102,255,153),200, 100);
			System.out.println("newGroupfromSearchResult");
		}else if(e.getSource() == Import) {
			MyImportAndExport mie = new MyImportAndExport();
			int groupSelected = ComponentPool.getGroupComponent().getSelectedGroupId();
			mie.ImportFile(groupSelected);
			System.out.println("Import");
		}else if(e.getSource() == Export) {
			MyImportAndExport mie = new MyImportAndExport();
			mie.ExportFile();
			System.out.println("Export");
		}else if(e.getSource() == quit) {
			System.exit(0);
			System.out.println("quit");
		}else if(e.getSource() == deletegroup) {
			new DeleteInfoBox(Constants.DELETEGROUP, 
					ComponentPool.getComponent(), "     您确定要删除该组吗？", 
							Color.YELLOW, 200, 80);	
			System.out.println("deletegroup");
		}else if(e.getSource() == deletecard) {
			new DeleteInfoBox(Constants.DELETECARD, 
					ComponentPool.getComponent(), "     您确定要删除该名片吗？", 
					Color.YELLOW, 200, 80);	
			System.out.println("deletename");
		}else if(e.getSource() == removefromgroup) {
			new DeleteInfoBox(Constants.DELETEFROMGROUP, 
					ComponentPool.getComponent(), "您确定从该组中删除该名片吗？", 
					Color.YELLOW, 200, 80);	
			System.out.println("removefromgroup");
		}else if(e.getSource() == selectall) {
			ComponentPool.getNameComponent().setSelected(0, 
					ComponentPool.getNameComponent().getTable().getRowCount()-1);
			System.out.println("selectall");
		}else if(e.getSource() == renamegroup) {
			new RenameGroupBox(ComponentPool.getGroupComponent(),Color.YELLOW,200,100);
			System.out.println("renamegroup");
		}else if(e.getSource() == editsmartgroup) {
			System.out.println("editsmartgroup");
		}else if(e.getSource() == editcard) {
			ComponentPool.getViewerComponent().getButtonEdit().setSelected(true);
			ComponentPool.getViewerComponent().startEditModel();
			ComponentPool.getBrowerComponent().setEnabled(false);
			ComponentPool.getGroupComponent().getTable().setCellSelectionEnabled(false);
			ComponentPool.getNameComponent().getTable().setCellSelectionEnabled(false);
			ComponentPool.getGroupComponent().getTable().setFocusable(false);
			ComponentPool.getNameComponent().getTable().setFocusable(false);
			System.out.println("editcard");
		}else if(e.getSource() == cardsum) {
			new CardSumInfoBox(ComponentPool.getComponent(), 
					"       名片数统计如下表：", Color.YELLOW, 200, 200);
			System.out.println("cardsum");
		}else if(e.getSource() == birthdaystatistic) {
			System.out.println("birthdaystatistic");
		}else if(e.getSource() == gotonextcard) {
			System.out.println("gotonextcard");
		}else if(e.getSource() == gotoprewcard) {
			System.out.println("gotoprewcard");
		}else if(e.getSource() == choosecustomimage) {
//			if(ComponentPool.getViewerComponent().isEditable()) {
				JButton b = ComponentPool.getViewerComponent().getPhoto();
				ComponentPool.getViewerComponent().setEditable(true);
				b.getActionListeners()[0].actionPerformed(new ActionEvent(b, ActionEvent.ACTION_PERFORMED, ""));
				ComponentPool.getViewerComponent().setEditable(false);
//			}
			System.out.println("choosecustomimage");
		}else if(e.getSource() == clearcustomimage) {
			System.out.println("clearcustomimage");
		}else if(e.getSource() == about) {
			new CoolInfoBox(ComponentPool.getComponent(),"hahaha!!",Color.blue,200,100);
		}
	}
}
