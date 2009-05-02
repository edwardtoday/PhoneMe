package org.kde9.control.controller;

import java.util.LinkedHashMap;

import org.kde9.model.ModelFactory;
import org.kde9.model.group.ConstGroup;
import org.kde9.model.group.Group;

public class MyGroup 
implements GroupController {
	private LinkedHashMap<Integer, Group> groups;
	private LinkedHashMap<Integer, String> groupNames;
	
	public MyGroup() {
		groups = new LinkedHashMap<Integer, Group>();
		groupNames = new LinkedHashMap<Integer, String>();
		// TODO
	}

	public boolean addGroup(String groupName) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addGroupMember(int personId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteGroup(int groupId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteGroupMember(int personId) {
		// TODO Auto-generated method stub
		return false;
	}

	public LinkedHashMap<Integer, String> getAllGroups() {
		return groupNames;
	}

	public ConstGroup getGroup(int groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean renameGroup(int groupId, String newName) {
		// TODO Auto-generated method stub
		return false;
	}

}
