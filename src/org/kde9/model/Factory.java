package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

class Factory {
	/**
	 * ʹ����Ӧ�Ĺ��췽������AllGroups
	 * <p>
	 * ���ļ�ϵͳ�е�AllGroups�ļ��ж�ȡ������AllGroups�ࡣ
	 * �����б����������������id��name����Ա��Ϣ��
	 */
	static AllGroups createAllGroups() {
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
	 * @throws IOException ��id��������ļ������ڡ���
	 */
	static Group createGroup(int id) 
	throws IOException {
		return Igroup.createGroup(id);
	}
}
