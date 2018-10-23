package com.yuchengtech.bcrm.custmanager.action;

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




@SuppressWarnings("serial")
@Action("/profitRelatedShips")
public class ProfitRelaShipsAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	public void prepare(){
		
		StringBuffer sb = new StringBuffer("select c.* from ACRM_A_CI_PROF_RELATION c where 1=1 ");
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("CUST_ID");
		String custNoR = request.getParameter("CUST_NO_R");
		sb.append(" and c.cust_id = '" + custId + "' ");
		sb.append(" and c.cust_no_r!='"+custNoR+"' ");
		addOracleLookup("RELATIONSHIP", "CUS0100038");  //关联关系
		SQL = sb.toString();
		datasource = ds ;
	}
	
	
	
}
