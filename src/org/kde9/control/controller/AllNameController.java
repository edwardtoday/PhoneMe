package org.kde9.control.controller;

public interface AllNameController {
	public boolean addPerson(int id, String firstName, String lastName);
	
	public boolean deletePerson(int id);
	
	public String getFirstName(int id);
	
	public String getLastName(int id);
	
	public boolean setFirstName(int id, String firstName);
	
	public boolean setLastzName(int id, String lastName);
	
	public boolean findByName(String Name, int type);
	
	public boolean save();
}
