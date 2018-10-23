package com.yuchengtech.bcrm.customer.potentialMkt.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktIntentP;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiMktIntentPService extends CommonService{
	public OcrmFCiMktIntentPService(){
		JPABaseDAO<OcrmFCiMktIntentP,Long> baseDao = new JPABaseDAO<OcrmFCiMktIntentP,Long>(OcrmFCiMktIntentP.class);
		super.setBaseDAO(baseDao);
	}
}
