package org.kde9.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import org.kde9.model.Factory;
import org.kde9.model.InterfaceAllNames;

public class AllNamesController {
	private InterfaceAllNames allNames;
	
	public AllNamesController() 
	throws IOException {
		try {
			allNames = Factory.createAllNames();
		} catch(FileNotFoundException e) {
			// TODO
		}
	}
	
	public void appendPerson(int id, String name) 
	throws IOException {
		allNames.appendPerson(id, name);
		allNames.save();
	}

	public void deletePersons(LinkedHashSet<Integer> ids) 
	throws IOException {
		if(ids != null) {
			for(int id : ids)
				allNames.deletePerson(id);
			allNames.save();
		}
	}
	
	public void deletePerson(int id) 
	throws IOException {
		allNames.deletePerson(id);
		allNames.save();
	}

	public String getNames(int id) {
		return allNames.getName(id);
	}
	
	public HashMap<Integer, String> getNames(int[] ids) {
		HashMap<Integer, String> temp = new HashMap<Integer, String>();
		for(int i = 0; i < ids.length; i++) {
			String name = allNames.getName(ids[i]);
			if(name != null)
				temp.put(ids[i], name);
		}
		return temp;
	}
}
