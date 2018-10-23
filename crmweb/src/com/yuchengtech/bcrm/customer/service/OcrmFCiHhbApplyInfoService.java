// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OcrmFCiHhbApplyInfoService.java

package com.yuchengtech.bcrm.customer.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.customer.model.OcrmFCiHhbApplyInfo;
import com.yuchengtech.bcrm.customer.model.OcrmFCiHhbMapping;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
@Transactional(value = "postgreTransactionManager")
public class OcrmFCiHhbApplyInfoService extends CommonService
{

	public OcrmFCiHhbApplyInfoService()
	{
		JPABaseDAO<OcrmFCiHhbApplyInfo,Long> baseDao = new JPABaseDAO<OcrmFCiHhbApplyInfo,Long>(OcrmFCiHhbApplyInfo.class);
		super.setBaseDAO(baseDao);
	}

	public String approvelUpdate(JSONArray jarray)
	{
		if (jarray.size() > 0)
		{
			for (int i = 0; i < jarray.size(); i++)
			{
				JSONObject wa = (JSONObject)jarray.get(i);
				String idStr = (String)wa.get("idStr");
				String hbCustId = (String)wa.get("hbCustId");
				String tarCustId = (String)wa.get("tarCustId");
				String ifMain = (String)wa.get("ifMain");
				String hbOrgId = (String)wa.get("hbOrgId");
				String hbMainType = (String)wa.get("hbMainType");
			
				
				JPABaseDAO<OcrmFCiHhbApplyInfo,Long> laiBaseDAO = new JPABaseDAO<OcrmFCiHhbApplyInfo,Long>(this.em,OcrmFCiHhbApplyInfo.class);
				String jql = (new StringBuilder("update OcrmFCiHhbApplyInfo c set c.approvelStatus='2' where c.id='")).append(idStr).append("'").toString();
				Map<String,Object> values=new HashMap<String,Object>();
				this.setBaseDAO(laiBaseDAO);
				batchUpdateByName(jql, values);
				JPABaseDAO<AcrmFCiCustomer,Long> upDao = new JPABaseDAO<AcrmFCiCustomer,Long>(this.em,AcrmFCiCustomer.class);

				if (ifMain.equals("0"))
				{//合并标志：02(未合并)
					String jql2 = (new StringBuilder("update OcrmFCiCustDesc c set c.hbFlag ='02' where c.custId='")).append(hbCustId).append("'").toString();
					Map<String,Object> values2=new HashMap<String,Object>();
					this.setBaseDAO(upDao);
					batchUpdateByName(jql2, values2);
				}
				OcrmFCiHhbMapping hhbMap = new OcrmFCiHhbMapping();
				hhbMap.setSourceCustId(hbCustId);
				hhbMap.setTargetCustId(tarCustId);
				Date ndate = new Date();
				hhbMap.setHhbDt(ndate);
				JPABaseDAO<OcrmFCiHhbMapping,Long> hhbBaseDAO = new JPABaseDAO<OcrmFCiHhbMapping,Long>(this.em,OcrmFCiHhbMapping.class);
				this.setBaseDAO(hhbBaseDAO);
				hhbBaseDAO.save(hhbMap);
			}

		}
		return "success";
	}

	public String approvelBackUpdate(JSONArray jarray)
	{
		if (jarray.size() > 0)
		{
			for (int i = 0; i < jarray.size(); i++)
			{
				JSONObject wa = (JSONObject)jarray.get(i);
				String idStr = (String)wa.get("idStr");
				JPABaseDAO<OcrmFCiHhbApplyInfo,Long> laiBaseDAO = new JPABaseDAO<OcrmFCiHhbApplyInfo,Long>(this.em,OcrmFCiHhbApplyInfo.class);
				String jql = (new StringBuilder("update OcrmFCiHhbApplyInfo c set c.approvelStatus='3' where c.id='")).append(idStr).append("'").toString();
				Map<String,Object> values=new HashMap<String,Object>();
				this.setBaseDAO(laiBaseDAO);
				batchUpdateByName(jql, values);
			}

		}
		return "success";
	}
}
