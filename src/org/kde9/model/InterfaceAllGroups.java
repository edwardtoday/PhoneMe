package org.kde9.model;

import java.io.IOException;
import java.util.LinkedHashSet;

public interface InterfaceAllGroups {
	
	public abstract void appendGroup(int id);

	public abstract void deleteGroup(int id);

	public abstract LinkedHashSet<Integer> getGroupIds();
	
	/**
	 * ����AllGroups����Ϣ����Ӧ���ļ���
	 * <p>
	 * ���������Ϊÿ�����id��ÿ��idΪһ�С�
	 * �ļ���β���л��з���
	 */
	public abstract void save()
	throws IOException;

}