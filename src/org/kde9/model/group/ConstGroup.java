package org.kde9.model.group;

import java.util.LinkedHashSet;

public interface ConstGroup {
	/**
	 * ������id
	 * @return
	 */
	public int getId();
	
	/**
	 * ������е����Ա
	 * @return
	 * 	�������Ա��id
	 */
	public LinkedHashSet<Integer> getGroupMembers();
	
	public String getGroupName();
}
