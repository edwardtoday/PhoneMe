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
	 * 			已经处理
	 */
	public void init()
	throws IOException, FileNotFoundException {
		restoreAndBackup = Factory.createRestoreAndBackup();
		restoreAndBackup.checkout();
		allGroups = Factory.createAllGroups();
		allNames = Factory.createAllNames();
		cards = new HashMap<Integer, Card>();
		groups = new LinkedHashMap<Integer, Group>();
		if(allGroups.getGroupIds().size() != 0)
			for(int id : allGroups.getGroupIds())
				groups.put(id, Factory.createGroup(id));
	}
	
	/**
	 * 返回所有组的id和组名
	 * @return 包含组id和组名的LinkedHashMap
	 */
	public LinkedHashMap<Integer, String> getAllGroups() {
		LinkedHashMap<Integer, String> temp = new LinkedHashMap<Integer, String>();
		if(groups.size() != 0)
			for(int id : groups.keySet())
				temp.put(id, groups.get(id).getGroupName());
		return temp;
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
	 * 通过组的id删除组
	 * <p>
	 * 从AllGroups中删除该id，并保存AllGroups。
	 * 然后删除group在磁盘上的文件。
	 * @param groupId 组的id
	 * @throws IOException 可能要删除的文件已经不存在了……
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
			System.err.println("deleteGroup : 未找到相应id的组！");
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
		Group g = groups.get(groupId);
		if(g != null) {
			g.renameGroup(groupName);
			g.save();
		}
		else {
			System.err.println("renameGroup : 未找到相应id的组！");
		}
	}
	
	/**
	 * 返回给定id的组的成员的id和名字
	 * @param groupId 组的id
	 * @return 返回包含组成员id和名字的LinkedHashMap
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
	 * 向给定组中添加一个组成员
	 * @param groupId 要添加成员的组的id
	 * @param memberId 要添加的成员的id
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
			System.err.println("addGroupMembers : 未找到相应id的组！");
		}
	}
	
	/**
	 * 向给定组中添加一堆组成员
	 * @param groupId 要添加成员的组的id
	 * @param memberIds 要添加的成员的id
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
			System.err.println("addGroupMembers : 未找到相应id的组！");
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
			System.err.println("deleteGroupMember : 未找到相应id的组！");
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
			System.err.println("deleteGroupMember : 未找到相应id的组！");
		}
	}
	
	public int addCard(int range, String name, LinkedHashMap<String, String> items) 
	throws IOException {
		Card card = Factory.createCard(name);
		int id = card.getCardId();
		cards.put(id, card);
		allNames.appendPerson(id, name);
		if(items != null)
			for(String str : items.keySet())
				card.appendItem(str, items.get(str));
		allNames.save();
		card.save();
		Group group = groups.get(ALLIDINT);
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
		Card card = cards.get(id);
		if(card == null)
			card = Factory.createCard(id);
		else
			cards.remove(id);
		card.delete();
		allNames.deletePerson(id);
		allNames.save();
		for(Group group : groups.values()) {
			if(group.getGroupMember().contains(id)) {
				group.deleteGroupMember(id);
				group.save();
			}
		}
	}
	
	public void renameCard(int id, String name) 
	throws FileNotFoundException, IOException {
		Card card = cards.get(id);
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
		Card card = cards.get(id);
		if(card == null) {
			card = Factory.createCard(id);
			cards.put(id, card);
		}
		card.appendItem(name, content);
		card.save();
	}
	
	public void deleteCardItem(int id, String name) 
	throws FileNotFoundException, IOException {
		Card card = cards.get(id);
		if(card == null) {
			card = Factory.createCard(id);
			cards.put(id, card);
		}
		card.deleteItem(name);
		card.save();
	}
	
	public void renameCardItem(int id, String oldName, String newName) 
	throws FileNotFoundException, IOException {
		Card card = cards.get(id);
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
		Card card = cards.get(id);
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
}
