package org.kde9.control.controller;

public class ControllerFactory {
	public static AllNameController createAllNameController() {
		return new MyAllName();
	}
	
	public static CardController createCardController() {
		return new MyCard();
	}
	
	public static GroupController createGroupController() {
		return new MyGroup();
	}
}
