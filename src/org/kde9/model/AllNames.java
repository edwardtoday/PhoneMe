package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.kde9.util.Constants;

class AllNames 
implements Constants, InterfaceAllNames {
	private HashMap<Integer, String> names;
	WriteFile wf;
	ReadFile rf;
	
	private AllNames()
	throws FileNotFoundException, IOException {
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
			else if(tempName.equals(""))
				continue;
			else
				names.put(Integer.valueOf(tempId), tempName);
		}
	}
	
	/**
	 * �ж�һ���ַ����Ƿ�Ϊ����
	 * <p>
	 * �ַ���Ϊ�մ�������ִ�������false��
	 * 
	 * @param str
	 * 		Ҫ�жϵ��ַ���
	 */
	private boolean isInt(String str) {
		// Ϊ�մ�����false
		if (str.length() == 0)
			return false;
		for (char c : str.toCharArray())
			// �����ַ���false
			if (!Character.isDigit(c))
				return false;
		return true;
	}
	
	public static InterfaceAllNames createAllNames() 
	throws FileNotFoundException, IOException {
		return new AllNames();
	}
	
	public void appendPerson(int id, String name) {
		names.put(id, name);
	}
	
	public void deletePerson(int id) {
		names.remove(id);
	}

	public HashMap<Integer, String> getNames() {
		return names;
	}
	
	public String getName(int id) {
		return names.get(id);
	}
	
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
