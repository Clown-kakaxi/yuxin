package com.yuchengtech.bob.action;


import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value="/queryremindinfoind", results={
    @Result(name="success", type="json")
})
public class QueryWorkplatRemindInfoAction extends CommonAction{
        
    @Autowired
    @Qualifier("dsOracle")
	private DataSource ds;
    
    public void prepare() {
        AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
        String userId = auth.getUserId(); 
        
        SQL = " select COUNT(1) AS COUNT,rule_code from OCRM_F_WP_REMIND where info_id not in " +
        		"(select remind_id from OCRM_F_WP_REMIND_READ where USER_ID='"+userId+"') " +
        		"and MSG_END_DATE >= sysdate and user_id='"+userId+"' GROUP BY rule_code"; 
        
		datasource = ds;
    }
}
