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
@Action(value = "/referenceRateQuery", results = { @Result(name = "success", type = "json") })
public class QueryReferenceRateAction extends CommonAction {

	private HttpServletRequest request;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
    public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);		

		String str = request.getParameter("name");
		
		StringBuilder sb = new StringBuilder("select crm_code,code_name_1 from fdm.acrm_f_pub_code where crm_code_type = 'DKQX'  and  crm_code = '" + str +"'");

		setPrimaryKey("crm_code");
		
        SQL=sb.toString();
        datasource = ds;
	}

}
