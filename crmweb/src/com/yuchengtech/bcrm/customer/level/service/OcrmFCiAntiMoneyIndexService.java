package com.yuchengtech.bcrm.customer.level.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.level.model.OcrmFCiAntiMoneyIndex;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
@SuppressWarnings("rawtypes")
public class OcrmFCiAntiMoneyIndexService extends CommonService{
	public OcrmFCiAntiMoneyIndexService(){
		JPABaseDAO<OcrmFCiAntiMoneyIndex,Long> baseDao = new JPABaseDAO<OcrmFCiAntiMoneyIndex,Long>(OcrmFCiAntiMoneyIndex.class);
		super.setBaseDAO(baseDao);
	}

	public String getIndexDic(String indexDic){
		List rList = null;
		String index_value = "";
		rList = this.em.createNativeQuery(" select f_value from ocrm_sys_lookup_item i where i.f_lookup_id = 'INDEX_TYPE' AND F_CODE = '"+indexDic+"' ").getResultList();
		if (rList != null && rList.size() > 0) {
			for (int i = 0; i < rList.size(); i++) {
				index_value = rList.get(i).toString();
			}
		}
		return index_value;	
	}
}
