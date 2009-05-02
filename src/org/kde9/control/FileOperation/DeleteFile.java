package org.kde9.control.FileOperation;

import java.io.File;

/**
 * ���ݲ����ײ�
 * <p>
 * ��װ���ļ�ɾ���Ļ���������
 * �����ṩ��delete������
 */
public class DeleteFile {
	String fileName;
	File file;

	/**
	 * �����ļ�ɾ��
	 * @param fileName Ҫɾ�����ļ���
	 */
	public DeleteFile(String fileName) {
		this.fileName = fileName;
		file = new File(fileName);
	}
	
	public DeleteFile(File file) {
		this.fileName = file.getPath();
		this.file = file;
	}

	/**
	 * ɾ���ļ�
	 */
	synchronized public void delete() {
		file.delete();
	}
}