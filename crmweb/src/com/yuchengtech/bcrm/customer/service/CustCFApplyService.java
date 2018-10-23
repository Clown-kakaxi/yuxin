// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CustCFApplyService.java

package com.yuchengtech.bcrm.customer.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.customer.model.OcrmFCiCfApplyInfo;
import com.yuchengtech.bcrm.customer.model.OcrmFCiCfHi;
import com.yuchengtech.bcrm.customer.model.OcrmFCiHhbMapping;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
@Service
@Transactional(value = "postgreTransactionManager")
public class CustCFApplyService extends CommonService
{

	public CustCFApplyService()
	{
		JPABaseDAO<OcrmFCiCfApplyInfo,Long> baseDao = new JPABaseDAO<OcrmFCiCfApplyInfo,Long>(OcrmFCiCfApplyInfo.class);
		super.setBaseDAO(baseDao);
	}

	public String cfCustApply(JSONArray jarray, String opTag, String createDate, String applyReason)
	{
		try
		{
			AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!"".equals(opTag) && opTag != null)
				if (opTag.equals("single"))
				{
					if (jarray.size() > 0)
					{
						for (int i = 0; i < jarray.size(); i++)
						{
							JSONObject wa = (JSONObject)jarray.get(i);
							String tarCustId = (String)wa.get("targetCustId");
							String jql = (new StringBuilder(" delete from OcrmFCiCfApplyInfo c where c.tarCustId in ('")).append(tarCustId).append("') and c.applyInit in('").append(auth.getUnitId()).append("')").toString();
							Map<String,Object> values=new HashMap<String,Object>();
							this.batchUpdateByName(jql, values);
							OcrmFCiCfApplyInfo we = new OcrmFCiCfApplyInfo();
							we.setApplyReason(applyReason);
							we.setApplyInit(auth.getUnitId());
							we.setApplyUser(auth.getUserId());
							we.setApprovelStatus("1");
							we.setCreateDate(new Date());
							we.setHbCustId((String)wa.get("source_custId"));
							we.setHbCustName((String)wa.get("source_custZhName"));
							we.setTarCustId((String)wa.get("targetCustId"));
							we.setTarCustName((String)wa.get("target_custZhName"));
							this.em.persist(we);
						}

					}
				} else
				if (opTag.equals("all"))
				{
					JSONObject wa = (JSONObject)jarray.get(0);
					String tarCustId = (String)wa.get("targetCustId");
					String jql = (new StringBuilder(" delete from OcrmFCiCfApplyInfo c where c.tarCustId in ('")).append(tarCustId).append("') and c.applyInit in('").append(auth.getUnitId()).append("')").toString();
					Map<String,Object> values=new HashMap<String,Object>();
					this.batchUpdateByName(jql, values);
					String searchSql = "select n from OcrmFCiHhbMapping n where n.targetCustId =?1";
					Query query = em.createQuery(searchSql);
					query.setParameter(1, (String)wa.get("targetCustId"));
					query.setFirstResult(0);
					List<OcrmFCiHhbMapping> result = (List<OcrmFCiHhbMapping>)query.getResultList();

					for (OcrmFCiHhbMapping aapl : result)
					{
						OcrmFCiCfApplyInfo we = new OcrmFCiCfApplyInfo();
						we.setApplyReason(applyReason);
						we.setApplyInit(auth.getUnitId());
						we.setApplyUser(auth.getUserId());
						we.setApprovelStatus("1");
						we.setCreateDate(new Date());
						we.setHbCustId(aapl.getSourceCustId());
						AcrmFCiCustomer bd = (AcrmFCiCustomer)this.em.find(AcrmFCiCustomer.class,aapl.getSourceCustId());
						we.setHbCustName(bd.getCustName().trim());
						we.setTarCustId(aapl.getTargetCustId());
						AcrmFCiCustomer bc = (AcrmFCiCustomer)this.em.find(AcrmFCiCustomer.class,aapl.getTargetCustId());
						we.setTarCustName(bc.getCustName().trim());
						this.em.persist(we);
					}

				}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "success";
	}

	public String approvelUpdate(String idStr, JSONArray data)
	{
		AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		JPABaseDAO<OcrmFCiCfApplyInfo,Long> sBaseDAO = new JPABaseDAO<OcrmFCiCfApplyInfo,Long>(this.em,OcrmFCiCfApplyInfo.class);
	String jql = (new StringBuilder("update OcrmFCiCfApplyInfo c set c.approvelStatus='2' where c.id='")).append(idStr).append("'").toString();
	Map<String,Object> values=new HashMap<String,Object>();
	this.setBaseDAO(sBaseDAO);
	this.batchUpdateByName(jql, values);
		if (data.size() > 0)
		{
			for (int i = 0; i < data.size(); i++)
			{
				JSONObject wa = (JSONObject)data.get(i);
				AcrmFCiCustomer bq = (AcrmFCiCustomer)em.find(AcrmFCiCustomer.class, (String)wa.get("tarCustId"));
				bq.setCustStat("");//合并标志：01(未合并)
				this.em.merge(bq);
				JPABaseDAO<OcrmFCiHhbMapping,Long> newBaseDAO_i = new JPABaseDAO<OcrmFCiHhbMapping,Long>(this.em,OcrmFCiHhbMapping.class);
				String jql3 = (new StringBuilder("delete from OcrmFCiHhbMapping c where c.sourceCustId='")).append((String)wa.get("tarCustId")).append("'").toString();
				Map<String,Object> values3=new HashMap<String,Object>();
				this.setBaseDAO(newBaseDAO_i);
				this.batchUpdateByName(jql3, values3);
				OcrmFCiCfHi bd_i = new OcrmFCiCfHi();
				bd_i.setHhbDt(new Date());
				bd_i.setOppUser(auth.getUserId());
				bd_i.setOppUserOrg(auth.getUnitId());
				bd_i.setSourceCustId((String)wa.get("hbCustId"));
				bd_i.setTargetCustId((String)wa.get("tarCustId"));
				this.em.persist(bd_i);
			}

		}
		return "success";
	}

	public String approvelBackUpdate(String idStr)
	{
		JPABaseDAO<OcrmFCiCfApplyInfo,Long> sBaseDAO = new JPABaseDAO<OcrmFCiCfApplyInfo,Long>(this.em,OcrmFCiCfApplyInfo.class);
		String jql = (new StringBuilder("update OcrmFCiCfApplyInfo c set c.approvelStatus='3' where c.id='")).append(idStr).append("'").toString();
		Map<String,Object> values=new HashMap<String,Object>();
		this.setBaseDAO(sBaseDAO);
		this.batchUpdateByName(jql, values);
		return "success";
	}
}
