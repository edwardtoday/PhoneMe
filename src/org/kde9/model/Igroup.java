package org.kde9.model;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.kde9.util.Constants;

public class Igroup implements Constants{
	private static int id = 0;
	private int myId;
	private String groupName;
	private Vector<Iperson> persons;
	
	public Vector<Iperson> getPersons() {
		return persons;
	}

	/**
	 * 新建一个group，
	 * 并给它赋予一个与已知group的id不同的id
	 * @param groupName group的名字
	 */
	public Igroup(String groupName) {
		// TODO Auto-generated constructor stub
		id++;
		File file = new File(GROUP_PATH + String.valueOf(id));
		while (file.exists()) {
			id++;
			file = new File(GROUP_PATH + String.valueOf(id));
		}
		myId = id;
		this.groupName = groupName;
		persons = new Vector<Iperson>();
	}
	
	/**
	 * 通过已有的信息建立group
	 * @param myId group的id
	 * @param groupName group的名字
	 */
	public Igroup(int myId, String groupName, Vector<Iperson> persons) {
		this.myId = myId;
		this.groupName = groupName;
		this.persons = persons;
	}
	
	/**
	 * 用于表现层的显示
	 * <p>
	 * 表现层的GroupComponent组件中的显示区域是一个JTable，
	 * JTable中的每一行均是一个group，
	 * 此方法用于在JTable中显示group的名字
	 */
	public String toString() {
		return groupName;
	}
	
	public void rename(String groupName) {
		this.groupName = groupName;
	}
	
	/**
	 * 用id作为文件名，保存group的组名和成员
	 * <p>
	 * 文件的第一行为组名，
	 * 以后的每二行为一个成员id和成员姓名
	 * @throws IOException
	 */
	public void save() throws IOException {
		String temp = groupName;
		if (persons.size() != 0)
			for (Iperson p : persons) {
				temp += System.getProperty("line.separator");
				temp += p.getId();
				temp += System.getProperty("line.separator");
				temp += p.getName();
			}
		System.out.println("group saving");// /////////////////////////////////////////////
//		File file = new File(GROUP_PATH + String.valueOf(myId));
//		while (file.exists()) {
//			id++;
//			file = new File(GROUP_PATH + String.valueOf(id));
//		}
//		System.out.println("after ID++");// //////////////////////////////////////////////
		WriteFile wf = new WriteFile(GROUP_PATH + String.valueOf(myId), false);
		wf.write(temp);
		wf.close();
		wf = new WriteFile(GROUP_PATH + ALLGROUPS, true);
		ReadFile rf = new ReadFile(GROUP_PATH + ALLGROUPS);
		boolean f = false;
		while(true) {
			String str = rf.readLine();
			if(str == null || str.equals(System.getProperty("line.separator")))
				break;
			if(String.valueOf(myId).equals(str)) {
				f = true;
				break;
			}
		}
		if (!f)
			wf.write(myId + System.getProperty("line.separator"));
		wf.close();
	}

	public int getMyId() {
		return myId;
	}
}
