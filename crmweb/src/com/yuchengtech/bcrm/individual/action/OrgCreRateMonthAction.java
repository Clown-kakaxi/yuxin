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
 *  @describtion: 机构贷款产品占比（月均）
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年7月26日 下午3:21:10
 */
@Action("/orgCreRateMonth")
public class OrgCreRateMonthAction extends CommonAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	public void prepare() {
		AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		SQL = " SELECT T.TYPE,SUM(nvl(T.BAL,0)) AS BAL FROM("
				+ "SELECT '普通商贷' AS TYPE, COMMERCIAL_LOAN AS BAL, ORG_ID from ACRM_F_CI_ORG_CRE_RATE_MONTH "
				+ " union select '银团' AS TYPE, SYNDICATIONS AS BAL, ORG_ID FROM ACRM_F_CI_ORG_CRE_RATE_MONTH "
				+ " UNION SELECT '银票贴现' AS TYPE, SILVER_TICKET AS BAL, ORG_ID FROM ACRM_F_CI_ORG_CRE_RATE_MONTH "
				+ " UNION SELECT '商票贴现' AS TYPE, BUSI_TICKET AS BAL, ORG_ID FROM ACRM_F_CI_ORG_CRE_RATE_MONTH "
				+ " UNION SELECT '应收账款融资及保理' AS TYPE, FACTORING AS BAL, ORG_ID FROM ACRM_F_CI_ORG_CRE_RATE_MONTH "
				+ " UNION SELECT '发票融资' AS TYPE, INVOICE AS BAL, ORG_ID FROM ACRM_F_CI_ORG_CRE_RATE_MONTH "
				+ " UNION SELECT '进出口贷款' AS TYPE, ENTRANCE_LOAN AS BAL, ORG_ID FROM ACRM_F_CI_ORG_CRE_RATE_MONTH "
				+ " UNION SELECT '预期贷款' AS TYPE, EXPECTED_LOAN AS BAL, ORG_ID FROM ACRM_F_CI_ORG_CRE_RATE_MONTH "
				+ " ) T where t.org_id in (SELECT SYSUNIT.UNITID FROM SYS_UNITS SYSUNIT WHERE SYSUNIT.UNITSEQ LIKE '"+auth.getUnitInfo().get("UNITSEQ")+"%')"
				+ " group by t.TYPE";
		
		PieChart fcbo = new PieChart();
		fcbo.addAttribute("palette", "1");
		fcbo.addAttribute("formatNumberScale", "0");
		fcbo.addAttribute("baseFontSize", "13");
		fcbo.addAttribute("basefont", "宋体");
		fcbo.addAttribute("bgColor", "#dfe6f1");
		
		fcbo.setLabelColumn("TYPE");
		fcbo.setValueColumn("BAL");
		
		chart = fcbo;
		datasource = ds;
	}

}
