package org.kde9.model.allname;

import java.util.HashSet;
import java.util.Set;

/**
 * 保存所有联系人的id与名字
 * <p>
 * 用于保存联系人的id与名字的对应，以便快速检索。
 * 
 * @author kfirst
 */
public interface AllName {
	/**
	 * 获得联系人的姓
	 * <p>
	 * 通过指定联系人的id，获得联系人的姓。
	 * @param id
	 * 		联系人的id
	 * @return
	 * 		联系人的姓
	 */
	public String getFirstName(int id);
	
	/**
	 * 获得联系人的名
	 * <p>
	 * 通过指定联系人的id，获得联系人的名。
	 * @param id
	 * 		联系人的id
	 * @return
	 * 		联系人的名
	 */
	public String getLastName(int id);
	
	/**
	 * 修改联系人的姓
	 * @param newName
	 * 		联系人的新姓
	 * @return
	 * 		修改是否成功，成功返回true。如果参数newFirst为null，则返回false
	 */
	boolean setFirstName(int id, String newFirst);
	
	/**
	 * 修改联系人的名
	 * @param newLast
	 * 		联系人的新名
	 * @return
	 * 		修改是否成功，成功返回true。如果参数newLast为null，则返回false
	 */
	boolean setLastName(int id, String newLast);
	
	/**
	 * 向姓名表中增加联系人
	 * @param id
	 * 		联系人的id
	 * @param firstName
	 * 		联系人的姓
	 * @param lastName
	 * 		联系人的名
	 * @return
	 * 		添加是否成功，成功返回true
	 */
	public boolean addPerson(int id, String firstName, String lastName);
	
	/**
	 * 从姓名表中删除联系人
	 * @param id
	 * 		要删除的联系人的id
	 * @return
	 * 		删除是否成功，成功返回true
	 */
	public boolean deletePerson(int id);
	
	public Set<Integer> getIds();
}
