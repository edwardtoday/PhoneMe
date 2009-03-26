package org.kde9.model;

import java.io.IOException;
import java.util.HashMap;

class Factory {
	
	static AllGroups createAllGroups()
	throws IOException {
		return Iallgroups.createAllGroups();
	}
	
	/**
	 * ʹ����Ӧ�Ĺ��췽������group
	 * <p>
	 * ͨ�������½�һ��group��
	 * <strong>
	 * ��������ļ���
	 * </strong>
	 * ����������һ������֪group��id��ͬ��id��
	 * <br><strong>
	 * �÷���ֻ�����½��飬
	 * Ҫ���ļ��д�������ʹ�����������ķ���
	 * </strong></br>
	 * @param groupName group������
	 * @throws IOException 
	 */
	static Group createGroup(String groupName)
	throws IOException {
		return Igroup.createGroup(groupName);
	}
	
	/**
	 * ʹ����Ӧ�Ĺ��췽������group
	 * <p>
	 * ͨ�����е���Ϣ����group
	 * @param myId group��id
	 * @param groupName group������
	 * @throws IOException 
	 */
	static Group createGroup(
			int id,
			String groupName,
			HashMap<Integer, String> persons ) {
		return Igroup.createGroup(id, groupName, persons);
	}
	
	/**
	 * ʹ����Ӧ�Ĺ��췽������group
	 * <p>
	 * ͨ�����е���Ϣ����group
	 * @param id group��id
	 * @throws IOException 
	 * @throws IOException
	 */
	static Group createGroup(int id) 
	throws IOException {
		return Igroup.createGroup(id);
	}
}