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
import com.yuchengtech.bob.core.PagingInfo;
import com.yuchengtech.bob.core.QueryHelper;

@ParentPackage("json-default")
@Action(value="/rollCustCanAddQuery", results={
    @Result(name="success", type="json")
})
public class RollCustCanAddQueryAction extends BaseAction{
    
	private HttpServletRequest request;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	RollMemberQueryAction rollMemberQuery = new RollMemberQueryAction();
	
    public String index() throws Exception {
    	
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String rollId = request.getParameter("rollId");
		
    	String custIdStr = rollMemberQuery.getCustListStr(rollId);
	    StringBuilder s = new StringBuilder("select t1.* from acrm_f_ci_cust_desc t1 where 1=1");
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                    if(key.equals("cust_zh_name")||key.equals("cust_zzdm")||key.equals("cust_typ")||key.equals("cust_lev"))
                      s.append(" and "+key+" like"+" '%"+this.getJson().get(key)+"%'");
               }}
        if(custIdStr != null && !custIdStr.equals(""))
        	s.append("and cust_id not in ("+custIdStr+")");
	    int currentPage =this.getStart()/this.getLimit()+1;
        PagingInfo pi = new PagingInfo(this.getLimit(),currentPage);
		QueryHelper qh = new QueryHelper(s.toString(), ds.getConnection(),pi);
		setJson(qh.getJSON());
        return "success";
    }



}
