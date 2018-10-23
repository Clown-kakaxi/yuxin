package com.yuchengtech.bcrm.custview.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.AcrmFCiChannelAnalysi;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class AcrmFCiChannelAnalysiService extends CommonService {
	
	public AcrmFCiChannelAnalysiService(){
		JPABaseDAO<AcrmFCiChannelAnalysi,Long> baseDao = new JPABaseDAO<AcrmFCiChannelAnalysi,Long>(AcrmFCiChannelAnalysi.class);
		super.setBaseDAO(baseDao);
	}
}
