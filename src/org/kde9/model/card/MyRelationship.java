package org.kde9.model.card;

import java.util.HashSet;
import java.util.LinkedHashMap;

class MyRelationship {
	private LinkedHashMap<Integer, String> showRelationship;
	private HashSet<Integer> hideRelationship;
	
	MyRelationship() {
		showRelationship = new LinkedHashMap<Integer, String>();
		hideRelationship = new HashSet<Integer>();
	}
	
	public boolean addHideRelationship(int id) {
		hideRelationship.add(id);
		return true;
	}
	
	public boolean addShowRelationship(int id, String content) {
		if(showRelationship.containsKey(id))
			return false;
		else
			showRelationship.put(id, content);
		return true;
	}

	public boolean deleteHideRelationship(int id) {
		hideRelationship.remove(id);
		return false;
	}
	
	public boolean deleteShowRelationship(int id) {
		if(showRelationship.containsKey(id)) {
			showRelationship.remove(id);
			return true;
		}
		return false;
	}

	public HashSet<Integer> getAllHideRelationship() {
		return hideRelationship;
	}
	
	public boolean setShowRelationship(int id, String content) {
		if(showRelationship.containsKey(id)) {
			showRelationship.put(id, content);
			return true;
		}
		return false;
	}
	
	public LinkedHashMap<Integer, String> getAllShowRelationship() {
		return showRelationship;
	}

	public String getShowRelationship(int cardId) {
		return showRelationship.get(cardId);
	}
}
