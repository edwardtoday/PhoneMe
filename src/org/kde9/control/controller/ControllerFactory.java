package org.kde9.control.controller;

public class ControllerFactory {
	public static AllNameController createAllNameController() {
		return new MyAllNameController();
	}
	
	public static CardController createCardController() {
		return new MyCardController();
	}
	
	public static GroupController createGroupController() {
		return new MyGroupController();
	}
}
