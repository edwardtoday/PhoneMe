package org.kde9.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

interface Card {

	public abstract void rename(String name);

	public abstract void appendItem(String name, String content);

	public abstract void deleteItem(String name);

	public abstract void renameItem(String oldName, String newName);

	public abstract void appendPerson(int id, String name);

	public abstract void deletePerson(int id);

	/**
	 * 查找函数
	 * @param item
	 * 		要查找的项，如果为空表示查找所有项
	 * @param content
	 * 		要查找的项的内容
	 * @param wholeWord
	 * 		是否全字符匹配，true表示全字符匹配
	 * @return
	 * 		若找到符合条件的项返回true，否则返回false
	 */
	public boolean find(String item, String content, boolean wholeWord);
	
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
	public abstract void save() throws IOException;

	public abstract void delete();

}