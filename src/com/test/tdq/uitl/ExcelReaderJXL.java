package com.test.tdq.uitl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

public class ExcelReaderJXL {
	/**
	 * 读取xls文件内容
	 * 
	 * @param file
	 *            想要读取的文件对象
	 * @return 返回文件内容
	 */
	public static List<Map<String, String>> getExclFileData(File file ,String[] rowArr) {
		List<Map<String, String>> sheetRestList = new ArrayList<Map<String, String>>();
		try {
			FileInputStream fis = new FileInputStream(file);
			// jxl.Workbook rwb = Workbook.getWorkbook(fis);
			WorkbookSettings setting = new WorkbookSettings();
			setting.setEncoding("GBK");
			Workbook rwb = Workbook.getWorkbook(fis, setting);

//			String[] rowArr = new String[] {"", "", "YPMC", "YPZBM", "ECODE869", "", "", "RCODE869"};

			Sheet[] sheet = rwb.getSheets();
			for (int i = 0; i < sheet.length; i++) {
				Sheet rs = rwb.getSheet(i);
				int rowsNum = rs.getRows();
				for (int j = 1; j < rowsNum; j++) {
					Cell[] cells = rs.getRow(j);

					Map<String, String> valMap = new HashMap<String, String>();

					if (rowArr.length == cells.length) {
						for (int k = 0; k < rowArr.length; k++) {
							String textStr = null != cells[k].getContents() ? cells[k].getContents() : "";
							valMap.put(rowArr[k], textStr.trim());
						}
						sheetRestList.add(valMap);
					} else {
						if (cells.length < 6) {
							String mc = null != cells[2] || null != cells[2].getContents() ? cells[2].getContents() : ""; // 名称
							String jx = null != cells[3] || null != cells[3].getContents() ? cells[2].getContents() : "";// 剂型
							String bm = null != cells[4] || null != cells[4].getContents() ? cells[2].getContents() : "";// 869编码
							// String gg = null != cells[6] || null !=
							// cells[6].getContents() ? cells[2].getContents() :
							// "";// 规格
							// String cj = null != cells[7] || null !=
							// cells[7].getContents() ? cells[2].getContents() :
							// ""; // 厂家
							// System.out.println("记录行数 = 【" + j +
							// "】   药品名称 = 【" + mc + "】  869编码  = 【" + bm +
							// "】 剂型 = 【" + jx + "】 规格 = 【" + gg + "】 厂家 = 【" +
							// cj + "】 ");
							System.out.println("记录行数 = 【" + j + "】   药品名称 = 【" + mc + "】  869编码  = 【" + bm + "】 剂型 = 【" + jx + "】 ");
						}
					}

					// for (int k = 0; k < cells.length; k++) {
					// // sb.append(cells[k].getContents());
					// System.out.println(cells[k].getContents() + " " + );
					// }
				}

			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheetRestList;
	}

	/**
	 * 读取诊所列表 xls文件内容
	 * 
	 * @param file
	 *            想要读取的文件对象
	 * @return 返回文件内容
	 */
	public static List<List<String>> getExclFileDataByZs(File file) {
		List<List<String>> sheetRestList = new ArrayList<List<String>>();
		try {
			FileInputStream fis = new FileInputStream(file);
			// jxl.Workbook rwb = Workbook.getWorkbook(fis);
			WorkbookSettings setting = new WorkbookSettings();
			setting.setEncoding("GBK");
			Workbook rwb = Workbook.getWorkbook(fis, setting);
			Sheet[] sheet = rwb.getSheets();
			for (int i = 0; i < sheet.length; i++) {
				Sheet rs = rwb.getSheet(i);
				int rowsNum = rs.getRows();
				for (int j = 1; j < rowsNum; j++) {
					Cell[] cells = rs.getRow(j);

					List<String> valList = new ArrayList<String>();
					for (int k = 0; k < cells.length; k++) {
						String textStr = null != cells[k].getContents() ? cells[k].getContents() : "";
						valList.add(textStr);
					}
					sheetRestList.add(valList);
					// for (int k = 0; k < cells.length; k++) {
					// // sb.append(cells[k].getContents());
					// System.out.println(cells[k].getContents() + " " + );
					// }
				}
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheetRestList;
	}

	public static void main(String[] args) {
		File file = new File("D:/2016jbbmk.xls");
		List<List<String>> ss = getExclFileDataByZs(file);
		for (List<String> list : ss) {
			
			System.out.println(list);
			
		}
	}
}