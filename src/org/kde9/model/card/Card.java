package org.kde9.model.card;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Vector;

/**
 * Card���ݽ���Ľӿ�
 * <p>
 * ����ά��Card���ݽ����
 * Card���ݽṹ����Ҫ�������ֲ��֣�����ֺ͹�ϵ���֡�
 * <p>
 * ���ֲ��������洢��ϵ�˵����֣������ա����Լ���Ӧ��ƴ���ȡ�
 * <br>
 * ����������洢��ϵ�˵�������Ϣ����绰�����յȡ�
 * һ������һ��Ϊ�����ĸ�ʽ��[�绰: 13401021234 51534418]��
 * ���а�����һ������ļ����硰�绰�������ɸ������ֵ���硰13401021234������51534418����
 * ����ֿ�����һ���������
 * <br>
 * ��ϵ�������ڴ洢��ϵ����������ϵ�˵Ĺ�ϵ��
 * ��ϵ��������ϵ���Թ�ϵ��
 * �Թ�ϵ�Ĵ洢���������֣�һ������ϵ���й�ϵ���˵�id��һ�ǹ�ϵ�����������ѡ�ͬѧ�ȣ���
 * ����ϵ�Ĵ洢ֻ��id��
 * ���磺����û���A����ΪB�ġ����ѡ�����δ��B����ΪA���κι�ϵ����
 * B�а���һ������Ϊ�����ѡ���idΪA��id���Թ�ϵ����A�а���һ��idΪB������ϵ��
 * @author kfirst
 */
public interface Card 
extends ConstCard {
	/**
	 * �޸���ϵ�˵���
	 * @param newName
	 * 		��ϵ�˵�����
	 * @return
	 * 		�޸��Ƿ�ɹ����ɹ�����true���������newFirstΪnull���򷵻�false
	 */
	boolean setFirstName(String newFirst);
	
	/**
	 * �޸���ϵ�˵���
	 * @param newLast
	 * 		��ϵ�˵�����
	 * @return
	 * 		�޸��Ƿ�ɹ����ɹ�����true���������newLastΪnull���򷵻�false
	 */
	boolean setLastName(String newLast);
	
	/**
	 * �����ϵ�˵ı���
	 * <p>
	 * ��������Ѵ��ڣ�Ӧ�ڸñ��������һ��ֵ������ֵ�Ѵ��������ʧ�ܡ�
	 * ���������ڣ����½��ñ������ֵ��
	 * @param key
	 * 		��ӱ���ļ�
	 * @param value
	 * 		��ӱ����ֵ
	 * @return
	 * 		����Ƿ�ɹ����ɹ�����true��������keyΪnull���򷵻�false
	 */
	boolean addItem(String key, String value);
	
	boolean addItem(String key, Vector<String> values);
	
	/**
	 * ���ñ����ֵ
	 * <p>
	 * ���ñ����ֵΪָ����ֵ�����ñ���ļ�������������ʧ�ܡ�
	 * @param key
	 * 		Ҫ���õı���
	 * @param values
	 * 		Ҫ���õ�ֵ
	 * @return
	 * 		�����Ƿ�ɹ����ɹ�����true��������keyΪnull���򷵻�false
	 */
	boolean setItem(String key, Vector<String> values);
	
	/**
	 * ���ñ����ֵ
	 * <p>
	 * ����ָ�������е�ĳ��ֵ������ֵ����������ֵ�滻����������������ʧ�ܡ�
	 * @param key
	 * 		����ļ�
	 * @param oldValue
	 * 		�ɵı���ֵ
	 * @param newValue
	 * 		�µı���ֵ
	 * @return
	 * 		�����Ƿ�ɹ����ɹ�����true��������keyΪnull���򷵻�false
	 */
	boolean setItem(String key, String oldValue, String newValue);
	
	/**
	 * ������ϵ�˵ı���
	 * @param items
	 * 		�µı���
	 * @return
	 * 		�����Ƿ�ɹ����ɹ�����true
	 */
	boolean setItems(LinkedHashMap<String, Vector<String>> items);
	
	/**
	 * ɾ����ϵ�˵ı���
	 * <p>
	 * �������м�λkey�����е�ֵΪvalue��ֵɾ����δ�ҵ���Ӧ��value��ɾ��ʧ�ܡ�
	 * ���valueΪnull���򽫱����м�Ϊkey����͸����ȫ��ֵɾ����
	 * @param key
	 * 		Ҫɾ������
	 * @param value
	 * 		Ҫɾ����ֵ
	 * @return
	 * 		ɾ���Ƿ�ɹ����ɹ�����true��������keyΪnull���򷵻�false
	 */
	boolean deleteItem(String key, String value);
	
	/**
	 * �����ϵ�˵�������ϵ
	 * @return
	 */
	HashSet<Integer> getAllHideRelationship();
	
	/**
	 * ����Թ�ϵ
	 * @param id
	 * @param content
	 * @return
	 */
	boolean addShowRelationship(int id, String content);
	
	/**
	 * �������ϵ
	 * @param id
	 * @return
	 */
	boolean addHideRelationship(int id);
	
	/**
	 * ɾ���Թ�ϵ
	 * @param id
	 * @return
	 */
	boolean deleteShowRelationship(int id);
	
	/**
	 * ɾ������ϵ
	 * @param id
	 * @return
	 */
	boolean deleteHideRelationship(int id);
	
	/**
	 * �޸��Թ�ϵ
	 * @param id
	 * @param content
	 * @return
	 */
	boolean setShowRelationship(int id, String content);
	
	boolean setScaleImage(BufferedImage scaleImage);
	
	boolean setImage(BufferedImage image);
	
	boolean setImageReady(boolean ready);
}
