package org.kde9.model.allname;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class MyAllName 
implements AllName {
	private LinkedHashMap<Integer, MyName> people;
	
	public MyAllName() {
		people = new LinkedHashMap<Integer, MyName>();
	}

	public boolean addPerson(int id, String firstName, String lastName) {
		if(people.containsKey(id))
			return false;
		else {
			MyName name = new MyName();
			name.setFirstName(firstName);
			name.setLastName(lastName);
			people.put(id, name);
		}
		return true;
	}

	public boolean deletePerson(int id) {
		if(people.containsKey(id)) {
			people.remove(id);
			return true;
		}
		else
			return false;
	}

	public String getFirstName(int id) {
		return people.get(id).getFirstName();
	}

	public String getLastName(int id) {
		return people.get(id).getLastName();
	}

	public boolean setFirstName(int id, String newFirst) {
		if(people.containsKey(id)) {
			people.get(id).setFirstName(newFirst);
			return true;
		}
		return false;
	}

	public boolean setLastName(int id, String newLast) {
		if(people.containsKey(id)) {
			people.get(id).setLastName(newLast);
			return true;
		}
		return false;
	}
	
	public LinkedHashSet<Integer> getIds() {
		LinkedHashSet<Integer> ids = new LinkedHashSet<Integer>();
		for(int id : people.keySet())
			ids.add(id);
		return ids;
	}

	public String getPYFirstName(int id) {
		return people.get(id).getPYFirstName();
	}

	public String getPYLastName(int id) {
		return people.get(id).getPYLastName();
	}

	public String getPinYinFirstName(int id) {
		return people.get(id).getPinYinFirstName();
	}

	public String getPinYinLastName(int id) {
		return people.get(id).getPinYinLastName();
	}
}
