package org.kde9.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import org.kde9.model.Icard;
import org.kde9.model.Mgroup;

/**
 * 2009.03.16
 * �������࣬ʵ�ֶ���Ӱ�ť����Ӧ
 */
public class ButtonAddListener
implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DefaultTableModel model = 
			(DefaultTableModel)Icontroller.getGroup().getModel();
		Mgroup tempGroup = new Mgroup("����������");
		try {
			tempGroup.save();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		model.addRow(new String[] {"����������"});
		System.out.println("buttonAdd!");/////////////////////////////////////////////////
		Icard card = getInputCard();
	}

	/**
	 * ���ڴ�ͼ�δ����л����Ƭ
	 */
	Icard getInputCard() {
		//TODO
		return new Icard();
	}
}
