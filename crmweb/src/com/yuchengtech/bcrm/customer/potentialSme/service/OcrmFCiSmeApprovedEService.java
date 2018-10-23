package com.yuchengtech.bcrm.customer.potentialSme.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktApprovedC;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiSmeApprovedEService extends CommonService {

	public OcrmFCiSmeApprovedEService(){
		JPABaseDAO<OcrmFCiMktApprovedC,Long> baseDao = new JPABaseDAO<OcrmFCiMktApprovedC,Long>(OcrmFCiMktApprovedC.class);
		super.setBaseDAO(baseDao);
	}

}
