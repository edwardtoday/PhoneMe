package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import org.kde9.util.Constants;
import org.kde9.view.Igroup;

public class Kernel implements Constants{
	/**
	 * 查找函数，要功能超强才行！
	 */
	public boolean find() {
		//TODO
		return false;
	}
	
	public Icard readCard() {
		return null;
	}
	
	public Igroup readGroup() {
		return null;
	}
	
	public static Vector<Mgroup> readAllGroups() throws IOException {
		ReadFile wf = new ReadFile(GROUP_PATH + ALLGROUPS);
		Vector<Mgroup> allGroups = new Vector<Mgroup>();
		while(true) {
			String id = wf.readLine();
			String name = wf.readLine();
			if(name != null)
				allGroups.add(new Mgroup(Integer.valueOf(id), name));
			else
				break;
		}
		System.out.println(allGroups.size());
		return allGroups;
	}
}
