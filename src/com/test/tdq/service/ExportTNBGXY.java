package com.test.tdq.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.tdq.uitl.ExportExcel;
import com.test.tdq.uitl.OracleJDBCUtile;

/**
 * 导出疾控需要的 糖尿病和高血压 随访
 * 
 * @文件名: com.test.tdq.service.ExportTNBGXY.java
 * @author: 唐登强
 * @日期: 2017年9月4日上午11:41:13
 */
public class ExportTNBGXY {
	public static void main(String[] args) {
//		ExportTNBGXY.exportTNB(); 
//		ExportTNBGXY.exportGXY();
//		ExportTNBGXY.exportGRDA();
		ExportTNBGXY.exportNJB();
	}

	private static void exportNJB() {

		// 获取机构信息;
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		try {

			String relativelyPath = System.getProperty("user.dir");
			// 医院门诊调查表
			String mzsql = new String(Files.readAllBytes(Paths.get(relativelyPath + "/config/sql/njb.sql")));
			jdbcUtile.getConnection();
			ExportExcel<Map<String, Object>> export = new ExportExcel<Map<String, Object>>();
			List<Object> pList = new ArrayList<Object>();

			List<Map<String, Object>> parmList = new ArrayList<Map<String, Object>>();

			Map<String, Object> yyjcxxmapObj = jdbcUtile.getHeadersAndDataMethod(mzsql, pList);
			String[] yyjcxxHeaders = (String[]) yyjcxxmapObj.get("headers");
			List<Map<String, Object>> yyjcxxList = (List<Map<String, Object>>) yyjcxxmapObj.get("dataList");

			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("Headers", yyjcxxHeaders);
			paraMap.put("dataList", yyjcxxList);
			paraMap.put("sheeTitile", "年检表");
			parmList.add(paraMap);

			export.exportExcel3("2015-2017年全县年检表", parmList);
			pList.clear();
			parmList.clear();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			jdbcUtile.releaseConn();
		}

	
		
	}

	/**
	 * 导出糖尿病随访
	 * 
	 * 用户名:唐登强 2017年9月4日上午11:43:21
	 */
	public static void exportTNB() {
		// 获取机构信息;
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		try {

			String relativelyPath = System.getProperty("user.dir");
			// 医院门诊调查表
			String mzsql = new String(Files.readAllBytes(Paths.get(relativelyPath + "/config/sql/tnb.sql")));
			jdbcUtile.getConnection();
			ExportExcel<Map<String, Object>> export = new ExportExcel<Map<String, Object>>();
			List<Object> pList = new ArrayList<Object>();

			List<Map<String, Object>> parmList = new ArrayList<Map<String, Object>>();

			Map<String, Object> yyjcxxmapObj = jdbcUtile.getHeadersAndDataMethod(mzsql, pList);
			String[] yyjcxxHeaders = (String[]) yyjcxxmapObj.get("headers");
			List<Map<String, Object>> yyjcxxList = (List<Map<String, Object>>) yyjcxxmapObj.get("dataList");

			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("Headers", yyjcxxHeaders);
			paraMap.put("dataList", yyjcxxList);
			paraMap.put("sheeTitile", "糖尿病");
			parmList.add(paraMap);

			export.exportExcel3("2015-2017年全县糖尿病随访", parmList);
			pList.clear();
			parmList.clear();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			jdbcUtile.releaseConn();
		}

	}

	public static void exportGXY() {
		// 获取机构信息;
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		try {

			String relativelyPath = System.getProperty("user.dir");
			// 医院门诊调查表
			String mzsql = new String(Files.readAllBytes(Paths.get(relativelyPath + "/config/sql/gxy.sql")));
			jdbcUtile.getConnection();
			ExportExcel<Map<String, Object>> export = new ExportExcel<Map<String, Object>>();
			List<Object> pList = new ArrayList<Object>();

			List<Map<String, Object>> parmList = new ArrayList<Map<String, Object>>();

			Map<String, Object> yyjcxxmapObj = jdbcUtile.getHeadersAndDataMethod(mzsql, pList);
			String[] yyjcxxHeaders = (String[]) yyjcxxmapObj.get("headers");
			List<Map<String, Object>> yyjcxxList = (List<Map<String, Object>>) yyjcxxmapObj.get("dataList");

			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("Headers", yyjcxxHeaders);
			paraMap.put("dataList", yyjcxxList);
			paraMap.put("sheeTitile", "高血压");
			parmList.add(paraMap);

			export.exportExcel3("2015-2017年全县高血压随访", parmList);
			pList.clear();
			parmList.clear();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			jdbcUtile.releaseConn();
		}

	}
	public static void exportGRDA() {
		// 获取机构信息;
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		try {
			
			String relativelyPath = System.getProperty("user.dir");
			// 医院门诊调查表
			String mzsql = new String(Files.readAllBytes(Paths.get(relativelyPath + "/config/sql/grda.sql")));
			jdbcUtile.getConnection();
			ExportExcel<Map<String, Object>> export = new ExportExcel<Map<String, Object>>();
			List<Object> pList = new ArrayList<Object>();
			
			List<Map<String, Object>> parmList = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> yyjcxxmapObj = jdbcUtile.getHeadersAndDataMethod(mzsql, pList);
			String[] yyjcxxHeaders = (String[]) yyjcxxmapObj.get("headers");
			List<Map<String, Object>> yyjcxxList = (List<Map<String, Object>>) yyjcxxmapObj.get("dataList");
			
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("Headers", yyjcxxHeaders);
			paraMap.put("dataList", yyjcxxList);
			paraMap.put("sheeTitile", "个人档案");
			parmList.add(paraMap);
			
			export.exportExcel3("2015-2017年全县个人档案", parmList);
			pList.clear();
			parmList.clear();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			jdbcUtile.releaseConn();
		}
		
	}

}
