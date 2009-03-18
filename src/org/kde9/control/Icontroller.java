package org.kde9.control;

import java.io.IOException;

import org.kde9.model.Kernel;
import org.kde9.model.Igroup;
import org.kde9.view.GroupComponent;
import org.kde9.view.MenubarComponent;
import org.kde9.view.NameComponent;
import org.kde9.view.ViewerComponent;

/**
 * 2009.03.16
 * 一个主要用于保存各个组件和监听器引用的类
 * 以便别的类调用这些组件和监听器
 */
public class Icontroller {
	private static MenubarComponent menubar;
	private static ViewerComponent viewer;
	private static GroupComponent group;
	private static NameComponent name;
	
	private static ButtonAddListener bal;
	private static ButtonSubListener bsl;
	
	/**
	 * 初始化各个组件和监听器 
	 */
	public static void init(
			MenubarComponent menubar,
			ViewerComponent viewer,
			GroupComponent group,
			NameComponent name) {
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

	public static void initGroup(GroupComponent group) {
		try {
			for(Igroup mg : Kernel.readAllGroups())
				group.getModel().addRow(new Object[]{mg});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * gets
	 */
	static public MenubarComponent getMenubar() {
		return menubar;
	}
	static public ViewerComponent getViewer() {
		return viewer;
	}
	static public GroupComponent getGroup() {
		return group;
	}
	static public NameComponent getName() {
		return name;
	}
	static public ButtonAddListener getBal() {
		return bal;
	}
}
