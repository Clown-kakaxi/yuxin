package com.yuchengtech.bcrm.custmanager.service;

import java.util.List;

import javax.persistence.Query;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custmanager.model.OcrmFCmCustMgrInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class CustMgrAffirmService extends CommonService{
	
	private JPABaseDAO<OcrmFCmCustMgrInfo, Long> baseDao;

	@SuppressWarnings("unchecked")
	public CustMgrAffirmService(){
		this.baseDao = new JPABaseDAO(OcrmFCmCustMgrInfo.class);
		super.setBaseDAO(this.baseDao);
	}
	public void save( JSONArray jarray){
		if (jarray.size() > 0){
			for (int i = 0; i < jarray.size(); ++i){
				JSONObject wa = (JSONObject)jarray.get(i);
				OcrmFCmCustMgrInfo ws = new OcrmFCmCustMgrInfo();
				ws.setCustManagerId((String)wa.get("userid"));
				ws.setCustManagerName((String)wa.get("custmanagername"));
				ws.setWorkUnit((String)wa.get("unitname"));
	//			ws.setWorkUnit((String)wa.get("workunit"));
	//			ws.setCustManagerType((String)wa.get("rolename"));
//				String a = wa.get("userid").toString();
//				Long b = Long.valueOf(a);
//				ws.setUserId(b) ;
				this.em.persist(ws);
        }
	}	
}

  public void remove(JSONArray jarray2){
  if (jarray2.size() > 0){
	  for (int i = 0; i < jarray2.size(); ++i) {
		  
		  JSONObject wb = (JSONObject)jarray2.get(i);
		  String t = (String)wb.get("userid");
		  OcrmFCmCustMgrInfo ws2 = (OcrmFCmCustMgrInfo)this.em.find(OcrmFCmCustMgrInfo.class,Long.valueOf(t));
	        if (ws2 != null){
	        this.em.remove(ws2);
	        }
	  	}}
    	}
  

  public OcrmFCmCustMgrInfo find(Long id)
  {
    return ((OcrmFCmCustMgrInfo)this.em.find(OcrmFCmCustMgrInfo.class, id));
  }

  public List<OcrmFCmCustMgrInfo> findAll()
  {
    String wsFindAll = "select ws from OcrmFCmCustMgrInfo ws";
    Query wsQuery = this.em.createQuery(wsFindAll);
    return wsQuery.getResultList();
  }
}