package com.yuchengtech.bcrm.customer.potentialMkt.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewCollateral;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFInterviewCollateralService extends CommonService{
	 private HttpServletRequest request;
	 public OcrmFInterviewCollateralService(){
		 JPABaseDAO<OcrmFInterviewCollateral,String> baseDao = new JPABaseDAO<OcrmFInterviewCollateral,String>(OcrmFInterviewCollateral.class);
	 	 super.setBaseDAO(baseDao);
	 }
	public Object save(Object obj) {
		OcrmFInterviewCollateral collateral = (OcrmFInterviewCollateral)obj;
		 ActionContext ctx = ActionContext.getContext();
	     request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		 if("0".equals(request.getParameter("flag"))){//新增
			 collateral.setId(null);
		 }
			return super.save(collateral);
	    }
}
