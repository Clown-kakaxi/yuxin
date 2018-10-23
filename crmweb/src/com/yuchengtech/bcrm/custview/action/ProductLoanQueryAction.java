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
@Action(value="/productLoanQuery", results={
    @Result(name="success", type="json"),
})
public class ProductLoanQueryAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle;   
	private HttpServletRequest request;
 	public void prepare() {
 		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        StringBuilder sb = new StringBuilder("select t1.* from ACRM_F_CI_ASSET_BUSI_PROTO t1 INNER JOIN OCRM_F_PD_PROD_INFO t2 " +
        		"ON (t1.PRODUCT_ID = t2.PRODUCT_ID) where 1=1 ");
    	if(!("").equals(request.getParameter("cust_id"))&&("").equals(request.getParameter("base_id")))
		{
			sb.append(" and t1.CUST_NO = '" + request.getParameter("cust_id")+"'");
		}	
		else if(("").equals(request.getParameter("cust_id"))&&!("").equals(request.getParameter("base_id")))
		{
			sb.append(" and t1.CUST_NO in ( select ta.cust_id " +
					"from OCRM_F_CI_RELATE_CUST_BASE ta where cust_base_id=" + request.getParameter("base_id")+")");
		}	
		else if(("").equals(request.getParameter("cust_id"))&&("").equals(request.getParameter("base_id"))&&!("").equals(request.getParameter("mgrid")))
		{
			sb.append(" and t1.CUST_NO IN (select c.cust_id from  OCRM_F_CI_BELONG_CUSTMGR c where c.mgr_id = '"+request.getParameter("mgrid")+"')");
		}
    	 sb.append(" and  t2.CATL_CODE=");
         sb.append(request.getParameter("catlCode"));
         addOracleLookup("CURR", "ACC1300012");
         addOracleLookup("INTR_CORR_MODE", "CDE0100020");
         addOracleLookup("INTC_CYCL", "CDE0100067");
         addOracleLookup("REPAY_MODE", "ACC0600018");
         addOracleLookup("SPEC_LOAN_TYE", "CDE0100030");
         addOracleLookup("LOAN_POL_PRO_CLASS", "CDE0100006");
         addOracleLookup("ENT_LOAN_TYP", "CDE0100032");
         addOracleLookup("LOAN_QUAL", "CDE0100064");
         addOracleLookup("LOAN_TYP", "CDE0100041");
         addOracleLookup("YN_DISC", "IF_FLAG");
         addOracleLookup("LOAN_INVEST", "PAR2100001");
         addOracleLookup("GRNT_TYP", "GRNT_TYP");
         addOracleLookup("REPAY_CYCL", "REPAY_CYCL");
        setPrimaryKey("t1.AGREEMENT_ID");
		SQL = sb.toString();
		datasource = dsOracle;
		}
}
