package org.kde9.view.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Window;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;


import org.kde9.view.ComponentPool;

import ch.randelshofer.quaqua.JSheet;

import com.sun.jna.examples.WindowUtils;

public class PhotoBox 
implements MouseListener, MouseMotionListener {
	private static JDialog frame;
	private JComponent father;
	private JSheet sheet;
	private JPanel container;
	private JLabel ok;
	private BufferedImage image;
	int x, y, loc;

	public PhotoBox(JComponent father, BufferedImage image, int loc) {
		this.frame = new JDialog(ComponentPool.getComponent(), true);
		this.ok = new JLabel();
		this.father = father;
		this.image = image;
		this.loc = loc;
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
		
		sheet.setSize(image.getWidth(), image.getHeight());

		//container = new JPanel(new BorderLayout());
		//sheet.setContentPane(container);
		ok.setIcon(new ImageIcon(image));
		ok.addMouseListener(this);
		ok.addMouseMotionListener(this);
		ok.setBorder(BorderFactory.createEmptyBorder());
		//container.add("Center",ok);
		//container.setOpaque(true);
		sheet.add(ok);

		RoundRectangle2D.Float mask = new RoundRectangle2D.Float(1, 1, 
				image.getWidth()-2, image.getHeight()-2, 20, 20);
		WindowUtils.setWindowMask(sheet, mask);
		if(father != null)
			centerWindow(father, sheet);
		else
			centerWindow(sheet, sheet);
		//changeAlphaDown(800, 0.8f, ComponentPool.getComponent(), false);
	}

	private void changeAlphaUp(final int a, final float s, final Window window) {
		new Thread() {
			public void run() {
				for (float i = s; i < 1; i += 0.01) {
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
		}.start();
	}
	
	private void changeAlphaDown(final int a, final float s, final Window window,
			final boolean close) {
		new Thread() {
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

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseDragged(MouseEvent e) {
		Point p = MouseInfo.getPointerInfo().getLocation();
		sheet.setLocation(
				p.x - x, 
				p.y - y);
	}

	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
		//ComponentPool.getComponent().setEnabled(true);
		//changeAlphaUp(300, 0.8f, ComponentPool.getComponent());
		if(x == e.getX() && y == e.getY())
			changeAlphaDown(300, 0, sheet, true);
	}
}
