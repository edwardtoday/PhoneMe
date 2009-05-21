package org.kde9.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.kde9.control.Kernel;
import org.kde9.util.Constants;
import org.kde9.view.ComponentPool;

import ch.randelshofer.quaqua.JSheet;

import com.sun.jna.examples.WindowUtils;

public class DeleteInfoBox 
implements ActionListener {
	private static JDialog frame;
	private Container father;
	private JSheet sheet;
	private JPanel container;
	private Color color;
	private String str;
	private JButton confirm;
	private JButton cancel;
	private Kernel kernel;
	private int type;
	int w;
	int h;
	
	private boolean closing;

	public DeleteInfoBox(int type, Container father, String str , Color color, int w, int h) {
		this.frame = new JDialog(ComponentPool.getComponent(), true);
		this.type = type;
		this.confirm = new JButton("[    OK   ]");
		this.cancel = new JButton("[Cancel]");
		this.father = father;
		this.str = str;
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

		container = new JPanel(new BorderLayout());
		sheet.setContentPane(container);
		JLabel label = new JLabel(str);
		container.add("Center",label);
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
				if(close)
					DeleteInfoBox.this.closing = true;
				for (float i = 1; i > s; i -= 0.02) {
					try {
						sleep((long) (a/((1-s)*50)));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(!DeleteInfoBox.this.closing)
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
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int groupIdSelected = ComponentPool.getGroupComponent().getSelectedGroupId();
		int nameSelected = ComponentPool.getNameComponent().getSelected();
		int nameIdSelected = ComponentPool.getNameComponent().getSelectedMemberId();
		int groupSelected = ComponentPool.getGroupComponent().getSelected();
		int groupNum = ComponentPool.getGroupComponent().getTable().getRowCount();
//		System.err.println(groupIdSelected);
//		System.err.println(nameIDSelected);
		System.err.println(ComponentPool.getGroupComponent().getTable().hasFocus());
		System.err.println(ComponentPool.getNameComponent().getTable().hasFocus());
		if (e.getSource() == this.getCancel()) {
			ComponentPool.getComponent().setEnabled(true);
			changeAlphaUp(300, 0.8f, ComponentPool.getComponent());
			changeAlphaDown(300, 0, sheet, true);
		} else if (e.getSource() == this.getConfirm()) {
			if(type == Constants.DELETEGROUP) {
				if(groupIdSelected == 0) {
					new CoolInfoBox(ComponentPool.getComponent(), 
							"  All分组不能被删除！", Color.YELLOW , 200, 35,-60);
				}else {
					this.confirm.setEnabled(false);
					this.cancel.setEnabled(false);
					kernel.deleteGroup(groupIdSelected);
					ComponentPool.getGroupComponent().deleteGroup();
					//ComponentPool.getBrowerComponent().showAllGroups(0, groupNum - 1);
					if(groupSelected < ComponentPool.getGroupComponent().getTable().getRowCount())
						ComponentPool.getGroupComponent().setSelected(groupSelected, groupSelected);
					else
						ComponentPool.getGroupComponent().setSelected(groupSelected-1, groupSelected-1);
				}
				System.err.println("delete group!!!");
			}else if(type == Constants.DELETECARD) {
				this.confirm.setEnabled(false);
				this.cancel.setEnabled(false);
				kernel.deleteCard(ComponentPool.getNameComponent().getSelectedMemberIds());
				ComponentPool.getNameComponent().deleteMember();
				ComponentPool.getViewerComponent().clear();
				if(nameSelected < ComponentPool.getNameComponent().getTable().getRowCount())
					ComponentPool.getNameComponent().setSelected(nameSelected, nameSelected);
				else
					ComponentPool.getNameComponent().setSelected(nameSelected-1, nameSelected-1);
				System.err.println("delete name!!!");
			}else if(type == Constants.DELETEFROMGROUP) {
				if(groupIdSelected == 0) {
					new CoolInfoBox(ComponentPool.getComponent(), 
							"不能从ALL分组里删除名片！", Color.YELLOW , 200, 35,-60);
				}else {
					this.confirm.setEnabled(false);
					this.cancel.setEnabled(false);
					kernel.deleteGroupMember(groupIdSelected, nameIdSelected);
					//ComponentPool.getBrowerComponent().showGroupMembers();
					ComponentPool.getNameComponent().deleteMember();
					if(nameSelected < ComponentPool.getNameComponent().getTable().getRowCount())
						ComponentPool.getNameComponent().setSelected(nameSelected, nameSelected);
					else
						ComponentPool.getNameComponent().setSelected(nameSelected-1, nameSelected-1);
				}
			}
			//int index = ComponentPool.getGroupComponent().getTable().getRowCount();
			//ComponentPool.getGroupComponent().setSelected(index - 1, index - 1);
			ComponentPool.getComponent().setEnabled(true);
			changeAlphaUp(300, 0.8f, ComponentPool.getComponent());
			changeAlphaDown(300, 0, sheet, true);
		}
	}
	
}
