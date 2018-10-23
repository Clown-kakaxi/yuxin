package com.yuchengtech.bcrm.customer.potentialMkt.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktCaC;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiMktCaCService extends CommonService{
	public OcrmFCiMktCaCService(){
		JPABaseDAO<OcrmFCiMktCaC,Long> baseDao = new JPABaseDAO<OcrmFCiMktCaC,Long>(OcrmFCiMktCaC.class);
		super.setBaseDAO(baseDao);
	}
}
