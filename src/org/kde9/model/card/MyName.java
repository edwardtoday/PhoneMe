package org.kde9.model.card;

import org.kde9.util.PinYin;

class MyName {
	private String firstName;
	private String lastName;
	private String pinYinFirstName;
	private String pinYinLastName;
	private String pYFirstName;
	private String pYLastName;
	
	public String getFirstName() {
		return firstName;
	}
	
	public boolean setFirstName(String firstName) {
		if(firstName == null)
			return false;
		this.firstName = firstName;
		this.pinYinFirstName = PinYin.getPingYin(firstName);
		this.pYFirstName = PinYin.getPinYinHeadChar(firstName);
		if(pinYinFirstName == null || pYFirstName == null)
			return false;
		else
			return true;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public boolean setLastName(String lastName) {
		if(lastName == null)
			return false;
		this.lastName = lastName;
		this.pinYinLastName = PinYin.getPingYin(lastName);
		this.pYLastName = PinYin.getPinYinHeadChar(lastName);
		if(pinYinLastName == null || pYLastName == null)
			return false;
		else
			return true;
	}
	
	public String getPinYinFirstName() {
		return pinYinFirstName;
	}
	
	public String getPinYinLastName() {
		return pinYinLastName;
	}
	
	public String getPYFirstName() {
		return pYFirstName;
	}
	
	public String getPYLastName() {
		return pYLastName;
	}
	
//	public static void main(String args[]) {
//		MyName myName = new MyName("єъ", "звзв");
//		System.out.println(myName.pinYinFirstName + " " + myName.pinYinLastName);
//		System.out.println(myName.pYFirstName + ' ' + myName.pYLastName);
//	}
}
