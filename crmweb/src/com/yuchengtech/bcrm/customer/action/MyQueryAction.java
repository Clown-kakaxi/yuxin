package com.yuchengtech.bcrm.customer.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiAttentionCustInfo;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;

/**
 * 客户信息查询临时类,为客户管理中,客户转移,客户认领,客户托管等模块提供查询服务
 * @author sujm
 * 
 */
@SuppressWarnings("serial")
@Action("/myqueryinfoaction")
public class MyQueryAction extends CommonAction {
	@Autowired
	private DataSource dataSource;

	//
	public String queryCustomerBelongInfo(String str, String str1) {
		String sign = null;
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		try {

			conn = dataSource.getConnection();
			statement = conn.createStatement();

			String sql = "select T.INSTITUTION_CODE from OCRM_F_CI_BELONG_ORG t where  t.cust_id = '"+ str
					+ "' and T.INSTITUTION_CODE NOT IN (select unitid from SYS_UNITS where unitseq like '%"+ str1 + "%')";
			rs = statement.executeQuery(sql);
			if (rs.next()) {
				sign = "true";
			} else {
				model = new OcrmFCiAttentionCustInfo();
				sign = "false";
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionMessage("New Record created failure");
		} finally {
			JdbcUtil.close(rs, statement, conn);
		}
		return sign;
	};
	//判定当前登录者身份，对公返回c，对私返回p ;
	//********* by sujm 2013-01-04
	public String queryUserType() {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List user_role = auth.getRolesInfo();
		Map roleInfo = (Map) user_role.get(0);
        if(roleInfo.get("ROLE_CODE").toString().substring(0, 1).equals("p")||roleInfo.get("ROLE_CODE").toString().substring(0, 1).equals("l")){
       	 addOracleLookup("CUST_LEV", "P_CUST_LEV");
       	return "p";
       } else{
       	return "c";
       }

	}
	
	//查询所选客户是否已经在客户转移列表中
	public String queryCustomerIfInApp(String cust_id) {
		String sign = null;
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		try {

			conn = dataSource.getConnection();
			statement = conn.createStatement();

			String sql = "SELECT * FROM OCRM_F_CI_TRANSFER_APP_INFO T WHERE T.APPLY_USER_ID = '"+auth.getUserId()+"' AND CUST_ID = '"+cust_id+"'";
			rs = statement.executeQuery(sql);
			if (rs.next()) {
				sign = "true";
			} else {
				sign = "false";
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionMessage("New Record created failure");
		} finally {
			JdbcUtil.close(rs, statement, conn);
		}
		return sign;
	}
	
	//判定该客户是否为当前操作人辖内的客户
	//********* by sujm 2012-12-20
	public void isBelongCustomer(String customerID){
    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currenUserId = auth.getUserId();
		
		Connection conn=null;
		Statement statement=null;
		ResultSet rs = null;
		
    	  ActionContext ctx = ActionContext.getContext();
    	  request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    		boolean flag = false;
    		try{
						conn = dataSource.getConnection();
						statement = conn.createStatement();
					
					String sql = "SELECT * FROM OCRM_F_CI_BELONG_ORG ORG WHERE ORG.INSTITUTION_CODE IN" +
								 "(SELECT UNITID FROM SYS_UNITS UN WHERE UN.UNITSEQ LIKE'%"+auth.getUnitId().toString()+"%') " +
								 "AND ORG.CUST_ID = '"+customerID+"'";
					rs = statement.executeQuery(sql);
					
					if(rs.next()){
						flag = true;
					}else{
						model = new OcrmFCiAttentionCustInfo(); 
					}
    		}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		JdbcUtil.close(rs, statement, conn);
	    		if(flag == false){
	    			throw new BizException(1,0,"100010","非管辖客户，不能访问客户视图!");
	    		}
	    	}
    
}
	
	//判定该客户是否为当前操作人辖内的客户
	//********* by sujm 2012-12-20
	public void latentApplyInfoQuery(){
    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Connection conn=null;
		Statement statement=null;
		ResultSet rs = null;
		
    	  ActionContext ctx = ActionContext.getContext();
    	  request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			String custId = request.getParameter("cust_id");
			String mainType = request.getParameter("main_type");
    		String flag = null;
    		try{
						conn = dataSource.getConnection();
						statement = conn.createStatement();
						//该客户在您机构是否已经为主办
					String sql = "SELECT * FROM OCRM_F_CI_BELONG_ORG ORG WHERE ORG.CUST_ID = '"+custId+"' AND ORG.INSTITUTION_CODE = '"+auth.getUnitId().toString()+"' and org.main_type = '1' ";
					rs = statement.executeQuery(sql);
					
					if(rs.next()){
						flag = "1";
					}else{
						//该客户在您机构是否已经为协办
						String sql1 = "SELECT * FROM OCRM_F_CI_BELONG_ORG ORG WHERE ORG.CUST_ID = '"+custId+"' AND ORG.INSTITUTION_CODE = '"+auth.getUnitId().toString()+"' and org.main_type = '"+mainType+"' ";
						rs = statement.executeQuery(sql1);
						if(rs.next()){
							flag = "2";
						}
					}
    		}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		JdbcUtil.close(rs, statement, conn);
	    		if("1".equals(flag)){
	    			throw new BizException(1,0,"100010","该客户在您机构已经为主办，无需认领!");
		    	}else if("2".equals(flag)){
		    		throw new BizException(1,0,"100010","该客户在您机构已经为协办，无需认领!!");
		    	}
	    	}
	}
}