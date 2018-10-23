package com.yuchengtech.bcrm.customer.potentialMkt.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewPerRecord;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFInterviewPersonService extends CommonService {
	 private HttpServletRequest request;
	 
	 public OcrmFInterviewPersonService(){
		 JPABaseDAO<OcrmFInterviewPerRecord,String> baseDao = new JPABaseDAO<OcrmFInterviewPerRecord,String>(OcrmFInterviewPerRecord.class);
	 	 super.setBaseDAO(baseDao);
	 }
	 public Object save(Object obj) {
		 OcrmFInterviewPerRecord ocrmPerson = (OcrmFInterviewPerRecord)obj;
		 ActionContext ctx = ActionContext.getContext();
	     request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		 if("0".equals(request.getParameter("flag"))){//新增
			 ocrmPerson.setId(null);
		 }
			return super.save(ocrmPerson);
	  }

}
