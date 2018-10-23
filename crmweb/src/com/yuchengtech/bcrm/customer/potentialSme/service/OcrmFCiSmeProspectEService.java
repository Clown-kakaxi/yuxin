package com.yuchengtech.bcrm.customer.potentialSme.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktProspectC;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiSmeProspectEService extends CommonService{
	
	public OcrmFCiSmeProspectEService(){
		JPABaseDAO<OcrmFCiMktProspectC,Long> baseDao = new JPABaseDAO<OcrmFCiMktProspectC,Long>(OcrmFCiMktProspectC.class);
		super.setBaseDAO(baseDao);
	}
	
	public void goBack(String id){
		OcrmFCiMktProspectC oProspectC = (OcrmFCiMktProspectC) this.find(Long.parseLong(id));
		if(oProspectC != null){
			oProspectC.setIfPipeline("2");
			oProspectC.setPipelineDate(null);
			oProspectC.setRecordDate(new Date());
			this.save(oProspectC);
		}
	}
}
