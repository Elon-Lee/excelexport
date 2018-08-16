package com.test.tdq.service;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.test.tdq.uitl.ExcelReaderJXL;
import com.test.tdq.uitl.OracleJDBCUtile;

/**
 * @描述: 869 批量对码
 * @文件路径:com.test.tdq.service.YB869DM.java
 * @author tdq
 * @日期:2017年6月2日 上午9:22:24
 */
public class YB869DM {


//	MZZYTJService.exportMZZYTJB("510132107", "方兴");
//	MZZYTJService.exportMZZYTJB("510132100", "五津");
//	MZZYTJService.exportMZZYTJB("510132111", "武阳");
//	MZZYTJService.exportMZZYTJB("510132202", "文井");
//	MZZYTJService.exportMZZYTJB("510132104", "普兴");
//	MZZYTJService.exportMZZYTJB("510132102", "花源");
//	MZZYTJService.exportMZZYTJB("510132105", "兴义");
//	MZZYTJService.exportMZZYTJB("510132109", "永商");
	public static void save869CodeMethod(String jgid,File file) throws SQLException {

		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		Connection con = jdbcUtile.getConnection();
		StringBuffer sb = new StringBuffer();

		// 读取Excel文件
		String[] rowArr = new String[] { "", "", "YPMC", "YPZBM", "ECODE869", "", "", "RCODE869" };
	
		List<Map<String, String>> excleData = ExcelReaderJXL.getExclFileData(file, rowArr);
		// 根据EXCEL的数据更新数据库中的数据

		sb.append("select t.code869 as CODE869 From yb_ypdz t  where t.ypxh = ? and t.cdxh = ? ");

		String querySql = sb.toString();
		sb.setLength(0);
		sb.append("update yb_ypdz t set t.code869 = ? where t.ypxh = ? and t.cdxh = ? ");

		String updateSql = sb.toString();
		List<Object> params = new ArrayList<Object>();
		PreparedStatement ps = con.prepareStatement(updateSql);

		for (int i = 1; i < excleData.size(); i++) {

			Map<String, String> map = excleData.get(i);

			String YPZBM = map.get("YPZBM");
			String RCODE869 = map.get("RCODE869").trim();
			String ECODE869 = map.get("ECODE869");
			String[] YPZBMArr = YPZBM.split("A");

			params.add(YPZBMArr[0]);
			params.add(YPZBMArr[1]);
			List<Map<String, Object>> rsList = jdbcUtile.findModeResult(querySql, params);
			params.clear();
			for (Map<String, Object> map2 : rsList) {
				if (!RCODE869.equals(String.valueOf(map2.get("CODE869")).trim())) {
					// 不相等的话,就修改数据的
					ps.setObject(1, RCODE869);
					ps.setObject(2, YPZBMArr[0]);
					ps.setObject(3, YPZBMArr[1]);
					 ps.executeUpdate();
					System.out.println("已处理 ! 真确的869: " + RCODE869 + " 错误的869 :" + String.valueOf(map2.get("CODE869")).trim() + " Excel文件的错误869 :" + ECODE869 + " 药品序号: " + YPZBMArr[0] + " 药品产地: "
							+ YPZBMArr[1]);
				}else{
					System.out.println("未处理 ! 真确的869: " + RCODE869 + " 错误的869 :" + String.valueOf(map2.get("CODE869")).trim() + " Excel文件的错误869 :" + ECODE869 + " 药品序号: " + YPZBMArr[0] + " 药品产地: "
							+ YPZBMArr[1]);
				}
					
			}
		}
	}

	public static void main(String[] args) {
		try {
			File file = new File("D:/gongAnData/新平8692.xls");
			YB869DM.save869CodeMethod("510132100",file);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
