package org.kde9.control.FileOperation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 数据操作底层
 * <p>
 * 封装了文件写的基本操作，
 * 向上提供了writeLine和write方法。
 * <br><strong>
 * 使用时注意，文件写操作结束后要调用close方法关闭流。
 * </strong></br>
 */
public class WriteFile {
	/**
	 * 要操作的文件名
	 */
	String fileName;
	String contant;
	FileOutputStream fos;
	OutputStreamWriter osw;
	BufferedWriter bw;

	/**
	 * 通过文件名和标识符创建文件write流
	 * @param fileName 文件名
	 * @param flag 为true时表示追加，为false时表示覆盖。
	 * @throws IOException
	 */
	public WriteFile(String fileName, boolean flag)
	throws IOException {
		this.fileName = fileName;
		fos = new FileOutputStream(fileName);
		osw = new OutputStreamWriter(fos, "UTF-8");
		bw = new BufferedWriter(osw);
	}

	/**
	 * 通过文件名和标识符创建文件write流
	 * @param fileName 文件名
	 * @param flag 为true时表示追加，为false时表示覆盖。
	 * @throws IOException
	 */
	public WriteFile(File file, boolean flag)
	throws IOException {
		this.fileName = file.getPath();
		fos = new FileOutputStream(file);
		osw = new OutputStreamWriter(fos, "UTF-8");
		bw = new BufferedWriter(osw);
	}
	
	/**
	 * 向文件中写入内容
	 * <br><strong>
	 * 使用时注意，文件写操作结束后要调用close方法关闭流。
	 * </strong></br>
	 * @param str 要写入的内容
	 * @return 
	 * @throws IOException
	 */
	synchronized public void write(String str) 
	throws IOException {
		bw.write(str);
	}

	/**
	 * 向文件中写入内容，并换行
	 * <br><strong>
	 * 使用时注意，文件写操作结束后要调用close方法关闭流。
	 * </strong></br>
	 * @param str 要写入的内容
	 * @throws IOException
	 */
	synchronized public void writeLine(String str) 
	throws IOException {
		bw.write(
				str + 
				System.getProperty("line.separator") );
	}
	
	/**
	 * 关闭流
	 * @throws IOException 
	 * @throws IOException
	 */
	synchronized public void close() 
	throws IOException {
		bw.close();
		osw.close();
		fos.close();
	}
}
