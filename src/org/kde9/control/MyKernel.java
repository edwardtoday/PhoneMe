package org.kde9.control;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.kde9.control.RestoreAndBackup.MyRandB;
import org.kde9.control.RestoreAndBackup.RestoreAndBackup;
import org.kde9.control.controller.AllNameController;
import org.kde9.control.controller.CardController;
import org.kde9.control.controller.ControllerFactory;
import org.kde9.control.controller.GroupController;
import org.kde9.model.card.Card;
import org.kde9.model.card.ConstCard;
import org.kde9.model.group.ConstGroup;
import org.kde9.util.Constants;

public class MyKernel 
implements Kernel, Constants {
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

	public ConstCard addCard(int groupId, String firstName, String lastName,
			LinkedHashMap<String, Vector<String>> items,
			LinkedHashMap<Integer, String> relation) {
		Card card = cards.addCard(firstName, lastName);
		card.setItems(items);
		int id = card.getId();
		cards.setRelationships(id, relation);
		names.addPerson(id, firstName, lastName);
		groups.addGroupMember(GROUPALLID, id);
		if(groupId != GROUPALLID)
			groups.addGroupMember(groupId, id);
		return null;
	}

	public ConstGroup addGroup(String groupName) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean addGroupMember(int groupId, int personId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteCard(int personId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteGroupMember(int groupId, int personId) {
		// TODO Auto-generated method stub
		return false;
	}

}
