package com.yuchengtech.bob.action;

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
@Action(value="/querycustomerbaseinformation", results={
    @Result(name="success", type="json")
})
public class QueryCustomerBaseInformationAction extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	@Override
    public void prepare() {
        	ActionContext ctx = ActionContext.getContext();
        	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        	 StringBuilder s = new StringBuilder("select t1.* from v_acrm_f_ci_cust_info t1 where  t1.cust_id='");
        	  s.append(request.getParameter("customerId")+"'");
        /*	   int currentPage =this.getStart()/this.getLimit()+1;
               PagingInfo pi = new PagingInfo(this.getLimit(),currentPage);
        	   QueryHelper qh = new QueryHelper(s.toString(), ds.getConnection(),pi); 
        	   qh.setPrimaryKey("t1.cust_id");
        	   HashMap<String, String> GreenplumMapping = new HashMap<String, String>();*/
        	

//       	       addGreenplumLookup("FZ_CRET_TYP", "ZJZL");
//        	   addGreenplumLookup("FR_CRET_TYP", "ZJZL");
//        	   addGreenplumLookup("GD_CRET_TYP", "ZJZL");
//        	   addGreenplumLookup("CRET_NAME", "ZMWJLX");
//        	   addGreenplumLookup("GD_CRET_TYPCD", "ZJZL");
//        	   addGreenplumLookup("SQ_CRET_TYP", "ZJZL");
//        	   addGreenplumLookup("CRED_LEV", "FXDJ");
//        	   addGreenplumLookup("CUST_SCOPE", "QYGM");
//        	   addGreenplumLookup("REGION", "XZQY");
//        	   addGreenplumLookup("COUNTRY", "GJDQ");
//        	   addGreenplumLookup("HY_TYP3", "HYLX2");
//        	   addGreenplumLookup("HY_TYP2", "HYLX1");
//        	   addGreenplumLookup("CRM_SCOPE", "KHQYGM");
//        	   addGreenplumLookup("GROUP_FLG", "YN");
//        	   addGreenplumLookup("MARKET_FLG", "YN");
//        	   addGreenplumLookup("SPECIFY_FLG", "YN");
//        	   addGreenplumLookup("HN_FLG", "YN");
//        	   addGreenplumLookup("ASS_AMT", "XSZE");
//        	   addGreenplumLookup("BUSINESS_JSR", "XSE");
//        	   addGreenplumLookup("EMPLOYEE_NUM", "CYRYS");
//        	   addGreenplumLookup("CPTL_CUR", "CCY");
//        	   addGreenplumLookup("HY_CLASS", "HYFL");
//        	   addGreenplumLookup("JJZZ_TYP", "JJZZLX");
     
        	  addOracleLookup("CRM_CUST_CLASS", "SYS_CUST_SORT");
        	  addOracleLookup("CUST_BIG_LEV", "CUST_LEVEL1");
        	  addOracleLookup("CUST_SMALL_LEV", "CUST_LEVEL2");
        	/*   if (!oracleMapping.isEmpty()) {
                   for(Entry<String, String> item : oracleMapping.entrySet()) {
                       qh.addOracleLookup(item.getKey(), item.getValue());
                   }
               }*/
        	  SQL=s.toString();
 	           datasource = ds;
    }
}




