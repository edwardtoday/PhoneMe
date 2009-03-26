package org.kde9.model;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import org.kde9.util.Constants;

/**
 * ���ڱ�ʾAllGroups���࣬ʵ����AllGroups�ӿ�
 * <p>
 * ��װ�˶�AllGroups�Ļ���������
 * ����AllGroups�ĳ�ʼ�������棬ɾ�����޸ĵȡ�
 * ����ά��AllGroups�ļ�
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
