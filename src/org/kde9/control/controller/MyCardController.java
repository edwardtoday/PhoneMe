package org.kde9.control.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.kde9.control.FileOperation.ReadFile;
import org.kde9.model.ModelFactory;
import org.kde9.model.card.Card;
import org.kde9.model.card.ConstCard;
import org.kde9.util.Constants;

public class MyCardController 
implements CardController, Constants {
	private HashMap<Integer, Card> cards;
	
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
		if (str == null || str.length() == 0)
			return false;
		for (char c : str.toCharArray())
			// 非数字返回false
			if (!Character.isDigit(c))
				return false;
		return true;
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
					// TODO
					rf.close();
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

	public boolean save() {
		// TODO Auto-generated method stub
		return false;
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
