// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CustomerCFDetailQueryAction.java

package com.yuchengtech.bcrm.customer.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value="/CustomerCFDetailQueryAction", results={
    @Result(name="success", type="json")})
public class CustomerCFDetailQueryAction extends CommonAction
{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	@Override
	public void prepare()
	{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		setJson(request.getParameter("condition"));
		String idStr = (String)getJson().get("id");
		String cfId = request.getParameter("cfId");
		StringBuilder sb = new StringBuilder("SELECT T.*, Q.*, W.USER_NAME  FROM OCRM_F_CI_CF_APPLY_INFO T LEFT JOIN OCRM_F_CI_CUST_DESC Q ON T.HB_CUST_ID = Q.CUST_ID LEFT JOIN ADMIN_AUTH_ACCOUNT W ON T.APPLY_USER = W.ACCOUNT_NAME WHERE 1=1 ");
		if (request.getParameter("start") != null)
			start = (new Integer(request.getParameter("start"))).intValue();
		if (request.getParameter("limit") != null)
			limit = (new Integer(request.getParameter("limit"))).intValue();
		if (!"".equals(idStr) && idStr != null)
			sb.append((new StringBuilder(" and T.ID =")).append(idStr).toString());
		if (!"".equals(cfId) && cfId != null)
			sb.append((new StringBuilder(" and T.TAR_CUST_ID ='")).append(cfId).append("'").toString());
		SQL = sb.toString();
		datasource = ds;
		addOracleLookup("CUST_STAT", "XD000081");
		addOracleLookup("CUST_TYP", "XD000080");
		addOracleLookup("CUST_LEV", "CDE0100016");
		addOracleLookup("CERT_TYPE", "PAR0100006");
		addOracleLookup("MAIN_TYPE", "SYS_USER_STATE");
	}
}
