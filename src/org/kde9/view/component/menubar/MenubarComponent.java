package org.kde9.view.component.menubar;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;

public class MenubarComponent 
extends JMenuBar {
	private JMenu file;
	private JMenu edit;
	private JMenu card;
	private JMenu window;
	private JMenu sync;
	private JMenu help;
	
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
		JMenu file = new JMenu("File");
		JMenuItem newCard = new JMenuItem("New Card");
		JMenuItem newGroup = new JMenuItem("New Group");
		JMenuItem newGroupfromSelection = new JMenuItem(
				"New Group from Selection");
		JMenuItem Import = new JMenuItem("Import");
		JMenuItem Export = new JMenuItem("Export");
		JMenuItem quit = new JMenuItem("Quit");

		file.add(newCard);
		file.add(newGroup);
		file.add(newGroupfromSelection);
		file.addSeparator();
		file.add(Import);
		file.add(Export);
		file.addSeparator();
		file.add(quit);
		return file;
	}

	protected JMenu buildEditMenu() {
		JMenu edit = new JMenu("Edit");
		JMenuItem undo = new JMenuItem("Undo");
		JMenuItem redo = new JMenuItem("Redo");
		JMenuItem cut = new JMenuItem("Cut");
		JMenuItem copy = new JMenuItem("Copy");
		JMenuItem paste = new JMenuItem("Paste");
		JMenuItem delete = new JMenuItem("Delete");
		JMenuItem removefromgroup = new JMenuItem("Remove from Group");
		JMenuItem selectall = new JMenuItem("Select All");
		JMenuItem renamegroup = new JMenuItem("Rename Group");
		JMenuItem editsmartgroup = new JMenuItem("Edit Smart Group");
		JMenuItem editcard = new JMenuItem("Edit Card");

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
		return edit;
	}

	protected JMenu buildCardMenu() {
		JMenu card = new JMenu("Card");
		JMenuItem gotonextcard = new JMenuItem("Goto Next Card");
		JMenuItem gotoprewcard = new JMenuItem("Goto Prew Card");
		JMenuItem choosecustomimage = new JMenuItem("Choose Custom Image");
		JMenuItem clearcustomimage = new JMenuItem("Clear Custom Image");

		card.add(gotonextcard);
		card.add(gotoprewcard);
		card.addSeparator();
		card.add(choosecustomimage);
		card.add(clearcustomimage);
		return card;
	}

	protected JMenu buildWindowMenu() {
		JMenu window = new JMenu("Window");
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
		JMenu sync = new JMenu("Sync");
		JMenuItem syncwithgoogle = new JMenuItem("Sync with Google");

		sync.add(syncwithgoogle);
		return sync;
	}

	protected JMenu buildHelpMenu() {
		JMenu help = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		JMenuItem birthdayReminder = new JMenuItem("Birthday Reminder");

		help.add(about);
		//help.add(birthdayReminder);
		return help;
	}
}
