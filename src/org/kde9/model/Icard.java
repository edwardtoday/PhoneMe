package org.kde9.model;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import org.kde9.util.Constants;

class Icard 
implements Constants {
	private static int staticId = 0;
	private int id;
	private String name;
	private String pinyinName;
	private String nickName;
	private String photo;
	private Date birthday;
	private Vector<String> Email;
	private Vector<String> phone;
	private String address;
	private HashMap<Integer, String> persons;
	private String other;
	
	private Icard(String name) {
		File file = new File(DATA_PATH + String.valueOf(staticId));
		while (file.exists()) {
			staticId++;
			file = new File(DATA_PATH + String.valueOf(staticId));
		}
		
		id = staticId++;
		this.name = name;
//		this.pinyinName = ??;
		// TODO
		persons = new HashMap<Integer, String>();
		this.save();
	}
	
	public void save() {
		
	}
	
}
