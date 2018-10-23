package com.yuchengtech.bob.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.core.PagingInfo;
import com.yuchengtech.bob.core.QueryHelper;

@Action("/performquery")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "performquery"})
})
public class PerformanceQueryAction extends BaseAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	public String index() {
        try {
        	ActionContext ctx = ActionContext.getContext();
        	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        	 StringBuilder s = new StringBuilder("select  t.* from fdm.ACRM_F_MANAGER_RELATION t WHERE 1> 0");
        	 for(String key:this.getJson().keySet()){
                 if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                 	if(key.equals("INSTN_NO")){
                 		s.append(" and " + key + " = '" + this.getJson().get(key) + "'");
                 	}else{
                 		if(key.equals("USER_ID")){
                 		s.append(" and " + key + " = '" + this.getJson().get(key) + "'");
                 		}else{
                 			if(key.equals("USER_NAME")){
                 				s.append(" and " + key + " = '" + this.getJson().get(key) + "'");
                 			}else{
                 				if(key.equals("CUST_NAME")){
                 					s.append(" and " + key + " = '" + this.getJson().get(key) + "'");
                 				}else{
                 					if(key.equals("CUST_ZZDM")){
                 						s.append(" and " + key + " = '" + this.getJson().get(key) + "'");
                 					}
                 				}
                 			}
                 		}
                 	}
//                     	sb.append("and user_id = '"+currenUserName+"'");
                 }
        		 
        	 }
        	   int currentPage =this.getStart()/this.getLimit()+1;
               PagingInfo pi = new PagingInfo(this.getLimit(),currentPage);
        	   QueryHelper qh = new QueryHelper(s.toString(), ds.getConnection(),pi); 
        	   setJson(qh.getJSON());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return "success";
    }
}






