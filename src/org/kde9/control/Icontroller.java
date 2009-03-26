package org.kde9.control;

import java.io.IOException;

import org.kde9.model.Ikernel;
import org.kde9.view.Icomponent;


public class Icontroller {
	public static Ikernel kernel;
	public static Icomponent component;
	
	private GroupAddListener gal;
	private GroupSubListener gsl;
	private GroupTableListener gtl;
	
	public Icontroller() {
		// TODO Auto-generated constructor stub
		gal = new GroupAddListener();
		gsl = new GroupSubListener();
		gtl = new GroupTableListener();
		
		try {
			kernel = new Ikernel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		component = new Icomponent();
		component.init(
				gal,
				gsl,
				gtl );
	}
}
