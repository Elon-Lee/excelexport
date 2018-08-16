package com.test.tdq.service;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.test.tdq.uitl.OracleJDBCUtile;
import com.test.tdq.uitl.PinYinUtil;
import com.test.tdq.uitl.ReadExcelUtilsPOI;

public class GJXBMDM {

	public static void saveGJXBMMethod(Map<Integer, Map<Integer, Object>> map) {
		PreparedStatement ps = null;
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		Connection con = jdbcUtile.getConnection();
		try {
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();

			sb.append("insert into gj_ypbmk ");
			sb.append("(jlxh, cpxh, yptym, xhjx, ypgg, ypbz, zxzjdw, zhb, jblb, scqy, pzwh, ygjbm, xgjbm, psqys, pym) ");
			sb.append("values");
			sb.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
			String updateSql = sb.toString();
			sb.setLength(0);
			
			sb.append("truncate table gj_ypbmk");
			PreparedStatement psOne = con.prepareStatement(sb.toString());
			psOne.executeUpdate(sb.toString());

			ps = con.prepareStatement(updateSql);
			
			for (int i = 1; i <= map.size(); i++) {
				Map<Integer, Object> valMap = map.get(i);

				ps.setObject(1, i);
				ps.setObject(2, valMap.get(0));
				ps.setObject(3, valMap.get(1));
				ps.setObject(4, valMap.get(3));
				ps.setObject(5, valMap.get(5));
				ps.setObject(6, valMap.get(9));
				ps.setObject(7, valMap.get(6));
				ps.setObject(8, valMap.get(7));
				ps.setObject(9, "无");
				ps.setObject(10, valMap.get(12));
				ps.setObject(11, valMap.get(11));
				ps.setObject(12, "无");
				ps.setObject(13, valMap.get(14));
				ps.setObject(14, "无");
				ps.setObject(15, PinYinUtil.getFirstSpell(String.valueOf(valMap.get(1))).toUpperCase());
				ps.executeUpdate();
			}
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if (null != con) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		try {
			// File file = new File("D:/gongAnData/新平8692.xls");
			// YB869DM.save869CodeMethod("510132100", file);
			String filepath = "D:/gjxbmk.xlsx";
			ReadExcelUtilsPOI excelReader = new ReadExcelUtilsPOI(filepath);
			Map<Integer, Map<Integer, Object>> map = excelReader.readExcelContent();
			GJXBMDM.saveGJXBMMethod(map);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
