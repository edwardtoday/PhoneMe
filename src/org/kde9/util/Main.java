package org.kde9.util;

import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

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
		File file = new File(DATA_PATH);
		if(!file.exists())
			file.mkdir();
		file = new File(GROUP_PATH);
		if(!file.exists())
			file.mkdir();
		file = new File(GROUP_PATH + ALLGROUPS);
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		Icontroller.init(
				(MenubarComponent)Factory.createMenuBar(),
				(ViewerComponent)Factory.createViewer(),
				(GroupComponent)Factory.createGroup(),
				(NameComponent)Factory.creatName() );
		
		Icontroller.getGroup().addListener(
				Icontroller.getBal(),
				Icontroller.getBsl(),
				Icontroller.getLs() );
		
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
