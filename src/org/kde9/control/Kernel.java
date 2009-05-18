package org.kde9.control;

import java.io.File;
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
	
	public boolean updateCard(int cardId, String firstName, String lastName,
			LinkedHashMap<String, Vector<String>> items, 
			LinkedHashMap<Integer, String> relation);
	
	public boolean setCardImage(int cardId, File file);
	
	public ConstGroup addGroup(String groupName);
	
	public boolean addGroupMember(int groupId, int personId);
	
	public boolean deleteCard(int personId);
	
	public boolean deleteGroup(int groupId);
	
	public boolean deleteGroupMember(int groupId, int personId);
	
	public boolean renameGroup(int groupId, String groupName);
	
//	public LinkedHashMap<Integer, String> findByName(String nameOrPinYin);
	
	public LinkedHashMap<Integer, String> find(String keyWords);
	
	public boolean isSearchFinish();
}
