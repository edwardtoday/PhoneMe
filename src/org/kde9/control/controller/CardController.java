package org.kde9.control.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.kde9.model.card.Card;
import org.kde9.model.card.ConstCard;

public interface CardController {
	/**
	 * 添加联系人
	 * @param name
	 * @return
	 */
	public Card addCard(String name);

	public boolean deleteCard(int id);
	
	public ConstCard getCard(int cardId);

	public boolean renameCard(int id, String firstName, String lastName);

	public boolean addCardItem(int id, String item, String content);

	public boolean deleteCardItem(int id, String item, String content);

	public boolean setCardItems(int id, HashMap<String, Vector<String>> items);

	public LinkedHashMap<String, Vector<String>> getCardItems(int id);

	public LinkedHashMap<Integer, String> getRelationship(int cardId);

	public boolean addRelationship(int cardId, int personId, String content);

	public boolean deleteRelationship(int cardId, int personId);
	
	public boolean setRelationship(int cardId, int personId, String content);
	
	public boolean setRelationships(int cardId, LinkedHashMap<Integer, String> relation);

	public boolean findByItem(int cardId, 
			String item, String content, boolean wholeWord);
	
	public boolean save();
}
