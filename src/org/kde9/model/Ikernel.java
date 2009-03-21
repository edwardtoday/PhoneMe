package org.kde9.model;

import java.io.IOException;
import java.util.HashMap;


public class Ikernel {
	private AllGroups allGroups;
	private HashMap<Integer, Group> groups;
	
	public Ikernel()
	throws IOException {
		allGroups = Factory.createAllGroups();
		groups = new HashMap<Integer, Group>();
		if(allGroups.getIds().size() != 0)
			for(int id : allGroups.getIds())
				groups.put(id, Factory.createGroup(id));
	}
	
	public HashMap<Integer, String> getAllGroups() {
		HashMap<Integer, String> temp = new HashMap<Integer, String>();
		if(groups.size() != 0)
			for(int id : groups.keySet())
				temp.put(id, groups.get(id).getGroupName());
		return temp;
	}
	
	public HashMap<Integer, String> getGroupMembers(int groupId) {
		return groups.get(groupId).getPersons();
	}
	
	public void addGroup(String groupName)
	throws IOException {
		Group g = Factory.createGroup(groupName);
		int groupId = g.getGroupId();
		groups.put(groupId, g);
		allGroups.appendToAllGroup(groupId);
	}
	
	public void deleteGroup(int groupId) {
		allGroups.deleteFromAllGroup(groupId);
		Group g = groups.get(groupId);
		groups.remove(groupId);
		g.delete();
	}
}
