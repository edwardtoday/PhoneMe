package org.kde9.control.ImportAndExport;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.read.*;

import org.kde9.control.Kernel;
import org.kde9.control.FileOperation.ReadFile;
import org.kde9.control.FileOperation.WriteFile;
import org.kde9.model.card.ConstCard;
import org.kde9.util.Constants;
import org.kde9.view.ComponentPool;
import org.kde9.view.dialog.CoolInfoBox;
import org.kde9.view.dialog.WarningInfoBox;

public class MyImportAndExport 
implements ImportAndExport , Constants {
	JFileChooser jfc;
	Kernel kernel;
	int type;
	
	public MyImportAndExport() {
		kernel = ComponentPool.getComponent().getKernel();
	}
	
	public MyImportAndExport(int type) {
		kernel = ComponentPool.getComponent().getKernel();
		this.type = type;
	}
	
	public String saveFile() {
		jfc = new MySaveChooser(".");
		jfc.setPreferredSize(new Dimension(700, 400));
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.addChoosableFileFilter(new MyFileFilter("csv"));
//		jfc.addChoosableFileFilter(new MyFileFilter("vcf"));
		jfc.addChoosableFileFilter(new MyFileFilter("xls"));
		int result = jfc.showSaveDialog(null);
		String path = jfc.getSelectedFile().getAbsolutePath();
		if(path == null)
			return null;
		if(result == JFileChooser.APPROVE_OPTION) {
			String ends = jfc.getFileFilter().getDescription();
			if(ends.equals("*.csv")) {
				if(path.endsWith(".csv"))
					return path;
				else {
					path += ".csv";
					return path;
				}
			}
//			else if(ends.equals("*.vcf")) {
//				if(path.endsWith(".vcf"))
//					return path;
//				else {
//					path += ".vcf";
//					return path;
//				}
//			}
			else if(ends.equals("*.xls")) {
				if(path.endsWith(".xls"))
					return path;
				else {
					path += ".xls";
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
//		jfc.addChoosableFileFilter(new MyFileFilter("vcf"));
		jfc.addChoosableFileFilter(new MyFileFilter("xls"));
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
			}
//			else if(str.equals("vcf")) {
//				return "*.vcf";
//			}
			else if(str.equals("xls")) {
				return "*.xls";
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
			}
//			else if(ends.equals("*.vcf")) {
//				if(temp.endsWith(".vcf")) {
//					temp += ".vcf";
//				}
//			}
			else if(ends.equals("*.xls")) {
				if(temp.endsWith(".xls")) {
					temp += ".xls";
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
	
	public void ExportFile() {
		// TODO Auto-generated method stub
		String filePath = this.saveFile();
		if(filePath == null)
			return;
		int cardCount = kernel.getGroup(0).getGroupMembers().size();
		//System.out.println(cardCount);
		ConstCard card;
		LinkedHashMap<String, Integer> itemsIndex = new LinkedHashMap<String, Integer>();
		/*
		 * 获得index
		 */
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
		if (filePath.endsWith(".csv")) {
			if (type == Constants.EXPORT) {
				try {
					WriteFile wf = new WriteFile(filePath, false, "GB2312");
					String index = "FirstName,LastName";
					for (String itemName : itemsIndex.keySet()) {
						for (int i = 0; i < itemsIndex.get(itemName); i++) {
							index += ",";
							index += itemName;
						}
					}
					wf.writeLine(index);
					String temp = "";
					for (int i = 0; i < cardCount; i++) {
						card = kernel.getCard(i);
						temp = card.getFirstName() + "," + card.getLastName();
						LinkedHashMap<String, Vector<String>> items = card
								.getAllItems();
						for (String itemName : itemsIndex.keySet()) {
							if (items.get(itemName) != null) {
								for (int t = 0; t < items.get(itemName).size(); t++) {
									temp += ",";
									temp += items.get(itemName).get(t);
								}
								for (int t = 0; t < itemsIndex.get(itemName)
										- items.get(itemName).size(); t++)
									temp += ",";
							} else {
								for (int t = 0; t < itemsIndex.get(itemName); t++)
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
			} else if (type == Constants.EXPORTCARD) {
				try {
					WriteFile wf = new WriteFile(filePath, false, "GB2312");
					int cardSelected = ComponentPool.getNameComponent().getSelectedMemberId();
					card = kernel.getCard(cardSelected);
					LinkedHashMap<String, Vector<String>> items = card.getAllItems();
					String index = "FirstName,LastName";
					String temp = card.getFirstName() + "," + card.getLastName();
					for (String itemName : items.keySet()) {
						for (int i = 0; i < items.get(itemName).size(); i++) {
							index += ",";
							index += itemName;
							temp += ",";
							temp += items.get(itemName).get(i);
						}
					}
					wf.writeLine(index);
					wf.writeLine(temp);
					wf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (type == Constants.EXPORTGROUP) {
				int groupSelected = ComponentPool.getGroupComponent().getSelectedGroupId();
				int groupCardCount = kernel.getGroup(groupSelected).getGroupMembers().size();
				LinkedHashMap<String, Integer> gItemsIndex = new LinkedHashMap<String, Integer>();
				LinkedHashSet<Integer> gCardItems = kernel.getGroup(groupSelected).getGroupMembers();
				for(int i : gCardItems) {
					card = kernel.getCard(i);
					LinkedHashMap<String, Vector<String>> items = card.getAllItems();
					for(String itemName : items.keySet()) {
						gItemsIndex.put(itemName, 0);
					}
				}
				for(int i : gCardItems) {
					card = kernel.getCard(i);
					LinkedHashMap<String, Vector<String>> items = card.getAllItems();
					for(String itemName : items.keySet()) {
						if(items.get(itemName).size() > gItemsIndex.get(itemName))
							gItemsIndex.put(itemName, items.get(itemName).size());
					}
				}
				try {
					WriteFile wf = new WriteFile(filePath, false, "GB2312");
					String index = "FirstName,LastName";
					for (String itemName : gItemsIndex.keySet()) {
						for (int i = 0; i < gItemsIndex.get(itemName); i++) {
							index += ",";
							index += itemName;
						}
					}
					wf.writeLine(index);
					String temp = "";
					for (int i : gCardItems) {
						card = kernel.getCard(i);
						temp = card.getFirstName() + "," + card.getLastName();
						LinkedHashMap<String, Vector<String>> items = card.getAllItems();
						for (String itemName : gItemsIndex.keySet()) {
							if (items.get(itemName) != null) {
								for (int t = 0; t < items.get(itemName).size(); t++) {
									temp += ",";
									temp += items.get(itemName).get(t);
								}
								for (int t = 0; t < itemsIndex.get(itemName)
										- items.get(itemName).size(); t++)
									temp += ",";
							} else {
								for (int t = 0; t < itemsIndex.get(itemName); t++)
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
		}else if(filePath.endsWith(".xls")) {
			WritableWorkbook wwb;
			OutputStream os;
			if (type == Constants.EXPORT) {
				try {
					os = new FileOutputStream(filePath);
					wwb = Workbook.createWorkbook(os);
					WritableSheet sheet = wwb.createSheet("sheet1", 0);
					Label label;
					int index = 2;
					label = new Label(0, 0, "FirstName");
					sheet.addCell(label);
					label = new Label(1, 0, "LastName");
					sheet.addCell(label);
					for (String itemName : itemsIndex.keySet()) {
						for (int i = 0; i < itemsIndex.get(itemName); i++) {
							label = new Label(index, 0, itemName);
							sheet.addCell(label);
							index++;
						}
					}
					for (int i = 0; i < cardCount; i++) {
						card = kernel.getCard(i);
						label = new Label(0, i + 1, card.getFirstName());
						sheet.addCell(label);
						label = new Label(1, i + 1, card.getLastName());
						sheet.addCell(label);
						int temp = 2;
						LinkedHashMap<String, Vector<String>> items = card
								.getAllItems();
						for (String itemName : itemsIndex.keySet()) {
							if (items.get(itemName) != null) {
								for (int t = 0; t < items.get(itemName).size(); t++) {
									label = new Label(temp, i + 1, items.get(
											itemName).get(t));
									temp++;
									sheet.addCell(label);
								}
								for (int t = 0; t < itemsIndex.get(itemName)
										- items.get(itemName).size(); t++) {
									label = new Label(temp, i + 1, "NULL");
									temp++;
									sheet.addCell(label);
								}
							} else {
								for (int t = 0; t < itemsIndex.get(itemName); t++) {
									label = new Label(temp, i + 1, "NULL");
									temp++;
									sheet.addCell(label);
								}
							}
						}
					}
					wwb.write();
					wwb.close();
					os.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if (type == Constants.EXPORTCARD) {
				try {
					os = new FileOutputStream(filePath);
					wwb = Workbook.createWorkbook(os);
					WritableSheet sheet = wwb.createSheet("sheet1", 0);
					Label label;
					int index = 2;
					label = new Label(0, 0, "FirstName");
					sheet.addCell(label);
					label = new Label(1, 0, "LastName");
					sheet.addCell(label);
					int cardSelected = ComponentPool.getNameComponent().getSelectedMemberId();
					card = kernel.getCard(cardSelected);
					LinkedHashMap<String, Vector<String>> items = card.getAllItems();
					for (String itemName : items.keySet()) {
						for (int i = 0; i < items.get(itemName).size(); i++) {
							label = new Label(index, 0, itemName);
							sheet.addCell(label);
							index++;
						}
					}
					label = new Label(0, 1, card.getFirstName());
					sheet.addCell(label);
					label = new Label(1, 1, card.getLastName());
					sheet.addCell(label);
					int temp = 2;
					for (String itemName : items.keySet()) {
						for (int i = 0; i < items.get(itemName).size(); i++) {
							label = new Label(temp, 1, items.get(itemName).get(i));
							sheet.addCell(label);
							temp++;
						}
					}
					wwb.write();
					wwb.close();
					os.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if (type == Constants.EXPORTGROUP) {
				try {
					int groupSelected = ComponentPool.getGroupComponent().getSelectedGroupId();
					int groupCardCount = kernel.getGroup(groupSelected).getGroupMembers().size();
					LinkedHashMap<String, Integer> gItemsIndex = new LinkedHashMap<String, Integer>();
					LinkedHashSet<Integer> gCardItems = kernel.getGroup(groupSelected).getGroupMembers();
					for(int i : gCardItems) {
						card = kernel.getCard(i);
						LinkedHashMap<String, Vector<String>> items = card.getAllItems();
						for(String itemName : items.keySet()) {
							gItemsIndex.put(itemName, 0);
						}
					}
					for(int i : gCardItems) {
						card = kernel.getCard(i);
						LinkedHashMap<String, Vector<String>> items = card.getAllItems();
						for(String itemName : items.keySet()) {
							if(items.get(itemName).size() > gItemsIndex.get(itemName))
								gItemsIndex.put(itemName, items.get(itemName).size());
						}
					}
					os = new FileOutputStream(filePath);
					wwb = Workbook.createWorkbook(os);
					WritableSheet sheet = wwb.createSheet("sheet1", 0);
					Label label;
					int index = 2;
					label = new Label(0, 0, "FirstName");
					sheet.addCell(label);
					label = new Label(1, 0, "LastName");
					sheet.addCell(label);
					for (String itemName : gItemsIndex.keySet()) {
						for (int i = 0; i < gItemsIndex.get(itemName); i++) {
							label = new Label(index, 0, itemName);
							sheet.addCell(label);
							index++;
						}
					}
					int rows = 0;
					for (int i : gCardItems) {
						card = kernel.getCard(i);
						label = new Label(0, rows + 1, card.getFirstName());
						sheet.addCell(label);
						label = new Label(1, rows + 1, card.getLastName());
						sheet.addCell(label);
						int temp = 2;
						LinkedHashMap<String, Vector<String>> items = card.getAllItems();
						for (String itemName : gItemsIndex.keySet()) {
							if (items.get(itemName) != null) {
								for (int t = 0; t < items.get(itemName).size(); t++) {
									label = new Label(temp, rows + 1, items.get(itemName).get(t));
									temp++;
									sheet.addCell(label);
								}
								for (int t = 0; t < itemsIndex.get(itemName)- items.get(itemName).size(); t++) {
									label = new Label(temp, rows + 1, "NULL");
									temp++;
									sheet.addCell(label);
								}
							} else {
								for (int t = 0; t < itemsIndex.get(itemName); t++) {
									label = new Label(temp, rows + 1, "NULL");
									temp++;
									sheet.addCell(label);
								}
							}
						}
						rows++;
					}
					wwb.write();
					wwb.close();
					os.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println(filePath);
	}

	public void ImportFile(int id) {
		// TODO Auto-generated method stub
		String filePath = this.openFile();
		if(filePath == null)
			return;
		if(filePath.endsWith(".csv")) {
			importCSV(filePath, id);
		}else if(filePath.endsWith(".xls")) {
			importXLS(filePath, id);
		}
		System.out.println(filePath);
	}
	
	private void importCSV(String filePath, int groupId) {
		LinkedHashMap<String, Integer> itemsIndex = 
			new LinkedHashMap<String, Integer>();
		try {
			ReadFile rf = new ReadFile(filePath, "GB2312");
			String temp = rf.readLine();
			String[] index = temp.split(",");
			for(int i = 2; i < index.length ; i++) {
				itemsIndex.put(index[i], 0);
			}
			for(int i = 2; i < index.length ; i++) {
				int sum = itemsIndex.get(index[i]) + 1;
				itemsIndex.put(index[i], sum);
			}
			for(String item : itemsIndex.keySet()) {
				System.out.println(item + "--------------" + itemsIndex.get(item));
			}
			String content = "";
			String [] tempContent;
			while((content = rf.readLine()) != null) {
				Vector<String> keys = new Vector<String>();
				int p = 0 ,j = 0;
				for (p = 0, j = 0; p < content.length(); p++) {
					if (content.charAt(p) == ',') {
						if (j != p)
							keys.add(content.substring(j, p));
						else
							keys.add("");
						j = p + 1;
					}
				}
				keys.add(content.substring(j));
//				System.out.println(keys.size() + "&&&&&&&&&&&&&");
//				for(int i = 0; i < keys.size(); i++)
//					System.out.print(keys.get(i) + " ");
//				System.out.println();
//				tempContent = content.split(",");
//				System.out.println(tempContent.length + "^^^^^^^^^^^^^^^^^^^^^");
				LinkedHashMap<String, Vector<String>> items = 
					new LinkedHashMap<String, Vector<String>>();
				ConstCard cardAdded; 
				String firstName = keys.get(0);
				String lastName = keys.get(1);
				int col = 2;
				while(col < keys.size()) {
					String oneIndex = index[col];
					Vector<String> realItem = new Vector<String>();
					if(itemsIndex.get(oneIndex) == 1) {
						realItem.add(keys.get(col));
						col++;
						items.put(oneIndex, realItem);
					}else {
						for(int tempIndex = 0; tempIndex < itemsIndex.get(oneIndex); tempIndex++) {
							if(keys.get(col).length() != 0)
								realItem.add(keys.get(col));
							col++;
						}
						items.put(oneIndex, realItem);
					}
				}
				System.out.println(firstName);
				System.out.println(lastName);
				cardAdded = kernel.addCard(groupId, firstName, lastName, items, null);
				System.out.println(cardAdded.getId() + "((((((((((((((((((((");
				kernel.addGroupMember(groupId, cardAdded.getId());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("csv imported!!!");
	}
	
	private void importXLS(String filePath, int groupId) {
		LinkedHashMap<String, Integer> itemsIndex = 
			new LinkedHashMap<String, Integer>();
		InputStream is;
		Workbook wb;
		try {
			is = new FileInputStream(filePath);   
			wb = Workbook.getWorkbook(is); 
			Sheet st = wb.getSheet(0);
			int rowCount = st.getRows();
			int colCount = st.getColumns();
			System.out.println(rowCount + "  :  rowCount");
			System.out.println(colCount + "  :  colCount");
			Cell cell;
			String content = "";
			for(int i = 2; i < colCount ; i++) {
				cell = st.getCell(i, 0);  
				content = cell.getContents().toString();
				itemsIndex.put(content, 0);
			}
			for(int i = 2; i < colCount ; i++) {
				cell = st.getCell(i, 0);  
				content = cell.getContents().toString();
				int sum = itemsIndex.get(content) + 1;
				itemsIndex.put(content, sum);
			}
			for(String item : itemsIndex.keySet()) {
				System.out.println(item + "--------------" + itemsIndex.get(item));
			}
			for(int row = 1; row < rowCount; row++) {
				LinkedHashMap<String, Vector<String>> items = 
					new LinkedHashMap<String, Vector<String>>();
				ConstCard cardAdded;
				cell = st.getCell(0, row);  
				String firstName = cell.getContents().toString();
				cell = st.getCell(1, row);  
				String lastName = cell.getContents().toString();
				int col = 2;
				while(col < colCount) {
					Cell indexCell = st.getCell(col, 0);
					String index = indexCell.getContents().toString();
					Vector<String> temp = new Vector<String>();
					if(itemsIndex.get(index) == 1) {
						cell = st.getCell(col, row);
						content = cell.getContents();
						temp.add(content);
						col++;
						items.put(index, temp);
					}else {
						for(int tempIndex = 0; tempIndex < itemsIndex.get(index); tempIndex++) {
							cell = st.getCell(col, row);
							content = cell.getContents();
							if(content.length() != 0)
								temp.add(content);
							col++;
						}
						items.put(index, temp);
					}
				}
				System.out.println(row + "*************************");
				System.out.println(firstName);
				System.out.println(lastName);
				cardAdded = kernel.addCard(groupId, firstName, lastName, items, null);
				System.out.println(cardAdded.getId() + "((((((((((((((((((((");
				kernel.addGroupMember(groupId, cardAdded.getId());
			}
			wb.close();
			is.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
