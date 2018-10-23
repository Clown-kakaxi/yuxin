package com.yuchengtech.bcrm.custmanager.action;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custmanager.model.OcrmFCmCustMgrInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
@Service
public class CustomerManagerAffirmService extends CommonService{
public CustomerManagerAffirmService(){
		
		JPABaseDAO<OcrmFCmCustMgrInfo, Long>  baseDAO=new JPABaseDAO<OcrmFCmCustMgrInfo, Long>(OcrmFCmCustMgrInfo.class);  
		   super.setBaseDAO(baseDAO);
	}

}
