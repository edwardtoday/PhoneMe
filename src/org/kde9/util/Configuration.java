package org.kde9.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.kde9.control.FileOperation.ReadFile;
import org.kde9.control.FileOperation.WriteFile;

/**
 * ���������ļ�
 * 
 * @author kfirst
 */
public class Configuration 
implements Constants {	
	private HashMap<String, String> config;
	private HashMap<String, String> original;
	private ReadFile rf;
	private WriteFile wf;
	
	public Configuration() {
		original = new LinkedHashMap<String, String>();
		config = new LinkedHashMap<String, String>();
		for(int i = 0; i < Constants.config.length; i += 2) {
			original.put(Constants.config[i], Constants.config[i+1]);
		}
		try {
			rf = new ReadFile(DATAPATH + CONFIGURATION);
			String item = "", content = "";
			while(true) {
				item = rf.readLine();
				while(item == "")
					item = rf.readLine();
				content = rf.readLine();
				while(content == "")
					content = rf.readLine();
				if(item == null || content == null)
					break;
				config.put(item, content);	
			}
			rf.close();
		} catch (FileNotFoundException e) {
			System.err.println("δ�ҵ������ļ�!");
		} catch (IOException e) {}
	}
	
	public Object getConfig(String item, int type) {
		if(type == CONFIGSTRING)
			return config.get(item);
		else if(type == CONFIGINT) {
			String temp = config.get(item);
			if(isInt(temp))
				return Integer.valueOf(temp);
			else
				return Integer.valueOf(original.get(item));
		}
		else
			return null;
	}
	
	public boolean setConfig(String item, String value) {
		config.put(item, value);
		if(save())
			return true;
		else
			return false;
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
		if (str == null || str.length() == 0)
			return false;
		for (char c : str.toCharArray())
			// �����ַ���false
			if (!Character.isDigit(c))
				return false;
		return true;
	}
	
	private boolean save() {
		try {
			wf = new WriteFile(DATAPATH + CONFIGURATION, false);
			String temp = new String();
			for(String item : config.keySet()) {
				temp += item;
				temp += NEWLINE;
				temp += config.get(item);
				temp += NEWLINE;
			}
			wf.write(temp);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public static void main(String args[]) {
		Configuration c = new Configuration();
		System.out.println(c.original);
		System.out.println(c.config);
	}
}
