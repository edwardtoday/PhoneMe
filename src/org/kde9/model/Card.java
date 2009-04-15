package org.kde9.model;

import java.io.IOException;

interface Card {

	public abstract void rename(String name);

	public abstract void appendItem(String name, String content);

	public abstract void deleteItem(String name);

	public abstract void renameItem(String oldName, String newName);

	public abstract void appendPerson(int id, String name);

	public abstract void deletePerson(int id);

	/**
	 * ������Ƭ
	 * <p>
	 * �����ʽΪ��
	 * �ļ�����id��
	 * ��һ�У�������
	 * ������ÿ����Ϊһ�飺item������String�ֶΣ�
	 * ��־λ��SEPERATOR;
	 * ������ÿ����Ϊһ�飺persons�������ֶΣ�
	 * ������һ�п��С�
	 * @throws IOException
	 */
	public abstract void save() throws IOException;

	public abstract void delete();

}