package com.yuchengtech.bcrm.customer.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bob.model.LookupMappingItem;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class LatentSourceChannelService extends CommonService {
	public LatentSourceChannelService(){
		   JPABaseDAO<LookupMappingItem, Long>  baseDAO=new JPABaseDAO<LookupMappingItem, Long>(LookupMappingItem.class); 
			super.setBaseDAO(baseDAO);
	   }
	
}
