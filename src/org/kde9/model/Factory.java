package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;

class Factory {

	/**
	 * ������Ӧ���췽���� ��AllGroups�ļ��ж�ȡ��Ϣ����һ��AllGroups����
	 * <p>
	 * ����ڶ�ALLGroups�ļ�ʱ���ַ����ֵ��л��������ԣ� �����������������У� ֱ�������ļ�β��
	 * 
	 * @throws FileNotFoundException
	 *             AllGroups�ļ������ڣ�Ӧ��������쳣��
	 * @throws IOException
	 *             ��δȷ��Ӱ��
	 */
	static I_AllGroups createAllGroups() throws FileNotFoundException,
			IOException {
		return AllGroups.createAllGroups();
	}

	/**
	 * ʹ����Ӧ�Ĺ��췽������group
	 * <p>
	 * ͨ�������½�һ��group�� ����������һ������֪group��id��ͬ��id�� <br>
	 * <strong> �÷���ֻ�����½��飬 Ҫ���ļ��д�������ʹ��int�����ķ��� </strong></br>
	 * 
	 * @param groupName
	 *            group������
	 * @throws IOException
	 *             ��δȷ��Ӱ��
	 */
	static I_Group createGroup(String groupName) throws IOException {
		return Group.createGroup(groupName);
	}

	/**
	 * ͨ�����е���Ϣ����group
	 * 
	 * @param id
	 *            group��id
	 * @throws FileNotFoundException
	 *             ��id������ļ������ڣ�Ӧ������쳣��
	 * @throws IOException
	 *             ��δȷ��Ӱ��
	 */
	static I_Group createGroup(int id) throws FileNotFoundException,
			IOException {
		return Group.createGroup(id);
	}

	static I_Card createCard(String name) throws IOException {
		return Card.createCard(name);
	}

	static I_Card createCard(int id) throws FileNotFoundException, IOException {
		return Card.createCard(id);
	}

	static I_AllNames createAllNames() throws FileNotFoundException,
			IOException {
		return AllNames.createAllNames();
	}

	static I_RestoreAndBackup createRestoreAndBackup() {
		return RestoreAndBackup.createIrestoreandbackup();
	}
}
