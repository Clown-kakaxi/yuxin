package com.yuchengtech.bcrm.custview.action;

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


@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custBelongMgrSearchAction", results = { @Result(name = "success", type = "json") })
public class CustBelongMgrSearchAction extends CommonAction {

	// 数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	
	public void prepare() {


    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        StringBuffer sb = new StringBuffer("select t.* from OCRM_F_CI_BELONG_CUSTMGR t where 1=1 ");
    	for(String key : this.getJson().keySet()){
    		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){	
				if(null!=key&&key.equals("USER_NAME")){
					sb.append("  AND (t.MGR_NAME like '"+this.getJson().get(key)+"')");
				}
				else if (key.equals("TREE_STORE")){
					sb.append("  AND (t.INSTITUTION in ( "+this.getJson().get(key)+"))");
				}
				else if (key.equals("cust_id")){
					sb.append("  AND (t.cust_id in ( "+this.getJson().get(key)+"))");
				}
    		}
		}
        	SQL=sb.toString();
			datasource = ds;
    
	
	}
	
	public void searchOrg() {


    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String cust_id = request.getParameter("cust_id");
    	String main_type = request.getParameter("main_type");
        StringBuffer sb = new StringBuffer("select 0 as parent_id ,t.* from OCRM_F_CI_BELONG_ORG t where 1>0");
        if(!main_type.equals("")){
        	sb.append(" and t.main_type = '"+main_type+"'");
        }
        if(!cust_id.equals("")){
        	sb.append(" and t.cust_id = '"+cust_id+"'");
        }
        	SQL=sb.toString();
			datasource = ds;
    
	
	}
}
