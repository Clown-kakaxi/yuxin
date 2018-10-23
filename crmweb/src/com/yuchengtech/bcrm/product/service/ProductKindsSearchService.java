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
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.crm.constance.JdbcUtil;

/**
 * @author yaoliang
 *
 */
@Service
@Scope("prototype")
@Transactional(value="postgreTransactionManager")
public class ProductKindsSearchService {

	@Resource(name="dsOracle")
	private DataSource dataSource;

	@SuppressWarnings("unchecked")
	public Map<String,Object> productKindsList(StringBuffer sb) throws Exception{
		
		Map kindSearchMap = new HashMap();
		ArrayList kindsList = new ArrayList();
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while(rs.next()){
				Map map = new HashMap();
				for(int i=1;i<=columnCount;i++){
					String columnName = rsmd.getColumnName(i);
					map.put(columnName, rs.getObject(columnName));
				}
				kindsList.add(map);
			}
			kindSearchMap.put("data", kindsList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close(rs, stat, con);
		}
		return kindSearchMap;
	}

}
