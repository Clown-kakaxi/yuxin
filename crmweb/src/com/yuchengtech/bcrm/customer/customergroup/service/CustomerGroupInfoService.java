package com.yuchengtech.bcrm.customer.customergroup.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.customergroup.model.OcrmFCiBase;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

@Service
@Transactional(value="postgreTransactionManager")
public class CustomerGroupInfoService extends CommonService{

	   public CustomerGroupInfoService(){
		   JPABaseDAO<OcrmFCiBase, Long>  baseDAO=new JPABaseDAO<OcrmFCiBase, Long>(OcrmFCiBase.class);  
		   super.setBaseDAO(baseDAO);
	   }	
	   
		 //根据ID是否为空进行新增或者修改并更新最近更新人，最近更新日期和最近更新机构等信息项
		@SuppressWarnings("unchecked")
		public Object save(Object obj) {
			OcrmFCiBase ocrmFCiBase = (OcrmFCiBase) obj;
			AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        if (ocrmFCiBase.getId() == null) {
	        	ocrmFCiBase.setCustBaseCreateDate(new Date());
	        	ocrmFCiBase.setCustBaseCreateName(auth.getUserId());
	        	ocrmFCiBase.setCustBaseCreateOrg(auth.getUnitId());
	        	ocrmFCiBase.setRecentUpdateDate(new Date());
	        	ocrmFCiBase.setRecentUpdateOrg(auth.getUnitId());
	        	ocrmFCiBase.setRecentUpdateUser(auth.getUserId());
	            //新增
	           em.persist(ocrmFCiBase);
	           OcrmFCiBase customerBase2 = em.find(OcrmFCiBase.class, ocrmFCiBase.getId());
	           String s1=customerBase2.getId().toString();
	           StringBuffer s = new StringBuffer("");
	          
	           if(s1.length()==5){
	        	   s.append("C00"+s1);
	  	      }
	  	      else if(s1.length()==6){
	  	    	 s.append("C0"+s1);
	  	      }
	  	      else {
	  	    	 s.append("C"+s1);
	  		      }
	           customerBase2.setCustBaseNumber(s.toString());
				return baseDAO.save(customerBase2);
	        } else {
	        	ocrmFCiBase.setRecentUpdateDate(new Date());
	        	ocrmFCiBase.setRecentUpdateOrg(auth.getUnitId());
	        	ocrmFCiBase.setRecentUpdateUser(auth.getUserId());
	            //更新
	           return baseDAO.save(ocrmFCiBase);
	        }
		
		}
		public Map<String, Object> loadGroupInfo(String groupId){
			   Map<String, Object> result = new HashMap<String, Object>();
		        List<HashMap<String, Object>> rowsList = new ArrayList<HashMap<String, Object>>();
		        StringBuffer JQL =  new StringBuffer("select cust" +
						"  from OcrmFCiBase cust" + 
						" where cust.id = '"+groupId+"'");
		 		Query q = em.createQuery(JQL.toString());
				List<OcrmFCiBase> rsList = q.getResultList();
				for(OcrmFCiBase ost: rsList){
					 if((null!=ost.getId())){
						 HashMap<String, Object> map = new HashMap<String, Object>();
						 map.put("GROUP_MEMBER_TYPE", ost.getGroupMemberType());
						 map.put("CUST_FROM", ost.getCustFrom());
						 map.put("CUST_BASE_NAME", ost.getCustBaseName());
						 map.put("CUST_BASE_CREATE_NAME", ost.getCustBaseCreateName());
						 rowsList.add(map);
					 }
				}
				 result.put("data", rowsList);
				 result.put("count", rsList.size());
				 return result;
		   }
		 
}
