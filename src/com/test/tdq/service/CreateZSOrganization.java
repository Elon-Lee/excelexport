package com.test.tdq.service;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.test.tdq.uitl.CommUtil;
import com.test.tdq.uitl.ExcelReaderJXL;
import com.test.tdq.uitl.OracleJDBCUtile;
import com.test.tdq.uitl.PinYinUtil;

public class CreateZSOrganization {
	public void createOrganizationMethod(List<List<String>> restList) {
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		Connection con = jdbcUtile.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO sys_organization (organizName,registerNumber,organizCode,organizSecondName,classifyCode, ");
		sb.append("organiztype,admindivision,address,zipCode,foundDate,telphone,subCode,parentid,legal,grade,institLevel,");
		sb.append("logoff,JGID,pycode)VALUES(?,?,?,?,2,?,'510132',?,?,?,?,?,'002',?,9,?,0,?,?)");
		// =510132205
		try {
			PreparedStatement ps = con.prepareStatement(sb.toString());

			con.setAutoCommit(false);

			for (int i = 0; i < restList.size(); i++) {
				List<String> valList = restList.get(i);

				if (valList.size() == 0) {
					break;
				}

				// 名称
				String mc = valList.get(1);
				String djh = valList.get(2);
				String wybs = valList.get(3);
				String dz = valList.get(4);
				String dh = valList.get(5);
				String yzbm = valList.get(6);
				String frdb = valList.get(8);
				String lsgx = valList.get(18); // 隶属关系
				if ("街道办事处属".equals(lsgx)) {
					lsgx = "6";
				} else if ("乡(镇)属".equals(lsgx)) {
					lsgx = "7";

				} else if ("村属".equals(lsgx)) {
					lsgx = "9";

				} else if ("县(旗)属".equals(lsgx)) {
					lsgx = "5";

				} else {
					lsgx = "10";
				}
				String zyfzr = valList.get(9); // 主要负责人
				String jglb = valList.get(20); // 机构类别
				if ("中医诊所".equals(jglb)) {
					jglb = "D212";

				} else if ("口腔诊所".equals(jglb)) {
					jglb = "D213";
				} else if ("综合门诊部".equals(jglb)) {
					jglb = "D214";

				} else if ("村卫生室".equals(jglb)) {
					jglb = "D6";
				} else if ("社区卫生服务站".equals(jglb)) {
					jglb = "D3";
				} else {
					jglb = "D211";
				}

				String jyxz = valList.get(23); // 经营性质

				String jgdc = valList.get(22); // 机构等次

				if (jgdc.equals("合格")) {
					jgdc = "4";
				} else {
					jgdc = "9";
				}
				String kyrq = valList.get(27); // 开业日期

				String pydm = PinYinUtil.getFirstSpell(mc).toLowerCase();
				int jgid = 510132205 + i;
				System.out.println("<unit id=\"" + jgid + "\" name=\"" + mc + "\" pyCode=\"" + pydm + "\" type=\"C\" ref=\"" + wybs + "\"" + "/>");

				ps.setObject(1, mc);
				ps.setObject(2, djh);
				ps.setObject(3, wybs);
				ps.setObject(4, mc);
				ps.setObject(5, jglb);
				ps.setObject(6, dz);
				ps.setObject(7, yzbm);
				ps.setObject(8, CommUtil.strToDate(kyrq));
				ps.setObject(9, dh);
				ps.setObject(10, lsgx);
				ps.setObject(11, frdb);
				ps.setObject(12, jgdc);

				ps.setObject(13, jgid);
				ps.setObject(14, pydm);
				// ps.addBatch();
				// if (i % 10000 == 0) {
				// ps.executeBatch();
				// }
				// ps.execute();
			}
			// ps.executeBatch();
			// con.commit();
			// ps.clearBatch();

			ps.close();
			con.close();
		} catch (SQLException e1) {
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			e1.printStackTrace();
		}

	}

	public void createOfficeMethod(List<List<String>> restList) {
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		Connection con = jdbcUtile.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO sys_office t (ID,OFFICECODE,OFFICENAME,ORGANIZCODE,ORGANIZTYPE,PYCODE,OUTPATIENTCLINIC,MEDICALLAB,HOSPITALDEPT, ");
		sb.append("HOSPITALAREA,LOGOFF,YBDZ,YBDZMC,PARENTID)VALUES(?,?,?,?,'AA02',?,'1','0','0','0','0','0312000','全科医疗科',?) ");
		try {
			PreparedStatement ps = con.prepareStatement(sb.toString());
			con.setAutoCommit(false);
			for (int i = 0; i < restList.size(); i++) {
				List<String> valList = restList.get(i);
				if (valList.size() == 0) {
					break;
				}
				// 名称
				String mc = valList.get(1);
				String pydm = PinYinUtil.getFirstSpell(mc).toLowerCase();
				String wybs = valList.get(3);
				int numInt = i + 1;
				ps.setObject(1, numInt);
				ps.setObject(2, "A" + i);
				ps.setObject(3, mc);
				ps.setObject(4, wybs);
				ps.setObject(5, pydm);
				ps.setObject(6, wybs);

				// ps.addBatch();
				// if (i % 10000 == 0) {
				// ps.executeBatch();
				// }
				ps.execute();
			}
			// ps.executeBatch();
			con.commit();
			// ps.clearBatch();

			ps.close();
			con.close();
		} catch (SQLException e1) {
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			e1.printStackTrace();
		}

	}

	public void createGHKSMethod() {
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		Connection con = jdbcUtile.getConnection_ZS();

		StringBuffer sb = new StringBuffer();
		sb.append("select t.id as KSID, a.jgid as JGID From SYS_Office t left join sys_organization a on t.organizcode = a.organizcode ");
		sb.append("where t.ybdzmc is not null  and t.ybdz = '0312000'  order by t.id ");
		String ksidsAndJgidsStr = sb.toString();
		sb.setLength(0);
		sb.append("INSERT INTO MS_GHKS t (KSDM,JGID,KSMC,GHLB,PYDM,GHF,ZLF ,ZJMZ,GHXE,YGRS,YYRS,MZKS,TJF,MZLB,JZXH,JJRGHF,YBZLF");
		sb.append(")VALUES(?,?,'全科门诊',1,'MZQK',3.00,2.00,0,0,0,0,?,0.00,?,0,0.00,0.00) ");
		String addGHKS = sb.toString();
		sb.setLength(0);
		sb.append("INSERT INTO MS_MZLB t (MZLB,JGID,MZMC ");
		sb.append(")VALUES(?,?,'门诊') ");

		String addMZLB = sb.toString();

		System.out.println(sb.toString());
		try {

			ResultSet rs = con.createStatement().executeQuery(ksidsAndJgidsStr);

			PreparedStatement ps = con.prepareStatement(addGHKS);
			PreparedStatement ps2 = con.prepareStatement(addMZLB);
			con.setAutoCommit(false);

			int i = 1;
			while (rs.next()) {
				int mzlb = i + 1;
				// 名称
				String jgid = rs.getString("JGID");
				String ksid = rs.getString("KSID");
				System.out.println(" KSID " + ksid + " JGID " + jgid);
				ps.setObject(1, i);
				ps.setObject(2, jgid);
				ps.setObject(3, ksid);
				ps.setObject(4, mzlb);

				ps2.setObject(1, mzlb);
				ps2.setObject(2, jgid);

				ps.execute();
				ps2.execute();
				i++;
			}
			con.commit();
			ps.close();
			con.close();
		} catch (SQLException e1) {
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			e1.printStackTrace();
		}

	}

	public void createKSPBMethod() {
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		Connection con = jdbcUtile.getConnection_ZS();

		StringBuffer sb = new StringBuffer();
		sb.append("select t.ksdm as KSID , t.jgid as JGID　from MS_GHKS t ");
		String ksidsAndJgidsStr = sb.toString();
		sb.setLength(0);
		sb.append("INSERT INTO MS_KSPB t (GHRQ,ZBLB,GHKS,JGID,JZXH,GHXE,YGRS,YYRS,YYXE,TGBZ");
		sb.append(")VALUES(?,?, ?,?,0,0,0,0,0,0) ");
		String addKSPB = sb.toString();

		System.out.println(sb.toString());
		try {
			ResultSet rs = con.createStatement().executeQuery(ksidsAndJgidsStr);
			PreparedStatement ps = con.prepareStatement(addKSPB);
			con.setAutoCommit(false);

			while (rs.next()) {
				// 名称
				String jgid = rs.getString("JGID");
				String ksid = rs.getString("KSID");
				for (int j = 1; j < 8; j++) {
					for (int j2 = 1; j2 < 3; j2++) {
						ps.setObject(1, j);
						ps.setObject(2, j2);
						ps.setObject(3, ksid);
						ps.setObject(4, jgid);
						ps.execute();
					}
				}
			}
			con.commit();
			ps.close();
			con.close();
		} catch (SQLException e1) {
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			e1.printStackTrace();
		}

	}

	public static void main(String[] args) {
		 File file = new File("D:/111.xls");
		CreateZSOrganization a = new CreateZSOrganization();
		 List<List<String>> rs = ExcelReaderJXL.getExclFileDataByZs(file);
		 a.createOrganizationMethod(rs);
		// a.createOfficeMethod(rs);
		// a.createGHKSMethod();
//		a.createKSPBMethod();
	}

}
