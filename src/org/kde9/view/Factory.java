package org.kde9.view;


public class Factory {
	public static GroupComponent createGroup() {
		return new GroupComponent();
	}
	public static NameComponent creatName() {
		return new NameComponent();
	}
	public static ViewerComponent createViewer() {
		return new ViewerComponent();
	}
	public static MenubarComponent createMenuBar() {
		return new MenubarComponent();
	}
}
