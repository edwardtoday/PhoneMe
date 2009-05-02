package org.kde9.control.controller;

import java.util.HashMap;

import org.kde9.model.ModelFactory;
import org.kde9.model.allname.AllName;

public class MyAllName 
implements AllNameController {
	private AllName names;
	
	public MyAllName() {
		names = ModelFactory.createAllName();
	}

	public boolean addPerson(int id, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deletePerson(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean findByName(String Name, int type) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getFirstName(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLastName(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean save() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean setFirstName(int id, String firstName) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean setLastzName(int id, String lastName) {
		// TODO Auto-generated method stub
		return false;
	}

}
