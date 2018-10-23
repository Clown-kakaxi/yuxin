package com.yuchengtech.bcrm.workplat.service;



import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.workplat.model.OcrmFWpScheduleMonth;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFWpScheduleMonthService extends CommonService{
	public OcrmFWpScheduleMonthService(){
		
		JPABaseDAO<OcrmFWpScheduleMonth, Long>  baseDAO=new JPABaseDAO<OcrmFWpScheduleMonth, Long>(OcrmFWpScheduleMonth.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	
}
