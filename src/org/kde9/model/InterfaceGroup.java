package org.kde9.model;

import java.io.IOException;
import java.util.LinkedHashSet;

public interface InterfaceGroup {
	
	/**
	 * �������Ա
	 * <p>
	 * <br><strong>
	 * ע�⣺û�б��浽�ļ�
	 * </br></strong> 
	 * @param personId
	 * @param name
	 */
	public abstract void appendGroupMember(int personId);

	/**
	 * ɾ�����Ա
	 * <p>
	 * <br><strong>
	 * ע�⣺û�б��浽�ļ�
	 * </br></strong> 
	 * @param personId
	 */
	public abstract void deleteGroupMember(int personId);

	/**
	 * �ı�����
	 * <p>
	 * <br><strong>
	 * ע�⣺û�б��浽�ļ�
	 * </br></strong> 
	 */
	public abstract void renameGroup(String groupName);

	public abstract String getGroupName();
	
	/**
	 * ��ø����id
	 * @return ���id
	 */
	public int getGroupId();
	
	public abstract LinkedHashSet<Integer> getGroupMember();
	
	/**
	 * ����group��Ϣ���ļ�
	 * <p>
	 * ��ʽΪ����һ��Ϊ������
	 * ����Ա��Ϊ�գ��Ժ�ÿ����Ϊһ�飬�洢��Ա��id������
	 * @throws IOException
	 */
	public abstract void save()
	throws IOException;

	/**
	 * ɾ������Ϣ���ļ�
	 */
	public abstract void delete();
	
}