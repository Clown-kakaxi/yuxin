package com.yuchengtech.bcrm.workplat.service;



import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.workplat.model.OcrmFWpScheduleLoanCheck;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFWpScheduleLoanCheckService extends CommonService{
	public OcrmFWpScheduleLoanCheckService(){
		
		JPABaseDAO<OcrmFWpScheduleLoanCheck, Long>  baseDAO=new JPABaseDAO<OcrmFWpScheduleLoanCheck, Long>(OcrmFWpScheduleLoanCheck.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	
}
