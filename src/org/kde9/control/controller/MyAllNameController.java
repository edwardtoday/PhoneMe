package org.kde9.control.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashSet;

import org.kde9.control.FileOperation.ReadFile;
import org.kde9.model.ModelFactory;
import org.kde9.model.allname.AllName;
import org.kde9.util.ConfigFactory;
import org.kde9.util.Configuration;
import org.kde9.util.Constants;

public class MyAllNameController 
implements AllNameController, Constants {
	private AllName names;
	//private Save save;
	
	public static final int NAME = 112;
	public static final int PINYINNAME = 123;
	public static final int PYNAME = 231;
	
	private Configuration configuration;
	
	private boolean isInt(String str) {
		if (str == null || str.length() == 0)
			return false;
		for (char c : str.toCharArray())
			if (!Character.isDigit(c))
				return false;
		return true;
	}

	public MyAllNameController() {
		configuration = ConfigFactory.creatConfig();
		names = ModelFactory.createAllName();
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

	public boolean findByName(int id, String name, int type) {
		// TODO Auto-generated method stub
		if(type == NAME) {
			String fullName;
			if((Integer)configuration.getConfig(NAME_FOMAT, CONFIGINT) == 0) {
				fullName = names.getFirstName(id) + names.getLastName(id);
				if(fullName.contains(name))
					return true;
			}
			else {
				fullName = names.getLastName(id) + names.getFirstName(id);
				if(fullName.contains(name))
					return true;
			}
		} else if(type == PINYINNAME) {
			String pinyinName;
			if((Integer)configuration.getConfig(NAME_FOMAT, CONFIGINT) == 0) {
				pinyinName = names.getPinYinFirstName(id) + names.getPinYinLastName(id);
				if(pinyinName.contains(name))
					return true;
			}
			else {
				pinyinName = names.getPinYinLastName(id) + names.getPinYinFirstName(id);
				if(pinyinName.contains(name))
					return true;
			}
		} else if(type == PYNAME) {
			String pyName;
			if((Integer)configuration.getConfig(NAME_FOMAT, CONFIGINT) == 0) {
				pyName = names.getPYFirstName(id) + names.getPYLastName(id);
				if(pyName.contains(name))
					return true;
			}
			else {
				pyName = names.getPYLastName(id) + names.getPYFirstName(id);
				if(pyName.contains(name))
					return true;
			}
		}
		return false;
	}
	
	public boolean findByName(int id, String name) {
		// TODO Auto-generated method stub
			if((Integer)configuration.getConfig(NAME_FOMAT, CONFIGINT) == 0) {
				String fullName = names.getFirstName(id) + names.getLastName(id);
				if(fullName.contains(name))
					return true;
			}
			else {
				String fullName = names.getLastName(id) + names.getFirstName(id);
				if(fullName.contains(name))
					return true;
			}
			if((Integer)configuration.getConfig(NAME_FOMAT, CONFIGINT) == 0) {
				String pinyinName = names.getPinYinFirstName(id) + names.getPinYinLastName(id);
				if(pinyinName.contains(name))
					return true;
			}
			else {
				String pinyinName = names.getPinYinLastName(id) + names.getPinYinFirstName(id);
				if(pinyinName.contains(name))
					return true;
			}
			if((Integer)configuration.getConfig(NAME_FOMAT, CONFIGINT) == 0) {
				String pyName = names.getPYFirstName(id) + names.getPYLastName(id);
				if(pyName.contains(name))
					return true;
			}
			else {
				String pyName = names.getPYLastName(id) + names.getPYFirstName(id);
				if(pyName.contains(name))
					return true;
			}
		return false;
	}

	public String getFirstName(int id) {
		return names.getFirstName(id);
	}

	public String getLastName(int id) {
		return names.getLastName(id);
	}

	public boolean save() {
		Save save = new Save();
		save.init(names);
		return save.save();
	}

	public boolean setFirstName(int id, String firstName) {
		return names.setFirstName(id, firstName);
	}

	public boolean setLastzName(int id, String lastName) {
		return names.setLastName(id, lastName);
	}

	public LinkedHashSet<Integer> getIds() {
		return names.getIds();
	}
}
