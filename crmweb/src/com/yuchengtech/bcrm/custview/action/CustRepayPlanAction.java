package com.yuchengtech.bcrm.custview.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;

/**
 * @description 分期还款计划
 * @author likai
 * @since 2014/08/13
 *
 */

@Action("/custRepayPlan")
public class CustRepayPlanAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	/**
	 *信息查询SQL
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId=request.getParameter("CUST_ID");
		StringBuilder sb = new StringBuilder(" select * from OCRM_F_CI_REPAY_PLAN where CUST_ID= '"+custId+"'");
		SQL=sb.toString();
		datasource = ds;
		setPrimaryKey("PERIOD desc");
		configCondition("PERIOD", "=", "PERIOD", DataType.Number);
	}
}
