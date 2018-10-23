package com.yuchengtech.bcrm.sales.message.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 渠道自动营销客户查询
 * @author geyu
 * 2014-7-18
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/ocrmCustPersonQuery", results = { @Result(name = "success", type = "json")})
public class OcrmCustPersonQueryAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	private HttpServletRequest request;
	
	private Map<String, Object> map = new HashMap<String, Object>();
 	public void prepare() {	
 		StringBuffer sb=new StringBuffer("select c.cust_id,c.cust_name,c.ident_type,c.ident_no,c.cust_type,c.cust_level," +
				"c.linkman_name,c.linkman_tel,m.institution_name as org_name,m.mgr_name,c.risk_level," +
				"c.credit_level,c.faxtrade_norec_num,t.belong_team_head_name, p.mobile_phone,p.email,p.weixin " +
				"from ACRM_F_CI_CUSTOMER c left join OCRM_F_CI_BELONG_CUSTMGR m on c.cust_id = m.cust_id " +
				"left join OCRM_F_CM_CUST_MGR_INFO t on m.mgr_id = t.cust_manager_id " +
				"left join ACRM_F_CI_PERSON p on p.cust_id=c.cust_id "+
				" where 1=1 and c.cust_type='2'");
		
		for(String key:this.getJson().keySet()){
   	     if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
   	         if(key.equals("ACCT_NO"))
   	             sb.append(" and cust_id in (select cust_id from ACRM_F_CI_LOAN_ACT where ACCOUNT like '"+this.json.get(key)+"%')");
   	     }
       }
		
		SQL=sb.toString();
		datasource=ds;
		
		setPrimaryKey("c.CUST_ID desc ");
		configCondition("c.CUST_ID","like","CUST_ID",DataType.String);
		configCondition("c.CUST_NAME","like","CUST_NAME",DataType.String);
		configCondition("c.CUST_TYPE","=","CUST_TYPE",DataType.String);
		configCondition("c.IDENT_TYPE","=","IDENT_TYPE",DataType.String);
		configCondition("c.IDENT_NO","like","IDENT_NO",DataType.String);
		configCondition("m.INSTITUTION","=","ORG_NAME",DataType.String);
		configCondition("c.LINKMAN_NAME","like","LINKMAN_NAME",DataType.String);
		configCondition("c.LINKMAN_TEL","like","LINKMAN_TEL",DataType.String);
		configCondition("c.CUST_LEVEL","=","CUST_LEVEL",DataType.String);
		configCondition("c.RISK_LEVEL","=","RISK_LEVEL",DataType.String);
		configCondition("c.CREDIT_LEVEL","=","CREDIT_LEVEL",DataType.String);
		configCondition("c.TOTAL_DEBT","=","TOTAL_DEBT",DataType.Number);
		configCondition("m.MGR_ID","=","MGR_ID",DataType.String);
		configCondition("c.FAXTRADE_NOREC_NUM","=","FAXTRADE_NOREC_NUM",DataType.Number);
		configCondition("c.CURRENT_AUM","=","CURRENT_AUM",DataType.Number);
		configCondition("bl.BELONG_LINE_NO","=","BL_NAME",DataType.String);
		configCondition("t.BELONG_TEAM_HEAD","=","MGR_ID1",DataType.String);
		
	}

}
