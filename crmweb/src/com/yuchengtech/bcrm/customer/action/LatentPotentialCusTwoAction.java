package com.yuchengtech.bcrm.customer.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.service.LatentPotentialCusTwoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/**
 * 个金潜在客户待处理清单池
 * @author Administrator
 *
 */
@Action("/latentPotentialCusTwoAction")
public class LatentPotentialCusTwoAction extends CommonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		//数据源
		@Autowired
		@Qualifier("dsOracle")
		private DataSource ds;
		
		@Autowired
		private LatentPotentialCusTwoService service;
		/**
		 *模块功能查询
		 */
		public void prepare() {
			AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ActionContext ctx = ActionContext.getContext();
	    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);   
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("select d.CUS_ID,"
	    			+ " d.CUS_NAME,"
	    			+ " d.CERT_TYPE,"
	    			+ " d.CERT_CODE, "
	    			+ " d.CUST_TYPE,"
	    			+ " d.ATTEN_NAME,"
	    			+ " d.job_type,"
	    			+ " d.indust_type,"
	    			+ " d.CALL_NO,"
	    			+ " d.zipcode,"
	    			+ " d.contmeth_info,"
	    			+ " d.CUS_ADDR,"
	    			+ " d.CUST_MGR,"
	    			+ " c.cust_name REFEREES_NAME,"
	    			+ " ac.user_name CUST_MGR_NAME, "
	    			+ " ao.org_name MAIN_BR_NAME,"
	    			+ " d.MAIN_BR_ID,"
	    			+ " d.DELETE_CUST_STATE,"
	    			+ " d.MKT_ACTIVITIE,"
	    			+ " FUN_TRANS_CODES(d.mkt_activitie) MKT_ACTIVITIE_V,"
	    			+ " d.SOURCE_CHANNEL,"
	    			+ " d.CUS_NATIONALITY,"
	    			+ " d.REFEREES_ID,"
	    			+ " d.RECORD_SESSION,"
	    			+ " d.MAIN_BR_ID TREE_STORE,"
	    			+ " d.CUS_EMAIL,"
	    			+ " d.CUS_WECHATID");
	    	sb.append("  from ACRM_F_CI_POT_CUS_CREATETEMP d ");
	    	sb.append("  left join ACRM_F_CI_CUSTOMER c  on d.referees_id=c.cust_id ");
	    	sb.append("  left join ADMIN_AUTH_ACCOUNT ac on d.CUST_MGR=ac.account_name ");
	    	sb.append("  left join ADMIN_AUTH_ACCOUNT M on d.CUST_MGR=M.account_name");
	    	sb.append("  left join ADMIN_AUTH_ORG ao on d.main_br_id=ao.org_id ");
	    	sb.append("  where 1=1 and d.cust_type='2' ");
	       System.out.println(sb.toString());
		   setPrimaryKey(" d.CUS_ID desc ");
			SQL=sb.toString();
			datasource = ds;
		}
		
		  
		  
		 // 删除
		public void batchDel() {
				
		    	ActionContext ctx = ActionContext.getContext();
		    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		    	try {
					Connection  connection = ds.getConnection();
					Statement stmt = connection.createStatement();
					String idStr = request.getParameter("idStr");
					String ids[] = idStr.split(",");
					for(String id : ids){
						String sql="delete from ACRM_F_CI_POT_CUS_CREATETEMP c where c.cus_id='"+ id + "'";
						stmt.execute(sql);
					}
				}catch (SQLException e) {
					e.printStackTrace();
				}
		    
		}
		
}
