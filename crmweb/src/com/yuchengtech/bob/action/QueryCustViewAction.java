package com.yuchengtech.bob.action;

import java.sql.SQLException;
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
import com.yuchengtech.bob.core.QueryHelper;

@ParentPackage("json-default")
@Action(value="/queryCustView", results={
    @Result(name="success", type="json"),
})
public class QueryCustViewAction  {
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
        try {
        	 StringBuilder sb = new StringBuilder("select o.* from OCRM_SYS_VIEW_MANAGER o  order by o.orders");
        	setJSON(new QueryHelper(sb.toString(), ds.getConnection()).getJSON());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return "success";
    }
    
}
