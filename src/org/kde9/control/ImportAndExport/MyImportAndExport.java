package org.kde9.control.ImportAndExport;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.kde9.control.Kernel;
import org.kde9.control.FileOperation.WriteFile;
import org.kde9.model.card.ConstCard;
import org.kde9.view.ComponentPool;
import org.kde9.view.dialog.CoolInfoBox;
import org.kde9.view.dialog.WarningInfoBox;

public class MyImportAndExport 
implements ImportAndExport {
	JFileChooser jfc;
	Kernel kernel;
	
	public MyImportAndExport() {
		kernel = ComponentPool.getComponent().getKernel();
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
			String ends = jfc.getFileFilter().getDescription();
			if(ends.equals("*.csv")) {
				if(path.endsWith(".csv"))
					return path;
				else {
					path += ".csv";
					return path;
				}
			}else if(ends.equals("*.vcf")) {
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
			String temp = this.getSelectedFile().getAbsolutePath();
			String ends = jfc.getFileFilter().getDescription();
			if(ends.equals("*.csv")) {
				if(!temp.endsWith(".csv")) {
					temp += ".csv";
				}
			}else if(ends.equals("*.vcf")) {
				if(temp.endsWith(".vcf")) {
					temp += ".vcf";
				}
			}
			File file = new File(temp);
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
		int cardCount = kernel.getGroup(0).getGroupMembers().size();
		//System.out.println(cardCount);
		ConstCard card;
		if (filePath.endsWith(".csv")) {
			try {
				WriteFile wf = new WriteFile(filePath, false, "GB2312");
//				String name = card.getFirstName() + "," + card.getLastName();
//				wf.writeLine(name);
//				items = card.getAllItems();
//				for (String itemName : items.keySet()) {
//					int sum = items.get(itemName).size();
//					String temp = itemName;
//					for (int i = 0; i < sum; i++) {
//						temp += ",";
//						temp += items.get(itemName).get(i);
//					}
//					wf.writeLine(temp);
//					temp = "";
//				}
				LinkedHashMap<String, Integer> itemsIndex = new LinkedHashMap<String, Integer>();
				for(int i = 0 ; i < cardCount; i++) {
					card = kernel.getCard(i);
					LinkedHashMap<String, Vector<String>> items = card.getAllItems();
					for(String itemName : items.keySet()) {
						//itemsIndex.put(itemName);
						itemsIndex.put(itemName, 0);
					}
				}
				for(int i = 0 ; i < cardCount; i++) {
					card = kernel.getCard(i);
					LinkedHashMap<String, Vector<String>> items = card.getAllItems();
					for(String itemName : items.keySet()) {
						if(items.get(itemName).size() > itemsIndex.get(itemName))
							itemsIndex.put(itemName, items.get(itemName).size());
					}
				}
				String index = "FirstName,LastName";
				for(String itemName : itemsIndex.keySet()) {
					for(int i = 0; i < itemsIndex.get(itemName); i++) {
						index += ",";
						index += itemName;
					}	
				}
				wf.writeLine(index);
				String temp = "";
				for(int i = 0; i < cardCount; i++) {
					card = kernel.getCard(i);
					temp = card.getFirstName() + "," + card.getLastName();
					LinkedHashMap<String, Vector<String>> items = card.getAllItems();
					for(String itemName : itemsIndex.keySet()) {
						if(items.get(itemName) != null) {
							for(int t = 0; t < items.get(itemName).size(); t++) {
								temp += ",";
								temp += items.get(itemName).get(t);
							}
							for(int t = 0; t < itemsIndex.get(itemName) - items.get(itemName).size(); t++)
								temp += ",";
						}else {
							for(int t = 0; t < itemsIndex.get(itemName); t++)
								temp += ",";
						}
					}
					wf.writeLine(temp);
					temp = "";
				}
				wf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(filePath);
	}

	public void ImportFile(int id) {
		// TODO Auto-generated method stub
		String filePath = this.openFile();
		
		System.out.println(filePath);
	}
	
}
