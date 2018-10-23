package com.yuchengtech.bcrm.custview.service;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

@Service
public class CustMgrInfoSearchService extends CommonService {
   
   public CustMgrInfoSearchService(){
		JPABaseDAO<OcrmFCiBelongCustmgr, Long>  baseDAO=new JPABaseDAO<OcrmFCiBelongCustmgr, Long>(OcrmFCiBelongCustmgr.class);  
		super.setBaseDAO(baseDAO);
	}
	public void save( JSONArray jarray,String custIds){
		

		
		if (jarray.size() > 0){
			for (int i = 0; i < jarray.size(); ++i){
				AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				JSONObject wa = (JSONObject)jarray.get(i);
				OcrmFCiBelongCustmgr ws = new OcrmFCiBelongCustmgr();
				ws.setMgrId((String) wa.get("mgrId"));
				ws.setMgrName((String) wa.get("mgrName"));
				ws.setInstitution((String) wa.get("institution"));
				ws.setInstitutionName((String) wa.get("institution_name"));
				ws.setMainType("2");
				ws.setAssignUser(auth.getUsername());	//设置分配人
				ws.setAssignUsername(auth.getCname());	//设置分配人名称
				ws.setAssignDate(new Date(System.currentTimeMillis()));	
				ws.setCustId(custIds);

				this.em.persist(ws);
        }
	}	
}

	public void remove(JSONArray jarray){
  if (jarray.size() > 0){
	  for (int i = 0; i < jarray.size(); ++i) {
		  
		  JSONObject wb = (JSONObject)jarray.get(i);
		  String t = (String)wb.get("id");
		  OcrmFCiBelongCustmgr ws2 = (OcrmFCiBelongCustmgr)this.em.find(OcrmFCiBelongCustmgr.class,Long.valueOf(t));
	        if (ws2 != null){
	        this.em.remove(ws2);
	        }
	  	}}
    	}
	public void saveMainTypeMgr(String custIds, String mainMgrId,
			String mainMgrName,String institution,String institution_name) {
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String mainType="1";
		String searchSql = "select bo from OcrmFCiBelongCustmgr bo where 1=1 and bo.custId = ?1 and bo.mainType='"+mainType+"'";
		Query query = em.createQuery(searchSql);
		query.setParameter(1, custIds);
		List<OcrmFCiBelongCustmgr> result = (List<OcrmFCiBelongCustmgr>)query.getResultList();
		int count=0;
		for(OcrmFCiBelongCustmgr aapl : result){
			
			OcrmFCiBelongCustmgr ws = (OcrmFCiBelongCustmgr)this.em.find(OcrmFCiBelongCustmgr.class, Long.valueOf(aapl.getId()));
	        if (ws != null){
	        	ws.setMgrId(mainMgrId);
	        	ws.setMgrName(mainMgrName);
	        	ws.setAssignDate(new Date(System.currentTimeMillis()));
	        	ws.setAssignUser(auth.getUsername());
	        	ws.setAssignUsername(auth.getCname());
	        	ws.setInstitution(institution);
	        	ws.setInstitutionName(institution_name);
	        	
				this.em.merge(ws);
	        }
			
		}
	}
  
}
