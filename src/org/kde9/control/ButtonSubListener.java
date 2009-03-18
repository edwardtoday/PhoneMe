package org.kde9.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import org.kde9.model.Icard;

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
		model.removeRow(
				Icontroller.getGroup().getTable().getSelectedRow());
		System.out.println("buttonAdd!");/////////////////////////////////////////////////
		Icard card = getInputCard();
	}

	/**
	 * 用于从图形窗口中获得名片
	 */
	Icard getInputCard() {
		//TODO
		return new Icard();
	}
}
