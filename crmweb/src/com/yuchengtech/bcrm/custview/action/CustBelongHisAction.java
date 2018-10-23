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
/**
 * 客户归属调整历史查询Action
 * @author wangwan
 * @since 2013-01-18
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custBelongHisAction", results = { @Result(name = "success", type = "json") })
public class CustBelongHisAction extends CommonAction {

	// 数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	public void prepare() {


    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String cust_id = request.getParameter("cust_id");
        StringBuffer sb = new StringBuffer("select t.* from ocrm_f_ci_cust_belong_hist t  where 1 = 1 and t.cust_id = '" + cust_id + "' ORDER BY T.ASSIGN_TIME");
        SQL=sb.toString();
        datasource = ds;
        
        addOracleLookup("CUST_ASSIGN_TYPE","CUST_ASSIGN_TYPE");
        addOracleLookup("MAIN_TYPE","MAINTAIN_TYPE");
        
	}
	
}
