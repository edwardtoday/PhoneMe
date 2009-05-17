package org.kde9.control.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.kde9.control.FileOperation.ReadFile;
import org.kde9.control.FileOperation.WriteFile;
import org.kde9.model.ModelFactory;
import org.kde9.model.allname.AllName;
import org.kde9.util.Constants;

public class MyAllNameController 
implements AllNameController, Constants {
	private AllName names;
	private Save save;
	
	private boolean isInt(String str) {
		if (str == null || str.length() == 0)
			return false;
		for (char c : str.toCharArray())
			if (!Character.isDigit(c))
				return false;
		return true;
	}

	public MyAllNameController() {
		names = ModelFactory.createAllName();
		save = new Save();
		ReadFile rf;
		try {
			rf = new ReadFile(CARDPATH + ALLNAMES);
			while (true) {
				String tempId = rf.readLine();
				while (!isInt(tempId)) {
					if (tempId == null)
						return;
					tempId = rf.readLine();
				}
				String tempFirst = rf.readLine();
				while (tempFirst == null || tempFirst == "") {
					if (tempFirst == null)
						return;
					tempFirst = rf.readLine();
				}
				String tempLast = rf.readLine();
				while (tempLast == null || tempLast == "") {
					if (tempLast == null)
						return;
					tempLast = rf.readLine();
				}
				names.addPerson(Integer.valueOf(tempId), tempFirst, tempLast);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean addPerson(int id, String firstName, String lastName) {
		return names.addPerson(id, firstName, lastName);
	}

	public boolean deletePerson(int id) {
		return names.deletePerson(id);
	}

	public boolean findByName(String Name, int type) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getFirstName(int id) {
		return names.getFirstName(id);
	}

	public String getLastName(int id) {
		return names.getLastName(id);
	}

	public boolean save() {
		save.init(names);
		return save.save();
	}

	public boolean setFirstName(int id, String firstName) {
		return names.setFirstName(id, firstName);
	}

	public boolean setLastzName(int id, String lastName) {
		return names.setLastName(id, lastName);
	}

}
