// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OcrmFCiHhbMappingAction.java

package com.yuchengtech.bcrm.customer.action;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiHhbMapping;
import com.yuchengtech.bcrm.customer.service.OcrmFCiHhbMappingService;
import com.yuchengtech.bob.common.CommonAction;

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value="/ocrmFCiHhbMapping-info", results={
    @Result(name="success", type="json")})
public class OcrmFCiHhbMappingAction extends CommonAction
{
	@Autowired
	private OcrmFCiHhbMappingService ocrmFCiHhbMappingService;
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;

	@Autowired
	public void init()
	{
		model = new OcrmFCiHhbMapping();
		setCommonService(ocrmFCiHhbMappingService);
	}
	@Override
	public void prepare()
	{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		setJson(request.getParameter("condition"));
		String idStr = (String)getJson().get("id");
		StringBuffer sb = new StringBuffer("select c.*,D.*,E.CUST_ZH_NAME AS SOURCE_ZH_NAME from OCRM_F_CI_HHB_MAPPING C  LEFT JOIN OCRM_F_CI_CUST_DESC D ON D.CUST_ID=C.TARGET_CUST_ID LEFT JOIN OCRM_F_CI_CUST_DESC E ON E.CUST_ID=C.SOURCE_CUST_ID where 1=1");
		if (!"".equals(idStr) && idStr != null)
			sb.append((new StringBuilder(" and c.ID =")).append(idStr).toString());
		for (String key : getJson().keySet())
		{
			 String value = getJson().get(key).toString();
			if (getJson().get(key) != null && !getJson().get(key).equals("")&& !key.equals("HHB_DT")){
				sb.append((new StringBuilder(" and C.")).append(key).append(" =").append("'").append(getJson().get(key)).toString()).append("'");
			}else if (getJson().get(key) != null && !getJson().get(key).equals("")&& key.equals("HHB_DT")){
				sb.append((new StringBuilder(" and C.")).append(key).append(" =").append("to_date('").append(getJson().get(key)).toString()).append("','yyyy-MM-dd')");
			}
				
		}

		SQL = sb.toString();
		datasource = ds;
		addOracleLookup("CUST_STAT", "XD000081");
		addOracleLookup("CUST_TYP", "XD000080");
		addOracleLookup("CUST_LEV", "CDE0100016");
		addOracleLookup("CERT_TYPE", "PAR0100006");
		addOracleLookup("MAIN_TYPE", "SYS_USER_STATE");
	}

	@SuppressWarnings("unchecked")
	public HttpHeaders indexPage()
		throws Exception
	{
		StringBuilder sb;
		Map values;
		sb = new StringBuilder("select c from OcrmFCiHhbMapping c where 1=1 ");
		values = new HashMap();
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		if (request.getParameter("start") != null)
			start = (new Integer(request.getParameter("start"))).intValue();
		if (request.getParameter("limit") != null)
			limit = (new Integer(request.getParameter("limit"))).intValue();
		setJson(request.getParameter("condition"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (String key : getJson().keySet())
		{
		@SuppressWarnings("unused")
		String value = getJson().get(key).toString();
			if (getJson().get(key) != null && !getJson().get(key).equals(""))
				if (key.equals("hhbDt"))
				{
					sb.append(" and c.hhbDt = :hhbDt");
					values.put("hhbDt", sdf.parse((String)getJson().get(key)));
				} else
				{
					sb.append((new StringBuilder(" and c.")).append(key).append(" = :").append(key).toString());
					values.put(key, getJson().get(key));
				}
		}

		return super.indexPageByJql(sb.toString(), values);
	}
}
