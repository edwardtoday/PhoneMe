package org.kde9.model.card;

import java.util.LinkedHashMap;
import java.util.Vector;

public interface ConstCard {
	/**
	 * 获得系统分配给该联系人的id。
	 * <p>
	 * 每个联系人均应拥有与其他联系人不同的id
	 * @return
	 * 		该联系人的id
	 */
	int getId();
	
	/**
	 * 获得该联系人的姓
	 * @return
	 */
	String getFirstName();
	
	/**
	 * 获得该联系人姓的拼音
	 * @return
	 */
	String getPinYinFirstName();
	
	/**
	 * 获得该联系人姓的拼音缩写
	 * @return
	 */
	String getPYFirstName();
	
	/**
	 * 获得该联系人的名
	 * @return
	 */
	String getLastName();
	
	/**
	 * 获得该联系人名的拼音
	 * @return
	 */
	String getPinYinLastName();
	
	/**
	 * 获得该联系人名的拼音缩写
	 * @return
	 */
	String getPYLastName();
	
	/**
	 * 获得该联系人所有的表项
	 * @return
	 * 		表项的键值对
	 */
	LinkedHashMap<String, Vector<String>> getAllItems();
	
	/**
	 * 获得该联系人中指定表项的值
	 * @param itemName
	 * 		指定表项的键
	 * @return
	 * 		指定表项的值，若未找到指定的键则返回null
	 */
	Vector<String> getItem(String itemName);
	
	/**
	 * 获得该联系人所有的显性关系
	 * @return
	 * 		每个关系中的关联人的id和关系的类型对
	 */
	LinkedHashMap<Integer, String> getAllShowRelationship();
	
	/**
	 * 获得该联系人与指定联系人的关系
	 * @param cardId
	 * 		指定联系人的id
	 * @return
	 * 		关系
	 */
	String getShowRelationship(int cardId);
}
