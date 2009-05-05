package org.kde9.model.group;

import java.util.LinkedHashSet;

public interface ConstGroup {
	/**
	 * 获得组的id
	 * @return
	 */
	public int getId();
	
	/**
	 * 获得所有的组成员
	 * @return
	 * 	所有组成员的id
	 */
	public LinkedHashSet<Integer> getGroupMembers();
	
	public String getGroupName();
}
