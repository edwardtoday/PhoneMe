package org.kde9.view.listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.kde9.view.component.brower.GroupComponent;
import org.kde9.view.dialog.AddGroupInfoBox;

public class AddGroupListener 
implements ActionListener{
	GroupComponent component;

	public void actionPerformed(ActionEvent arg0) {
		//component.addNewGroup();
		new AddGroupInfoBox(component, new Color(102,255,153),200, 100);
		System.out.println("A new group added!!");
	}
	
	public void setComponent(GroupComponent component) {
		this.component = component;
	}
}
