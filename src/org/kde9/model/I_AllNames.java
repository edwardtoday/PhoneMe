package org.kde9.model;

import java.io.IOException;
import java.util.HashMap;

interface I_AllNames {

	public abstract void appendPerson(int id, String name);

	public abstract void deletePerson(int id);

	public abstract HashMap<Integer, String> getNames();

	public abstract void save() throws IOException;

}