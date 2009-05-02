package org.kde9.control.controller;

import java.util.LinkedHashMap;

import org.kde9.model.group.ConstGroup;

public interface GroupController {
	public LinkedHashMap<Integer, String> getAllGroups();
	
	public ConstGroup getGroup(int groupId);
	
	public boolean addGroup(String groupName);
	
	public boolean deleteGroup(int groupId);
	
	public boolean renameGroup(int groupId, String newName);
	
	public boolean addGroupMember(int personId);
	
	public boolean deleteGroupMember(int personId);
}
