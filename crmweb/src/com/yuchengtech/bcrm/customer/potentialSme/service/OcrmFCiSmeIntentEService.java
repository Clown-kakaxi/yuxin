package com.yuchengtech.bcrm.customer.potentialSme.service;


import java.util.Date;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktIntentC;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiSmeIntentEService extends CommonService {
	
	public OcrmFCiSmeIntentEService(){
		JPABaseDAO<OcrmFCiMktIntentC,Long> baseDao = new JPABaseDAO<OcrmFCiMktIntentC,Long>(OcrmFCiMktIntentC.class);
		super.setBaseDAO(baseDao);
	}
	
	public void goBack(String id){
		OcrmFCiMktIntentC oC = (OcrmFCiMktIntentC) this.find(Long.parseLong(id));
		if(oC != null){
			oC.setIfSecondStep("2");
			oC.setRecordDate(new Date());
			this.save(oC);
		}
	}
}
