package com.test.tdq.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.test.tdq.uitl.CommUtil;
import com.test.tdq.uitl.ExportExcel;
import com.test.tdq.uitl.SqlServerJDBCUtile;
import com.test.tdq.uitl.https.HttpClientUtil;

public class GetXinJingTianQi {

	public static void main(String[] args) {
		List<Map<String, Object>> vlaList = new ArrayList<Map<String, Object>>();

		// http://tianqi.2345.com/t/wea_history/js/60616_20161.js
		// http://tianqi.2345.com/t/wea_history/js/60616_201512.js
//		 String yearMath = "201805"; //2018年5月28日 11:31:43
		String yearMath = "201512";
		String url = "";

		String yearMath1 = CommUtil.formatDate(CommUtil.getCurrentDate(), CommUtil.YYYY_MM_DD, CommUtil.YYYYMMDD);
		yearMath1 = yearMath1.substring(0, 6);
		do {
			yearMath = CommUtil.getNextMonthDay(yearMath + "01");
			yearMath = yearMath.substring(0, 6);
			String year = yearMath.substring(0, 4);
			String math = yearMath.substring(4, 6);

			int mathInt = Integer.valueOf(math);

			String yearMathTwo = year + mathInt;

			if (Integer.valueOf(yearMath) >= Integer.valueOf("201712")) {
				url = "http://tianqi.2345.com/t/wea_history/js/" + yearMath + "/60616_" + yearMath + ".js";
			} else {
				url = "http://tianqi.2345.com/t/wea_history/js/60616_" + yearMathTwo + ".js";
			}

			
			String aa = HttpClientUtil.doGet(url, "GBK");

			extracted(vlaList, aa, url);
		} while (!yearMath.equals(yearMath1));

		// 把数据写如excel
		// ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String,
		// Object>>();
		// String[] headers = { "ymd", "week", "bWendu", "yWendu", "tianqi",
		// "fengxiang", "fengli", "aqi", "aqiInfo" };
		// ex.exportExcel("新津16年到现在的天气数据", headers, vlaList);

		SqlServerJDBCUtile serverJDBCUtile = new SqlServerJDBCUtile();
		serverJDBCUtile.getConnection();

		String sql = "INSERT INTO t_xjweather(ymd,week,bWendu,yWendu,tianqi,fengxiang,fengli,aqi,aqiInfo)VALUES(?,?,?,?,?,?,?,?,?)";

		List<Object> params = new ArrayList<Object>();
		for (Map<String, Object> valMap : vlaList) {
			params.clear();
			params.add(valMap.get("ymd"));
			params.add(valMap.get("week"));
			params.add(valMap.get("bWendu"));
			params.add(valMap.get("yWendu"));
			params.add(valMap.get("tianqi"));
			params.add(valMap.get("fengxiang"));
			params.add(valMap.get("fengli"));
			params.add(valMap.get("aqi"));
			params.add(valMap.get("aqiInfo"));

			try {
				serverJDBCUtile.updateByPreparedStatement(sql, params);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		serverJDBCUtile.releaseConn();
		params = null;
	}

	private static void extracted(List<Map<String, Object>> vlaList, String aa, String url) {
		if (!CommUtil.isBlank(aa)) {
			String aaa = aa.split("=")[1];
			aaa = aaa.substring(0, aaa.length() - 1);
			aaa = aaa.replaceAll("'", "\"");
			JSONObject aaaa = null;
			try {
				aaaa = JSONObject.fromObject(aaa);
				JSONArray b = aaaa.optJSONArray("tqInfo");
				List bbb = JSONArray.toList(b);
				for (Object object : bbb) {
					JSONObject c = JSONObject.fromObject(object);
					Map<String, Object> valMap = new HashMap<String, Object>();

					if (!CommUtil.isBlank(c.optString("ymd"))) {
						// System.out.println(c.optString("ymd"));
						valMap.put("ymd", c.optString("ymd"));
						valMap.put("week", CommUtil.getWeek(c.optString("ymd")));
						valMap.put("bWendu", c.optString("bWendu"));
						valMap.put("yWendu", c.optString("yWendu"));
						valMap.put("tianqi", c.optString("tianqi"));
						valMap.put("fengxiang", c.optString("fengxiang"));
						valMap.put("fengli", c.optString("fengli"));
						valMap.put("aqi", c.optString("aqi"));
						valMap.put("aqiInfo", c.optString("aqiInfo"));
						vlaList.add(valMap);
					}
				}
			} catch (Exception e) {
				System.out.println(url);
				System.out.println(aaa);
				e.printStackTrace();
			}
		}
	}

}
