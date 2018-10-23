package com.yuchengtech.bcrm.serviceManage.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.serviceManage.model.CustServiceInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * 客户服务管理Service
 * @author yuyz
 * @since 2012-12-06 
 */
@Service
public class CustServiceManageService extends CommonService {
   
   public CustServiceManageService(){
	   JPABaseDAO<CustServiceInfo, Long>  baseDAO=new JPABaseDAO<CustServiceInfo, Long>(CustServiceInfo.class);  
		super.setBaseDAO(baseDAO);
	}
}
