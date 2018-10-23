package com.yuchengtech.bcrm.customer.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

@Action("/ocrmFCiBuygroupInfo")
public class OcrmFCiBuygroupInfoAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		StringBuffer sb=new StringBuffer("select * from OCRM_F_CI_BUYGROUP_INFO where 1 = 1");

		addOracleLookup("CREDIT_CURRENCY", "XD000226");
				
		SQL=sb.toString();
		datasource=ds;

		configCondition("SERNO","like","SERNO",DataType.String);
		configCondition("GRP_NAME","like","GRP_NAME",DataType.String);
		configCondition("GRP_NO","=","GRP_NO",DataType.String);
	}
}
