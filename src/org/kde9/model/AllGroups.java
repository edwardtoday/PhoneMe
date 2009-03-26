package org.kde9.model;

import java.io.IOException;
import java.util.HashSet;

interface AllGroups {

	public abstract void appendToAllGroup(int id);

	public abstract void deleteFromAllGroup(int id);

	public abstract HashSet<Integer> getIds();
	
	public void save() throws IOException;

}