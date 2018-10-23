package com.yuchengtech.bcrm.custview.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.AcrmFCiCustLawInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class AcrmFCiCustLawInfoService extends CommonService {
	public AcrmFCiCustLawInfoService(){
		JPABaseDAO<AcrmFCiCustLawInfo,Long> baseDao = new JPABaseDAO<AcrmFCiCustLawInfo,Long>(AcrmFCiCustLawInfo.class);
		super.setBaseDAO(baseDao);
	}
	
	// 删除
	public void batchDel(HttpServletRequest request) {
		String id = request.getParameter("id");
		this.em.createNativeQuery("delete from ACRM_F_CI_CUST_LAW_INFO where ID in ("+id+")").executeUpdate();
	}

}

