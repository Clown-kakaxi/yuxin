package com.yuchengtech.bcrm.custview.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.wealthManager.model.OcrmFFinCustRisk;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
/**
 * 对私客户视图（风险偏好信息信息）
 * @author agile
 *
 */
@Service
public class RiskPreferInfoService extends CommonService {
	
	public RiskPreferInfoService(){
		JPABaseDAO<OcrmFFinCustRisk,Long> baseDao = new JPABaseDAO<OcrmFFinCustRisk,Long>(OcrmFFinCustRisk.class);
		super.setBaseDAO(baseDao);
	}
}
