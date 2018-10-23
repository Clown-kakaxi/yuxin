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
@Action(value="/queryaffiliationinformation1", results={
    @Result(name="success", type="json")
})
public class QueryAffiliationInformation1Action extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	@Override
    public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    StringBuilder s = new StringBuilder("select t2.*,t3.MEMBERSNUM from ocrm_f_ci_relate_cust_base t1 left join ocrm_f_ci_base t2 on t1.cust_base_id=t2.id left join(select count(*)AS \"MEMBERSNUM\",cust_base_id  from ocrm_f_ci_relate_cust_base  group by cust_base_id) t3 on t3.cust_base_id=t1.cust_base_id  where t1.cust_id='");
	    s.append(request.getParameter("customerId")+"'");
		setPrimaryKey("t1.id");
		SQL=s.toString();
	  	datasource = ds;
    }

}
