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
				
@Action(value="/rollCustCanAddQuery2", results={
    @Result(name="success", type="json")
})
public class RollCustCanAddQuery2Action extends CommonAction{
    
	private HttpServletRequest request;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
//	RollMemberQueryAction rollMemberQuery = new RollMemberQueryAction();
	
    public void prepare() {
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String planId = request.getParameter("planId");
		StringBuilder s;
	     s = new StringBuilder("select t.* from ocrm_f_ci_cust_desc t where 1>0");
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                    if(key.equals("cust_zh_name"))
                      s.append(" and t."+key+" like"+" '%"+this.getJson().get(key)+"%'");
               }}

        SQL = s.toString();
        setPrimaryKey("t.cust_id");
        datasource=ds;
    }



}
