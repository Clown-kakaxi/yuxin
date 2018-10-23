package com.yuchengtech.bcrm.customer.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewLoanbank;
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFInterviewLoanbankService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
@SuppressWarnings("serial")
@Action("/ocrmFInterviewLoanbankRelation")
public class OcrmFInterviewLoanbankRelationAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    
    @Autowired
    private OcrmFInterviewLoanbankService service;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
        	model = new OcrmFInterviewLoanbank();  
        	setCommonService(service);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		//needLog=true;
	}
    
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	StringBuilder sb  = new StringBuilder();
    	sb.append("select c.* from OCRM_F_INTERVIEW_LOANBANK c where 1=1 ");
    	if(!"".equals(request.getParameter("strnum"))&&request.getParameter("strnum")!=null){
    	sb.append(" and c.task_number = '"+request.getParameter("strnum")+"' ");
    	}
    	SQL=sb.toString();
    	datasource = ds;
	}
	
	 /**
     * 删除
     */
    public void batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	try {
			Connection  connection = ds.getConnection();
			Statement stmt = connection.createStatement();
			String idStr = request.getParameter("idStr");
			String ids[] = idStr.split(",");
			for(String id : ids){
				String sql="delete from OCRM_F_INTERVIEW_LOANBANK l where l.id="+id+" ";
				stmt.execute(sql);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
