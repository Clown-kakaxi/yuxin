package com.yuchengtech.bcrm.custmanager.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;


@Action("businessLineTree")
public class BusinessLineTreeAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	public void prepare(){
		StringBuffer sb =new StringBuffer("SELECT * FROM ACRM_F_CI_BUSI_LINE WHERE 1=1 ");
		SQL=sb.toString();
		datasource=ds;
	}
}
