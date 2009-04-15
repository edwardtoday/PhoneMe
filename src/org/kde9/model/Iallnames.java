package org.kde9.model;

import java.util.HashMap;

class Iallnames {
	private HashMap<Integer, String> names;
	
	public Iallnames() {
		
	}
	
	public void appendPerson(int id, String name) {
		names.put(id, name);
	}
	
	public void deletePerson(int id) {
		names.remove(id);
	}
	
	public void save() {
		
	}
}
