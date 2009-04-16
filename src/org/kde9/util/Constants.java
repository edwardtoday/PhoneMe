package org.kde9.util;

/**
 * ���ڱ��泣��
 */
public interface Constants {
	
//����������	
//*************************************************************************
	/**
	 * �����ڿ�
	 */
	static int MAIN_FRAME_WIDTH = 600;
	
	/**
	 * �����ڸ�
	 */
	static int MAIN_FRAME_HEIGHT = 400;
	
	
//�ļ�·�����ļ���
//*************************************************************************
	/**
	 * �����ļ�·��
	 */
	static String DATA_PATH = "./datafiles/";
	
	/**
	 * ���ļ�·��
	 */
	static String GROUP_PATH = "./datafiles/groupfiles/"; 
	
	/**
	 * ��Ƭ�ļ�·��
	 */
	static String CARD_PATH = "./datafiles/cardfiles/";
	
	/**
	 * ����������id���ļ����ļ���
	 */
	static String ALLGROUPS = "AllGroups";
	
	/**
	 * �����������ֵ��ļ����ļ���
	 */
	static String ALLNAMES = "AllNames";
	
	
//�ļ���ʽ���
//*************************************************************************
	/**
	 * Ĭ�Ϸ���All��id
	 * String��
	 */
	static String ALLIDSTRING = "0";
	
	/**
	 * Ĭ�Ϸ���All��id
	 * int��
	 */
	static int ALLIDINT = 0;
	
	/**
	 * Ĭ�Ϸ���All��Name
	 * String��
	 */
	static String ALLGROUPNAME = "ALL";
	
	/**
	 * δ���������Ĭ������
	 */
	static String NULLGROUPNAME = "Null";
	
	/**
	 * δ��������Ƭ��Ĭ������
	 */
	static String NULLCARDNAME = "Null";
	
	/**
	 * δ��ֵ��Item��Ĭ��ֵ
	 */
	static String NULLITEMCONTENT = "Null";
	
	/**
	 * �ļ������ֲ�ͬ�ֶεķָ���
	 * ��ָ�Item��persons
	 */
	static String SEPERATOR = "!$$%)&^(~()(%]$!";
	
	/**
	 * �����˹�ϵ�����ڱ�ʾ������ϵ�ı�ʶ��
	 */
	static String HIDEPERSON = "@#$%^!{{%)>|$(*";
	
	/**
	 * ��Ƭ�в�ͬitem�ķָ���
	 * ��ָ�绰��Email
	 */
	static String ITEMSEPERATOR = "@@$-=\\*&{*)<?':#";
	
	/**
	 * ���з�
	 */
	static String NEWLINE = System.getProperty("line.separator");
	
}
