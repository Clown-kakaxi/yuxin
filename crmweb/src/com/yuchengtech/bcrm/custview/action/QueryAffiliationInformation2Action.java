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
@Action(value="/queryaffiliationinformation2", results={
    @Result(name="success", type="json")
})
public class QueryAffiliationInformation2Action extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	@Override
    public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    StringBuilder s = new StringBuilder("select t1.*,t1.institution_code instn_no,t1.institution_name unitname from ocrm_f_ci_belong_org t1  where t1.cust_id='");
	    s.append(request.getParameter("customerId")+"'");
		setPrimaryKey("t1.institution_code");
//		setBranchFileldName("t1.instn_no");
		SQL=s.toString();
  	    datasource = ds;
    }

}
