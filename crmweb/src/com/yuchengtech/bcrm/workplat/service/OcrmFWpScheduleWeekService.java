package com.yuchengtech.bcrm.workplat.service;



import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.workplat.model.OcrmFWpScheduleWeek;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFWpScheduleWeekService extends CommonService{
	public OcrmFWpScheduleWeekService(){
		
		JPABaseDAO<OcrmFWpScheduleWeek, Long>  baseDAO=new JPABaseDAO<OcrmFWpScheduleWeek, Long>(OcrmFWpScheduleWeek.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	
}
