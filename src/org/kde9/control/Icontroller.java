package org.kde9.control;

import org.kde9.model.Ifile;
import org.kde9.view.Igroup;
import org.kde9.view.Imenubar;
import org.kde9.view.Iname;
import org.kde9.view.Iviewer;

/**
 * 2009.03.16
 * 一个主要用于保存各个组件和监听器引用的类
 * 以便别的类调用这些组件和监听器
 */
public class Icontroller {
	private static Imenubar menubar;
	private static Iviewer viewer;
	private static Igroup group;
	private static Iname name;
	private static Ifile file;
	
	private static ButtonAddListener bal;
	private static ButtonSubListener bsl;
	
	/**
	 * 初始化各个组件和监听器 
	 */
	public static void init(
			Imenubar menubar,
			Iviewer viewer,
			Igroup group,
			Iname name,
			Ifile file) {
		Icontroller.menubar = menubar;
		Icontroller.viewer = viewer;
		Icontroller.group = group;
		Icontroller.name = name;
		Icontroller.file = file;
		bal = new ButtonAddListener();
		bsl = new ButtonSubListener();
	}

	public static ButtonSubListener getBsl() {
		return bsl;
	}

	public static void setBsl(ButtonSubListener bsl) {
		Icontroller.bsl = bsl;
	}

	/**
	 * gets
	 */
	static public Imenubar getMenubar() {
		return menubar;
	}
	static public Iviewer getViewer() {
		return viewer;
	}
	static public Igroup getGroup() {
		return group;
	}
	static public Iname getName() {
		return name;
	}
	static public Ifile getFile() {
		return file;
	}
	static public ButtonAddListener getBal() {
		return bal;
	}
}
