package com.yuchengtech.bcrm.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiGrade;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewProfit;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class AcrmFCiGradeService extends CommonService {
	private HttpServletRequest request;

	public AcrmFCiGradeService() {
		JPABaseDAO<AcrmFCiGradeService, Long> baseDao = new JPABaseDAO<AcrmFCiGradeService, Long>(
				AcrmFCiGradeService.class);
		super.setBaseDAO(baseDao);
	}
	public void updatePotCusInfo(String sql){
		this.em.createNativeQuery(sql).executeUpdate();
	}


}
