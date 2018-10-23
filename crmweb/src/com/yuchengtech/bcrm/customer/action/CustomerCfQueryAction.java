// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CustomerCfQueryAction.java

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
import com.yuchengtech.bcrm.customer.service.OcrmFCiHhbMappingService;
import com.yuchengtech.bob.common.CommonAction;

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value="/CustomerCfQueryAction", results={
    @Result(name="success", type="json")})
public class CustomerCfQueryAction extends CommonAction
{
	@Autowired
	private OcrmFCiHhbMappingService ocrmFCiHhbMappingService;
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	@Autowired
	 @Override
	public void prepare()
	{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		setJson(request.getParameter("condition"));
		String idStr = (String)getJson().get("id");
		StringBuffer sb = new StringBuffer("select distinct c.*,D.* from OCRM_F_CI_HHB_MAPPING C LEFT JOIN OCRM_F_CI_CUST_DESC D ON D.CUST_ID=C.TARGET_CUST_ID where 1=1 and C.ID IN (SELECT MAX(A.ID) FROM OCRM_F_CI_HHB_MAPPING A GROUP BY A.TARGET_CUST_ID) and C.TARGET_CUST_ID NOT IN (SELECT W.TAR_CUST_ID FROM OCRM_F_CI_CF_APPLY_INFO W WHERE W.APPROVEL_STATUS IN ('1','2') )");
		if (!"".equals(idStr) && idStr != null)
			sb.append((new StringBuilder(" and t.ID =")).append(idStr).toString());
		for (Iterator iterator = getJson().keySet().iterator(); iterator.hasNext();)
		{
			String key = (String)iterator.next();
			if (getJson().get(key) != null && !getJson().get(key).equals(""))
				sb.append((new StringBuilder(" and D.")).append(key).append(" ='").append(getJson().get(key)).append("'").toString());
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
