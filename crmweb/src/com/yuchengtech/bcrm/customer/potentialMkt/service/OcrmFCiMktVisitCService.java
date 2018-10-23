package com.yuchengtech.bcrm.customer.potentialMkt.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktVisitC;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiMktVisitCService extends CommonService{
	public OcrmFCiMktVisitCService(){
		JPABaseDAO<OcrmFCiMktVisitC,Long> baseDao = new JPABaseDAO<OcrmFCiMktVisitC,Long>(OcrmFCiMktVisitC.class);
		super.setBaseDAO(baseDao);
	}
}
