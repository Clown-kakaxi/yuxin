package com.yuchengtech.bcrm.customer.level.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
/**
 * 客户视图-客户评级信息
 * @author geyu
 * 2014-7-23
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/acrmFCiRatingQuery", results = { @Result(name = "success", type = "json")})
public class AcrmFCiRatingQueryAction extends CommonAction{
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		ActionContext ctx = ActionContext.getContext();
   	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	    String custId=request.getParameter("custId");
		StringBuffer sb=new StringBuffer(" SELECT *  FROM ACRM_F_CI_GRADE T WHERE T.CUST_ID='"+custId+"'");
		setPrimaryKey("EVALUATE_DATE DESC");
		SQL=sb.toString();
		datasource = ds;
		configCondition("CUST_GRADE_TYPE", "=", "CUST_GRADE_TYPE",DataType.String);
		configCondition("CUST_GRADE", "=", "CUST_GRADE",DataType.String);
	}

}
