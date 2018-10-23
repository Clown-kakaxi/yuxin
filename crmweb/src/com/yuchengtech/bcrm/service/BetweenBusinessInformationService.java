package com.yuchengtech.bcrm.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.model.AcrmFCiGkZjyw;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class BetweenBusinessInformationService extends CommonService{
	
	   public BetweenBusinessInformationService(){
		   JPABaseDAO<AcrmFCiGkZjyw, String>  baseDAO=new JPABaseDAO<AcrmFCiGkZjyw, String>(AcrmFCiGkZjyw.class);  
		   super.setBaseDAO(baseDAO);
	   }


	
}
