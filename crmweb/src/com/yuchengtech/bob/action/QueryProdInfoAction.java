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
				
@Action(value="/queryprodinfo1", results={
    @Result(name="success", type="json")
})
public class QueryProdInfoAction extends CommonAction{
    
	@SuppressWarnings("unused")
	private HttpServletRequest request;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
			StringBuilder sb = new StringBuilder("select PRODUCT_ID,PROD_NAME from OCRM_F_PD_PROD_INFO where 1>0" +
					" ");
	        	SQL=sb.toString();
	        	limit = 9999999;
				datasource = ds;	
				setPrimaryKey("PRODUCT_ID");
	}
}
