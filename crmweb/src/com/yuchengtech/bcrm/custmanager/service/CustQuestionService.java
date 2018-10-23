package com.yuchengtech.bcrm.custmanager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.system.model.OcrmFSeTitle;
import com.yuchengtech.bcrm.system.model.OcrmFSeTitleResult;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class CustQuestionService extends CommonService{
	
	   public CustQuestionService(){
		   JPABaseDAO<OcrmFSeTitle, Long>  baseDAO=new JPABaseDAO<OcrmFSeTitle, Long>(OcrmFSeTitle.class);  
		   super.setBaseDAO(baseDAO);
	   }
	   
	   @SuppressWarnings("unchecked")
	public Map<String, Object> loadTitleRs(String paperId){


		   Map<String, Object> result = new HashMap<String, Object>();
	        List<HashMap<String, Object>> rowsList = new ArrayList<HashMap<String, Object>>();
	 		String JQL = "select t from OcrmFSeTitle t ,OcrmFSmPapersQuestionRel r where t.titleId = r.questionId and r.paperId = ?1 order by r.questionOrder ";
	 		
	 		Query q = em.createQuery(JQL);
	 		
			q.setParameter(1, Long.valueOf(paperId));
	 		
			List<OcrmFSeTitle> rsList = q.getResultList();
			for(OcrmFSeTitle ost: rsList){
				 if(Integer.parseInt(ost.getAvailable()) == 1){
					 HashMap<String, Object> map = new HashMap<String, Object>();
					 map.put("titleId", ost.getTitleId());
					 map.put("titleName", ost.getTitleName());
					 map.put("titleRemark", ost.getTitleRemark());
//					 map.put("qaId", ost.getQaId());
					 List r_mapList = new ArrayList();
					 for(OcrmFSeTitleResult ostr:ost.getTitleIdL()){
						 HashMap<String, Object> rsmap = new HashMap<String, Object>();
						 rsmap.put("resultId", ostr.getResultId());
						 rsmap.put("result", ostr.getResult());
						 rsmap.put("resultScoring", ostr.getResultScoring());
						 rsmap.put("titleId", ostr.getTitleId().getTitleId());
						 r_mapList.add(rsmap);
					 }
					 map.put("titleIdL", r_mapList);
					 rowsList.add(map);
				 }
			}
			 result.put("data", rowsList);
			 result.put("count", rsList.size());
			 return result;
	   
	   
	   }

}
