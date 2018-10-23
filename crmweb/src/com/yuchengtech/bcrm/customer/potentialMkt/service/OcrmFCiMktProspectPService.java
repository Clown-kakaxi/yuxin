package com.yuchengtech.bcrm.customer.potentialMkt.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktProspectP;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiMktProspectPService extends CommonService{
	public OcrmFCiMktProspectPService(){
		JPABaseDAO<OcrmFCiMktProspectP,Long> baseDao = new JPABaseDAO<OcrmFCiMktProspectP,Long>(OcrmFCiMktProspectP.class);
		super.setBaseDAO(baseDao);
	}
}
