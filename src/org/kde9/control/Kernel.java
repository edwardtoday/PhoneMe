package org.kde9.control;

import java.util.LinkedHashMap;
import java.util.Vector;

import org.kde9.model.card.ConstCard;
import org.kde9.model.group.ConstGroup;

public interface Kernel {
	public ConstGroup getGroup(int groupId);
	
	public ConstCard getCard(int cardId);
	
	public LinkedHashMap<Integer, String> getAllGroups();
	
	public String getFirstName(int cardId);
	
	public String getLastName(int cardId);
	
	public ConstCard addCard(int groupId, String firstName, String lastName,
			LinkedHashMap<String, Vector<String>> items, 
			LinkedHashMap<Integer, String> relation);
	
	public ConstGroup addGroup(String groupName);
	
	public boolean addGroupMember(int groupId, int personId);
	
	public boolean deleteCard(int personId);
	
	public boolean deleteGroupMember(int groupId, int personId);
}
