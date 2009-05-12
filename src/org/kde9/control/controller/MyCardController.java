package org.kde9.control.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.kde9.control.FileOperation.ReadFile;
import org.kde9.control.FileOperation.WriteFile;
import org.kde9.model.ModelFactory;
import org.kde9.model.card.Card;
import org.kde9.model.card.ConstCard;
import org.kde9.util.Constants;

public class MyCardController 
implements CardController, Constants {
	private HashMap<Integer, Card> cards;
	private Save save;
	
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
		if (str == null || str.length() == 0)
			return false;
		for (char c : str.toCharArray())
			// �����ַ���false
			if (!Character.isDigit(c))
				return false;
		return true;
	}
	
	private String getKey(ReadFile rf) 
	throws IOException {
		String key = rf.readLine();
		if(key == null)
			return key;
		if(key.equals(VALUESEPERSTOR))
			key = NULLITEMCONTENT;
		return key;
	}
	
	private Vector<String> getValue(ReadFile rf) 
	throws IOException {
		Vector<String> values = new Vector<String>();
		while(true) {
			String temp = getOneValue(rf);
			if(temp == null || temp.equals(ITEMSEPERATOR))
				break;
			if(temp.equals(VALUESEPERSTOR))
				continue;
			values.add(temp);
		}
		return values;
	}
	
	private String getOneValue(ReadFile rf) 
	throws IOException {
		String temp = rf.readLine();
		if(temp == null || temp.equals(ITEMSEPERATOR) || 
				temp.equals(VALUESEPERSTOR))
			return temp;
		String tempx = rf.readLine();
		while(tempx != null && !tempx.equals(ITEMSEPERATOR) &&
				!tempx.equals(VALUESEPERSTOR)) {
			temp += tempx;
			temp += NEWLINE;
			tempx = rf.readLine();
		}
		return temp;
	}
	
	private Card get(int cardId) {
		Card card = cards.get(cardId);
		if(card == null) {
			File file = new File(CARDPATH + cardId);
			if(file.isFile()) {
				card = ModelFactory.createCard(cardId);
				ReadFile rf;
				try {
					rf = new ReadFile(file);
					String item = rf.readLine();
					if(item == null || item == "")
						item = NULLGROUPNAME;
					card.setFirstName(item);
					item = rf.readLine();
					if(item == null || item == "")
						item = NULLGROUPNAME;
					card.setLastName(item);
					while(true) {
						String key = getKey(rf);
						if(key == null || key.equals(SEPERATOR))
							break;
						if(key.equals(ITEMSEPERATOR))
							continue;
						Vector<String> value = getValue(rf);
						if(value == null)
							break;
						card.addItem(key, value);
					}
					while(true) {
						String id = rf.readLine();
						while(id != null && !id.equals(SEPERATOR))
							if(isInt(id))
								break;
							else
								id = rf.readLine();
						if(id == null || id.equals(SEPERATOR))
							break;
						String content  = rf.readLine();
						if(content == null || content.equals(SEPERATOR))
							break;
						card.addShowRelationship(Integer.valueOf(id), content);
					}
					while(true) {
						String id = rf.readLine();
						while(id != null && !id.equals(SEPERATOR))
							if(isInt(id))
								break;
							else
								id = rf.readLine();
						if(id == null || id.equals(SEPERATOR))
							break;
						card.addHideRelationship(Integer.valueOf(id));
					}
					rf.close();
					cards.put(cardId, card);
				} catch (FileNotFoundException e) {
					System.err.println("MyCard: card " + cardId + " not exists!");
				} catch (IOException e) {
					System.err.println("MyCard: IOException!");
				}
			}
		}
		return card;
	}
	
	public MyCardController() {
		cards = new HashMap<Integer, Card>();
		save = new Save();
	}
	
	public Card addCard(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean addCardItem(int id, String item, String content) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addRelationship(int cardId, int personId, String content) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteCard(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteCardItem(int id, String item, String content) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteRelationship(int cardId, int personId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean findByItem(int cardId, String item, String content,
			boolean wholeWord) {
		// TODO Auto-generated method stub
		return false;
	}

	public ConstCard getCard(int cardId) {
		return get(cardId);
	}

	public LinkedHashMap<String, Vector<String>> getCardItems(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public LinkedHashMap<Integer, String> getRelationship(int cardId) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean renameCard(int id, String firstName, String lastName) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean save(int id) {
		Card card = get(id);
		String temp = card.getFirstName();
		temp += NEWLINE;
		temp += card.getLastName();
		for (String key : card.getAllItems().keySet()) {
			temp += key;
			temp += NEWLINE;
			for (String value : card.getItem(key)) {
				temp += value;
				temp += NEWLINE;
				temp += VALUESEPERSTOR;
				temp += NEWLINE;
			}
			temp += ITEMSEPERATOR;
			temp += NEWLINE;
		}
		temp += SEPERATOR;
		temp += NEWLINE;
		for (int relation : card.getAllShowRelationship().keySet()) {
			temp += relation;
			temp += NEWLINE;
			temp += card.getShowRelationship(relation);
			temp += NEWLINE;
		}
		temp += SEPERATOR;
		temp += NEWLINE;
		for (int relation : card.getAllHideRelationship()) {
			temp += relation;
			temp += NEWLINE;
		}
		save.init(CARDPATH + id, temp);
		return save.save();
	}

	public boolean setCardItems(int id, HashMap<String, Vector<String>> items) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean setRelationship(int cardId, int personId, String content) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean setRelationships(int cardId,
			LinkedHashMap<Integer, String> relation) {
		// TODO Auto-generated method stub
		return false;
	}

}
