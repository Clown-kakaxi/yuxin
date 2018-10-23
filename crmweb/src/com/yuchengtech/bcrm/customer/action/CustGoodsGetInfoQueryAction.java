package com.yuchengtech.bcrm.customer.action;

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

/**
 * 客户视图-客户礼品领取记录信息
 * @author geyu
 * 2014-7-23
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custGoodsGetInfoQuery", results = { @Result(name = "success", type = "json")})
public class CustGoodsGetInfoQueryAction extends CommonAction {
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
   	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	    String custId=request.getParameter("custId");
   		StringBuffer sb=new StringBuffer("select *  from OCRM_F_SE_GOODS_HIS t where t.cust_id='"+custId+"'");
		SQL=sb.toString();
		datasource = ds;
	}

}
