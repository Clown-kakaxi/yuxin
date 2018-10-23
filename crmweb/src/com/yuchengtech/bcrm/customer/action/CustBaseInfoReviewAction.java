package com.yuchengtech.bcrm.customer.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;
/**
 * 对公客户基本信息修改审批
 * @author geyu
 * 2014-9-7
 */

@Action("/CustBaseInfoReviewAction")
public class CustBaseInfoReviewAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Override
	public void prepare() {
		String id = request.getParameter("id");
		StringBuffer sb=new StringBuffer("");
		sb.append("SELECT * FROM ACRM_F_CI_CUSTOMER_REVIEW V WHERE V.ID='"
					+ id + "'");
		SQL = sb.toString();
		datasource = ds;
	}
}
