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
@Action(value="/querycreditrisk", results={
    @Result(name="success", type="json")
})
public class QueryCreditRiskAction extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	@Override
	public void prepare() {
    	   
   		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        	StringBuilder s = new StringBuilder
        	   ("select t1.*  from OCRM_F_CI_CREDIT_RISK t1 where  t1.payaccount='");
        	   s.append(request.getParameter("accountId")+"'");
        	   setPrimaryKey("t1.id");
        	   SQL=s.toString();
               datasource = ds;
    }
    
}





