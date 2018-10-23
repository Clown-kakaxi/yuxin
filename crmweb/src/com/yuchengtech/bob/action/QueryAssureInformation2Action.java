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
@Action(value="/assureinformation2", results={
    @Result(name="success", type="json")
})
public class QueryAssureInformation2Action extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	@Override
    public void prepare() {
    
        	ActionContext ctx = ActionContext.getContext();
        	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        	 StringBuilder s = new StringBuilder("select t1.*,t2.* from fdm.ACRM_F_CI_ASS_DETAIL t1 left join fdm.ACRM_F_CI_ASS_INFO t2 on t1.CONTRACT_ID=t2.CONTRACT_ID where  t2.cust_id='");
        	  s.append(request.getParameter("customerId")+"'");
        	   setPrimaryKey("t2.cust_id");
        	   SQL=s.toString();
    	       datasource = ds;
    }
}




