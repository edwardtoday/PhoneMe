package org.kde9.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Vector;

import org.kde9.model.Factory;
import org.kde9.model.InterfaceCard;
import org.kde9.util.Constants;

public class CardController 
implements Constants{
	private HashMap<Integer, InterfaceCard> cards;
	
	private InterfaceCard getCard(int id) 
	throws IOException {
		InterfaceCard card = cards.get(id);
		if(card == null) {
			try {
				card = Factory.createCard(id);
			} catch (FileNotFoundException e) {
				// TODO 
			}
			cards.put(id, card);
		}
		return card;
	}
	
	public CardController() {
		cards = new HashMap<Integer, InterfaceCard>();
	}
	
	public void renameCard(int id, String name) 
	throws IOException {
		InterfaceCard card = getCard(id);
		if(card != null) {
			card.rename(name);
			card.save();
		}
	}
	
	public void addCardItem(int id, String name, String content) 
	throws IOException {
		InterfaceCard card = getCard(id);
		if(card != null) {
			card.appendItem(name, content);
			card.save();
		}
	}
	
	public void deleteCardItem(int id, String name) 
	throws IOException {
		InterfaceCard card = getCard(id);
		if(card != null) {
			card.deleteItem(name);
			card.save();
		}
	}
	
	public void renameCardItem(int id, String oldName, String newName) 
	throws IOException {
		InterfaceCard card = getCard(id);
		if(card != null) {
			card.renameItem(oldName, newName);
			card.save();
		}
	}
	
	public LinkedHashMap<String, String> getCardItem(int id) 
	throws IOException {
		LinkedHashMap<String, String> l = new LinkedHashMap<String, String>();
		InterfaceCard card = getCard(id);
		if(card != null)
			for(String str : card.getItems().keySet())
				l.put(str, card.getItems().get(str));
		return l;
	}

	public HashSet<Integer> getRelationship(int cardId) 
	throws IOException {
		HashSet<Integer> temp = new HashSet<Integer>();
		HashMap<Integer, String> relation = getCard(cardId).getRelationship();
		if(relation != null)
			for(int id : relation.keySet())
				if(!relation.get(id).equals(HIDEPERSON))
					temp.add(id);
		return temp;
	}

	public void addRelationship(int cardId, int personId)
	throws IOException {
		InterfaceCard card = getCard(cardId);
		if(card != null) {
			card.appendPerson(personId, "");
			addHideRelationship(personId, cardId, false);
		}
	}
	
	private void addHideRelationship(int cardId, int personId, boolean strong)
	throws IOException {
		InterfaceCard card = getCard(cardId);
		if(card != null)
			if(strong || !card.getRelationship().containsKey(personId))
				card.appendPerson(personId, HIDEPERSON);
	}
	
	public void deleteRelationship(int cardId, int personId) 
	throws IOException {
		InterfaceCard card = getCard(cardId);
		if(card != null) {
			InterfaceCard hide = getCard(personId);
			if(!hide.getRelationship().containsKey(cardId))
				card.deletePerson(personId);
			else if(hide.getRelationship().get(cardId).equals(HIDEPERSON)) {
				card.deletePerson(personId);
				hide.deletePerson(cardId);
			} else
				addHideRelationship(cardId, personId, true);
		}
	}
	
	public boolean findByItem(int cardId, 
			String item, String content, boolean wholeWord) 
	throws IOException {
		InterfaceCard card = getCard(cardId);
		if(card != null) {
			LinkedHashMap<String, String> items = card.getItems();
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
		}
		return false;
	}
	
	public void delete(int cardId) {
		InterfaceCard card = cards.get(cardId);
		if(card != null) {
			card.delete();
			cards.remove(cardId);
		}
	}
}
