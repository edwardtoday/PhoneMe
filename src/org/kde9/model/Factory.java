package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;

class Factory {

	/**
	 * 调用相应构造方法，
	 * 从AllGroups文件中读取信息建立一个AllGroups对象。
	 * <p>
	 * 如果在读ALLGroups文件时出现非数字的行或空行则忽略，
	 * 并继续读接下来的行，
	 * 直到读到文件尾。
	 * 
	 * @throws FileNotFoundException
	 *             AllGroups文件不存在，应当处理该异常！
	 * @throws IOException
	 *             尚未确认影响
	 */
	static AllGroups createAllGroups() throws FileNotFoundException,
			IOException {
		return Iallgroups.createAllGroups();
	}

	/**
	 * 使用相应的构造方法构造group
	 * <p>
	 * 通过组名新建一个group，
	 * <strong>
	 * 并保存成文件。
	 * </strong>
	 * 并给它赋予一个与已知group的id不同的id。 <br>
	 * <strong>
	 * 该方法只用于新建组，
	 * 要从文件中创建组请使用int参数的方法
	 * </strong></br>
	 * 
	 * @param groupName
	 * 			   group的名字
	 * @throws IOException
	 *      	   尚未确认影响
	 */
	static Group createGroup(String groupName) throws IOException {
		return Igroup.createGroup(groupName);
	}

	/**
	 * 通过已有的信息建立group
	 * 
	 * @param id
	 * 	           group的id
	 * @throws FileNotFoundException
	 *             该id的组的文件不存在，应处理该异常。
	 * @throws IOException
	 *             尚未确认影响
	 */
	static Group createGroup(int id) throws FileNotFoundException, IOException {
		return Igroup.createGroup(id);
	}
	
	static RestoreAndBackup createRestoreAndBackup() {
		return Irestoreandbackup.createIrestoreandbackup();
	}
}
