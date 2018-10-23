package com.yuchengtech.bcrm.action;

/**
 * 大额客户流失预警批示处理
 * luyy
 * 2014-07-19
 */
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmACiCustlossFeedback;
import com.yuchengtech.bcrm.customer.service.OcrmACiCustlossFeedbackService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value = "/custLostF")
public class OcrmACiCustlossFeedbackAction  extends CommonAction{

@Autowired
@Qualifier("dsOracle")
private DataSource ds;

@Autowired
private OcrmACiCustlossFeedbackService service;

@Autowired
public void init(){
	model = new OcrmACiCustlossFeedback();
	setCommonService(service);
}

AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 

public void prepare(){
	 ActionContext ctx = ActionContext.getContext();
	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	 StringBuilder sb=new StringBuilder();
	String yjId = request.getParameter("yjId");
	sb.append(" select * from OCRM_A_CI_CUSTLOSS_FEEDBACK where yj_id='"+yjId+"'");
	SQL = sb.toString();
	datasource = ds;
}

public void save(){
	((OcrmACiCustlossFeedback)model).setFRemarkDate(new Date());
	((OcrmACiCustlossFeedback)model).setFUserId(auth.getUserId());
	((OcrmACiCustlossFeedback)model).setFUserName(auth.getUsername());
	
	service.save(model);
	
}
}