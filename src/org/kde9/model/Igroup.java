package org.kde9.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.kde9.util.Constants;

/**
 * 用于表示group的类，实现了Group接口
 * <p>
 * 封装了对group的基本操作，
 * 包括group的初始化、保存，删除、修改等。
 * 负责维护group文件
 */
class Igroup 
implements Constants, Group {
	static int staticId = 0;
	private int id;
	private String groupName;
	private HashMap<Integer, String> persons;

	/**
	 * 通过组名新建一个group，并保存成文件
	 * 并给它赋予一个与已知group的id不同的id。
	 * <br><strong>
	 * 该方法只用于新建组，
	 * 要从文件中创建组请使用三个参数的方法
	 * </strong></br>
	 * @param groupName group的名字
	 * @throws IOException 
	 */
	private Igroup(String groupName)
	throws IOException {
		//找到未使用的id
		File file = new File(GROUP_PATH + String.valueOf(staticId));
		while (file.exists()) {
			staticId++;
			file = new File(GROUP_PATH + String.valueOf(staticId));
		}
		
		id = staticId++;
		this.groupName = groupName;
		persons = new HashMap<Integer, String>();
		this.save();
	}
	
	/**
	 * 通过已有的信息建立group
	 * @param myId group的id
	 * @param groupName group的名字
	 * @throws IOException 
	 */
	private Igroup(
			int id, 
			String groupName, 
			HashMap<Integer, String> persons ) {
		this.id = id;
		this.groupName = groupName;
		this.persons = persons;
	}
	
	/**
	 * 通过已有的信息建立group
	 * @param id group的id
	 * @throws IOException 该id所代表的文件不存在……
	 */
	private Igroup(int id) 
	throws IOException {
		ReadFile rf = new ReadFile(GROUP_PATH + String.valueOf(id));
		this.id = id;
		groupName = rf.readLine();
		persons = new HashMap<Integer, String>();
		while(true) {
			String tempId = rf.readLine();
			String tempName = rf.readLine();
			if(tempId == null 
					|| tempId == System.getProperty("line.separator"))
				break;
			else {
				persons.put(Integer.valueOf(tempId), tempName);
			}
		}
	}

	/**
	 * 使用相应的构造方法构造group
	 * <p>
	 * 通过组名新建一个group，并保存成文件
	 * 并给它赋予一个与已知group的id不同的id。
	 * <br><strong>
	 * 该方法只用于新建组，
	 * 要从文件中创建组请使用三个参数的方法
	 * </strong></br>
	 * @param groupName group的名字
	 * @throws IOException 
	 */
	public static Group createGroup(String groupName)
	throws IOException {
		return new Igroup(groupName);
	}
	
	/**
	 * 使用相应的构造方法构造group
	 * <p>
	 * 通过已有的信息建立group
	 * @param myId group的id
	 * @param groupName group的名字
	 * @throws IOException 
	 */
	public static Group createGroup(
			int id,
			String groupName,
			HashMap<Integer, String> persons ) {
		return new Igroup(id, groupName, persons);
	}
	
	/**
	 * 使用相应的构造方法构造group
	 * <p>
	 * 通过已有的信息建立group
	 * @param id group的id
	 * @throws IOException 
	 * @throws IOException
	 */
	public static Group createGroup(int id) throws IOException {
		return new Igroup(id);
	}
		
	public String getGroupName() {
		return groupName;
	}
	
	public int getGroupId() {
		return id;
	}
	
	public void renamePerson(int personId, String name) {
		persons.put(personId, name);
	}
	
	public void appendPerson(int personId, String name) {
		persons.put(personId, name);
	}
	
	public void deletePerson(int personId) {
		persons.remove(personId);
	}
	
	public void rename(String groupName) {
		this.groupName = groupName;
	}
	
	public void save()
	throws IOException {
		String temp = groupName;
		if(persons.size() != 0)
			for (int i : persons.keySet()) {
				temp += System.getProperty("line.separator");
				temp += i;
				temp += System.getProperty("line.separator");
				temp += persons.get(i);
			}
		System.out.println("group saving");// /////////////////////////////////////////////
		
		//写入group文件
		WriteFile wf = new WriteFile(GROUP_PATH + String.valueOf(id), false);
		wf.write(temp);
		wf.close();
	}
	
	public void delete() {
		DeleteFile df = new DeleteFile(GROUP_PATH + String.valueOf(id));
		df.delete();
	}

	public HashMap<Integer, String> getPersons() {
		return persons;
	}

}
