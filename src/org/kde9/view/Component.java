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
import org.kde9.view.component.menubar.MenubarComponent;
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
	JPanel cube;
	
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

		menuBar = new MenubarComponent();
		setJMenuBar(menuBar);

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
	
	public void setCenterComponent(JPanel panel) {
		this.cube = panel;
		contacts.remove(browerComponent);
		contacts.add("Center", panel);
		panel.updateUI();
		contacts.repaint();
		this.repaint();
	}
	
	public void resetCenterComponent() {
		contacts.remove(cube);
		contacts.add("Center", browerComponent);
		contacts.repaint();
		this.repaint();
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
}
