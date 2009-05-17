package org.kde9.control.RestoreAndBackup;

import java.io.IOException;

public interface RestoreAndBackup {
	public void checkout() 
	throws IOException;
	
	public void restore();
	
	public void backup();
}
