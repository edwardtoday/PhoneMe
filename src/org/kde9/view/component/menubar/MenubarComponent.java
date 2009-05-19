package org.kde9.view.component.menubar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;

import org.kde9.view.Component;
import org.kde9.view.ComponentPool;
import org.kde9.view.dialog.AddGroupInfoBox;
import org.kde9.view.dialog.AddNameInfoBox;
import org.kde9.view.dialog.CoolInfoBox;

public class MenubarComponent 
extends JMenuBar 
implements ActionListener {
	private JMenu file;
	private JMenu edit;
	private JMenu card;
	private JMenu window;
	private JMenu sync;
	private JMenu help;
	
	private JMenuItem newCard;
	private JMenuItem newGroup;
	private JMenuItem newGroupfromSelection;
	private JMenuItem Import;
	private JMenuItem Export;
	private JMenuItem quit;
	
	private JMenuItem delete;
	private JMenuItem removefromgroup;
	private JMenuItem selectall;
	private JMenuItem renamegroup;
	private JMenuItem editsmartgroup;
	private JMenuItem editcard;
	
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
		window = buildWindowMenu();
		sync = buildSyncMenu();
		help = buildHelpMenu();

		add(file);
		add(edit);
		add(card);
		//add(window);
		//add(sync);
		add(help);
	}

	protected JMenu buildFileMenu() {
		file = new JMenu("File");
		newCard = new JMenuItem("New Card");		
		newGroup = new JMenuItem("New Group");		
		newGroupfromSelection = new JMenuItem(
				"New Group from Selection");
		Import = new JMenuItem("Import");
		Export = new JMenuItem("Export");
		quit = new JMenuItem("Quit");
		
		file.add(newCard);
		file.add(newGroup);
		file.add(newGroupfromSelection);
		file.addSeparator();
		file.add(Import);
		file.add(Export);
		file.addSeparator();
		file.add(quit);
		
		newCard.addActionListener(this);
		newGroup.addActionListener(this);
		newGroupfromSelection.addActionListener(this);
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
		delete = new JMenuItem("Delete");
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
		edit.add(delete);
		edit.add(removefromgroup);
		edit.add(selectall);
		edit.addSeparator();
		edit.add(renamegroup);
		edit.add(editsmartgroup);
		edit.add(editcard);
		delete.addActionListener(this);
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
		}else if(e.getSource() == Import) {
			System.out.println("Import");
		}else if(e.getSource() == Export) {
			System.out.println("Export");
		}else if(e.getSource() == quit) {
			System.exit(0);
			System.out.println("quit");
		}else if(e.getSource() == delete) {
			System.out.println("delete");
		}else if(e.getSource() == removefromgroup) {
			System.out.println("removefromgroup");
		}else if(e.getSource() == selectall) {
			System.out.println("selectall");
		}else if(e.getSource() == renamegroup) {
			System.out.println("renamegroup");
		}else if(e.getSource() == editsmartgroup) {
			System.out.println("editsmartgroup");
		}else if(e.getSource() == editcard) {
			System.out.println("editcard");
		}else if(e.getSource() == gotonextcard) {
			System.out.println("gotonextcard");
		}else if(e.getSource() == gotoprewcard) {
			System.out.println("gotoprewcard");
		}else if(e.getSource() == choosecustomimage) {
			System.out.println("choosecustomimage");
		}else if(e.getSource() == clearcustomimage) {
			System.out.println("clearcustomimage");
		}else if(e.getSource() == about) {
			new CoolInfoBox(ComponentPool.getComponent(),"hahaha!!",Color.blue,200,100);
		}
	}
}
