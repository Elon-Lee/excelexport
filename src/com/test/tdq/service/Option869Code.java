package com.test.tdq.service;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.tdq.uitl.ExcelReaderJXL;
import com.test.tdq.uitl.OracleJDBCUtile;
import com.test.tdq.uitl.PinYinUtil;

public class Option869Code {

	public void save869CodeMethod(File file) {
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		Connection con = jdbcUtile.getConnection();

		String updateSql = "update YB_FWXM set yka262 = ? where jlxh = ?";

		String sql = "select *from (select rownum rw, a.* from (select  t.jlxh as JLXH ,t.yka003 as YKA003 from   YB_FWXM t where t.isnuw = '1' "
				+ "and t.yka003 is not null and t.yka262 = '1234567'  ) a where rownum < ?) b where b.rw >= ?";
		try {
			PreparedStatement ps = con.prepareStatement(updateSql);
			con.setAutoCommit(false);
			for (int j = 1; j < 1000000; j++) {
				List<Object> pList = new ArrayList<Object>();
				pList.add(j * 1000);
				pList.add((j - 1) * 1000);
				List<Map<String, Object>> rsList = jdbcUtile.findModeResult(sql, pList);

				if (null == rsList || rsList.isEmpty()) {
					break;
				}
				for (int i = 0; i < rsList.size(); i++) {
					Map<String, Object> valMap = rsList.get(i);

					String pydm = PinYinUtil.getFirstSpell((String) valMap.get("YKA003"));
					String jlxh = String.valueOf(valMap.get("JLXH"));

					ps.setObject(1, pydm);
					ps.setObject(2, jlxh);

					ps.addBatch();
					if (i % 10000 == 0) {
						ps.executeBatch();
						con.commit();
						ps.clearBatch();
					}
					System.out.println("当前第" + (i + 1) + "列    记录序号 " + jlxh);
				}
				ps.executeBatch();
				con.commit();

			}
			ps.close();
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * @描述: 通过869码找到YP码的对应关系,反之没有869码,通过YP码对照
	 * @author tdq
	 * @日期:2016年11月14日上午9:59:32
	 * @参数:@param file
	 */
	public void find869CodeAndYPBMMethod(List<List<String>> sheetRestList) {
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		Connection con = jdbcUtile.getConnection();

		Map<String, String> valMap = new HashMap<String, String>();
		valMap.put("510132110", "651118");
		valMap.put("510132106", "650369");
		valMap.put("510132103", "651115");
		valMap.put("510132107", "651117");
		valMap.put("510132108", "650572");
		valMap.put("510132102", "650226");
		valMap.put("510132202", "651119");
		valMap.put("510132101", "650367");
		valMap.put("510132104", "651114");
		valMap.put("510132105", "651116");
		valMap.put("510132109", "650562");
		valMap.put("510132111", "650306");
		valMap.put("510132100", "650347");

		StringBuffer sb = new StringBuffer();
		sb.append(" select yb.sbxh    SBXH,mx.ypxh    as YPXH,yp.ypmc    as YPMC,yp.ypgg    as YPGG,yp.ypdw    as YPDW,yp.Pydm    as PYDM,");
		sb.append(" dz.cdmc    as CDMC,mx.ypcd    as CDXH,yb.ybbm    as YBBM,yb.CODE869 as  CODE869");
		sb.append(" from YK_YPCD mx left join YB_YPDZ yb on yb.ypxh = mx.ypxh and yb.cdxh = mx.ypcd inner join YK_TYPK yp ");
		sb.append(" on mx.ypxh = yp.ypxh inner join YK_CDDZ dz on mx.ypcd = dz.ypcd and yp.zfpb != 1");
		// sb.append(" where mx.ypxh in (select t.ypxh From yk_ypxx t where t.jgid like ?) order by mx.ypxh desc ");

		String sql = sb.toString();

		// sb.setLength(0);
		// String updateYBBM =
		// "update YB_YPDZ t set t.ybbm = ? where t.ypxh = ? and t.cdxh = ?";
		// String updateCode869 =
		// "update YB_YPDZ t set t.code869 = ? where t.ypxh = ? and t.cdxh = ?";

		try {

			// Set<String> keySet = valMap.keySet();
			// List<Object> valArrayList = new ArrayList<Object>();
			// for (String string : keySet) {
			// valArrayList.add(string + "%");
			// List<Map<String, Object>> rsList = jdbcUtile.findModeResult(sql,
			// valArrayList);
			List<Map<String, Object>> rsList = jdbcUtile.findModeResult(sql, null);
			// if (null == rsList || rsList.isEmpty()) {
			// break;
			// }
			// valArrayList.clear();
			// HIS 系统中的药品数据 包含 869 YBBM
			for (Map<String, Object> map : rsList) {
				// 先通过 869码,进行比较
				Object code869 = map.get("CODE869");
				String ybbm = map.get("YBBM") + "";
				if (null != code869 && !"".equals(code869)) {

					for (int i = 1; i < sheetRestList.size(); i++) {
						List<String> excelList = sheetRestList.get(i);
						String excl869 = excelList.get(1);
						String exclYbbm = excelList.get(0);
						if (null != excl869 && !"".equals(excl869)) {
							// 869码相同.再比较YBBM
							// if (excl869.equals(code869.toString())) {
							// if (!exclYbbm.equals(ybbm)) {
							// // System.out.println("869码相同");
							// // System.out.println(string + " " +
							// // valMap.get(string) + " " + excl869 + " "
							// // + ybbm + " " + " 药品编码不合适 " + " " +
							// // exclYbbm);
							// System.out.println(excl869 + " " + ybbm + " " +
							// " 药品编码不合适 " + " " + exclYbbm);
							// }
							// }
							if (exclYbbm.equals(ybbm)) {
								// 通过YBBM 修改869码
								if (!excl869.equals(code869)) {
									System.out.println(map.get("YPMC") + " " + map.get("YPXH") + " " + excl869 + " " + exclYbbm + " " + " 问题描述 " + " " + ybbm);
								}
							}

						}
					}
				}
				// }
			}

			// PreparedStatement ps = con.prepareStatement(updateSql);
			//
			// for (int j = 1; j < 1000000; j++) {
			// List<Object> pList = new ArrayList<Object>();
			// pList.add(j * 1000);
			// pList.add((j - 1) * 1000);
			//
			// con.setAutoCommit(false);
			//
			// for (int i = 0; i < rsList.size(); i++) {
			// Map<String, Object> valMap = rsList.get(i);
			//
			// String pydm = PinYinUtil.getFirstSpell((String)
			// valMap.get("YKA003"));
			// String jlxh = String.valueOf(valMap.get("JLXH"));
			//
			// ps.setObject(1, pydm);
			// ps.setObject(2, jlxh);
			//
			// ps.addBatch();
			// if (i % 10000 == 0) {
			// ps.executeBatch();
			// }
			// System.out.println("当前第" + (i + 1) + "列    记录序号 " + jlxh);
			// }
			// ps.executeBatch();
			// con.commit();
			// ps.clearBatch();
			//
			// }
			// ps.close();
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// File file = new File("D:/test2.xls");
		Option869Code a = new Option869Code();
		// a.save869CodeMethod(file);

		File fileTwo = new File("D:/2222.xls");

		List<List<String>> rs = ExcelReaderJXL.getExclFileDataByZs(fileTwo);
		a.find869CodeAndYPBMMethod(rs);

	}

}
