package com.yuchengtech.bcrm.custview.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 
 *客户适合产品详情查询
 * @author hujun
 * @since 2014-2-27
 * 
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custFitProdDetailsQueryAction", results = { @Result(name = "success", type = "json")})
public class CustFitProdDetailsQueryAction extends CommonAction {
	
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
   
	/**
	 *模块功能查询
	 */
	public void prepare() {
		AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //当前用户所属法人号
        String frId = (String)auth.getUnitInfo().get("FR_ID");
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
 		String prodId =request.getParameter("prodId");
		StringBuilder sb = new StringBuilder(
					"select c.* from ocrm_f_pd_prod_info c where 1=1 ");
		sb.append(" and c.PRODUCT_ID = '"+prodId+"'");
//        //增加法人权限控制
//  		if(frId != null && !"".equals(frId)){
//  			sb.append(" and c.FR_ID = '"+frId+"'");
//  		}
		addOracleLookup("FR_ID","FR_TYPE");//所属法人机构

		SQL=sb.toString();
		datasource = ds;
	}
}
