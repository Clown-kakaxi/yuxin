// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ComCertInfoAction.java

package com.yuchengtech.bcrm.custview.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiCertInfo;
import com.yuchengtech.bcrm.custview.service.ComCertInfoService;
import com.yuchengtech.bcrm.nioclient.EsbUtil;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.crm.exception.BizException;
@ParentPackage("json-default")
@Action(value="/comCertInfoAction", results={
    @Result(name="success", type="json"),
})
public class ComCertInfoAction extends CommonAction
{
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;
	@Autowired
	private ComCertInfoService service;
	@SuppressWarnings("unchecked")
	private Map JSON;

	@Autowired
	public void init()
	{
		model = new AcrmFCiCertInfo();
		needLog = true;
		setCommonService(service);
	}

	@SuppressWarnings("unchecked")
	public Map getJSON()
	{
		return JSON;
	}

	@SuppressWarnings("unchecked")
	public void setJSON(Map jSON)
	{
		JSON = jSON;
	}

	@SuppressWarnings("unchecked")
	public String searchOrgCustInfo()
	{
		Map map = null;
		Map returnMap = new HashMap();
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		String custId = request.getParameter("custId");
		try
		{
//			String custId = "773829930861";
			String txCode = "CRMTEST2";
			map = sendMsgToEcifForSearch(custId, txCode, "S007001990AO1016");
			if (map != null)
			{
				if (map != null && "000000".equals(map.get("FaultCode")))
				{
					returnMap.put("custId", map.get("custId").toString());
					returnMap.put("mainProduct", map.get("mainProduct").toString());
					returnMap.put("mainCust", map.get("mainCust").toString());
					returnMap.put("mainService", map.get("mainService").toString());
					returnMap.put("zoneCode", map.get("zoneCode").toString());
					returnMap.put("mainBusiness", map.get("mainBusiness").toString());
					returnMap.put("employeeScale", map.get("employeeScale").toString());
					returnMap.put("assetsScale", map.get("assetsScale").toString());
					json = returnMap;
				} else
				{
					String faultString = (String)map.get("FaultString");
					throw new BizException(1, 2, "1003", faultString, new Object[0]);
				}
			} else
			{
				throw new BizException(1, 2, "1003", "OCRM与ESB通信异常!", new Object[0]);
			}
		}
		catch (BizException e)
		{
			e.printStackTrace();
			throw new BizException(1, 0, e.getCode(), e.getMessage(), new Object[0]);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BizException(1, 0, "1002", "�Թ��ͻ���Ϣ��ѯʧ��!", new Object[0]);
		}
		return "success";
	}

	public String update()
	{
		Map map;
		map = null;
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		String custId = request.getParameter("custId");
		String txCode = "CRMTEST5";
		String updArray = request.getParameter("updArray");
		JSONObject jasonObject = JSONObject.fromObject(updArray);
		Map updMap = jasonObject;
		map = sendMsgToEcifForUpdate(updMap, txCode);
		String returnFlag = null;
		if (map != null){
			returnFlag = (String)map.get("FaultString");
			if (returnFlag.equals("成功"))
				return "success";
		}
		return returnFlag;
	}

	public Map sendMsgToEcifForSearch(String custId, String ecifTxCode, String esbTxCode)
	{
		try{
			String txCode = ecifTxCode;
			Map map;
			StringBuffer sendMsg = new StringBuffer();
			sendMsg.append("<txCode>");
			sendMsg.append(txCode);
			sendMsg.append("</txCode>");
			sendMsg.append("<authType>");
			sendMsg.append("1");
			sendMsg.append("</authType>");
			sendMsg.append("<authCode>");
			sendMsg.append("1001");
			sendMsg.append("</authCode>");
			sendMsg.append("<custNo>");
			sendMsg.append(custId);
			sendMsg.append("</custNo>");
			map = EsbUtil.sendMsgToEcifUnThrowException(sendMsg.toString(), esbTxCode);
			return map;
		}catch (BizException e) {
			e.printStackTrace();
			throw new BizException(e.getDirect(), e.getLevel(), e.getCode(), e.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1002", "通信异常!");
		}
	}

	public Map sendMsgToEcifForUpdate(Map map, String ecifTxCode)
	{ try{
		String custId;
		String txCode;
		String mainProduct;
		String mainCust;
		String mainService;
		String zoneCode;
		String mainBusiness;
		String employeeScale;
		String assetsScale;
		txCode = ecifTxCode;
		custId = (String)map.get("custId");
		mainProduct = (String)map.get("mainProduct");
		mainCust = (String)map.get("mainCust");
		mainService = (String)map.get("mainService");
		zoneCode = (String)map.get("zoneCode");
		mainBusiness = (String)map.get("mainBusiness");
		employeeScale = (String)map.get("employeeScale");
		assetsScale = (String)map.get("assetsScale");
		Map mapResp;
		StringBuffer sendMsg = new StringBuffer();
		sendMsg.append("<authCode>");
		sendMsg.append("1001");
		sendMsg.append("</authCode>");
		sendMsg.append("<authType>");
		sendMsg.append("1");
		sendMsg.append("</authType>");
		sendMsg.append("<custNo>");
		sendMsg.append(custId);
		sendMsg.append("</custNo>");
		sendMsg.append("<txCode>");
		sendMsg.append(txCode);
		sendMsg.append("</txCode>");
		sendMsg.append("<orgCustInfo>");
		sendMsg.append("<mainProduct>");
		sendMsg.append(mainProduct);
		sendMsg.append("</mainProduct>");
		sendMsg.append("<mainCust>");
		sendMsg.append(mainCust);
		sendMsg.append("</mainCust>");
		sendMsg.append("<mainService>");
		sendMsg.append(mainService);
		sendMsg.append("</mainService>");
		sendMsg.append("<zoneCode>");
		sendMsg.append(zoneCode);
		sendMsg.append("</zoneCode>");
		sendMsg.append("<mainBusiness>");
		sendMsg.append(mainBusiness);
		sendMsg.append("</mainBusiness>");
		sendMsg.append("<employeeScale>");
		sendMsg.append(employeeScale);
		sendMsg.append("</employeeScale>");
		sendMsg.append("<assetsScale>");
		sendMsg.append(assetsScale);
		sendMsg.append("</assetsScale>");
		sendMsg.append("</orgCustInfo>");
		mapResp = EsbUtil.sendMsgToEcifUnThrowException(sendMsg.toString(), txCode);
		return mapResp;
	}catch (BizException e) {
		e.printStackTrace();
		throw new BizException(e.getDirect(), e.getLevel(), e.getCode(), e.getMsg());
	} catch (Exception e) {
		e.printStackTrace();
		throw new BizException(1, 2, "1002", "控制层发送ESB消息失败!");
	}
}}
