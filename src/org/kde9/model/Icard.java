package org.kde9.model;

/*
 * 2009.03.16
 * 卡片类，表示每个名片
 */
public class Icard {
	private int id;
	private String name;
	private String phone;
	private String addr;
	private String other;
	
	/*
	 * gets and sets
	 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	/*
	 * functions
	 */
	
}
