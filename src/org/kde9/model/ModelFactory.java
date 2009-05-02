package org.kde9.model;

import org.kde9.model.allname.AllName;
import org.kde9.model.allname.MyAllName;
import org.kde9.model.card.Card;
import org.kde9.model.card.MyCard;
import org.kde9.model.group.Group;
import org.kde9.model.group.MyGroup;

/**
 * model��Ĺ����࣬��������model��ĸ��ֽӿ�
 * 
 * @author kfirst
 */
public class ModelFactory {
	
	public static Card createCard(int id) {
		return new MyCard(id);
	}
	
	public static Group createGroup(int id) {
		return new MyGroup(id);
	}
	
	public static AllName createAllName() {
		return new MyAllName();
	}
}
