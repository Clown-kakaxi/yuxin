package com.yuchengtech.bob.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.service.CustViewSearchService;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value="/queryCustView3", results={
    @Result(name="success", type="json"),
})
public class QueryCustView3Action  {
	@Autowired
	private CustViewSearchService cvs;
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	private HttpServletRequest request;
	
    private Map<String, Object> JSON;
    public Map<String, Object> getJSON() {
		return JSON;
	}

	public void setJSON(Map<String, Object> jSON) {
		JSON = jSON;
	}

    public String index() {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        auth.getAuthorities();
       //String[] roleIds = (String[])request.getParameter("role_id").toString().split("$");
        StringBuilder roleIdSb = new StringBuilder("");
        for(int i=0;i<auth.getAuthorities().size();i++){
		   if(!"".equals(auth.getAuthorities().get(i).toString())&&auth.getAuthorities().get(i)!=null){
			   if(i==0)
			       roleIdSb.append("'"+auth.getAuthorities().get(i).toString()+"'");
			   else
				   roleIdSb.append(",'"+auth.getAuthorities().get(i).toString()+"'");
		   }		 
		 
		}
      
    	Map numMap =cvs.searchCustViewTree(roleIdSb.toString());
    	//Map numMap2=(Map) numMap.get("data");
    	List numList= (List)numMap.get("data");
    	Map  numMap2= (Map)numList.get(0);
    	StringBuilder sb = new StringBuilder("");
        try {
        	if(!"0".equals(numMap2.get("NUM_ID").toString())){
        	sb.append("select * from OCRM_SYS_VIEW_MANAGER o1 inner join OCRM_SYS_VIEW_USER_RELATION o2  on  o1.ID=o2.VIEW_ID  where 1=1");
        	
        	 sb.append(" and o2.ROLE_ID IN (");
             sb.append(roleIdSb.toString());
             sb.append(")");
         	sb.append(" and viewtype="+request.getParameter("viewtype"));}
        	// sb.append(" and o3.ROLE_CODE='"+request.getParameter("role_id")+"'");}
        	else{
        		sb.append("select * from OCRM_SYS_VIEW_MANAGER where 1=1");
        		sb.append(" and viewtype="+request.getParameter("viewtype"));
        	}
        	
        	setJSON(new QueryHelper(sb.toString(), ds.getConnection()).getJSON());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return "success";
    }
    
}
