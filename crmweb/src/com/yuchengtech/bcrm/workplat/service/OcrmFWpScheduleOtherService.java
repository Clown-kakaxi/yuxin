package com.yuchengtech.bcrm.workplat.service;



import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.workplat.model.OcrmFWpScheduleOther;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFWpScheduleOtherService extends CommonService{
	public OcrmFWpScheduleOtherService(){
		
		JPABaseDAO<OcrmFWpScheduleOther, Long>  baseDAO=new JPABaseDAO<OcrmFWpScheduleOther, Long>(OcrmFWpScheduleOther.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	
}
