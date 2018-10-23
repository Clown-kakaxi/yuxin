package com.yuchengtech.bcrm.workplat.service;



import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.workplat.model.OcrmFWpScheduleCredit;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFWpScheduleCreditService extends CommonService{
	public OcrmFWpScheduleCreditService(){
		
		JPABaseDAO<OcrmFWpScheduleCredit, Long>  baseDAO=new JPABaseDAO<OcrmFWpScheduleCredit, Long>(OcrmFWpScheduleCredit.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	
}
