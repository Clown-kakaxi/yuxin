package com.yuchengtech.bcrm.individual.action;

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
import com.yuchengtech.crm.constance.fusioncharts.PieChart;

/**
 *  @describtion: 所辖客户等级占比
 *
 * @author : luyy
 * @date : 2014-08-04
 */
@Action("/custLevelRate")
public class CustLevelRateAction extends CommonAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String type = request.getParameter("type");
		if("org".equals(type)){
			SQL = "select count(*) as NUM,decode(CUST_LEVEL,'1','一星级','2','二星级','3','三星级','4','四星级','5','五星级','未评级') as LEVEL_NAME " +
					"from ACRM_F_CI_customer  where cust_type='2' " +
					"and cust_id in (select cust_id from OCRM_F_CI_BELONG_ORG o where o.institution_code in " +
					"(SELECT SYSUNIT.UNITID FROM SYS_UNITS SYSUNIT WHERE SYSUNIT.UNITSEQ LIKE '"+auth.getUnitInfo().get("UNITSEQ")+"%')) group by CUST_LEVEL";
		}
		if("mgr".equals(type)){
			SQL = "select count(*) as NUM,decode(CUST_LEVEL,'1','一星级','2','二星级','3','三星级','4','四星级','5','五星级','未评级') as LEVEL_NAME " +
			"from ACRM_F_CI_customer where cust_type='2' " +
			" and cust_id in (select cust_id from OCRM_F_CI_BELONG_CUSTMGR where mgr_id='"+auth.getUserId()+"')  group by CUST_LEVEL";
		}
		
		
		PieChart fcbo = new PieChart();
		fcbo.addAttribute("palette", "1");
		fcbo.addAttribute("formatNumberScale", "0");
		fcbo.addAttribute("baseFontSize", "13");
		fcbo.addAttribute("basefont", "宋体");
		
		fcbo.setLabelColumn("LEVEL_NAME");
		fcbo.setValueColumn("NUM");
		
		chart = fcbo;
		datasource = ds;
	}

}
