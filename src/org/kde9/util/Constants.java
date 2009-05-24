package org.kde9.util;

public interface Constants {
	//************************************************************************
	// 文件路径及文件名
	static String DATAPATH = "./datafiles/";
	static String GROUPPATH = "./datafiles/groupfiles/";
	static String CARDPATH = "./datafiles/cardfiles/";
	
	static String CONFIGURATION = "configuration";
	
	static String IMGPATH = "/img/";
	
	static String NULLIMAGE = "nullImag.gif";
	
	static int GROUPALLID = 0;
	
	/**
	 * 未命名的名片的默认名字
	 */
	static String NULLCARDNAME = "Null";
	
	static String NULLGROUPNAME = "no name";
	
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
	
	static String GROUP_ALL_NAME = "GROUP_ALL_NAME";
	//************************************************************************
	//配置文件内容
	static String[] config = {
		MAIN_FRAME_HEIGHT, "500",
		MAIN_FRAME_WIDTH, "800",
		GROUP_ALL_NAME, "ALL",
		NAME_FOMAT, "0",
	};
	
	//************************************************************************
	//文件格式相关
	/**
	 * 换行符
	 */
	static String NEWLINE = System.getProperty("line.separator");
	
	/**
	 * 文件中区分不同字段的分隔符 如分割Item和persons
	 */
	static String SEPERATOR = "!$$%)&(~()(!$#$%#";

	/**
	 * 名片中不同item的分隔符 如分割电话和Email
	 */
	static String ITEMSEPERATOR = "@@$-=&{*)<?':#}{)($";
	
	static String VALUESEPERSTOR = "*((***&^&%^^%%^??+)";
	
	/**
	 * 未赋值的Item的默认值
	 */
	static String NULLITEMCONTENT = "Null";
	
	//************************************************************************
	//配置项类型
	static int CONFIGINT = 0;
	static int CONFIGSTRING = 1;
	
	//************************************************************************
	//删除类型
	/*
	 * 删除组
	 */
	static int DELETEGROUP = 0;
	/*
	 * 删除card
	 */
	static int DELETECARD = 1;
	/*
	 * 将card从组中移出
	 */
	static int DELETEFROMGROUP = 2;
	//************************************************************************
	//导出类型
	/*
	 * 导出所有
	 */
	static int EXPORT = 0;
	/*
	 * 导出card
	 */
	static int EXPORTCARD = 1;
	/*
	 * 导出组
	 */
	static int EXPORTGROUP = 2;
	//************************************************************************
	//导入类型
	/*
	 * 导入到ALL
	 */
	static int IMPORT = 0;
	/*
	 * 导入到某组
	 */
	static int IMPORTTOGROUP = 1;
	/*
	 * 导入新组
	 */
	static int IMPORTNEWGROUP = 2;
}
