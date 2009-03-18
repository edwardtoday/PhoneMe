package org.kde9.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import org.kde9.model.Icard;
import org.kde9.model.Igroup;
import org.kde9.model.Kernel;

/**
 * 2009.03.16
 * 监听器类，实现对删除按钮的响应
 */
public class ButtonSubListener
implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DefaultTableModel model = 
			(DefaultTableModel)Icontroller.getGroup().getModel();
		int row = Icontroller.getGroup().getTable().getSelectedRow();
		System.out.println(row);
		Igroup g = (Igroup) Icontroller.getGroup().getModel().getValueAt(
				row, 0 );
		
		model.removeRow(row);
		try {
			System.out.println(g.getMyId());
			Kernel.delFromAllGroup(g.getMyId());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("buttonSub!");/////////////////////////////////////////////////
	}

}
