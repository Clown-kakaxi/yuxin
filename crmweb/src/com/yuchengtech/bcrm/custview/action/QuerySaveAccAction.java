package com.yuchengtech.bcrm.custview.action;



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
 * 
 * 存款帐号store查询
 * @author songxs
 * @since 2012-12-20
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/querySaveAcc-Action", results = { @Result(name = "success", type = "json")})
public class QuerySaveAccAction extends CommonAction {
	
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
   
	/**
	 *模块功能查询
	 */
	public void prepare() {
		
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custid =request.getParameter("custid");
		StringBuilder sb = new StringBuilder("select t.account as Value,t.account as Name from ACRM_F_CI_DEPOSIT_ACT t where 1=1 ");
		if(custid != null && ! "".equals(custid) && !"false".equals(custid))
			sb.append("and t.cust_id = '"+custid+"'");	
				
  
		SQL=sb.toString();
		datasource = ds;
	}
}
