package com.yuchengtech.bcrm.serviceManage.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.serviceManage.model.OcrmFSeGoodsHis;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * 礼品领取service
 * 
 * @author luyy
 * @since 2014-06-11
 */
@Service
public class OcrmFSeGoodsHisService extends CommonService {
   
   public OcrmFSeGoodsHisService(){
	   JPABaseDAO<OcrmFSeGoodsHis, Long>  baseDAO=new JPABaseDAO<OcrmFSeGoodsHis, Long>(OcrmFSeGoodsHis.class);  
		super.setBaseDAO(baseDAO);
	}
   
}
