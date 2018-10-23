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
 *主要短信营销记录查询
 * @author hujun
 * @since 2014-2-26
 * 
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custMessageMktQueryAction", results = { @Result(name = "success", type = "json")})
public class CustMessageMktQueryAction extends CommonAction {
	
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
 		String custId =request.getParameter("custId");
		StringBuilder sb = new StringBuilder(
					"select c.*,t.model_name from OCRM_F_WP_REMIND_MSG c left join OCRM_F_MM_SYS_TYPE t" +
					" on c.model_id = t.id  where 1=1 ");
		sb.append(" and c.CUST_ID = '"+custId+"'");
        //增加法人权限控制
  		if(frId != null && !"".equals(frId)){
  			sb.append(" and c.FR_ID = '"+frId+"'");
  		}
		setPrimaryKey("c.ID");
		addOracleLookup("FR_ID","FR_TYPE");//所属法人机构
        addOracleLookup("IF_SEND","IF_FLAG");//是否已发送

		SQL=sb.toString();
		datasource = ds;
	}
}
