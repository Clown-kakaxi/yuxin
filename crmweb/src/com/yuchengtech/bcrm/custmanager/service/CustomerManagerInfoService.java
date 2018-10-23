package com.yuchengtech.bcrm.custmanager.service;


import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custmanager.model.OcrmFCmCustMgrInfo;
import com.yuchengtech.bcrm.custmanager.model.OcrmFCmCustMgrInfoReview;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;


/**
 * 客户经理信息处理
 * @author geyu
 * 2014-7-7
 */
@Service
public class CustomerManagerInfoService extends CommonService{
	
	@SuppressWarnings("unchecked")
	public CustomerManagerInfoService(){
		JPABaseDAO<OcrmFCmCustMgrInfo, Long> baseDao  = new JPABaseDAO<OcrmFCmCustMgrInfo, Long>(OcrmFCmCustMgrInfo.class);
		super.setBaseDAO(baseDao);
	}
	
	/**
	 * 保存修改后需审核的客户经理信息
	 */
	public void saveReview(OcrmFCmCustMgrInfoReview cmCustMgrInfoReview){
		super.save(cmCustMgrInfoReview);
	}

}