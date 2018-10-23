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
import com.yuchengtech.bob.vo.AuthUser;

@Action("/ocrmFCiGroupPledgebusi")
public class OcrmFCiGroupPledgebusiAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String grpNo=request.getParameter("GRP_NO");
		StringBuffer sb=new StringBuffer("select p.* from OCRM_F_CI_GROUP_PLEDGEBUSI p " +
				"where 1 = 1 and p.GRP_NO = '"+grpNo+"'");

		addOracleLookup("CURRENCY", "XD000226");	
		
		SQL=sb.toString();
		datasource=ds;
	}
}
