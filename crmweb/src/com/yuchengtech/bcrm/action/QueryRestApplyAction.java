package com.yuchengtech.bcrm.action;

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
@Action(value = "/queryrestapply", results = { @Result(name = "success", type = "json"), })
public class QueryRestApplyAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource dsOracle;

    private HttpServletRequest request;
    
	public void prepare() {

        ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest) ctx
                .get(ServletActionContext.HTTP_REQUEST);
        String id = request.getParameter("ID");
		StringBuilder sb = new StringBuilder(
				"select t.* from rest_apply t where 1>0 ");
		if(id!=null&&!id.equals("")){
			sb.append(" and t.ID = '"+id+"'");//for db2 9.7
//			sb.append(" and t.ID = "+id);//for db2 9.5
		}

        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
            	if(key.equals("ID"))
                    sb.append(" and t."+key+" = '"+this.getJson().get(key)+"'");
            }
        }
		this.setPrimaryKey("t.ID");
		SQL = sb.toString();
		datasource = dsOracle;
	}
}
