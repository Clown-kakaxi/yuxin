package com.yuchengtech.bcrm.customer.potentialMkt.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewShareholder;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFInterviewShareholderService extends CommonService{
	 private HttpServletRequest request;
	 public OcrmFInterviewShareholderService(){
		 JPABaseDAO<OcrmFInterviewShareholder,String> baseDao = new JPABaseDAO<OcrmFInterviewShareholder,String>(OcrmFInterviewShareholder.class);
	 	 super.setBaseDAO(baseDao);
	 }
	public Object save(Object obj) {
		 OcrmFInterviewShareholder shareholder = (OcrmFInterviewShareholder)obj;
		 ActionContext ctx = ActionContext.getContext();
	     request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		 if("0".equals(request.getParameter("flag"))){//新增
			 shareholder.setId(null);
		 }
			return super.save(shareholder);
	    }
}
