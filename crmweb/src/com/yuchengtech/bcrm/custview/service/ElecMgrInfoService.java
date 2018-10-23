package com.yuchengtech.bcrm.custview.service;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.AcrmFCiCustElecMgrInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class ElecMgrInfoService extends CommonService{
	
	   public ElecMgrInfoService(){
		   JPABaseDAO<AcrmFCiCustElecMgrInfo, Long>  baseDAO=new JPABaseDAO<AcrmFCiCustElecMgrInfo, Long>(AcrmFCiCustElecMgrInfo.class);  
		   super.setBaseDAO(baseDAO);
	   }
}
