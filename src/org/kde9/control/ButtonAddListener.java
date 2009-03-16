package org.kde9.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.kde9.model.Icard;

/**
 * 2009.03.16
 * 监听器类，实现对添加按钮的响应
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
	 * 用于从图形窗口中获得名片
	 */
	Icard getInputCard() {
		//TODO
		return new Icard();
	}
}
