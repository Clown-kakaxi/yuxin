// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CustCFApplyAction.java

package com.yuchengtech.bcrm.customer.action;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.service.CustCFApplyService;
import com.yuchengtech.bob.common.CommonAction;

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value="/CustCFApplyAction", results={
    @Result(name="success", type="json")})
public class CustCFApplyAction extends CommonAction
{
	@Autowired
	private CustCFApplyService custCFApplyService;
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
		StringBuffer sb = new StringBuffer(" SELECT t.TAR_CUST_ID, t.APPLY_INIT, t.APPLY_REASON,t.APPLY_USER,t.APPROVEL_STATUS,t.CREATE_DATE,t.HB_CUST_ID, t.HB_CUST_NAME,t.ID,t.TAR_CUST_NAME FROM OCRM_F_CI_HHB_APPLY_INFO t  where 1=1 and t.ID in (select max(t1.id) from OCRM_F_CI_HHB_APPLY_INFO t1 group by t1.TAR_CUST_ID)");
		if (!"".equals(idStr) && idStr != null)
			sb.append((new StringBuilder(" and t.ID =")).append(idStr).toString());
		for (Iterator iterator = getJson().keySet().iterator(); iterator.hasNext();)
		{
			String key = (String)iterator.next();
			if (getJson().get(key) != null && !getJson().get(key).equals("") && (key == null || !key.equals("ACCOUNT_NAME")) && !key.equals("TREE_STORE"))
				key.equals("userId");
		}

		SQL = sb.toString();
		datasource = ds;
		addOracleLookup("CUST_STAT", "XD000081");
		addOracleLookup("CUST_TYP", "XD000080");
		addOracleLookup("CUST_LEV", "CDE0100016");
		addOracleLookup("CERT_TYPE", "PAR0100006");
		addOracleLookup("MAIN_TYPE", "SYS_USER_STATE");
	}

	public String batchCF()
	{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		String data = request.getParameter("data");
		String opTag = request.getParameter("opTag");
		String createDate = request.getParameter("createDate");
		String applyReason = request.getParameter("applyReason");
		if (!data.equals("[]"))
		{
			JSONArray jarray = JSONArray.fromObject(data);
			custCFApplyService.cfCustApply(jarray, opTag, createDate, applyReason);
		}
		return "success";
	}

	public String approvel()
		throws Exception
	{
		try
		{
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			String idStr = request.getParameter("idStr");
			String data = request.getParameter("tempArray");
			JSONArray jarray = JSONArray.fromObject(data);
			custCFApplyService.approvelUpdate(idStr, jarray);
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
			String idStr = request.getParameter("idStr");
			custCFApplyService.approvelBackUpdate(idStr);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return "success";
	}
}
