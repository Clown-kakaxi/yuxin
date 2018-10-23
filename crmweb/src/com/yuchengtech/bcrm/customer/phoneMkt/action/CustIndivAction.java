package com.yuchengtech.bcrm.customer.phoneMkt.action;


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
/***
 * 零售客户查询（电话营销使用）  luyy
 */
@ParentPackage("json-default")
@Action(value="/custIndiv", results={
    @Result(name="success", type="json"),
})
public class CustIndivAction extends CommonAction{
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle;  
	private HttpServletRequest request;
	AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 	public void prepare() {	
 		ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String custId = request.getParameter("custId");
        String frId = (String)auth.getUnitInfo().get("FR_ID");
        StringBuilder sb = new StringBuilder("select a.cust_zh_name,a.cust_id,a.cust_typ,a.cert_num,b.sex,b.work_Unit,b.birthday,b.indu_Code,b.remark from OCRM_F_CI_CUST_DESC a left join " +
        		"OCRM_F_CI_PER_CUST_INFO b on a.cust_id = b.cust_id where a.cust_id = '"+custId+"' ");
      //增加法人权限控制
  		if(frId != null && !"".equals(frId)){
  			sb.append(" and b.FR_ID = '"+frId+"'");
  		}
        SQL = sb.toString();
		datasource = dsOracle;
	}
}
