package com.yuchengtech.bcrm.custview.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.OcrmFCiContractInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiContractInfoService extends CommonService {

	public OcrmFCiContractInfoService(){
		JPABaseDAO<OcrmFCiContractInfo, Long>  baseDAO=new JPABaseDAO<OcrmFCiContractInfo, Long>(OcrmFCiContractInfo.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	// 删除
	public void batchDel(HttpServletRequest request) {
		String id = request.getParameter("id");
		this.em.createNativeQuery("delete from OCRM_F_CI_CONTRACT_INFO where ID in ("+id+")").executeUpdate();
	}
}
