package org.kde9.control.controller;

import java.util.LinkedHashMap;

import org.kde9.model.group.ConstGroup;
import org.kde9.model.group.Group;

public interface GroupController {
	public LinkedHashMap<Integer, String> getAllGroups();
	
	public ConstGroup getGroup(int groupId);
	
	public Group addGroup(String groupName);
	
	public boolean deleteGroup(int groupId);
	
	public boolean renameGroup(int groupId, String newName);
	
	public boolean addGroupMember(int groupId, int personId);
	
	public boolean deleteGroupMember(int groupId, int personId);
	
	public boolean save(int groupId);
	
	public boolean save(ConstGroup group);
}
