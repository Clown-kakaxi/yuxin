package com.yuchengtech.bcrm.customer.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiAttentionCustInfo;
import com.yuchengtech.bcrm.customer.service.CustConcernOperService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;

//import com.yuchengtech.crm.service.custumer.CrmFciRelativesService;



/**
 * @author mahcao 客户基本信息操作service
 */
@SuppressWarnings("serial")
@Action("/custConcernOper")
public class CustConcernOperAction  extends CommonAction{
	
	@Autowired
	private DataSource dsOracle;


    @Autowired
    private CustConcernOperService custConcernOperService ;
    
//	 新增一条记录
//     POST /actionName
  
    @SuppressWarnings("finally")
	public DefaultHttpHeaders create(){
    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currenUserId = auth.getUserId();
		
		Connection conn=null;
		Statement statement=null;
		ResultSet rs = null;
		
    	  ActionContext ctx = ActionContext.getContext();
    	  request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    		String ss=request.getParameter("condition");
    		String sss = "";
    		boolean flag = true;
    		try{
	    		String[] strarray = ss.split(",");
	    		for (int i = 0; i < strarray.length; i++) {
					sss=strarray[i];
					
						conn = dsOracle.getConnection();
						statement = conn.createStatement();
					
					String sql = "select * from ocrm_f_ci_attention_cust_info t where t.cust_id ='"+sss+"' and user_id = '"+currenUserId+"'";
					rs = statement.executeQuery(sql);
					
					if(rs.next()){
						flag = false;
					}else{
						model = new OcrmFCiAttentionCustInfo(); 
			    		custConcernOperService.save(model,sss);
					}
				
	    		}
    		}catch(Exception e){
	    		e.printStackTrace();
	    		addActionMessage("New Record created failure");
	    	}finally{
	    		JdbcUtil.close(rs, statement, conn);
	    		if(flag == false){
	    			throw new BizException(1,0,"100010","客户"+sss+"已经为关注客户!");
	    		}
	    		return new DefaultHttpHeaders("success");
	    	}
    
    }
}