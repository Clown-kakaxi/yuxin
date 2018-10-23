package com.yuchengtech.bcrm.finService.action;

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
 * @describtion： 顾问式理财服务
 * @author： likai
 * @since：2014年07月17日
 *
 */
@Action("/consultantFinancialNew")
public class ConsultantFinancialNewAction extends CommonAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Override
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		StringBuffer sb = new StringBuffer("select c.cust_id,c.cust_name,r.cust_risk_charact,nvl(s.BAL,0) as ASSET_SUM,nvl(s1.amount_value,0) as OTHER_BANK,d.demand_id,d.CREATE_DT  from acrm_f_ci_customer c ");
		sb.append(" left join OCRM_F_FIN_CUST_DEMAND d on d.cust_id = c.cust_id ");
		sb.append(" left join OCRM_F_FIN_CUST_RISK r on r.cust_id = c.cust_id and r.his_flag = '0' ");
		sb.append(" left join (SELECT a.cust_id, sum(a.MS_AC_BAL) as BAL FROM ACRM_F_DP_SAVE_INFO A group by a.cust_id) S on s.cust_id = c.cust_id ");
		sb.append(" left join (select CUST_ID,SUM(AMOUNT_VALUE) AMOUNT_VALUE from OCRM_F_FIN_BAL_SHEET where belong_type = '1' AND ASSET_DEBT_TYPE = '1' group by CUST_ID ) S1 on s1.cust_id = c.cust_id");
		sb.append(" where 1=1 and c.cust_type='2' ");
		sb.append(" and exists (select 1 from ocrm_f_ci_belong_custmgr mgr where mgr.cust_id = c.cust_id and mgr.mgr_id = '"+auth.getUserId()+"' ) ");
		
		SQL = sb.toString();
		datasource = ds;
		
		configCondition("c.CUST_ID", "=", "CUST_ID",DataType.String);
		configCondition("c.CUST_NAME", "like", "CUST_NAME",DataType.String);
		configCondition("R.CUST_RISK_CHARACT", "=", "CUST_RISK_CHARACT",DataType.String);
		
	}
	
}
