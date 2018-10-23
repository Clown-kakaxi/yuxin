package com.yuchengtech.bcrm.custmanager.action;

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
 * 客户积分记录查询
 * @author geyu
 * 2014-7-23
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custTranInfoQuery", results = { @Result(name = "success", type = "json")})
public class CustTranInfoQueryAction extends CommonAction {
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
   	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	    String custId=request.getParameter("custId");
   		StringBuffer sb=new StringBuffer(" select * from ACRM_F_EVT_SAVE_TRAD_TANS t where t.cust_id ='"+custId+"'");
		SQL=sb.toString();
		datasource = ds;
	}

}
