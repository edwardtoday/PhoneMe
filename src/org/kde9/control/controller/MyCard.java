package org.kde9.control.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.kde9.model.card.Card;
import org.kde9.model.card.ConstCard;

public class MyCard 
implements CardController {

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
		// TODO Auto-generated method stub
		return null;
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
