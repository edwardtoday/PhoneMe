package org.kde9.view.dialog;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.kde9.view.ComponentPool;

import ch.randelshofer.quaqua.JSheet;
import ch.randelshofer.quaqua.util.Fonts;

import com.sun.jna.examples.WindowUtils;


public class CoolInfoBox {
	private static JDialog frame = 
		new JDialog(ComponentPool.getComponent());
	private Container father;
	private JSheet sheet;
	private JPanel container;
	private String str;
	private Color color;
	int w;
	int h;
	int loc = 0;

	public CoolInfoBox(Container father, String str, Color color,
			int w, int h) {
		this.father = father;
		this.str = str;
		this.color = color;
		this.w = w;
		this.h = h;
		launch();
	}
	
	public CoolInfoBox(Container father, String str, Color color,
			int w, int h, int loc) {
		this.loc = loc;
		this.frame = new JDialog(ComponentPool.getComponent());
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
		sheet = new JSheet(frame);
		sheet.setSize(w, h);
		//sheet.setAnimated(false);
		//sheet.setAlwaysOnTop(true);
		//sheet.setBackground(Color.YELLOW);

		container = new JPanel();
		sheet.setContentPane(container);
		JTextArea label = new JTextArea(str);
		label.setEditable(false);
		label.setBackground(color);
		Font f = Fonts.getDialogFont();
		f = new Font(f.getName(), 1, 14);
		label.setFont(f);
		container.add(label);
		container.setOpaque(true);
		container.setBackground(color);

		RoundRectangle2D.Float mask = new RoundRectangle2D.Float(1, 1, 
				sheet.getWidth()-2, sheet.getHeight()-2, 20, 20);
		WindowUtils.setWindowMask(sheet, mask);
		if(father != null)
			centerWindow(father, sheet);
		else
			centerWindow(sheet, sheet);
		changeAlpha(200, 800, 400);
	}

	private void changeAlpha(final int a, final int b, int c) {
		new Thread() {
			public void run() {
				for (float i = 0.5f; i < 1; i += 0.1) {
					try {
						sleep(a/5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					WindowUtils.setWindowAlpha(sheet, i);
				}
				try {
					sleep(b);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (float i = 0.9f; i > 0; i -= 0.05) {
					WindowUtils.setWindowAlpha(sheet, i);
					try {
						sleep(b/20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				sheet.dispose();
			}
		}.start();
	}
	
	private void centerWindow(Container window, Container sheet) {
		int xx = (int) window.getLocationOnScreen().getX();
		int yy = (int) window.getLocationOnScreen().getY();
		int w = window.getWidth();
		int h = window.getHeight();
//		System.out.println(w + " " + h + " " + sheet.getWidth() + " " + sheet.getHeight());
		int x = xx + w/2;
		int y = yy + (h-sheet.getHeight())/2 + loc;
		frame.setLocation(x, y);
	}

}