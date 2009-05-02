package org.kde9.model.group;

import java.util.LinkedHashSet;

public interface Group
extends ConstGroup {
	/**
	 * 增加组成员
	 * @param id
	 * 		要增加的组成员的id
	 * @return
	 * 		添加成员是否成功，成功返回true
	 */
	public boolean addGroupMember(int id);
	
	/**
	 * 设置组成员
	 * @param members
	 * @return
	 */
	public boolean setGroupMembers(LinkedHashSet<Integer> members);
	
	/**
	 * 删除组的成员
	 * @param id
	 * 		要删除的组成员的id
	 * @return
	 * 		删除是否成功，成功返回true
	 */
	public boolean deleteGroupMember(int id);
}
