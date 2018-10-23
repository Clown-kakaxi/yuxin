package com.yuchengtech.bcrm.custview.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.service.RiskPreferInfoService;
import com.yuchengtech.bcrm.wealthManager.model.OcrmFFinCustRisk;
import com.yuchengtech.bob.common.CommonAction;
/**
 * 对私客户视图(风险偏好信息)
 * @author agile
 *
 */
@SuppressWarnings("serial")
@Action("/riskPreferInfo")
public class RiskPreferInfoAction extends CommonAction{
	@Autowired
	private RiskPreferInfoService service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init(){
		model = new OcrmFFinCustRisk();
		setCommonService(service);
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx
	             .get(ServletActionContext.HTTP_REQUEST);
	    String customerId = request.getParameter("custId");
		StringBuilder sb = new StringBuilder();
		sb.append("select S.USERNAME AS NAME,U.UNITNAME AS ORG_NAME,t.* from OCRM_F_FIN_CUST_RISK t  " +
				" LEFT JOIN SYS_USERS S ON  S.USERID = T.EVALUATE_NAME " +
				" left join sys_units u on t.evaluate_inst = u.unitID where 1=1 ");
		if(customerId != null){
			sb.append(" and t.cust_id = '"+customerId+"'");
		}
		SQL = sb.toString();
	    setPrimaryKey(" t.EVALUATE_DATE desc ");
		datasource = ds;
	}	
}
