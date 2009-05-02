package org.kde9.control;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.kde9.control.RestoreAndBackup.MyRandB;
import org.kde9.control.RestoreAndBackup.RestoreAndBackup;
import org.kde9.control.controller.AllNameController;
import org.kde9.control.controller.CardController;
import org.kde9.control.controller.ControllerFactory;
import org.kde9.control.controller.GroupController;
import org.kde9.model.card.ConstCard;
import org.kde9.model.group.ConstGroup;

public class MyKernel 
implements Kernel {
	private AllNameController names;
	private CardController cards;
	private GroupController groups;
	private RestoreAndBackup randb;

	public MyKernel() {
		randb = new MyRandB();
		try {
			randb.checkout();
		} catch (IOException e) {
			System.err.println("Kernel: checkout error!");
		}
		names = ControllerFactory.createAllNameController();
		cards = ControllerFactory.createCardController();
		groups = ControllerFactory.createGroupController();
	}
	
	public LinkedHashMap<Integer, String> getAllGroups() {
		return groups.getAllGroups();
	}

	public ConstCard getCard(int cardId) {
		return cards.getCard(cardId);
	}

	public String getFirstName(int cardId) {
		return names.getFirstName(cardId);
	}

	public ConstGroup getGroup(int groupId) {
		return groups.getGroup(groupId);
	}

	public String getLastName(int cardId) {
		return names.getLastName(cardId);
	}

}
