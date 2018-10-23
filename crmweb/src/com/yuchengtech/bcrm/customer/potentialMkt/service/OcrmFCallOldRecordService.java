package com.yuchengtech.bcrm.customer.potentialMkt.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCallOldRecord;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCallOldRecordService extends CommonService{
	public OcrmFCallOldRecordService(){
		JPABaseDAO<OcrmFCallOldRecord,Long> baseDao = new JPABaseDAO<OcrmFCallOldRecord,Long>(OcrmFCallOldRecord.class);
		super.setBaseDAO(baseDao);
	}

	public Object delData(String id) {
    	return this.em.createNativeQuery("delete from ocrm_f_call_old_record where id ='"+id+"'").executeUpdate();
    }
}
