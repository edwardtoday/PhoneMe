package org.kde9.control.ImportAndExport;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.kde9.view.ComponentPool;
import org.kde9.view.dialog.CoolInfoBox;
import org.kde9.view.dialog.WarningInfoBox;

public class MyImportAndExport 
implements ImportAndExport {
	JFileChooser jfc;
	
	public MyImportAndExport() {
		
	}
	
	public String saveFile() {
		jfc = new MySaveChooser(".");
		jfc.setPreferredSize(new Dimension(700, 400));
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.addChoosableFileFilter(new MyFileFilter("csv"));
		jfc.addChoosableFileFilter(new MyFileFilter("vcf"));
		int result = jfc.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			String path = jfc.getSelectedFile().getAbsolutePath();
			if(jfc.getDescription(jfc.getSelectedFile()).equals("*.csv")) {
				if(path.endsWith(".csv"))
					return path;
				else {
					path += ".csv";
					return path;
				}
			}else if(jfc.getDescription(jfc.getSelectedFile()).equals("*.vcf")) {
				if(path.endsWith(".vcf"))
					return path;
				else {
					path += ".vcf";
					return path;
				}
			}
		}
		return null;
	}
	
	public String openFile() {
		jfc = new MyOpenChooser(".");
		jfc.setPreferredSize(new Dimension(700, 400));
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.addChoosableFileFilter(new MyFileFilter("csv"));
		jfc.addChoosableFileFilter(new MyFileFilter("vcf"));
		int result = jfc.showOpenDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			return jfc.getSelectedFile().getAbsolutePath();
		}
		return null;
	}
	
	private class MyFileFilter
	extends FileFilter {
		String str;
		
		public MyFileFilter(String str) {
			// TODO Auto-generated constructor stub
			this.str = str;
		}

		@Override
		public boolean accept(File f) {
			// TODO Auto-generated method stub
			if(f.isDirectory()) {
				return true;
			}
			String fileName = f.getName();
			int index = fileName.lastIndexOf(".");
			if(index > 0 && index < fileName.length() - 1) {
				String extension = fileName.substring(index + 1).toLowerCase();
				if(extension.equals(str)) {
					return true;
				}
			}
			return false;
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			if(str.equals("csv")) {
				return "*.csv";
			}else if(str.equals("vcf")) {
				return "*.vcf";
			}
			return "";
		}
		
	}

	private class MyOpenChooser
	extends JFileChooser {
		public MyOpenChooser(String path) {
			// TODO Auto-generated constructor stub
			super(path);
		}
		
		public void approveSelection() {
			File file = new File(getSelectedFile().getPath());
			if (file.exists())
			    super.approveSelection();
			else
			    new CoolInfoBox(ComponentPool.getComponent(),"你选择的文件不存在，请重新选择！",
						Color.YELLOW , 200, 100);
		}
	}
	
	private class MySaveChooser
	extends JFileChooser {
		public MySaveChooser(String path) {
			// TODO Auto-generated constructor stub
			super(path);
		}
		
		public void approveSelection() {
			String temp = this.getSelectedFile().getName();
			File file = this.getSelectedFile();
			if (file.exists()) {
				WarningInfoBox wib = new WarningInfoBox(ComponentPool.getComponent(),"是否要覆盖当前文件？",
						Color.YELLOW, 200, 100);
				if(wib.isFlag()) {
					super.approveSelection(); 
				}
			}else {
				super.approveSelection(); 
			}
		}
	}
	
	public void ExportFile(int id) {
		// TODO Auto-generated method stub
		String filePath = this.saveFile();
		
		System.out.println(filePath);
	}

	public void ImportFile(int id) {
		// TODO Auto-generated method stub
		String filePath = this.openFile();
		
		System.out.println(filePath);
	}
	
}
