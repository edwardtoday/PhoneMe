package org.kde9.view;

import java.awt.GridLayout;

import javax.swing.JFrame;

import org.kde9.util.Constants;

public class Icomponent
implements Constants {
	GroupComponent groupComponent;
	MenubarComponent menubarComponent;
	NameComponent nameComponent;
	ViewerComponent viewerComponent;
	
	public Icomponent() {
		groupComponent = Factory.createGroup();
		menubarComponent = Factory.createMenuBar();
		nameComponent = Factory.creatName();
		viewerComponent = Factory.createViewer();
		
		JFrame frame = new JFrame("what's this?");
		frame.setJMenuBar(menubarComponent);
		frame.setLayout(new GridLayout(1, 0, 10, 10));
		frame.add(groupComponent);
		frame.add(nameComponent);
		frame.add(viewerComponent);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
        frame.setVisible(true);
	}
}
