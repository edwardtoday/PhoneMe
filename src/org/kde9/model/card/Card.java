package org.kde9.model.card;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Vector;

/**
 * Card数据结果的接口
 * <p>
 * 负责维护Card数据结果，
 * Card数据结构中主要包括名字部分，表项部分和关系部分。
 * <p>
 * 名字部分用来存储联系人的名字，包括姓、名以及相应的拼音等。
 * <br>
 * 表项部分用来存储联系人的其他信息，如电话、生日等。
 * 一个表项一般为这样的格式：[电话: 13401021234 51534418]。
 * 其中包括，一个表项的键，如“电话”；若干个表项的值，如“13401021234”、“51534418”。
 * 表项部分可以有一个或多个表项。
 * <br>
 * 关系部分用于存储联系人与其他联系人的关系。
 * 关系包括隐关系和显关系。
 * 显关系的存储包括两部分，一是与联系人有关系的人的id，一是关系的描述（朋友、同学等）。
 * 隐关系的存储只有id。
 * 例如：如果用户将A设置为B的“朋友”，但未将B设置为A的任何关系，则
 * B中包括一个描述为“朋友”、id为A的id的显关系，而A中包括一个id为B的隐关系。
 * @author kfirst
 */
public interface Card 
extends ConstCard {
	/**
	 * 修改联系人的姓
	 * @param newName
	 * 		联系人的新姓
	 * @return
	 * 		修改是否成功，成功返回true。如果参数newFirst为null，则返回false
	 */
	boolean setFirstName(String newFirst);
	
	/**
	 * 修改联系人的名
	 * @param newLast
	 * 		联系人的新名
	 * @return
	 * 		修改是否成功，成功返回true。如果参数newLast为null，则返回false
	 */
	boolean setLastName(String newLast);
	
	/**
	 * 添加联系人的表项
	 * <p>
	 * 如果表项已存在，应在该表项后增加一个值；若该值已存在则添加失败。
	 * 如果表项不存在，则新建该表项并增加值。
	 * @param key
	 * 		添加表项的键
	 * @param value
	 * 		添加表项的值
	 * @return
	 * 		添加是否成功，成功返回true。若参数key为null，则返回false
	 */
	boolean addItem(String key, String value);
	
	boolean addItem(String key, Vector<String> values);
	
	/**
	 * 设置表项的值
	 * <p>
	 * 设置表项的值为指定的值，若该表项的键不存在则设置失败。
	 * @param key
	 * 		要设置的表项
	 * @param values
	 * 		要设置的值
	 * @return
	 * 		设置是否成功，成功返回true。若参数key为null，则返回false
	 */
	boolean setItem(String key, Vector<String> values);
	
	/**
	 * 设置表项的值
	 * <p>
	 * 设置指定表项中的某个值，若该值存在则用新值替换，若不存在则设置失败。
	 * @param key
	 * 		表项的键
	 * @param oldValue
	 * 		旧的表项值
	 * @param newValue
	 * 		新的表项值
	 * @return
	 * 		设置是否成功，成功返回true。若参数key为null，则返回false
	 */
	boolean setItem(String key, String oldValue, String newValue);
	
	/**
	 * 设置联系人的表项
	 * @param items
	 * 		新的表项
	 * @return
	 * 		设置是否成功，成功返回true
	 */
	boolean setItems(LinkedHashMap<String, Vector<String>> items);
	
	/**
	 * 删除联系人的表项
	 * <p>
	 * 将表项中键位key的项中的值为value的值删除，未找到相应的value则删除失败。
	 * 如果value为null，则将表项中键为key的项和该项的全部值删除。
	 * @param key
	 * 		要删除的项
	 * @param value
	 * 		要删除的值
	 * @return
	 * 		删除是否成功，成功返回true。若参数key为null，则返回false
	 */
	boolean deleteItem(String key, String value);
	
	/**
	 * 获得联系人的隐含关系
	 * @return
	 */
	HashSet<Integer> getAllHideRelationship();
	
	/**
	 * 添加显关系
	 * @param id
	 * @param content
	 * @return
	 */
	boolean addShowRelationship(int id, String content);
	
	/**
	 * 添加隐关系
	 * @param id
	 * @return
	 */
	boolean addHideRelationship(int id);
	
	/**
	 * 删除显关系
	 * @param id
	 * @return
	 */
	boolean deleteShowRelationship(int id);
	
	/**
	 * 删除隐关系
	 * @param id
	 * @return
	 */
	boolean deleteHideRelationship(int id);
	
	/**
	 * 修改显关系
	 * @param id
	 * @param content
	 * @return
	 */
	boolean setShowRelationship(int id, String content);
	
	boolean setScaleImage(BufferedImage scaleImage);
	
	boolean setImage(BufferedImage image);
	
	boolean setImageReady(boolean ready);
}
