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
@Action(value="/queryrollcustomer", results={
    @Result(name="success", type="json")
})
public class QueryRollCustomerAction extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Override
	public void prepare() {
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currenUserId = auth.getUserId();
	    StringBuffer s = new StringBuffer("select t1.* from OCRM_F_MM_ROLL_CUST t1 where create_user='"+currenUserId+"'");
		setPrimaryKey("t1.roll_id");
		SQL=s.toString();
		datasource = ds;
	}
}
