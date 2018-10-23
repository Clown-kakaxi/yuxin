package com.yuchengtech.bcrm.customer.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiRelationAnalNy;
import com.yuchengtech.bcrm.customer.service.OcrmFCiRelationAnalNyService;
import com.yuchengtech.bob.common.CommonAction;

@Action("/ocrmFCiRelationAnalNyAction")
public class OcrmFCiRelationAnalNyAction extends CommonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private OcrmFCiRelationAnalNyService ocrmFCiRelationAnalNyService;
	
	@Autowired
	public void init() {
		model = new OcrmFCiRelationAnalNy();
		setCommonService(ocrmFCiRelationAnalNyService);
	}
	

	/**
	 * 保存 客户关系计划表_今年Wallet Size分析
	 * @return
	 */
	public DefaultHttpHeaders saveData()  throws Exception{
		try{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String planidstr="";
		if(!"".equals(request.getParameter("creatplanid"))&&request.getParameter("creatplanid")!=null){
			planidstr=request.getParameter("creatplanid");
		}
		if("".equals(((OcrmFCiRelationAnalNy)model).getId())||((OcrmFCiRelationAnalNy)model).getId()==null){
			((OcrmFCiRelationAnalNy)model).setPlanId(planidstr);
			ocrmFCiRelationAnalNyService.save(model);
			
		}else{
			ocrmFCiRelationAnalNyService.updateOcrmFCiRelationAnalNy((OcrmFCiRelationAnalNy)model);
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
			ocrmFCiRelationAnalNyService.remove(idstr);
		return idstr;
	}
}
