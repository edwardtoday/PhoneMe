package org.kde9.model.card;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Vector;

public class MyCard 
implements Card {
	private int id;
	private MyName name;
	private MyItems items;
	private MyRelationship relationship;
	private Image image;

	public MyCard(int id) {
		this.id = id;
		name = new MyName();
		items = new MyItems();
		relationship = new MyRelationship();
		image = new Image();
	}
	
	public boolean addHideRelationship(int id) {
		return relationship.addHideRelationship(id);
	}

	public boolean addItem(String key, String value) {
		return items.addItem(key, value);
	}

	public boolean addShowRelationship(int id, String content) {
		return relationship.addShowRelationship(id, content);
	}

	public boolean deleteHideRelationship(int id) {
		return relationship.deleteHideRelationship(id);
	}

	public boolean deleteItem(String key, String value) {
		return items.deleteItem(key, value);
	}

	public boolean deleteShowRelationship(int id) {
		return relationship.deleteShowRelationship(id);
	}

	public HashSet<Integer> getAllHideRelationship() {
		return relationship.getAllHideRelationship();
	}

	public boolean setFirstName(String newFirst) {
		return name.setFirstName(newFirst);
	}

	public boolean setItem(String key, Vector<String> values) {
		return items.setItem(key, values);
	}

	public boolean setItem(String key, String oldValue, String newValue) {
		return items.setItem(key, oldValue, newValue);
	}

	public boolean setItems(LinkedHashMap<String, Vector<String>> items) {
		return this.items.setItems(items);
	}

	public boolean setLastName(String newLast) {
		return name.setLastName(newLast);
	}

	public boolean setShowRelationship(int id, String content) {
		return relationship.setShowRelationship(id, content);
	}

	public LinkedHashMap<String, Vector<String>> getAllItems() {
		return items.getAllItems();
	}

	public LinkedHashMap<Integer, String> getAllShowRelationship() {
		return relationship.getAllShowRelationship();
	}

	public String getFirstName() {
		return name.getFirstName();
	}

	public int getId() {
		return id;
	}

	public Vector<String> getItem(String itemName) {
		return items.getItem(itemName);
	}

	public String getLastName() {
		return name.getLastName();
	}

	public String getPYFirstName() {
		return name.getPinYinFirstName();
	}

	public String getPYLastName() {
		return name.getPYLastName();
	}

	public String getPinYinFirstName() {
		return name.getPinYinFirstName();
	}

	public String getPinYinLastName() {
		return name.getPinYinLastName();
	}

	public String getShowRelationship(int cardId) {
		return relationship.getShowRelationship(cardId);
	}

	public boolean addItem(String key, Vector<String> values) {
		return items.addItem(key, values);
	}

	public boolean setImage(BufferedImage image) {
		this.image.setImage(image);
		return true;
	}

	public boolean setScaleImage(BufferedImage scaleImage) {
		this.image.setScaleImage(scaleImage);
		return true;
	}

	public BufferedImage getImage() {
		return image.getImage();
	}

	public BufferedImage getScaleImage() {
		return image.getScaleImage();
	}

	public boolean isImageRafdy() {
		return image.isReady();
	}

	public boolean setImageReady(boolean ready) {
		image.setReady(ready);
		return true;
	}

	
//	public static void main(String args[]) {
//		MyCard card = new MyCard(21);
//		card.setFirstName("胡");
//		card.setLastName("玮玮!");
//		card.addItem("电话", "51534418");
//		card.addItem("电话", "000000");
//		card.addItem("班级", "计62班");
//		card.addShowRelationship(2, "test");
//		card.addShowRelationship(3, "ok!");
//		card.show();
//		card.deleteShowRelationship(3);
//		card.deleteItem("电话", "000000");
//		card.show();
//	}
//	
//	private void show() {
//		System.out.println(id);
//		System.out.println(name.getFirstName() + " " + name.getLastName());
//		System.out.println(name.getPinYinFirstName() + " " + name.getPinYinLastName());
//		System.out.println(items);
//		for(String str : items.getAllItems().keySet()) {
//			System.out.println("\t" + str + " " + items.getAllItems().get(str));
//		}
//		System.out.println(relationship);
//		for(int id : relationship.getAllShowRelationship().keySet()) {
//			System.out.println("\t" + id + " " + relationship.getShowRelationship(id));
//		}
//	}
}
