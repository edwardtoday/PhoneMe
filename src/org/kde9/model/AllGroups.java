package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

interface AllGroups {

	/**
	 * 初始化方法，别忘了调用！
	 * @throws IOException 
	 * @throws FileNotFoundException 文件系统中保存AllGroups的文件不存在或被破坏，
	 * 可以使用restore方法试图恢复（应提示用户文件恢复并不能保证数据不丢失）。
	 */
	public void init()
	throws IOException, FileNotFoundException;
	
	public abstract void appendToAllGroup(int id);

	public abstract void deleteFromAllGroup(int id);

	public abstract ArrayList<Integer> getIds();
	
	/**
	 * 保存AllGroups的信息到相应的文件。
	 * <p>
	 * 保存的内容为每个组的id，每个id为一行。
	 * 文件结尾会有换行符。
	 */
	public void save()
	throws IOException;
	
	public void restore()
	throws IOException;

}