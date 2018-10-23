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
@Action(value="/querydictionary", results={
    @Result(name="success", type="json")
})
public class QueryDictionaryAction extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request; 
	
	@Override
    public void prepare() 
	{
        	ActionContext ctx = ActionContext.getContext();
        	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        	 StringBuilder s = new StringBuilder("select t1.code_name_1,t1.code_name from fdm.acrm_f_pub_code t1 where trim(t1.crm_code_type)='");
        	  s.append(request.getParameter("name")+"'");/*
        	   int currentPage =this.getStart()/this.getLimit()+1;
               PagingInfo pi = new PagingInfo(this.getLimit(),currentPage);
        	   QueryHelper qh = new QueryHelper(s.toString(), ds.getConnection(),pi); */
        	   setPrimaryKey("t1.crm_code");
        	/*   setJson(qh.getJSON());
        return "success";*/
        	   SQL=s.toString();
   	           datasource = ds;
    }
}
