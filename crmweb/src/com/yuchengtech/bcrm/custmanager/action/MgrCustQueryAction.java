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
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 客户经理视图-管辖客户查询
 * @author geyu
 * 2014-8-9
 */
@Action("mgrCustQuery")
public class MgrCustQueryAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String mgr = request.getParameter("mgrId");
		StringBuffer sb=new StringBuffer("select c.cust_id,c.cust_name,c.ident_type,c.ident_no,c.cust_type,c.cust_level,c.current_aum," +
				"c.linkman_name,c.linkman_tel,m.institution_name as org_name,m.mgr_name " +
				"from ACRM_F_CI_CUSTOMER c left join OCRM_F_CI_BELONG_CUSTMGR m on c.cust_id = m.cust_id " +
				"left join OCRM_F_CM_CUST_MGR_INFO t on m.mgr_id = t.cust_manager_id " +
				" where 1=1 and m.mgr_id='"+mgr+"'");
		SQL=sb.toString();
		datasource=ds;
		
		setPrimaryKey("c.CUST_ID desc ");
		configCondition("c.CUST_ID","like","CUST_ID",DataType.String);
		configCondition("c.CUST_NAME","like","CUST_NAME",DataType.String);
		configCondition("c.CUST_TYPE","=","CUST_TYPE",DataType.String);
		configCondition("c.IDENT_TYPE","=","IDENT_TYPE",DataType.String);
		configCondition("c.IDENT_NO","like","IDENT_NO",DataType.String);
		configCondition("c.LINKMAN_NAME","like","LINKMAN_NAME",DataType.String);
		configCondition("c.LINKMAN_TEL","like","LINKMAN_TEL",DataType.String);
		configCondition("c.CUST_LEVEL","=","CUST_LEVEL",DataType.String);
//		configCondition("c.RISK_LEVEL","=","RISK_LEVEL",DataType.String);
//		configCondition("c.CREDIT_LEVEL","=","CREDIT_LEVEL",DataType.String);
//		configCondition("c.TOTAL_DEBT","=","TOTAL_DEBT",DataType.Number);
//		configCondition("m.MGR_ID","=","MGR_ID",DataType.String);
//		configCondition("c.FAXTRADE_NOREC_NUM","=","FAXTRADE_NOREC_NUM",DataType.Number);
//		configCondition("c.CURRENT_AUM","=","CURRENT_AUM",DataType.Number);
//		configCondition("bl.BELONG_LINE_NO","=","BL_NAME",DataType.String);
//		configCondition("t.BELONG_TEAM_HEAD","=","MGR_ID1",DataType.String);
	}

}
