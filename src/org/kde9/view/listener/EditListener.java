package org.kde9.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.kde9.view.component.brower.ViewerComponent;

public class EditListener
implements ActionListener {
	ViewerComponent component;
	
	public void actionPerformed(ActionEvent arg0) {
		component.startEditModel();
	}
	
	public void setComponent(ViewerComponent component) {
		this.component = component;
	}
}
