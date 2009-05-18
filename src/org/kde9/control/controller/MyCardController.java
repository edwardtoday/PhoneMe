package org.kde9.control.controller;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.kde9.control.FileOperation.DeleteFile;
import org.kde9.control.FileOperation.MoveFile;
import org.kde9.control.FileOperation.ReadFile;
import org.kde9.model.ModelFactory;
import org.kde9.model.card.Card;
import org.kde9.model.card.ConstCard;
import org.kde9.util.Constants;

public class MyCardController 
implements CardController, Constants {
	private int staticId = 0;
	HashMap<Integer, Card> cards;
	private Save save;
	
	/**
	 * 判断一个字符串是否为数字
	 * <p>
	 * 字符串为空串或非数字串将返回false。
	 * 
	 * @param str
	 * 		要判断的字符串
	 */
	private boolean isInt(String str) {
		// 为空串返回false
		if (str == null || str.length() == 0)
			return false;
		for (char c : str.toCharArray())
			// 非数字返回false
			if (!Character.isDigit(c))
				return false;
		return true;
	}
	
	private String getKey(ReadFile rf) 
	throws IOException {
		String key = rf.readLine();
		if(key == null)
			return key;
		if(key.equals(VALUESEPERSTOR))
			key = NULLITEMCONTENT;
		return key;
	}
	
	private Vector<String> getValue(ReadFile rf) 
	throws IOException {
		Vector<String> values = new Vector<String>();
		while(true) {
			String temp = getOneValue(rf);
			if(temp == null || temp.equals(ITEMSEPERATOR))
				break;
			if(temp.equals(VALUESEPERSTOR))
				continue;
			values.add(temp);
		}
		return values;
	}
	
	private String getOneValue(ReadFile rf) 
	throws IOException {
		String temp = rf.readLine();
		if(temp == null || temp.equals(ITEMSEPERATOR) || 
				temp.equals(VALUESEPERSTOR))
			return temp;
		String tempx = rf.readLine();
		while(tempx != null && !tempx.equals(ITEMSEPERATOR) &&
				!tempx.equals(VALUESEPERSTOR)) {
			temp += tempx;
			temp += NEWLINE;
			tempx = rf.readLine();
		}
		return temp;
	}
	
	private void loadImage(final Card card) {
		if(card == null)
			return;
		new Thread() {
			public void run() {
				File fi = new File(CARDPATH + card.getId() + ".p"); // 大图文件
				if (!fi.isFile())
					return;
				AffineTransform transform = new AffineTransform();
				BufferedImage bis;
				try {
					int nw, nh;
					bis = ImageIO.read(fi);
					int w = bis.getWidth();
					int h = bis.getHeight();
					// double scale = (double) w / h;
					if(w > h) {
						nw = 115;
						nh = (nw * h) / w;
					} else {
						nh = 115;
						nw = (nh * w) / h;
					}
					double sx = (double) nw / w;
					double sy = (double) nh / h;
					transform.setToScale(sx, sy);
//					System.out.println(w + " " + h);
					AffineTransformOp ato = new AffineTransformOp(transform,
							AffineTransformOp.TYPE_BILINEAR);
					BufferedImage bid = new BufferedImage(nw, nh,
							bis.getType());
					ato.filter(bis, bid);
					if(w > h) {
						nw = 300;
						nh = (nw * h) / w;
					} else {
						nh = 300;
						nw = (nh * w) / h;
					}
					sx = (double) nw / w;
					sy = (double) nh / h;
					transform.setToScale(sx, sy);
//					System.out.println(w + " " + h);
					ato = new AffineTransformOp(transform,
							AffineTransformOp.TYPE_BILINEAR);
					BufferedImage bidd = new BufferedImage(nw, nh,
							bis.getType());
					ato.filter(bis, bidd);
					card.setImage(bidd);
					card.setScaleImage(bid);
					card.setImageReady(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException e) {
					card.setImage(null);
					card.setScaleImage(null);
					card.setImageReady(true);
				}
			}
		}.start();
	}
	
	private Card get(int cardId, boolean buffer) {
		if(cards.size() > 100)
			cards.remove(cards.get(cards.keySet().toArray()[0]));
		Card card = cards.get(cardId);
		if(card == null) {
			File file = new File(CARDPATH + cardId);
			if(file.isFile()) {
				card = ModelFactory.createCard(cardId);
				ReadFile rf;
				try {
					rf = new ReadFile(file);
					String item = rf.readLine();
					if(item == null || item == "")
						item = NULLGROUPNAME;
					card.setFirstName(item);
					item = rf.readLine();
					if(item == null || item == "")
						item = NULLGROUPNAME;
					card.setLastName(item);
					while(true) {
						String key = getKey(rf);
						if(key == null || key.equals(SEPERATOR))
							break;
						if(key.equals(ITEMSEPERATOR))
							continue;
						Vector<String> value = getValue(rf);
						if(value == null)
							break;
						card.addItem(key, value);
					}
					while(true) {
						String id = rf.readLine();
						while(id != null && !id.equals(SEPERATOR))
							if(isInt(id))
								break;
							else
								id = rf.readLine();
						if(id == null || id.equals(SEPERATOR))
							break;
						String content  = rf.readLine();
						if(content == null || content.equals(SEPERATOR))
							break;
						card.addShowRelationship(Integer.valueOf(id), content);
					}
					while(true) {
						String id = rf.readLine();
						while(id != null && !id.equals(SEPERATOR))
							if(isInt(id))
								break;
							else
								id = rf.readLine();
						if(id == null || id.equals(SEPERATOR))
							break;
						card.addHideRelationship(Integer.valueOf(id));
					}
					rf.close();
					loadImage(card);
					if(buffer)
						cards.put(cardId, card);
				} catch (FileNotFoundException e) {
					System.err.println("MyCard: card " + cardId + " not exists!");
				} catch (IOException e) {
					System.err.println("MyCard: IOException!");
				}
			}
		}
		return card;
	}
	
	public MyCardController() {
		cards = new HashMap<Integer, Card>();
		save = new Save();
	}
	
	public Card addCard(String firstName, String lastName) {
		File file = new File(CARDPATH + staticId);
		while(file.exists())
			file = new File(CARDPATH + ++staticId);
		Card card = ModelFactory.createCard(staticId);
		card.setFirstName(firstName);
		card.setLastName(lastName);
		cards.put(staticId, card);
		return card;
	}

	public boolean addCardItem(int id, String item, String content) {
		Card card = get(id, true);
		if(card != null) {
			card.addItem(item, content);
			return true;
		}
		return false;
	}

	public boolean addRelationship(int cardId, int personId, String content) {
		Card card = get(cardId, true);
		if(card != null) {
			Card person = get(personId, true);
			if(person != null) {
				card.addShowRelationship(personId, content);
				if(person.getShowRelationship(cardId) == null) {
					person.addHideRelationship(cardId);
				}
				return true;
			}
		}
		return false;
	}

	public boolean deleteCard(int id) {
		DeleteFile df = new DeleteFile(CARDPATH + id);
		df.delete();
		cards.remove(id);
		return true;
	}

	public boolean deleteCardItem(int id, String item, String content) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteRelationship(int cardId, int personId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean findByItem(int cardId, String item, String content,
			boolean wholeWord) {
		Card card = get(cardId, false);
		if(card != null) {
			if(item != null) {
				if(content != null) {
					Vector<String> temp = card.getItem(item);
					if(temp == null) {
						return false;
					} else {
						for(String str : temp)
							if(wholeWord && str.equals(content))
								return true;
							else if(!wholeWord && str.contains(content))
								return true;
					}
				} else {
					if(wholeWord)
						return (card.getItem(item) != null);
					else
						for(String it : card.getAllItems().keySet())
							if(it.contains(item))
								return true;
				}
			} else if(content != null) {
				for(String it : card.getAllItems().keySet()) {
					Vector<String> temp = card.getItem(it);
					for(String str : temp)
						if(wholeWord && str.equals(content))
							return true;
						else if(!wholeWord && str.contains(content))
							return true;
				}
			}
		}
		return false;
	}
	
	public boolean findByRelation(int cardId, 
			String content, boolean wholeWord) {		
		Card card = get(cardId, false);
		if(card != null) {
			LinkedHashMap<Integer, String> relation = 
				card.getAllShowRelationship();
			for(String str : relation.values()) {
				if(wholeWord) {
					if(str.equals(content))
						return true;
				} else {
					if(str.contains(content))
						return true;
				}
			}
		}
		return false;
	}

	public Card getCard(int cardId) {
		return get(cardId, true);
	}

	public LinkedHashMap<String, Vector<String>> getCardItems(int id) {
		return null;
	}

	public LinkedHashMap<Integer, String> getRelationship(int cardId) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean renameCard(int id, String firstName, String lastName) {
		Card card = get(id, true);
		if(card != null) {
			card.setFirstName(firstName);
			card.setLastName(lastName);
			return true;
		}
		return false;
	}

	public boolean save(int id) {
		Card card = get(id, true);
		save.init(card);
		return save.save();
	}

	public boolean setCardItems(int id, LinkedHashMap<String, Vector<String>> items) {
		Card card = get(id, true);
		if(card != null) {
			card.setItems(items);
			return true;
		}
		return false;
	}

	public boolean setRelationship(int cardId, int personId, String content) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean setRelationships(int cardId, 
			LinkedHashMap<Integer, String> relation) {
		Card card = get(cardId, true);
		if(card != null) {
			for(int id : relation.keySet()) {
				addRelationship(cardId, id, relation.get(id));
			}
			return true;
		}
		return false;
	}

	public BufferedImage getImage(int cardId) {
		Card card = get(cardId, true);
		if(card != null)
			return card.getImage();
		return null;
	}

	public BufferedImage getScaleImage(int cardId) {
		Card card = get(cardId, true);
		if(card != null)
			return card.getScaleImage();
		return null;
	}

	public boolean isImageReady(int cardId) {
		Card card = get(cardId, true);
		if(card != null)
			return card.isImageRafdy();
		return false;
	}

	public boolean setImage(final int cardId, final File file) {
		Card card = cards.get(cardId);
		card.setImageReady(false);
		new Thread() {
			public void run() {
				if(file.isFile()) {
					try {
						new MoveFile(file).move(CARDPATH + cardId + ".p");
						loadImage(get(cardId, true));
					} catch (FileNotFoundException e) {
						System.err.println("MyCardController : setImage error!");
					}
				}
			}
		}.start();
		return true;
	}

	
//	public static void main(String args[]) {
//		MyCardController my = new MyCardController();
//		ConstCard card = my.addCard("test", "!");
//		int id = card.getId();
//		LinkedHashMap<String, Vector<String>> items = new LinkedHashMap<String, Vector<String>>();
//		for(int key = 0; key < 10; key++) {
//			Vector<String> values = new Vector<String>();
//			for(int i = 0; i < 10; i++)
//				values.add("value" + i);
//			items.put("key" + key, values);
//		}
//		my.setCardItems(id, items);
//		my.addCardItem(id, "tel", "000000");
//		my.addRelationship(id, 0, "test for relation");
//		my.addRelationship(1, id, "test!");
//		System.out.println(my.cards.get(id).getAllHideRelationship());
//		System.out.println(my.cards.get(0).getAllShowRelationship());
//		my.save(id);
//	}
}
