// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CustCFDetailQueryAction.java

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
@Action(value="/CustCFDetailQueryAction", results={
    @Result(name="success", type="json")})
public class CustCFDetailQueryAction extends CommonAction
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
		StringBuffer sb = new StringBuffer("SELECT T1.*,T2.CUST_ZH_NAME AS SOURCE_CUST_ZH_NAME,T3.CUST_ZH_NAME AS TARGET_CUST_ZH_NAME FROM OCRM_F_CI_HHB_MAPPING T1  LEFT JOIN OCRM_F_CI_CUST_DESC T2 ON T1.SOURCE_CUST_ID = T2.CUST_ID  LEFT JOIN OCRM_F_CI_CUST_DESC T3 ON T1.TARGET_CUST_ID = T3.CUST_ID  WHERE 1=1 ");
		if (!"".equals(targetCustId) && targetCustId != null)
			sb.append((new StringBuilder(" AND T1.TARGET_CUST_ID ='")).append(targetCustId).append("'").toString());
		for (Iterator iterator = getJson().keySet().iterator(); iterator.hasNext();)
		{
			String key = (String)iterator.next();
			if (getJson().get(key) != null && !getJson().get(key).equals("") && (key == null || !key.equals("ACCOUNT_NAME")) && !key.equals("TREE_STORE"))
				key.equals("userId");
		}

		SQL = sb.toString();
		datasource = ds;
	}
}
