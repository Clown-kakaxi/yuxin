package com.yuchengtech.bcrm.customer.action;

import java.util.Map;

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
@Action(value="/queryimportquery", results={
    @Result(name="success", type="json")
})
public class QueryImportQueryAction extends CommonAction{
    
	@Autowired
    @Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	@Override
    public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        	StringBuilder s = new StringBuilder
        	   ("select t1.*" +
        	   		" from OCRM_F_CI_IMPORT_QUERY t1 " +
        	   			"where ");
        	Map<String, Object> session = ActionContext.getContext().getSession();
        	if(request.getParameter("pkHead")==null){
        		if(session.get("pkHead")!=null){
        			s.append(" t1.id like '"+session.get("pkHead").toString()+"%'");
        		}
        		else{    s.append(" 0>1");}}
        	else{
        	s.append(" t1.id like '"+request.getParameter("pkHead")+"%'");
        	session.put("pkHead",request.getParameter("pkHead"));
        	}
        	   setPrimaryKey("t1.id");
        	   SQL=s.toString();
   	           datasource = ds;
    }
}