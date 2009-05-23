package org.kde9.control.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.kde9.control.FileOperation.WriteFile;
import org.kde9.model.allname.AllName;
import org.kde9.model.card.Card;
import org.kde9.model.group.ConstGroup;
import org.kde9.util.Constants;

public class Save 
implements Constants{
	private static Queue<String> pathAndName = new LinkedList<String>();
	private static Queue<String> content = new LinkedList<String>();
	private static Thread thread = new Thread() {
		public void run() {
			try {
				while (true) {
					write();
					synchronized (this) {
						wait();
					}
				}
			} catch (IOException e) {
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	synchronized static private void write()
	throws IOException {
		while(pathAndName.size() != 0 && pathAndName.size() == content.size()) {
			String str1 = pathAndName.poll();
			String str2 = content.poll();
			WriteFile wf = new WriteFile(str1, false);
			wf.write(str2);
			wf.close();
		}
	}
	
	public Save() {
		if(thread.getState() == Thread.State.NEW)
			thread.start();
	}
	
	public void init(Card card) {
		String temp = card.getFirstName();
		temp += NEWLINE;
		temp += card.getLastName();
		temp += NEWLINE;
		for (String key : card.getAllItems().keySet()) {
			temp += key;
			temp += NEWLINE;
			for (String value : card.getItem(key)) {
				temp += value;
				temp += NEWLINE;
				temp += VALUESEPERSTOR;
				temp += NEWLINE;
			}
			temp += ITEMSEPERATOR;
			temp += NEWLINE;
		}
		temp += SEPERATOR;
		temp += NEWLINE;
		for (int relation : card.getAllShowRelationship().keySet()) {
			temp += relation;
			temp += NEWLINE;
			temp += card.getShowRelationship(relation);
			temp += NEWLINE;
		}
		temp += SEPERATOR;
		temp += NEWLINE;
		for (int relation : card.getAllHideRelationship()) {
			temp += relation;
			temp += NEWLINE;
		}
		this.pathAndName.add(CARDPATH + card.getId());
		this.content.add(temp);
	}
	
	public void init(ConstGroup group) {
		String temp = "";
		if(group != null) {
			temp = group.getGroupName() + NEWLINE;
			for(int id : group.getGroupMembers()) {
				temp += id;
				temp += NEWLINE;
			}
		}
		this.pathAndName.add(GROUPPATH + group.getId());
		this.content.add(temp);
	}
	
	public void init(AllName names) {
		String temp = "";
		for (int id : names.getIds()) {
			temp += id;
			temp += NEWLINE;
			temp += names.getFirstName(id);
			temp += NEWLINE;
			temp += names.getLastName(id);
			temp += NEWLINE;
		}
		this.pathAndName.add(CARDPATH + ALLNAMES);
		this.content.add(temp);
	}
	
	public boolean save() {
		synchronized (thread) {
			thread.notify();
		}
		return true;
	}
	
}
