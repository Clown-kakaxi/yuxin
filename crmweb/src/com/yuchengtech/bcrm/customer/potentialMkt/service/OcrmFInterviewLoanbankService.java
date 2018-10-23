package com.yuchengtech.bcrm.customer.potentialMkt.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewLoanbank;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFInterviewLoanbankService extends CommonService{
	 private HttpServletRequest request;
	 public OcrmFInterviewLoanbankService(){
		 JPABaseDAO<OcrmFInterviewLoanbank,String> baseDao = new JPABaseDAO<OcrmFInterviewLoanbank,String>(OcrmFInterviewLoanbank.class);
	 	 super.setBaseDAO(baseDao);
	 }
	public Object save(Object obj) {
		OcrmFInterviewLoanbank loanbank = (OcrmFInterviewLoanbank)obj;
		 ActionContext ctx = ActionContext.getContext();
	     request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		 if("0".equals(request.getParameter("flag"))){//新增
			 loanbank.setId(null);
		 }
			return super.save(loanbank);
	    }
}
