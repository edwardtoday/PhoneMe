package org.kde9.util;

public interface Constants {
	//************************************************************************
	// �ļ�·�����ļ���
	static String DATAPATH = "./datafiles/";
	static String GROUPPATH = "./datafiles/groupfiles/";
	static String CARDPATH = "./datafiles/cardfiles/";
	
	static String CONFIGURATION = "configuration";
	
	/**
	 * �����������ֵ��ļ����ļ���
	 */
	static String ALLNAMES = "AllNames";
	
	//************************************************************************	
	// ϵͳ��������
	/**
	 * �����ڿ�
	 */
	static String MAIN_FRAME_WIDTH = "MAIN_FRAME_WIDTH";

	/**
	 * �����ڸ�
	 */
	static String MAIN_FRAME_HEIGHT = "MAIN_FRAME_HEIGHT";
	
	/**
	 * ������ʾ��ʽ
	 */
	static String NAME_FOMAT = "NAME_FOMAT";
	//************************************************************************
	//�����ļ�����
	static String[] config = {
		MAIN_FRAME_HEIGHT, "500",
		MAIN_FRAME_WIDTH, "800",
		NAME_FOMAT, "0",
	};
	
	//************************************************************************
	//�ļ���ʽ���
	/**
	 * ���з�
	 */
	static String NEWLINE = System.getProperty("line.separator");
	
	/**
	 * δ��������Ƭ��Ĭ������
	 */
	static String NULLCARDNAME = "Null";
	
	//************************************************************************
	//����������
	static int CONFIGINT = 0;
	static int CONFIGSTRING = 1;
}
