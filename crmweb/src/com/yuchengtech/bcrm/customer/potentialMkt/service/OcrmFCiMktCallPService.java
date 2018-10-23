package com.yuchengtech.bcrm.customer.potentialMkt.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktCallP;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiMktCallPService extends CommonService{
	public OcrmFCiMktCallPService(){
		JPABaseDAO<OcrmFCiMktCallP,Long> baseDao = new JPABaseDAO<OcrmFCiMktCallP,Long>(OcrmFCiMktCallP.class);
		super.setBaseDAO(baseDao);
	}
}
