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

@Action("/ocrmFCiGroupDiscountbusi")
public class OcrmFCiGroupDiscountbusiAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String grpNo=request.getParameter("GRP_NO");
		StringBuffer sb=new StringBuffer("select d.* from OCRM_F_CI_GROUP_DISCOUNTBUSI d " +
				"where 1 = 1 and d.GRP_NO = '"+grpNo+"'");
				
		SQL=sb.toString();
		datasource=ds;
	}
}
