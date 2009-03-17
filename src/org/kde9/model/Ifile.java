package org.kde9.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 2009.03.16
 * 与文件系统相关的类，负责文件的存取和查找等。
 */
class ReadFile {
	String fileName;
	File file;
	FileReader fr;
	BufferedReader br;
	
	public ReadFile(String fileName) throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		this.fileName = fileName;
		file = new File(fileName);
		fr = new FileReader(fileName);
		br = new BufferedReader(fr);
	}
	
	String readLine() throws IOException {
		return br.readLine();
	}
	
	void close() throws IOException {
		br.close();
		fr.close();
	}
}

class WriteFile {
	String fileName;
	String contant;
	File file;
	FileWriter fw;
	BufferedWriter bw;
	
	public WriteFile(String fileName, boolean flag) throws IOException {
		// TODO Auto-generated constructor stub
		this.fileName = fileName;
		file = new File(fileName);
		fw = new FileWriter(fileName, flag);
		bw = new BufferedWriter(fw);
	}
	
	void write(String str) throws IOException {
		bw.write(str);
	}
	
	void close() throws IOException {
		bw.close();
		fw.close();
	}
}
