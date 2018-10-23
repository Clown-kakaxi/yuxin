package com.yuchengtech.bcrm.serviceManage.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.serviceManage.model.OcrmFSeGoods;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * 礼品管理service
 * 
 * @author luyy
 * @since 2014-06-10
 */
@Service
public class OcrmFSeGoodsService extends CommonService {
   
   public OcrmFSeGoodsService(){
	   JPABaseDAO<OcrmFSeGoods, Long>  baseDAO=new JPABaseDAO<OcrmFSeGoods, Long>(OcrmFSeGoods.class);  
		super.setBaseDAO(baseDAO);
	}
   
}
