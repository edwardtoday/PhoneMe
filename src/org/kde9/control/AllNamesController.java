package org.kde9.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

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

	public void deletePerson(int[] id) 
	throws IOException {
		for(int i = 0; i < id.length; i++)
			allNames.deletePerson(id[i]);
		allNames.save();
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
