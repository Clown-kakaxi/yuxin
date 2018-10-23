package com.yuchengtech.bcrm.sales.message.service;




import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.sales.message.model.OcrmFMmSysMsg;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFMmSysMsgService extends CommonService{
//	
	public OcrmFMmSysMsgService(){
		
		JPABaseDAO<OcrmFMmSysMsg, Long>  baseDAO=new JPABaseDAO<OcrmFMmSysMsg, Long>(OcrmFMmSysMsg.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	
	
}
