﻿package org.kde9.control;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Vector;

import org.kde9.control.RestoreAndBackup.MyRandB;
import org.kde9.control.RestoreAndBackup.RestoreAndBackup;
import org.kde9.control.controller.AllNameController;
import org.kde9.control.controller.CardController;
import org.kde9.control.controller.ControllerFactory;
import org.kde9.control.controller.GroupController;
import org.kde9.model.card.Card;
import org.kde9.model.card.ConstCard;
import org.kde9.model.group.ConstGroup;
import org.kde9.model.group.Group;
import org.kde9.util.ConfigFactory;
import org.kde9.util.Configuration;
import org.kde9.util.Constants;

public class MyKernel 
implements Kernel, Constants {
	private AllNameController names;
	private CardController cards;
	private GroupController groups;
	private RestoreAndBackup randb;
	
	private Vector<String> keys;

	private Configuration configuration;
	
	private static int threadId = 0;
	
	private int SearchThread = 0;

	synchronized public boolean isSearchFinish() {
		if(SearchThread == 0)
			return true;
		else
			return false;
	}
	
	synchronized private void addThreadNum() {
		SearchThread++;
	}
	
	synchronized private void subThreadNum() {
		SearchThread--;
	}

	public MyKernel() {
		configuration = ConfigFactory.creatConfig();
		randb = new MyRandB();
		try {
			randb.checkout();
		} catch (IOException e) {
//			System.err.println("Kernel: checkout error!");
		}
		names = ControllerFactory.createAllNameController();
		cards = ControllerFactory.createCardController();
		groups = ControllerFactory.createGroupController(names.getIds());
	}

	public LinkedHashMap<Integer, String> getAllGroups() {
		return groups.getAllGroups();
	}

	public String getModifyTime(int cardId) {
		File file = new File(CARDPATH + cardId);
		File fileP = new File(CARDPATH + cardId + ".p");
		long a = 0, b = 0;
		if(file.exists())
			a = file.lastModified();
		if(fileP.exists())
			b = file.lastModified();
		Date date;
		if (a > b)
			date = new Date(a);
		else
			date = new Date(b);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateString = formatter.format(date);
		return dateString;
	}
	
	public String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateString = formatter.format(date);
		return dateString;
	}
	
	public ConstCard getCard(int cardId) {
		Card card = cards.getCard(cardId);
		if (card != null) {
			String firstName = names.getFirstName(cardId);
			String lastName = names.getLastName(cardId);
			if (firstName != card.getFirstName()
					|| lastName != card.getLastName())
				cards.renameCard(cardId, firstName, lastName);
		}
		return card;
	}

	public String getFirstName(int cardId) {
		return names.getFirstName(cardId);
	}

	public ConstGroup getGroup(int groupId) {
		return groups.getGroup(groupId);
	}

	public String getLastName(int cardId) {
		return names.getLastName(cardId);
	}
	
	public String getName(int cardId) {
		String name = "";
		try {
			if ((Integer) configuration.getConfig(NAME_FOMAT, CONFIGINT) == 0) {
				name = names.getFirstName(cardId) + " " + names.getLastName(cardId);
			} else {
				name = names.getLastName(cardId) + " " + names.getFirstName(cardId);
			}
		} catch(NullPointerException e) {}
		return name;
	}
	
	public String getName(int cardId, boolean space) {
		if(space)
			return getName(cardId);
		String name = "";
		try {
			if ((Integer) configuration.getConfig(NAME_FOMAT, CONFIGINT) == 0) {
				name = names.getFirstName(cardId) + names.getLastName(cardId);
			} else {
				name = names.getLastName(cardId) + names.getFirstName(cardId);
			}
		} catch(NullPointerException e) {}
		return name;
	}

	public ConstCard addCard(int groupId, String firstName, String lastName,
			LinkedHashMap<String, Vector<String>> items,
			LinkedHashMap<Integer, String> relation) {
		Card card = cards.addCard(firstName, lastName);
		int id = card.getId();
		if (items != null)
			cards.setCardItems(id, items);
		if (relation != null)
			cards.setRelationships(id, relation);
//		System.out.println(card);
		cards.save(id);
		names.addPerson(id, firstName, lastName);
		names.save();
		groups.addGroupMember(GROUPALLID, id);
		// groups.save(GROUPALLID);
		if (groupId != GROUPALLID) {
			groups.addGroupMember(groupId, id);
			groups.save(groupId);
		}
		return card;
	}

	public ConstGroup addGroup(String groupName) {
		Group group = groups.addGroup(groupName);
		groups.save(group);
		return group;
	}

	public boolean addGroupMember(int groupId, int personId) {
		if (groups.addGroupMember(groupId, personId)) {
			if (groupId != GROUPALLID)
				groups.save(groupId);
			return true;
		}
		return false;
	}
	
	public boolean addGroupMember(int groupId, Set<Integer> ids) {
		if (ids != null) {
			groups.addGroupMember(groupId, ids);
			groups.save(groupId);
			return true;
		}
		return false;
	}

	public boolean deleteCard(int personId) {
		for (int groupId : groups.getAllGroups().keySet()) {
			if (groups.deleteGroupMember(groupId, personId)
					&& groupId != GROUPALLID)
				groups.save(groupId);
		}
		names.deletePerson(personId);
		names.save();
		return cards.deleteCard(personId);
	}

	public boolean deleteCard(Set<Integer> personIds) {
		if (personIds != null) {
			for (int groupId : groups.getAllGroups().keySet()) {
				for (int personId : personIds) {
					groups.deleteGroupMember(groupId, personId);
					names.deletePerson(personId);
				}
				if (groupId != GROUPALLID)
					groups.save(groupId);
			}
			names.save();
			cards.deleteCard(personIds);
			return true;
		}
		return false;
	}
	
	public boolean deleteGroupMember(int groupId, int personId) {
		if (groupId != GROUPALLID)
			if (groups.deleteGroupMember(groupId, personId)) {
				groups.save(groupId);
				return true;
			}
		return false;
	}
	
	public boolean deleteGroupMember(int groupId, Set<Integer> ids) {
		if (groupId != GROUPALLID && ids != null) {
			for(int id : ids)
				groups.deleteGroupMember(groupId, id);
			groups.save(groupId);
			return true;
		}
		return false;
	}

	public boolean deleteGroup(int groupId) {
		if (groupId != GROUPALLID)
			return groups.deleteGroup(groupId);
		return false;
	}
	
	public boolean deleteImage(int id) {
		return cards.deleteImage(id);
	}

	public boolean renameGroup(int groupId, String groupName) {
		if (groups.renameGroup(groupId, groupName)) {
			groups.save(groupId);
			return true;
		}
		return false;
	}

	public boolean updateCard(int cardId, String firstName, String lastName,
			LinkedHashMap<String, Vector<String>> items,
			LinkedHashMap<Integer, String> relation) {
		cards.renameCard(cardId, firstName, lastName);
		names.setFirstName(cardId, firstName);
		names.setLastzName(cardId, lastName);
		if (items != null)
			cards.setCardItems(cardId, items);
		if (relation != null)
			cards.setRelationships(cardId, relation);
		cards.save(cardId);
		names.save();
		return true;
	}

	public boolean setCardImage(int cardId, File file) {
		cards.setImage(cardId, file);
		return true;
	}

	private void nextThread() {
		threadId  = (++threadId)%100;
	}
	
	public LinkedHashMap<Integer, String> find(String keyWords) {
		// TODO Auto-generated method stub
		keyWords += " ";
		keys = new Vector<String>();
		for (int i = 0, j = 0; i < keyWords.length(); i++) {
			if (keyWords.charAt(i) == ' ') {
				if (j != i)
					keys.add(keyWords.substring(j, i));
				j = i + 1;
			}
		}
		final LinkedHashMap<Integer, String> temp = new LinkedHashMap<Integer, String>();

		nextThread();
		
		final int thread = threadId;
		addThreadNum();
		new Thread() {
			public void run() {
				for (int id : names.getIds()) {
					if(thread != threadId) {
						subThreadNum();
						return;
					}
					boolean flag = true;
					for (String key : keys) {
						if(thread != threadId) {
							subThreadNum();
							return;
						}
						if (!names.findByName(id, key) &&
								!cards.findByItem(id, null, key, false)) {
							flag = false;
							break;
						}
					}
					if (flag)
						addSearchResult(temp, id, thread, threadId);

					if(keys.size() == 1) {
						if (thread != threadId) {
							subThreadNum();
							return;
						}
						if(cards.findByRelation(id, keys.firstElement(), false))
							addSearchResult(temp, id, thread, threadId);
					}
					
					if(keys.size() == 2) {
						ConstCard card = getCard(id);
						HashMap<Integer, String> rel = card
								.getAllShowRelationship();
						for (int person : rel.keySet()) {
							if (thread != threadId) {
								subThreadNum();
								return;
							}
							if (names.findByName(person, keys.lastElement())
									&& rel.get(person).contains(keys.firstElement())) {
								addSearchResult(temp, id, thread, threadId);
								break;
							}
						}	
					}
//					try {
//						sleep(1000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
//				System.out.println("kernel : " + temp);
				subThreadNum();
//				System.out.println("kernel Thread Num : " + SearchThread);
			}
		}.start();

		return temp;
	}
	
	public boolean findByName(int cardId, String name) {
		return names.findByName(cardId, name);
	}
	
	public Vector<String> getKeys() {
		return keys;
	}

	synchronized void addSearchResult(LinkedHashMap<Integer, String> temp,
			int id, int thread, int threadId) {
		if(thread == threadId)
			temp.put(id, getName(id));
	}

	public static void main(String args[]) {
		Kernel kernel = new MyKernel();
		System.out.println(kernel.getModifyTime(0));
		System.out.println(kernel.getCurrentTime());
//		System.out.println(kernel.find("u"));
	}
}
