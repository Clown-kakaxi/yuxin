package com.yuchengtech.bcrm.custview.action;

import java.sql.Timestamp;
import java.util.Date;
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
import com.yuchengtech.bcrm.custview.model.AcrmACardUsed;
import com.yuchengtech.bcrm.custview.service.AcrmACardUsedService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@Action("/acrmACardUsed")
public class AcrmACardUsedAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
    private  AcrmACardUsedService  acrmACardUsedService;
	
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	@Autowired
	public void init(){
	  	model = new AcrmACardUsed(); 
		setCommonService(acrmACardUsedService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
	
	//信息查询
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId=request.getParameter("custId");
		StringBuilder sb = new StringBuilder(" select * from ACRM_A_CARD_USED where CUST_ID= '"+custId+"'");
		SQL=sb.toString();
		datasource = ds;
	}
	
	public DefaultHttpHeaders saveData(){
		 ActionContext ctx = ActionContext.getContext();
		 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	   	 //新建
	   	 if(((AcrmACardUsed)model).getId() == null){
	   		String custId=request.getParameter("custId");
				((AcrmACardUsed)model).setCustId(custId);
			    ((AcrmACardUsed)model).setUpdateDate(new Timestamp(new Date().getTime()));
			    acrmACardUsedService.save(model);
			//修改
	   	 } else if(((AcrmACardUsed)model).getId()!=null){
	   		String id = ((AcrmACardUsed)model).getId().toString();
				String jql = "update AcrmACardUsed u set u.cardType=:cardType,u.outAtm=:outAtm,u.outUsed=:outUsed,u.innerSameAtm=:innerSameAtm,u.innerUsed=:innerUsed,u.innerDiffAtm=:innerDiffAtm,u.updateDate=:updateDate where u.id='"+id+"'";
		        Map<String,Object> values = new HashMap<String,Object>();
		        values.put("cardType",((AcrmACardUsed)model).getCardType());
		        values.put("outAtm",((AcrmACardUsed)model).getOutAtm());
		        values.put("outUsed",((AcrmACardUsed)model).getOutUsed());
		        values.put("innerSameAtm",((AcrmACardUsed)model).getInnerSameAtm());
		        values.put("innerUsed",((AcrmACardUsed)model).getInnerUsed());
		        values.put("innerDiffAtm",((AcrmACardUsed)model).getInnerDiffAtm());
		        values.put("updateDate", new Date());
		        acrmACardUsedService.batchUpdateByName(jql, values);
   	     }
		return new DefaultHttpHeaders("success");
	}
	
	//删除
    public DefaultHttpHeaders batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	acrmACardUsedService.batchDel(request);
		return new DefaultHttpHeaders("success");
    }
}
