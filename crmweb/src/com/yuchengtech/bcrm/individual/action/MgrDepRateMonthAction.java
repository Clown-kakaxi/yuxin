package com.yuchengtech.bcrm.individual.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.fusioncharts.PieChart;

/**
 *  @describtion: 客户经理存款产品占比（月均）
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年7月26日 下午3:21:10
 */
@Action("/mgrDepRateMonth")
public class MgrDepRateMonthAction extends CommonAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	public void prepare() {
		AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		SQL = "SELECT T.TYPE,SUM(T.BAL) AS BAL FROM (select '活期' AS TYPE,curr_bal AS BAL,MGR_ID from ACRM_F_CI_MGR_DEP_RATE_MONTH union select '定期' AS TYPE,FIX_BAL AS BAL,MGR_ID FROM ACRM_F_CI_MGR_DEP_RATE_MONTH UNION SELECT '理财' AS TYPE,FIN_BAL AS BAL,MGR_ID FROM ACRM_F_CI_MGR_DEP_RATE_MONTH ) T where t.mgr_id = '"+auth.getUserId()+"'"
				+ " group by t.TYPE";
		
		PieChart fcbo = new PieChart();
		fcbo.addAttribute("palette", "1");
		fcbo.addAttribute("formatNumberScale", "0");
		fcbo.addAttribute("baseFontSize", "13");
		fcbo.addAttribute("basefont", "宋体");
		
		fcbo.setLabelColumn("TYPE");
		fcbo.setValueColumn("BAL");
		
		chart = fcbo;
		datasource = ds;
	}

}
