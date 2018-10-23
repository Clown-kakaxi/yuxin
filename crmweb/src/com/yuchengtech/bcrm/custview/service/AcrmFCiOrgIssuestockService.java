package com.yuchengtech.bcrm.custview.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.AcrmFCiOrgIssuestock;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class AcrmFCiOrgIssuestockService extends CommonService {
 
	public AcrmFCiOrgIssuestockService(){
		JPABaseDAO<AcrmFCiOrgIssuestock, Long>  baseDAO=new JPABaseDAO<AcrmFCiOrgIssuestock, Long>(AcrmFCiOrgIssuestock.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	// 删除
	public void batchDel(HttpServletRequest request) {
		String issueStockId = request.getParameter("issueStockId");
		this.em.createNativeQuery("delete from ACRM_F_CI_ORG_ISSUESTOCK where ISSUE_STOCK_ID in ("+issueStockId+")").executeUpdate();
	}
}
