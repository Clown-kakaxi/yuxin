package com.yuchengtech.crm.comsit.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.yuchengtech.crm.comsit.model.OcrmCompositFunction;
import com.yuchengtech.crm.comsit.service.CompositFunctionService;

@SuppressWarnings("serial")
@Action("composit")
@Results({
    @Result(name="success",type="redirectAction", params = {"actionName" , "workplatnotice", "success", "false"})
})
public class CompositFunctionAction  extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{

	private OcrmCompositFunction wn = new OcrmCompositFunction();
	
	@Autowired
	private CompositFunctionService cfs;
	
	public HttpHeaders create() {
		cfs.save(wn);
		return new DefaultHttpHeaders("success");
	}
	
	public Object getModel() {
		return wn;
	}

	public void validate() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
