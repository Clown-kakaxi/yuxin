package com.yuchengtech.bcrm.service;

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
public class TitleQueryService extends CommonService{
	
	   public TitleQueryService(){
		   JPABaseDAO<OcrmFSeTitle, Long>  baseDAO=new JPABaseDAO<OcrmFSeTitle, Long>(OcrmFSeTitle.class);  
		   super.setBaseDAO(baseDAO);
	   }
	   
	   @SuppressWarnings("unchecked")
	public Map<String, Object> loadTitleRs(){
		   Map<String, Object> result = new HashMap<String, Object>();
	        List<HashMap<String, Object>> rowsList = new ArrayList<HashMap<String, Object>>();
	 		String JQL = "select t from OcrmFSeTitle t where 1=1";
	 		Query q = em.createQuery(JQL);
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
	   
	   //查询题目及题目选项信息
	   @SuppressWarnings("unchecked")
		public Map<String, Object> loadTitleById(String paperId){
			   Map<String, Object> result = new HashMap<String, Object>();
		        List<HashMap<String, Object>> rowsList = new ArrayList<HashMap<String, Object>>();
		        /*****************原处理未能实现按顺序查询题目****************/
//		 		String JQL = "select t from OcrmFSeTitle t where t.titleId in (select p.questionId from OcrmFSmPapersQuestionRel p where p.paperId='"+paperId+"')";
//		 		Query q = em.createQuery(JQL);
//				List<OcrmFSeTitle> rsList = q.getResultList();
//				for(OcrmFSeTitle ost: rsList){
//						 HashMap<String, Object> map = new HashMap<String, Object>();
//						 map.put("titleId", ost.getTitleId());
//						 map.put("titleName", ost.getTitleName());
//						 map.put("titleRemark", ost.getTitleRemark());
//						 List r_mapList = new ArrayList();
//						 for(OcrmFSeTitleResult ostr:ost.getTitleIdL()){
//							 HashMap<String, Object> rsmap = new HashMap<String, Object>();
//							 rsmap.put("resultId", ostr.getResultId());
//							 rsmap.put("result", ostr.getResult());
//							 rsmap.put("resultScoring", ostr.getResultScoring());
//							 rsmap.put("titleId", ostr.getTitleId().getTitleId());
//							 r_mapList.add(rsmap);
//						 }
//						 map.put("titleIdL", r_mapList);
//						 rowsList.add(map);
//				}
//				 result.put("data", rowsList);
//				 result.put("count", rsList.size());
		        
			//查询本问卷的题目 注意题目顺序	p.question_order 
			List<Object[]> list = baseDAO.findByNativeSQLWithIndexParam(" select t.title_id,t.title_name,t.TITLE_REMARK from OCRM_F_SE_TITLE t,OCRM_F_SM_PAPERS_QUESTION_REL p where" +
					" t.title_id=p.question_id  and p.paper_Id='"+paperId+"' order by p.question_order");	
			if (list != null && list.size() > 0) {
				for(Object[] o : list ){
					HashMap<String, Object> map = new HashMap<String, Object>();
					 map.put("TITLE_ID", o[0]);
					 map.put("TITLE_NAME", o[1]);
					 map.put("TITLE_REMARK", o[2]);
					 List r_mapList = new ArrayList();
					 //查询本题目的答案  注意题目顺序 RESULT_SORT
					 List<Object[]> list2 = baseDAO.findByNativeSQLWithIndexParam(" select result_id,result,result_scoring,title_id from " +
					 		"OCRM_F_SE_TITLE_RESULT where title_id = '"+o[0].toString()+"' order by RESULT_SORT ");	
					 if (list2 != null && list2.size() > 0) {
							for(Object[] oo : list2 ){
								HashMap<String, Object> rsmap = new HashMap<String, Object>();
								 rsmap.put("RESULT_ID", oo[0]);
								 rsmap.put("RESULT", oo[1]);
								 rsmap.put("RESULT_SCORING", oo[2]);
								 rsmap.put("TITLE_ID", o[0]);
								 r_mapList.add(rsmap);
							}
					}
					 map.put("titleIdL", r_mapList);
					 rowsList.add(map);
	    		}
			}
			 result.put("data", rowsList);
			 result.put("count", list.size());
				 
			return result;
		   }
	   
	   //查询题目结果信息
	   public Map<String, Object> loadResultById(String id,String paperId){
		   Map<String, Object> result = new HashMap<String, Object>();
	       List<HashMap<String, Object>> rowsList = new ArrayList<HashMap<String, Object>>();
	       //需要保证查询的结果与题目顺序统一
	       List<Object[]> list =  baseDAO.findByNativeSQLWithIndexParam("select q.ID,q.RESULT_ID from Ocrm_f_Se_Cust_SATISFY_Qa q,OCRM_F_SM_PAPERS_QUESTION_REL r " +
	       		"where q.title_id = r.question_id and q.satisfy_id = '"+id+"' and r.paper_id = '"+paperId+"' order by r.question_order");
	       if(list != null&&list.size()>0){
	    	   for(Object[] o : list){
	    		   HashMap<String,Object> map = new HashMap<String, Object>();
	    		   map.put("ID", o[0]);
	    		   map.put("RESULT_ID", o[1]);
	    		   rowsList.add(map);
	    	   }
	       }
	       result.put("data", rowsList);
	       result.put("count", list.size());
	        return result;
	   }
	   
}
