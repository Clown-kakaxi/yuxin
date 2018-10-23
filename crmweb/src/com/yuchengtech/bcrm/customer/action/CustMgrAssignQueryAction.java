package com.yuchengtech.bcrm.customer.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 
 *客户分配客户经理分配的查询
 * @author sxs
 * @since 2012-12-29
 * 
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custMgrAssignQuery-Action", results = { @Result(name = "success", type = "json")})
public class  CustMgrAssignQueryAction extends CommonAction {
	
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
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 		String custId =request.getParameter("custId");
		StringBuilder sb = new StringBuilder(
					"select * from OCRM_F_CI_BELONG_CUSTMGR where 1=1");
		sb.append(" and CUST_ID in('"+custId+"')");
		sb.append(" and INSTITUTION = '"+auth.getUnitId()+"'");
		sb.append(" and MAIN_TYPE =  '2'");
		setPrimaryKey("ID");
    //    setBranchFileldName(" INSTITUTION");//查询本机构及辖内机构 

		SQL=sb.toString();
		datasource = ds;
	}
}
