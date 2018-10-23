package com.yuchengtech.bcrm.custmanager.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;


@Action("businessTypeTree")
public class BusinessTypeTreeAction extends CommonAction  {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	public void prepare(){
		StringBuffer sb =new StringBuffer("select T.F_CODE,T.PARENT_CODE,T.F_VALUE  from ACRM_F_CI_BUSI_TYPE T  WHERE 1=1 ");
		SQL=sb.toString();
		datasource=ds;
	}

}
