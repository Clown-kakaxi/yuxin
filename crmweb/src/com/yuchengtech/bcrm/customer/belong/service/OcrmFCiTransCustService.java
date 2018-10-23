package com.yuchengtech.bcrm.customer.belong.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.belong.model.OcrmFCiTransCust;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiTransCustService extends CommonService{
	public OcrmFCiTransCustService(){
		JPABaseDAO<OcrmFCiTransCust,Long> baseDao = new JPABaseDAO<OcrmFCiTransCust,Long>(OcrmFCiTransCust.class);
		super.setBaseDAO(baseDao);
	}
}
