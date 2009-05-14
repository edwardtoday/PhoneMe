package org.kde9.view.dialog;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ch.randelshofer.quaqua.JSheet;

import com.sun.jna.examples.WindowUtils;


public class CoolInfoBox {
	private JFrame frame;
	private JComponent father;
	private JSheet sheet;
	private JPanel container;
	private String str;
	private Color color;
	int w;
	int h;

	public CoolInfoBox(JComponent father, String str, Color color,
			int w, int h) {
		this.frame = new JFrame();
		this.father = father;
		this.str = str;
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
		sheet.getRootPane().putClientProperty(
				"Quaqua.RootPane.isVertical", Boolean.TRUE);
	}

	private void createUI() {
		System.setProperty("sun.java2d.noddraw", "true");
		sheet = new JSheet(frame);
		sheet.setSize(w, h);
		sheet.setAlwaysOnTop(true);
		//sheet.setBackground(Color.YELLOW);

		container = new JPanel();
		sheet.setContentPane(container);
		JTextArea label = new JTextArea(str);
		label.setEditable(false);
		label.setBackground(color);
		label.setFont(new Font("HeiTi", 1, 14));
		container.add(label);
		container.setOpaque(true);
		container.setBackground(color);

		RoundRectangle2D.Float mask = new RoundRectangle2D.Float(1, 1, 
				sheet.getWidth()-2, sheet.getHeight()-2, 20, 20);
		WindowUtils.setWindowMask(sheet, mask);
		if(father != null)
			centerWindow(father, frame);
		else
			centerWindow(sheet, sheet);
		new Thread() {
			public void run() {
				for(float i = 0.5f; i < 1; i += 0.1) {
					try {
						sleep(40);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					WindowUtils.setWindowAlpha(sheet, i);
				}
				try {
					sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(float i = 0.9f; i > 0; i -= 0.05) {
					WindowUtils.setWindowAlpha(sheet, i);
					try {
						sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				sheet.dispose();
			}
		}.start();
	}

	private static void centerWindow(Container window, Container frame) {
		int xx = (int) window.getLocationOnScreen().getX();
		int yy = (int) window.getLocationOnScreen().getY();
		int w = window.getWidth();
		int h = window.getHeight();
		//System.out.println(w + " " + h + " " + frame.getWidth() + " " + frame.getHeight());
		int x = xx + (w-frame.getWidth())/2;
		int y = yy + (h-frame.getHeight())/2;
		frame.setLocation(x, y);
	}
	
}