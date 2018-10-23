package com.yuchengtech.bcrm.custview.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.AcrmACardUsed;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service("/acrmACardUsed")
public class AcrmACardUsedService extends CommonService {

	public AcrmACardUsedService(){
		
		JPABaseDAO<AcrmACardUsed, Long>  baseDAO=new JPABaseDAO<AcrmACardUsed, Long>(AcrmACardUsed.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	// 删除
	public void batchDel(HttpServletRequest request) {
		String id = request.getParameter("id");
		this.em.createNativeQuery("delete from ACRM_A_CARD_USED where ID in ("+id+")").executeUpdate();
	}
}
