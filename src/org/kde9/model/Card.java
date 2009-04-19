package org.kde9.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.kde9.util.Constants;

class Card 
implements Constants, InterfaceCard {
	private static int staticId = 0;
	private int cardId;
	private String name;
	private LinkedHashMap<String, String> items;
	private HashMap<Integer, String> relationship;
	
	private Card(String name) 
	throws IOException {
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
	
	private Card(int cardId) 
	throws FileNotFoundException, IOException {
		ReadFile rf = new ReadFile(CARD_PATH + String.valueOf(cardId));
		this.cardId = cardId;
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
	 * ÅÐ¶ÏÒ»¸ö×Ö·û´®ÊÇ·ñÎªÊý×Ö
	 * <p>
	 * ×Ö·û´®Îª¿Õ´®»ò·ÇÊý×Ö´®½«·µ»Øfalse¡£
	 * 
	 * @param str
	 * 		ÒªÅÐ¶ÏµÄ×Ö·û´®
	 */
	private boolean isInt(String str) {
		// Îª¿Õ´®·µ»Øfalse
		if (str.length() == 0)
			return false;
		for (char c : str.toCharArray())
			// ·ÇÊý×Ö·µ»Øfalse
			if (!Character.isDigit(c))
				return false;
		return true;
	}
	
	public static InterfaceCard createCard(String name) 
	throws IOException {
		return new Card(name);
	}
	
	public static InterfaceCard createCard(int id) 
	throws FileNotFoundException, IOException {
		return new Card(id);
	}

	public void rename(String name) {
		this.name = name;
		
	}

	public void appendItem(String name, String content) {
		items.put(name, content);
	}

	public void deleteItem(String name) {
		items.remove(name);
	}

	public void renameItem(String oldName, String newName) {
		String content = items.get(oldName);
		items.remove(oldName);
		items.put(newName, content);
	}

	public void appendPerson(int cardId, String name) {
		relationship.put(cardId, name);
	}
	
	public void deletePerson(int cardId) {
		relationship.remove(cardId);
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

	
	/*
	 * test!
	 */
	public static void main(String args[]) {
		try {
			Card c = new Card("ÇäÅà");
			String str = new String("tel");
			c.appendItem(str, "12345");
			c.appendItem("tel2", "aabb");
			c.appendPerson(0, "hww");
			c.appendItem("a", "sdsd");
			c.save();
			c = new Card("¿×ÏéÐÀ");
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
