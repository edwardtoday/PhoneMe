package org.kde9.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashSet;

import org.kde9.util.Constants;


/**
 * ���ڱ�ʾgroup���࣬ʵ����Group�ӿ�
 * <p>
 * ��װ�˶�group�Ļ���������
 * ����group�ĳ�ʼ�������棬ɾ�����޸ĵȡ�
 * <p>
 * ����ά��group�ļ���
 * �ļ��б����ҽ�����������ֺ����Ա��id��
 * �ļ��ĵ�һ��Ϊ������֣�
 * ������ÿһ��Ϊһ�����Ա��id��
 * ������һ�п��С�
 * ����ڶ�group�ļ�ʱ��
 * ������ʱ���ֿ�����������ΪĬ��ֵ��
 * �����Աʱ���ַ����ֵ��л��������ԣ�
 * �����������������У�
 * ֱ�������ļ�β��
 */
class Igroup 
implements Constants, Group {
	static int staticId = 0;
	private int groupId;
	private String groupName;
	private LinkedHashSet<Integer> groupMembers;
	
	/**
	 * ͨ�������½�һ��group����������ļ�
	 * ����������һ������֪group��id��ͬ��id��
	 * <br><strong>
	 * �÷���ֻ�����½��飬
	 * Ҫ���ļ��д�������ʹ��int�����ķ���
	 * </strong></br>
	 * @param groupName 
	 * 		group������
	 * @throws IOException 
	 * 		��δȷ��Ӱ�� 
	 */
	private Igroup(String groupName)
	throws IOException {
		//�ҵ�δʹ�õ�id
		File file = new File(GROUP_PATH + String.valueOf(staticId));
		while (file.exists()) {
			staticId++;
			file = new File(GROUP_PATH + String.valueOf(staticId));
		}
		groupId = staticId++;
		this.groupName = groupName;
		groupMembers = new LinkedHashSet<Integer>();
		this.save();
	}
	
	/**
	 * ͨ�����е���Ϣ����group
	 * 
	 * @param id 
	 * 		group��id
	 * @throws FileNotFoundException
	 * 		��id������ļ������ڣ�Ӧ������쳣��
	 * @throws IOException
	 * 		��δȷ��Ӱ��
	 */
	private Igroup(int groupId) 
	throws FileNotFoundException, IOException {
		ReadFile rf = new ReadFile(GROUP_PATH + String.valueOf(groupId));
		this.groupId = groupId;
		groupName = rf.readLine();
		if(groupName == null || groupName == "")
			groupName = NULLGROUPNAME;
		groupMembers = new LinkedHashSet<Integer>();
		while(true) {
			String tempId = rf.readLine();
			if(tempId == null)
				break;
			else if(isInt(tempId))
				groupMembers.add(Integer.valueOf(tempId));
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
	 * ͨ�������½�һ��group����������ļ�
	 * ����������һ������֪group��id��ͬ��id��
	 * <br><strong>
	 * �÷���ֻ�����½��飬
	 * Ҫ���ļ��д�������ʹ��int�����ķ���
	 * </strong></br>
	 * @param groupName 
	 * 		group������
	 * @throws IOException 
	 * 		��δȷ��Ӱ�� 
	 */
	static Group createGroup(String groupName) 
	throws IOException {
		return new Igroup(groupName); 
	}
	
	/**
	 * ͨ�����е���Ϣ����group
	 * 
	 * @param id 
	 * 		group��id
	 * @throws FileNotFoundException
	 * 		��id������ļ������ڣ�Ӧ������쳣��
	 * @throws IOException
	 * 		��δȷ��Ӱ��
	 */
	static Group createGroup(int id) 
	throws FileNotFoundException, IOException {
		return new Igroup(id);
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public int getGroupId() {
		return groupId;
	}
	
	public void appendGroupMember(int personId) {
		groupMembers.add(personId);
	}
	
	public void deleteGroupMember(int personId) {
		groupMembers.remove(personId);
	}
	
	public void renameGroup(String groupName) {
		this.groupName = groupName;
	}
	
	public void save()
	throws IOException {
		String temp = groupName;
		temp += NEWLINE;
		if(groupMembers.size() != 0)
			for (int i : groupMembers) {
				temp += i;
				temp += NEWLINE;
			}
		System.out.println("group saving");// /////////////////////////////////////////////
		
		//д��group�ļ�
		WriteFile wf = new WriteFile(GROUP_PATH + String.valueOf(groupId), false);
		wf.write(temp);
		wf.close();
	}
	
	public void delete() {
		DeleteFile df = new DeleteFile(GROUP_PATH + String.valueOf(groupId));
		df.delete();
	}

	public LinkedHashSet<Integer> getGroupMember() {
		return groupMembers;
	}
}

