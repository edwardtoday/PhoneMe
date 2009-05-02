package org.kde9.control;

import java.util.LinkedHashMap;

import org.kde9.model.card.ConstCard;
import org.kde9.model.group.ConstGroup;

public interface Kernel {
	public ConstGroup getGroup(int groupId);
	
	public ConstCard getCard(int cardId);
	
	public LinkedHashMap<Integer, String> getAllGroups();
	
	public String getFirstName(int cardId);
	
	public String getLastName(int cardId);
}
