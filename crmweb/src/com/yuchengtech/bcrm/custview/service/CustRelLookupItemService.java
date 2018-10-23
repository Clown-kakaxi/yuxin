package com.yuchengtech.bcrm.custview.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.model.LookupMappingItem;

@Service
public class CustRelLookupItemService extends CommonService{
	
	   public CustRelLookupItemService(){
		   JPABaseDAO<LookupMappingItem, Long>  baseDAO=new JPABaseDAO<LookupMappingItem, Long>(LookupMappingItem.class);  
		   super.setBaseDAO(baseDAO);
	   }

}
