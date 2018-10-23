
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
 * 客户移交历史查询
 * @author luyy
 *@since 2014-07-9
 */

@SuppressWarnings("serial")
@Action("/custTransHis")
public class CustTransHisAction  extends CommonAction {
    
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
    	String s=auth.getUserId();
    	String custId=request.getParameter("custId");
    	
    	StringBuffer sb = new StringBuffer(" select h.*,a.cust_name from OCRM_F_CI_BELONG_HIST h " +
    			" left join (select r.cust_id,r.cust_name from ACRM_F_CI_CUSTOMER r union all select c.cus_id,c.cus_name from acrm_f_ci_pot_cus_com c ) a " +
    			" on h.cust_id=a.cust_id " +
    			"where 1=1  ");
    	if(custId!=null&&!"".equals(custId)){
    		//查询客户相关的记录
    		sb.append(" and a.cust_id='"+custId+"' ");
    	}else{
    		//查询与当前人相关的记录
    		sb.append(" and (h.BEFORE_MGR_ID='"+auth.getUserId()+"' or h.AFTER_MGR_ID='"+auth.getUserId()+"' or  h.ASSIGN_USER='"+auth.getUserId()+"')");
    	}
    	this.addOracleLookup("BEFORE_MAIN_TYPE", "MAINTAIN_TYPE");
    	this.addOracleLookup("AFTER_MAIN_TYPE", "MAINTAIN_TYPE");
    	this.addOracleLookup("WORK_TRAN_LEVEL", "HAND_KIND");
    	this.setPrimaryKey(" id desc");
    	
    	SQL = sb.toString();
    	datasource = ds;
    }
	
	
}
