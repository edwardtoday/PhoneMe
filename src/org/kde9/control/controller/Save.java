package org.kde9.control.controller;

import java.io.IOException;

import org.kde9.control.FileOperation.WriteFile;
import org.kde9.model.allname.AllName;
import org.kde9.model.card.Card;
import org.kde9.model.group.ConstGroup;
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
		this.pathAndName = CARDPATH + card.getId();
		this.content = temp;
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
		this.pathAndName = GROUPPATH + group.getId();
		this.content = temp;
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
		this.pathAndName = CARDPATH + ALLNAMES;
		this.content = temp;
	}
	
	public boolean save() {
		thread.start();
		return true;
	}
	
}
