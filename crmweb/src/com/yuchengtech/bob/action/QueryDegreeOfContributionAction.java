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
@Action(value="/querydegreeofcontribution", results={
    @Result(name="success", type="json")
})
public class QueryDegreeOfContributionAction extends CommonAction{
    
	@Autowired
    @Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;  
	@Override
	 public void prepare() {
		     ActionContext ctx = ActionContext.getContext();
            	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        	 StringBuilder s = new StringBuilder("select  t1.* from ACRM_F_CI_CONTRIBUTION t1 where 1>0 and t1.cust_id='");
        	   s.append(request.getParameter("customerId")+"'"); 
        	   for(String key:this.getJson().keySet()){
                   if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                    if(key.equals("StatStart"))
                    	  s.append(" and CRM_DT>="+"to_date('"+this.getJson().get(key)+"', 'YYYY-MM-DD') ");
                    else if(key.equals("StatEnd"))
                    	 s.append(" and CRM_DT<="+"to_date('"+this.getJson().get(key)+"', 'YYYY-MM-DD') ");
               }}/*
        	   int currentPage =this.getStart()/this.getLimit()+1;
               PagingInfo pi = new PagingInfo(this.getLimit(),currentPage);
        	   QueryHelper qh = new QueryHelper(s.toString(), ds.getConnection(),pi);*/
        	   setPrimaryKey("t1.cust_id");
        	 /*  setJson(qh.getJSON());
        return "success";*/
        	   SQL=s.toString();
   	           datasource = ds;
    }
}