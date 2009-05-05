package org.kde9.model.group;

import java.util.LinkedHashSet;

public class MyGroup 
implements Group {
	int id;
	String groupName;
	private LinkedHashSet<Integer> members;
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public MyGroup(int id) {
		this.id = id;
		members = new LinkedHashSet<Integer>();
	}
	
	public int getId() {
		return id;
	}
	
	public boolean addGroupMember(int id) {
		if(members.contains(id))
			return false;
		else
			members.add(id);
		return true;
	}

	public boolean setGroupMembers(LinkedHashSet<Integer> members) {
		this.members = members;
		return false;
	}

	public LinkedHashSet<Integer> getGroupMembers() {
		return members;
	}

	public boolean deleteGroupMember(int id) {
		if(members.contains(id)) {
			members.remove(id);
			return true;
		} else
			return false;
	}

}
