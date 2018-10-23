package com.yuchengtech.bcrm.customer.potentialMkt.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktIntentC;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiMktIntentCService extends CommonService{
	public OcrmFCiMktIntentCService(){
		JPABaseDAO<OcrmFCiMktIntentC,Long> baseDao = new JPABaseDAO<OcrmFCiMktIntentC,Long>(OcrmFCiMktIntentC.class);
		super.setBaseDAO(baseDao);
	}
}
