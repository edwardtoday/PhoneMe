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
	 * �½�һ��group��
	 * ����������һ������֪group��id��ͬ��id
	 * @param groupName group������
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
	 * ͨ�����е���Ϣ����group
	 * @param myId group��id
	 * @param groupName group������
	 */
	public Igroup(int myId, String groupName, Vector<Iperson> persons) {
		this.myId = myId;
		this.groupName = groupName;
		this.persons = persons;
	}
	
	/**
	 * ���ڱ��ֲ����ʾ
	 * <p>
	 * ���ֲ��GroupComponent����е���ʾ������һ��JTable��
	 * JTable�е�ÿһ�о���һ��group��
	 * �˷���������JTable����ʾgroup������
	 */
	public String toString() {
		return groupName;
	}
	
	public void rename(String groupName) {
		this.groupName = groupName;
	}
	
	/**
	 * ��id��Ϊ�ļ���������group�������ͳ�Ա
	 * <p>
	 * �ļ��ĵ�һ��Ϊ������
	 * �Ժ��ÿ����Ϊһ����Աid�ͳ�Ա����
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
