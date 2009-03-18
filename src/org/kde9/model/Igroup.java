package org.kde9.model;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.kde9.util.Constants;

public class Igroup implements Constants{
	private static int id = 0;
	private String groupName;
	private Vector<String> persons;
	
	public Igroup(String groupName) {
		// TODO Auto-generated constructor stub
		//id = 0;
		this.groupName = groupName;
		persons = new Vector<String>();
	}
	
	public Igroup(int id, String groupName) {
		this.id = id;
		this.groupName = groupName;
	}
	
	public String toString() {
		return groupName;
	}
	
	public void rename(String groupName) {
		this.groupName = groupName;
	}
	
	public void save() throws IOException {
		String temp = groupName;
		if(persons.size() != 0)
			for(String p : persons) {
				temp += System.getProperty("line.separator");
				temp += p;
			}
		System.out.println("before ID++");
		File file = new File(GROUP_PATH + String.valueOf(id));
		while(file.exists()) {
			id++;
			file = new File(GROUP_PATH + String.valueOf(id));
		}
		System.out.println("after ID++");
		WriteFile wf = new WriteFile(GROUP_PATH + String.valueOf(id), false);
		wf.write(temp);
		wf.close();
		wf = new WriteFile(GROUP_PATH + ALLGROUPS, true);
		wf.write(
				id +
				System.getProperty("line.separator") +
				groupName + 
				System.getProperty("line.separator") );
		wf.close();
	}
}
