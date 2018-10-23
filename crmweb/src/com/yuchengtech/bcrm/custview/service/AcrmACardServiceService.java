package com.yuchengtech.bcrm.custview.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.AcrmACardService;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class AcrmACardServiceService extends CommonService {

	public AcrmACardServiceService(){
		
		JPABaseDAO<AcrmACardService, Long>  baseDAO=new JPABaseDAO<AcrmACardService, Long>(AcrmACardService.class);  
		   super.setBaseDAO(baseDAO);
	}
	// 删除
	public void batchDel(HttpServletRequest request) {
		String id = request.getParameter("id");
		this.em.createNativeQuery("delete from ACRM_A_CARD_SERVICE where ID in ('"+id+"')").executeUpdate();
	}
}
