package com.yuchengtech.bcrm.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.service.WatingAppUserInfoService;
import com.yuchengtech.bcrm.system.model.OcrmFSeTitle;
import com.yuchengtech.bob.common.CommonAction;

@SuppressWarnings("serial")
@Action("/watingappuserinfoaction")
public class WatingAppUserInfoAction extends CommonAction{
    @Autowired
    private WatingAppUserInfoService watingAppUserInfoService ;
    @Autowired
	public void init(){
	  	model = new OcrmFSeTitle(); 
		setCommonService(watingAppUserInfoService);
	}
 	
 	public String loadTitleRs(){
 		ActionContext ctx = ActionContext.getContext();
 		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
 	String role = request.getParameter("role");
 		json = watingAppUserInfoService.loadTitleRs(role);
 		return "success";
 	}
 	
}
