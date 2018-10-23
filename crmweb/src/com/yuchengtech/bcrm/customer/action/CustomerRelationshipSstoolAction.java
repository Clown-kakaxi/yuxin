package com.yuchengtech.bcrm.customer.action;

import java.sql.SQLException;
import java.util.HashMap;

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
import com.yuchengtech.bob.core.QueryHelper;

@Action("/cusRelationshipstool")
public class CustomerRelationshipSstoolAction extends CommonAction {
	private static final long serialVersionUID = 1L;
	
	@Autowired	
	@Qualifier("dsOracle")
	private DataSource ds;
	
	
	
	/**
	 * 客户关系计划表获取客户类型
	 * ACRM_F_CI_BUSI_LINE表BL_NAME
	 */
	public void searchBlname(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT BL_ID,BL_NAME FROM ACRM_F_CI_BUSI_LINE  WHERE 1=1");
    	if(this.json!=null)
    		this.json.clear();
    	else 
    		this.json = new HashMap<String,Object>(); 
		try {
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			this.json = query.getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
