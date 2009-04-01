package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

class Factory {
	/**
	 * 使用相应的构造方法构造AllGroups
	 * <p>
	 * 从文件系统中的AllGroups文件中读取并创建AllGroups类。
	 * 该类中保存了所有已有组的id、name、成员信息。
	 */
	static AllGroups createAllGroups() {
		return Iallgroups.createAllGroups();
	}
	
	/**
	 * 使用相应的构造方法构造group
	 * <p>
	 * 通过组名新建一个group，
	 * <strong>
	 * 并保存成文件。
	 * </strong>
	 * 并给它赋予一个与已知group的id不同的id。
	 * <br><strong>
	 * 该方法只用于新建组，
	 * 要从文件中创建组请使用三个参数的方法
	 * </strong></br>
	 * @param groupName group的名字
	 * @throws IOException 
	 */
	static Group createGroup(String groupName)
	throws IOException {
		return Igroup.createGroup(groupName);
	}
	
	/**
	 * 使用相应的构造方法构造group
	 * <p>
	 * 通过已有的信息建立group
	 * @param myId group的id
	 * @param groupName group的名字
	 */
	static Group createGroup(
			int id,
			String groupName,
			HashMap<Integer, String> persons ) {
		return Igroup.createGroup(id, groupName, persons);
	}
	
	/**
	 * 使用相应的构造方法构造group
	 * <p>
	 * 通过已有的信息建立group
	 * @param id group的id
	 * @throws IOException 该id所代表的文件不存在……
	 */
	static Group createGroup(int id) 
	throws IOException {
		return Igroup.createGroup(id);
	}
}
