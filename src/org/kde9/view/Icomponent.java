package org.kde9.view;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.UIManager;

import org.kde9.control.GroupAddListener;
import org.kde9.control.GroupSubListener;
import org.kde9.control.GroupTableListener;
import org.kde9.util.Constants;

public class Icomponent
implements Constants {
	public Icomponent() {

		//JFrame frame = new JFrame("what's this?");
		JFrame frame = new IFrameComponent();		
        frame.setVisible(true);
	}
	
	public void init(
			GroupAddListener gal,
			GroupSubListener gsl,
			GroupTableListener gtl) {
		//groupComponent.buttonAddListener(gal);
		//groupComponent.buttonSubListener(gsl);
		//groupComponent.tableListener(gtl, gtl);
	}
	
	public void addGroup() {
		//添加一个group，并选中它，将name栏清空
	}
}
