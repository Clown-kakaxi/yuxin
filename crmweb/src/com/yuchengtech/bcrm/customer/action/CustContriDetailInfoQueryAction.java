package com.yuchengtech.bcrm.customer.action;

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
 * 客户视图-客户贡献度
 * @author geyu
 * 2014-7-23
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custContriDetailInfoQuery", results = { @Result(name = "success", type = "json")})
public class CustContriDetailInfoQueryAction extends CommonAction {
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
   	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	    String custId=request.getParameter("custId");
   	    String etlDate=request.getParameter("etlDate");
   		StringBuffer sb=new StringBuffer("select  '存款贡献度' as CONTRI_TYPE, t.CONTRI_DEPOSIT AS CONTRI,t.etl_date,t.cust_Id from ACRM_F_CI_CUST_CONTRIBUTION  t where t.cust_id='"+custId+"' and t.etl_date=to_date('"+etlDate+"', 'yyyy-mm-dd') "+ 
   				"union all "+
   				"select '贷款贡献度' as CONTRI_TYPE,  t.CONTRIBUTION_LOAN AS CONTRI,t.etl_date,t.cust_Id from ACRM_F_CI_CUST_CONTRIBUTION  t where t.cust_id='"+custId+"' and t.etl_date=to_date('"+etlDate+"', 'yyyy-mm-dd') "+
   				"union all "+ 
   				"select '中间业务贡献度' as CONTRI_TYPE,  t.CONTRIBUTION_MID AS CONTRI,t.etl_date,t.cust_Id from ACRM_F_CI_CUST_CONTRIBUTION  t where t.cust_id='"+custId+"' and t.etl_date=to_date('"+etlDate+"', 'yyyy-mm-dd') ");
		SQL=sb.toString();
		datasource = ds;
	}
}
