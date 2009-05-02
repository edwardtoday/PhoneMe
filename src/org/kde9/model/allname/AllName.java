package org.kde9.model.allname;

/**
 * ����������ϵ�˵�id������
 * <p>
 * ���ڱ�����ϵ�˵�id�����ֵĶ�Ӧ���Ա���ټ�����
 * 
 * @author kfirst
 */
public interface AllName {
	/**
	 * �����ϵ�˵���
	 * <p>
	 * ͨ��ָ����ϵ�˵�id�������ϵ�˵��ա�
	 * @param id
	 * 		��ϵ�˵�id
	 * @return
	 * 		��ϵ�˵���
	 */
	public String getFirstName(int id);
	
	/**
	 * �����ϵ�˵���
	 * <p>
	 * ͨ��ָ����ϵ�˵�id�������ϵ�˵�����
	 * @param id
	 * 		��ϵ�˵�id
	 * @return
	 * 		��ϵ�˵���
	 */
	public String getLastName(int id);
	
	/**
	 * �޸���ϵ�˵���
	 * @param newName
	 * 		��ϵ�˵�����
	 * @return
	 * 		�޸��Ƿ�ɹ����ɹ�����true���������newFirstΪnull���򷵻�false
	 */
	boolean setFirstName(int id, String newFirst);
	
	/**
	 * �޸���ϵ�˵���
	 * @param newLast
	 * 		��ϵ�˵�����
	 * @return
	 * 		�޸��Ƿ�ɹ����ɹ�����true���������newLastΪnull���򷵻�false
	 */
	boolean setLastName(int id, String newLast);
	
	/**
	 * ����������������ϵ��
	 * @param id
	 * 		��ϵ�˵�id
	 * @param firstName
	 * 		��ϵ�˵���
	 * @param lastName
	 * 		��ϵ�˵���
	 * @return
	 * 		����Ƿ�ɹ����ɹ�����true
	 */
	public boolean addPerson(int id, String firstName, String lastName);
	
	/**
	 * ����������ɾ����ϵ��
	 * @param id
	 * 		Ҫɾ������ϵ�˵�id
	 * @return
	 * 		ɾ���Ƿ�ɹ����ɹ�����true
	 */
	public boolean deletePerson(int id);
}
