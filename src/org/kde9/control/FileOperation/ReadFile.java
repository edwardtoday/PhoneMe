package org.kde9.control.FileOperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

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
	FileInputStream fis;
	InputStreamReader isr;
	BufferedReader br;

	/**
	 * 通过文件名构造一个文件read流
	 * @param fileName 文件名
	 * @throws FileNotFoundException 要打开的文件不存在
	 */
	public ReadFile(String fileName) 
	throws FileNotFoundException {
		this.fileName = fileName;
		fis = new FileInputStream(fileName);
		try {
			isr = new InputStreamReader(fis, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		br = new BufferedReader(isr);
	}
	
	public ReadFile(String fileName, String charset) 
	throws FileNotFoundException {
		this.fileName = fileName;
		fis = new FileInputStream(fileName);
		try {
			isr = new InputStreamReader(fis, charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		br = new BufferedReader(isr);
	}

	public ReadFile(File file) 
	throws FileNotFoundException {
		this.fileName = file.getPath();
		fis = new FileInputStream(file);
		try {
			isr = new InputStreamReader(fis, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		br = new BufferedReader(isr);
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
		isr.close();
		fis.close();
	}
}
