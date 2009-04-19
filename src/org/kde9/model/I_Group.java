package org.kde9.model;

import java.io.IOException;
import java.util.LinkedHashSet;

interface I_Group {

	public abstract String getGroupName();

	/**
	 * 获得该组的id
	 * 
	 * @return 组的id
	 */
	public int getGroupId();

	/**
	 * 增加组成员
	 * <p>
	 * <br>
	 * <strong> 注意：没有保存到文件 </br></strong>
	 * 
	 * @param personId
	 * @param name
	 */
	public abstract void appendGroupMember(int personId);

	/**
	 * 删除组成员
	 * <p>
	 * <br>
	 * <strong> 注意：没有保存到文件 </br></strong>
	 * 
	 * @param personId
	 */
	public abstract void deleteGroupMember(int personId);

	/**
	 * 改变组名
	 * <p>
	 * <br>
	 * <strong> 注意：没有保存到文件 </br></strong>
	 */
	public abstract void renameGroup(String groupName);

	/**
	 * 保存group信息到文件
	 * <p>
	 * 格式为：第一行为组名， 若组员不为空，以后每两行为一组，存储组员的id和姓名
	 * 
	 * @throws IOException
	 */
	public abstract void save() throws IOException;

	/**
	 * 删除组信息的文件
	 */
	public abstract void delete();

	public abstract LinkedHashSet<Integer> getGroupMember();

}