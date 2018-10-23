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
 * @describtion: 客户经理客户数
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年7月22日 上午10:54:48
 */
@Action("/mgrCustNum")
public class MgrCustNumAction extends CommonAction{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

	public void prepare(){
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		SQL =/*"SELECT '客户总数' AS CUST_TYPE,COUNT(1) AS NUM FROM ACRM_F_CI_CUSTOMER C1 "
				+ " WHERE C1.CUST_STAT = '1' "
				+ " AND EXISTS (SELECT 1 FROM OCRM_F_CI_BELONG_CUSTMGR mgr WHERE mgr.CUST_ID = C1.CUST_ID AND mgr.mgr_id = '"+auth.getUserId()+"' )"
				+ " UNION "
				+*/ " SELECT CASE WHEN C2.CUST_TYPE = 2 THEN '对私客户数' WHEN C2.CUST_TYPE = '1' THEN '对公客户数' END as CUST_TYPE,COUNT(1) AS NUM FROM ACRM_F_CI_CUSTOMER C2 "
				+ " WHERE C2.POTENTIAL_FLAG = '0' "
				+ " AND EXISTS (SELECT 1 FROM OCRM_F_CI_BELONG_CUSTMGR mgr WHERE mgr.CUST_ID = C2.CUST_ID AND mgr.mgr_id = '"+auth.getUserId()+"' )"
				+ " GROUP BY C2.CUST_TYPE" ;
		PieChart fcbo = new PieChart();
		fcbo.addAttribute("palette", "1");
		fcbo.addAttribute("formatNumberScale", "0");
		fcbo.addAttribute("baseFontSize", "13");
		fcbo.addAttribute("basefont", "宋体");
		
		fcbo.setLabelColumn("CUST_TYPE");
		fcbo.setValueColumn("NUM");
		
		chart = fcbo;
		datasource = ds;
	}

}

