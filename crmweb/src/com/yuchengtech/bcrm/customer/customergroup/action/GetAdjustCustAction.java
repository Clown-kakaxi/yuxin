package com.yuchengtech.bcrm.customer.customergroup.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.service.TargetCusSearchService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
/**
 * 查询客户群的适合客户
 */
@SuppressWarnings("serial")
@Action("/getJustCust")
public class GetAdjustCustAction  extends CommonAction {
    @Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	private TargetCusSearchService targetCusSearchService;
    
    public void prepare() {
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		//客户群id
		String base_id = request.getParameter("groupId");
		//获取客户群类型
		Connection conn = null ;
    	Statement stmt = null ;
    	ResultSet rs = null;
    	
		 StringBuilder sb=new StringBuilder("");
		
		 //筛选方案客户群，需要拼接客户群成员查询sql
			 //1.拼接查询条件的map
			 JSONArray jaCondition = new JSONArray();
			 String radio = "false";
			 String sql = " select ss_col_item,ss_col_op,ss_col_value,ss_col_join from OCRM_F_A_SS_COL where ss_id=(select id from OCRM_F_CI_BASE_SEARCHSOLUTION where group_id='"+base_id+"')";
			 try {
		        	conn=ds.getConnection();
		        	stmt = conn.createStatement();
		    		rs = stmt.executeQuery(sql);
		    		while(rs.next()){
		    			if("true".equals(rs.getString("ss_col_join")))
		    				radio = "true";
		    			Map map = new HashMap<String,Object>();
		    			map.put("ss_col_item", rs.getString("ss_col_item"));
		    			map.put("ss_col_op", rs.getString("ss_col_op"));
		    			map.put("ss_col_value", rs.getString("ss_col_value"));
		    			jaCondition.add(map);
		    		}
			 }catch (SQLException e) {}
			 finally{
				 JdbcUtil.close(rs, stmt, conn);
		     }
			 //2.拼接sql语句,注：如果是动态客户群查询，查询方案并未设置，则直接返回空查询SQL
			if(jaCondition.size() < 1){
				SQL = "SELECT 1 FROM DUAL WHERE 1<>1";
				datasource = ds;
				return ;
			}
			String res = targetCusSearchService.generatorSql1(jaCondition,radio);
			//添加限制条件  如果为总行 查询全部的  如果不是，查询自己管理的
		    String level = auth.getUnitlevel();
		    if(!"1".equals(level)){
		    	res+= "  and custinfo.cust_id in (select cust_id from ocrm_f_ci_belong_custmgr where mgr_id='"+auth.getUserId()+"')";
		    }
		    String groupMemberType = request.getParameter("groupMemberType");
		    if("2".equals(groupMemberType)){//对私客户群	
		    	res +=" and custInfo.cust_type='2' ";
		    }else if("1".equals(groupMemberType)){//对公客户群	
		    	res +=" and custInfo.cust_type='1' ";
		    }
			sb.append(res );
			
//			 addOracleLookup("IDENT_TYPE", "PAR0100006");
			addOracleLookup("IDENT_TYPE", "XD000078");
		  	addOracleLookup("CUST_TYPE", "XD000080");	
			 
		 SQL = sb.toString();
         datasource = ds;
    }  
}