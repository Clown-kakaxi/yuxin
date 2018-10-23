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
 * 客户视图-客户财务信息
 * @author geyu
 * 2014-7-27
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custFinInfoDetailQuery", results = { @Result(name = "success", type = "json")})
public class CustFinInfoDetailQueryAction  extends CommonAction{
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
		String reportType=request.getParameter("reportType");
		StringBuffer sb= new StringBuffer();
		if("资产负债表".equals(reportType)){
			sb.append("select r1.item_name,r1.amt,r1.curr,r2.amt as amt_pre,r2.curr_pre from "+  
					"(select t1.item_name,t.amt,t.item_id,t.curr  from  ACRM_F_CI_ORG_ASSET_DEBT t "+ 
							"left join ACRM_F_CI_ORG_FIN_ITEM  t1 on t1.item_id=t.item_id "+
							"where t.cust_id='"+custId+"' and t.etl_date=date '"+etlDate+"')r1 "+
							"left join "+ 
							"(select t1.item_name,t.amt,t.item_id,t.curr as curr_pre from  ACRM_F_CI_ORG_ASSET_DEBT t "+ 
									"left join ACRM_F_CI_ORG_FIN_ITEM  t1 on t1.item_id=t.item_id "+
									"where t.cust_id='"+custId+"' and t.etl_date=ADD_MONTHS(DATE '"+etlDate+"', -12))r2 "+
									"on r1.item_id=r2.item_id");
		}else if("现金流量表".equals(reportType)){
			sb.setLength(0);
			sb.append("select r1.item_name,r1.amt,r1.curr,r2.amt as amt_pre,r2.curr_pre from "+  
					"(select t1.item_name,t.amt,t.item_id,t.curr  from  ACRM_F_CI_ORG_CRASH t "+ 
							"left join ACRM_F_CI_ORG_FIN_ITEM  t1 on t1.item_id=t.item_id "+
							"where t.cust_id='"+custId+"' and t.etl_date=date '"+etlDate+"')r1 "+
							"left join "+ 
							"(select t1.item_name,t.amt,t.item_id ,t.curr  as curr_pre from  ACRM_F_CI_ORG_CRASH t "+ 
									"left join ACRM_F_CI_ORG_FIN_ITEM  t1 on t1.item_id=t.item_id "+
									"where t.cust_id='"+custId+"' and t.etl_date=ADD_MONTHS(DATE '"+etlDate+"', -12))r2 "+
									"on r1.item_id=r2.item_id");
		}else if("利润表".equals(reportType)){
			sb.setLength(0);
			sb.append("select r1.item_name,r1.amt,r1.curr,r2.amt as amt_pre,r2.curr_pre from "+  
					"(select t1.item_name,t.amt,t.item_id,t.curr  from  ACRM_F_CI_ORG_PROFIT t "+ 
							"left join ACRM_F_CI_ORG_FIN_ITEM  t1 on t1.item_id=t.item_id "+
							"where t.cust_id='"+custId+"' and t.etl_date=date '"+etlDate+"')r1 "+
							"left join "+ 
							"(select t1.item_name,t.amt,t.item_id,t.curr as curr_pre  from  ACRM_F_CI_ORG_PROFIT t "+ 
									"left join ACRM_F_CI_ORG_FIN_ITEM  t1 on t1.item_id=t.item_id "+
									"where t.cust_id='"+custId+"' and t.etl_date=ADD_MONTHS(DATE '"+etlDate+"', -12))r2 "+
									"on r1.item_id=r2.item_id");
		}
		addOracleLookup("CURR","XD000226");
		addOracleLookup("CURR_PRE","XD000226");
		SQL=sb.toString();
		datasource = ds;
	}

}
