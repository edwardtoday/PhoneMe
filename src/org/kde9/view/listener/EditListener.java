package org.kde9.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

import org.kde9.control.Kernel;
import org.kde9.util.ConfigFactory;
import org.kde9.util.Configuration;
import org.kde9.util.Constants;
import org.kde9.view.ComponentPool;
import org.kde9.view.component.brower.NameComponent;
import org.kde9.view.component.brower.ViewerComponent;

public class EditListener
implements ActionListener, Constants {
	ViewerComponent component;
	Kernel kernel = ComponentPool.getComponent().getKernel();
	Configuration config = ConfigFactory.creatConfig();
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
			ViewerComponent viewer = ComponentPool.getViewerComponent();
			ComponentPool.getGroupComponent().getTable().setRowSelectionAllowed(true);
			ComponentPool.getNameComponent().getTable().setCellSelectionEnabled(true);
			ComponentPool.getGroupComponent().getTable().setFocusable(true);
			ComponentPool.getNameComponent().getTable().setFocusable(true);
			ComponentPool.getGroupComponent().setSelected(i, i);
			ComponentPool.getNameComponent().setSelected(j, j);
			ComponentPool.getBrowerComponent().setEnabled(true);
			if((Integer)config.getConfig(NAME_FOMAT, CONFIGINT) == 0) {
				kernel.updateCard(viewer.getCardId(), 
						viewer.getNewNameOne(), 
						viewer.getNewNameTwo(), 
						viewer.getNewItems(),
						viewer.getNewRelation());
				ComponentPool.getNameComponent().getModel().setValueAt(
						viewer.getNewNameOne() + " " + viewer.getNewNameTwo(),
						ComponentPool.getGroupComponent().getSelected(), 0);
			}
			else {
				kernel.updateCard(viewer.getCardId(),
						viewer.getNewNameTwo(), 
						viewer.getNewNameOne(), 		 
						viewer.getNewItems(),
						viewer.getNewRelation());
				ComponentPool.getNameComponent().getModel().setValueAt(
						viewer.getNewNameTwo() + " " + viewer.getNewNameOne(),
						ComponentPool.getGroupComponent().getSelected(), 0);
			}
			System.out.println("Saving!!!!!!!!!!!!!!!!!!!!");
		}
	}
	
	public void setComponent(ViewerComponent component) {
		this.component = component;
	}
}
