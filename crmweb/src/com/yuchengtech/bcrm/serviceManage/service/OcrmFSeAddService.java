package com.yuchengtech.bcrm.serviceManage.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.serviceManage.model.OcrmFSeAdd;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * 客户积分管理service
 * 
 * @author luyy
 * @since 2014-06-09
 */
@Service
public class OcrmFSeAddService extends CommonService {
   
   public OcrmFSeAddService(){
	   JPABaseDAO<OcrmFSeAdd, Long>  baseDAO=new JPABaseDAO<OcrmFSeAdd, Long>(OcrmFSeAdd.class);  
		super.setBaseDAO(baseDAO);
	}
   
}
