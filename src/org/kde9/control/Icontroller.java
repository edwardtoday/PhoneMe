package org.kde9.control;

import org.kde9.model.Ifile;
import org.kde9.view.Igroup;
import org.kde9.view.Imenubar;
import org.kde9.view.Iname;
import org.kde9.view.Iviewer;

public class Icontroller {
	private Imenubar menubar;
	private Iviewer viewer;
	private Igroup group;
	private Iname name;
	private Ifile file;
	
	ButtonAddListener bal;
	
	public Icontroller(
			Imenubar menubar,
			Iviewer viewer,
			Igroup group,
			Iname name,
			Ifile file) {
		this.menubar = menubar;
		this.viewer = viewer;
		this.group = group;
		this.name = name;
		this.file = file;
		bal = new ButtonAddListener();
	}

	public Imenubar getMenubar() {
		return menubar;
	}

	public void setMenubar(Imenubar menubar) {
		this.menubar = menubar;
	}

	public Iviewer getViewer() {
		return viewer;
	}

	public void setViewer(Iviewer viewer) {
		this.viewer = viewer;
	}

	public Igroup getGroup() {
		return group;
	}

	public void setGroup(Igroup group) {
		this.group = group;
	}

	public Iname getName() {
		return name;
	}

	public void setName(Iname name) {
		this.name = name;
	}

	public Ifile getFile() {
		return file;
	}

	public void setFile(Ifile file) {
		this.file = file;
	}
}
