package org.kde9.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;

import org.kde9.view.ComponentPool;
import org.kde9.view.component.brower.ViewerComponent;

public class EditListener
implements ActionListener {
	ViewerComponent component;
	int i, j;
	
	public void actionPerformed(ActionEvent e) {
		if(((JToggleButton)e.getSource()).isSelected()) {
			component.startEditModel();
			//ComponentPool.getViewerComponent().setEnabled(true);
			ComponentPool.getBrowerComponent().setEnabled(false);
			i = ComponentPool.getGroupComponent().getSelected();
			j = ComponentPool.getNameComponent().getSelected();
			ComponentPool.getGroupComponent().getTable().setCellSelectionEnabled(false);
			ComponentPool.getNameComponent().getTable().setCellSelectionEnabled(false);
			ComponentPool.getGroupComponent().getTable().setFocusable(false);
			ComponentPool.getNameComponent().getTable().setFocusable(false);
		}
		else {
			component.stopEditModel();
			ComponentPool.getGroupComponent().getTable().setRowSelectionAllowed(true);
			ComponentPool.getNameComponent().getTable().setCellSelectionEnabled(true);
			ComponentPool.getGroupComponent().getTable().setFocusable(true);
			ComponentPool.getNameComponent().getTable().setFocusable(true);
			ComponentPool.getGroupComponent().setSelected(i, i);
			ComponentPool.getNameComponent().setSelected(j, j);
			ComponentPool.getBrowerComponent().setEnabled(true);
			System.out.println("Saving!!!!!!!!!!!!!!!!!!!!");
		}
	}
	
	public void setComponent(ViewerComponent component) {
		this.component = component;
	}
}
