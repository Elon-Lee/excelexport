package com.test.tdq.uitl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OracleJDBCUtile {
	// 数据库用户名
	private static final String USERNAME = "phis";
	// 数据库密码
	private static final String PASSWORD = "phis";
	// 数据库用户名
	private static final String USERNAME_ZS = "phis_zs";
	// 数据库密码
	private static final String PASSWORD_ZS = "phis_zs";
	// 驱动信息
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	// 数据库地址
	private static final String URL = "jdbc:oracle:thin:@10.102.226.40:1521/orcl";
	// 数据库地址
	private static final String URLCS = "jdbc:oracle:thin:@10.102.226.44:1521/orcl";
	private Connection connection;
	private PreparedStatement pstmt;
	private ResultSet resultSet;

	public OracleJDBCUtile() {
		// TODO Auto-generated constructor stub
		try {
			Class.forName(DRIVER);
		} catch (Exception e) {
		}
	}

	/**
	 * 获得数据库的连接
	 * 
	 * @return
	 */
	public Connection getConnection() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 获得诊所数据库的连接
	 * 
	 * @return
	 */
	public Connection getConnection_ZS() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME_ZS, PASSWORD_ZS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 获得诊所数据库的连接
	 * 
	 * @return
	 */
	public Connection getConnection_CS() {
		try {
			connection = DriverManager.getConnection(URLCS, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 增加、删除、改
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public boolean updateByPreparedStatement(String sql, List<Object> params) throws SQLException {
		boolean flag = false;
		int result = -1;
		pstmt = connection.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;
		return flag;
	}

	/**
	 * 查询单条记录
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();// 返回查询结果
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		while (resultSet.next()) {
			for (int i = 0; i < col_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
		}
		return map;
	}

	public Map<String, Object> getHeadersAndDataMethod(String sql, List<Object> params) throws SQLException {
		pstmt = connection.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		ResultSet rs3 = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs3.getMetaData();
		int columnCountNum = rsmd.getColumnCount();
		String[] headers = new String[columnCountNum];
		for (int i = 0; i < columnCountNum; i++) {
			String name = rsmd.getColumnName(i + 1);
			headers[i] = name;
		}
		// 对结果集进行封装
		List<Map<String, Object>> rsList = findModeResult(sql, params);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("headers", headers);
		returnMap.put("dataList", rsList);
		return returnMap;
	}

	/**
	 * 查询单条记录
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findSimpleResult(String sql, Map<String, Object> params) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		NamedParamStatement pstmt = new NamedParamStatement(connection, sql);
		for (Iterator<Entry<String, Object>> iter = params.entrySet().iterator(); iter.hasNext();) {
			Entry<String, Object> entry = iter.next();
			pstmt.setObject(entry.getKey(), entry.getValue());
		}
		resultSet = pstmt.executeQuery();// 返回查询结果
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		while (resultSet.next()) {
			for (int i = 0; i < col_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
		}
		return map;
	}

	public Map<String, Object> getHeadersAndDataMethodTwo(String sql, Map<String, Object> params) throws SQLException {

		NamedParamStatement pstmt = new NamedParamStatement(connection, sql);
		for (Iterator<Entry<String, Object>> iter = params.entrySet().iterator(); iter.hasNext();) {
			Entry<String, Object> entry = iter.next();
			pstmt.setObject(entry.getKey(), entry.getValue());
		}
		ResultSet rs3 = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs3.getMetaData();
		int columnCountNum = rsmd.getColumnCount();
		String[] headers = new String[columnCountNum];
		for (int i = 0; i < columnCountNum; i++) {
			String name = rsmd.getColumnName(i + 1);
			headers[i] = name;
		}
		// 对结果集进行封装
		List<Map<String, Object>> rsList = findModeResultTwo(sql, params);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("headers", headers);
		returnMap.put("dataList", rsList);
		return returnMap;
	}

	/**
	 * 查询多条记录
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findModeResultTwo(String sql, Map<String, Object> params) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		NamedParamStatement pstmt = new NamedParamStatement(connection, sql);
		for (Iterator<Entry<String, Object>> iter = params.entrySet().iterator(); iter.hasNext();) {
			Entry<String, Object> entry = iter.next();
			pstmt.setObject(entry.getKey(), entry.getValue());
		}
  
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}
		pstmt.close();
		return list;
	}

	public Map<String, Object> getHeadersAndDataMethod_28(String sql, List<Object> params) throws SQLException {
		new OracleJDBCUtile().getConnection_ZS();
		pstmt = connection.prepareStatement(sql);

		ResultSet rs3 = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs3.getMetaData();
		int columnCountNum = rsmd.getColumnCount();
		String[] headers = new String[columnCountNum];
		for (int i = 0; i < columnCountNum; i++) {
			String name = rsmd.getColumnName(i + 1);
			headers[i] = name;
		}
		// 对结果集进行封装
		List<Map<String, Object>> rsList = findModeResult(sql, params);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("headers", headers);
		returnMap.put("dataList", rsList);
		return returnMap;
	}

	/**
	 * 查询多条记录
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findModeResult(String sql, List<Object> params) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		// if(sql.indexOf(":JGID") > -1 ){
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		// }
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}
		pstmt.close();
		return list;
	}

	/**
	 * 通过反射机制查询单条记录
	 * 
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> T findSimpleRefResult(String sql, List<Object> params, Class<T> cls) throws Exception {
		T resultObject = null;
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			// 通过反射机制创建一个实例
			resultObject = cls.newInstance();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true); // 打开javabean的访问权限
				field.set(resultObject, cols_value);
			}
		}
		return resultObject;

	}

	/**
	 * 通过反射机制查询多条记录
	 * 
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> findMoreRefResult(String sql, List<Object> params, Class<T> cls) throws Exception {
		List<T> list = new ArrayList<T>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			// 通过反射机制创建一个实例
			T resultObject = cls.newInstance();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				Field field = cls.getDeclaredField(cols_name.toLowerCase());
				field.setAccessible(true); // 打开javabean的访问权限
				field.set(resultObject, cols_value);
			}
			list.add(resultObject);
		}
		return list;
	}

	/**
	 * 释放数据库连接
	 */
	public void releaseConn() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		OracleJDBCUtile jdbcUtils = new OracleJDBCUtile();
		jdbcUtils.getConnection();

		/******************* 增 *********************/
		/*
		 * String sql =
		 * "insert into userinfo (username, pswd) values (?, ?), (?, ?), (?, ?)"
		 * ; List<Object> params = new ArrayList<Object>(); params.add("小明");
		 * params.add("123xiaoming"); params.add("张三"); params.add("zhangsan");
		 * params.add("李四"); params.add("lisi000"); try { boolean flag =
		 * jdbcUtils.updateByPreparedStatement(sql, params);
		 * System.out.println(flag); } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		/******************* 删 *********************/
		// 删除名字为张三的记录
		/*
		 * String sql = "delete from userinfo where username = ?"; List<Object>
		 * params = new ArrayList<Object>(); params.add("小明"); boolean flag =
		 * jdbcUtils.updateByPreparedStatement(sql, params);
		 */

		/******************* 改 *********************/
		// 将名字为李四的密码改了
		/*
		 * String sql = "update userinfo set pswd = ? where username = ? ";
		 * List<Object> params = new ArrayList<Object>();
		 * params.add("lisi88888"); params.add("李四"); boolean flag =
		 * jdbcUtils.updateByPreparedStatement(sql, params);
		 * System.out.println(flag);
		 */

		/******************* 查 *********************/
		// 不利用反射查询多个记录
		/*
		 * String sql2 = "select * from userinfo "; List<Map<String, Object>>
		 * list = jdbcUtils.findModeResult(sql2, null);
		 * System.out.println(list);
		 */

		// 利用反射查询 单条记录
		// String sql = "select * from userinfo where username = ? ";
		// List<Object> params = new ArrayList<Object>();
		// params.add("李四");
		// UserInfo userInfo;
		// try {
		// userInfo = jdbcUtils.findSimpleRefResult(sql, params,
		// UserInfo.class);
		// System.out.print(userInfo);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

}