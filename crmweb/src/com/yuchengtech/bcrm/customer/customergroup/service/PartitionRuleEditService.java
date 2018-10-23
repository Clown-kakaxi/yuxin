package com.yuchengtech.bcrm.customer.customergroup.service;


import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.customergroup.model.OcrmFCiPartitionRule;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
/**
 * 客户群管理-关联客户经理成员维护
 * @author  sujm
 * @since 2013-04-04
 */
@Service
public class PartitionRuleEditService extends CommonService{
	
	 public PartitionRuleEditService(){
		   JPABaseDAO<OcrmFCiPartitionRule, Long>  baseDAO = new JPABaseDAO<OcrmFCiPartitionRule, Long>(OcrmFCiPartitionRule.class);  
		   super.setBaseDAO(baseDAO);
	 }
	 //添加客户群成员
	 public Object saveData(Object obj) {
			return baseDAO.save(obj);
		}
	 //删除客户群成员
    public void remove(JSONArray jarray) {
    	 if(jarray.size()>0){
    		for(int i=0;i<jarray.size();i++){
    			OcrmFCiPartitionRule ocrmFCiPartitionRule = em.find(OcrmFCiPartitionRule.class, Long.parseLong(jarray.get(i).toString()));;
                if (ocrmFCiPartitionRule != null) {
                   em.remove(ocrmFCiPartitionRule);
                    }
    	       }  
    	  }
    }
	 
}
