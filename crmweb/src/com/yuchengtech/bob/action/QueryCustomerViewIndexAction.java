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
@Action(value="/querycustomerviewindex", results={
    @Result(name="success", type="json")
})
public class QueryCustomerViewIndexAction extends CommonAction{
    
	@Autowired
    @Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	
	@Override
	 public void prepare() {
      
        	ActionContext ctx = ActionContext.getContext();
            request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        	 StringBuffer s = new StringBuffer("select  t1.* from V_ACRM_F_CI_USERESURCES t1 where 1>0 and t1.cust_id='");
        	   s.append(request.getParameter("customerId")+"'");
        	   //QueryHelper qh = new QueryHelper(s.toString(), ds.getConnection());
        	   //setJson(qh.getJSON());
	
        	   SQL=s.toString();
   	           datasource = ds;
    }
}