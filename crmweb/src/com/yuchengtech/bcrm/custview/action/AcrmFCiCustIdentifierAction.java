package com.yuchengtech.bcrm.custview.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;

@Action("/acrmFCiCustIdentifier")
public class AcrmFCiCustIdentifierAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	

	/**
	 *信息查询SQL
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String identId=request.getParameter("identId");
		StringBuilder sb = new StringBuilder(" select * from ACRM_F_CI_CUST_IDENTIFIER where IDENT_ID= '"+identId+"'");
		SQL=sb.toString();
		datasource = ds;
	}
}
