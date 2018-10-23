package com.yuchengtech.bcrm.custmanager.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;


@Action("economicTree")
public class EconomicTreeAction extends CommonAction  {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	public void prepare(){
		StringBuffer sb =new StringBuffer(
				"SELECT I.F_CODE,F_VALUE,    " +
				"CASE      " +
				"  WHEN SUBSTR(I.F_CODE,2,1)='0' THEN -1  " +
				"  WHEN I.F_CODE BETWEEN 110 AND 190 THEN 100 " +
				"  WHEN I.F_CODE BETWEEN 210 AND 290 THEN 200 " +
				"  WHEN I.F_CODE BETWEEN 310 AND 390 THEN 300 " +
				"END AS PARENT_CODE    " +
				" FROM OCRM_SYS_LOOKUP_ITEM I  " +
				"  WHERE I.F_LOOKUP_ID='XD000062'  " +
				" AND SUBSTR(I.F_CODE,3,1) ='0'  " );
		SQL=sb.toString();
		datasource=ds;
	}

}