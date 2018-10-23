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
 * @describtion: 机构贡献度时点占比
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年7月22日 上午10:54:48
 */
@Action("/orgContribute")
public class OrgContributeAction extends CommonAction{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

	public void prepare(){
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		SQL =" SELECT T.TYPE,SUM(NUM)AS NUM FROM ( "
				+ " SELECT '存款' AS TYPE,T.DEP_NUM AS NUM,T.ORG_ID,T.ODS_DATE  FROM ACRM_F_CI_ORG_CONTRIBUTE T "
				+ " UNION SELECT '贷款' AS TYPE,T.CRE_NUM AS NUM,T.ORG_ID,T.ODS_DATE  FROM ACRM_F_CI_ORG_CONTRIBUTE T "
				+ " UNION SELECT '中间业务' AS TYPE,T.MID_NUM AS NUM,T.ORG_ID,T.ODS_DATE  FROM ACRM_F_CI_ORG_CONTRIBUTE T "
				+ " ) T WHERE  T.ORG_ID IN (SELECT SYSUNIT.UNITID FROM SYS_UNITS SYSUNIT WHERE SYSUNIT.UNITSEQ LIKE '"+auth.getUnitInfo().get("UNITSEQ")+"%')"
				+ " GROUP BY T.TYPE";
		PieChart fcbo = new PieChart();
		fcbo.addAttribute("palette", "1");
		fcbo.addAttribute("formatNumberScale", "0");
		fcbo.addAttribute("baseFontSize", "13");
		fcbo.addAttribute("basefont", "宋体");
		
		fcbo.setLabelColumn("TYPE");
		fcbo.setValueColumn("NUM");
		
		chart = fcbo;
		datasource = ds;
	}

}

