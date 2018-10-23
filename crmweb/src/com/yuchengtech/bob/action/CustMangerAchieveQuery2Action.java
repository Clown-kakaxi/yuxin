package com.yuchengtech.bob.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;

@Action("/custmanagerachievequery2")
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "custmanagerachievequery2" }) })
public class CustMangerAchieveQuery2Action extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		 ActionContext ctx = ActionContext.getContext();
		 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		 //String code_id = request.getParameter("nodeid");

		StringBuilder sb = new StringBuilder("select t.CUST_MANAGER_NAME,c.* from OCRM_F_CM_CUST_MGR_INFO t, ACRM_F_CM_BUSI_TOTAL c where t.cust_manager_id = c.USER_ID");
		
		SQL = sb.toString();
		setPrimaryKey("c.USER_ID desc");
		//addOracleLookup("APP_STATUS", "APP_STATUS");
		datasource = ds;

	}
}
