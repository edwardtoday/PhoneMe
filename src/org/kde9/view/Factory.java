package org.kde9.view;

import javax.swing.JMenuBar;
import javax.swing.JPanel;


public class Factory {
	public static JPanel createGroup() {
		return new GroupComponent();
	}
	public static JPanel creatName() {
		return new NameComponent();
	}
	public static JPanel createViewer() {
		return new ViewerComponent();
	}
	public static JMenuBar createMenuBar() {
		return new MenubarComponent();
	}
}
