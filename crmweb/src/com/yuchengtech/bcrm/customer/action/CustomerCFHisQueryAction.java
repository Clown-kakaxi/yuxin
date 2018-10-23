// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CustomerCFHisQueryAction.java

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
@Action(value="/CustomerCFHisQueryAction", results={
    @Result(name="success", type="json")})
public class CustomerCFHisQueryAction extends CommonAction
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
		StringBuilder sb = new StringBuilder("select t.*,q.*,q.CUST_ZH_NAME AS HB_CUST_NAME,p.CUST_ZH_NAME AS TAR_CUST_NAME from OCRM_F_CI_CF_HIS t  left join OCRM_F_CI_CUST_DESC q on t.SOURCE_CUST_ID=q.CUST_ID  left join OCRM_F_CI_CUST_DESC p on t.TARGET_CUST_ID=p.CUST_ID WHERE 1=1");
		if (request.getParameter("start") != null)
			start = (new Integer(request.getParameter("start"))).intValue();
		if (request.getParameter("limit") != null)
			limit = (new Integer(request.getParameter("limit"))).intValue();
		if (!"".equals(idStr) && idStr != null)
			sb.append((new StringBuilder(" and T.ID =")).append(idStr).toString());
		for (Iterator iterator = getJson().keySet().iterator(); iterator.hasNext();)
		{
			String key = (String)iterator.next();
			if (getJson().get(key) != null && !getJson().get(key).equals(""))
				if (key.equals("targetCustId"))
					sb.append((new StringBuilder(" and t.TARGET_CUST_ID = '")).append(getJson().get(key)).append("'").toString());
				else
				if (key.equals("targetCustName"))
					sb.append((new StringBuilder(" and  q.CUST_ZH_NAME like '%")).append(getJson().get(key)).append("%'").toString());
				else
				if (key.equals("hhbDt"))
					sb.append((new StringBuilder(" and  T.HHB_DT = to_date('")).append(getJson().get(key)).append("','yyyy-MM-dd')").toString());
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
