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

import org.kde9.view.ComponentPool;

import ch.randelshofer.quaqua.JSheet;

import com.sun.jna.examples.WindowUtils;

public class AddNameInfoBox 
implements ActionListener {
	private JFrame frame;
	private JComponent father;
//	private Container mainContainer;
	private JSheet sheet;
	private JPanel container;
	private Color color;
	JTextField firstName;
	JTextField lastName;
	private JButton confirm;
	private JButton cancel;
	int w;
	int h;

	public AddNameInfoBox(JComponent father,Color color, int w, int h) {
		this.frame = new JFrame();
		this.firstName = new JTextField();
		this.lastName = new JTextField();
		this.confirm = new JButton("Yes");
		this.cancel = new JButton("No");
		this.father = father;
//		this.mainContainer = container;
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
		int y = yy + (h-frame.getHeight())*2/5;
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
		ComponentPool.getComponent().setEnabled(true);
		sheet.dispose();
		if(e.getSource() == this.getCancel()) {
			System.out.println("Name added cancelled!!");
		}else if(e.getSource() == this.getConfirm()) {
			System.out.println("Name added confirmed!!");
		}
	}
	
}