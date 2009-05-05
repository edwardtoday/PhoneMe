package org.kde9.model.card;

import java.util.LinkedHashMap;
import java.util.Vector;

class MyItems {
	private LinkedHashMap<String, Vector<String>> items;
	
	public MyItems() {
		items = new LinkedHashMap<String, Vector<String>>();
	}

	public boolean addItem(String key, String value) {
		if(key == null || value == null)
			return false;
		Vector<String> contents;
		if(items.containsKey(key)) {
			contents = items.get(key);
			if(contents.contains(value))
				return false;
			else
				contents.add(value);
		} else {
			contents = new Vector<String>();
			contents.add(value);
			items.put(key, contents);
		}
		return true;
	}
	
	public boolean addItem(String key, Vector<String> values) {
		if(key == null)
			return false;
		if(items.containsKey(key))
			return false;
		else
			items.put(key, values);
		return true;
	}
	
	public boolean deleteItem(String key, String value) {
		if(key == null)
			return false;
		if(value == null) {
			items.remove(key);
		} else {
			return items.get(key).remove(value);
		}
		return true;
	}
	
	public boolean setItem(String key, Vector<String> values) {
		if(key == null)
			return false;
		if(!items.containsKey(key))
			return false;
		else
			items.put(key, values);
		return true;
	}

	public boolean setItem(String key, String oldValue, String newValue) {
		if(key == null)
			return false;
		if(items.containsKey(key)) {
			Vector<String> contents = items.get(key);
			if(contents.contains(oldValue)) {
				contents.set(contents.indexOf(oldValue), newValue);
			}
		} else 
			return false;
		return true;
	}

	public boolean setItems(LinkedHashMap<String, Vector<String>> items) {
		this.items = items;
		return true;
	}
	
	public LinkedHashMap<String, Vector<String>> getAllItems() {
		return items;
	}
	
	public Vector<String> getItem(String itemName) {
		return items.get(itemName);
	}
}
