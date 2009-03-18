package org.kde9.model;

public class Iperson {
	int id;
	String name;
	
	public Iperson(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}
