package org.kde9.control;

import java.io.IOException;

import org.kde9.model.Kernel;
import org.kde9.model.Mgroup;
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
	
	private static ButtonAddListener bal;
	private static ButtonSubListener bsl;
	
	/**
	 * 初始化各个组件和监听器 
	 */
	public static void init(
			Imenubar menubar,
			Iviewer viewer,
			Igroup group,
			Iname name) {
		Icontroller.menubar = menubar;
		Icontroller.viewer = viewer;
		Icontroller.group = group;
		Icontroller.name = name;
		bal = new ButtonAddListener();
		bsl = new ButtonSubListener();
	}

	public static ButtonSubListener getBsl() {
		return bsl;
	}

	public static void setBsl(ButtonSubListener bsl) {
		Icontroller.bsl = bsl;
	}

	public static void initGroup(Igroup group) {
		try {
			for(Mgroup mg : Kernel.readAllGroups())
				group.getModel().addRow(new Object[]{mg});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	static public ButtonAddListener getBal() {
		return bal;
	}
}
