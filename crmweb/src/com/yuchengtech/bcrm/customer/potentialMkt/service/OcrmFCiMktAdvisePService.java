package com.yuchengtech.bcrm.customer.potentialMkt.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktAdviseP;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiMktAdvisePService extends CommonService{
	public OcrmFCiMktAdvisePService(){
		JPABaseDAO<OcrmFCiMktAdviseP,Long> baseDao = new JPABaseDAO<OcrmFCiMktAdviseP,Long>(OcrmFCiMktAdviseP.class);
		super.setBaseDAO(baseDao);
	}
}
