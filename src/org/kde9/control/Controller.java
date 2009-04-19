package org.kde9.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Vector;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.kde9.util.Constants;


public class Controller
implements Constants, TreeModel{
	private AllGroupsController allGroupsController;
	private AllNamesController allNamesController;
	private CardController cardController;
	private GroupController groupController;
	private TreeNode root;

	public Controller() {
		try {
			allGroupsController = new AllGroupsController();
			allNamesController = new AllNamesController();
			cardController = new CardController();
			groupController = new GroupController(
					allGroupsController.getGroupIds());
			root = new TreeNode();
			root.setType("AllGroups");
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	/**
	 * �����������id������
	 * @return ������id��������LinkedHashMap
	 */
	public LinkedHashMap<Integer, String> getAllGroups() {
		return groupController.getAllGroupNames();
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
		int id = groupController.addGroup(groupName);
		groupController.appendGroupMembers(id, memberIds);
		return id;
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
			int range, Vector<String> items, Vector<String> keywords, 
			boolean isWholeWord)
	throws IOException {
		int groupId = groupController.addGroup(groupName);
		LinkedHashSet<Integer> memberIds = search(range, items, keywords, isWholeWord);
		groupController.appendGroupMembers(groupId, memberIds);
		allGroupsController.appendGroup(groupId);
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
		if(groupId != ALLIDINT) {
			allGroupsController.deleteGroup(groupId);
			groupController.deleteGroup(groupId);
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
		groupController.renameGroup(groupId, groupName);
	}
	
	/**
	 * ���ظ���id����ĳ�Ա��id������
	 * @param groupId ���id
	 * @return ���ذ������Աid�����ֵ�LinkedHashMap
	 */
	public LinkedHashMap<Integer, String> getGroupMembers(int groupId) {
		LinkedHashMap<Integer, String> temp = new LinkedHashMap<Integer, String>();
		LinkedHashSet<Integer> members = groupController.getGroupMember(groupId);
		for(int id : members) {
			String name = allNamesController.getNames(id);
			if(name != null)
				temp.put(id, name);
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
		groupController.appendGroupMember(groupId, memberId);
	}
	
	/**
	 * ������������һ�����Ա
	 * @param groupId Ҫ��ӳ�Ա�����id
	 * @param memberIds Ҫ��ӵĳ�Ա��id
	 * @throws IOException
	 */
	public void addGroupMembers(int groupId, LinkedHashSet<Integer> memberIds) 
	throws IOException {
		groupController.appendGroupMembers(groupId, memberIds);
	}
	
	public void deleteGroupMember(int groupId, int memberId) 
	throws IOException {
		groupController.deleteGroupMember(groupId, new int[]{memberId});
	}
	
	public void deleteGroupMember(int groupId, int[] memberIds) 
	throws IOException {
		groupController.deleteGroupMember(groupId, memberIds);
	}
	
	public int addCard(int groupId, String name, LinkedHashMap<String, String> items) 
	throws IOException {
		int id = cardController.addCard(name, items);
		allNamesController.appendPerson(id, name);
		return id;
	}
	
	public void deleteCard(int id) 
	throws IOException {
		try {
			cardController.deleteCard(id);
			allNamesController.deletePerson(id);
			groupController.deleteCard(id);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void renameCard(int id, String name) 
	throws IOException {
		cardController.renameCard(id, name);
	}
	
	public void addCardItem(int id, String name, String content) 
	throws IOException {
		cardController.addCardItem(id, name, content);
	}
	
	public void deleteCardItem(int id, String name) 
	throws IOException {
		cardController.deleteCardItem(id, name);
	}
	
	public void renameCardItem(int id, String oldName, String newName) 
	throws IOException {
		cardController.renameCardItem(id, oldName, newName);
	}
	
	public LinkedHashMap<String, String> getCardItem(int id) 
	throws IOException {
		return cardController.getCardItem(id);
	}
	
	public LinkedHashSet<Integer> search(int groupId, 
			Vector<String> items, Vector<String> keywords, boolean isWholeWord) 
	throws IOException {
		LinkedHashSet<Integer> temp = new LinkedHashSet<Integer>();
//		
//		StringReader sr = new StringReader(keywords);
//		Vector<String> v = new Vector<String>();
//		int c = sr.read();
//		while(c != -1) {
//			String str = new String();
//			while(c != -1 && (char)c != ' ') {
//				str += (char)c;
//				c = sr.read();
//			}
//			v.add(str);
//			c = sr.read();
//		}
//		
////		for(int i = 0; i < v.size(); i++)
////			System.out.println(v.get(i));//////////////////////////////////////////
//		
//		InterfaceGroup g = groups.get(groupId);
//		if(g != null) {
//			LinkedHashSet<Integer> memberIds = g.getGroupMember();
//			if(memberIds != null)
//				outer:
//				for(int id : memberIds) {
//					InterfaceCard card = cards.get(id);
//					if(card == null) {
//						try {
//							card = Factory.createCard(id);
//							cards.put(id, card);
//						} catch (FileNotFoundException e) {
//							System.err.println("search��card�ļ�" + id + "δ�ҵ���");
//							continue outer;
//						}
//					}
//					System.out.println("name:" + card.getName());/////////////////////////////
//					for(int i = 0; i < v.size(); i++)
//						if(!card.find(item, v.get(i), isWholeWord))
//							continue outer;
//					temp.add(id);
//				}
//		}
		return temp;
	}
	

	
	
	public void addTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public Object getChild(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		TreeNode TreeNode = (TreeNode)arg0;
		LinkedHashMap<Integer, String> l;
		LinkedHashMap<String, String> s = new LinkedHashMap<String, String>();
		if(TreeNode.getType().equals("Group")) {
			l = getGroupMembers(TreeNode.getId());
			TreeNode child = new TreeNode();
			child.setType("Member");
			child.setId((Integer) l.keySet().toArray()[arg1]);
			child.setName(l.get(l.keySet().toArray()[arg1]));
			return child;
		}
		else if(TreeNode.getType().equals("AllGroups")) {
			l = getAllGroups();
			TreeNode child = new TreeNode();
			child.setType("Group");
			child.setId((Integer) l.keySet().toArray()[arg1]);
			child.setName(l.get(l.keySet().toArray()[arg1]));
			return child;
		}
		else {
			try {
				s = getCardItem(TreeNode.getId());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TreeNode child = new TreeNode();
			child.setType("Item");
			child.setName((String) s.keySet().toArray()[arg1]);
			child.setContent(s.get(s.keySet().toArray()[arg1]));
			return child;
		}
	}

	public int getChildCount(Object arg0) {
		TreeNode TreeNode = (TreeNode)arg0;
		if(TreeNode.getType().equals("Group")) {	
			return getGroupMembers(TreeNode.getId()).size();
		}
		else if(TreeNode.getType().equals("AllGroups")) {
			return getAllGroups().size();
		}
		else {
			try {
				return getCardItem(TreeNode.getId()).size();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	public int getIndexOfChild(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getRoot() {
		// TODO Auto-generated method stub
		return root;
	}

	public boolean isLeaf(Object arg0) {
		// TODO Auto-generated method stub
		if(((TreeNode)arg0).getType().equals("Item"))
			return true;
		return false;
	}

	public void removeTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void valueForPathChanged(TreePath arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
