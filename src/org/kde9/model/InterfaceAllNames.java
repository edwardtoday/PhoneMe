package org.kde9.model;

import java.io.IOException;
import java.util.HashMap;

public interface InterfaceAllNames {

	public abstract void appendPerson(int id, String name);

	public abstract void deletePerson(int id);

	public abstract HashMap<Integer, String> getNames();
	
	public String getName(int id);

	public abstract void save()
	throws IOException;

}