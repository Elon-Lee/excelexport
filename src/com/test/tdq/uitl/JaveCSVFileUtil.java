package com.test.tdq.uitl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.csvreader.CsvReader;

public class JaveCSVFileUtil {
	static String[] rowArr = new String[] { "YPBM", "C4", "YPMC", "YPJX", "CODE869", "YPBM", "YPGG", "YPCJ", "YPBZ", "C30", "C31" };

	public static void main(String[] args) throws IOException {
		File inFile = new File("D://b.csv"); // 读取的CSV文件
		// File outFile = new File("C://outtest.csv");// 输出的CSV文
		readCSVFileMethod(inFile);
	}

	private static void readCSVFileMethod(File inFile) throws UnsupportedEncodingException, IOException {
		try {

			DataInputStream in = new DataInputStream(new FileInputStream(inFile));

			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "GBK"));
			// BufferedWriter writer = new BufferedWriter(new
			// FileWriter(outFile));
			CsvReader creader = new CsvReader(reader, ',');
			// CsvWriter cwriter = new CsvWriter(writer, ',');
			int i = 0;
			while (creader.readRecord()) {
				if (i == 0) {
					continue;
				}
				String[] valStrArr = creader.getValues();

				for (int j = 0; j < rowArr.length; j++) {
					
				}

				// inString = creader.getRawRecord();// 读取一行数据

				// for (int i = 0; i < str.length; i++) {
				// tmpString = inString.replace(str[i], "," + str[i] + ",");
				// inString = tmpString;
				// }
				// 第一个参数表示要写入的字符串数组，每一个元素占一个单元格，第二个参数为true时表示写完数据后自动换行
				// cwriter.writeRecord(inString.split(","), true);
				// 注意，此时再用cwriter.write(inString)方法写入数据将会看到只往第一个单元格写入了数据，“，”没起到调到下一个单元格的作用
				// 如果用cwriter.write(String
				// str)方法来写数据，则要用cwriter.endRecord()方法来实现换行
				// cwriter.endRecord();//换行
				// cwriter.flush();// 刷新数据

				i++;
			}
			creader.close();
			// cwriter.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}
}