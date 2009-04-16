package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;


public class Ikernel {
	private AllGroups allGroups;
	private LinkedHashMap<Integer, Group> groups;
	private RestoreAndBackup restoreAndBackup;
	
	/**
	 * 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 			�Ѿ�����
	 */
	public void init()
	throws IOException, FileNotFoundException {
		restoreAndBackup = Factory.createRestoreAndBackup();
		restoreAndBackup.checkout();
		allGroups = Factory.createAllGroups();
		groups = new LinkedHashMap<Integer, Group>();
		if(allGroups.getGroupIds().size() != 0)
			for(int id : allGroups.getGroupIds())
				groups.put(id, Factory.createGroup(id));
	}
	
	/**
	 * �����������id������
	 * @return ������id��������LinkedHashMap
	 */
	public LinkedHashMap<Integer, String> getAllGroups() {
		LinkedHashMap<Integer, String> temp = new LinkedHashMap<Integer, String>();
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
		allGroups.appendGroup(groupId);
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
		allGroups.deleteGroup(groupId);
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
			g.renameGroup(groupName);
			g.save();
		}
		else {
			System.err.println("renameGroup : δ�ҵ���Ӧid���飡");
		}
	}
	
	/**
	 * ���ظ���id����ĳ�Ա��id������
	 * @param groupId ���id
	 * @return ���ذ������Աid�����ֵ�LinkedHashMap
	 */
	public LinkedHashMap<Integer, String> getGroupMembers(int groupId) {
		return (LinkedHashMap<Integer, String>) groups.get(groupId).getGroupMember().clone();
	}
	
	/**
	 * ���������������Ա
	 * @param groupId Ҫ��ӳ�Ա�����id
	 * @param memberId Ҫ��ӵĳ�Ա��id
	 * @param memberName Ҫ��ӵĳ�Ա������
	 * @throws IOException
	 */
	public void addGroupMember(int groupId, int memberId) 
	throws IOException {
		Group g = groups.get(groupId);
		if(g != null) {
			g.appendGroupMember(memberId);
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
			g.deleteGroupMember(memberId);
			g.save();
		}
		else {
			System.err.println("deleteGroupMember : δ�ҵ���Ӧid���飡");
		}
	}
	
	
}
