package org.kde9.control;

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
	
//	private static ButtonAddListener bal;
//	private static ButtonSubListener bsl;
//	private static TableSelectionListener ls;
	


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
//		bal = new ButtonAddListener();
//		bsl = new ButtonSubListener();
//		ls = new TableSelectionListener();
//		initGroup();
//		initName();
	}

//	public static ButtonSubListener getBsl() {
//		return bsl;
//	}
//
//	public static void setBsl(ButtonSubListener bsl) {
//		Icontroller.bsl = bsl;
//	}

//	static void initGroup() {
//		group.getButtonAdd().addActionListener(bal);
//		group.getButtonSub().addActionListener(bsl);
//		group.getTable().getSelectionModel().setSelectionMode(
//				ListSelectionModel.SINGLE_SELECTION );
//		group.getTable().getSelectionModel().addListSelectionListener(ls);
//		group.getTable().addKeyListener(ls);
//
//		try {
//			for(Igroup mg : Kernel.readAllGroups())
//				group.getModel().addRow(new Object[]{mg});
//			group.getTable().getSelectionModel().setSelectionInterval(0, 0);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	static void initName() {
//		System.out.println(group.getModel());
//		Igroup g = (Igroup) group.getModel().getValueAt(0, 0);
//		System.out.println(g.getPersons());
//	}
	
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
}
