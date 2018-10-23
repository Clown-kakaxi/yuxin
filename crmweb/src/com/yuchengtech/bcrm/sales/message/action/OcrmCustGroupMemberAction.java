package com.yuchengtech.bcrm.sales.message.action;

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
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.service.TargetCusSearchService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;

/**
 * 客户群成员查询
 * @author geyu
 * 2014-7-19
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/ocrmCustGroupMemberQuery", results = { @Result(name = "success", type = "json")})
public class OcrmCustGroupMemberAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle;  
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	@Autowired
	private TargetCusSearchService targetCusSearchService;
	private HttpServletRequest request;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String id=request.getParameter("id");
        String groupMemberType=request.getParameter("groupMemberType");
        String custFrom=request.getParameter("custFrom");
        String sb = "";
        if("2".equals(custFrom)){//自动筛选客户群成员
    		//客户群id
    		String base_id = id;
    		//获取客户群类型
    		Connection conn = null ;
        	Statement stmt = null ;
        	ResultSet rs = null;
        	
    		 StringBuilder sbb=new StringBuilder("");
    		
    		 //筛选方案客户群，需要拼接客户群成员查询sql
    			 //1.拼接查询条件的map
    			 JSONArray jaCondition = new JSONArray();
    			 String radio = "false";
    			 String sql = " select ss_col_item,ss_col_op,ss_col_value,ss_col_join from OCRM_F_A_SS_COL where ss_id=(select id from OCRM_F_CI_BASE_SEARCHSOLUTION where group_id='"+base_id+"')";
    			 try {
    		        	conn=dsOracle.getConnection();
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
    				datasource = dsOracle;
    				return ;
    			}
    			String res = targetCusSearchService.generatorSql1(jaCondition,radio);
    			//添加限制条件  如果为总行 查询全部的  如果不是，查询自己管理的
    		    String level = auth.getUnitlevel();
    		    if(!"1".equals(level)){
    		    	res+= "  and custinfo.cust_id in (select cust_id from ocrm_f_ci_belong_custmgr where mgr_id='"+auth.getUserId()+"')";
    		    }
    		    
    		    if("2".equals(groupMemberType)){//对私客户群	
    		    	res +=" and custInfo.cust_type='2' ";
    		    }else if("1".equals(groupMemberType)){//对公客户群	
    		    	res +=" and custInfo.cust_type='1' ";
    		    }
    		    sbb.append(res );
    			addOracleLookup("IDENT_TYPE", "XD000078");
    		  	addOracleLookup("CUST_TYPE", "XD000080");
    		  	sb = sbb.toString();
        }else{
        	StringBuffer sbf = new StringBuffer("select t.cust_id,a.cust_name,a.IDENT_TYPE,a.IDENT_NO from ACRM_F_CI_PERSON t " +
        			" left join acrm_f_ci_customer a on t.cust_id = a.cust_id "+
        			" where t.cust_id in (select t1.cust_id from OCRM_F_CI_RELATE_CUST_BASE t1 where t1.cust_base_id='"+id+"')");
        	sb = sbf.toString();
        }
        SQL = sb.toString();
		datasource = dsOracle;
		
	}

}
