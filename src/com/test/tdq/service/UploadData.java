package com.test.tdq.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.tdq.bean.Jiuzhenxx;
import com.test.tdq.uitl.CommUtil;
import com.test.tdq.uitl.OracleJDBCUtile;
import com.test.tdq.uitl.https.HttpClientUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class UploadData {

	// 上传请求地址
	private static String url = "https://171.221.252.191:8087/pkrkwebapi/";

	public void uploadDateMethod() {
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		jdbcUtile.getConnection();
		// 组装上传数据
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append("select a.brxm as xingming,a.sfzh as shenfenzh,to_char(a.ryrq,'yyyy-MM-dd') as jiuzhenrq,to_char(a.cyrq,'yyyy-MM-dd') as chuyuanrq,a.zyhm as zhuyuanhao,a.ryzd_text as zhuyaozd, ");
		sbBuffer.append("a.zyh as zyh,max(t2.fyhj) as zongjine,nvl(sum(t.yka107), 0) + nvl(max(t2.yfdx), 0) as baoxiaoje, ");
		sbBuffer.append("case when nvl(sum(t.yka107), 0) = 0 then max(t2.fyhj) else max(t2.fyhj) - nvl(sum(t.yka107), 0) - nvl(max(yfdx), 0) ");
		sbBuffer.append("end as zifeije, a.jgid as jgid  ");
		sbBuffer.append("From zy_zyjs t2 left join yb_js02 t on t.zyh = t2.zyh and t2.jscs = t.zyjscs and t.mzzy = 2 and t2.zfpb = 0, zy_brry a ");
		sbBuffer.append("where t2.zyh = a.zyh and a.cypb = 8 and to_char(a.cyrq,'YYYY-mm-dd hh24:mi:ss') >= ? ");
		sbBuffer.append("and to_char(a.cyrq,'YYYY-mm-dd hh24:mi:ss') <= ? and a.sfzh is not null  ");
		sbBuffer.append("group by a.brxm, a.sfzh, a.ryrq, a.cyrq, a.zyhm, a.ryzd_text, a.zyh,a.jgid ");
		String findUploadDataSql = sbBuffer.toString();
		
		System.out.println(findUploadDataSql);
		
		
		sbBuffer.setLength(0);
		sbBuffer.append(" select id, jgid, jgidcode, jgmcpy, jgmczw from up_data_pkrq  where jgid = ?");
		String findOrgSql = sbBuffer.toString();
		sbBuffer.setLength(0);

		sbBuffer.append("insert into up_data_log_jzxx ");
		sbBuffer.append("(id, jiaoyixh, xingming, shenfenzh, menzhenzypb, jiuzhenrq, zhuyuanhao, chuyuanrq, jiuzhenjg, zhuyaozd, ciyaozd, zongjine, zifeije, baoxiaoje, ylfy01, ylfy02, ylfy03, ylfy04, ylfy05, ylfy06, ylfy07, ylfy08, ylfy09, jmfy1, jmfy2, jmfy3, jmfy4, jmfy5, jmfy6, jmfy7, jmfy8, jmfy9, jmfy10, bzfy2, bzfy3, bzfy4, bzfy5) ");
		sbBuffer.append("values ");
		sbBuffer.append("((select max(id) + 1 from up_data_log_jzxx ), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

		try {
			String beginData = CommUtil.getYesterDay(CommUtil.getCurrentDate());
			List<Object> params = new ArrayList<Object>();
			params.add(beginData + " 00:00:00");
			params.add(beginData + " 23:59:59");
			List<Jiuzhenxx> rsList = jdbcUtile.findMoreRefResult(findUploadDataSql, params, Jiuzhenxx.class);
			Map<String, String> createMap = new HashMap<String, String>();
			for (Jiuzhenxx jiuzhenxx : rsList) {
				jiuzhenxx.setMenzhenzypb("2");
				jiuzhenxx.setJiaoyixh(String.valueOf(new Date().getTime()));
				params.clear();
				params.add(jiuzhenxx.getJgid());
				Map<String, Object> rsMap = jdbcUtile.findSimpleResult(findOrgSql, params);
				Object jgidCode = rsMap.get("JGIDCODE");
				jiuzhenxx.setJiuzhenjg(String.valueOf(jgidCode));

				// 检查上传数据
				createMap.put("xingming", jiuzhenxx.getXingming());
				createMap.put("shenfenzh", jiuzhenxx.getShenfenzh());
				createMap.put("jigouid", String.valueOf(jgidCode));

				int returnCode = postMothod(createMap, "GetPinkunpb");
				if (1 == returnCode) {
					// 上传数据
					createMap.clear();
					String xmlStr = beanToXMLStr(jiuzhenxx);
					createMap.put("jiuzhenxx", xmlStr);
					for (int i = 0; i < 3; i++) {
						returnCode = postMothod(createMap, "SendJiuzhenxx");
						if (1 == returnCode) {
							jiuzhenxx.setScbz("1");
							break;
						}
					}
					// 不管上传成功还是失败后. 都进行数据入库,,
//					jdbcUtile.updateByPreparedStatement(sql, params);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtile.releaseConn();
		}

	}

	public static int postMothod(Map<String, String> createMap, String uri) {
		String httpOrgCreateTest = url + "/" + uri;
		String httpOrgCreateTestRtn = HttpClientUtil.doPost(httpOrgCreateTest, createMap, "GB2312");
		return Integer.valueOf(httpOrgCreateTestRtn).intValue();
	}

	public static String beanToXMLStr(Jiuzhenxx joe) {

		XStream xstream = new XStream(new StaxDriver());
		xstream.omitField(joe.getClass(), "scbz");
		xstream.omitField(joe.getClass(), "jgid");
		xstream.omitField(joe.getClass(), "zyh");
		// XStream的XML输出更简洁,可以为您的自定义类名创建别名XML元素名称。这是唯一类型的映射需要使用XStream甚至是可选的。
		xstream.alias("jiuzhenxx", joe.getClass());
		// bean to XML
		String xml = xstream.toXML(joe);
		// XML to bean
		// Jiuzhenxx newJoe = (Jiuzhenxx) xstream.fromXML(xml);
		// System.out.println(newJoe.getBaoxiaoje());

		System.out.println(xml);

		return xml;
	}

	public static void main(String[] args) {
		UploadData uploadData = new UploadData();

		uploadData.uploadDateMethod();

	}
}
