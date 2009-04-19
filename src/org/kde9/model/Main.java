package org.kde9.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.kde9.util.TreeNode;

import ch.randelshofer.quaqua.QuaquaManager;

public class Main 
extends JFrame
implements KeyListener, TreeModel {
	private JLabel label;
	private JTextField textField;
	private JPanel panel;
	private JTree tree;
	private TreeNode root;
	private int state = 0;
	private int currentGroup;
	private int currentCard;
	
	Ikernel ikernel;
	
	/**
	 * 
	 */
	public Main() {
		ikernel = new Ikernel();
		try {
			ikernel.init();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setLayout(new BorderLayout());
		
		root = new TreeNode();
		root.setType("AllGroups");
		
		label = new JLabel();
		label.setText(">>");
		textField = new JTextField();
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		tree = new JTree(this);
		tree.setVisible(true);
		
		for(int i = 0; i < tree.getRowCount(); i++)
			tree.expandRow(i);
		
		panel.add("West",label);
		panel.add("Center", textField);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		add("North", panel);
		JScrollPane scrollPane = new JScrollPane(tree);
		add("Center", scrollPane);
		
		textField.putClientProperty("JComponent.sizeVariant","small");
		//textField.putClientProperty("Quaqua.TextComponent.autoSelect",Boolean.TRUE);
		
		textField.addKeyListener(this);
		
		setSize(400, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String args[]) {
		if (System.getProperty("os.name").toLowerCase().indexOf("mac") == -1) {
			System.setProperty("Quaqua.Debug.crossPlatform", "true");
			System.setProperty("swing.aatext", "true");
			System.setProperty("JButton.style", "bevel");
		}
		try {
			System.setProperty("Quaqua.TabbedPane.design", "jaguar");
			String lafClassName = QuaquaManager.getLookAndFeelClassName();
			System.out.println(lafClassName);
			UIManager.setLookAndFeel(lafClassName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Main main = new Main();
		main.setVisible(true);
	}
	
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		char a = arg0.getKeyChar();
		switch(a) {
		case KeyEvent.VK_ENTER:
			String string = textField.getText();
			StringReader sr = new StringReader(string);
			Vector<String> v = new Vector<String>();
			int c = -1;
			try {
				c = sr.read();
				while(c != -1) {
					String str = new String();
					while(c != -1 && (char)c != ' ') {
						str += (char)c;
						c = sr.read();
					}
					v.add(str);
					c = sr.read();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(v.get(0).equals("cd")) {
				if(state == 0) {
					currentGroup = Integer.valueOf(v.get(1));
					state++;
					label.setText(">>Group" + v.get(1) + ">>");
					textField.setSelectionStart(0);
					textField.setSelectionEnd(textField.getText().length());
				} else if(state == 1) {
					currentCard = Integer.valueOf(v.get(1));
					state++;
					String temp = label.getText();
					label.setText(temp + "Member" + v.get(1) + ">>");
					textField.setSelectionStart(0);
					textField.setSelectionEnd(textField.getText().length());
				} else {
					textField.setText("command error!");
					textField.setSelectionStart(0);
					textField.setSelectionEnd(textField.getText().length());
				}
			}
			else if(v.get(0).equals("cd..")) {
				if(state == 1) {
					state--;
					label.setText(">>");
					textField.setSelectionStart(0);
					textField.setSelectionEnd(textField.getText().length());
				} else if(state == 2) {
					state--;
					label.setText(">>Group" + currentGroup + ">>");
					textField.setSelectionStart(0);
					textField.setSelectionEnd(textField.getText().length());
				} else {
					textField.setText("command error!");
					textField.setSelectionStart(0);
					textField.setSelectionEnd(textField.getText().length());
				}
			}
			else if(v.get(0).equals("add")) {
				if(state == 0) {
					try {
						if(v.get(1).equals("group"))
							ikernel.addGroup(v.get(2), null);
						else if(v.get(1).equals("smartgroup"))
							ikernel.addSmartGroup(v.get(2), 0, null, v.get(3), false);
						else
							textField.setText("command error!");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(state == 1) {
					try {
						if(v.get(1).equals("member"))
							ikernel.addGroupMember(currentGroup, Integer.valueOf(v.get(2)));
						else if(v.get(1).equals("card"))
							ikernel.addCard(currentGroup, v.get(2), null);
						else
							textField.setText("command error!");
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(state == 2) {
					if(v.get(1).equals("item"))
						try {
							ikernel.addCardItem(currentCard, v.get(2), v.get(3));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					else
						textField.setText("command error!");
				}
				textField.setSelectionStart(0);
				textField.setSelectionEnd(textField.getText().length());
			}
			else if(v.get(0).equals("delete")) {
				if(state == 2) {
					try {
						if(v.get(1).equals("item")) {
							ikernel.deleteCardItem(currentCard, v.get(2));
						}
						else
							textField.setText("command error!");
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(state == 0) {
					try {
						if(v.get(1).equals("group")) {
							ikernel.deleteGroup(Integer.valueOf(v.get(2)));
						}
						else
							textField.setText("command error!");
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(state == 1) {
					if(v.get(1).equals("card"))
						try {
							ikernel.deleteCard(Integer.valueOf(v.get(2)));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					else if(v.get(1).equals("member")) {
						try {
							ikernel.deleteGroupMember(currentGroup, Integer.valueOf(v.get(2)));
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else
						textField.setText("command error!");
				}
				textField.setSelectionStart(0);
				textField.setSelectionEnd(textField.getText().length());
			}
			else if(v.get(0).equals("rename")) {
				if(state == 0) {
					try {
						if(v.get(1).equals("group"))
							ikernel.renameGroup(Integer.valueOf(v.get(2)), v.get(3));
						else
							textField.setText("command error!");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(state == 1) {
					try {
						if(v.get(1).equals("member"))
							ikernel.renameCard(Integer.valueOf(v.get(2)), v.get(3));
						else if(v.get(1).equals("card"))
							ikernel.renameCard(Integer.valueOf(v.get(2)), v.get(3));
						else
							textField.setText("command error!");
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(state == 2) {
					if(v.get(1).equals("item"))
						try {
							ikernel.renameCardItem(currentCard, v.get(2), v.get(3));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					else
						textField.setText("command error!");
				}
				textField.setSelectionStart(0);
				textField.setSelectionEnd(textField.getText().length());
			}
			else {
				textField.setText("command error!");
				textField.setSelectionStart(0);
				textField.setSelectionEnd(textField.getText().length());
			}
			tree.updateUI();
			for(int i = 0; i < tree.getRowCount(); i++)
				tree.expandRow(i);
			break;
		default:
		}
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void addTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public Object getChild(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		TreeNode TreeNode = (TreeNode)arg0;
		LinkedHashMap<Integer, String> l;
		LinkedHashMap<String, String> s = new LinkedHashMap<String, String>();
		if(TreeNode.getType().equals("Group")) {
			l = ikernel.getGroupMembers(TreeNode.getId());
			TreeNode child = new TreeNode();
			child.setType("Member");
			child.setId((Integer) l.keySet().toArray()[arg1]);
			child.setName(l.get(l.keySet().toArray()[arg1]));
			return child;
		}
		else if(TreeNode.getType().equals("AllGroups")) {
			l = ikernel.getAllGroups();
			TreeNode child = new TreeNode();
			child.setType("Group");
			child.setId((Integer) l.keySet().toArray()[arg1]);
			child.setName(l.get(l.keySet().toArray()[arg1]));
			return child;
		}
		else {
			try {
				s = ikernel.getCardItem(TreeNode.getId());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TreeNode child = new TreeNode();
			child.setType("Item");
			child.setName((String) s.keySet().toArray()[arg1]);
			child.setContent(s.get(s.keySet().toArray()[arg1]));
			return child;
		}
	}

	public int getChildCount(Object arg0) {
		TreeNode TreeNode = (TreeNode)arg0;
		System.out.println("child");
		System.out.println(TreeNode.getId());
		System.out.println(ikernel.getGroupMembers(TreeNode.getId()));
		if(TreeNode.getType().equals("Group")) {	
			return ikernel.getGroupMembers(TreeNode.getId()).size();
		}
		else if(TreeNode.getType().equals("AllGroups")) {
			System.out.println(ikernel.getAllGroups());
			return ikernel.getAllGroups().size();
		}
		else {
			try {
				return ikernel.getCardItem(TreeNode.getId()).size();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	public int getIndexOfChild(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getRoot() {
		System.out.println("root");
		return root;
	}

	public boolean isLeaf(Object arg0) {
		if(((TreeNode)arg0).getType().equals("Item"))
			return true;
		return false;
	}

	public void removeTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void valueForPathChanged(TreePath arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
