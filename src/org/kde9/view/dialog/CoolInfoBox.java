package org.kde9.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.jna.examples.WindowUtils;

/**
 * Transparent JFrame use JNA
 * 
 * @author ruislan <a href="mailto:z17520@126.com"/>
 * @version 0.1.0
 */
public class CoolInfoBox {
	private JComponent father;
	private JDialog dialog;
	private JPanel container;
	private String str;

	public CoolInfoBox(JComponent father, String str) {
		this.father = father;
		this.str = str;
		launch();
	}

	private void launch() {
		createUI();
		launchUI();
	}

	private void launchUI() {
		dialog.setUndecorated(true);
		dialog.setVisible(true);
		dialog.getRootPane().putClientProperty(
				"Quaqua.RootPane.isVertical", Boolean.TRUE);
	}

	private void createUI() {
		System.setProperty("sun.java2d.noddraw", "true");
		dialog = new JDialog();
		dialog.setSize(200, 70);
		dialog.setAlwaysOnTop(true);
		//dialog.setBackground(Color.YELLOW);

		container = new JPanel();
		dialog.setContentPane(container);
		JLabel label = new JLabel(str);
		//label.setFont(new Font("HeiTi", 1, 15));
		container.add(label);
		container.setOpaque(true);
		container.setBackground(Color.YELLOW);

		RoundRectangle2D.Float mask = new RoundRectangle2D.Float(0, 0, dialog
				.getWidth(), dialog.getHeight(), 20, 20);
		WindowUtils.setWindowMask(dialog, mask);
		if(father != null)
			centerWindow(father, dialog);
		else
			centerWindow(dialog, dialog);
		new Thread() {
			public void run() {
				for(float i = 0.5f; i < 1; i += 0.05) {
					try {
						sleep(40);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					WindowUtils.setWindowAlpha(dialog, i);
				}
				try {
					sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(float i = 1; i > 0; i -= 0.05) {
					WindowUtils.setWindowAlpha(dialog, i);
					try {
						sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				dialog.dispose();
			}
		}.start();
	}

	private static void centerWindow(Container window, Container dialog) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int xx = (int) window.getLocationOnScreen().getX();
		int yy = (int) window.getLocationOnScreen().getY();
		int w = window.getSize().width;
		int h = window.getSize().height;
		int x = xx + (w-dialog.getWidth())/2;
		int y = yy + (h-dialog.getHeight())/2;
		dialog.setLocation(x, y);
	}
	
	public static void main(String args[]) {
		new CoolInfoBox(null, "呵呵，名片内容不能为空啊！");
	}
}