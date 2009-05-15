package org.kde9.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

import org.kde9.view.ComponentPool;
import org.kde9.view.component.brower.ViewerComponent;

public class EditListener
implements ActionListener {
	ViewerComponent component;
	
	public void actionPerformed(ActionEvent e) {
		if(((JToggleButton)e.getSource()).isSelected()) {
			component.startEditModel();
			ComponentPool.getNameComponent().setEnabled(false);
		}
		else {
			component.stopEditModel();
		}
	}
	
	public void setComponent(ViewerComponent component) {
		this.component = component;
	}
}
