package com.yuchengtech.bcrm.individual.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.fusioncharts.LineChart;

/**
 * @describtion: 机构存款趋势图
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年7月21日 上午10:54:48
 */
@Action("/orgDepMonth")
public class OrgDepMonthAction extends CommonAction{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

	public void prepare(){
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		SQL ="SELECT T.COUNT_YEAR,T.COUNT_MONTH, ROUND(COALESCE(SUM(T.DEP_BAL),0)/10000,2) AS DEP_BAL, ROUND(COALESCE(SUM(T.DEP_YEAR_AVG),0)/10000,2) AS DEP_YEAR_AVG  FROM ACRM_F_CI_ORG_DEP_MONTH T "
				+ " WHERE T.ORG_ID IN (SELECT SYSUNIT.UNITID FROM SYS_UNITS SYSUNIT WHERE SYSUNIT.UNITSEQ LIKE '"+auth.getUnitInfo().get("UNITSEQ")+"%') "
				+ " AND T.COUNT_YEAR = (SELECT SUBSTR(DATADATE,0,4) FROM  AE_USER_DATA_DATE) GROUP BY COUNT_YEAR, COUNT_MONTH " ;
		this.setPrimaryKey("COUNT_YEAR,COUNT_MONTH");
		LineChart fcbo = new LineChart();
		fcbo.addDataColumn("DEP_YEAR_AVG", "存款年日均（折人民币）");
		fcbo.addDataColumn("DEP_BAL", "存款时点余额（折人民币）");
		fcbo.setCategories("一月,二月,三月,四月,五月,六月,七月,八月,九月,十月,十一月,十二月", ",");
		fcbo.addAttribute("yAxisMinValue", "10");
		fcbo.addAttribute("showValues", "0");
		fcbo.addAttribute("yaxisname", "(单位:万元)");
	//	fcbo.addAttribute("caption", "机构存款趋势图");
		fcbo.addAttribute("palette", "1");
		fcbo.addAttribute("formatNumberScale", "0");
		fcbo.addAttribute("baseFontSize", "13");
		fcbo.addAttribute("basefont", "宋体");
	//	PieChart fcbo = new PieChart();
	//	fcbo.setLabelColumn("BUSI_SCALE");
	//	fcbo.setValueColumn("COMP_PRE_MONTH");
		
		chart = fcbo;
		datasource = ds;
	}

}

