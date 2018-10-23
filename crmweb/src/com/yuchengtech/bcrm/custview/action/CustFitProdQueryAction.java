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
 *客户适合产品信息查询
 * @author hujun
 * @since 2014-2-27
 * 
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custFitProdQueryAction", results = { @Result(name = "success", type = "json")})
public class CustFitProdQueryAction extends CommonAction {
	
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
//        String frId = (String)auth.getUnitInfo().get("FR_ID");
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
 		String custId =request.getParameter("custId");
		StringBuilder sb = new StringBuilder(
					"select c.*,cc.cust_zh_name,cc.cust_typ,cc.TELEPHONE_NUM,cc.OFFICE_PHONE,cc.LINK_PHONE,cc.link_user,cc.cust_stat from ocrm_f_ci_cust_fit_prod c left join OCRM_F_CI_CUST_DESC cc on c.cust_id=cc.cust_id where 1=1 ");
		sb.append(" and c.CUST_ID = '"+custId+"'");
		setPrimaryKey("c.ID");

		SQL=sb.toString();
		datasource = ds;
	}
}
