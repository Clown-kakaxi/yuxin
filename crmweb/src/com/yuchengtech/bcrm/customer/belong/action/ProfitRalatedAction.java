package com.yuchengtech.bcrm.customer.belong.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;


/**
 * 移交申请信息查询  流程页面使用
 * @author luyy
 *@since 2014-07-08
 */

@SuppressWarnings("serial")
@Action("/profitRelate")
public class ProfitRalatedAction  extends CommonAction{
	
	  
    private HttpServletRequest request;

	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
	}
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	/**
	 * 关联表查询
	 */
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String profitId = request.getParameter("profitId");
    	StringBuffer sb = new StringBuffer("");
    	sb.append("SELECT C.* FROM ACRM_A_CI_PROF_RELATION C WHERE C.CREATE_TIMES='"+profitId+"'");
    	addOracleLookup("RELATIONSHIP", "CUS0100038");  //关联关系
    	SQL = sb.toString();
    	datasource = ds;
    	//如果排序的话。
       setPrimaryKey("C.OPERATE_TIME DESC NULLS LAST");
    }

}
