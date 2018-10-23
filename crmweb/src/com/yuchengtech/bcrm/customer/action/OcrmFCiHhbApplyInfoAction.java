// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OcrmFCiHhbApplyInfoAction.java

package com.yuchengtech.bcrm.customer.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiHhbApplyInfo;
import com.yuchengtech.bcrm.customer.service.OcrmFCiHhbApplyInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value="/ocrmFCiHhbApplyInfo-info", results={
    @Result(name="success", type="json")})
public class OcrmFCiHhbApplyInfoAction extends CommonAction
{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Autowired
	private OcrmFCiHhbApplyInfoService ocrmFCiHhbApplyInfoService;
	@Autowired
	public void init()
	{
		model = new OcrmFCiHhbApplyInfo();
		setCommonService(ocrmFCiHhbApplyInfoService);
	}

	public void prepare()
	{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		setJson(request.getParameter("condition"));
		StringBuffer sb = new StringBuffer("SELECT T1.* FROM OCRM_F_CI_HHB_APPLY_INFO T1 WHERE 1=1 ");
		sb.append(" AND t1.id in (SELECT max (a.id) FROM OCRM_F_CI_HHB_APPLY_INFO a  GROUP BY a.tar_cust_id)");
		for (String key : this.getJson().keySet())
		{
			@SuppressWarnings("unused")
			String value = getJson().get(key).toString();
			if (getJson().get(key) != null && !getJson().get(key).equals(""))
				if (key.equals("id"))
					sb.append((new StringBuilder(" and T1.ID ='")).append(getJson().get(key)).append("'").toString());
				else
					sb.append((new StringBuilder(" and T1.")).append(key).append(" like '%").append(getJson().get(key)).append("%'").toString());
		}

		SQL = sb.toString();
		datasource = ds;
		try
		{
			json = (new QueryHelper(SQL, ds.getConnection())).getJSON();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public HttpHeaders indexPage()
		throws Exception
	{
		StringBuilder sb;
		Map values;
		sb = new StringBuilder("select c from OcrmFCiHhbApplyInfo c where 1=1 ");
		values = new HashMap();
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		if (request.getParameter("start") != null)
			start = (new Integer(request.getParameter("start"))).intValue();
		if (request.getParameter("limit") != null)
			limit = (new Integer(request.getParameter("limit"))).intValue();
		setJson(request.getParameter("condition"));
		for (Iterator iterator = getJson().keySet().iterator(); iterator.hasNext();)
		{
			String key = (String)iterator.next();
			if (getJson().get(key) != null && !getJson().get(key).equals(""))
				if (key.equals("id"))
				{
					sb.append(" and c.id = :id");
					values.put("id", Long.valueOf(Long.parseLong((String)getJson().get(key))));
				} else
				{
					sb.append((new StringBuilder(" and c.")).append(key).append(" like '%").append(getJson().get(key)).append("%'").toString());
				}
		}

		return super.indexPageByJql(sb.toString(), values);
	}

	public String approvel()
		throws Exception
	{
		try
		{
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			String data = request.getParameter("data");
			if (!data.equals("[]"))
			{
				JSONArray data2 = JSONArray.fromObject(data);
				ocrmFCiHhbApplyInfoService.approvelUpdate(data2);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return "success";
	}

	public String approvelBack()
		throws Exception
	{
		try
		{
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			String data = request.getParameter("data");
			if (!data.equals("[]"))
			{
				JSONArray data2 = JSONArray.fromObject(data);
				ocrmFCiHhbApplyInfoService.approvelBackUpdate(data2);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return "success";
	}
}
