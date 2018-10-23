/**
 * 
 */
package com.yuchengtech.bob.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.crm.constance.JdbcUtil;

/**
 * @author yaoliang
 */
@Service
@Transactional
public class SystemUnitRecursiveGPService {
	
	@Autowired
	private DataSource dsOracle;
	
	
	@SuppressWarnings("unchecked")
	public Map systemUnitList(String kindSql){
		Connection conn=null;
		Statement stat=null;
		ResultSet rs = null;
		Map kindMap = new HashMap();
		ArrayList kindList = new ArrayList();		
		try{
			 conn = dsOracle.getConnection();
			 stat = conn.createStatement();
			 rs = stat.executeQuery(kindSql);
			 ResultSetMetaData rsmd = rs.getMetaData();
			 int columnCount = rsmd.getColumnCount();
			 while(rs.next()){
				 Map map = new HashMap();
				 for(int i=1;i<=columnCount;i++){
					 map.put(rsmd.getColumnName(i), rs.getObject(rsmd.getColumnName(i)));
				 }
				 kindList.add(map);
			 }
		}catch(Exception ex){
			ex.printStackTrace();			
		}finally{	
			JdbcUtil.close(rs, stat, conn);
		}
		
		kindMap.put("data", kindList);
		return kindMap;
	}
	
}
