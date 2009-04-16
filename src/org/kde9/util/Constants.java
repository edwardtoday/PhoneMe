package org.kde9.util;

/**
 * 用于保存常数
 */
public interface Constants {
	
//主窗口设置	
//*************************************************************************
	/**
	 * 主窗口宽
	 */
	static int MAIN_FRAME_WIDTH = 600;
	
	/**
	 * 主窗口高
	 */
	static int MAIN_FRAME_HEIGHT = 400;
	
	
//文件路径与文件名
//*************************************************************************
	/**
	 * 数据文件路径
	 */
	static String DATA_PATH = "./datafiles/";
	
	/**
	 * 组文件路径
	 */
	static String GROUP_PATH = "./datafiles/groupfiles/"; 
	
	/**
	 * 名片文件路径
	 */
	static String CARD_PATH = "./datafiles/cardfiles/";
	
	/**
	 * 保存所有组id的文件的文件名
	 */
	static String ALLGROUPS = "AllGroups";
	
	/**
	 * 保存所有名字的文件的文件名
	 */
	static String ALLNAMES = "AllNames";
	
	
//文件格式相关
//*************************************************************************
	/**
	 * 默认分组All的id
	 * String型
	 */
	static String ALLIDSTRING = "0";
	
	/**
	 * 默认分组All的id
	 * int型
	 */
	static int ALLIDINT = 0;
	
	/**
	 * 默认分组All的Name
	 * String型
	 */
	static String ALLGROUPNAME = "ALL";
	
	/**
	 * 未命名的组的默认名字
	 */
	static String NULLGROUPNAME = "Null";
	
	/**
	 * 未命名的名片的默认名字
	 */
	static String NULLCARDNAME = "Null";
	
	/**
	 * 未赋值的Item的默认值
	 */
	static String NULLITEMCONTENT = "Null";
	
	/**
	 * 文件中区分不同字段的分隔符
	 * 如分割Item和persons
	 */
	static String SEPERATOR = "!$$%)&^(~()(%]$!";
	
	/**
	 * 人与人关系中用于表示隐含关系的标识符
	 */
	static String HIDEPERSON = "@#$%^!{{%)>|$(*";
	
	/**
	 * 名片中不同item的分隔符
	 * 如分割电话和Email
	 */
	static String ITEMSEPERATOR = "@@$-=\\*&{*)<?':#";
	
	/**
	 * 换行符
	 */
	static String NEWLINE = System.getProperty("line.separator");
	
}
