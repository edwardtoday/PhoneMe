package org.kde9.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 数据操作底层，此类仅供包内使用。
 * <p>
 * 封装了文件读的基本操作，
 * 向上提供了readLine方法。
 * <br><strong>
 * 使用时注意，文件读操作结束后要调用close方法关闭流。
 * </strong></br>
 */
class ReadFile {
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
	ReadFile(String fileName) 
	throws FileNotFoundException {
		this.fileName = fileName;
		fr = new FileReader(fileName);
		br = new BufferedReader(fr);
	}

	/**
	 * 从文件中读取一行
	 * @return 包含要读取行内容的String
	 * @throws IOException
	 */
	String readLine() 
	throws IOException {
		return br.readLine();
	}

	/**
	 * 关闭流
	 * @throws IOException
	 */
	void close() throws IOException {
		br.close();
		fr.close();
	}
}


/**
 * 数据操作底层，此类仅供包内使用。
 * <p>
 * 封装了文件写的基本操作，
 * 向上提供了writeLine和write方法。
 * <br><strong>
 * 使用时注意，文件写操作结束后要调用close方法关闭流。
 * </strong></br>
 */
class WriteFile {
	/**
	 * 要操作的文件名
	 */
	String fileName;
	String contant;
	FileWriter fw;
	BufferedWriter bw;

	/**
	 * 通过文件名和标识符创建文件write流
	 * @param fileName 文件名
	 * @param flag 为true时表示追加，为false时表示覆盖。
	 * @throws IOException
	 */
	WriteFile(String fileName, boolean flag)
	throws IOException {
		this.fileName = fileName;
		fw = new FileWriter(fileName, flag);
		bw = new BufferedWriter(fw);
	}

	/**
	 * 向文件中写入内容
	 * @param str 要写入的内容
	 * @throws IOException
	 */
	void write(String str) 
	throws IOException {
		bw.write(str);
	}

	/**
	 * 向文件中写入内容，并换行
	 * @param str 要写入的内容
	 * @throws IOException
	 */
	void writeLine(String str) 
	throws IOException {
		bw.write(
				str + 
				System.getProperty("line.separator") );
	}
	
	/**
	 * 关闭流
	 * @throws IOException
	 */
	void close() 
	throws IOException {
		bw.close();
		fw.close();
	}
}


/**
 * 数据操作底层，此类仅供包内使用。
 * <p>
 * 封装了文件删除的基本操作，
 * 向上提供了delete方法。
 */
class DeleteFile {
	String fileName;
	File file;

	/**
	 * 创建文件删除
	 * @param fileName 要删除的文件名
	 */
	DeleteFile(String fileName) {
		this.fileName = fileName;
		file = new File(fileName);
	}

	/**
	 * 删除文件
	 */
	void delete() {
		file.delete();
	}
}