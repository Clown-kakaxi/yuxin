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
 * @describtion: 客户经理贡献度时点占比
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年7月22日 上午10:54:48
 */
@Action("/mgrContribute")
public class MgrContributeAction extends CommonAction{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

	public void prepare(){
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		SQL =" SELECT * FROM ( SELECT '存款' AS TYPE,T.DEP_NUM as NUM,T.MGR_ID,T.ODS_DATE  FROM ACRM_F_CI_MGR_CONTRIBUTE T "
				+ " UNION SELECT '贷款' AS TYPE,T.CRE_NUM as NUM,T.MGR_ID,T.ODS_DATE  FROM ACRM_F_CI_MGR_CONTRIBUTE T "
				+ " UNION SELECT '中间业务' AS TYPE,T.MID_NUM as NUM,T.MGR_ID,T.ODS_DATE  FROM ACRM_F_CI_MGR_CONTRIBUTE T "
				+ " ) T WHERE  T.MGR_ID = '"+auth.getUserId()+"'";
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

