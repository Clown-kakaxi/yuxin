package com.yuchengtech.bcrm.customer.potentialMkt.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewCreditpro;
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFInterviewCreditproService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 企商金客户营销流程 -  拜访信息页面  
 */

@SuppressWarnings("serial")
@Action("/ocrmFInterviewCreditpro")
public class OcrmFInterviewCreditproAction  extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    
    @Autowired
    private OcrmFInterviewCreditproService service;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
        	model = new OcrmFInterviewCreditpro();  
        	setCommonService(service);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		//needLog=true;
	}
    
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String sqlapp = "select c.* from OCRM_F_INTERVIEW_CREDITPRO c " +
    			" where 1=1 and c.task_number = '"+request.getParameter("ID")+"' ";
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	SQL=sb.toString();
    	datasource = ds;
	}

    /**
     * 删除
     */
    public void batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String idStr = request.getParameter("idStr");
    	String ids[] = idStr.split(",");
    	for(String id : ids){
    		service.batchUpdateByName(" delete from OcrmFInterviewCreditpro g where g.id='"+Long.parseLong(id)+"'", null);
    	}
    }
}