package com.yuchengtech.bcrm.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.model.AcrmFCiAssurerInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class AssurerInformationService extends CommonService{
	
	   public AssurerInformationService(){
		   JPABaseDAO<AcrmFCiAssurerInfo, String>  baseDAO=new JPABaseDAO<AcrmFCiAssurerInfo, String>(AcrmFCiAssurerInfo.class);  
		   super.setBaseDAO(baseDAO);
	   }


	
}
