package org.kde9.model.card;

import java.util.LinkedHashMap;
import java.util.Vector;

public interface ConstCard {
	/**
	 * ���ϵͳ���������ϵ�˵�id��
	 * <p>
	 * ÿ����ϵ�˾�Ӧӵ����������ϵ�˲�ͬ��id
	 * @return
	 * 		����ϵ�˵�id
	 */
	int getId();
	
	/**
	 * ��ø���ϵ�˵���
	 * @return
	 */
	String getFirstName();
	
	/**
	 * ��ø���ϵ���յ�ƴ��
	 * @return
	 */
	String getPinYinFirstName();
	
	/**
	 * ��ø���ϵ���յ�ƴ����д
	 * @return
	 */
	String getPYFirstName();
	
	/**
	 * ��ø���ϵ�˵���
	 * @return
	 */
	String getLastName();
	
	/**
	 * ��ø���ϵ������ƴ��
	 * @return
	 */
	String getPinYinLastName();
	
	/**
	 * ��ø���ϵ������ƴ����д
	 * @return
	 */
	String getPYLastName();
	
	/**
	 * ��ø���ϵ�����еı���
	 * @return
	 * 		����ļ�ֵ��
	 */
	LinkedHashMap<String, Vector<String>> getAllItems();
	
	/**
	 * ��ø���ϵ����ָ�������ֵ
	 * @param itemName
	 * 		ָ������ļ�
	 * @return
	 * 		ָ�������ֵ����δ�ҵ�ָ���ļ��򷵻�null
	 */
	Vector<String> getItem(String itemName);
	
	/**
	 * ��ø���ϵ�����е����Թ�ϵ
	 * @return
	 * 		ÿ����ϵ�еĹ����˵�id�͹�ϵ�����Ͷ�
	 */
	LinkedHashMap<Integer, String> getAllShowRelationship();
	
	/**
	 * ��ø���ϵ����ָ����ϵ�˵Ĺ�ϵ
	 * @param cardId
	 * 		ָ����ϵ�˵�id
	 * @return
	 * 		��ϵ
	 */
	String getShowRelationship(int cardId);
}
