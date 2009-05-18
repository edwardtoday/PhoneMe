package org.kde9.control.controller;

import java.util.LinkedHashSet;
import java.util.Set;

public interface AllNameController {
	public boolean addPerson(int id, String firstName, String lastName);
	
	public boolean deletePerson(int id);
	
	public String getFirstName(int id);
	
	public String getLastName(int id);
	
	public boolean setFirstName(int id, String firstName);
	
	public boolean setLastzName(int id, String lastName);
	
	public boolean findByName(int id, String Name, int type);
	
	public boolean findByName(int id, String Name);
	
	public boolean save();
	
	public LinkedHashSet<Integer> getIds();
}
