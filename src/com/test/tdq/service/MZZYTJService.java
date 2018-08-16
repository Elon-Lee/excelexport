package com.test.tdq.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.test.tdq.uitl.ExportExcel;
import com.test.tdq.uitl.OracleJDBCUtile;

/**
 * 每年门诊住院数据统计
 * 
 * @author Administrator
 * 
 */
public class MZZYTJService {

	static  Map<String,String> orgNames = new HashMap<String, String>()  ; 
	
	static void initData()
	{
		orgNames.put("510132107","方兴");
		orgNames.put("510132100","五津");
		orgNames.put("510132111","武阳");
		orgNames.put("510132202","文井");
		orgNames.put("510132104","普兴");
		orgNames.put("510132102","花源");
		orgNames.put("510132105","兴义");
		orgNames.put("510132109","永商");
		
	}
	/**
	 * 统计去年的门诊住院数据
	 * 
	 * @param jgid
	 * @throws URISyntaxException 
	 */
	public static void exportMZZYTJB(final String[] args) throws URISyntaxException {
		OracleJDBCUtile jdbcUtile = new OracleJDBCUtile();
		initData();
		if (args.length != 3 && !args[0].equals("-h")) {
			System.out.println("参数不正确!");
			System.exit(1);
		}
		if(args[0].equals("-h")|| args.length ==0 ){
			System.out.println("用法: java -jar *.sql(要打包的sql语句支持正则表达式) title(报表名字) args(key=value,key=value...) ");
			System.exit(0);
		}
		/**
		 * 判断过滤fileName
		 */
		System.out.println(new File(".").getAbsolutePath());
		File sqlFiles[] = new File(".").listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				if(pathname.getName().toString().matches(args[0])){
					return true ; 
				}
				return false;
			}
		}); 

		String fileNames[]  = null ;
		if(sqlFiles ==null || sqlFiles.length  ==0 ){
			System.out.println("没有匹配到sql文件");
			System.exit(0);
		}
		fileNames = new String[sqlFiles.length] ;  
		for(int i = 0 ; i <  sqlFiles.length ; i ++){
			fileNames[i] = sqlFiles[i].getName() ; 
		}
		List<Map<String, Object>> parmList = new ArrayList<Map<String, Object>>();
		try {
			jdbcUtile.getConnection(); 
			//看是否指定jgid导出
			String param = args[2];
			Object[] array  = null ; 
			//如果是机构id查询的语句,按机构id导出多张表
			if(param.indexOf("jgid")>-1 ){
				if(param.contains("*")){
					Set<String> set = new HashSet<String>();
					  for ( Iterator<String> s = orgNames.keySet().iterator() ;
							  s.hasNext() ; ) {
						  set.add("jgid="+ s.next()) ; 
					  }
					  array = set.toArray();
				}else{
					array = param.split(",");
				}
				for (Object s : array) {
					Map<String, Object> params = new HashMap<String, Object>();
					String keyString = s.toString().split("=")[0];
					String valueString = s.toString().split("=")[1];
					//添加非jgid参数
					for (Object s2 : param.split(",")) {
						if(! s2.toString().split("=")[0].equals("jgid")){
							params.put(s2.toString().split("=")[0], s2.toString().split("=")[1]);
						}
					}
					params.put(keyString, valueString);
					String keyName = orgNames.get(valueString); 
					//封装sheet支持单文件多表格
					for(String file :fileNames ){
						// 门诊sql
						String mzsql  = new String(Files.readAllBytes(
									new File(  file ).toPath())); 
						if (!new File(  file ).exists()) {
							System.out.println(String.format("sql文件[%s]不存在!", file));
							System.exit(1);
						}
						Map<String, Object> mzDataMap = jdbcUtile.getHeadersAndDataMethodTwo(mzsql, params);
						String[] mzDataHeaders = (String[]) mzDataMap.get("headers");
						List<Map<String, Object>> mzDataList = (List<Map<String, Object>>) mzDataMap.get("dataList");
						Map<String, Object> paraMap = new HashMap<String, Object>();
						paraMap.put("Headers", mzDataHeaders);
						paraMap.put("dataList", mzDataList);
						paraMap.put("sheeTitile", file);
						parmList.add(paraMap);
					}
					ExportExcel<Map<String, Object>> export = new ExportExcel<Map<String, Object>>();
					if(keyName!=null){
						export.exportExcel3(keyName+"-"+args[1], parmList);
					}else{
						export.exportExcel3(args[1], parmList);
					}
					params = null;
					parmList.clear();
				}
			}else{
				array = param.split(",");
				for (Object s : array) {
					Map<String, Object> params = new HashMap<String, Object>();
					String keyString = s.toString().split("=")[0];
					String valueString = s.toString().split("=")[1];
					params.put(keyString, valueString);
				}
				for (Object s : array) {
					Map<String, Object> params = new HashMap<String, Object>();
					String keyString = s.toString().split("=")[0];
					String valueString = s.toString().split("=")[1];
					params.put(keyString, valueString);
					String keyName = orgNames.get(valueString); 
					System.out.println(new File(".").toURI());
					 
					//封装sheet支持单文件多表格
					for(String file :fileNames ){
						// 门诊sql
						String mzsql  = new String(Files.readAllBytes(
									new File(  file ).toPath())); 
						if (!new File(  file ).exists()) {
							System.out.println(String.format("sql文件[%s]不存在!", file));
							System.exit(1);
						}
						
					
						Map<String, Object> mzDataMap = jdbcUtile.getHeadersAndDataMethodTwo(mzsql, params);
						String[] mzDataHeaders = (String[]) mzDataMap.get("headers");
						List<Map<String, Object>> mzDataList = (List<Map<String, Object>>) mzDataMap.get("dataList");
						Map<String, Object> paraMap = new HashMap<String, Object>();
						paraMap.put("Headers", mzDataHeaders);
						paraMap.put("dataList", mzDataList);
						paraMap.put("sheeTitile", file);
						parmList.add(paraMap);
					}
					ExportExcel<Map<String, Object>> export = new ExportExcel<Map<String, Object>>();
					if(keyName!=null){
						export.exportExcel3(keyName+"-"+args[1], parmList);
					}else{
						export.exportExcel3(args[1].substring(0,args[1].indexOf(".")), parmList);
					}
					params = null;
					parmList.clear();
				}
			}  
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  catch (Exception e) {
			e.printStackTrace();
			System.out.println("用法: java -jar *.sql(要打包的sql语句支持正则表达式) title(报表名字) args(key=value,key=value...) ");
		} finally {
			jdbcUtile.releaseConn();
		}
	}
 
	public static void main(String[] args) throws Exception {
//		MZZYTJService.exportMZZYTJB(Arrays.asList("2.sql","住院报表","jgid=*,rw=20").toArray(new String[0]));
		MZZYTJService.exportMZZYTJB(args);
	}
}
