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

	public Controller() {
		try {
			allGroupsController = new AllGroupsController();
			allNamesController = new AllNamesController();
			cardController = new CardController();
			groupController = new GroupController(
					allGroupsController.getGroupIds());
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	/**
	 * 返回所有组的id和组名
	 * @return 包含组id和组名的LinkedHashMap
	 */
	public LinkedHashMap<Integer, String> getAllGroups() {
		return groupController.getAllGroupNames();
	}
	
	/**
	 * 通过组名和组成员新建一个普通组
	 * <p>
	 * 首先通过组名和组成员新建一个group，并保存成文件，
	 * 并给它赋予一个与已知group的id不同的id。
	 * 然后向AllGroups中添加该组的id，并保存。
	 * @param groupName 新建组的组名
	 * @throws IOException
	 * @return 新建组的id 
	 */
	public int addGroup(String groupName, int[] memberIds)
	throws IOException {
		int id = groupController.addGroup(groupName);
		groupController.appendGroupMember(id, memberIds);
		return id;
	}
	
	/**
	 * 通过组名和组成员新建一个智能组
	 * <p>
	 * 首先通过组名和组成员新建一个group，并保存成文件，
	 * 并给它赋予一个与已知group的id不同的id。
	 * 然后向AllGroups中添加该组的id，并保存。
	 * @param groupName 新建组的组名
	 * @throws IOException
	 * @return 新建组的id 
	 */
	public int addSmartGroup(String groupName, 
			int range, HashMap<String, String> keywords, boolean isWholeWord)
	throws IOException {
		int groupId = groupController.addGroup(groupName);
		int[] memberIds = search(range, keywords, isWholeWord);
		groupController.appendGroupMember(groupId, memberIds);
		allGroupsController.appendGroup(groupId);
		return groupId;
	}
	
	/**
	 * 通过组的id删除组
	 * <p>
	 * 从AllGroups中删除该id，并保存AllGroups。
	 * 然后删除group在磁盘上的文件。
	 * @param groupId 组的id
	 * @throws IOException 可能要删除的文件已经不存在了……
	 */
	public void deleteGroup(int groupId)
	throws IOException {
		if(groupId != ALLIDINT) {
			allGroupsController.deleteGroup(groupId);
			groupController.deleteGroup(groupId);
		}
	}
	
	/**
	 * 对给定id表示的组进行重命名
	 * <p>
	 * 对给定id的组进行重命名，并保存。
	 * @param groupId 要重命名的组的id
	 * @param groupName 组的新名字
	 * @throws IOException
	 */
	public void renameGroup(int groupId, String groupName)
	throws IOException {
		groupController.renameGroup(groupId, groupName);
	}
	
	/**
	 * 返回给定id的组的成员的id和名字
	 * @param groupId 组的id
	 * @return 返回包含组成员id和名字的LinkedHashMap
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
	 * 向给定组中添加一个组成员
	 * @param groupId 要添加成员的组的id
	 * @param memberId 要添加的成员的id
	 * @throws IOException
	 */
	public void addGroupMember(int groupId, int memberId) 
	throws IOException {
		groupController.appendGroupMember(groupId, new int[]{memberId});
	}
	
	/**
	 * 向给定组中添加一堆组成员
	 * @param groupId 要添加成员的组的id
	 * @param memberIds 要添加的成员的id
	 * @throws IOException
	 */
	public void addGroupMembers(int groupId, int[] memberIds) 
	throws IOException {
		groupController.appendGroupMember(groupId, memberIds);
	}
	
	public void deleteGroupMember(int groupId, int memberId) 
	throws IOException {
		groupController.deleteGroupMember(groupId, new int[]{memberId});
	}
	
	public void deleteGroupMember(int groupId, int[] memberIds) 
	throws IOException {
		groupController.deleteGroupMember(groupId, memberIds);
	}
	
	public int addCard(int range, String name, LinkedHashMap<String, String> items) 
	throws IOException {
		InterfaceCard card = Factory.createCard(name);
		int id = card.getCardId();
		cards.put(id, card);
		allNames.appendPerson(id, name);
		if(items != null)
			for(String str : items.keySet())
				card.appendItem(str, items.get(str));
		allNames.save();
		card.save();
		InterfaceGroup group = groups.get(ALLIDINT);
		group.appendGroupMember(id);
		group.save();
		if(range != ALLIDINT) {
			group = groups.get(range);
			group.appendGroupMember(id);
			group.save();
		}
		return id;
	}
	
	public void deleteCard(int id) 
	throws FileNotFoundException, IOException {
		InterfaceCard card = cards.get(id);
		if(card == null)
			card = Factory.createCard(id);
		else
			cards.remove(id);
		card.delete();
		allNames.deletePerson(id);
		allNames.save();
		for(InterfaceGroup group : groups.values()) {
			if(group.getGroupMember().contains(id)) {
				group.deleteGroupMember(id);
				group.save();
			}
		}
	}
	
	public void renameCard(int id, String name) 
	throws FileNotFoundException, IOException {
		InterfaceCard card = cards.get(id);
		if(card == null) {
			card = Factory.createCard(id);
			cards.put(id, card);
		}
		card.rename(name);
		allNames.appendPerson(id, name);
		card.save();
		allNames.save();
	}
	
	public void addCardItem(int id, String name, String content) 
	throws FileNotFoundException, IOException {
		InterfaceCard card = cards.get(id);
		if(card == null) {
			card = Factory.createCard(id);
			cards.put(id, card);
		}
		card.appendItem(name, content);
		card.save();
	}
	
	public void deleteCardItem(int id, String name) 
	throws FileNotFoundException, IOException {
		InterfaceCard card = cards.get(id);
		if(card == null) {
			card = Factory.createCard(id);
			cards.put(id, card);
		}
		card.deleteItem(name);
		card.save();
	}
	
	public void renameCardItem(int id, String oldName, String newName) 
	throws FileNotFoundException, IOException {
		InterfaceCard card = cards.get(id);
		if(card == null) {
			card = Factory.createCard(id);
			cards.put(id, card);
		}
		card.renameItem(oldName, newName);
		card.save();
	}
	
	public LinkedHashMap<String, String> getCardItem(int id) 
	throws FileNotFoundException, IOException {
		LinkedHashMap<String, String> l = new LinkedHashMap<String, String>();
		InterfaceCard card = cards.get(id);
		if(card == null) {
			card = Factory.createCard(id);
			cards.put(id, card);
		}
		for(String str : card.getItems().keySet())
			l.put(str, card.getItems().get(str));
		return l;
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
		
		InterfaceGroup g = groups.get(groupId);
		if(g != null) {
			LinkedHashSet<Integer> memberIds = g.getGroupMember();
			if(memberIds != null)
				outer:
				for(int id : memberIds) {
					InterfaceCard card = cards.get(id);
					if(card == null) {
						try {
							card = Factory.createCard(id);
							cards.put(id, card);
						} catch (FileNotFoundException e) {
							System.err.println("search：card文件" + id + "未找到！");
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
			System.out.println(ikernel.getAllGroups().size());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public Object getChild(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getChildCount(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getIndexOfChild(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isLeaf(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void valueForPathChanged(TreePath arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
