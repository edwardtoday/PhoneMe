package org.kde9.view;

import javax.swing.JFrame;

import org.kde9.control.Kernel;
import org.kde9.util.Constants;
import org.kde9.view.component.frame.FrameComponent;

public class Component implements Constants {
	public Component(Kernel model) {

		// JFrame frame = new JFrame("what's this?");
		JFrame frame = new FrameComponent(model);
		frame.setVisible(true);
	}

	public void addGroup() {
		// 添加一个group，并选中它，将name栏清空
	}
}
