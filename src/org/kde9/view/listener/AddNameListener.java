package org.kde9.view.listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.kde9.view.component.brower.NameComponent;
import org.kde9.view.dialog.AddNameInfoBox;

public class AddNameListener 
implements ActionListener{
	NameComponent component;

	public void actionPerformed(ActionEvent arg0) {
		//component.addNewGroup();
		new AddNameInfoBox(component, new Color(102,255,153),200, 100);
		System.out.println("A new Name added!!");
	}
	
	public void setComponent(NameComponent component) {
		this.component = component;
	}
}

