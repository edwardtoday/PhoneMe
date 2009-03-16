package org.kde9.view;

import javax.swing.JMenuBar;
import javax.swing.JPanel;


public class Factory {
	public static JPanel createGroup() {
		return new Igroup();
	}
	public static JPanel creatName() {
		return new Iname();
	}
	public static JPanel createViewer() {
		return new Iviewer();
	}
	public static JMenuBar createMenuBar() {
		return new Imenubar();
	}
}
