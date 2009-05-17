package org.kde9.control.FileOperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 数据操作底层
 * <p>
 * 封装了文件读的基本操作，
 * 向上提供了readLine方法。
 * <br><strong>
 * 使用时注意，文件读操作结束后要调用close方法关闭流。
 * </strong></br>
 */
public class ReadFile {
	/**
	 * 要操作的文件名
	 */
	String fileName;
	FileReader fr;
	BufferedReader br;

	/**
	 * 通过文件名构造一个文件read流
	 * @param fileName 文件名
	 * @throws FileNotFoundException 要打开的文件不存在
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
	 * 从文件中读取一行
	 * <br><strong>
	 * 使用时注意，文件读操作结束后要调用close方法关闭流。
	 * </strong></br>
	 * @return 包含要读取行内容的String
	 * @throws IOException
	 */
	synchronized public String readLine() 
	throws IOException {
		return br.readLine();
	}

	/**
	 * 关闭流
	 * @throws IOException
	 */
	synchronized public void close()
	throws IOException {
		br.close();
		fr.close();
	}
}
