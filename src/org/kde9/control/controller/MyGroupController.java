﻿package org.kde9.control.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.kde9.control.FileOperation.DeleteFile;
import org.kde9.control.FileOperation.ReadFile;
import org.kde9.model.ModelFactory;
import org.kde9.model.group.ConstGroup;
import org.kde9.model.group.Group;
import org.kde9.util.ConfigFactory;
import org.kde9.util.Configuration;
import org.kde9.util.Constants;

public class MyGroupController 
implements GroupController, Constants {
	private int staticId = 1;
	private HashMap<Integer, Group> groups;
	private LinkedHashMap<Integer, String> groupNames;
	//private Save save;
	
	private Configuration configuration;

	/**
	 * 判断一个字符串是否为数字
	 * <p>
	 * 字符串为空串或非数字串将返回false。
	 * 
	 * @param str
	 * 		要判断的字符串
	 */
	private boolean isInt(String str) {
		// 为空串返回false
		if (str == null || str.length() == 0)
			return false;
		for (char c : str.toCharArray())
			// 非数字返回false
			if (!Character.isDigit(c))
				return false;
		return true;
	}
	
	private Group initGroup(File file, int id) {
		Group g = ModelFactory.createGroup(id);
		try {
			ReadFile rf = new ReadFile(file);
			String member;
			member = rf.readLine();
			if(member == null || member == "")
				member = NULLGROUPNAME;
			g.setGroupName(member);
			groupNames.put(id, member);
			member = rf.readLine();
			while(true) {
				if(member == null)
					break;
				if(!isInt(member)) {
					member = rf.readLine();
					continue;
				}
				g.addGroupMember(Integer.valueOf(member));
				member = rf.readLine();
			}
			rf.close();
		} catch (FileNotFoundException e) {
			System.err.println("MyGroup: initGroup error! File " + id + " not find!");
		} catch (IOException e) {
			System.err.println("MyGroup: initGroup error! IOException!");
		}
		return g;
	}
	
	public MyGroupController(LinkedHashSet<Integer> ids) {
		configuration = ConfigFactory.creatConfig();
		
		groups = new HashMap<Integer, Group>();
		groupNames = new LinkedHashMap<Integer, String>();
		File groupPath = new File(GROUPPATH);
		if(!groupPath.isDirectory())
			return;
		Group group = ModelFactory.createGroup(GROUPALLID);
		String groupName = (String) configuration.getConfig(GROUP_ALL_NAME, CONFIGSTRING);
		group.setGroupName(groupName);
		group.setGroupMembers(ids);
		groups.put(GROUPALLID, group);
		groupNames.put(GROUPALLID, groupName);
		for(File file : groupPath.listFiles()) {
			if(file.isFile() && isInt(file.getName()) && file.getName().length() < 6) {
				int id = Integer.valueOf(file.getName());
				if(id != GROUPALLID)
					groups.put(id, initGroup(file, id));
			}
		}

		// TODO
	}

	public Group addGroup(String groupName) {
		File file = new File(GROUPPATH + staticId);
		while(file.exists())
			file = new File(GROUPPATH + ++staticId);
		Group group = ModelFactory.createGroup(staticId);
		group.setGroupName(groupName);
		groups.put(staticId, group);
		return group;
	}

	public boolean addGroupMember(int groupId, int personId) {
		Group group = groups.get(groupId);
		if(group != null) {
			return group.addGroupMember(personId);
		}
		return false;
	}

	public boolean deleteGroup(int groupId) {
		DeleteFile df = new DeleteFile(GROUPPATH + groupId);
		df.delete();
		return true;
	}

	public boolean deleteGroupMember(int groupId, int personId) {
		Group group = groups.get(groupId);
		if(group != null) {
			return group.deleteGroupMember(personId);
		}
		return false;
	}

	public LinkedHashMap<Integer, String> getAllGroups() {
		return groupNames;
	}

	public ConstGroup getGroup(int groupId) {
		return groups.get(groupId);
	}

	public boolean renameGroup(int groupId, String newName) {
		Group group = groups.get(groupId);
		if(group != null) {
			group.setGroupName(newName);
			return true;
		}
		return false;
	}

	public boolean save(int groupId) {
		Group group = groups.get(groupId);
		Save save = new Save();
		save.init(group);
		return save.save();
	}

	public boolean save(ConstGroup group) {
		Save save = new Save();
		save.init(group);
		return save.save();
	}

	public boolean addGroupMember(int groupId, Set<Integer> ids) {
		Group group = groups.get(groupId);
		if(group != null && ids != null) {
			for(int id : ids)
				group.addGroupMember(id);
			return true;
		}
		return false;
	}
}
