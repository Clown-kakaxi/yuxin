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
@Action(value="/queryaffiliationinformation3", results={
    @Result(name="success", type="json")
})
public class QueryAffiliationInformation3Action extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	@Override
    public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    StringBuilder s = new StringBuilder("select t.mgr_id user_id,t.mgr_name user_name,t.mobilephone contact,t.unitname,t.institution instn_no from  " +
	    		"( select mgr_id,mgr_name,mobilephone,unitname,institution from " +
	    		"(select distinct t1.mgr_id,t1.mgr_name,t3.mobilephone,t2.unitname,t1.institution " +
	    		"from ocrm_f_ci_belong_custmgr t1 left join sys_units t2 on t1.institution=t2.unitid left join sys_users t3 on" +
	    		" t1.mgr_id=t3.userid where t1.cust_id='");
	    s.append(request.getParameter("customerId")+"')) t");
			setPrimaryKey("t.mgr_id");
			SQL=s.toString();
 	       datasource = ds;
    }
}
