package com.yuchengtech.bcrm.customer.potentialMkt.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewProsale;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFInterviewProsaleService extends CommonService{
	 private HttpServletRequest request;
	 public OcrmFInterviewProsaleService(){
		 JPABaseDAO<OcrmFInterviewProsale,String> baseDao = new JPABaseDAO<OcrmFInterviewProsale,String>(OcrmFInterviewProsale.class);
	 	 super.setBaseDAO(baseDao);
	 }
	public Object save(Object obj) {
		OcrmFInterviewProsale prosale = (OcrmFInterviewProsale)obj;
		 ActionContext ctx = ActionContext.getContext();
	     request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		 if("0".equals(request.getParameter("flag"))){//新增
			 prosale.setId(null);
		 }
			return super.save(prosale);
	    }
}
