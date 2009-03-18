package org.kde9.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import org.kde9.util.Constants;
import org.kde9.view.GroupComponent;

public class Kernel implements Constants{
	/**
	 * ���Һ�����Ҫ���ܳ�ǿ���У�
	 */
	public boolean find() {
		//TODO
		return false;
	}
	
	public Icard readCard() {
		return null;
	}
	
	public GroupComponent readGroup() {
		return null;
	}
	
	/**
	 * �������е�group
	 * <p>
	 * ͨ����AllGroups�ļ��������group��id������Ӧ��group�ļ���
	 * ���ļ��н�����group�����������һ��vector�У�
	 * ���շ��ذ�������group��vector
	 */
	public static Vector<Igroup> readAllGroups() throws IOException {
		ReadFile wf = new ReadFile(GROUP_PATH + ALLGROUPS);
		Vector<Igroup> allGroups = new Vector<Igroup>();
		while(true) {
			String id = wf.readLine();
			if(id != System.getProperty("line.separator") && id != null) {
				System.out.println(id);
				ReadFile wfx = new ReadFile(GROUP_PATH + id);
				String groupName = wfx.readLine();
				Vector<Iperson> persons = new Vector<Iperson>();
				while(true) {
					String personId = wfx.readLine();
					String personName = wfx.readLine();
					if(personName == null || personId == null)
						break;
					persons.add(new Iperson(Integer.valueOf(personId), personName));
				}
				Igroup g = new Igroup(Integer.valueOf(id), groupName, persons);
				allGroups.add(g);
			}
			else
				break;
		}
		System.out.println(allGroups.size());
		return allGroups;
	}
}
