package com.yuchengtech.bcrm.individual.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.SystemConstance;

/**
 * @describtion: 机构贷款客户TOP10
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年7月18日 上午11:37:13
 */
@Action("/orgCustCreTopN")
public class OrgCustCreTopNAction extends CommonAction{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;   
	
	public void prepare() {
		String rownumcondition = " WHERE ROWNUM <= 10";
		if("DB2".equals(SystemConstance.DB_TYPE)){
			rownumcondition = " fetch first 11 rows only ";
		} else {
			rownumcondition = " WHERE ROWNUM <= 10 ";
		}
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SQL = "SELECT * FROM (SELECT T.CUST_ID, T.CUST_NAME, T.ODS_DATE, SUM(T.CRE_BAL) AS BAL FROM ACRM_F_CI_CRE_TOP T "
			+"WHERE T.ORG_ID IN (SELECT SYSUNIT.UNITID FROM SYS_UNITS SYSUNIT WHERE SYSUNIT.UNITSEQ LIKE '"+auth.getUnitInfo().get("UNITSEQ")+"%') "
			+"GROUP BY T.CUST_ID, T.CUST_NAME, T.ODS_DATE ORDER BY SUM(T.CRE_BAL) DESC) " + rownumcondition;
		datasource = ds;
	}
}
