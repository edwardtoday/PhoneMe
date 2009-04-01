package org.kde9.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.kde9.util.Constants;

/**
 * 用于表示AllGroups的类，实现了AllGroups接口
 * <p>
 * 封装了对AllGroups的基本操作，
 * 包括AllGroups的初始化、保存，删除、修改等。
 * 负责维护AllGroups文件
 */
class Iallgroups
implements Constants, AllGroups {
	private ArrayList<Integer> ids;
	WriteFile wf;
	ReadFile rf;
	
	private Iallgroups() {}
	
	public static AllGroups createAllGroups() {
		return new Iallgroups();
	}
	
	public void init() 
	throws IOException, FileNotFoundException {
		rf = new ReadFile(GROUP_PATH + ALLGROUPS);
		ids = new ArrayList<Integer>();
		while(true) {
			String tempId = rf.readLine();
			if(tempId == null 
					|| tempId == ""
					|| tempId == System.getProperty("line.separator"))
				break;
			else
				ids.add(Integer.valueOf(tempId));
		}
	}
	
	/**
	 * 保存AllGroups的信息到相应的文件。
	 * <p>
	 * 保存的内容为每个组的id，每个id为一行。
	 * 文件结尾会有换行符。
	 */
	public void save() 
	throws IOException {
		WriteFile wf = new WriteFile(GROUP_PATH + ALLGROUPS, false);
		String temp = "";
		if(ids.size() != 0)
			for (int i : ids) {
				temp += i;
				temp += System.getProperty("line.separator");
			}
		wf.write(temp);
		wf.close();
	}
	
	/**
	 * 恢复AllGroups信息。
	 * <p>
	 * 通过读取所有group的信息，从文件系统中恢复AllGroups信息。
	 */
	public void rebuilt() 
	throws IOException {
		File file = new File(GROUP_PATH + ALLGROUPS);
		if(file.exists()) {
			new DeleteFile(GROUP_PATH + ALLGROUPS).delete();
		}
		wf = new WriteFile(GROUP_PATH + ALLGROUPS, true);
		ids = new ArrayList<Integer>();
		//添加ALL分组的id
		wf.writeLine(ALL);
		ids.add(Integer.valueOf(ALL));
		File temp = new File(GROUP_PATH);
		File[] groups = temp.listFiles();
		//添加其他分组的id
		if(groups != null && groups.length != 0)
			for(File f : groups) {
				if(f.isFile()) {
					String name = f.getName();
					if(name == ALL) continue;
					if(isInt(name)) {
						wf.writeLine(name);
						ids.add(Integer.valueOf(name));
					}
				}
			}
		wf.close();
	}
	
	public void appendToAllGroup(int id) {
		ids.add(id);
	}
	
	public void deleteFromAllGroup(int id) {
		ids.remove(id);
	}

	public ArrayList<Integer> getIds() {
		return ids;
	}
	
	private boolean isInt(String str) {
		for(char c : str.toCharArray())
			if(!Character.isDigit(c))
				return false;
		return true;
	}
	
	/*
	 * test !
	 */
	public static void main(String args[]) {
		AllGroups ag = null;
		try {
			ag = Iallgroups.createAllGroups();
			ag.init();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			try {
				ag.rebuilt();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int id : ag.getIds()) {
			System.out.println(id);
		}
		try {
			ag.save();
			for(int id : ag.getIds()) {
				System.out.println(id);
			}
//			ag.appendToAllGroup(3);
//			for(int id : ag.getIds()) {
//				System.out.println(id);
//			}
//			ag.save();
//			ag.deleteFromAllGroup(3);
//			ag.save();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
