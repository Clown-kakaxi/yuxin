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
 * 查询客户群的贡献度   luyy  2014-08-04
 */
@SuppressWarnings("serial")
@Action("/groupContribution")
public class GetGroupContributionAction  extends CommonAction {
    @Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	private TargetCusSearchService targetCusSearchService;
    
    public void prepare() {
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String stype = request.getParameter("type");
		StringBuilder sb=new StringBuilder("");
		if("1".equals(stype)){//客户群贡献度查询
			//客户群id
			String base_id = request.getParameter("groupId");
			//获取客户群类型
			Connection conn = null ;
	    	Statement stmt = null ;
	    	ResultSet rs = null;
	    	String type = "";
	    	String custType = "";
	    	 try {
		        	conn=ds.getConnection();
		        	stmt = conn.createStatement();
		    		String count =  " select CUST_FROM,GROUP_MEMBER_TYPE  from OCRM_F_CI_BASE where id='"+base_id+"'";
		    		rs = stmt.executeQuery(count);
		    		while(rs.next()){
		    			type = rs.getString("CUST_FROM");
		    			custType = rs.getString("GROUP_MEMBER_TYPE");
		    		}
			 }catch (SQLException e) {}
			 finally{
				 JdbcUtil.close(rs, stmt, conn);
		        }
	    	
			 if("1".equals(type)||"3".equals(type))//手动添加和证件导入的，直接在关联客户表中查询
				 sb.append("select b.* from ACRM_F_CI_CUST_CONTRIBUTION b  left join OCRM_F_CI_RELATE_CUST_BASE a " +
				 		"on a.cust_id=b.CUST_ID where a.CUST_BASE_ID='"+base_id+"'  ");
			 else{//筛选方案客户群，需要拼接客户群成员查询sql
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
				 //2.拼接sql语句
				String res = targetCusSearchService.generatorSql(jaCondition,radio);
				 //2.查询
				sb.append( "select a.* from  ACRM_F_CI_CUST_CONTRIBUTION a " +
						"where a.cust_id in" );
				sb.append("( "+res );
				 if("1".equals(custType)||"2".equals(custType)){//客户群的类别有要求2：对私，1：对公
					 sb.append(" and custinfo.CUST_TYPE='"+custType+"'");
				 }
				 sb.append(")  order by etl_date desc " );
			 }
		}else if("2".equals(stype)){//贡献度明细查询
			String custId = request.getParameter("custId");
			String date = request.getParameter("date");
			sb.append(" select a.*,b.prod_name from ACRM_F_CI_PROD_CONT a left join Ocrm_f_Pd_Prod_Info b on a.PROD_ID =b.product_id where a.CUSTOM_ID='"+custId+"'" +
					" and a.ETL_DATE=to_date('"+date.substring(0,10)+"','YYYY-MM-dd') ");
			
		}
		
			 
		 SQL = sb.toString();
        datasource = ds;
    }  
}