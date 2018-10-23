package com.yuchengtech.bcrm.workplat.service;



import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.workplat.model.OcrmFWpScheduleVisit;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFWpScheduleVisitService extends CommonService{
	public OcrmFWpScheduleVisitService(){
		
		JPABaseDAO<OcrmFWpScheduleVisit, Long>  baseDAO=new JPABaseDAO<OcrmFWpScheduleVisit, Long>(OcrmFWpScheduleVisit.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	
}
