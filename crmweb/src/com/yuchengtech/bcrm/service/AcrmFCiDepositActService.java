package com.yuchengtech.bcrm.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.model.AcrmFCiDepositAct;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class AcrmFCiDepositActService extends CommonService {
	public AcrmFCiDepositActService(){
		JPABaseDAO<AcrmFCiDepositAct,Long> baseDAO = new JPABaseDAO<AcrmFCiDepositAct,Long>(AcrmFCiDepositAct.class);
		super.setBaseDAO(baseDAO);
	}
}
