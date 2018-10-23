package com.yuchengtech.bcrm.customer.belong.action;


import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 移交申请信息查询  流程页面使用
 * @author luyy
 *@since 2014-07-08
 */

@SuppressWarnings("serial")
@Action("/transApply")
public class OcrmFCiTransApplyAction  extends CommonAction {
    
    private HttpServletRequest request;

	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
	}
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	/**
	 * 关联表查询
	 */
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String type = request.getParameter("type");
    	String apply = request.getParameter("applyNo");
    	StringBuffer sb = new StringBuffer("");
    	if("1".equals(type)){//查询申请主表
    		sb.append(" select * from OCRM_F_CI_TRANS_APPLY where APPLY_NO='"+apply+"'");
    	}else if("2".equals(type)){//查询关联客户信息
    		sb.append(" select t.*,r.current_aum,nvl(r.current_aum,0)-nvl(h.current_aum,0) as dif_aum from OCRM_F_CI_TRANS_CUST t left join ACRM_F_CI_CUSTOMER r on t.cust_id = r.cust_id " +
    				"  left join ACRM_A_CI_GATH_BUSINESS_his h on t.cust_id = h.cust_id and h.etl_date = trunc(sysdate, 'mm')+1 "+
    				" where APPLY_NO ='"+apply+"'");
    	}
    	SQL = sb.toString();
    	datasource = ds;
    }
	  
}
