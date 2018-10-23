package com.yuchengtech.bcrm.customer.potentialMkt.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktApprovedC;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiMktApprovedCService extends CommonService{
	public OcrmFCiMktApprovedCService(){
		JPABaseDAO<OcrmFCiMktApprovedC,Long> baseDao = new JPABaseDAO<OcrmFCiMktApprovedC,Long>(OcrmFCiMktApprovedC.class);
		super.setBaseDAO(baseDao);
	}
}
