// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CustHBDetailQueryAction.java

package com.yuchengtech.bcrm.customer.action;

import java.util.Iterator;

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
@Action(value="/CustHBDetailQueryAction", results={
    @Result(name="success", type="json")})
public class CustHBDetailQueryAction extends CommonAction
{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Autowired
	private HttpServletRequest request;
	@Override
	public void prepare()
	{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		setJson(request.getParameter("condition"));
		String idStr = request.getParameter("idStr");
		StringBuffer sb = new StringBuffer(" SELECT t.* FROM OCRM_F_CI_HHB_APPLY_INFO t  where 1=1 ");
		if (!"".equals(idStr) && idStr != null)
			sb.append((new StringBuilder(" and t.TAR_CUST_ID in '")).append(idStr).append("'").toString());
		for (Iterator iterator = getJson().keySet().iterator(); iterator.hasNext();)
		{
			String key = (String)iterator.next();
			if (getJson().get(key) != null && !getJson().get(key).equals("") && key != null && key.equals("id"))
				sb.append((new StringBuilder(" and t.TAR_CUST_ID in '")).append(idStr).append("'").toString());
		}

		SQL = sb.toString();
		datasource = ds;
	}
}
