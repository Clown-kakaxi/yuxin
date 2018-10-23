package com.yuchengtech.bcrm.customer.potentialMkt.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktChangeP;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiMktChangePService extends CommonService{
	public OcrmFCiMktChangePService(){
		JPABaseDAO<OcrmFCiMktChangeP,Long> baseDao = new JPABaseDAO<OcrmFCiMktChangeP,Long>(OcrmFCiMktChangeP.class);
		super.setBaseDAO(baseDao);
	}
}
