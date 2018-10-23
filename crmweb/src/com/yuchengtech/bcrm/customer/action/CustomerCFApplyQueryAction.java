// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CustomerCFApplyQueryAction.java

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
@Action(value="/CustomerCFApplyQueryAction", results={
    @Result(name="success", type="json")})
public class CustomerCFApplyQueryAction extends CommonAction
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
		StringBuilder sb = new StringBuilder("SELECT T.*,Q.*,W.USER_NAME FROM OCRM_F_CI_CF_APPLY_INFO T  LEFT JOIN OCRM_F_CI_CUST_DESC Q on T.HB_CUST_ID = Q.CUST_ID  LEFT JOIN  ADMIN_AUTH_ACCOUNT W ON T.APPLY_USER = W.ACCOUNT_NAME WHERE 1=1 AND T.ID IN (SELECT max (a.id)  FROM OCRM_F_CI_CF_APPLY_INFO a  GROUP BY a.TAR_CUST_ID)");
		if (request.getParameter("start") != null)
			start = (new Integer(request.getParameter("start"))).intValue();
		if (request.getParameter("limit") != null)
			limit = (new Integer(request.getParameter("limit"))).intValue();
		if (!"".equals(idStr) && idStr != null)
			sb.append((new StringBuilder(" and t.ID =")).append(idStr).toString());
		for (Iterator iterator = getJson().keySet().iterator(); iterator.hasNext();)
		{
			String key = (String)iterator.next();
			if (getJson().get(key) != null && !getJson().get(key).equals(""))
				if (key.equals("id"))
					sb.append((new StringBuilder(" and T.ID ='")).append(getJson().get(key)).append("'").toString());
				else
				if (key.equals("MGR_ID"))
					sb.append((new StringBuilder(" and t.")).append(key).append(" like '%").append(getJson().get(key)).append("%'").toString());
				else
				if (key.equals("MGR_NAME"))
					sb.append((new StringBuilder(" and t.")).append(key).append(" like '%").append(getJson().get(key)).append("%'").toString());
				else
				if (key.equals("approvelStatus"))
					sb.append((new StringBuilder(" and t.APPROVEL_STATUS like '%")).append(getJson().get(key)).append("%'").toString());
				else
				if (key.equals("hbCustId"))
					sb.append((new StringBuilder(" and t.HB_CUST_ID like '%")).append(getJson().get(key)).append("%'").toString());
				else
				if (key.equals("tarCustId"))
					sb.append((new StringBuilder(" and t.TAR_CUST_ID like '%")).append(getJson().get(key)).append("%'").toString());
		}

		SQL = sb.toString();
		datasource = ds;
		addOracleLookup("CUST_STAT", "XD000081");
		addOracleLookup("CUST_TYP", "XD000080");
		addOracleLookup("CUST_LEV", "CDE0100016");
		addOracleLookup("CERT_TYPE", "PAR0100006");
		addOracleLookup("MAIN_TYPE", "SYS_USER_STATE");
	}
}
