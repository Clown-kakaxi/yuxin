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

/**
 * 客户经理视图-客户持有产品信息
 * @author geyu
 * 2014-8-17
 */
@Action("/mgrCustProdInfoQuery")
public class MgrCustProdInfoQueryAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String mgr = request.getParameter("mgrId");
    	String custId = request.getParameter("cust_id");
		StringBuffer sb=new StringBuffer("SELECT T.CUST_ID,T.CUST_NAME FROM ACRM_F_CI_CUSTOMER T "+
						"LEFT JOIN   OCRM_F_CI_BELONG_CUSTMGR  T1 ON T.CUST_ID =T1.CUST_ID "+
						"WHERE (T1.MAIN_TYPE=1 OR T1.MAIN_TYPE IS NULL) AND T1.MGR_ID='"+mgr+"'");
		SQL=sb.toString();
		datasource=ds;
		setPrimaryKey("T.CUST_ID desc ");
	}

}
