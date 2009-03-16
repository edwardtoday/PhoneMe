package org.kde9.main;

import java.awt.GridLayout;

import javax.swing.JFrame;

import org.kde9.control.Icontroller;
import org.kde9.model.Ifile;
import org.kde9.view.Factory;
import org.kde9.view.Igroup;
import org.kde9.view.Imenubar;
import org.kde9.view.Iname;
import org.kde9.view.Iviewer;

public class main 
implements Constants{
	public static void main(String args[]) {
		Icontroller controller = new Icontroller(
				(Imenubar)Factory.createMenuBar(),
				(Iviewer)Factory.createViewer(),
				(Igroup)Factory.createGroup(),
				(Iname)Factory.creatName(),
				new Ifile() );
		JFrame frame = new JFrame("what's this?");
		frame.setJMenuBar(controller.getMenubar());
		frame.setLayout(new GridLayout(1, 0, 10, 10));
		frame.add(controller.getGroup());
		frame.add(controller.getName());
		frame.add(controller.getViewer());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
        frame.setVisible(true);
	}
}
