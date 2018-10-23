// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CustCFApplyDetailQueryAction.java

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
@Action(value="/CustCFApplyDetailQueryAction", results={
    @Result(name="success", type="json")})

public class CustCFApplyDetailQueryAction extends CommonAction
{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;

	public void prepare()
	{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		String targetCustId = request.getParameter("targetCustId");
		StringBuffer sb = new StringBuffer("SELECT t1.*, t2.CUST_ZH_NAME  FROM OCRM_F_CI_CF_APPLY_INFO t1 LEFT JOIN OCRM_F_CI_CUST_DESC t2 ON t1.HB_CUST_ID = t2.CUST_ID where 1=1 ");
		if (!"".equals(targetCustId) && targetCustId != null)
			sb.append((new StringBuilder(" and t1.TAR_CUST_ID = '")).append(targetCustId).append("'").toString());
		SQL = sb.toString();
		datasource = ds;
	}
}
