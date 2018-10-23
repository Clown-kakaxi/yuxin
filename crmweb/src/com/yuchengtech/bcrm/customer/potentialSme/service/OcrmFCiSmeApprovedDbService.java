package com.yuchengtech.bcrm.customer.potentialSme.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktApprovedDb;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiSmeApprovedDbService extends CommonService {

	public OcrmFCiSmeApprovedDbService(){
		JPABaseDAO<OcrmFCiMktApprovedDb,Long> baseDao = new JPABaseDAO<OcrmFCiMktApprovedDb,Long>(OcrmFCiMktApprovedDb.class);
		super.setBaseDAO(baseDao);
	}
}
