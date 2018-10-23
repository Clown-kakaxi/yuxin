package com.yuchengtech.bcrm.customer.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.model.OcrmFCiAttentionCustInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class CustConcernService extends CommonService{
	
    public CustConcernService(){
	    JPABaseDAO<OcrmFCiAttentionCustInfo, Long>  baseDAO=new JPABaseDAO<OcrmFCiAttentionCustInfo, Long>(OcrmFCiAttentionCustInfo.class);  
	    super.setBaseDAO(baseDAO);
    }
	   
	// 删除
	public void batchDel(HttpServletRequest request) {
		String id = request.getParameter("id");
		this.em.createNativeQuery("delete from OCRM_F_CI_ATTENTION_CUST_INFO where ID in ("+id+")").executeUpdate();
	}

}
