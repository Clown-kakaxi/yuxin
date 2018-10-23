package com.yuchengtech.bcrm.sales.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;

/**
 * 商机客户放大镜-联系人查询
 * @author geyu
 * 2014-8-7
 */
@ParentPackage("json-default")
@Action(value = "/mktOpporLinkManQuery", results = { @Result(name = "success", type = "json")})
public class MktOpporLinkManQueryAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
   	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId=request.getParameter("custId");
		String custType=request.getParameter("custType");
		StringBuffer sb=new StringBuffer();
		if("2".equals(custType)){
			sb.setLength(0);
			sb.append("SELECT T.LINKMAN_NAME,T.MOBILE FROM ACRM_F_CI_PER_LINKMAN T WHERE T.CUST_ID='"+custId+"'");
		}else if("1".equals(custType)){
			sb.setLength(0);
			sb.append("SELECT T.LINKMAN_NAME,T. MOBILE FROM ACRM_F_CI_ORG_EXECUTIVEINFO T WHERE T.ORG_CUST_ID='"+custId+"'");
		}
		SQL = sb.toString();
		datasource = ds;
		
	}
	

}
