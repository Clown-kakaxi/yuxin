package com.yuchengtech.bcrm.customer.service;

import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.model.OcrmFCiAttentionCustInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

@Service
public class CustConcernOperService extends CommonService{
	
	   public CustConcernOperService(){
		   JPABaseDAO<OcrmFCiAttentionCustInfo, Long>  baseDAO=new JPABaseDAO<OcrmFCiAttentionCustInfo, Long>(OcrmFCiAttentionCustInfo.class);  
		   super.setBaseDAO(baseDAO);
	   }
	   
	   public Object save(Object model,String sss) {
		   OcrmFCiAttentionCustInfo ocrmFCiAttentionCustInfo=(OcrmFCiAttentionCustInfo)model;
		   AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   String currenUserId = auth.getUserId();
			   ocrmFCiAttentionCustInfo.setCustId(sss);
			   ocrmFCiAttentionCustInfo.setCreateDate(new Date());
			   ocrmFCiAttentionCustInfo.setUserId(currenUserId);

		
	    return super.save(ocrmFCiAttentionCustInfo);

	}
}
