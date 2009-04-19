package org.kde9.control;

import java.io.IOException;

import org.kde9.model.Kernel;
import org.kde9.view.Component;

public class Controller {
	public static Kernel kernel;
	public static Component component;

	private GroupAddListener gal;
	private GroupSubListener gsl;
	private GroupTableListener gtl;

	public Controller() {
		// TODO Auto-generated constructor stub
		gal = new GroupAddListener();
		gsl = new GroupSubListener();
		gtl = new GroupTableListener();

		try {
			kernel = new Kernel();
			kernel.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		component = new Component();
		component.init(gal, gsl, gtl);
	}
}
