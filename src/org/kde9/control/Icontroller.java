package org.kde9.control;

import java.io.IOException;

import org.kde9.model.Ikernel;
import org.kde9.view.Icomponent;


public class Icontroller {
	private Ikernel kernel;
	private Icomponent component;
	
	public Icontroller() {
		// TODO Auto-generated constructor stub
		try {
			kernel = new Ikernel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		component = new Icomponent();
	}
}
