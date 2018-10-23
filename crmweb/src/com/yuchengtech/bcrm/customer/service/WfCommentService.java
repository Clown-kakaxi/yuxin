package com.yuchengtech.bcrm.customer.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.model.OcrmFCiReviewContent;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class WfCommentService extends CommonService {

	public WfCommentService(){
		JPABaseDAO<OcrmFCiReviewContent, Long>  baseDAO=new JPABaseDAO<OcrmFCiReviewContent, Long>(OcrmFCiReviewContent.class);  
		   super.setBaseDAO(baseDAO);
	}
}
