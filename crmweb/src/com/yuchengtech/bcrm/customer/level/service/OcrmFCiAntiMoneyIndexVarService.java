package com.yuchengtech.bcrm.customer.level.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.level.model.OcrmFCiAntiMoneyIndexVar;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiAntiMoneyIndexVarService extends CommonService{
	public OcrmFCiAntiMoneyIndexVarService(){
		JPABaseDAO<OcrmFCiAntiMoneyIndexVar,Long> baseDao = new JPABaseDAO<OcrmFCiAntiMoneyIndexVar,Long>(OcrmFCiAntiMoneyIndexVar.class);
		super.setBaseDAO(baseDao);
	}
}
