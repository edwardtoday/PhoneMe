package org.kde9.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import org.kde9.control.Kernel;
import org.kde9.model.group.ConstGroup;
import org.kde9.util.Constants;
import org.kde9.view.ComponentPool;

import ch.randelshofer.quaqua.JSheet;
import ch.randelshofer.quaqua.util.Fonts;

import com.sun.jna.examples.WindowUtils;

public class CardSumInfoBox 
implements ActionListener, Constants {
	private static JDialog frame;
	private Container father;
	private Kernel kernel;
	private JSheet sheet;
	private JPanel container;
	private String str;
	private Color color;
	private JButton confirm;
	private JTable infoTable;
	//private int groupSum;
	//private int cardSum;
	int w;
	int h;
	int loc = 0;

	public CardSumInfoBox(Container father, String str, Color color,
			int w, int h) {
		this.frame = new JDialog(ComponentPool.getComponent());
		this.father = father;
		this.confirm = new JButton("[    OK   ]");
		this.str = str;
		this.color = color;
		this.w = w;
		this.h = h;
		this.kernel = ComponentPool.getComponent().getKernel();
		int groupSum = kernel.getAllGroups().size();
		//this.cardSum = kernel.getGroup(0).getGroupMembers().size();
		Object rows[][] = new Object[groupSum][3];
		int i = 0;
		for(int groupId : kernel.getAllGroups().keySet()){
			if (groupId != GROUPALLID) {
				rows[i][0] = "";
				rows[i][1] = kernel.getGroup(groupId).getGroupName().toString();
				rows[i][2] = String.valueOf(kernel.getGroup(groupId)
						.getGroupMembers().size());
				i++;
			}
		}
		rows[groupSum - 1][0] = "";
		rows[groupSum - 1][1] = "总计";
		rows[groupSum - 1][2] = String.valueOf(kernel.getGroup(0).getGroupMembers().size());
		String columns[] = {"","组名", "人数"};
		TableModel model = new DefaultTableModel(rows, columns);
		infoTable = new JTable(model){
			public boolean isCellEditable(int i, int j) {
				return false;
			}
		};
		infoTable.getColumnModel().getColumn(0).setMinWidth(10);
		infoTable.getColumnModel().getColumn(1).setMinWidth(150);
		infoTable.getColumnModel().getColumn(2).setMinWidth(30);
		infoTable.setBackground(Color.YELLOW);
		launch();
	}
	
	private void launch() {
		createUI();
		launchUI();
	}

	private void launchUI() {
		sheet.setUndecorated(true);
		sheet.setVisible(true);
		sheet.getRootPane().putClientProperty(
				"Quaqua.RootPane.isVertical", Boolean.TRUE);
	}
	
	private void createUI() {
		sheet = new JSheet(frame);
		sheet.setSize(w, h);

		container = new JPanel(new BorderLayout());
		sheet.setContentPane(container);
		JLabel label = new JLabel(str);
		container.add("North",label);
		confirm.addActionListener(this);
		confirm.putClientProperty("Quaqua.Button.style", "toolBarRollover");
		container.add("Center", infoTable);
		JPanel button = new JPanel();
		button.add(confirm);
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
		int x = xx + w/2;
		int y = yy + (h-sheet.getHeight())/2;
		frame.setLocation(x, y);
	}

	public JButton getConfirm() {
		return confirm;
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.getConfirm()) {
			ComponentPool.getComponent().setEnabled(true);
			changeAlphaUp(300, 0.8f, ComponentPool.getComponent());
			changeAlphaDown(300, 0, sheet, true);
		}
	}
}
