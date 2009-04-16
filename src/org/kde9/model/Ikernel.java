package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Vector;

import org.kde9.util.Constants;


public class Ikernel 
implements Constants{
	private AllGroups allGroups;
	private AllNames allNames;
	private LinkedHashMap<Integer, Group> groups;
	private RestoreAndBackup restoreAndBackup;
	private HashMap<Integer, Card> cards;
	
	/**
	 * 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 			�Ѿ�����
	 */
	public void init()
	throws IOException, FileNotFoundException {
		restoreAndBackup = Factory.createRestoreAndBackup();
		restoreAndBackup.checkout();
		allGroups = Factory.createAllGroups();
		cards = new HashMap<Integer, Card>();
		groups = new LinkedHashMap<Integer, Group>();
		if(allGroups.getGroupIds().size() != 0)
			for(int id : allGroups.getGroupIds())
				groups.put(id, Factory.createGroup(id));
	}
	
	/**
	 * �����������id������
	 * @return ������id��������LinkedHashMap
	 */
	public LinkedHashMap<Integer, String> getAllGroups() {
		LinkedHashMap<Integer, String> temp = new LinkedHashMap<Integer, String>();
		if(groups.size() != 0)
			for(int id : groups.keySet())
				temp.put(id, groups.get(id).getGroupName());
		return temp;
	}
	
	/**
	 * ͨ�����������Ա�½�һ����ͨ��
	 * <p>
	 * ����ͨ�����������Ա�½�һ��group����������ļ���
	 * ����������һ������֪group��id��ͬ��id��
	 * Ȼ����AllGroups����Ӹ����id�������档
	 * @param groupName �½��������
	 * @throws IOException
	 * @return �½����id 
	 */
	public int addGroup(String groupName, LinkedHashSet<Integer> memberIds)
	throws IOException {
		Group g = Factory.createGroup(groupName);
		int groupId = g.getGroupId();
		if(memberIds != null && memberIds.size() != 0)
			for(int i : memberIds)
				g.appendGroupMember(i);
		g.save();
		groups.put(groupId, g);
		allGroups.appendGroup(groupId);
		allGroups.save();
		return groupId;
	}
	
	/**
	 * ͨ�����������Ա�½�һ��������
	 * <p>
	 * ����ͨ�����������Ա�½�һ��group����������ļ���
	 * ����������һ������֪group��id��ͬ��id��
	 * Ȼ����AllGroups����Ӹ����id�������档
	 * @param groupName �½��������
	 * @throws IOException
	 * @return �½����id 
	 */
	public int addSmartGroup(String groupName, 
			int range, String item, String keywords, boolean isWholeWord)
	throws IOException {
		Group g = Factory.createGroup(groupName);
		int groupId = g.getGroupId();
		LinkedHashSet<Integer> memberIds = 
			search(range, item, keywords, isWholeWord);
		if(memberIds != null && memberIds.size() != 0)
			for(int i : memberIds)
				g.appendGroupMember(i);
		g.save();
		groups.put(groupId, g);
		allGroups.appendGroup(groupId);
		allGroups.save();
		return groupId;
	}
	
	/**
	 * ͨ�����idɾ����
	 * <p>
	 * ��AllGroups��ɾ����id��������AllGroups��
	 * Ȼ��ɾ��group�ڴ����ϵ��ļ���
	 * @param groupId ���id
	 * @throws IOException ����Ҫɾ�����ļ��Ѿ��������ˡ���
	 */
	public void deleteGroup(int groupId)
	throws IOException {
		allGroups.deleteGroup(groupId);
		allGroups.save();
		Group g = groups.get(groupId);
		if(g != null) {
			groups.remove(groupId);
			g.delete();
		}
		else {
			System.err.println("deleteGroup : δ�ҵ���Ӧid���飡");
		}
	}
	
	/**
	 * �Ը���id��ʾ�������������
	 * <p>
	 * �Ը���id��������������������档
	 * @param groupId Ҫ�����������id
	 * @param groupName ���������
	 * @throws IOException
	 */
	public void renameGroup(int groupId, String groupName)
	throws IOException {
		Group g = groups.get(groupId);
		if(g != null) {
			g.renameGroup(groupName);
			g.save();
		}
		else {
			System.err.println("renameGroup : δ�ҵ���Ӧid���飡");
		}
	}
	
	/**
	 * ���ظ���id����ĳ�Ա��id������
	 * @param groupId ���id
	 * @return ���ذ������Աid�����ֵ�LinkedHashMap
	 */
	public LinkedHashMap<Integer, String> getGroupMembers(int groupId) {
		LinkedHashMap<Integer, String> temp = new LinkedHashMap<Integer, String>();
		if(groups != null) {
			Group g = groups.get(groupId);
			if(g != null && g.getGroupMember().size() != 0) {
				for(int id : g.getGroupMember()) {
					temp.put(id, allNames.getNames().get(id));
				}
			}
		}
		return temp;
	}
	
	/**
	 * ������������һ�����Ա
	 * @param groupId Ҫ��ӳ�Ա�����id
	 * @param memberId Ҫ��ӵĳ�Ա��id
	 * @throws IOException
	 */
	public void addGroupMember(int groupId, int memberId) 
	throws IOException {
		Group g = groups.get(groupId);
		if(g != null) {
			g.appendGroupMember(memberId);
			g.save();
		}
		else {
			System.err.println("addGroupMembers : δ�ҵ���Ӧid���飡");
		}
	}
	
	/**
	 * ������������һ�����Ա
	 * @param groupId Ҫ��ӳ�Ա�����id
	 * @param memberIds Ҫ��ӵĳ�Ա��id
	 * @throws IOException
	 */
	public void addGroupMembers(int groupId, LinkedHashSet<Integer> memberIds) 
	throws IOException {
		Group g = groups.get(groupId);
		if(g != null) {
			if(memberIds != null)
				for(int memberId : memberIds)
					g.appendGroupMember(memberId);
			g.save();
		}
		else {
			System.err.println("addGroupMembers : δ�ҵ���Ӧid���飡");
		}
	}
	
	public void deleteGroupMember(int groupId, int memberId) 
	throws IOException {
		Group g = groups.get(groupId);
		if(g != null) {
			g.deleteGroupMember(memberId);
			g.save();
		}
		else {
			System.err.println("deleteGroupMember : δ�ҵ���Ӧid���飡");
		}
	}
	
	public void deleteGroupMember(int groupId, HashSet<Integer> memberIds) 
	throws IOException {
		Group g = groups.get(groupId);
		if(g != null) {
			if(memberIds != null)
				for(int memberId : memberIds)
					g.deleteGroupMember(memberId);
			g.save();
		}
		else {
			System.err.println("deleteGroupMember : δ�ҵ���Ӧid���飡");
		}
	}
	
	public LinkedHashSet<Integer> search(int groupId, 
			String item, String keywords, boolean isWholeWord) 
	throws IOException {
		LinkedHashSet<Integer> temp = new LinkedHashSet<Integer>();
		
		StringReader sr = new StringReader(keywords);
		Vector<String> v = new Vector<String>();
		int c = sr.read();
		while(c != -1) {
			String str = new String();
			while(c != -1 && (char)c != ' ') {
				str += (char)c;
				c = sr.read();
			}
			v.add(str);
			c = sr.read();
		}
		
//		for(int i = 0; i < v.size(); i++)
//			System.out.println(v.get(i));//////////////////////////////////////////
		
		Group g = groups.get(groupId);
		if(g != null) {
			LinkedHashSet<Integer> memberIds = g.getGroupMember();
			if(memberIds != null)
				outer:
				for(int id : memberIds) {
					Card card = cards.get(id);
					if(card == null) {
						try {
							card = Factory.createCard(id);
							cards.put(id, card);
						} catch (FileNotFoundException e) {
							System.err.println("search��card�ļ�" + id + "δ�ҵ���");
							continue outer;
						}
					}
					System.out.println("name:" + card.getName());/////////////////////////////
					for(int i = 0; i < v.size(); i++)
						if(!card.find(item, v.get(i), isWholeWord))
							continue outer;
					temp.add(id);
				}
		}
		return temp;
	}
	
	
	/*
	 * test!
	 */
	public static void main(String args[]) {
		Ikernel ikernel = new Ikernel();
		try {
			ikernel.init();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			LinkedHashSet<Integer> l = ikernel.search(0, "tel2", "23 34567", false);
			for(int id : l)
				System.out.println(id);
			System.out.println(l.size());
			ikernel.addSmartGroup("��62��", 0, "tel2", "aa bb", false);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
