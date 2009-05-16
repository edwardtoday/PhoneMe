package org.kde9.control.controller;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.kde9.model.card.Card;
import org.kde9.model.card.ConstCard;

public interface CardController {

	public Card addCard(String firstName, String lastName);

	public boolean deleteCard(int id);
	
	public ConstCard getCard(int cardId);

	public boolean renameCard(int id, String firstName, String lastName);

	public boolean addCardItem(int id, String item, String content);

	public boolean deleteCardItem(int id, String item, String content);

	public boolean setCardItems(int id, LinkedHashMap<String, Vector<String>> items);

	public LinkedHashMap<String, Vector<String>> getCardItems(int id);

	public LinkedHashMap<Integer, String> getRelationship(int cardId);

	public boolean addRelationship(int cardId, int personId, String content);

	public boolean deleteRelationship(int cardId, int personId);
	
	public boolean setRelationship(int cardId, int personId, String content);
	
	public boolean setRelationships(int cardId, LinkedHashMap<Integer, String> relation);

	public boolean findByItem(int cardId, 
			String item, String content, boolean wholeWord);
	
	public boolean findByRelation(int cardId, 
			String content, boolean wholeWord);
	
	public boolean setImage(String path);
	
	public BufferedImage getImage(int id);
	
	public BufferedImage getScaleImage(int id);
	
	public boolean isImageReady(int id);
	
	public boolean save(int cardId);
}
