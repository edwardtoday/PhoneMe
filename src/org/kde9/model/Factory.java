package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;

class Factory {

	/**
	 * ������Ӧ���췽����
	 * ��AllGroups�ļ��ж�ȡ��Ϣ����һ��AllGroups����
	 * <p>
	 * ����ڶ�ALLGroups�ļ�ʱ���ַ����ֵ��л��������ԣ�
	 * �����������������У�
	 * ֱ�������ļ�β��
	 * 
	 * @throws FileNotFoundException
	 *             AllGroups�ļ������ڣ�Ӧ��������쳣��
	 * @throws IOException
	 *             ��δȷ��Ӱ��
	 */
	static AllGroups createAllGroups() throws FileNotFoundException,
			IOException {
		return Iallgroups.createAllGroups();
	}

	/**
	 * ʹ����Ӧ�Ĺ��췽������group
	 * <p>
	 * ͨ�������½�һ��group��
	 * <strong>
	 * ��������ļ���
	 * </strong>
	 * ����������һ������֪group��id��ͬ��id�� <br>
	 * <strong>
	 * �÷���ֻ�����½��飬
	 * Ҫ���ļ��д�������ʹ��int�����ķ���
	 * </strong></br>
	 * 
	 * @param groupName
	 * 			   group������
	 * @throws IOException
	 *      	   ��δȷ��Ӱ��
	 */
	static Group createGroup(String groupName) throws IOException {
		return Igroup.createGroup(groupName);
	}

	/**
	 * ͨ�����е���Ϣ����group
	 * 
	 * @param id
	 * 	           group��id
	 * @throws FileNotFoundException
	 *             ��id������ļ������ڣ�Ӧ������쳣��
	 * @throws IOException
	 *             ��δȷ��Ӱ��
	 */
	static Group createGroup(int id) throws FileNotFoundException, IOException {
		return Igroup.createGroup(id);
	}
	
	static RestoreAndBackup createRestoreAndBackup() {
		return Irestoreandbackup.createIrestoreandbackup();
	}
}
