package com.yuchengtech.bcrm.customer.level.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.level.model.AcrmACiCardApply;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class AcrmACiCardApplyService extends CommonService{
	public AcrmACiCardApplyService(){
		JPABaseDAO<AcrmACiCardApply,Long> baseDao = new JPABaseDAO<AcrmACiCardApply,Long>(AcrmACiCardApply.class);
		super.setBaseDAO(baseDao);
	}
}
