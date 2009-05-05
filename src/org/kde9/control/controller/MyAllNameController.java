package org.kde9.control.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.kde9.control.FileOperation.ReadFile;
import org.kde9.model.ModelFactory;
import org.kde9.model.allname.AllName;
import org.kde9.util.Constants;

public class MyAllNameController 
implements AllNameController, Constants {
	private AllName names;
	
	/**
	 * 判断一个字符串是否为数字
	 * <p>
	 * 字符串为空串或非数字串将返回false。
	 * 
	 * @param str
	 * 		要判断的字符串
	 */
	private boolean isInt(String str) {
		// 为空串返回false
		if (str == null || str.length() == 0)
			return false;
		for (char c : str.toCharArray())
			// 非数字返回false
			if (!Character.isDigit(c))
				return false;
		return true;
	}

	public MyAllNameController() {
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
		return names.getFirstName(id);
	}

	public String getLastName(int id) {
		return names.getLastName(id);
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
