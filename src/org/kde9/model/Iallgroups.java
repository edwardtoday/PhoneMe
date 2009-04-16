package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashSet;

import org.kde9.util.Constants;

/**
 * ���ڱ�ʾAllGroups���࣬ʵ����AllGroups�ӿ�
 * <p>
 * ��װ�˶�AllGroups�Ļ���������
 * ����AllGroups�ĳ�ʼ�������棬ɾ�����޸ĵȡ�
 * <p>
 * ����ά��AllGroups�ļ��� 
 * �ļ��б����ҽ������������id��
 * ÿ��idռһ�У��ļ���β����һ���С�
 * ����ڶ�ALLGroups�ļ�ʱ���ַ����ֵ��л��������ԣ�
 * �����������������У�
 * ֱ�������ļ�β��
 */
class Iallgroups 
implements Constants, AllGroups {
	private LinkedHashSet<Integer> groupIds;
	WriteFile wf;
	ReadFile rf;

	/**
	 * ���췽��
	 * <p>
	 * ��AllGroups�ļ��ж�ȡ��Ϣ����һ��AllGroups����
	 * <p>
	 * ����ڶ�ALLGroups�ļ�ʱ���ַ����ֵ��л��������ԣ�
	 * �����������������У�
	 * ֱ�������ļ�β��
	 * 
	 * @throws FileNotFoundException 
	 * 		AllGroups�ļ������ڣ�Ӧ��������쳣��
	 * @throws IOException
	 * 		��δȷ��Ӱ��
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
	 * �ж�һ���ַ����Ƿ�Ϊ����
	 * <p>
	 * �ַ���Ϊ�մ�������ִ�������false��
	 * 
	 * @param str
	 * 		Ҫ�жϵ��ַ���
	 */
	private boolean isInt(String str) {
		// Ϊ�մ�����false
		if (str == "")
			return false;
		for (char c : str.toCharArray())
			// �����ַ���false
			if (!Character.isDigit(c))
				return false;
		return true;
	}

	/**
	 * ���ù��췽����
	 * ��AllGroups�ļ��ж�ȡ��Ϣ����һ��AllGroups����
	 * <p>
	 * ����ڶ�ALLGroups�ļ�ʱ���ַ����ֵ��л��������ԣ�
	 * �����������������У�
	 * ֱ�������ļ�β��
	 * 
	 * @throws FileNotFoundException 
	 * 		AllGroups�ļ������ڣ�Ӧ��������쳣��
	 * @throws IOException
	 * 		��δȷ��Ӱ��
	 */
	public static AllGroups createAllGroups() 
	throws FileNotFoundException, IOException {
		return new Iallgroups();
	}

	/**
	 * ���group
	 */
	public void appendGroup(int id) {
		groupIds.add(id);
	}

	/**
	 * ɾ��group
	 */
	public void deleteGroup(int id) {
		groupIds.remove(id);
	}

	public LinkedHashSet<Integer> getGroupIds() {
		return groupIds;
	}

	/**
	 * ����AllGroups����Ϣ����Ӧ���ļ���
	 * <p>
	 * ���������Ϊÿ�����id��ÿ��idΪһ�С� �ļ���β���л��з���
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
