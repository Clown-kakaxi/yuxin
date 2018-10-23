package com.yuchengtech.bcrm.custmanager.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

/**
 * 客户经理信息修改-代办工作查询
 * @author geyu
 * 2014-8-18
 */
@Action("/CustomerManagerInfoReviewAction")
public class CustomerManagerInfoReviewQueryAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Override
	public void prepare() {
		String operate = request.getParameter("operate");
		String id = request.getParameter("id");
		StringBuffer sb=new StringBuffer("");
		sb.append("SELECT * FROM OCRM_F_CM_CUST_MGR_INFO_REVIEW V WHERE V.ID='"
					+ id + "'");
		SQL = sb.toString();
		datasource = ds;
	}

}
