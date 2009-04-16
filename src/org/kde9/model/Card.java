package org.kde9.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

interface Card {

	public abstract void rename(String name);

	public abstract void appendItem(String name, String content);

	public abstract void deleteItem(String name);

	public abstract void renameItem(String oldName, String newName);

	public abstract void appendPerson(int id, String name);

	public abstract void deletePerson(int id);

	/**
	 * ���Һ���
	 * @param item
	 * 		Ҫ���ҵ�����Ϊ�ձ�ʾ����������
	 * @param content
	 * 		Ҫ���ҵ��������
	 * @param wholeWord
	 * 		�Ƿ�ȫ�ַ�ƥ�䣬true��ʾȫ�ַ�ƥ��
	 * @return
	 * 		���ҵ��������������true�����򷵻�false
	 */
	public boolean find(String item, String content, boolean wholeWord);
	
	public int getCardId();

	public String getName();

	public LinkedHashMap<String, String> getItems();

	public HashMap<Integer, String> getRelationship();
	
	/**
	 * ������Ƭ
	 * <p>
	 * �����ʽΪ��
	 * �ļ�����id��
	 * ��һ�У�������
	 * ������ÿ����Ϊһ�飺item������String�ֶΣ�
	 * ��־λ��SEPERATOR;
	 * ������ÿ����Ϊһ�飺persons�������ֶΣ�
	 * ������һ�п��С�
	 * @throws IOException
	 */
	public abstract void save() throws IOException;

	public abstract void delete();

}