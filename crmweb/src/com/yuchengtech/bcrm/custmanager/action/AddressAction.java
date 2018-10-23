package com.yuchengtech.bcrm.custmanager.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;


@Action("address")
public class AddressAction extends CommonAction  {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	public void prepare(){
		StringBuffer sb =new StringBuffer(" SELECT * FROM ( SELECT T.F_VALUE, T.F_CODE,       "+
				"	CASE  WHEN SUBSTR(F_CODE,5,6) != '00' AND  SUBSTR(F_CODE,3,6) != '0000'    THEN  SUBSTR(F_CODE,0,4) ||'00'     "+
				"     		WHEN SUBSTR(F_CODE,5,6) = '00' AND   SUBSTR(F_CODE,3,6) != '0000'    THEN SUBSTR(F_CODE,0,2) ||'0000'  "+
				"        WHEN  SUBSTR(F_CODE,3,6) = '0000'    THEN  '-1'   "+
				"	END PARENT_CODE          "+
				"  FROM OCRM_SYS_LOOKUP_ITEM T    "+
				" WHERE T.F_LOOKUP_ID = 'XD000001')A     "+
				" ORDER BY A.PARENT_CODE,A.F_CODE ");
		SQL=sb.toString();
		datasource=ds;
	}

}