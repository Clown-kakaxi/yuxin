package com.yuchengtech.bcrm.customer.potentialMkt.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewDepositbank;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFInterviewDepositbankService extends CommonService{
	 private HttpServletRequest request;
	 public OcrmFInterviewDepositbankService(){
		 JPABaseDAO<OcrmFInterviewDepositbank,String> baseDao = new JPABaseDAO<OcrmFInterviewDepositbank,String>(OcrmFInterviewDepositbank.class);
	 	 super.setBaseDAO(baseDao);
	 }
	public Object save(Object obj) {
		 OcrmFInterviewDepositbank bank = (OcrmFInterviewDepositbank)obj;
			 ActionContext ctx = ActionContext.getContext();
		     request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			 if("0".equals(request.getParameter("flag"))){//新增
				 bank.setId(null);
			 }
				return super.save(bank);
		    }
}
