package org.kde9.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import org.kde9.model.Icard;
import org.kde9.model.Igroup;

/**
 * 2009.03.16
 * 监听器类，实现对添加按钮的响应
 */
public class ButtonAddListener
implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DefaultTableModel model = 
			(DefaultTableModel)Icontroller.getGroup().getModel();
		Igroup tempGroup = new Igroup("请输入组名");
		try {
			tempGroup.save();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		model.addRow(new Object[] {tempGroup});
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
