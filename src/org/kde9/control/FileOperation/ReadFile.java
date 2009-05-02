package org.kde9.control.FileOperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * ���ݲ����ײ�
 * <p>
 * ��װ���ļ����Ļ���������
 * �����ṩ��readLine������
 * <br><strong>
 * ʹ��ʱע�⣬�ļ�������������Ҫ����close�����ر�����
 * </strong></br>
 */
public class ReadFile {
	/**
	 * Ҫ�������ļ���
	 */
	String fileName;
	FileReader fr;
	BufferedReader br;

	/**
	 * ͨ���ļ�������һ���ļ�read��
	 * @param fileName �ļ���
	 * @throws FileNotFoundException Ҫ�򿪵��ļ�������
	 */
	public ReadFile(String fileName) 
	throws FileNotFoundException {
		this.fileName = fileName;
		fr = new FileReader(fileName);
		br = new BufferedReader(fr);
	}

	public ReadFile(File file) 
	throws FileNotFoundException {
		this.fileName = file.getPath();
		fr = new FileReader(file);
		br = new BufferedReader(fr);
	}
	
	/**
	 * ���ļ��ж�ȡһ��
	 * <br><strong>
	 * ʹ��ʱע�⣬�ļ�������������Ҫ����close�����ر�����
	 * </strong></br>
	 * @return ����Ҫ��ȡ�����ݵ�String
	 * @throws IOException
	 */
	synchronized public String readLine() 
	throws IOException {
		return br.readLine();
	}

	/**
	 * �ر���
	 * @throws IOException
	 */
	synchronized public void close()
	throws IOException {
		br.close();
		fr.close();
	}
}
