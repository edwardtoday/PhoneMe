package org.kde9.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.kde9.model.Icard;

/**
 * 2009.03.16
 * �������࣬ʵ�ֶ���Ӱ�ť����Ӧ
 */
public class ButtonAddListener
implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("buttonAdd!");/////////////////////////////////////////////////
		Icard card = getInputCard();
		if(!Icontroller.getFile().save(card))
			System.out.println("saveMethod failed!");
	}

	/**
	 * ���ڴ�ͼ�δ����л����Ƭ
	 */
	Icard getInputCard() {
		//TODO
		return new Icard();
	}
}
