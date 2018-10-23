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
 * 客户视图-客户业务概览
 * @author geyu
 * 2014-7-28
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custBusiInfoOverViewQuery", results = { @Result(name = "success", type = "json")})
public class CustBusiInfoOverviewQueryAction extends CommonAction {
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
   	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	    String custId=request.getParameter("custId");
   	    String tab=request.getParameter("tab");
   	    StringBuffer sb=new StringBuffer();
   	    if("loan".equals(tab)){
   	    	sb.setLength(0);
   	    	sb.append("select t.*,t1.prod_name from ACRM_F_CI_GK_LOAN t left join OCRM_F_PD_PROD_INFO t1 on t.loan_typ=t1.product_id where t.cust_id='"+custId+"'");
   	    }else if("save".equals(tab)){
   	    	sb.setLength(0);
   	    	sb.append("select t.*,t1.prod_name from ACRM_F_CI_GK_SAVE t left join  OCRM_F_PD_PROD_INFO t1 on t.deposit_type=t1.product_id where t.cust_id='"+custId+"'");
   	    }else if("middel".equals(tab)){
   	    	sb.setLength(0);
   	    	sb.append("select t.*,t1.prod_name  from ACRM_F_CI_GK_ZJYW t left join OCRM_F_PD_PROD_INFO t1 on t.type=t1.product_id where t.cust_id='"+custId+"'");
   	    }else if("channel".equals(tab)){
   	    	sb.setLength(0);
   	    	sb.append("select * from ACRM_F_CI_CHANNEL_ANALYSIS t where t.cust_id='"+custId+"'");
   	    }
   	    SQL=sb.toString();
		datasource = ds;
	}

}
