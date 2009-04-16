package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashSet;

import org.kde9.util.Constants;

/**
 * 用于表示AllGroups的类，实现了AllGroups接口
 * <p>
 * 封装了对AllGroups的基本操作，
 * 包括AllGroups的初始化、保存，删除、修改等。
 * <p>
 * 负责维护AllGroups文件。 
 * 文件中保存且仅保存所有组的id，
 * 每个id占一行，文件结尾会有一空行。
 * 如果在读ALLGroups文件时出现非数字的行或空行则忽略，
 * 并继续读接下来的行，
 * 直到读到文件尾。
 */
class Iallgroups 
implements Constants, AllGroups {
	private LinkedHashSet<Integer> groupIds;
	WriteFile wf;
	ReadFile rf;

	/**
	 * 构造方法
	 * <p>
	 * 从AllGroups文件中读取信息建立一个AllGroups对象。
	 * <p>
	 * 如果在读ALLGroups文件时出现非数字的行或空行则忽略，
	 * 并继续读接下来的行，
	 * 直到读到文件尾。
	 * 
	 * @throws FileNotFoundException 
	 * 		AllGroups文件不存在，应当处理该异常！
	 * @throws IOException
	 * 		尚未确认影响
	 */
	private Iallgroups()
	throws FileNotFoundException, IOException {
		rf = new ReadFile(GROUP_PATH + ALLGROUPS);
		groupIds = new LinkedHashSet<Integer>();
		while (true) {
			String tempId = rf.readLine();
			if (tempId == null)
				break;
			else if (isInt(tempId))
				groupIds.add(Integer.valueOf(tempId));
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
	 * 调用构造方法，
	 * 从AllGroups文件中读取信息建立一个AllGroups对象。
	 * <p>
	 * 如果在读ALLGroups文件时出现非数字的行或空行则忽略，
	 * 并继续读接下来的行，
	 * 直到读到文件尾。
	 * 
	 * @throws FileNotFoundException 
	 * 		AllGroups文件不存在，应当处理该异常！
	 * @throws IOException
	 * 		尚未确认影响
	 */
	public static AllGroups createAllGroups() 
	throws FileNotFoundException, IOException {
		return new Iallgroups();
	}

	/**
	 * 添加group
	 */
	public void appendGroup(int id) {
		groupIds.add(id);
	}

	/**
	 * 删除group
	 */
	public void deleteGroup(int id) {
		groupIds.remove(id);
	}

	public LinkedHashSet<Integer> getGroupIds() {
		return groupIds;
	}

	/**
	 * 保存AllGroups的信息到相应的文件。
	 * <p>
	 * 保存的内容为每个组的id，每个id为一行。 文件结尾会有换行符。
	 */
	public void save() throws IOException {
		wf = new WriteFile(GROUP_PATH + ALLGROUPS, false);
		String temp = "";
		if (groupIds.size() != 0)
			for (int i : groupIds) {
				temp += i;
				temp += NEWLINE;
			}
		wf.write(temp);
		wf.close();
	}
	
	
	/*
	 * test !
	 */
	public static void main(String args[]) {
		AllGroups ag = null;
		try {
			ag = Iallgroups.createAllGroups();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int id : ag.getGroupIds()) {
			System.out.println(id);
		}
		try {
			ag.appendGroup(4);
			ag.deleteGroup(3);
			ag.save();
			for(int id : ag.getGroupIds()) {
				System.out.println(id);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
