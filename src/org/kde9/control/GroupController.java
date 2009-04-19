package org.kde9.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import org.kde9.model.Factory;
import org.kde9.model.InterfaceGroup;

public class GroupController {
	private LinkedHashMap<Integer, InterfaceGroup> groups;
	
	public GroupController(LinkedHashSet<Integer> ids) 
	throws IOException {
		groups = new LinkedHashMap<Integer, InterfaceGroup>();
		if(ids != null)
			for(int id : ids)
				try {
					groups.put(id, Factory.createGroup(id));
				} catch(FileNotFoundException e) {
					// TODO
				}
	}
	
	public LinkedHashMap<Integer, String> getAllGroupNames() {
		LinkedHashMap<Integer, String> temp = new LinkedHashMap<Integer, String>();
		if(groups != null)
			for(int id : groups.keySet())
				temp.put(id, groups.get(id).getGroupName());
		return temp;
	}
	
	public void appendGroupMember(int groupId, int[] personId) 
	throws IOException {
		InterfaceGroup group = groups.get(groupId);
		if(group != null) {
			for(int i = 0; i < personId.length; i++)
				group.appendGroupMember(personId[i]);
			group.save();
		}
	}
	
	public void deleteGroupMember(int groupId, int[] personId)
	throws IOException {
		InterfaceGroup group = groups.get(groupId);
		if(group != null) {
			for(int i = 0; i < personId.length; i++)
				group.deleteGroupMember(personId[i]);
			group.save();
		}
	}
	
	public LinkedHashSet<Integer> getGroupMember(int groupId) {
		LinkedHashSet<Integer> temp = new LinkedHashSet<Integer>();
		InterfaceGroup group = groups.get(groupId);
		if(group != null) {
			LinkedHashSet<Integer> members = group.getGroupMember();
			if(members != null)
				for(int id : members)
					temp.add(id);
		}
		return temp;
	}
	
	public int addGroup(String groupName) 
	throws IOException {
		InterfaceGroup group = Factory.createGroup(groupName);
		int id = group.getGroupId();
		group.save();
		groups.put(id, group);
		return id;
	}
	
	public void renameGroup(int groupId, String groupName) {
		InterfaceGroup group = groups.get(groupId);
		if(group != null)
			group.renameGroup(groupName);
	}
	
	public void deleteGroup(int groupId) {
		InterfaceGroup group = groups.get(groupId);
		if(group != null) {
			group.delete();
			groups.remove(groupId);
		}
	}
	
}
