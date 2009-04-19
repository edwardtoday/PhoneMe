package org.kde9.control;

import java.io.IOException;

import org.kde9.view.Component;

public class Controller {
	private AllGroupsController allGroupsController;
	private AllNamesController allNamesController;
	private CardController cardController;
	private GroupController groupController;
	private TreeShow treeShow;
	
	private GroupAddListener gal;
	private GroupSubListener gsl;
	private GroupTableListener gtl;

	public Controller() {
		try {
			allGroupsController = new AllGroupsController();
			allNamesController = new AllNamesController();
			cardController = new CardController();
			groupController = new GroupController(
					allGroupsController.getGroupIds());
			treeShow = new TreeShow();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		// TODO
		gal = new GroupAddListener();
		gsl = new GroupSubListener();
		gtl = new GroupTableListener();

	}

	public TreeShow getTreeShow() {
		return treeShow;
	}
}
