package com.yuchengtech.bcrm.customer.customergroup.service;


import java.math.BigDecimal;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.customergroup.model.OcrmFCiBase;
import com.yuchengtech.bcrm.customer.customergroup.model.OcrmFCiRelateCustBase;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
/**
 * 客户群管理-群成员维护
 * @author  sujm
 * @since 2013-04-04
 */
@Service
public class GroupMemberEditService extends CommonService{
	
	 public GroupMemberEditService(){
		   JPABaseDAO<OcrmFCiRelateCustBase, Long>  baseDAO = new JPABaseDAO<OcrmFCiRelateCustBase, Long>(OcrmFCiRelateCustBase.class);  
		   super.setBaseDAO(baseDAO);
	 }
	 //添加客户群成员
	 public Object saveData(Object obj) {
			return baseDAO.save(obj);
		}
	 //保存客户成员数
	 public void saveDataLong(int size,String groupId) {
		 OcrmFCiBase pool=em.find(OcrmFCiBase.class, Long.parseLong(groupId));
		 pool.setCustBaseMemberNum(BigDecimal.valueOf(size));
		 em.merge(pool);
		}
	 //删除客户群成员
    public void remove(JSONArray jarray) {
    	 if(jarray.size()>0){
    		for(int i=0;i<jarray.size();i++){
    			OcrmFCiRelateCustBase ocrmFCiRelateCustBase = em.find(OcrmFCiRelateCustBase.class, Long.parseLong(jarray.get(i).toString()));;
                if (ocrmFCiRelateCustBase != null) {
                   em.remove(ocrmFCiRelateCustBase);
                    }
    	       }  
    	  }
    }
	 
}
