package com.yuchengtech.bcrm.customer.level.service;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.core.QueryHelper;


@Service
@Transactional
public class IndexSearchService {
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource dsOracle;
	
	
		public Map<String,Object> searchIndexTypeTree()
		{
			
			String s = "select f_code,f_value,'1000' as root from OCRM_SYS_LOOKUP_ITEM where f_lookup_id='RULE_USE'";
			QueryHelper qh;
			try 
			{
				qh = new QueryHelper(s, dsOracle.getConnection());
				return qh.getJSON();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
				return null;
			}
			
		}
	
	 
}
