package org.kde9.view;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.UIManager;

import org.kde9.control.GroupAddListener;
import org.kde9.control.GroupSubListener;
import org.kde9.control.GroupTableListener;
import org.kde9.util.I_Constants;

public class Component implements I_Constants {
	public Component() {

		// JFrame frame = new JFrame("what's this?");
		JFrame frame = new FrameComponent();
		frame.setVisible(true);
	}

	public void init(GroupAddListener gal, GroupSubListener gsl,
			GroupTableListener gtl) {
		// groupComponent.buttonAddListener(gal);
		// groupComponent.buttonSubListener(gsl);
		// groupComponent.tableListener(gtl, gtl);
	}

	public void addGroup() {
		// 添加一个group，并选中它，将name栏清空
	}
}
