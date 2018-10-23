package com.yuchengtech.bcrm.customer.potentialMkt.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktCallC;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiMktCallCService extends CommonService{
	public OcrmFCiMktCallCService(){
		JPABaseDAO<OcrmFCiMktCallC,Long> baseDao = new JPABaseDAO<OcrmFCiMktCallC,Long>(OcrmFCiMktCallC.class);
		super.setBaseDAO(baseDao);
	}
}
