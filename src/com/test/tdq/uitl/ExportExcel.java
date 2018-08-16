package com.test.tdq.uitl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 * 
 * @author leno
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class ExportExcel<T> {

	public void exportExcel(String fileName, List<T> dataset) {
		exportExcel(fileName, null, dataset, "yyyy-MM-dd");
	}

	public void exportExcel(String fileName, String[] headers, List<T> dataset) {
		exportExcel(fileName, headers, dataset, "yyyy-MM-dd");
	}

	/**
	 * 通过一个公共的方法把List<map<String,object>> 中的数据,直接生成Excel 文件.
	 * 次方自能生成一个文件一个Sheet格式的Excel
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            List<map<String,object>> 需要显示的数据集合,
	 *            数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	public void exportExcel(String fileName, String[] headers, List<T> dataset, String pattern) {
		try {
			OutputStream out = new FileOutputStream("D:\\" + fileName + ".xls");
			// 声明一个工作薄
			XSSFWorkbook workbook = new XSSFWorkbook();
			// 处理多个Sheet的情况. Sheet默认名称和文件名称一致
			createSheetAndAddRowDataMethod(fileName, headers, dataset, pattern, workbook);
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @描述: 通过一个公共的方法把List<map<String,object>> 中的数据,直接生成Excel 文件.
	 *      次方法能生成一个文件多个个Sheet格式的Excel,根据传入的数据集个数,进行生成多个Sheet
	 * @author tdq
	 * @日期:2016年12月22日下午1:47:54
	 * @参数:@param fileName 文件名称
	 * @参数:@param headers 表格属性列名数组 需要显示的数据集合,集合中一定要放置Map<String,Object>类型的数据
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @参数:@param yyjcsjList 医院基础信息
	 * @参数:@param brjcsjList 病人基础信息
	 * @参数:@param mzbrxxList 门诊病人信息
	 * @参数:@param zybrxxList 住院病人信息
	 * @参数:@param ysxxList 医生信息
	 * @参数:@param tjxxList 体检信息
	 * @参数:@param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	public void exportExcel(String fileName, String[] yyjcsjheaders, String[] brjcsheaders, String[] mzbrxxheaders, String[] zybrxxheaders, String[] ysxxheaders,
			String[] tjxxheaders, List<T> yyjcsjList, List<T> brjcsjList, List<T> mzbrxxList, List<T> zybrxxList, List<T> ysxxList, List<T> tjxxList, String pattern) {
		try {
			OutputStream out = new FileOutputStream("D:\\gongAnData\\" + fileName + ".xlsx");
			// 声明一个工作薄
			// HSSFWorkbook workbook = new HSSFWorkbook();
			XSSFWorkbook workbook = new XSSFWorkbook();
			// 处理多个Sheet的情况. Sheet默认名称和文件名称一致
			System.out.println("生成EXcel数据文件开始" + CommUtil.getCurrentDateTime());

			createSheetAndAddRowDataMethod("医院基础信息", yyjcsjheaders, yyjcsjList, pattern, workbook);
			yyjcsjheaders = null;
			yyjcsjList = null;

			createSheetAndAddRowDataMethod("病人基础信息", brjcsheaders, brjcsjList, pattern, workbook);
			brjcsheaders = null;
			brjcsjList = null;

			createSheetAndAddRowDataMethod("门诊病人信息", mzbrxxheaders, mzbrxxList, pattern, workbook);
			mzbrxxheaders = null;
			mzbrxxList = null;

			createSheetAndAddRowDataMethod("住院病人信息", zybrxxheaders, zybrxxList, pattern, workbook);
			zybrxxheaders = null;
			zybrxxList = null;

			createSheetAndAddRowDataMethod("医生信息", ysxxheaders, ysxxList, pattern, workbook);
			ysxxheaders = null;
			ysxxList = null;

			createSheetAndAddRowDataMethod("体检信息", tjxxheaders, tjxxList, pattern, workbook);
			tjxxheaders = null;
			tjxxList = null;

			workbook.write(out);
			System.out.println("生成EXcel数据文件 结束" + CommUtil.getCurrentDateTime());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @描述: 通过一个公共的方法把List<map<String,object>> 中的数据,直接生成Excel 文件.
	 *      次方法能生成一个文件多个个Sheet格式的Excel,根据传入的数据集个数,进行生成多个Sheet
	 * @author tdq
	 * @日期:2016年12月22日下午1:47:54
	 * @参数:@param fileName 文件名称
	 * @参数:@param headers 表格属性列名数组 需要显示的数据集合,集合中一定要放置Map<String,Object>类型的数据
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @参数:@param yyjcsjList 医院基础信息
	 * @参数:@param brjcsjList 病人基础信息
	 * @参数:@param mzbrxxList 门诊病人信息
	 * @参数:@param zybrxxList 住院病人信息
	 * @参数:@param ysxxList 医生信息
	 * @参数:@param tjxxList 体检信息
	 * @参数:@param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	public void exportExcel2(String fileName, String[] ksxxjheaders, String[] ryxxheaders, List<T> ksxxList, List<T> ryxxList, String pattern) {
		try {
			OutputStream out = new FileOutputStream("D:\\gongAnData\\" + fileName + ".xlsx");
			// 声明一个工作薄
			// HSSFWorkbook workbook = new HSSFWorkbook();
			XSSFWorkbook workbook = new XSSFWorkbook();
			// 处理多个Sheet的情况. Sheet默认名称和文件名称一致
			System.out.println("生成EXcel数据文件开始" + CommUtil.getCurrentDateTime());
			createSheetAndAddRowDataMethod("科室信息", ksxxjheaders, ksxxList, pattern, workbook);
			createSheetAndAddRowDataMethod("人员信息", ryxxheaders, ryxxList, pattern, workbook);
			workbook.write(out);
			System.out.println("生成EXcel数据文件 结束" + CommUtil.getCurrentDateTime());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @描述: 通过一个公共的方法把List<map<String,object>> 中的数据,直接生成Excel 文件.
	 *      次方法能生成一个文件多个个Sheet格式的Excel,根据传入的数据集个数,进行生成多个Sheet
	 * @author tdq
	 * @日期:2016年12月22日下午1:47:54
	 * @参数:@param fileName 文件名称
	 * @参数:@param headers 表格属性列名数组 需要显示的数据集合,集合中一定要放置Map<String,Object>类型的数据
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @参数:@param yyjcsjList 医院基础信息
	 * @参数:@param brjcsjList 病人基础信息
	 * @参数:@param mzbrxxList 门诊病人信息
	 * @参数:@param zybrxxList 住院病人信息
	 * @参数:@param ysxxList 医生信息
	 * @参数:@param tjxxList 体检信息
	 * @参数:@param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	public void exportExcel3(String fileName, String[] ksxxjheaders, List<T> ksxxList, String title) {
		try {
			OutputStream out = new FileOutputStream("D:\\gongAnData\\" + fileName + ".xlsx");
			// 声明一个工作薄
			// HSSFWorkbook workbook = new HSSFWorkbook();
			XSSFWorkbook workbook = new XSSFWorkbook();

			// 处理多个Sheet的情况. Sheet默认名称和文件名称一致
			System.out.println("生成EXcel数据文件开始" + CommUtil.getCurrentDateTime());

			createSheetAndAddRowDataMethod(title, ksxxjheaders, ksxxList, null, workbook);

			workbook.write(out);
			System.out.println("生成EXcel数据文件 结束" + CommUtil.getCurrentDateTime());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void exportExcel3(String fileName, List<Map<String, Object>> parmList) {
		try {
			FileOutputStream out = new FileOutputStream("" + fileName + ".xlsx");
			// 声明一个工作薄
			// HSSFWorkbook workbook = new HSSFWorkbook();
			XSSFWorkbook workbook = new XSSFWorkbook();
			System.out.println("生成 " + fileName + "开始" + CommUtil.getCurrentDateTime());
			for (Map<String, Object> parmMap : parmList) {
				String[] ksxxjheaders = (String[]) parmMap.get("Headers");
				List<T> ksxxList = (List<T>) parmMap.get("dataList");
				String title = (String) parmMap.get("sheeTitile");
				// 处理多个Sheet的情况. Sheet默认名称和文件名称一致
				createSheetAndAddRowDataMethod(title, ksxxjheaders, ksxxList, null, workbook);
			}
			workbook.write(out);
			out.close();
			workbook.close();
			System.out.println("生成 " + fileName + " 结束" + CommUtil.getCurrentDateTime());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @描述: 通过一个公共的方法把List<map<String,object>> 中的数据,直接生成Excel 文件.
	 *      次方法能生成一个文件多个个Sheet格式的Excel,根据传入的数据集个数,进行生成多个Sheet
	 * @author tdq
	 * @日期:2016年12月22日下午1:47:54
	 * @参数:@param fileName 文件名称
	 * @参数:@param headers 表格属性列名数组 需要显示的数据集合,集合中一定要放置Map<String,Object>类型的数据
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @参数:@param yyjcsjList 医院基础信息
	 * @参数:@param brjcsjList 病人基础信息
	 * @参数:@param mzbrxxList 门诊病人信息
	 * @参数:@param zybrxxList 住院病人信息
	 * @参数:@param ysxxList 医生信息
	 * @参数:@param tjxxList 体检信息
	 * @参数:@param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	public void exportExcel4(String fileName, String[] ksxxjheaders, List<T> ksxxList, String pattern) {
		try {
			OutputStream out = new FileOutputStream("D:\\gongAnData\\" + fileName + ".xls");
			// 声明一个工作薄
			// HSSFWorkbook workbook = new HSSFWorkbook();
			XSSFWorkbook workbook = new XSSFWorkbook();
			// 处理多个Sheet的情况. Sheet默认名称和文件名称一致
			System.out.println("生成EXcel数据文件开始" + CommUtil.getCurrentDateTime());
			createSheetAndAddRowDataMethod1("填报表", ksxxjheaders, ksxxList, pattern, workbook);
			workbook.write(out);
			System.out.println("生成EXcel数据文件 结束" + CommUtil.getCurrentDateTime());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @描述: 创建Sheet并对当前Sheet进行数据填充
	 * @author tdq
	 * @日期:2016年12月22日下午1:09:43
	 * @参数:@param title
	 * @参数:@param headers
	 * @参数:@param dataset
	 * @参数:@param pattern
	 * @参数:@param workbook
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	private void createSheetAndAddRowDataMethod(String title, String[] headers, List<T> dataset, String pattern, XSSFWorkbook workbook) {
		if (null == pattern) {
			pattern = "yyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		if (null != dataset) {
			// 生成一个表格
			XSSFSheet sheet = workbook.createSheet(title);
//			// 设置表格默认列宽度为15个字节
//			sheet.setDefaultColumnWidth((short) 15);
			// 生成一个样式
			XSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 生成一个字体
			XSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.VIOLET.index);
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 把字体应用到当前的样式
			style.setFont(font);
			// 生成并设置另一个样式
			XSSFCellStyle style2 = workbook.createCellStyle();
			style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 生成另一个字体
			XSSFFont font2 = workbook.createFont();
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			// 把字体应用到当前的样式
			style2.setFont(font2);

			// 声明一个画图的顶级管理器
			XSSFDrawing patriarch = sheet.createDrawingPatriarch();
			// 定义注释的大小和位置,详见文档
			XSSFComment comment = patriarch.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
			// 设置注释内容
			comment.setString(new XSSFRichTextString("可以在POI中添加注释！"));
			// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
			comment.setAuthor("tdq");

			// 产生表格标题行
			XSSFRow row = sheet.createRow(0);
			for (short i = 0; i < headers.length; i++) {
				XSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				XSSFRichTextString text = new XSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}

			XSSFFont font3 = workbook.createFont();
			// 遍历集合数据，产生数据行
			int index = 0;
			for (int i = 0; i < dataset.size(); i++) {
				// System.out.println(i);
				index++;
				row = sheet.createRow(index);
				Map<String, Object> valMap = (Map<String, Object>) dataset.get(i);
				for (int j = 0; j < headers.length; j++) {
					String string = headers[j];
					XSSFCell cell = row.createCell(j);
					cell.setCellStyle(style2);
					Object value = valMap.get(string);
					String textValue = null;
					if (value instanceof Integer) {
						int intValue = (Integer) value;
						cell.setCellValue(intValue);
					} else if (value instanceof Float) {
						float fValue = (Float) value;
						XSSFRichTextString textValueF = new XSSFRichTextString(String.valueOf(fValue));
						cell.setCellValue(textValueF);
					} else if (value instanceof Double) {
						double dValue = (Double) value;
						XSSFRichTextString textValueD = new XSSFRichTextString(String.valueOf(dValue));
						cell.setCellValue(textValueD);
					} else if (value instanceof Long) {
						long longValue = (Long) value;
						cell.setCellValue(longValue);
					}
					if (value instanceof Boolean) {
						boolean bValue = (Boolean) value;
						textValue = "男";
						if (!bValue) {
							textValue = "女";
						}
					} else if (value instanceof Date) {
						Date date = (Date) value;
						textValue = sdf.format(date);
					}

					// else if (value instanceof byte[]) {
					// // 有图片时，设置行高为60px;
					// row.setHeightInPoints(60);
					// // 设置图片所在列宽度为80px,注意这里单位的一个换算
					// sheet.setColumnWidth(i, (short) (35.7 * 80));
					// // sheet.autoSizeColumn(i);
					// byte[] bsValue = (byte[]) value;
					// XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0,
					// 1023, 255, (short) 6, index, (short) 6,
					// index);
					// anchor.setAnchorType(2);
					// patriarch.createPicture(anchor,
					// workbook.addPicture(bsValue,
					// XSSFWorkbook.PICTURE_TYPE_JPEG));
					// }

					else {
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						// Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						// Matcher matcher = p.matcher(textValue);
						// if (matcher.matches()) {
						// // 是数字当作double处理
						// cell.setCellValue(Double.parseDouble(textValue));
						// } else {
						XSSFRichTextString richString = new XSSFRichTextString(textValue);
						font3.setColor(HSSFColor.BLUE.index);
						richString.applyFont(font3);
						cell.setCellValue(richString);
						richString = null;
						// }
					}
				}
			}
			 //Auto size all the columns
		    for (int x = 0; x < sheet.getRow(0).getPhysicalNumberOfCells(); x++) {
		    	sheet.autoSizeColumn(x);
		    }
		}
		dataset = null;
		headers = null;
	}

	/**
	 * @描述:
	 * @author tdq
	 * @日期:2017年5月24日下午4:02:52
	 * @参数:@param title
	 * @参数:@param headers
	 * @参数:@param dataset
	 * @参数:@param pattern
	 * @参数:@param workbook
	 */
	private void createSheetAndAddRowDataMethod1(String title, String[] headers, List<T> dataset, String pattern, XSSFWorkbook workbook) {
		if (null != dataset) {
			// 生成一个表格
			XSSFSheet sheet = workbook.createSheet(title);
			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth((short) 15);
			// 生成一个样式
			XSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 生成一个字体
			XSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.VIOLET.index);
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 把字体应用到当前的样式
			style.setFont(font);
			// 生成并设置另一个样式
			// XSSFCellStyle style2 = workbook.createCellStyle();
			// style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			// style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			// style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			// style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			// style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			// style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			// style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// // 生成另一个字体
			// XSSFFont font2 = workbook.createFont();
			// font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			// // 把字体应用到当前的样式
			// style2.setFont(font2);

			// 声明一个画图的顶级管理器
			XSSFDrawing patriarch = sheet.createDrawingPatriarch();
			// 定义注释的大小和位置,详见文档
			XSSFComment comment = patriarch.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
			// 设置注释内容
			comment.setString(new XSSFRichTextString("可以在POI中添加注释！"));
			// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
			comment.setAuthor("leno");

			// 产生表格标题行
			XSSFRow row = sheet.createRow(0);
			for (short i = 0; i < headers.length; i++) {
				XSSFCell cell = row.createCell(i);
				// cell.setCellStyle(style);
				XSSFRichTextString text = new XSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}

			XSSFFont font3 = workbook.createFont();
			// 遍历集合数据，产生数据行
			int index = 4;
			for (int i = 0; i < dataset.size(); i++) {
				index++;
				row = sheet.createRow(index);
				Map<String, Object> valMap = (Map<String, Object>) dataset.get(i);
				for (int j = 0; j < headers.length; j++) {
					String string = headers[j];
					XSSFCell cell = row.createCell(j);
					// cell.setCellStyle(style2);
					Object value = valMap.get(string);
					String textValue = null;
					if (value instanceof Integer) {
						int intValue = (Integer) value;
						cell.setCellValue(intValue);
					} else if (value instanceof Float) {
						float fValue = (Float) value;
						XSSFRichTextString textValueF = new XSSFRichTextString(String.valueOf(fValue));
						cell.setCellValue(textValueF);
					} else if (value instanceof Double) {
						double dValue = (Double) value;
						XSSFRichTextString textValueD = new XSSFRichTextString(String.valueOf(dValue));
						cell.setCellValue(textValueD);
					} else if (value instanceof Long) {
						long longValue = (Long) value;
						cell.setCellValue(longValue);
					}
					if (value instanceof Boolean) {
						boolean bValue = (Boolean) value;
						textValue = "男";
						if (!bValue) {
							textValue = "女";
						}
					} else if (value instanceof Date) {
						Date date = (Date) value;
						if (null == pattern) {
							pattern = "yyy-MM-dd";
						}
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6, index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(bsValue, XSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							XSSFRichTextString richString = new XSSFRichTextString(textValue);
							// font3.setColor(HSSFColor.BLUE.index);
							// richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		// 测试学生
		ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
		String[] headers = { "DJH", "YYMC", "YYDM", "YYDZ", "FRDB" };

		List<Map<String, Object>> dataset = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < headers.length; i++) {
			Map<String, Object> valMap = new HashMap<String, Object>();
			int num = i + 1;
			valMap.put("DJH", num);
			valMap.put("YYMC", num + "A");
			valMap.put("YYDM", num + "B");
			valMap.put("YYDZ", num + "C");
			valMap.put("FRDB", num + "D");
			dataset.add(valMap);
		}

		ex.exportExcel("", headers, dataset);

	}
	// public static void main(String[] args) {
	// // 测试学生
	// ExportExcel<Student> ex = new ExportExcel<Student>();
	// String[] headers = { "学号", "姓名", "年龄", "性别", "出生日期" };
	// List<Student> dataset = new ArrayList<Student>();
	// dataset.add(new Student(10000001, "张三", 20, true, new Date()));
	// dataset.add(new Student(20000002, "李四", 24, false, new Date()));
	// dataset.add(new Student(30000003, "王五", 22, true, new Date()));
	// // 测试图书
	// ExportExcel<Book> ex2 = new ExportExcel<Book>();
	// String[] headers2 = { "图书编号", "图书名称", "图书作者", "图书价格", "图书ISBN",
	// "图书出版社", "封面图片" };
	// List<Book> dataset2 = new ArrayList<Book>();
	// try {
	// BufferedInputStream bis = new BufferedInputStream(
	// new FileInputStream("book.jpg"));
	// byte[] buf = new byte[bis.available()];
	// while ((bis.read(buf)) != -1) {
	// //
	// }
	// dataset2.add(new Book(1, "jsp", "leno", 300.33f, "1234567",
	// "清华出版社", buf));
	// dataset2.add(new Book(2, "java编程思想", "brucl", 300.33f, "1234567",
	// "阳光出版社", buf));
	// dataset2.add(new Book(3, "DOM艺术", "lenotang", 300.33f, "1234567",
	// "清华出版社", buf));
	// dataset2.add(new Book(4, "c++经典", "leno", 400.33f, "1234567",
	// "清华出版社", buf));
	// dataset2.add(new Book(5, "c#入门", "leno", 300.33f, "1234567",
	// "汤春秀出版社", buf));
	//
	// OutputStream out = new FileOutputStream("E://a.xls");
	// OutputStream out2 = new FileOutputStream("E://b.xls");
	// ex.exportExcel(headers, dataset, out);
	// ex2.exportExcel(headers2, dataset2, out2);
	// out.close();
	// JOptionPane.showMessageDialog(null, "导出成功!");
	// System.out.println("excel导出成功！");
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
}
