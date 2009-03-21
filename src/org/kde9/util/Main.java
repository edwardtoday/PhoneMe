package org.kde9.util;

import java.awt.GridLayout;

import javax.swing.JFrame;

import org.kde9.control.Icontroller;
import org.kde9.view.Factory;
import org.kde9.view.GroupComponent;
import org.kde9.view.MenubarComponent;
import org.kde9.view.NameComponent;
import org.kde9.view.ViewerComponent;

public class Main
implements Constants{
	public static void main(String args[]) {
		Icontroller.init(
				(MenubarComponent)Factory.createMenuBar(),
				(ViewerComponent)Factory.createViewer(),
				(GroupComponent)Factory.createGroup(),
				(NameComponent)Factory.creatName() );
		
		JFrame frame = new JFrame("what's this?");
		frame.setJMenuBar(Icontroller.getMenubar());
		frame.setLayout(new GridLayout(1, 0, 10, 10));
		frame.add(Icontroller.getGroup());
		frame.add(Icontroller.getName());
		frame.add(Icontroller.getViewer());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
        frame.setVisible(true);
	}
}
