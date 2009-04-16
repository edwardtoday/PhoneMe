package org.kde9.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.kde9.util.Constants;

class Icard 
implements Constants, Card {
	private static int staticId = 0;
	private int cardId;
	private String name;
	private LinkedHashMap<String, String> items;
	private HashMap<Integer, String> relationship;
	
	private Icard(String name) 
	throws FileNotFoundException, IOException {
		File file = new File(CARD_PATH + String.valueOf(staticId));
		while (file.exists()) {
			staticId++;
			file = new File(CARD_PATH + String.valueOf(staticId));
		}
		
		cardId = staticId++;
		this.name = name;
		items = new LinkedHashMap<String, String>();
		relationship = new HashMap<Integer, String>();
	}
	
	private Icard(int cardId) 
	throws FileNotFoundException, IOException {
		ReadFile rf = new ReadFile(CARD_PATH + String.valueOf(cardId));
		name = rf.readLine();
		if (name == null || name.length() == 0)
			name = NULLCARDNAME;
		items = new LinkedHashMap<String, String>();
		String str1 = rf.readLine();
		String str2;
		while(str1 != null &&
				!str1.equals(SEPERATOR)) {
			if(str1.equals("")) {
				str1 = rf.readLine();
				continue;
			}
			str2 = rf.readLine();
			if(str2 == null)
				str2 = NULLITEMCONTENT;
			String str2add = rf.readLine();
			while(str2add != null && 
					!str2add.equals(ITEMSEPERATOR) &&
					!str2add.equals(SEPERATOR)) {
				str2 += NEWLINE;
				str2 += str2add;
				str2add = rf.readLine();
			}
			if(str2add.equals(SEPERATOR))
				break;
			items.put(str1, str2);
			str1 = rf.readLine();
		}
		relationship = new HashMap<Integer, String>();
		while(true) {
			str1 = rf.readLine();
			str2 = rf.readLine();
			if(str1 == null)
				break;
			else if(isInt(str1)) {
				if(str2 == null)
					str2 = HIDEPERSON;
				relationship.put(Integer.valueOf(str1), str2);
			}
			else
				continue;
		}
		rf.close();
	}
	
	/**
	 * 判断一个字符串是否为数字
	 * <p>
	 * 字符串为空串或非数字串将返回false。
	 * 
	 * @param str
	 * 		要判断的字符串
	 */
	private boolean isInt(String str) {
		// 为空串返回false
		if (str.length() == 0)
			return false;
		for (char c : str.toCharArray())
			// 非数字返回false
			if (!Character.isDigit(c))
				return false;
		return true;
	}
	
	public static Card createCard(String name) 
	throws FileNotFoundException, IOException {
		return new Icard(name);
	}
	
	public static Card createCard(int id) 
	throws FileNotFoundException, IOException {
		return new Icard(id);
	}
	
	/* (non-Javadoc)
	 * @see org.kde9.model.Card#rename(java.lang.String)
	 */
	public void rename(String name) {
		this.name = name;
		
	}
	
	/* (non-Javadoc)
	 * @see org.kde9.model.Card#appendItem(java.lang.String, java.lang.String)
	 */
	public void appendItem(String name, String content) {
		items.put(name, content);
	}
	
	/* (non-Javadoc)
	 * @see org.kde9.model.Card#deleteItem(java.lang.String)
	 */
	public void deleteItem(String name) {
		items.remove(name);
	}
	
	/* (non-Javadoc)
	 * @see org.kde9.model.Card#renameItem(java.lang.String, java.lang.String)
	 */
	public void renameItem(String oldName, String newName) {
		String content = items.get(oldName);
		items.remove(oldName);
		items.put(newName, content);
	}
	
	/* (non-Javadoc)
	 * @see org.kde9.model.Card#appendPerson(int, java.lang.String)
	 */
	public void appendPerson(int cardId, String name) {
		relationship.put(cardId, name);
	}
	
	public void deletePerson(int cardId) {
		relationship.remove(cardId);
	}
	
	/**
	 * 查找函数
	 * @param item
	 * 		要查找的项，如果为空表示查找所有项
	 * @param content
	 * 		要查找的项的内容
	 * @param wholeWord
	 * 		是否全字符匹配，true表示全字符匹配
	 * @return
	 * 		若找到符合条件的项返回true，否则返回false
	 */
	public boolean find(String item, String content, boolean wholeWord) {
		if(item == null) {
			if(items.size() != 0)
				for(String temp : items.values())
					if(wholeWord) {
						if(temp.equals(content))
							return true;
					} else {
						if(temp.contains(content))
							return true;
					}
		}
		else if(wholeWord) {
			String temp = items.get(item);
			if(temp != null)
				return temp.equals(content);
		}
		else {
			String temp = items.get(item);
			System.out.println(temp + '!');/////////////////////////////////////////
			if(temp != null)
				return temp.contains(content);
		}
		return false;
	}
	
	public int getCardId() {
		return cardId;
	}

	public String getName() {
		return name;
	}

	public LinkedHashMap<String, String> getItems() {
		return items;
	}

	public HashMap<Integer, String> getRelationship() {
		return relationship;
	}

	public void save() 
	throws IOException {
		WriteFile wf = new WriteFile(CARD_PATH + String.valueOf(cardId), false);
		String temp = name;
		//System.out.println("sdsdadsd");
		temp += NEWLINE;
		if(items.size() != 0)
			for(String s : items.keySet()) {
				temp += s;
				temp += NEWLINE;
				temp += items.get(s);
				temp += NEWLINE;
				temp += ITEMSEPERATOR;
				temp += NEWLINE;
			}
		temp += SEPERATOR;
		temp += NEWLINE;
		if(relationship.size() != 0)
			for(int i : relationship.keySet()) {
				temp += i;
				temp += NEWLINE;
				temp += relationship.get(i);
				temp += NEWLINE;
			}
		wf.write(temp);
		wf.close();
	}
	
	public void delete() {
		DeleteFile df = new DeleteFile(CARD_PATH + String.valueOf(cardId));
		df.delete();
	}

	
	public static void main(String args[]) {
		try {
			Icard c = new Icard("卿培");
			String str = new String("tel");
			c.appendItem(str, "12345");
			c.appendItem("tel2", "aabb");
			c.appendPerson(0, "hww");
			c.appendItem("a", "sdsd");
			c.save();
			c = new Icard("孔祥欣");
			str = new String("tel");
			c.appendItem(str, "12345");
			c.appendItem("tel2", "aaaaabb");
			c.appendPerson(0, "hww");
			c.appendItem("a", "sdsd");
			c.save();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
