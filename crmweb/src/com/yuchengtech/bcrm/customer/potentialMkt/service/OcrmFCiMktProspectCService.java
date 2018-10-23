package com.yuchengtech.bcrm.customer.potentialMkt.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktProspectC;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiMktProspectCService extends CommonService{
	public OcrmFCiMktProspectCService(){
		JPABaseDAO<OcrmFCiMktProspectC,Long> baseDao = new JPABaseDAO<OcrmFCiMktProspectC,Long>(OcrmFCiMktProspectC.class);
		super.setBaseDAO(baseDao);
	}
}
