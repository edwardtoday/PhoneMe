package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

interface AllGroups {

	/**
	 * ��ʼ�������������˵��ã�
	 * @throws IOException 
	 * @throws FileNotFoundException �ļ�ϵͳ�б���AllGroups���ļ������ڻ��ƻ���
	 * ����ʹ��restore������ͼ�ָ���Ӧ��ʾ�û��ļ��ָ������ܱ�֤���ݲ���ʧ����
	 */
	public void init()
	throws IOException, FileNotFoundException;
	
	public abstract void appendToAllGroup(int id);

	public abstract void deleteFromAllGroup(int id);

	public abstract ArrayList<Integer> getIds();
	
	/**
	 * ����AllGroups����Ϣ����Ӧ���ļ���
	 * <p>
	 * ���������Ϊÿ�����id��ÿ��idΪһ�С�
	 * �ļ���β���л��з���
	 */
	public void save()
	throws IOException;
	
	public void restore()
	throws IOException;

}