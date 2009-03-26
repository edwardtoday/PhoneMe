package org.kde9.model;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

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
	private HashSet<Integer> ids;
	WriteFile wf;
	ReadFile rf;
	
	private Iallgroups() 
	throws IOException {
		File file = new File(GROUP_PATH + ALLGROUPS);
		if(!file.exists()) {
			wf = new WriteFile(GROUP_PATH + ALLGROUPS, false);
			wf.write(ALL);
			wf.close();
			ids = new HashSet<Integer>();
		}
		rf = new ReadFile(GROUP_PATH + ALLGROUPS);
		ids = new HashSet<Integer>();
		while(true) {
			String tempId = rf.readLine();
			if(tempId == null 
					|| tempId == System.getProperty("line.separator"))
				break;
			else
				ids.add(Integer.valueOf(tempId));
		}
	}
	
	public static AllGroups createAllGroups() 
	throws IOException {
		return new Iallgroups();
	}
	
	public void save() throws IOException {
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
	
	/* (non-Javadoc)
	 * @see org.kde9.model.AllGroups#appendToAllGroup(int)
	 */
	public void appendToAllGroup(int id) {
		ids.add(id);
	}
	
	/* (non-Javadoc)
	 * @see org.kde9.model.AllGroups#deleteFromGroup(int)
	 */
	public void deleteFromAllGroup(int id) {
		ids.remove(id);
	}

	/* (non-Javadoc)
	 * @see org.kde9.model.AllGroups#getIds()
	 */
	public HashSet<Integer> getIds() {
		return ids;
	}
}
