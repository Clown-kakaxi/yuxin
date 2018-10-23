package com.yuchengtech.bcrm.workplat.service;



import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.workplat.model.OcrmFWpRemindRule1;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFWpRemindRule1Service extends CommonService{
//	
	public OcrmFWpRemindRule1Service(){
		
		JPABaseDAO<OcrmFWpRemindRule1, Long>  baseDAO=new JPABaseDAO<OcrmFWpRemindRule1, Long>(OcrmFWpRemindRule1.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	
}
