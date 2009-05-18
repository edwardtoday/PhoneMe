package org.kde9.control.controller;

import java.util.LinkedHashSet;

public class ControllerFactory {
	public static AllNameController createAllNameController() {
		return new MyAllNameController();
	}
	
	public static CardController createCardController() {
		return new MyCardController();
	}
	
	public static GroupController createGroupController(LinkedHashSet<Integer> ids) {
		return new MyGroupController(ids);
	}
}
