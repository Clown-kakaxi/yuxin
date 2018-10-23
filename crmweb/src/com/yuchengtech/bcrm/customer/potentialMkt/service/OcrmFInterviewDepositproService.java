package com.yuchengtech.bcrm.customer.potentialMkt.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewDepositpro;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFInterviewDepositproService extends CommonService{
	 private HttpServletRequest request;
	 public OcrmFInterviewDepositproService(){
		 JPABaseDAO<OcrmFInterviewDepositpro,String> baseDao = new JPABaseDAO<OcrmFInterviewDepositpro,String>(OcrmFInterviewDepositpro.class);
	 	 super.setBaseDAO(baseDao);
	 }
	public Object save(Object obj) {
		OcrmFInterviewDepositpro fixedasset = (OcrmFInterviewDepositpro)obj;
		 ActionContext ctx = ActionContext.getContext();
	     request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		 if("0".equals(request.getParameter("flag"))){//新增
			 fixedasset.setId(null);
		 }
			return super.save(fixedasset);
	    }
}
