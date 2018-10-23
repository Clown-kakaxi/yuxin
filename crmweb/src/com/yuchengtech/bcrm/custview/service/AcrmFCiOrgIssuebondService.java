package com.yuchengtech.bcrm.custview.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.AcrmFCiOrgIssuebond;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class AcrmFCiOrgIssuebondService extends CommonService {

	public AcrmFCiOrgIssuebondService(){
		
		JPABaseDAO<AcrmFCiOrgIssuebond, Long>  baseDAO=new JPABaseDAO<AcrmFCiOrgIssuebond, Long>(AcrmFCiOrgIssuebond.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	// 删除
	public void batchDel(HttpServletRequest request) {
		String issueBondId = request.getParameter("issueBondId");
		this.em.createNativeQuery("delete from ACRM_F_CI_ORG_ISSUEBOND where ISSUE_BOND_ID in ("+issueBondId+")").executeUpdate();
	}
}
