package org.kde9.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.kde9.model.Igroup;
import org.kde9.model.Iperson;

public class TableSelectionListener 
implements ListSelectionListener, KeyListener {
	private static Igroup g;

	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getValueIsAdjusting());/////////////////////////////////////
		if(!e.getValueIsAdjusting()) {
			while(Icontroller.getName().getModel().getRowCount() != 0)
				Icontroller.getName().getModel().removeRow(0);
			if(Icontroller.getGroup().getTable().getSelectedRow() != -1)
				g = (Igroup) Icontroller.getGroup().getModel().getValueAt(
						Icontroller.getGroup().getTable().getSelectedRow(), 0 );
			System.out.println(g);///////////////////////////////////////////////////////////
			if(g.getPersons() != null) {
				for(Iperson p : g.getPersons())
					Icontroller.getName().getModel().addRow(new Object[]{p});
			}
		}
	}
	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("keyReleased!");
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			String name = (String) Icontroller.getGroup().getModel().getValueAt(
					Icontroller.getGroup().getTable().getSelectedRow(), 0 );
			try {
				g.rename(name);
				g.save();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Icontroller.getGroup().getModel().setValueAt(
					g, Icontroller.getGroup().getTable().getSelectedRow(), 0 );
		}
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
