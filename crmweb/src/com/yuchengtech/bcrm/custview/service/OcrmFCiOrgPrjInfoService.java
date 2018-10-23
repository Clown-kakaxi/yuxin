package com.yuchengtech.bcrm.custview.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.OcrmFCiOrgPrjInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiOrgPrjInfoService extends CommonService {

	public OcrmFCiOrgPrjInfoService(){
		JPABaseDAO<OcrmFCiOrgPrjInfo, Long>  baseDAO=new JPABaseDAO<OcrmFCiOrgPrjInfo, Long>(OcrmFCiOrgPrjInfo.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	// 删除
	public void batchDel(HttpServletRequest request) {
		String id = request.getParameter("id");
		this.em.createNativeQuery("delete from OCRM_F_CI_ORG_PRJ_INFO where ID in ("+id+")").executeUpdate();
	}
}
