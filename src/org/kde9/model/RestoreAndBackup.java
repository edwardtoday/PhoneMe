package org.kde9.model;

import java.io.File;
import java.io.IOException;

import org.kde9.util.Constants;

class RestoreAndBackup 
implements Constants, InterfaceRestoreAndBackup {
	
	private RestoreAndBackup() {}
	
	/**
	 * 判断一个字符串是否为数字
	 * <p>
	 * 字符串为空串或非数字串将返回false。
	 * 
	 * @param str
	 * 		要判断的字符串
	 */
	private boolean isInt(String str) {
		// 为空串返回false
		if (str == "")
			return false;
		for (char c : str.toCharArray())
			// 非数字返回false
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
					if(!item.isDirectory() && isInt(item.getName())) {
						wf.writeLine(item.getName());
						if(readContent) {
							ReadFile rf = new ReadFile(item);
							String temp = rf.readLine();
							if(temp != null)
								wf.writeLine(temp);
							else
								wf.writeLine(NULLCARDNAME);
							rf.close();
						}
					}
				}
			wf.close();
		}
	}
	
	public static RestoreAndBackup createIrestoreandbackup() {
		return new RestoreAndBackup();
	}
	
	/* (non-Javadoc)
	 * @see org.kde9.model.RestoreAndBackup#checkout()
	 */
	public void checkout() 
	throws IOException {
		checkoutDir(DATA_PATH);
		checkoutDir(GROUP_PATH);
		checkoutDir(CARD_PATH);
		checkoutIndex(GROUP_PATH, ALLGROUPS, false);
		checkoutIndex(CARD_PATH, ALLNAMES, true);
	}
	
	/* (non-Javadoc)
	 * @see org.kde9.model.RestoreAndBackup#restore()
	 */
	public synchronized void restore() {
		
	}
	
	/* (non-Javadoc)
	 * @see org.kde9.model.RestoreAndBackup#backup()
	 */
	public synchronized void backup() {

	}
	
	/*
	 * test!
	 */
	public static void main(String args[]) {
		InterfaceRestoreAndBackup rab = new RestoreAndBackup();
		try {
			rab.checkout();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
