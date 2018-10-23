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
 * 客户视图-产品贡献度
 * @author geyu
 * 2014-7-24
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custProdContriDetailInfoQuery", results = { @Result(name = "success", type = "json")})
public class CustProdContriDetailInfoQueryAction extends CommonAction{
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
   	    String contriType=request.getParameter("contriType");
   	    StringBuffer sb=new StringBuffer("");
   	    if("存款贡献度".equals(contriType)){
   	    	sb.setLength(0);
   	    	sb.append("select t2.*,t3.prod_name from ACRM_F_CI_PROD_CONT t2 left join OCRM_F_PD_PROD_INFO t3 on t2.prod_id=t3.product_id  "+
   	    			" where t2.prod_id in (select t1.product_id from OCRM_F_PD_PROD_INFO t1 "+
                       " where t1.catl_code in "+
                             " (SELECT t.CATL_CODE FROM OCRM_F_PD_PROD_CATL t  where t.catl_parent = '110')) "+
   	    						" and t2.cust_id = '"+custId+"'  and t2.etl_date = to_date('"+etlDate+"', 'yyyy-MM-dd')");
   	    														
   	    }else if("贷款贡献度".equals(contriType)){
   	    	sb.setLength(0);
   	    	sb.append("select t2.*,t3.prod_name from ACRM_F_CI_PROD_CONT t2 left join OCRM_F_PD_PROD_INFO t3 on t2.prod_id=t3.product_id   "+
   	    			" where t2.prod_id in (select t1.product_id from OCRM_F_PD_PROD_INFO t1 "+
                       " where t1.catl_code in "+
                             " (SELECT t.CATL_CODE FROM OCRM_F_PD_PROD_CATL t  where t.catl_parent = '100')) "+
   	    						" and t2.cust_id = '"+custId+"'  and t2.etl_date = to_date('"+etlDate+"', 'yyyy-MM-dd')");
   	    }else if("中间业务贡献度".equals(contriType)){
   	    	sb.setLength(0);
   	    	sb.append("select t2.*,t3.prod_name from ACRM_F_CI_PROD_CONT t2 left join OCRM_F_PD_PROD_INFO t3 on t2.prod_id=t3.product_id   "+
   	    			" where t2.prod_id in (select t1.product_id from OCRM_F_PD_PROD_INFO t1 "+
                       " where t1.catl_code in "+
                             " (SELECT t.CATL_CODE FROM OCRM_F_PD_PROD_CATL t  where t.catl_parent not in ('100','110'))) "+
   	    						" and t2.cust_id = '"+custId+"'  and t2.etl_date = to_date('"+etlDate+"', 'yyyy-MM-dd')");
   	    }
		SQL=sb.toString();
		datasource = ds;
	}

}
