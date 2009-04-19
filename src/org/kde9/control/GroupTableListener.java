package org.kde9.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GroupTableListener implements ListSelectionListener, KeyListener {

	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getValueIsAdjusting());// ///////////////////////////////////
		if (!e.getValueIsAdjusting()) {
			HashMap<Integer, String> groups = Controller.kernel.getAllGroups();

		}
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
