package org.kde9.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public interface InterfaceCard {

	public abstract void rename(String name);

	public abstract void appendItem(String name, String content);

	public abstract void deleteItem(String name);

	public abstract void renameItem(String oldName, String newName);

	public abstract void appendPerson(int id, String name);

	public abstract void deletePerson(int id);
	
	public int getCardId();

	public String getName();

	public LinkedHashMap<String, String> getItems();

	public HashMap<Integer, String> getRelationship();
	
	/**
	 * 保存名片
	 * <p>
	 * 保存格式为，
	 * 文件名：id；
	 * 第一行：姓名；
	 * 接下来每两行为一组：item的两个String字段；
	 * 标志位：SEPERATOR;
	 * 接下来每两行为一组：persons的两个字段；
	 * 最后会有一行空行。
	 * @throws IOException
	 */
	public abstract void save()
	throws IOException;

	public abstract void delete();

}