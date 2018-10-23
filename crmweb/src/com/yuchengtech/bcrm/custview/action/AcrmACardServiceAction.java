package com.yuchengtech.bcrm.custview.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmACardService;
import com.yuchengtech.bcrm.custview.service.AcrmACardServiceService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@Action("/acrmACardService")
public class AcrmACardServiceAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
    private  AcrmACardServiceService  acrmACardServiceService;
	
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	@Autowired
	public void init(){
	  	model = new AcrmACardService(); 
		setCommonService(acrmACardServiceService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
	
	//信息查询
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId=request.getParameter("custId");
		StringBuilder sb = new StringBuilder(" select * from ACRM_A_CARD_SERVICE where CUST_ID= '"+custId+"'");
		SQL=sb.toString();
		datasource = ds;
	}
	
	//信息保存
	public DefaultHttpHeaders saveData(){
		ActionContext ctx = ActionContext.getContext();
	   	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	   	//新建
	   	if(((AcrmACardService)model).getId() == null){
	   		String custId=request.getParameter("custId");
			((AcrmACardService)model).setCustId(custId);
		    acrmACardServiceService.save(model);
			//修改
	   	 } else if(((AcrmACardService)model).getId()!=null){
	   		String id = ((AcrmACardService)model).getId().toString();
			String jql = "update AcrmACardService s set s.insuranceStartDate=:insuranceStartDate,s.cardNo=:cardNo,s.memberCardNo=:memberCardNo,s.memberUsingDate=:memberUsingDate where s.id='"+id+"'";
	        Map<String,Object> values = new HashMap<String,Object>();
	        values.put("insuranceStartDate",((AcrmACardService)model).getInsuranceStartDate());
	        values.put("cardNo",((AcrmACardService)model).getCardNo());
	        values.put("memberCardNo",((AcrmACardService)model).getMemberCardNo());
	        values.put("memberUsingDate",((AcrmACardService)model).getMemberUsingDate());
	        acrmACardServiceService.batchUpdateByName(jql, values);
	   	 }
        return new DefaultHttpHeaders("success");
	}
	
	//删除
    public DefaultHttpHeaders batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	acrmACardServiceService.batchDel(request);
		return new DefaultHttpHeaders("success");
    }
}
