package com.yuchengtech.bcrm.custmanager.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custmanager.model.AcrmACustFxqIndex;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class AcrmACustFxqIndexService extends CommonService {

	public AcrmACustFxqIndexService(){
		JPABaseDAO<AcrmACustFxqIndex, Long>  baseDAO=new JPABaseDAO<AcrmACustFxqIndex, Long>(AcrmACustFxqIndex.class);  
		   super.setBaseDAO(baseDAO);
	}
}
