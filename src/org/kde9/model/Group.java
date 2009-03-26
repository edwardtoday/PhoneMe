package org.kde9.model;

import java.io.IOException;
import java.util.HashMap;

interface Group {

	public abstract String getGroupName();
	
	/**
	 * ��ø����id
	 * @return ���id
	 */
	public int getGroupId();
	
	/**
	 * �ı����Ա������
	 * <p>
	 * <br><strong>
	 * ע�⣺û�б��浽�ļ�
	 * </br></strong> 
	 * @param personId
	 * @param name
	 */
	public abstract void renamePerson(int personId, String name);

	/**
	 * �������Ա
	 * <p>
	 * <br><strong>
	 * ע�⣺û�б��浽�ļ�
	 * </br></strong> 
	 * @param personId
	 * @param name
	 */
	public abstract void appendPerson(int personId, String name);

	/**
	 * ɾ�����Ա
	 * <p>
	 * <br><strong>
	 * ע�⣺û�б��浽�ļ�
	 * </br></strong> 
	 * @param personId
	 */
	public abstract void deletePerson(int personId);

	/**
	 * �ı�����
	 * <p>
	 * <br><strong>
	 * ע�⣺û�б��浽�ļ�
	 * </br></strong> 
	 */
	public abstract void rename(String groupName);

	/**
	 * ����group��Ϣ���ļ�
	 * <p>
	 * ��ʽΪ����һ��Ϊ������
	 * ����Ա��Ϊ�գ��Ժ�ÿ����Ϊһ�飬�洢��Ա��id������
	 * @throws IOException
	 */
	public abstract void save() throws IOException;

	/**
	 * ɾ������Ϣ���ļ�
	 */
	public abstract void delete();
	
	public abstract HashMap<Integer, String> getPersons();

}