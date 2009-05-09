package org.kde9.control.controller;

import java.io.IOException;

import org.kde9.control.FileOperation.WriteFile;
import org.kde9.util.Constants;

public class Save 
implements Constants{
	private String pathAndName;
	private String content;
	private Thread thread;
	
	public Save() {
		thread = new Thread() {
			public void run() {
				try {
					WriteFile wf = new WriteFile(pathAndName, false);
					wf.write(content);
					wf.close();
				} catch (IOException e) {}
			}
		};
	}
	
	public void init(String pathAndName, String content) {
		this.pathAndName = pathAndName;
		this.content = content;
	}
	
	public boolean save() {
		thread.start();
		return true;
	}
	
	public static void main(String args[]) {
		Save s = new Save();
		s.init(DATAPATH + "test.txt", "just for test!\r\nhehe!");
		s.save();
	}
}
