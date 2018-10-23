package com.yuchengtech.bcrm.custview.service;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongOrg;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 客户归属机构信息
 */

@Service
public class CustBelOrgInfoService extends CommonService {

	public CustBelOrgInfoService() {
		JPABaseDAO<OcrmFCiBelongOrg, Long> baseDAO = new JPABaseDAO<OcrmFCiBelongOrg, Long>(
				OcrmFCiBelongOrg.class);
		super.setBaseDAO(baseDAO);
	}
	public void update(JSONArray jarray3){
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (jarray3.size() > 0){
			for (int i = 0; i < jarray3.size(); ++i){
				JSONObject wb = (JSONObject)jarray3.get(i);
				String searchSql = "select bo from OcrmFCiBelongOrg bo where 1=1 and bo.custId = ?1";
				Query query = em.createQuery(searchSql);
				query.setParameter(1, (String)wb.get("cust_id"));
				List<OcrmFCiBelongOrg> result = (List<OcrmFCiBelongOrg>)query.getResultList();
				int count=0;
				for(OcrmFCiBelongOrg aapl : result){
					
					String type =(String)wb.get("type");
					if(type.equals(aapl.getMainType())){
						count++;
						OcrmFCiBelongOrg ws = (OcrmFCiBelongOrg)this.em.find(OcrmFCiBelongOrg.class, Long.valueOf(aapl.getId()));
				        if (ws != null){
				        	ws.setInstitutionCode((String)wb.get("org_id"));
				        	ws.setInstitutionName((String)wb.get("org_name"));
				        	ws.setAssignDate(new Date(System.currentTimeMillis()));
				        	ws.setAssignUser(auth.getUsername());
				        	ws.setAssignUsername(auth.getCname());
							this.em.merge(ws);
				        }
					}
					
				}
				if(count==0){
					OcrmFCiBelongOrg bo = new OcrmFCiBelongOrg();
					bo.setInstitutionName((String)wb.get("org_name"));
					bo.setInstitutionCode((String)wb.get("org_id"));
					bo.setCustId((String)wb.get("cust_id"));
					bo.setMainType((String)wb.get("type"));
					bo.setInstitutionName((String)wb.get("org_name"));
					bo.setAssignUser(auth.getUsername());	//设置分配人
					bo.setAssignUsername(auth.getCname());	//设置分配人名称
					bo.setAssignDate(new Date(System.currentTimeMillis()));			//设置分配日期
					this.em.persist(bo);
				}
		}
		}
	}
	public void save(JSONArray jarray){
		if (jarray.size() > 0){
			for (int i = 0; i < jarray.size(); ++i){
				AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				JSONObject wa = (JSONObject)jarray.get(i);
				OcrmFCiBelongOrg bo = new OcrmFCiBelongOrg();
				bo.setInstitutionName((String)wa.get("org_name"));
				bo.setInstitutionCode((String)wa.get("org_id"));
				bo.setCustId((String)wa.get("cust_id"));
				bo.setMainType((String)wa.get("type"));
				bo.setInstitutionName((String)wa.get("org_name"));
				bo.setAssignUser(auth.getUsername());	//设置分配人
				bo.setAssignUsername(auth.getCname());	//设置分配人名称
				bo.setAssignDate(new Date(System.currentTimeMillis()));			//设置分配日期
				this.em.persist(bo);
		}
		}
	}
	public void remove(JSONArray jarray2){
		if (jarray2.size() > 0){
			for (int i = 0; i < jarray2.size(); ++i){
				
				JSONObject wb = (JSONObject)jarray2.get(i);
				String searchSql = "select bo from OcrmFCiBelongOrg bo where 1=1 and bo.custId = ?1";
				Query query = em.createQuery(searchSql);
				query.setParameter(1, (String)wb.get("cust_id"));
				List<OcrmFCiBelongOrg> result = (List<OcrmFCiBelongOrg>)query.getResultList();
				for(OcrmFCiBelongOrg aapl : result){
					String org_id=(String)wb.get("org_id");
					String type =(String)wb.get("type");
					if(org_id.equals(aapl.getInstitutionCode()) && type.equals(aapl.getMainType())){
						OcrmFCiBelongOrg ws = (OcrmFCiBelongOrg)this.em.find(OcrmFCiBelongOrg.class, Long.valueOf(aapl.getId()));
				        if (ws != null){
				        this.em.remove(ws);
				        }
					}
				}
		}
		}
	}
	public void reset(String cust_id) throws Exception{
		try{
		String searchSql = "select bo from OcrmFCiBelongOrg bo where 1=1 and bo.custId = ?1";
		Query query = em.createQuery(searchSql);
		query.setParameter(1, cust_id);
		List<OcrmFCiBelongOrg> result = (List<OcrmFCiBelongOrg>)query.getResultList();
		for(OcrmFCiBelongOrg aapl : result){
			OcrmFCiBelongOrg ws = (OcrmFCiBelongOrg)this.em.find(OcrmFCiBelongOrg.class,Long.valueOf(aapl.getId()));
		        if (ws != null){
		        this.em.remove(ws);
		        }
		}
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	}
	
}


