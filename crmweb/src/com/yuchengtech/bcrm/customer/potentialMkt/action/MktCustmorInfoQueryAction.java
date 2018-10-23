package com.yuchengtech.bcrm.customer.potentialMkt.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
/**
 * 在pipeline各个阶段中，根据客户id查询其相关的归属信息  主要是客户的归属部门信息
 * @author dongyi
 *  2014-11-28
 */

@SuppressWarnings("serial")
@Action("/MktCustmorInfoQuery")
public class MktCustmorInfoQueryAction  extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	
	private HttpServletRequest request;
    @Autowired
   	public void init(){}
    /**
	 * 设置查询SQL并为父类相关属性赋值
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        
    	String id = request.getParameter("id");
    	StringBuilder sb  = new StringBuilder("select  t.belong_dept_id,t.belong_dept" +
    			" from acrm_f_ci_belong_dept t where cust_id ='"+id+"'");
    
        	SQL=sb.toString();
        	datasource = ds;
	}
}