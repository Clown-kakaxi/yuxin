package com.yuchengtech.bcrm.product.action;

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
 * 客户视图-客户适合产品信息
 * @author geyu
 * 2014-7-22
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custFitProdQuery", results = { @Result(name = "success", type = "json")})
public class CustFitProdQueryAction extends CommonAction  {
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
   	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	    String custId=request.getParameter("custId");
   	    StringBuffer sb=new StringBuffer("select t.prod_id,t.prod_name ,c.cust_id,c.cust_name,c.ident_type,c.ident_no,c.cust_type,c.cust_level,c.job_type,c.indust_type,c.linkman_name,"+
   	    								" c.linkman_tel,c.cust_stat,m.institution_name as org_name,m.mgr_name,m.mgr_id,m.INSTITUTION as org_id "+
   	    								" from OCRM_F_CI_CUST_FIT_PROD t "+      
   	    								" left join  ACRM_F_CI_CUSTOMER c on t.cust_id=c.cust_id "+
   	    								" left join OCRM_F_CI_BELONG_CUSTMGR m on c.cust_id = m.cust_id and (m.MAIN_TYPE = '1' or m.MAIN_TYPE is null) "+
   	    								" where t.cust_id='"+custId+"'");
   	    SQL=sb.toString();
		datasource = ds;
		configCondition("PROD_ID", "like", "PROD_ID",DataType.String);
		configCondition("PROD_NAME", "like", "PROD_NAME",DataType.String);
	}

}
