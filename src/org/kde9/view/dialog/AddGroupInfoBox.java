package org.kde9.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.kde9.control.Kernel;
import org.kde9.model.group.ConstGroup;
import org.kde9.view.ComponentPool;

import ch.randelshofer.quaqua.JSheet;

import com.sun.java.swing.plaf.windows.resources.windows;
import com.sun.jna.examples.WindowUtils;

public class AddGroupInfoBox 
implements ActionListener, KeyListener {
	private static JDialog frame;
	private JComponent father;
//	private Container mainContainer;
	private JSheet sheet;
	private JPanel container;
	private Color color;
	private JTextField textField;
	private JButton confirm;
	private JButton cancel;
	private Kernel kernel;
	private int groupIdSelected;
	int w;
	int h;

	public AddGroupInfoBox(JComponent father,Color color, int w, int h) {
		this.frame = new JDialog(ComponentPool.getComponent(), true);
		this.textField = new JTextField();
		this.confirm = new JButton("[    OK   ]");
		this.cancel = new JButton("[Cancel]");
		this.father = father;
		this.color = color;
		this.w = w;
		this.h = h;
		this.kernel = ComponentPool.getComponent().getKernel();
		launch();
	}

	private void launch() {
		createUI();
		launchUI();
	}

	private void launchUI() {
		sheet.setUndecorated(true);
		sheet.setVisible(true);
		sheet.getRootPane().putClientProperty("Quaqua.RootPane.isVertical", Boolean.TRUE);
	}

	private void createUI() {
		System.setProperty("sun.java2d.noddraw", "true");
		sheet = new JSheet(frame);
		sheet.setSize(w, h);
		//ComponentPool.getComponent().setAlwaysOnTop(true);
		//sheet.setAlwaysOnTop(true);

		container = new JPanel(new BorderLayout());
		sheet.setContentPane(container);
		JLabel label;
		groupIdSelected = 
			ComponentPool.getGroupComponent().getSelectedGroupId();
		if(groupIdSelected != -1)
			label = new JLabel("Group Name:");
		else
			label = new JLabel("Group Name: (将保存搜索结果)");
		//label.setFont(new Font("HeiTi", 1, 15));
		container.add("North",label);
		container.add("Center",textField);
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
		
		textField.addKeyListener(this);

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
				for (float i = s; i < 1; i += 0.02) {
					try {
						sleep((long) (a/((1-s)*50)));
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
				for (float i = 1; i > s; i -= 0.02) {
					try {
						sleep((long) (a/((1-s)*50)));
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
	
	public JTextField getTextField() {
		return textField;
	}
	
//	public Container getMainContainer() {
//		return mainContainer;
//	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		getMainContainer().setEnabled(true);
		//ComponentPool.getComponent().setAlwaysOnTop(false);
		if(e.getSource() == this.getCancel()) {		
			System.out.println("Group added cancelled!!");
			ComponentPool.getComponent().setEnabled(true);
			changeAlphaUp(300, 0.8f, ComponentPool.getComponent());
			changeAlphaDown(300, 0, sheet, true);
		}else if(e.getSource() == this.getConfirm()) {
			String groupName = getTextField().getText();
			if(groupName.length() == 0) {
				new CoolInfoBox(ComponentPool.getGroupComponent(),
						"请输入组名！",Color.YELLOW,200,35,-70);
			}else {
				ConstGroup group = kernel.addGroup(groupName); 
				System.err.println(ComponentPool.getNameComponent().getMembers());
				if(groupIdSelected == -1) {
					kernel.addGroupMember(group.getId(), 
							ComponentPool.getNameComponent().getMembers().keySet());
				}
				ComponentPool.getGroupComponent().addGroup(group.getId(), groupName);
				int index = ComponentPool.getGroupComponent().getTable().getRowCount();
				ComponentPool.getGroupComponent().setSelected(index-1, index-1);
				ComponentPool.getComponent().setEnabled(true);
				changeAlphaUp(300, 0.8f, ComponentPool.getComponent());
				changeAlphaDown(300, 0, sheet, true);
			}
			System.out.println("Group added confirmed!!");
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

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
}
