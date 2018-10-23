package com.yuchengtech.bcrm.customer.service;


import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.customerView.model.OcrmSysLookupItemtemp;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;


@Service
public class LatentSourceChannelTempService extends CommonService {
	public LatentSourceChannelTempService(){
		   JPABaseDAO<OcrmSysLookupItemtemp, Long>  baseDAO=new JPABaseDAO<OcrmSysLookupItemtemp, Long>(OcrmSysLookupItemtemp.class); 
			super.setBaseDAO(baseDAO);
	   }
}
