package org.kde9.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import com.sun.java_cup.internal.internal_error;

class Icard {
	private int id;
	private String firstName;
	private String pinyinFirstName;
	private String lastName;
	private String pinyinLastName;
	private String nickName;
	private String photo;
	private Date birthday;
	private Vector<String> Email;
	private Vector<String> phone;
	private String address;
	private HashMap<Integer, String> persons;
	private String other;
}
