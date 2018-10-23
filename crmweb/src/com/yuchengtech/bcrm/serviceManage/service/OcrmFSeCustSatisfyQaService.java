package com.yuchengtech.bcrm.serviceManage.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.serviceManage.model.OcrmFSeCustSatisfyQa;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * 满意度调查的结果保存service
 * 
 * @author luyy
 * @since 2014-06-16
 */
@Service
public class OcrmFSeCustSatisfyQaService extends CommonService {
   
   public OcrmFSeCustSatisfyQaService(){
	   JPABaseDAO<OcrmFSeCustSatisfyQa, Long>  baseDAO=new JPABaseDAO<OcrmFSeCustSatisfyQa, Long>(OcrmFSeCustSatisfyQa.class);  
		super.setBaseDAO(baseDAO);
	}
   
}
