package com.yuchengtech.bcrm.customer.potentialMkt.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewForexlimit;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFInterviewForexlimitService extends CommonService{
	 private HttpServletRequest request;
	 public OcrmFInterviewForexlimitService(){
		 JPABaseDAO<OcrmFInterviewForexlimit,String> baseDao = new JPABaseDAO<OcrmFInterviewForexlimit,String>(OcrmFInterviewForexlimit.class);
	 	 super.setBaseDAO(baseDao);
	 }
	public Object save(Object obj) {
		OcrmFInterviewForexlimit loanbank = (OcrmFInterviewForexlimit)obj;
		 ActionContext ctx = ActionContext.getContext();
	     request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		 if("0".equals(request.getParameter("flag"))){//新增
			 loanbank.setId(null);
		 }
			return super.save(loanbank);
	    }
}
