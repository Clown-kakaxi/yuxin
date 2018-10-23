package com.yuchengtech.bcrm.customer.action;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.icu.text.SimpleDateFormat;
import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiRelationplanPattern;
import com.yuchengtech.bcrm.customer.service.CustomerRelationshipScheduleMethodsService;
import com.yuchengtech.bob.common.CommonAction;



@Action("/customerRelationshipScheduleMe")
public class CustomerRelationshipScheduleMeAction extends CommonAction {
private static final long serialVersionUID = 1L;
	
	@Autowired
	private CustomerRelationshipScheduleMethodsService customerRelationshipScheduleMethodsService;
	
	@Autowired
	public void init() {
		model = new OcrmFCiRelationplanPattern();
		setCommonService(customerRelationshipScheduleMethodsService);
	}
	/**
	 * 保存 客户关系计划表_交易模式
	 * @return
	 */
	public DefaultHttpHeaders saveData()  throws Exception{
		try{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
		if("".equals(((OcrmFCiRelationplanPattern)model).getPlanId())||((OcrmFCiRelationplanPattern)model).getPlanId()==null){
			String creatPlanid="";
			creatPlanid=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			((OcrmFCiRelationplanPattern)model).setPlanId(creatPlanid);
			/*customerRelationshipScheduleMethodsService.save(model);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("creatPlanid", creatPlanid);
			this.setJson(map);*/
			customerRelationshipScheduleMethodsService.saveOcrmFCiRelationplanPattern((OcrmFCiRelationplanPattern)model, response);
		}else{
			customerRelationshipScheduleMethodsService.updateOcrmFCiRelationplanPattern((OcrmFCiRelationplanPattern)model);	 
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new DefaultHttpHeaders("success");
	}
	

	@Override
	public String destroy() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String idstr="";
			if(!"".equals(request.getParameter("id"))&&request.getParameter("id")!=null){
				idstr=request.getParameter("id");
			}
			customerRelationshipScheduleMethodsService.remove(idstr);
		return idstr;
	}
	
}
