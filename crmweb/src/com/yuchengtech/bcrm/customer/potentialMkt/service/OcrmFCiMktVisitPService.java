package com.yuchengtech.bcrm.customer.potentialMkt.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktVisitP;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiMktVisitPService extends CommonService{
	public OcrmFCiMktVisitPService(){
		JPABaseDAO<OcrmFCiMktVisitP,Long> baseDao = new JPABaseDAO<OcrmFCiMktVisitP,Long>(OcrmFCiMktVisitP.class);
		super.setBaseDAO(baseDao);
	}
}
