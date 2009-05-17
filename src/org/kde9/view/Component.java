package org.kde9.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;

import org.kde9.control.Kernel;
import org.kde9.util.ConfigFactory;
import org.kde9.util.Configuration;
import org.kde9.util.Constants;
import org.kde9.view.component.brower.BrowerComponent;
import org.kde9.view.component.toolbar.ToolbarComponent;
import org.kde9.view.listener.AddGroupListener;
import org.kde9.view.listener.AddNameListener;
import org.kde9.view.listener.EditListener;

public class Component 
extends JFrame 
implements Constants {
	JPanel contacts;
	JPanel renlifang;
	JMenuBar menuBar;
	Kernel kernel;
	ToolbarComponent toolbarComponent;
	BrowerComponent browerComponent;
	Configuration config;
	
	private EditListener editListener;
	private AddGroupListener addGroupListener;
	private AddNameListener addNameListener;

	public Component(Kernel kernel) {
		super("what's this?");
		
		this.kernel = kernel;
		
		dispatchEvent(new FocusEvent(this, FocusEvent.FOCUS_GAINED, true));
		
		ComponentPool.setComponent(this);
		
		toolbarComponent = new ToolbarComponent();
		browerComponent = new BrowerComponent(kernel);
		config = ConfigFactory.creatConfig();

		buildMenus();

		contacts = new JPanel();
		renlifang = new JPanel();

		BorderLayout bl1 = new BorderLayout();

		contacts.setLayout(bl1);

		contacts.add(browerComponent, BorderLayout.CENTER);
		contacts.add(toolbarComponent, BorderLayout.NORTH);
		// contacts.add(new JLabel(), BorderLayout.EAST);
		add(contacts);

		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);

		// dispatchEvent(new FocusEvent(this,FocusEvent.FOCUS_GAINED, true));
		// System.out.println(isFocusOwner() + "[[[[[[[[[[[[");
		// contacts.requestFocusInWindow();
		// System.out.println(contacts.isFocusOwner() + "[[[[[[[[[[[[");
		// browerComponent.requestFocusInWindow();
		// System.out.println(browerComponent.isFocusOwner() + "[[[[[[[[[[[[");

		setResizable(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((Integer)config.getConfig(MAIN_FRAME_WIDTH, CONFIGINT), 
				(Integer)config.getConfig(MAIN_FRAME_HEIGHT, CONFIGINT));

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
		setLocation(((screenWidth - shellWidth) / 2),
				((screenHeight - shellHeight) / 2));
		
		editListener = new EditListener();
		addGroupListener = new AddGroupListener();
		addNameListener = new AddNameListener();
		addEditListener(editListener);
		addGroupListener(addGroupListener);
		addNameListener(addNameListener);
	}
	
	public void addEditListener(EditListener editListener) {
		browerComponent.addEditListener(editListener);
	}
	
	public void addGroupListener(AddGroupListener addGroupListener) {
		browerComponent.addGroupListener(addGroupListener);
	}
	
	public void addNameListener(AddNameListener addNameListener) {
		browerComponent.addNameListener(addNameListener);
	}
	
	public Kernel getKernel() {
		return kernel;
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
