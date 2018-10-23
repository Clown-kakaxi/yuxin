package com.yuchengtech.bcrm.custview.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.OcrmFCiOtherBank;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiOtherBankService extends CommonService {

	public OcrmFCiOtherBankService(){
		JPABaseDAO<OcrmFCiOtherBank, Long>  baseDAO=new JPABaseDAO<OcrmFCiOtherBank, Long>(OcrmFCiOtherBank.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	// 删除
	public void batchDel(HttpServletRequest request) {
		String mxtid = request.getParameter("mxtid");
		this.em.createNativeQuery("delete from OCRM_F_CI_OTHER_BANK where MXTID in ("+mxtid+")").executeUpdate();
	}
}
