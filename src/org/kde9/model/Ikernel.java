package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;


public class Ikernel {
	private AllGroups allGroups;
	private HashMap<Integer, Group> groups;
	
	/**
	 * 
	 * @throws IOException 
	 * @throws FileNotFoundException 文件系统中保存AllGroups的文件不存在或被破坏，
	 * 可以使用restore方法试图恢复（应提示用户文件恢复并不能保证数据不丢失）。
	 */
	public void init()
	throws IOException, FileNotFoundException {
		allGroups = Factory.createAllGroups();
		allGroups.init();
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
	 * 通过组名新建一个组
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
	
	/**
	 * 通过组的id删除组
	 * <p>
	 * 从AllGroups中删除该id，并保存AllGroups。
	 * 然后删除group在磁盘上的文件。
	 * @param groupId 组的id
	 * @throws IOException 可能要删除的文件已经不存在了……
	 */
	public void deleteGroup(int groupId)
	throws IOException {
		allGroups.deleteFromAllGroup(groupId);
		allGroups.save();
		Group g = groups.get(groupId);
		if(g != null) {
			groups.remove(groupId);
			g.delete();
		}
		else {
			System.err.println("deleteGroup : 未找到相应id的组！");
		}
	}
	
	/**
	 * 对给定id表示的组进行重命名
	 * <p>
	 * 对给定id的组进行重命名，并保存。
	 * @param groupId 要重命名的组的id
	 * @param groupName 组的新名字
	 * @throws IOException
	 */
	public void renameGroup(int groupId, String groupName)
	throws IOException {
		Group g = groups.get(groupId);
		if(g != null) {
			g.rename(groupName);
			g.save();
		}
		else {
			System.err.println("renameGroup : 未找到相应id的组！");
		}
	}
	
	/**
	 * 返回给定id的组的成员的id和名字
	 * @param groupId 组的id
	 * @return 返回包含组成员id和名字的HashMap
	 */
	public HashMap<Integer, String> getGroupMembers(int groupId) {
		return groups.get(groupId).getPersons();
	}
	
	/**
	 * 向给定组中添加组成员
	 * @param groupId 要添加成员的组的id
	 * @param memberId 要添加的成员的id
	 * @param memberName 要添加的成员的名字
	 * @throws IOException
	 */
	public void addGroupMember(int groupId, int memberId, String memberName) 
	throws IOException {
		Group g = groups.get(groupId);
		if(g != null) {
			g.appendPerson(memberId, memberName);
			g.save();
		}
		else {
			System.err.println("addGroupMembers : 未找到相应id的组！");
		}
	}
	
	public void deleteGroupMember(int groupId, int memberId) 
	throws IOException {
		Group g = groups.get(groupId);
		if(g != null) {
			g.deletePerson(memberId);
			g.save();
		}
		else {
			System.err.println("deleteGroupMember : 未找到相应id的组！");
		}
	}
	
	public void renameGroupMember(int groupId, int memberId, String memberName) 
	throws IOException {
		Group g = groups.get(groupId);
		if(g != null) {
			g.renamePerson(memberId, memberName);
			g.save();
		}
		else {
			System.err.println("renameGroupMember : 未找到相应id的组！");
		}	
	}
}
