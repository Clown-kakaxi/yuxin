package com.yuchengtech.bcrm.finService.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.finService.model.OcrmFFinTarget;
import com.yuchengtech.bcrm.finService.service.OcrmFFinTargetService;
import com.yuchengtech.bob.common.CommonAction;

@Action("/ocrmFFinTarget")
public class OcrmFFinTargetAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private OcrmFFinTargetService ocrmFFinTargetService;
	
	@Autowired
	public void init(){
		model = new OcrmFFinTarget();
		setCommonService(ocrmFFinTargetService);
	}
	
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		
		String demandId = request.getParameter("DEMAND_ID");
		StringBuffer sb = new StringBuffer("select t.* from OCRM_F_FIN_TARGET t where t.DEMAND_ID = '"+demandId+"'");
		
		SQL = sb.toString();
		datasource = ds;
	}
	
	/**
     * batch delete 
     */
    public String batchDestroy(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String ids = request.getParameter("ids");
        ocrmFFinTargetService.batchRemove(ids);
        return "success";
    }
	
}
