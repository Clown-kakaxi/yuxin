package com.yuchengtech.bcrm.calc.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.calc.model.OcrmOFtpRate;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class CalcPriceQueryFtpRateService extends CommonService {
	
	public CalcPriceQueryFtpRateService(){
		JPABaseDAO<OcrmOFtpRate, Long> baseDao = new JPABaseDAO<OcrmOFtpRate, Long>(OcrmOFtpRate.class);
        super.setBaseDAO(baseDao);
	}
	
}
