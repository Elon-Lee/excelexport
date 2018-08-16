package com.test.tdq.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.test.tdq.uitl.ExportExcel;
import com.test.tdq.uitl.OracleJDBCUtile;
import com.test.tdq.uitl.SqlServerJDBCUtile;
import com.test.tdq.uitl.XMLUtil;

/**
 * @描述: 导出医疗数据,并生成Excel文件
 * @文件路径:com.test.tdq.service.ExportData.java
 * @author tdq
 * @日期:2016年12月22日 上午11:04:56
 */
public class ExportData {

	@SuppressWarnings("unchecked")
	public static void expotExcel() {
		// 获取机构信息;
		Map<String, String> hosMap = XMLUtil.getXMLUtil().getDicList("hospital3");
		// Map<String, String> sqlMap =
		// XMLUtil.getXMLUtil().getProperties("oracleSql");
		Map<String, String> sqlMap = XMLUtil.getXMLUtil().getProperties("expotYPOracleSql");
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		jdbcUtile.getConnection();
		ExportExcel<Map<String, Object>> export = new ExportExcel<Map<String, Object>>();
		List<Object> pList = new ArrayList<Object>();
		Set<String> keySetObj = hosMap.keySet();
		String yyjcxxSql = sqlMap.get("yyjcxxSql");
		// String brjcxx = sqlMap.get("brjcxx");
		// String mzbrxx = sqlMap.get("mzbrxx");
		// String zybrxx = sqlMap.get("zybrxx");
		// String ysxx = sqlMap.get("ysxx");
		// String tjxx = sqlMap.get("tjxx");

		// String ksxx = sqlMap.get("ksxx");
		// String ryxx = sqlMap.get("ryxx");

		try {
			for (String keyStr : keySetObj) {
				String jgid = hosMap.get(keyStr);
				pList.add(keyStr);

				if (keyStr.length() > 6) {
					System.out.println(jgid);
					// pList.add(keyStr);
					// // 获取 医院基础信息
					Map<String, Object> yyjcxxmapObj = jdbcUtile.getHeadersAndDataMethod(yyjcxxSql, pList);
					String[] yyjcxxHeaders = (String[]) yyjcxxmapObj.get("headers");
					List<Map<String, Object>> yyjcxxList = (List<Map<String, Object>>) yyjcxxmapObj.get("dataList");
					// // 获取病人基础信息
					// Map<String, Object> brjcxxMap =
					// jdbcUtile.getHeadersAndDataMethod(brjcxx, pList);
					// String[] jcbrxxHeaders = (String[])
					// brjcxxMap.get("headers");
					// List<Map<String, Object>> brjcsjList = (List<Map<String,
					// Object>>) brjcxxMap.get("dataList");
					//
					// // 获取门诊病人信息
					// Map<String, Object> mzbrxxMap =
					// jdbcUtile.getHeadersAndDataMethod(mzbrxx, pList);
					// String[] mzbrxxHeaders = (String[])
					// mzbrxxMap.get("headers");
					// List<Map<String, Object>> mzbrxxList = (List<Map<String,
					// Object>>) mzbrxxMap.get("dataList");
					//
					// // 获取住院病人信息
					// Map<String, Object> zybrxxMap =
					// jdbcUtile.getHeadersAndDataMethod(zybrxx, pList);
					// String[] zybrxxHeaders = (String[])
					// zybrxxMap.get("headers");
					// List<Map<String, Object>> zybrxxList = (List<Map<String,
					// Object>>) zybrxxMap.get("dataList");
					//
					// // 获取医生信息
					// Map<String, Object> ysxxMap =
					// jdbcUtile.getHeadersAndDataMethod(ysxx, pList);
					// String[] ysxxHeaders = (String[]) ysxxMap.get("headers");
					// List<Map<String, Object>> ysxxList = (List<Map<String,
					// Object>>) ysxxMap.get("dataList");
					//
					// // 获取体检信息
					// Map<String, Object> tjxxMap =
					// jdbcUtile.getHeadersAndDataMethod(tjxx, pList);
					// String[] tjxxHeaders = (String[]) tjxxMap.get("headers");
					// List<Map<String, Object>> tjxxList = (List<Map<String,
					// Object>>) tjxxMap.get("dataList");
					//
					// export.exportExcel(jgid, yyjcxxHeaders, jcbrxxHeaders,
					// mzbrxxHeaders, zybrxxHeaders, ysxxHeaders, tjxxHeaders,
					// yyjcxxList, brjcsjList, mzbrxxList, zybrxxList,
					// ysxxList, tjxxList, null);
					// Map<String, Object> ksxxMap =
					// jdbcUtile.getHeadersAndDataMethod(ksxx, pList);
					// String[] ksxxHeaders = (String[]) ksxxMap.get("headers");
					//
					// List<Map<String, Object>> ksxxList = (List<Map<String,
					// Object>>) ksxxMap.get("dataList");
					// Map<String, Object> ryxxMap =
					// jdbcUtile.getHeadersAndDataMethod(ryxx, pList);
					// String[] ryxxHeaders = (String[]) ryxxMap.get("headers");

					// List<Map<String, Object>> ryxxList = (List<Map<String,
					// Object>>) ryxxMap.get("dataList");

					// 获取 医院基础信息
					export.exportExcel3(jgid, yyjcxxHeaders, yyjcxxList, null);
				}
				pList.clear();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtile.releaseConn();
		}
	}

	@SuppressWarnings("unchecked")
	public static void expotExcelSqlerver() {
		// 获取机构信息;
		Map<String, String> hosMap = XMLUtil.getXMLUtil().getDicList("hospital3");
		ExportExcel<Map<String, Object>> export = new ExportExcel<Map<String, Object>>();
		Map<String, String> sqlMap = XMLUtil.getXMLUtil().getProperties("sqlServerSql");
		SqlServerJDBCUtile sqlserver = new SqlServerJDBCUtile();
		sqlserver.getConnection();
		List<Object> pList = new ArrayList<Object>();
		Set<String> keySetObj = hosMap.keySet();
		String yyjcxxSql = sqlMap.get("yyjcxxSql");
		String brjcxx = sqlMap.get("brjcxx");
		String mzbrxx = sqlMap.get("mzbrxx");
		String zybrxx = sqlMap.get("zybrxx");
		String ysxx = sqlMap.get("ysxx");
		String tjxx = sqlMap.get("tjxx");

		try {
			for (String keyStr : keySetObj) {
				String jgid = hosMap.get(keyStr);
				pList.add(keyStr);
				if (keyStr.length() == 6) {
					// pList.add(keyStr);
					// 获取 医院基础信息
					Map<String, Object> yyjcxxmapObj = sqlserver.getHeadersAndDataMethod(yyjcxxSql, pList);
					String[] yyjcxxHeaders = (String[]) yyjcxxmapObj.get("headers");
					List<Map<String, Object>> yyjcxxList = (List<Map<String, Object>>) yyjcxxmapObj.get("dataList");
					// 获取病人基础信息
					Map<String, Object> brjcxxMap = sqlserver.getHeadersAndDataMethod(brjcxx, pList);
					String[] jcbrxxHeaders = (String[]) brjcxxMap.get("headers");
					List<Map<String, Object>> brjcsjList = (List<Map<String, Object>>) brjcxxMap.get("dataList");

					// 获取门诊病人信息
					Map<String, Object> mzbrxxMap = sqlserver.getHeadersAndDataMethod(mzbrxx, pList);
					String[] mzbrxxHeaders = (String[]) mzbrxxMap.get("headers");
					List<Map<String, Object>> mzbrxxList = (List<Map<String, Object>>) mzbrxxMap.get("dataList");

					// 获取住院病人信息
					Map<String, Object> zybrxxMap = sqlserver.getHeadersAndDataMethod(zybrxx, pList);
					String[] zybrxxHeaders = (String[]) zybrxxMap.get("headers");
					List<Map<String, Object>> zybrxxList = (List<Map<String, Object>>) zybrxxMap.get("dataList");

					// 获取医生信息
					Map<String, Object> ysxxMap = sqlserver.getHeadersAndDataMethod(ysxx, pList);
					String[] ysxxHeaders = (String[]) ysxxMap.get("headers");
					List<Map<String, Object>> ysxxList = (List<Map<String, Object>>) ysxxMap.get("dataList");

					// 获取体检信息
					Map<String, Object> tjxxMap = sqlserver.getHeadersAndDataMethod(tjxx, pList);
					String[] tjxxHeaders = (String[]) tjxxMap.get("headers");
					List<Map<String, Object>> tjxxList = (List<Map<String, Object>>) tjxxMap.get("dataList");

					export.exportExcel(jgid, yyjcxxHeaders, jcbrxxHeaders, mzbrxxHeaders, zybrxxHeaders, ysxxHeaders, tjxxHeaders, yyjcxxList, brjcsjList, mzbrxxList, zybrxxList,
							ysxxList, tjxxList, null);

				}
				pList.clear();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void expotExcelJCDATA() {
		// 获取机构信息;

		// Map<String, String> hosMap =
		// XMLUtil.getXMLUtil().getDicList("hospital3");

		Map<String, String> hosMap = new HashMap<String, String>();

		// hosMap.put("花源-公卫数据", "510132102");
		// hosMap.put("文井-公卫数据", "510132202");
		// hosMap.put("永商-公卫数据", "510132109");
		// hosMap.put("方兴-公卫数据", "510132107");
		hosMap.put("五津-公卫数据", "510132100");

		// 获取机构信息;
		// Map<String, String> sqlMap =
		// XMLUtil.getXMLUtil().getProperties("oracleSql");
		Map<String, String> sqlMap = XMLUtil.getXMLUtil().getProperties("expotYPOracleSql");
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		jdbcUtile.getConnection();
		ExportExcel<Map<String, Object>> export = new ExportExcel<Map<String, Object>>();
		List<Object> pList = new ArrayList<Object>();
		List<Map<String, Object>> parmList = new ArrayList<Map<String, Object>>();
		Set<String> keySetObj = hosMap.keySet();
		String yyjcxxSql = sqlMap.get("zdxx");
		String brjcxx = sqlMap.get("tnb");
		String mzbrxx = sqlMap.get("gxy");
		String etmzrc = sqlMap.get("etmzrc");

		try {
			for (String keyStr : keySetObj) {
				String jgid = hosMap.get(keyStr) + "%";
				pList.add(jgid);
				jgid = keyStr;

				Map<String, Object> yyjcxxmapObj = jdbcUtile.getHeadersAndDataMethod(yyjcxxSql, pList);
				String[] yyjcxxHeaders = (String[]) yyjcxxmapObj.get("headers");
				List<Map<String, Object>> yyjcxxList = (List<Map<String, Object>>) yyjcxxmapObj.get("dataList");

				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("Headers", yyjcxxHeaders);
				paraMap.put("dataList", yyjcxxList);
				paraMap.put("sheeTitile", "病种及诊断人次");
				parmList.add(paraMap);

				// 获取病人基础信息
				Map<String, Object> brjcxxMap = jdbcUtile.getHeadersAndDataMethod(brjcxx, pList);
				String[] jcbrxxHeaders = (String[]) brjcxxMap.get("headers");
				List<Map<String, Object>> brjcsjList = (List<Map<String, Object>>) brjcxxMap.get("dataList");

				Map<String, Object> paraMap1 = new HashMap<String, Object>();
				paraMap1.put("Headers", jcbrxxHeaders);
				paraMap1.put("dataList", brjcsjList);
				paraMap1.put("sheeTitile", "糖尿病血糖满意度");
				parmList.add(paraMap1);

				// 获取门诊病人信息
				Map<String, Object> mzbrxxMap = jdbcUtile.getHeadersAndDataMethod(mzbrxx, pList);
				String[] mzbrxxHeaders = (String[]) mzbrxxMap.get("headers");
				List<Map<String, Object>> mzbrxxList = (List<Map<String, Object>>) mzbrxxMap.get("dataList");

				Map<String, Object> paraMap2 = new HashMap<String, Object>();
				paraMap2.put("Headers", mzbrxxHeaders);
				paraMap2.put("dataList", mzbrxxList);
				paraMap2.put("sheeTitile", "高血压血压控制");
				parmList.add(paraMap2);

				// 14以下儿童门诊人次
				Map<String, Object> etmzrcMap = jdbcUtile.getHeadersAndDataMethod(etmzrc, pList);
				String[] etmzrcHeaders = (String[]) etmzrcMap.get("headers");
				List<Map<String, Object>> etmzrcList = (List<Map<String, Object>>) etmzrcMap.get("dataList");

				Map<String, Object> paraMap3 = new HashMap<String, Object>();
				paraMap3.put("Headers", etmzrcHeaders);
				paraMap3.put("dataList", etmzrcList);
				paraMap3.put("sheeTitile", "14以下儿童门诊人次");
				parmList.add(paraMap3);

				export.exportExcel3(jgid, parmList);
				pList.clear();
				parmList.clear();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtile.releaseConn();
		}

	}

	/**
	 * 导出医院调查表数据
	 * 
	 * 用户名:唐登强 2017年8月29日下午2:02:44
	 */
	public static void exportYYDCB(String year) {
		// 获取机构信息;
		Map<String, String> hosMap = new HashMap<String, String>();

//		 hosMap.put("花源" + year, "510132102");
//		 hosMap.put("文井" + year, "510132202");
		// hosMap.put("永商" + year, "510132109");
//		 hosMap.put("方兴" + year, "510132107");
		// hosMap.put("兴义" + year, "510132105");
		 hosMap.put("五津" + year, "510132100");
		 hosMap.put("武阳" + year, "510132111");
		 hosMap.put("普兴" + year, "510132104");

		// hosMap.put("邓双" + year, "510132110");
		// hosMap.put("新平" + year, "510132106");
		// hosMap.put("金华" + year, "510132103");
		// hosMap.put("安西" + year, "510132108");
		// hosMap.put("花桥" + year, "510132101");

		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		try {

			String relativelyPath = System.getProperty("user.dir");
			// 医院门诊调查表
			String mzsql = new String(Files.readAllBytes(Paths.get(relativelyPath + "/config/sql/yydcb_mz.sql")));
			String zysql = new String(Files.readAllBytes(Paths.get(relativelyPath + "/config/sql/yydcb_zy.sql")));

			jdbcUtile.getConnection();
			ExportExcel<Map<String, Object>> export = new ExportExcel<Map<String, Object>>();
			List<Object> pList = new ArrayList<Object>();
			List<Map<String, Object>> parmList = new ArrayList<Map<String, Object>>();
			Set<Entry<String, String>> keySetObj = hosMap.entrySet();

			for (Entry<String, String> keyStr : keySetObj) {
				pList.add(year + "-01-1 00:00:00");
				pList.add(year + "-12-31 23:59:59");
				pList.add(year + "-01-1 00:00:00");
				pList.add(year + "-12-31 23:59:59");
				pList.add(keyStr.getValue());
				String jgid = keyStr.getKey();

				Map<String, Object> yyjcxxmapObj = jdbcUtile.getHeadersAndDataMethod(mzsql, pList);
				String[] yyjcxxHeaders = (String[]) yyjcxxmapObj.get("headers");
				List<Map<String, Object>> yyjcxxList = (List<Map<String, Object>>) yyjcxxmapObj.get("dataList");

				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("Headers", yyjcxxHeaders);
				paraMap.put("dataList", yyjcxxList);
				paraMap.put("sheeTitile", "门诊调查表");
				parmList.add(paraMap);

				// // 获取病人基础信息
				Map<String, Object> brjcxxMap = jdbcUtile.getHeadersAndDataMethod(zysql, pList);
				String[] jcbrxxHeaders = (String[]) brjcxxMap.get("headers");
				List<Map<String, Object>> brjcsjList = (List<Map<String, Object>>) brjcxxMap.get("dataList");

				Map<String, Object> paraMap1 = new HashMap<String, Object>();
				paraMap1.put("Headers", jcbrxxHeaders);
				paraMap1.put("dataList", brjcsjList);
				paraMap1.put("sheeTitile", "住院调查表");
				parmList.add(paraMap1);

				export.exportExcel3(jgid, parmList);
				pList.clear();
				parmList.clear();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			jdbcUtile.releaseConn();
		}

	}

	public static void main(String[] args) {
		// ExportData.expotExcel();
		// ExportData.expotExcelSqlerver();
		// ExportData.expotExcelJCDATA();
		ExportData.exportYYDCB("2015");
		ExportData.exportYYDCB("2016");

	}

}
