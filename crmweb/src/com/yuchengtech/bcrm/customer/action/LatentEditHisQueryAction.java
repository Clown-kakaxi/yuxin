package com.yuchengtech.bcrm.customer.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
/**
 * 个金潜在客户修改历史查询
 * @author mamusa
 * @date2016-01-08
 *
 */
@Action("/latentEditHisQueryAction")
public class LatentEditHisQueryAction extends CommonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String cusId = request.getParameter("cusId");
    	StringBuffer sb=new StringBuffer("SELECT H.CUS_ID,M.USER_NAME CUS_FOR_MGR,A.USER_NAME EDIT_USER,to_char(to_date(h.edit_date,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd') EDIT_DATE,H.EDIT_BEFORE_ITEM,H.EDIT_BEFORE_CONTENT,H.EDIT_AFTER_ITEM,H.EDIT_AFTER_CONTENT FROM ACRM_F_CI_POT_CUS_CHANGEHIS H ");
    	sb.append(" LEFT JOIN ADMIN_AUTH_ACCOUNT M ON H.CUS_FOR_MGR=M.ACCOUNT_NAME");
    	sb.append(" LEFT JOIN ADMIN_AUTH_ACCOUNT A ON H.EDIT_USER=A.ACCOUNT_NAME");
    	sb.append(" WHERE 1=1 and H.CUS_ID='"+cusId+"'");
    	setPrimaryKey("EDIT_DATE desc");
    	
    	 addOracleLookup("EDIT_BEFORE_CONTENT", "XD000353");
    	 addOracleLookup("EDIT_BEFORE_CONTENT", "XD000025");
    	 addOracleLookup("EDIT_BEFORE_CONTENT", "XD000040");
    	 addOracleLookup("EDIT_BEFORE_CONTENT", "PAR0400044");
    	 addOracleLookup("EDIT_BEFORE_CONTENT", "HYFL");
    	 addOracleLookup("EDIT_AFTER_CONTENT", "XD000353");
    	 addOracleLookup("EDIT_AFTER_CONTENT", "XD000025");
    	 addOracleLookup("EDIT_AFTER_CONTENT", "XD000040");
    	 addOracleLookup("EDIT_AFTER_CONTENT", "PAR0400044");
    	 addOracleLookup("EDIT_AFTER_CONTENT", "HYFL");
    	SQL=sb.toString();
		datasource=ds;
	}
	
   
}
