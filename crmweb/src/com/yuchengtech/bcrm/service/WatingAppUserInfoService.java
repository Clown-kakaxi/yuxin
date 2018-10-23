package com.yuchengtech.bcrm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.system.model.AdminAuthAccount;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class WatingAppUserInfoService extends CommonService{
	
	   public WatingAppUserInfoService(){
		   JPABaseDAO<AdminAuthAccount, Long>  baseDAO=new JPABaseDAO<AdminAuthAccount, Long>(AdminAuthAccount.class);  
		   super.setBaseDAO(baseDAO);
	   }
	   
	   @SuppressWarnings("unchecked")
	public Map<String, Object> loadTitleRs(String role){
		   Map<String, Object> result = new HashMap<String, Object>();
	        List<HashMap<String, Object>> rowsList = new ArrayList<HashMap<String, Object>>();
	 		StringBuffer JQL = new StringBuffer("select t from AdminAuthAccount t , AdminAuthAccountRole t1,AdminAuthRole t2 where t1.accountId = t.id and t1.roleId =t2.id ");
	 		if(null!=role&&role.length()>0){
	 			JQL.append("and t2.roleCode in ('"+role.replace(",", "','")+"') ");
	 		}
	 		Query q = em.createQuery(JQL.toString());
			List<AdminAuthAccount> rsList = q.getResultList();
			for(AdminAuthAccount ost: rsList){
				 if((null!=ost.getUserState())&&(Integer.parseInt(ost.getUserState()) == 1)){
					 HashMap<String, Object> map = new HashMap<String, Object>();
					 map.put("userName", ost.getUserName());
					 map.put("accountName", ost.getAccountName());
					 map.put("userState", ost.getUserState());
					 rowsList.add(map);
				 }
			}
			 result.put("data", rowsList);
			 result.put("count", rsList.size());
			 return result;
	   }
}
