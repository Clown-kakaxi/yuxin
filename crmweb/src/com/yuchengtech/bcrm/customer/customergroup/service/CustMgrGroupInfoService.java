package com.yuchengtech.bcrm.customer.customergroup.service;


import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.customergroup.model.OcrmFCiBaseMgrRelate;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
/**
 * 客户群管理-群成员维护
 * @author  sujm
 * @since 2013-04-04
 */
@Service
public class CustMgrGroupInfoService extends CommonService{
	
	 public CustMgrGroupInfoService(){
		   JPABaseDAO<OcrmFCiBaseMgrRelate, Long>  baseDAO = new JPABaseDAO<OcrmFCiBaseMgrRelate, Long>(OcrmFCiBaseMgrRelate.class);  
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
    			OcrmFCiBaseMgrRelate ocrmFCiBaseMgrRelate = em.find(OcrmFCiBaseMgrRelate.class, Long.parseLong(jarray.get(i).toString()));;
                if (ocrmFCiBaseMgrRelate != null) {
                   em.remove(ocrmFCiBaseMgrRelate);
                    }
    	       }  
    	  }
    }
	 
}
