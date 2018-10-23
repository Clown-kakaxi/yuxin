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
@Action(value="/QueryCreditGranting1", results={
    @Result(name="success", type="json")
})
public class QueryCreditGranting1Action extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	@Override
	public void prepare() {
      
        	ActionContext ctx = ActionContext.getContext();
        	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        	 StringBuilder s = new StringBuilder("select  t1.*,t2.CUST_ZZDM,t2.CUST_ZH_NAME from fdm.ACRM_F_CI_CRE_CONTRACT t1 left join fdm.ACRM_F_CI_CUST_DESC t2 on t1.cust_id=t2.cust_id where  t1.cust_id='");
        	  s.append(request.getParameter("customerId")+"'");
        	  setPrimaryKey("t1.id");
        	  SQL=s.toString();
              datasource = ds;
    }
}






