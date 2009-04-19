package org.kde9.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashSet;

import org.kde9.model.Factory;
import org.kde9.model.InterfaceAllGroups;

public class AllGroupsController {
	private InterfaceAllGroups allGroups;
	
	public AllGroupsController() 
	throws FileNotFoundException, IOException {
		try {
			allGroups = Factory.createAllGroups();
		} catch(FileNotFoundException e) {
			// TODO
		}
	}
	
	/**
	 * Ìí¼Ógroup
	 * @throws IOException 
	 */
	public void appendGroup(int id) 
	throws IOException {
		allGroups.appendGroup(id);
		allGroups.save();
	}

	/**
	 * É¾³ýgroup
	 * @throws IOException 
	 */
	public void deleteGroup(int id) 
	throws IOException {
		allGroups.deleteGroup(id);
		allGroups.save();
	}

	public LinkedHashSet<Integer> getGroupIds() {
		LinkedHashSet<Integer> temp = new LinkedHashSet<Integer>();
		LinkedHashSet<Integer> ids = allGroups.getGroupIds();
		if(ids != null)
			for(int id : ids)
				temp.add(id);
		return temp;
	}

}
