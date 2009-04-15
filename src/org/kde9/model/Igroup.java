package org.kde9.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashSet;

import org.kde9.util.Constants;


/**
 * 用于表示group的类，实现了Group接口
 * <p>
 * 封装了对group的基本操作，
 * 包括group的初始化、保存，删除、修改等。
 * <p>
 * 负责维护group文件。
 * 文件中保存且仅保存组的名字和组成员的id。
 * 文件的第一行为组的名字，
 * 接下来每一行为一个组成员的id，
 * 最后会有一行空行。
 * 如果在读group文件时，
 * 读组名时出现空行则将组名置为默认值，
 * 读组成员时出现非数字的行或空行则忽略，
 * 并继续读接下来的行，
 * 直到读到文件尾。
 */
class Igroup 
implements Constants, Group {
	static int staticId = 0;
	private int groupId;
	private String groupName;
	private LinkedHashSet<Integer> groupMembers;
	
	/**
	 * 通过组名新建一个group，并保存成文件
	 * 并给它赋予一个与已知group的id不同的id。
	 * <br><strong>
	 * 该方法只用于新建组，
	 * 要从文件中创建组请使用int参数的方法
	 * </strong></br>
	 * @param groupName 
	 * 		group的名字
	 * @throws IOException 
	 * 		尚未确认影响 
	 */
	private Igroup(String groupName)
	throws IOException {
		//找到未使用的id
		File file = new File(GROUP_PATH + String.valueOf(staticId));
		while (file.exists()) {
			staticId++;
			file = new File(GROUP_PATH + String.valueOf(staticId));
		}
		groupId = staticId++;
		this.groupName = groupName;
		groupMembers = new LinkedHashSet<Integer>();
		this.save();
	}
	
	/**
	 * 通过已有的信息建立group
	 * 
	 * @param id 
	 * 		group的id
	 * @throws FileNotFoundException
	 * 		该id的组的文件不存在，应处理该异常。
	 * @throws IOException
	 * 		尚未确认影响
	 */
	private Igroup(int groupId) 
	throws FileNotFoundException, IOException {
		ReadFile rf = new ReadFile(GROUP_PATH + String.valueOf(groupId));
		this.groupId = groupId;
		groupName = rf.readLine();
		if(groupName == null || groupName == "")
			groupName = NULLGROUPNAME;
		groupMembers = new LinkedHashSet<Integer>();
		while(true) {
			String tempId = rf.readLine();
			if(tempId == null)
				break;
			else if(isInt(tempId))
				groupMembers.add(Integer.valueOf(tempId));
			else
				continue;
		}
	}

	/**
	 * 判断一个字符串是否为数字
	 * <p>
	 * 字符串为空串或非数字串将返回false。
	 * 
	 * @param str
	 * 		要判断的字符串
	 */
	private boolean isInt(String str) {
		// 为空串返回false
		if (str == "")
			return false;
		for (char c : str.toCharArray())
			// 非数字返回false
			if (!Character.isDigit(c))
				return false;
		return true;
	}
	
	/**
	 * 通过组名新建一个group，并保存成文件
	 * 并给它赋予一个与已知group的id不同的id。
	 * <br><strong>
	 * 该方法只用于新建组，
	 * 要从文件中创建组请使用int参数的方法
	 * </strong></br>
	 * @param groupName 
	 * 		group的名字
	 * @throws IOException 
	 * 		尚未确认影响 
	 */
	static Group createGroup(String groupName) 
	throws IOException {
		return new Igroup(groupName); 
	}
	
	/**
	 * 通过已有的信息建立group
	 * 
	 * @param id 
	 * 		group的id
	 * @throws FileNotFoundException
	 * 		该id的组的文件不存在，应处理该异常。
	 * @throws IOException
	 * 		尚未确认影响
	 */
	static Group createGroup(int id) 
	throws FileNotFoundException, IOException {
		return new Igroup(id);
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public int getGroupId() {
		return groupId;
	}
	
	public void appendGroupMember(int personId) {
		groupMembers.add(personId);
	}
	
	public void deleteGroupMember(int personId) {
		groupMembers.remove(personId);
	}
	
	public void renameGroup(String groupName) {
		this.groupName = groupName;
	}
	
	public void save()
	throws IOException {
		String temp = groupName;
		temp += NEWLINE;
		if(groupMembers.size() != 0)
			for (int i : groupMembers) {
				temp += i;
				temp += NEWLINE;
			}
		System.out.println("group saving");// /////////////////////////////////////////////
		
		//写入group文件
		WriteFile wf = new WriteFile(GROUP_PATH + String.valueOf(groupId), false);
		wf.write(temp);
		wf.close();
	}
	
	public void delete() {
		DeleteFile df = new DeleteFile(GROUP_PATH + String.valueOf(groupId));
		df.delete();
	}

	public LinkedHashSet<Integer> getGroupMember() {
		return groupMembers;
	}
}

