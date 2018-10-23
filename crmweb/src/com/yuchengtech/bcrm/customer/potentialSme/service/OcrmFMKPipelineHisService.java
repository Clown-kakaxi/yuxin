package com.yuchengtech.bcrm.customer.potentialSme.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialSme.model.OcrmFMkPipelineBackHis;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFMKPipelineHisService extends CommonService{
	
	public OcrmFMKPipelineHisService(){
		JPABaseDAO<OcrmFMkPipelineBackHis,Long> baseDao = new JPABaseDAO<OcrmFMkPipelineBackHis,Long>(OcrmFMkPipelineBackHis.class);
		super.setBaseDAO(baseDao);
	}
	
}
