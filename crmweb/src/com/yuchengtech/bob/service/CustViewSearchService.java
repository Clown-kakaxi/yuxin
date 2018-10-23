package com.yuchengtech.bob.service;

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
public class CustViewSearchService {
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource dsOracle;
	
	/**
	 * 查询角色查看视图权限
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> searchCustViewTree(String useId){
		
        //String seq = orgSeq(orgId);
		//String s = "SELECT UNIT.* FROM SYS_UNITS UNIT WHERE UNITSEQ LIKE '"+seq+"%' order by UNIT.levelunit,UNIT.id";
		String s=" select count(t1.id)as num_id  from OCRM_SYS_VIEW_USER_RELATION t1  where t1.role_id in("+useId+")";
		QueryHelper qh;
		try {
			qh = new QueryHelper(s, dsOracle.getConnection());
			return qh.getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
