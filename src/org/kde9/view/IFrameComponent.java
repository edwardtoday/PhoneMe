package org.kde9.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;

import org.kde9.model.Main;
import org.kde9.util.Constants;

public class IFrameComponent extends JFrame 
implements Constants{
	JPanel contacts;
	JPanel renlifang;
	JMenuBar menuBar;
	GroupComponent groupComponent;
	MenubarComponent menubarComponent;
	NameComponent nameComponent;
	ViewerComponent viewerComponent;
	BrowerComponent browerComponent;
	
	IFrameComponent() {
		super("what's this?");
		groupComponent = Factory.createGroup();
		menubarComponent = Factory.createMenuBar();
		nameComponent = Factory.creatName();
		viewerComponent = Factory.createViewer();
		browerComponent = new BrowerComponent(new Main()); 
		
		buildMenus();
		
		contacts = new JPanel();
		renlifang = new JPanel();
		
		BorderLayout bl1 = new BorderLayout();
		GridLayout gl2 = new GridLayout();
		GridLayout gl3 = new GridLayout();
//		bl1.setHgap(1);
//		gl2.setHgap(2);
//		gl3.setHgap(2);
		
		contacts.setLayout(bl1);
		JPanel innerPanel = new JPanel();
		JPanel inner_innerPanel = new JPanel();		
		innerPanel.setLayout(gl2);		
		inner_innerPanel.setLayout(gl3);
		
		//inner_innerPanel.add(groupComponent);
		//inner_innerPanel.add(nameComponent);
		inner_innerPanel.add(browerComponent);
		
		innerPanel.add(inner_innerPanel);
		innerPanel.add(viewerComponent);
		
		contacts.add(menubarComponent,BorderLayout.NORTH);
		contacts.add(innerPanel,BorderLayout.CENTER);
		add(contacts);
		
		//setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
	
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);

		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		// 得到Shell窗口的宽度和高度
		int shellHeight = getBounds().height;
		int shellWidth = getBounds().width;
		// 如果窗口大小超过屏幕大小，让窗口与屏幕等大
		if (shellHeight > screenHeight)
			shellHeight = screenHeight;
		if (shellWidth > screenWidth)
			shellWidth = screenWidth;
		// 让窗口在屏幕中间显示
		setLocation(((screenWidth - shellWidth) / 2),((screenHeight - shellHeight) / 2) );
	}
	
	protected void buildMenus() {
		menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		JMenu file = buildFileMenu();
		JMenu edit = buildEditMenu();
		JMenu card = buildCardMenu();
		JMenu window = buildWindowMenu();
		JMenu sync = buildSyncMenu();
		JMenu help = buildHelpMenu();

		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(card);
		menuBar.add(window);
		menuBar.add(sync);
		menuBar.add(help);
		setJMenuBar(menuBar);
	}
	
	protected JMenu buildFileMenu() {
		JMenu file = new JMenu("File");
		JMenuItem newCard = new JMenuItem("New Card");
		JMenuItem newGroup = new JMenuItem("New Group");
		JMenuItem newGroupfromSelection = new JMenuItem("New Group from Selection");
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
		
		edit.add(undo);
		edit.add(redo);
		edit.addSeparator();
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
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
		help.add(birthdayReminder);
		return help;
	}
}
