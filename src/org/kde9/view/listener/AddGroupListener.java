﻿package org.kde9.view.listener;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.kde9.view.ComponentPool;
import org.kde9.view.component.brower.GroupComponent;
import org.kde9.view.dialog.AddGroupInfoBox;

public class AddGroupListener 
implements ActionListener {
	GroupComponent groupComponent;
//	Container container;

	public void actionPerformed(ActionEvent e) {
		//System.err.println(ComponentPool.getNameComponent().getMembers());
		//component.addNewGroup();
		//container.setEnabled(false);
		ComponentPool.getComponent().setEnabled(false);
		new AddGroupInfoBox(groupComponent, new Color(102,255,153),200, 100);	
		System.out.println("A new group added!!");
	}
	
	public void setComponent(GroupComponent component) {
		this.groupComponent = component;
	}
	
//	public void setMainComponent(Container component) {
//		this.container = component;
//	}
}
