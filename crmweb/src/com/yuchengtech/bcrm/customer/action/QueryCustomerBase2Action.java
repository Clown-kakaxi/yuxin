package com.yuchengtech.bcrm.customer.action;

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
@Action(value="/querycustomerbase2", results={
    @Result(name="success", type="json")
})
public class QueryCustomerBase2Action extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    StringBuilder s = new StringBuilder("select t1.RELA_CREATE_DT,t1.ID,t2.* from OCRM_F_CI_RELATE_CUST_BASE t1 left join OCRM_F_CI_CUST_DESC t2 on t1.CUST_ID=t2.CUST_ID where t1.CUST_BASE_ID=");
	    s.append(request.getParameter("cbid"));
	    for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
             if(key.equals("CUST_ZH_NAME"))
                    s.append(" and CUST_ZH_NAME like"+" '%"+this.getJson().get(key)+"%'");
             else if(key.equals("CERT_NUM"))
                 s.append(" and CERT_NUM like"+" '%"+this.getJson().get(key)+"%'");
             else if(key.equals("RELA_CREATE_DT"))
                 s.append(" and  RELA_CREATE_DT ="+"to_date('"+this.getJson().get(key)+"', 'YYYY-MM-DD') ");
        }}

		setPrimaryKey("t1.ID");
		SQL=s.toString();
	    datasource = ds;
    }

}
