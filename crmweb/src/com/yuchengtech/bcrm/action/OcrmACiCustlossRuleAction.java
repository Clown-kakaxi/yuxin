package com.yuchengtech.bcrm.action;

/**
 * 大额客户流失预警信息体现参数
 * luyy
 * 2014-08-05
 */

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmACiCustlossRule;
import com.yuchengtech.bcrm.customer.service.OcrmACiCustlossRuleService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value = "/custLostRule")
public class OcrmACiCustlossRuleAction  extends CommonAction{

@Autowired
@Qualifier("dsOracle")
private DataSource ds;

@Autowired
private OcrmACiCustlossRuleService service;

@Autowired
public void init(){
	model = new OcrmACiCustlossRule();
	setCommonService(service);
}

AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 

public void prepare(){
	 ActionContext ctx = ActionContext.getContext();
	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	 StringBuilder sb=new StringBuilder();
	 sb.append(" select r.* from OCRM_A_CI_CUSTLOSS_RULE r where 1=1");
	
	SQL = sb.toString();
	datasource = ds;
}

public void save(){
	((OcrmACiCustlossRule)model).setCreateId(auth.getUserId());
	((OcrmACiCustlossRule)model).setCreateName(auth.getUsername());
	service.save(model);
}
public String batchDestroy(){
   	ActionContext ctx = ActionContext.getContext();
    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	String idStr = request.getParameter("idStr");
	String jql="delete from OcrmACiCustlossRule c where c.id in ("+idStr+")";
	Map<String,Object> values=new HashMap<String,Object>();
	service.batchUpdateByName(jql, values);
	addActionMessage("batch removed successfully");
    return "success";
}

public String custLostRule()  {
	ActionContext ctx = ActionContext.getContext();
	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	try {
		StringBuilder sb = new StringBuilder("");
		sb.append("select role_name as value,role_code as key from admin_auth_role ");
		this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return "success";
}
}