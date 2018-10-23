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
 * @describtion: 机构持有贵宾卡客户
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年7月25日 上午9:14:13
 */
@Action("/orgHoldCard")
public class OrgHoldCardAction extends CommonAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	public void prepare() {
		AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		SQL = "select t.card_type,sum(t.CARD_NUM) as CARD_NUM from ACRM_F_CI_ORG_HOLD_CARD t where t.org_id in (SELECT SYSUNIT.UNITID FROM SYS_UNITS SYSUNIT WHERE SYSUNIT.UNITSEQ LIKE '"+auth.getUnitInfo().get("UNITSEQ")+"%')"
				+ " group by t.card_type";
		
		PieChart fcbo = new PieChart();
		fcbo.addAttribute("palette", "1");
		fcbo.addAttribute("formatNumberScale", "0");
		fcbo.addAttribute("baseFontSize", "13");
		fcbo.addAttribute("basefont", "宋体");
		
		fcbo.setLabelColumn("CARD_TYPE");
		fcbo.setValueColumn("CARD_NUM");
		
		chart = fcbo;
		datasource = ds;
	}

}
