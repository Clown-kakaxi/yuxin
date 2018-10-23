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
 *  @describtion: 客户经理贷款产品占比（时点）
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年7月26日 下午3:21:10
 */
@Action("/mgrCreRate")
public class MgrCreRateAction extends CommonAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	public void prepare() {
		AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		SQL = " SELECT T.TYPE,SUM(T.BAL) AS BAL FROM("
				+ "SELECT '普通商贷' AS TYPE, COMMERCIAL_LOAN AS BAL, MGR_ID from ACRM_F_CI_MGR_CRE_RATE "
				+ " union select '银团' AS TYPE, SYNDICATIONS AS BAL, MGR_ID FROM ACRM_F_CI_MGR_CRE_RATE "
				+ " UNION SELECT '银票贴现' AS TYPE, SILVER_TICKET AS BAL, MGR_ID FROM ACRM_F_CI_MGR_CRE_RATE "
				+ " UNION SELECT '商票贴现' AS TYPE, SILVER_TICKET AS BAL, MGR_ID FROM ACRM_F_CI_MGR_CRE_RATE "
				+ " UNION SELECT '应收账款融资及保理' AS TYPE, SILVER_TICKET AS BAL, MGR_ID FROM ACRM_F_CI_MGR_CRE_RATE "
				+ " UNION SELECT '发票融资' AS TYPE, SILVER_TICKET AS BAL, MGR_ID FROM ACRM_F_CI_MGR_CRE_RATE "
				+ " UNION SELECT '进出口贷款' AS TYPE, SILVER_TICKET AS BAL, MGR_ID FROM ACRM_F_CI_MGR_CRE_RATE "
				+ " UNION SELECT '预期贷款' AS TYPE, SILVER_TICKET AS BAL, MGR_ID FROM ACRM_F_CI_MGR_CRE_RATE "
				+ " ) T where t.MGR_ID = '"+auth.getUserId()+"'"
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
