package com.yuchengtech.bcrm.serviceManage.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.serviceManage.model.OcrmFSeScore;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * 客户积分管理service
 * 
 * @author luyy
 * @since 2014-06-09
 */
@Service
public class OcrmFSeScoreService extends CommonService {
   
   public OcrmFSeScoreService(){
	   JPABaseDAO<OcrmFSeScore, Long>  baseDAO=new JPABaseDAO<OcrmFSeScore, Long>(OcrmFSeScore.class);  
		super.setBaseDAO(baseDAO);
	}
   
}
