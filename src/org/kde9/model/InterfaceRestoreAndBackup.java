package org.kde9.model;

import java.io.IOException;

public interface InterfaceRestoreAndBackup {

	public abstract void checkout() 
	throws IOException;

	public abstract void restore();

	public abstract void backup();

}