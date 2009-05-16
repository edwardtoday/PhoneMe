package org.kde9.control.controller;

import java.util.LinkedHashMap;

import org.kde9.model.group.ConstGroup;

public interface GroupController {
	public LinkedHashMap<Integer, String> getAllGroups();
	
	public ConstGroup getGroup(int groupId);
	
	public ConstGroup addGroup(String groupName);
	
	public boolean deleteGroup(int groupId);
	
	public boolean renameGroup(int groupId, String newName);
	
	public boolean addGroupMember(int groupId, int personId);
	
	public boolean deleteGroupMember(int groupId, int personId);
	
	public boolean save(int personId);
}
