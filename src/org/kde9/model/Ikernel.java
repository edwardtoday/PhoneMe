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
	
	/**
	 * 返回所有组的id和组名
	 * @return 包含组id和组名的HashMap
	 */
	public HashMap<Integer, String> getAllGroups() {
		HashMap<Integer, String> temp = new HashMap<Integer, String>();
		if(groups.size() != 0)
			for(int id : groups.keySet())
				temp.put(id, groups.get(id).getGroupName());
		return temp;
	}
	
	/**
	 * 用组名新建一个组
	 * <p>
	 * 首先通过组名新建一个group，并保存成文件，
	 * 并给它赋予一个与已知group的id不同的id。
	 * 然后向AllGroups中添加该组的id，并保存。
	 * @param groupName 新建组的组名
	 * @throws IOException 
	 */
	public void addGroup(String groupName)
	throws IOException {
		Group g = Factory.createGroup(groupName);
		int groupId = g.getGroupId();
		groups.put(groupId, g);
		allGroups.appendToAllGroup(groupId);
		allGroups.save();
	}
	
	public void deleteGroup(int groupId) {
		allGroups.deleteFromAllGroup(groupId);
		Group g = groups.get(groupId);
		groups.remove(groupId);
		g.delete();
	}
	
	public void renameGroup(int groupId, String groupName)
	throws IOException {
		Group g = groups.get(groupId);
		g.rename(groupName);
		g.save();
	}
	
	public HashMap<Integer, String> getGroupMembers(int groupId) {
		return groups.get(groupId).getPersons();
	}
	
	public void addGroupMembers(int groupId, String memberName) {
		
	}
}
