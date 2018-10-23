package com.yuchengtech.bcrm.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.model.AcrmFCiGkSave;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class SaveInformationService extends CommonService{
	
	   public SaveInformationService(){
		   JPABaseDAO<AcrmFCiGkSave, String>  baseDAO=new JPABaseDAO<AcrmFCiGkSave, String>(AcrmFCiGkSave.class);  
		   super.setBaseDAO(baseDAO);
	   }


	
}
