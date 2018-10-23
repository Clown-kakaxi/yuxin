package com.yuchengtech.bcrm.customer.potentialSme.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialSme.model.OcrmFMkPipeline;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFMKPipelineService extends CommonService{
	
	public OcrmFMKPipelineService(){
		JPABaseDAO<OcrmFMkPipeline,Long> baseDao = new JPABaseDAO<OcrmFMkPipeline,Long>(OcrmFMkPipeline.class);
		super.setBaseDAO(baseDao);
	}
	
}
