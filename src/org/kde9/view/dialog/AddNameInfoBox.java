package org.kde9.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.kde9.control.Kernel;
import org.kde9.model.card.ConstCard;
import org.kde9.util.ConfigFactory;
import org.kde9.util.Configuration;
import org.kde9.util.Constants;
import org.kde9.view.ComponentPool;

import ch.randelshofer.quaqua.JSheet;

import com.sun.jna.examples.WindowUtils;

public class AddNameInfoBox 
implements ActionListener, KeyListener, Constants {
	private static JDialog frame;
	private JComponent father;
//	private Container mainContainer;
	private JSheet sheet;
	private JPanel container;
	private Color color;
	JTextField firstName;
	JTextField lastName;
	private JButton confirm;
	private JButton cancel;
	private Configuration config;
	private LinkedHashMap<String, Vector<String>> items;
	int w;
	int h;

	public AddNameInfoBox(JComponent father,Color color, int w, int h) {
		this.frame = new JDialog(ComponentPool.getComponent(), true);
		this.firstName = new JTextField();
		this.lastName = new JTextField();
		this.confirm = new JButton("[    OK   ]");
		this.cancel = new JButton("[Cancel]");
		config = ConfigFactory.creatConfig();
		this.father = father;
		this.color = color;
		this.w = w;
		this.h = h;
		items = new LinkedHashMap<String, Vector<String>>();
		initItems();
		launch();
	}
	
	private void initItems() {
		Vector<String> temp = new Vector<String>();
		temp.add(Constants.NULLITEMCONTENT);
		items.put("电话号码", temp);
		items.put("电子邮件", temp);
		items.put("通信地址", temp);
		items.put("工作单位", temp);
		items.put("IM", temp);
		items.put("生日", temp);
		items.put("URL", temp);
	}

	private void launch() {
		createUI();
		launchUI();
	}

	private void launchUI() {
		sheet.setUndecorated(true);
		sheet.setVisible(true);
		//sheet.getRootPane().putClientProperty("Quaqua.RootPane.isVertical", Boolean.TRUE);
	}

	private void createUI() {
		System.setProperty("sun.java2d.noddraw", "true");
		sheet = new JSheet(frame);
		sheet.setSize(w, h);
		//ComponentPool.getComponent().setAlwaysOnTop(true);
		//sheet.setAlwaysOnTop(true);

		container = new JPanel(new BorderLayout());
		sheet.setContentPane(container);
		JPanel name = new JPanel();
		JPanel firstNamePane = new JPanel(new BorderLayout());
		JPanel lastNamePane = new JPanel(new BorderLayout());
		JLabel firstNameLabel = new JLabel("First Name:");
		JLabel lastNameLabel = new JLabel("Last Name:");
		firstNamePane.add("North",firstNameLabel);
		firstNamePane.add("Center",firstName);
		lastNamePane.add("North",lastNameLabel);
		lastNamePane.add("Center",lastName);
		name.add(firstNamePane);
		name.add(lastNamePane);
		container.add("Center",name);
		confirm.addActionListener(this);
		cancel.addActionListener(this);
		confirm.putClientProperty("Quaqua.Button.style", "toolBarRollover");
		cancel.putClientProperty("Quaqua.Button.style", "toolBarRollover");
		JPanel button = new JPanel();
		button.add(confirm);
		button.add(cancel);
		container.add("South",button);
		container.setOpaque(true);
		container.setBackground(color);
		
		firstName.addKeyListener(this);
		lastName.addKeyListener(this);

		RoundRectangle2D.Float mask = new RoundRectangle2D.Float(1, 1, 
				sheet.getWidth()-2, sheet.getHeight()-2, 20, 20);
		WindowUtils.setWindowMask(sheet, mask);
		if(father != null)
			centerWindow(father, sheet);
		else
			centerWindow(sheet, sheet);
		changeAlphaDown(800, 0.8f, ComponentPool.getComponent(), false);
	}
	
	private void changeAlphaUp(final int a, final float s, final Window window) {
		Thread thread = new Thread() {
			public void run() {
				for (float i = s; i < 1; i += 0.1) {
					try {
						sleep((long) (a/((1-s)*100)));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					WindowUtils.setWindowAlpha(window, i);
				}
				WindowUtils.setWindowAlpha(window, 1);
			}
		};
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}
	
	private void changeAlphaDown(final int a, final float s, final Window window,
			final boolean close) {
		Thread thread = new Thread() {
			public void run() {
				for (float i = 1; i > s; i -= 0.01) {
					try {
						sleep((long) (a/((1-s)*100)));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					WindowUtils.setWindowAlpha(window, i);
				}
				if(close) {
					window.dispose();
					ComponentPool.getComponent().setAlwaysOnTop(true);
					ComponentPool.getComponent().setAlwaysOnTop(false);
				}
			}
		};
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}

	private static void centerWindow(Container window, Container sheet) {
		int xx = (int) window.getLocationOnScreen().getX();
		int yy = (int) window.getLocationOnScreen().getY();
		int w = window.getWidth();
		int h = window.getHeight();
//		System.out.println(w + " " + h + " " + sheet.getWidth() + " " + sheet.getHeight());
		int x = xx + w/2;
		int y = yy + (h-sheet.getHeight())/2;
		frame.setLocation(x, y);
	}
	
	public JButton getConfirm() {
		return confirm;
	}
	
	public JButton getCancel() {
		return cancel;
	}
	
//	public Container getMainContainer() {
//		return mainContainer;
//	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		getMainContainer().setEnabled(true);
		if(e.getSource() == this.getCancel()) {
			System.out.println("Name added cancelled!!");
			ComponentPool.getComponent().setEnabled(true);
			changeAlphaUp(300, 0.8f, ComponentPool.getComponent());
			changeAlphaDown(300, 0, sheet, true);
		}else if(e.getSource() == this.getConfirm()) {
			String firstName = this.firstName.getText();
			String lastName = this.lastName.getText();
			if(firstName.length() == 0 || lastName.length() == 0) {
				new CoolInfoBox(ComponentPool.getNameComponent(),
						"请输入完整姓名！",Color.YELLOW,200,35,-70);
			}else {
				
				Kernel kernel = ComponentPool.getComponent().getKernel();
				ConstCard card;
				int groupIdSelected = 
					ComponentPool.getGroupComponent().getSelectedGroupId();
				if(groupIdSelected != -1)
					card = kernel.addCard(groupIdSelected,
												firstName, lastName, items, null);
				else {
					
					card = kernel.addCard(0,
							firstName, lastName, items, null);
				}
				String name;
				if((Integer)config.getConfig(NAME_FOMAT, CONFIGINT) == 0)
					name = firstName + ' ' + lastName;
				else
					name = lastName + ' ' + firstName;
				ComponentPool.getNameComponent().addMember(card.getId(), name);
				int index = ComponentPool.getNameComponent().getTable().getRowCount();
				ComponentPool.getNameComponent().setSelected(index-1, index-1);
				ComponentPool.getComponent().setEnabled(true);
				changeAlphaUp(300, 0.8f, ComponentPool.getComponent());
				changeAlphaDown(300, 0, sheet, true);
				JToggleButton button = ComponentPool.getViewerComponent().getButtonEdit();
				button.setSelected(true);
				button.getActionListeners()[0].actionPerformed(
						new ActionEvent(button, ActionEvent.ACTION_PERFORMED, "Edit"));
			}
			System.out.println("Name added confirmed!!");		
		}
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			actionPerformed(new ActionEvent(
					confirm, ActionEvent.ACTION_PERFORMED, confirm.getText()));
		} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			actionPerformed(new ActionEvent(
					cancel, ActionEvent.ACTION_PERFORMED, confirm.getText()));
		}
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
