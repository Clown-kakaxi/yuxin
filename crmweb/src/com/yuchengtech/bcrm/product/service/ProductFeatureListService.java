/**
 * 
 */
package com.yuchengtech.bcrm.product.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.crm.constance.JdbcUtil;

/**
 * @author yaoliang
 *2011-08-30
 */
@Service
@Transactional(value="postgreTransactionManager")
public class ProductFeatureListService  {

	@Resource(name = "dsOracle")
	private DataSource dataSource;
	
	@SuppressWarnings("unchecked")
	public List featureSearchList(String sb){
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		List featureSearchList = new ArrayList();
		try{
			conn = dataSource.getConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery(sb);
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			while(rs.next()){
				Map map = new HashMap();
				for(int i =1 ;i<=count;i++){
					String columnName = rsmd.getColumnName(i);
					map.put(columnName, rs.getObject(columnName));
				}
				featureSearchList.add(map);
			}			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stat, conn);
		}	
		return featureSearchList;
	}
	
}
