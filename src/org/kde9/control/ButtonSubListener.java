package org.kde9.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import org.kde9.model.Icard;

/**
 * 2009.03.16
 * �������࣬ʵ�ֶ�ɾ����ť����Ӧ
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
	 * ���ڴ�ͼ�δ����л����Ƭ
	 */
	Icard getInputCard() {
		//TODO
		return new Icard();
	}
}
