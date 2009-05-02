package org.kde9.model.allname;

import java.util.HashMap;

public class MyAllName 
implements AllName {
	private HashMap<Integer, MyName> people;
	
	public MyAllName() {
		people = new HashMap<Integer, MyName>();
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
}
