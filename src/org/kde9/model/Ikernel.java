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
	 * @throws FileNotFoundException �ļ�ϵͳ�б���AllGroups���ļ������ڻ��ƻ���
	 * ����ʹ��restore������ͼ�ָ���Ӧ��ʾ�û��ļ��ָ������ܱ�֤���ݲ���ʧ����
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
	 * �����������id������
	 * @return ������id��������HashMap
	 */
	public HashMap<Integer, String> getAllGroups() {
		HashMap<Integer, String> temp = new HashMap<Integer, String>();
		if(groups.size() != 0)
			for(int id : groups.keySet())
				temp.put(id, groups.get(id).getGroupName());
		return temp;
	}
	
	/**
	 * ͨ�������½�һ����
	 * <p>
	 * ����ͨ�������½�һ��group����������ļ���
	 * ����������һ������֪group��id��ͬ��id��
	 * Ȼ����AllGroups����Ӹ����id�������档
	 * @param groupName �½��������
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
	 * ͨ�����idɾ����
	 * <p>
	 * ��AllGroups��ɾ����id��������AllGroups��
	 * Ȼ��ɾ��group�ڴ����ϵ��ļ���
	 * @param groupId ���id
	 * @throws IOException ����Ҫɾ�����ļ��Ѿ��������ˡ���
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
			System.err.println("deleteGroup : δ�ҵ���Ӧid���飡");
		}
	}
	
	/**
	 * �Ը���id��ʾ�������������
	 * <p>
	 * �Ը���id��������������������档
	 * @param groupId Ҫ�����������id
	 * @param groupName ���������
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
			System.err.println("renameGroup : δ�ҵ���Ӧid���飡");
		}
	}
	
	/**
	 * ���ظ���id����ĳ�Ա��id������
	 * @param groupId ���id
	 * @return ���ذ������Աid�����ֵ�HashMap
	 */
	public HashMap<Integer, String> getGroupMembers(int groupId) {
		return groups.get(groupId).getPersons();
	}
	
	/**
	 * ���������������Ա
	 * @param groupId Ҫ��ӳ�Ա�����id
	 * @param memberId Ҫ��ӵĳ�Ա��id
	 * @param memberName Ҫ��ӵĳ�Ա������
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
			System.err.println("addGroupMembers : δ�ҵ���Ӧid���飡");
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
			System.err.println("deleteGroupMember : δ�ҵ���Ӧid���飡");
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
			System.err.println("renameGroupMember : δ�ҵ���Ӧid���飡");
		}	
	}
}
