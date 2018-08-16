package com.test.tdq.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.test.tdq.uitl.OracleJDBCUtile;

public class UpdateOldPeopleSelfCare {

	private static Logger log = Logger.getLogger(UpdateOldPeopleSelfCare.class);

	/**
	 * @描述: 修改
	 * @author tdq
	 * @throws SQLException
	 * @日期:2017年3月7日下午7:10:55
	 * @参数:
	 */
	public static void updateDataMethod(String year) throws SQLException {
		StringBuffer sbBuffer = new StringBuffer();

		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		Connection con = jdbcUtile.getConnection();

		// 修改自理评估数据 和年检表的关系
		String updateSql = "update MDC_OldPeopleSelfCare a set a.healthcheckid = ? where a.scid = ?";

		// 查询年检表数据
		sbBuffer.append(" select t.empiid as EMPIID, t.healthcheck as CHECKID From HC_HealthCheck t where to_char(t.checkdate, 'yyyy') = ? group by t.empiid, t.checkdate, t.healthcheck ");
		sbBuffer.append(" order by count(1) desc ");

		String sql = sbBuffer.toString();
		sbBuffer.setLength(0);
		// 查询自理评估数据
		sbBuffer.append("select scid as　SCID  from (select nvl(t.scid, 0) as SCID From ");
		sbBuffer.append("MDC_OldPeopleSelfCare t where  to_char(t.createdate, 'yyyy') = ? and t.empiid = ? ) a where rownum = 1");
		String sqlOne = sbBuffer.toString();
		sbBuffer = null;
		try {
			PreparedStatement ps = con.prepareStatement(updateSql);
			con.setAutoCommit(false);
			List<Object> pList = new ArrayList<Object>();
			pList.add(year);
			List<Map<String, Object>> rsList = jdbcUtile.findModeResult(sql, pList);

			int i = 1;
			for (Map<String, Object> map : rsList) {
				String empiid = (String) map.get("EMPIID");
				String checkId = (String) map.get("CHECKID");

				pList.clear();
				pList.add(year);
				pList.add(empiid);
				List<Map<String, Object>> zlpbList = jdbcUtile.findModeResult(sqlOne, pList);

				if (!zlpbList.isEmpty()) {
					Map<String, Object> zlpgMap = zlpbList.get(0);
					ps.setObject(1, checkId);
					Object scid = zlpgMap.get("SCID");
					ps.setObject(2, scid);

					log.info(year + " 年份 ,条数" + i + "修改对应的数据 EMPIID " + empiid + " 年检ID 是 : " + checkId + " 自理评估ID " + scid);

					ps.addBatch();
					if (i % 10000 == 0) {
						ps.executeBatch();
						con.commit();
						ps.clearBatch();
					}
				}
				i++;
			}

			ps.executeBatch();
			con.commit();

		} catch (SQLException e1) {
			e1.printStackTrace();
			con.rollback();

		} finally {
			con.close();
		}
	}

	public static void main(String[] args) {
		try {
			UpdateOldPeopleSelfCare.updateDataMethod("2015");
			UpdateOldPeopleSelfCare.updateDataMethod("2016");
			UpdateOldPeopleSelfCare.updateDataMethod("2017");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
