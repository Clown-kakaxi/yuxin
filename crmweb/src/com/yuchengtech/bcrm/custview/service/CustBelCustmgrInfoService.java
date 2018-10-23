package com.yuchengtech.bcrm.custview.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * 客户归属客户经理信息
 */

@Service
public class CustBelCustmgrInfoService extends CommonService {

	public CustBelCustmgrInfoService() {
		JPABaseDAO<OcrmFCiBelongCustmgr, Long> baseDAO = new JPABaseDAO<OcrmFCiBelongCustmgr, Long>(
				OcrmFCiBelongCustmgr.class);
		super.setBaseDAO(baseDAO);
	}
}
