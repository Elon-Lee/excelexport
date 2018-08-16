package com.test.tdq.uitl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NamedParamStatement {
    public NamedParamStatement(Connection conn, String sql) throws SQLException {
        int pos;
        while((pos = sql.indexOf(":")) != -1) {
            int end = sql.substring(pos).indexOf("\n");
            if (end == -1){
            	end = sql.substring(pos).indexOf("\r");
            	if(end == -1)
                	end = sql.substring(pos).indexOf(")");
            	if(end == -1)
                	end = sql.substring(pos).indexOf(" ");
            	else 
            		end = sql.length();
            }else{
            	  end += pos;
            }
            fields.add(sql.substring(pos+1,end).trim());
            sql = sql.substring(0, pos) + "?" + sql.substring(end);
        }       
        System.out.println("sql:\n"+sql);
        prepStmt = conn.prepareStatement(sql);
    }

    public PreparedStatement getPreparedStatement() {
        return prepStmt;
    }
    public ResultSet executeQuery() throws SQLException {
        return prepStmt.executeQuery();
    }
    public void close() throws SQLException {
        prepStmt.close();
    }

    public void setObject(String name, Object value) throws SQLException {        
    	/**
    	 * 多个name相同查找?位置的算法
    	 */
    	for(int i  = 0 ; i  < fields.size() ; i ++) {
    		if(fields.get(i).equals(name)){
    			System.out.println(String.format("binding argments index[%d] , value %s",i+1,value.toString()));
       		 	prepStmt.setObject(i+1, value);
    		}
    	}
    }

    private int getIndex(String name) {
        return fields.indexOf(name)+1;
    }
    private PreparedStatement prepStmt;
    private List<String> fields = new ArrayList<String>();
}