package com.yuchengtech.bcrm.customer.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
/**
 * 客户视图-客户担保信息
 * @author geyu
 * 2014-7-25
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custGrtInfoQuery", results = { @Result(name = "success", type = "json")})
public class custGrtInfoAction extends CommonAction {
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
   	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	    String custId=request.getParameter("custId");
   		StringBuffer sb=new StringBuffer("select t.*,to_char(t.account_amt,'999,999,990.99') account_amt1,t1.cust_manager_name from ACRM_F_CI_GRT_INFO t "+
   						"left join OCRM_F_CM_CUST_MGR_INFO t1 on t.cust_manager_id = t1.cust_manager_id "+
   						"where t.cust_id='"+custId+"'   order by t.guaranty_state asc,t.CN_CONT_NO desc,t.GUARANTY_END_DATE desc");
		SQL=sb.toString();
		datasource = ds;
		configCondition("CONT_TYPE", "=", "CONT_TYPE",DataType.String);
		configCondition("CONT_CATEGORY", "=", "CONT_CATEGORY",DataType.String);
		configCondition("CONT_ID", "like", "CONT_ID",DataType.String);
		configCondition("CN_CONT_NO", "like", "CN_CONT_NO",DataType.String);
		configCondition("GUAR_WAY", "=", "GUAR_WAY",DataType.String);
		configCondition("GUAR_CONT_NO", "like", "GUAR_CONT_NO",DataType.String);
		configCondition("GUAR_CONT_CN_NO", "like", "GUAR_CONT_CN_NO",DataType.String);
		configCondition("GAGE_TYPE", "=", "GAGE_TYPE",DataType.String);
		configCondition("GUARANTEE_IDENT_TYPE", "=", "GUARANTEE_IDENT_TYPE",DataType.String);
		configCondition("GUARANTEE_IDENT_ID", "like", "GUARANTEE_IDENT_ID",DataType.String);
		configCondition("GUARANTEE_CUST_ID", "like", "GUARANTEE_CUST_ID",DataType.String);
		configCondition("GUARANTEE_NAME", "like", "GUARANTEE_NAME",DataType.String);
		configCondition("CURRENCY", "=", "CURRENCY",DataType.String);
		configCondition("ACCOUNT_AMT", "like", "ACCOUNT_AMT",DataType.String);
		configCondition("HYC_SHOW", "=", "HYC_SHOW",DataType.String);
		configCondition("GUARANTY_STATE", "=", "GUARANTY_STATE",DataType.String);
		configCondition("AREA_LOCATION", "=", "AREA_LOCATION",DataType.String);
		configCondition("GUARANTY_TYPE", "=", "GUARANTY_TYPE",DataType.String);
		configCondition("GUARANTY_START_DATE", "=", "GUARANTY_START_DATE",DataType.Date);
		configCondition("GUARANTY_END_DATE", "=", "ACCOUNT_AMT",DataType.Date);
		configCondition("IDENT_ID", "like", "IDENT_ID",DataType.String);
		configCondition("CUST_MANAGER_NAME", "like", "CUST_MANAGER_NAME",DataType.String);
		configCondition("LOAN_CARD_NO", "like", "LOAN_CARD_NO",DataType.String);
	
	}

}
