package org.kde9.util;

public class TreeNode {
	String type;
	int id = -1;
	String name = "";
	String content = "";
	
	public String toString() {
		if(id == -1)
			return type + " : " + name + " - " + content;
		return type + " " + id + " : " + name + " " + content;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
