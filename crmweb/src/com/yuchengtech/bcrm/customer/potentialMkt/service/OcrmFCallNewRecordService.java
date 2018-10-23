package com.yuchengtech.bcrm.customer.potentialMkt.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCallNewRecord;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCallNewRecordService extends CommonService{
	public OcrmFCallNewRecordService(){
		JPABaseDAO<OcrmFCallNewRecord,Long> baseDao = new JPABaseDAO<OcrmFCallNewRecord,Long>(OcrmFCallNewRecord.class);
		super.setBaseDAO(baseDao);
	}
	public Object delData(String id) {
    	return this.em.createNativeQuery("delete from ocrm_f_call_new_record where id ='"+id+"'").executeUpdate();
    }
}
