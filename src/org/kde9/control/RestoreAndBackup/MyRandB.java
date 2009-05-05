package org.kde9.control.RestoreAndBackup;

import java.io.File;
import java.io.IOException;

import org.kde9.control.FileOperation.ReadFile;
import org.kde9.control.FileOperation.WriteFile;
import org.kde9.util.Constants;

public class MyRandB 
implements RestoreAndBackup, Constants {
	
	/**
	 * �ж�һ���ַ����Ƿ�Ϊ����
	 * <p>
	 * �ַ���Ϊ�մ�������ִ�������false��
	 * 
	 * @param str
	 * 		Ҫ�жϵ��ַ���
	 */
	private boolean isInt(String str) {
		// Ϊ�մ�����false
		if (str == "")
			return false;
		for (char c : str.toCharArray())
			// �����ַ���false
			if (!Character.isDigit(c))
				return false;
		return true;
	}
	
	private void checkoutDir(String path) {
		File dir = new File(path);
		if(!dir.exists())
			dir.mkdir();
		else if(!dir.isDirectory()) {
			dir.delete();
			dir.mkdir();
		}
	}
	
	private void checkoutIndex(String path, String fileName, boolean readContent) 
	throws IOException {
		File dir = new File(path);
		File file = new File(path + fileName);
		if(!file.exists()) {
			WriteFile wf = new WriteFile(file, true);
			File[] items = dir.listFiles();
			if(items.length != 0)
				for(File item : items) {
					if(item.isFile() && isInt(item.getName())) {
						wf.writeLine(item.getName());
						if(readContent) {
							ReadFile rf = new ReadFile(item);
							String tempFirst = rf.readLine();
							String tempLast = rf.readLine();
							if(tempFirst != null)
								wf.writeLine(tempFirst);
							else
								wf.writeLine(NULLCARDNAME);
							if(tempLast != null)
								wf.writeLine(tempLast);
							else
								wf.writeLine(NULLCARDNAME);
							rf.close();
						}
					}
				}
			wf.close();
		}
	}
	
	public void checkout() 
	throws IOException {
		checkoutDir(DATAPATH);
		checkoutDir(GROUPPATH);
		checkoutDir(CARDPATH);
		checkoutIndex(CARDPATH, ALLNAMES, true);
	}
	
	public synchronized void restore() {
		
	}
	
	public synchronized void backup() {

	}
	
	/*
	 * test!
	 */
	public static void main(String args[]) {
		RestoreAndBackup rab = new MyRandB();
		try {
			rab.checkout();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
