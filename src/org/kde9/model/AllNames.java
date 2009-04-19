package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.kde9.util.I_Constants;

class AllNames implements I_Constants, I_AllNames {
	private HashMap<Integer, String> names;
	WriteFile wf;
	ReadFile rf;

	private AllNames() throws FileNotFoundException, IOException {
		rf = new ReadFile(CARD_PATH + ALLNAMES);
		names = new HashMap<Integer, String>();
		while (true) {
			String tempId = rf.readLine();
			if (tempId == null)
				break;
			else if (isInt(tempId))
				;
			else
				continue;
			String tempName = rf.readLine();
			if (tempName == null)
				break;
			else if (tempName.equals(""))
				continue;
			else
				names.put(Integer.valueOf(tempId), tempName);
		}
	}

	/**
	 * 判断一个字符串是否为数字
	 * <p>
	 * 字符串为空串或非数字串将返回false。
	 * 
	 * @param str
	 *            要判断的字符串
	 */
	private boolean isInt(String str) {
		// 为空串返回false
		if (str.length() == 0)
			return false;
		for (char c : str.toCharArray())
			// 非数字返回false
			if (!Character.isDigit(c))
				return false;
		return true;
	}

	public static I_AllNames createAllNames() throws FileNotFoundException,
			IOException {
		return new AllNames();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kde9.model.AllNames#appendPerson(int, java.lang.String)
	 */
	public void appendPerson(int id, String name) {
		names.put(id, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kde9.model.AllNames#deletePerson(int)
	 */
	public void deletePerson(int id) {
		names.remove(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kde9.model.AllNames#getNames()
	 */
	public HashMap<Integer, String> getNames() {
		return names;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kde9.model.AllNames#save()
	 */
	public void save() throws IOException {
		wf = new WriteFile(CARD_PATH + ALLNAMES, false);
		String temp = "";
		if (names.size() != 0)
			for (int i : names.keySet()) {
				temp += i;
				temp += NEWLINE;
				temp += names.get(i);
				temp += NEWLINE;
			}
		wf.write(temp);
		wf.close();
	}
}
