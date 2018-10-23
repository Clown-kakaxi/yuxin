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
import com.yuchengtech.bob.common.DataType;

/**
 * 客户视图-客户财务信息
 * @author geyu
 * 2014-7-26
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custFinInfoQuery", results = { @Result(name = "success", type = "json")})
public class CustFinInfoQueryAction extends CommonAction {
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
   	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	    String custId=request.getParameter("custId");
		StringBuffer sb=new StringBuffer("select * from (select distinct t1.cust_id,t1.etl_date, case when  t1.report_type='1' then '资产负债表' end as report_type  from  ACRM_F_CI_ORG_ASSET_DEBT t1 where t1.cust_id='"+custId+"' "+
				"union all "+
				"select  distinct t2.cust_id,t2.etl_date,case when t2.report_type='2' then '利润表' end as report_type   from  ACRM_F_CI_ORG_PROFIT t2 where t2.cust_id='"+custId+"' "+
				"union all "+
				"select  distinct t3.cust_id,t3.etl_date,case when t3.report_type='3' then '现金流量表' end as report_type from ACRM_F_CI_ORG_CRASH t3  where t3.cust_id='"+custId+"'");
		        sb.append(" ) b where 1=1 ");
		        
		for(String key:this.getJson().keySet()){
			 if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
				 if("REPORT_TYPE".equals(key)){
					 sb.append(" AND b.report_type like '%"+this.getJson().get(key)+"%'");
				 }else if( "ETL_DATE".equals(key)){//模糊查询 客户经理名称查询
					 sb.append(" AND b.etl_date = to_date('"+this.getJson().get(key)+"','yyyy-MM-dd')");
				 }
			 }
		 }
		
		SQL=sb.toString();
		datasource = ds;
		configCondition("RATING_SCORE", "=", "RATING_SCORE",DataType.String);
		configCondition("RATING_RESULT", "=", "RATING_RESULTss",DataType.String);
		configCondition("RATING_DATE", "=", "RATING_DATE",DataType.Date);
	}

}
