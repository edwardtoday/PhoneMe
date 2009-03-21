package org.kde9.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ���ݲ����ײ㣬�����������ʹ�á�
 * <p>
 * ��װ���ļ����Ļ���������
 * �����ṩ��readLine������
 * <br><strong>
 * ʹ��ʱע�⣬�ļ�������������Ҫ����close�����ر�����
 * </strong></br>
 */
class ReadFile {
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
	ReadFile(String fileName) 
	throws FileNotFoundException {
		this.fileName = fileName;
		fr = new FileReader(fileName);
		br = new BufferedReader(fr);
	}

	/**
	 * ���ļ��ж�ȡһ��
	 * @return ����Ҫ��ȡ�����ݵ�String
	 * @throws IOException
	 */
	String readLine() 
	throws IOException {
		return br.readLine();
	}

	/**
	 * �ر���
	 * @throws IOException
	 */
	void close() throws IOException {
		br.close();
		fr.close();
	}
}


/**
 * ���ݲ����ײ㣬�����������ʹ�á�
 * <p>
 * ��װ���ļ�д�Ļ���������
 * �����ṩ��writeLine��write������
 * <br><strong>
 * ʹ��ʱע�⣬�ļ�д����������Ҫ����close�����ر�����
 * </strong></br>
 */
class WriteFile {
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
	WriteFile(String fileName, boolean flag)
	throws IOException {
		this.fileName = fileName;
		fw = new FileWriter(fileName, flag);
		bw = new BufferedWriter(fw);
	}

	/**
	 * ���ļ���д������
	 * @param str Ҫд�������
	 * @throws IOException
	 */
	void write(String str) 
	throws IOException {
		bw.write(str);
	}

	/**
	 * ���ļ���д�����ݣ�������
	 * @param str Ҫд�������
	 * @throws IOException
	 */
	void writeLine(String str) 
	throws IOException {
		bw.write(
				str + 
				System.getProperty("line.separator") );
	}
	
	/**
	 * �ر���
	 * @throws IOException
	 */
	void close() 
	throws IOException {
		bw.close();
		fw.close();
	}
}


/**
 * ���ݲ����ײ㣬�����������ʹ�á�
 * <p>
 * ��װ���ļ�ɾ���Ļ���������
 * �����ṩ��delete������
 */
class DeleteFile {
	String fileName;
	File file;

	/**
	 * �����ļ�ɾ��
	 * @param fileName Ҫɾ�����ļ���
	 */
	DeleteFile(String fileName) {
		this.fileName = fileName;
		file = new File(fileName);
	}

	/**
	 * ɾ���ļ�
	 */
	void delete() {
		file.delete();
	}
}