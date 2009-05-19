package org.kde9.view.component.brower;

import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.kde9.control.Kernel;
import org.kde9.model.card.ConstCard;
import org.kde9.util.ConfigFactory;
import org.kde9.util.Configuration;
import org.kde9.util.Constants;
import org.kde9.view.ComponentPool;
import org.kde9.view.listener.AddGroupListener;
import org.kde9.view.listener.AddNameListener;
import org.kde9.view.listener.EditListener;


public class BrowerComponent 
extends JPanel 
implements ListSelectionListener, KeyListener, Constants {
	private GroupComponent group;
	private NameComponent name;
	private ViewerComponent viewer;
	private JSplitPane groupSplitPane;
	private JSplitPane nameSplitPane;
	private Kernel kernel;
	private Configuration config;
	

	public BrowerComponent(Kernel kernel) {
		dispatchEvent(new FocusEvent(this, FocusEvent.FOCUS_GAINED,
				true));
		
		ComponentPool.setBrowerComponent(this);
		
		config = ConfigFactory.creatConfig();
		group = new GroupComponent();
		group.getTable().getSelectionModel().addListSelectionListener(this);
		group.getTable().addKeyListener(this);
		name = new NameComponent();
		name.getTable().getSelectionModel().addListSelectionListener(this);
		name.getTable().addKeyListener(this);
		viewer = new ViewerComponent(kernel);
		groupSplitPane = new JSplitPane();
		groupSplitPane.setLeftComponent(group);
		groupSplitPane.setRightComponent(name);
		groupSplitPane.setDividerLocation((Integer)(
				config.getConfig(Constants.MAIN_FRAME_WIDTH, CONFIGINT))/4);
		groupSplitPane.setResizeWeight(0);
		groupSplitPane.setDividerSize(2);
		nameSplitPane = new JSplitPane();
		nameSplitPane.setLeftComponent(groupSplitPane);
		nameSplitPane.setRightComponent(viewer);//////////////////////////////////
		nameSplitPane.setDividerLocation((Integer)(
				config.getConfig(Constants.MAIN_FRAME_WIDTH, CONFIGINT))/2);
		nameSplitPane.setResizeWeight(0.5);
		nameSplitPane.setDividerSize(2);
		this.kernel = kernel;

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		add(nameSplitPane);
		// groupSplitPane.setDividerLocation(0.5d);
		// add(nameSplitPane);
		init();
	}

	public void addEditListener(EditListener editListener) {
		viewer.addEditListener(editListener);
	}
	
	public void addGroupListener(AddGroupListener addGroupListener) {
		group.addGroupListener(addGroupListener);
	}
	
	public void addNameListener(AddNameListener addNameListener) {
		name.addNameListener(addNameListener);
	}
	
	private void init() {
		showAllGroups(0, 0);
		// showGroupMembers(0, 0, 0);
	}

	public void showAllGroups(int begin, int end) {
		LinkedHashMap<Integer, String> map = kernel.getAllGroups();
		group.setGroups(map);
		group.setSelected(begin, end);
	}

	public void showGroupMembers() {
		name.getButtonAdd().setEnabled(true);
		int id = group.getSelectedGroupId();
		LinkedHashMap<Integer, String> members = 
			new LinkedHashMap<Integer, String>();
		for(int current : kernel.getGroup(id).getGroupMembers()) {
			String name;
			if((Integer)config.getConfig(NAME_FOMAT, CONFIGINT) == 0)
				name = 
					kernel.getFirstName(current) + ' ' + kernel.getLastName(current);
			else
				name = 
					kernel.getLastName(current) + ' ' + kernel.getFirstName(current);
			members.put(current, name);
		}
		if(members.size() == 0)
			viewer.clear();
		else
			viewer.ready();
		name.setMembers(members);
		//name.setSelected(begin, end);
		//name.setMembers(map);
	}

	public void showItem() {
		int id = name.getSelectedMemberId();
		if(id != -1) {
			ConstCard card = kernel.getCard(id);
			if(card == null)
				return;
			String name, pinyin;
			if((Integer)config.getConfig(NAME_FOMAT, CONFIGINT) == 0) {
				name = 
					card.getFirstName() + ' ' + card.getLastName();
				pinyin = 
					card.getPinYinFirstName() + ' ' + card.getPinYinLastName();
			}
			else {
				name = 
					card.getLastName() + ' ' + card.getFirstName();
				pinyin = 
					card.getPinYinLastName() + ' ' + card.getPinYinFirstName();
			}
			viewer.setCard(card);
			viewer.setName(name);
			viewer.setPinYin(pinyin);
			viewer.setItems(card.getAllItems());
			viewer.setRelations(card.getAllShowRelationship());
			viewer.setImage(card);
			viewer.ready();
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(e.getSource().equals(group.getSelectionModel()));
		if (this.isEnabled()) {
			if (e.getSource().equals(group.getSelectionModel())) {
				if (group.getSelected() != -1) {
					showGroupMembers();
					name.setSelected(0, 0);
				}
			} else if (e.getSource().equals(name.getSelectionModel())) {
				if (name.getSelected() != -1)
					showItem();
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (group.getTable().hasFocus()) {
				name.getTable().requestFocusInWindow();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (name.getTable().hasFocus()) {
				group.getTable().requestFocusInWindow();
			}
		}
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
