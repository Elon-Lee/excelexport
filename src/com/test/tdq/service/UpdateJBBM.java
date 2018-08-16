package com.test.tdq.service;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.test.tdq.uitl.ExcelReaderJXL;
import com.test.tdq.uitl.OracleJDBCUtile;
import com.test.tdq.uitl.PinYinUtil;

/**
 * 修改最新疾病编码库的拼音代码
 * 
 * @文件名: com.test.tdq.service.UpdateJBBM.java
 * @author: 唐登强
 * @日期: 2017年8月23日下午5:13:51
 */
public class UpdateJBBM {

	public void queryJbbmUpdatePydm() {
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		Connection con = jdbcUtile.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append(" select ID, NICD10, CREATEDATE, NJBMC,t.NPYDM from testtzbs t ");
		String sql = sb.toString();

		sb.setLength(0);
		sb.append("update testtzbs t set t.npydm = ? where t.id = ?");
		String updateSql = sb.toString();

		try {

			List<Map<String, Object>> rsList = jdbcUtile.findModeResult(sql, null);
			PreparedStatement ps = con.prepareStatement(updateSql);
			con.setAutoCommit(false);
			int i = 0;
			for (Map<String, Object> map : rsList) {
				// 先通过 869码,进行比较

				Object code869 = map.get("NJBMC");
				if (null != code869 && !"".equals(code869)) {
					String pydm = PinYinUtil.getFirstSpell((String) code869).toUpperCase();
					String jlxh = String.valueOf(map.get("ID"));
					ps.setObject(1, pydm);
					ps.setObject(2, jlxh);
					ps.addBatch();
					if (i % 10000 == 0) {
						ps.executeBatch();
					}
					System.out.println("当前第" + (i + 1) + "列    记录序号 " + jlxh);
				}
				i++;
			}
			ps.executeBatch();
			con.commit();
			ps.clearBatch();
			ps.close();
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	public void queryJbbm() {
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		Connection con = jdbcUtile.getConnection();
		StringBuffer sb = new StringBuffer();
/*		sb.append(" select ID, NICD10, CREATEDATE, NJBMC,t.NPYDM from testtzbs t where  t.nicd10 like '%?%' ");*/
		sb.append(" select t.icd10,t.jbmc,t.pydm From gy_jbbm_new  t where t.jbxh = 21072 ");
		String sql = sb.toString();
		try {
			List<Map<String, Object>> rsList = jdbcUtile.findModeResult(sql, null);
			for (Map<String, Object> map : rsList) {
				String nicd10 = map.get("ICD10") + "";
				System.out.println(nicd10.indexOf("?"));
			}
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}  
	}

	public void queryJbbm(List<List<String>> ss) {

		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		Connection con = jdbcUtile.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into gy_jbbm_new (jbxh, dmlb,jbmc,icd10,pydm,version) values ");
		sb.append("((select max(a.jbxh) + 1 from gy_jbbm_new  a ),10,?,?,?,'2016')");
		String updateSql = sb.toString();
		try {
			PreparedStatement ps = con.prepareStatement(updateSql);
			con.setAutoCommit(false);
			int jlxh = 0;
			for (int i = 0; i < ss.size(); i++) {
				List<String> list = ss.get(i);
				// 先通过 869码,进行比较

				String icd10 = list.get(0);
				if (null != icd10 && !"".equals(icd10)) {
					String pydm = PinYinUtil.getFirstSpell(list.get(2)).toUpperCase();
					jlxh = i + 1;
//					ps.setObject(1, jlxh);

					ps.setObject(1, list.get(2));

					int indexNum = icd10.indexOf("†");
					if(indexNum != -1){
						String newIcd10 = icd10.substring(0 , indexNum);
						icd10 = newIcd10 + "+";
					} 
					ps.setObject(2, icd10);
					ps.setObject(3, pydm);
					ps.addBatch();
//					if (i % 10000 == 0) {
//						ps.executeBatch();
//					}
					System.out.println("当前第" + (i + 1) + "列    记录序号 " + jlxh + " ICD10 " + icd10);
				}
			}
			ps.executeBatch();
			con.commit();
			ps.clearBatch();
			ps.close();
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}
	public void queryJbbm_ZY(List<List<String>> ss) {
		
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		Connection con = jdbcUtile.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into gy_jbbm (jbxh, dmlb,jbmc,icd10,pydm,version) values ");
		sb.append("(?,10,?,?,?,'2016')");
		String updateSql = sb.toString();
		try {
			PreparedStatement ps = con.prepareStatement(updateSql);
			con.setAutoCommit(false);
			int jlxh = 0;
			int jbxh  = 42978;
			for (int i = 0; i < ss.size(); i++) {
				List<String> list = ss.get(i);
				// 先通过 869码,进行比较
				
				String icd10 = list.get(2);
				if (null != icd10 && !"".equals(icd10)) {
					String pydm = PinYinUtil.getFirstSpell(list.get(3)).toUpperCase();
					jlxh = i + 1;
					ps.setObject(1, jbxh+ 1);
					
					ps.setObject(2, list.get(3));
					
//					int indexNum = icd10.indexOf("†");
//					if(indexNum != -1){
//						String newIcd10 = icd10.substring(0 , indexNum);
//						icd10 = newIcd10 + "+";
//					} 
					ps.setObject(3, icd10);
					ps.setObject(4, pydm);
					ps.addBatch();
//					if (i % 10000 == 0) {
						ps.executeBatch();
//					}
					System.out.println("当前第" + (i + 1) + "列    记录序号 " + jlxh + " ICD10 " + icd10);
				}
			}
//			ps.executeBatch();
			con.commit();
			ps.clearBatch();
			ps.close();
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		// new UpdateJBBM().queryJbbmUpdatePydm();
//		File file = new File("D:/2016jbbmk.xls");
//		List<List<String>> ss = ExcelReader.getExclFileDataByZs(file);
//		 new UpdateJBBM().queryJbbm();
		File file = new File("D:/jbbm_zy.xls"); 
		List<List<String>> ss = ExcelReaderJXL.getExclFileDataByZs(file);
		new UpdateJBBM().queryJbbm_ZY(ss);
	}

}
