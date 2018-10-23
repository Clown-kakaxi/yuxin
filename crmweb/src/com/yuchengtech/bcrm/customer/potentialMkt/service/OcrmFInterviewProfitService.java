package com.yuchengtech.bcrm.customer.potentialMkt.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewProfit;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFInterviewProfitService extends CommonService{
	 private HttpServletRequest request;
	 public OcrmFInterviewProfitService(){
		 JPABaseDAO<OcrmFInterviewProfit,String> baseDao = new JPABaseDAO<OcrmFInterviewProfit,String>(OcrmFInterviewProfit.class);
	 	 super.setBaseDAO(baseDao);
	 }
	public Object save(Object obj) {
		OcrmFInterviewProfit profit = (OcrmFInterviewProfit)obj;
		 String year = "";
		 String yearEnd = "";
		 if(profit.getPYears() != null && !profit.getPYears().isEmpty()){
			 year = profit.getPYears().substring(0, 7);
		 }
		 if(profit.getPYearsEnd() != null && !profit.getPYearsEnd().isEmpty()){
			 yearEnd = profit.getPYearsEnd().substring(0,7);
		 }
		 ActionContext ctx = ActionContext.getContext();
	     request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		 if("0".equals(request.getParameter("flag"))){//新增
			 profit.setId(null);
			 profit.setPYears(year);
			 profit.setPYearsEnd(yearEnd);
		 }else{
			 profit.setPYears(year);
			 profit.setPYearsEnd(yearEnd);
		 }
			return super.save(profit);
	    }
}
