package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import org.kde9.util.Constants;
import org.kde9.view.GroupComponent;

public class Kernel implements Constants{
	/**
	 * 查找函数，要功能超强才行！
	 */
	public boolean find() {
		//TODO
		return false;
	}
	
	public Icard readCard() {
		return null;
	}
	
	public GroupComponent readGroup() {
		return null;
	}
	
	public static void writeFile(String fileName, String contant, boolean f) throws IOException {
		WriteFile wf = new WriteFile(fileName, f);
		wf.write(contant);
		wf.close();
	}
	
	/**
	 * 从数据文件夹中删除当前id的组，包括group文件和相应的AllGroups信息
	 * @param id 要删除的组的id
	 * @throws IOException
	 */
	public static void delFromAllGroup(int id) throws IOException {
		ReadFile rf = new ReadFile(GROUP_PATH + ALLGROUPS);
		String temp = "";
		while(true) {
			String tempx = rf.readLine();
			if(tempx != null) {
				if(!tempx.equals(String.valueOf(id))) {
					temp += tempx;
					temp += System.getProperty("line.separator");
				}
			}
			else
				break;
		}
		rf.close();
		WriteFile wf = new WriteFile(GROUP_PATH + ALLGROUPS, false);
		wf.write(temp);
		wf.close();
		new DelFile(GROUP_PATH + String.valueOf(id)).delete();
		System.out.println("deleted!");
	}
	
	/**
	 * 返回所有的group
	 * <p>
	 * 通过读AllGroups文件中所存的group的id查找相应的group文件，
	 * 从文件中建立该group，并将其加入一个vector中，
	 * 最终返回包含所有group的vector
	 */
	public static Vector<Igroup> readAllGroups() throws IOException {
		ReadFile rf = new ReadFile(GROUP_PATH + ALLGROUPS);
		Vector<Igroup> allGroups = new Vector<Igroup>();
		while(true) {
			String id = rf.readLine();
			if(id != System.getProperty("line.separator") && id != null) {
				System.out.println(id);
				ReadFile wfx = new ReadFile(GROUP_PATH + id);
				String groupName = wfx.readLine();
				Vector<Iperson> persons = new Vector<Iperson>();
				while(true) {
					String personId = wfx.readLine();
					String personName = wfx.readLine();
					if(personName == null || personId == null)
						break;
					persons.add(new Iperson(Integer.valueOf(personId), personName));
				}
				Igroup g = new Igroup(Integer.valueOf(id), groupName, persons);
				allGroups.add(g);
			}
			else
				break;
		}
		System.out.println(allGroups.size());
		return allGroups;
	}
}
