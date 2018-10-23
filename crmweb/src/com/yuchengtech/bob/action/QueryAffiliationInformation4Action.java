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
@Action(value="/queryaffiliationinformation4", results={
    @Result(name="success", type="json")
})
public class QueryAffiliationInformation4Action extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	@Override
    public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    StringBuilder s = new StringBuilder("select t2.*,t3.MEMBERSNUM from OCRM_F_MM_RCUST_LIST t1 LEFT JOIN OCRM_F_MM_ROLL_CUST t2 ON t1.roll_id=t2.roll_id left join (select count(*)AS \"MEMBERSNUM\",roll_id  from OCRM_F_MM_RCUST_LIST  group by roll_id) t3 on t3.roll_id=t1.roll_id where t1.cust_id='");
	    s.append(request.getParameter("customerId")+"'");
			setPrimaryKey("t1.id");
			 SQL=s.toString();
  	       datasource = ds;
    }

}
