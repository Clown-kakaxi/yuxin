package com.yuchengtech.bcrm.calc.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.calc.model.OcrmORmbRate;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class CalcPriceQueryRmbRateService extends CommonService {
	
	public CalcPriceQueryRmbRateService(){
		JPABaseDAO<OcrmORmbRate,Long> baseDao = new JPABaseDAO<OcrmORmbRate,Long>(OcrmORmbRate.class);
		super.setBaseDAO(baseDao);
	}
}
