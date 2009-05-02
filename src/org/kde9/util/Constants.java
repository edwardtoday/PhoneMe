package org.kde9.util;

public interface Constants {
	//************************************************************************
	// 文件路径及文件名
	static String DATAPATH = "./datafiles/";
	static String GROUPPATH = "./datafiles/groupfiles/";
	static String CARDPATH = "./datafiles/cardfiles/";
	
	static String CONFIGURATION = "configuration";
	
	/**
	 * 保存所有名字的文件的文件名
	 */
	static String ALLNAMES = "AllNames";
	
	//************************************************************************	
	// 系统设置名称
	/**
	 * 主窗口宽
	 */
	static String MAIN_FRAME_WIDTH = "MAIN_FRAME_WIDTH";

	/**
	 * 主窗口高
	 */
	static String MAIN_FRAME_HEIGHT = "MAIN_FRAME_HEIGHT";
	
	/**
	 * 名字显示方式
	 */
	static String NAME_FOMAT = "NAME_FOMAT";
	//************************************************************************
	//配置文件内容
	static String[] config = {
		MAIN_FRAME_HEIGHT, "500",
		MAIN_FRAME_WIDTH, "800",
		NAME_FOMAT, "0",
	};
	
	//************************************************************************
	//文件格式相关
	/**
	 * 换行符
	 */
	static String NEWLINE = System.getProperty("line.separator");
	
	/**
	 * 未命名的名片的默认名字
	 */
	static String NULLCARDNAME = "Null";
	
	//************************************************************************
	//配置项类型
	static int CONFIGINT = 0;
	static int CONFIGSTRING = 1;
}
