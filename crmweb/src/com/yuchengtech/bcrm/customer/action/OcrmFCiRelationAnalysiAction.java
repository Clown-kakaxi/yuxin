package com.yuchengtech.bcrm.customer.action;



import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;







import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiRelationAnalysi;
import com.yuchengtech.bcrm.customer.service.OcrmFCiRelationAnalysiService;
import com.yuchengtech.bob.common.CommonAction;

@Action("/ocrmFCiRelationAnalysiAction")
public class OcrmFCiRelationAnalysiAction extends CommonAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private OcrmFCiRelationAnalysiService ocrmFCiRelationAnalysiService;
	
	@Autowired
	public void init() {
		model = new OcrmFCiRelationAnalysi();
		setCommonService(ocrmFCiRelationAnalysiService);
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
		if("".equals(((OcrmFCiRelationAnalysi)model).getId())||((OcrmFCiRelationAnalysi)model).getId()==null){
			((OcrmFCiRelationAnalysi)model).setPlanId(planidstr);
			ocrmFCiRelationAnalysiService.save(model);
			
		}else{
			ocrmFCiRelationAnalysiService.updateOcrmFCiRelationAnalysi((OcrmFCiRelationAnalysi)model);
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
		ocrmFCiRelationAnalysiService.remove(idstr);
		return idstr;
	}
	
	
}
