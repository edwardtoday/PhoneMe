package org.kde9.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.randelshofer.quaqua.JSheet;

import com.sun.jna.examples.WindowUtils;

public class AddGroupInfoBox 
implements ActionListener {
	private JFrame frame;
	private JComponent father;
	private Container mainContainer;
	private JSheet sheet;
	private JPanel container;
	private Color color;
	private JTextField textField;
	private JButton confirm;
	private JButton cancel;
	int w;
	int h;

	public AddGroupInfoBox(JComponent father, Container container,Color color, int w, int h) {
		this.frame = new JFrame();
		this.textField = new JTextField();
		this.confirm = new JButton("Yes");
		this.cancel = new JButton("No");
		this.father = father;
		this.mainContainer = container;
		this.color = color;
		this.w = w;
		this.h = h;
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
		sheet.setAlwaysOnTop(true);

		container = new JPanel(new BorderLayout());
		sheet.setContentPane(container);
		JLabel label = new JLabel("Group Name:");
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

		RoundRectangle2D.Float mask = new RoundRectangle2D.Float(1, 1, 
				sheet.getWidth()-2, sheet.getHeight()-2, 20, 20);
		WindowUtils.setWindowMask(sheet, mask);
		if(father != null)
			centerWindow(father, frame);
		else
			centerWindow(sheet, frame);
	}

	private static void centerWindow(Container window, Container frame) {
		int xx = (int) window.getLocationOnScreen().getX();
		int yy = (int) window.getLocationOnScreen().getY();
		int w = window.getSize().width;
		int h = window.getSize().height;
		int x = xx + (w-frame.getWidth())/2;
		int y = yy + (h-frame.getHeight())/2;
		frame.setLocation(x, y);
	}
	
	public JButton getConfirm() {
		return confirm;
	}
	
	public JButton getCancel() {
		return cancel;
	}
	
	public Container getMainContainer() {
		return mainContainer;
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		getMainContainer().setEnabled(true);
		sheet.dispose();
		if(e.getSource() == this.getCancel()) {		
			System.out.println("Group added cancelled!!");
		}else if(e.getSource() == this.getConfirm()) {
			System.out.println("Group added confirmed!!");
		}
	}
	
}
