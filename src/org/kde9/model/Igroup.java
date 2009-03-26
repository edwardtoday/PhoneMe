package org.kde9.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.kde9.util.Constants;

/**
 * ���ڱ�ʾgroup���࣬ʵ����Group�ӿ�
 * <p>
 * ��װ�˶�group�Ļ���������
 * ����group�ĳ�ʼ�������棬ɾ�����޸ĵȡ�
 * ����ά��group�ļ�
 */
class Igroup 
implements Constants, Group {
	static int staticId = 0;
	private int id;
	private String groupName;
	private HashMap<Integer, String> persons;

	/**
	 * ͨ�������½�һ��group����������ļ�
	 * ����������һ������֪group��id��ͬ��id��
	 * <br><strong>
	 * �÷���ֻ�����½��飬
	 * Ҫ���ļ��д�������ʹ�����������ķ���
	 * </strong></br>
	 * @param groupName group������
	 * @throws IOException 
	 */
	private Igroup(String groupName)
	throws IOException {
		//�ҵ�δʹ�õ�id
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
	 * ͨ�����е���Ϣ����group
	 * @param myId group��id
	 * @param groupName group������
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
	 * ͨ�����е���Ϣ����group
	 * @param id group��id
	 * @throws IOException ��id��������ļ������ڡ���
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
	 * ʹ����Ӧ�Ĺ��췽������group
	 * <p>
	 * ͨ�������½�һ��group����������ļ�
	 * ����������һ������֪group��id��ͬ��id��
	 * <br><strong>
	 * �÷���ֻ�����½��飬
	 * Ҫ���ļ��д�������ʹ�����������ķ���
	 * </strong></br>
	 * @param groupName group������
	 * @throws IOException 
	 */
	public static Group createGroup(String groupName)
	throws IOException {
		return new Igroup(groupName);
	}
	
	/**
	 * ʹ����Ӧ�Ĺ��췽������group
	 * <p>
	 * ͨ�����е���Ϣ����group
	 * @param myId group��id
	 * @param groupName group������
	 * @throws IOException 
	 */
	public static Group createGroup(
			int id,
			String groupName,
			HashMap<Integer, String> persons ) {
		return new Igroup(id, groupName, persons);
	}
	
	/**
	 * ʹ����Ӧ�Ĺ��췽������group
	 * <p>
	 * ͨ�����е���Ϣ����group
	 * @param id group��id
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
		
		//д��group�ļ�
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
