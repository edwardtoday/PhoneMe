package org.kde9.model;

import java.io.IOException;
import java.util.LinkedHashSet;

public interface InterfaceAllGroups {
	
	public abstract void appendGroup(int id);

	public abstract void deleteGroup(int id);

	public abstract LinkedHashSet<Integer> getGroupIds();
	
	/**
	 * 保存AllGroups的信息到相应的文件。
	 * <p>
	 * 保存的内容为每个组的id，每个id为一行。
	 * 文件结尾会有换行符。
	 */
	public abstract void save()
	throws IOException;

}