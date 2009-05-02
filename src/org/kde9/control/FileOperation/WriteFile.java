package org.kde9.control.FileOperation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ���ݲ����ײ�
 * <p>
 * ��װ���ļ�д�Ļ���������
 * �����ṩ��writeLine��write������
 * <br><strong>
 * ʹ��ʱע�⣬�ļ�д����������Ҫ����close�����ر�����
 * </strong></br>
 */
public class WriteFile {
	/**
	 * Ҫ�������ļ���
	 */
	String fileName;
	String contant;
	FileWriter fw;
	BufferedWriter bw;

	/**
	 * ͨ���ļ����ͱ�ʶ�������ļ�write��
	 * @param fileName �ļ���
	 * @param flag Ϊtrueʱ��ʾ׷�ӣ�Ϊfalseʱ��ʾ���ǡ�
	 * @throws IOException
	 */
	public WriteFile(String fileName, boolean flag)
	throws IOException {
		this.fileName = fileName;
		fw = new FileWriter(fileName, flag);
		bw = new BufferedWriter(fw);
	}

	/**
	 * ͨ���ļ����ͱ�ʶ�������ļ�write��
	 * @param fileName �ļ���
	 * @param flag Ϊtrueʱ��ʾ׷�ӣ�Ϊfalseʱ��ʾ���ǡ�
	 * @throws IOException
	 */
	public WriteFile(File file, boolean flag)
	throws IOException {
		this.fileName = file.getPath();
		fw = new FileWriter(file, flag);
		bw = new BufferedWriter(fw);
	}
	
	/**
	 * ���ļ���д������
	 * <br><strong>
	 * ʹ��ʱע�⣬�ļ�д����������Ҫ����close�����ر�����
	 * </strong></br>
	 * @param str Ҫд�������
	 * @return 
	 * @throws IOException
	 */
	synchronized public void write(String str) 
	throws IOException {
		bw.write(str);
	}

	/**
	 * ���ļ���д�����ݣ�������
	 * <br><strong>
	 * ʹ��ʱע�⣬�ļ�д����������Ҫ����close�����ر�����
	 * </strong></br>
	 * @param str Ҫд�������
	 * @throws IOException
	 */
	synchronized public void writeLine(String str) 
	throws IOException {
		bw.write(
				str + 
				System.getProperty("line.separator") );
	}
	
	/**
	 * �ر���
	 * @throws IOException 
	 * @throws IOException
	 */
	synchronized public void close() 
	throws IOException {
		bw.close();
		fw.close();
	}
}
