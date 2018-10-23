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

@ParentPackage("json-default")
@Action(value="/productInternationalQuery", results={
    @Result(name="success", type="json"),
})
public class ProductInternationalQueryAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle;
	private HttpServletRequest request;
 	public void prepare() {
 		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        StringBuilder sb = new StringBuilder("SELECT t1.* " +
        		"FROM ACRM_F_NI_INTERNATIONAL_BUSI  t1 " +
        		"INNER JOIN OCRM_F_PD_PROD_INFO  t2 " +
        		"ON (t1.PRODUCT_ID = t2.PRODUCT_ID) " +
        		" where 1=1 ");
    	if(!("").equals(request.getParameter("cust_id"))&&("").equals(request.getParameter("base_id")))
		{
		    sb.append(" and t1.CUSTOM_ID = '" + request.getParameter("cust_id")+"'");
		}	
		else if(("").equals(request.getParameter("cust_id"))&&!("").equals(request.getParameter("base_id")))
		{
			sb.append(" and t1.CUSTOM_ID in ( select ta.cust_id " +
					"from OCRM_F_CI_RELATE_CUST_BASE ta where cust_base_id=" + request.getParameter("base_id")+")");
		}	
		else if(("").equals(request.getParameter("cust_id"))&&("").equals(request.getParameter("base_id"))&&!("").equals(request.getParameter("mgrid")))
		{
			sb.append(" and t1.CUSTOM_ID  IN (select c.cust_id from  OCRM_F_CI_BELONG_CUSTMGR c where c.mgr_id = '"+request.getParameter("mgrid")+"')");
		}
        sb.append(" and  t2.CATL_CODE=");
        sb.append(request.getParameter("catlCode"));
        addOracleLookup("STATUS", "DM0006");
        addOracleLookup("MONEY_CNCY", "ACC1300012");
        addOracleLookup("POUNDAGE_CNCY", "ACC1300012");
        setPrimaryKey("t1.AGREEMENT_ID");
		SQL = sb.toString();
		datasource = dsOracle;
 	
	}
}
