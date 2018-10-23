package com.yuchengtech.bcrm.customer.customerMktTeam.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.customerMktTeam.model.AcrmFCmContriPara;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;

/**
 * @description:客户经理贡献度业绩参数设置
 * @author xiebz
 * @data 2014-07-04
 */
@Service
public class CustMgParameterNewService extends CommonService{
	public CustMgParameterNewService(){
        JPABaseDAO<AcrmFCmContriPara, Long> baseDao = new JPABaseDAO<AcrmFCmContriPara, Long>(AcrmFCmContriPara.class);
        super.setBaseDAO(baseDao);
    }

    /**
     * 修改，保存数据处理
     */
    public Object save(Object obj) {
    	Connection conn = null ;
    	Statement stmt = null ;
    	ResultSet rs = null;
		AcrmFCmContriPara marketTeam = (AcrmFCmContriPara)obj;
		String ids = marketTeam.getUserId();
		String[] idStr = ids.split(",");
		try {
			conn=JdbcUtil.getConnection();
			stmt = conn.createStatement();
			for(String id:idStr){
				String  sql = "select * from admin_auth_account where account_name = '"+id+"'";
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					marketTeam.setUserId(id);
					marketTeam.setUserName(rs.getString("USER_NAME"));
					super.save(marketTeam);
				}
			}
		}catch (SQLException e) {
			throw new BizException(1,2,"1002",e.getMessage());
        }finally{
        	JdbcUtil.close(rs, stmt, conn);
        }
		return marketTeam;
    }
    
    /**
     * 删除 客户经理业绩参数设置
     */
    public void batchRemove(String ids) {
        Map<String, Object> values = new HashMap<String, Object>();
        super.batchUpdateByName("DELETE FROM AcrmFCmContriPara t WHERE t.userId IN("+ ids +")", values);
    }
}
