package org.kde9.model.group;

import java.util.LinkedHashSet;

public interface Group
extends ConstGroup {
	/**
	 * �������Ա
	 * @param id
	 * 		Ҫ���ӵ����Ա��id
	 * @return
	 * 		��ӳ�Ա�Ƿ�ɹ����ɹ�����true
	 */
	public boolean addGroupMember(int id);
	
	/**
	 * �������Ա
	 * @param members
	 * @return
	 */
	public boolean setGroupMembers(LinkedHashSet<Integer> members);
	
	/**
	 * ɾ����ĳ�Ա
	 * @param id
	 * 		Ҫɾ�������Ա��id
	 * @return
	 * 		ɾ���Ƿ�ɹ����ɹ�����true
	 */
	public boolean deleteGroupMember(int id);
}
