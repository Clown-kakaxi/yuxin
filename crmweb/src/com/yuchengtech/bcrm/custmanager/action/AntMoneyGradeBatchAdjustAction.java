package com.yuchengtech.bcrm.custmanager.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.common.DateUtils;
import com.yuchengtech.bcrm.custmanager.model.AcrmAAntiChangeGradeHi;
import com.yuchengtech.bcrm.customer.customergroup.model.OcrmFCiRelateCustBase;
import com.yuchengtech.bcrm.customer.customergroup.service.GroupMemberEditService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;
/**
 *	 客户群-反洗钱批量调整Action
 * @author Administrator
 *
 */
@ParentPackage("json-default")
@Action(value="/antMoneyGradeBatchAdjust", results={
    @Result(name="success", type="json")
})
public class AntMoneyGradeBatchAdjustAction extends CommonAction{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Autowired
	private GroupMemberEditService groupMemberEditService ;
	public void init(){
    	model = new OcrmFCiRelateCustBase();  
    	setCommonService(groupMemberEditService);
	}
	
  
    //客户群风险等级批量调整
    public HttpHeaders batchUpdate() throws SQLException{
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		Connection conn = null ;
    	Statement stmt = null ;
    	ResultSet rs = null;
		
		//获取DBTABLE_ID和pramas
		String custIds = request.getParameter("custId");
		String fxqRiskLevel=request.getParameter("fxqRiskLevel");
		String sql0 = "";
		int i = 0;
		// 在流程中的不允许更改
		String itemId="";
		//查询所需信息
		String[] custIdArray=custIds.split(",");
			 try {
		        	conn=ds.getConnection();
		    		stmt = conn.createStatement();
		    		
		    		for(int j=0;j<custIdArray.length;j++){
						
		    		
		    			List list = groupMemberEditService.getBaseDAO().findByNativeSQLWithIndexParam(" select * from WF_MAIN_RECORD where instanceid like '%ANTMONEY%"+custIdArray[j]+"%'");
		    			if(list.size()>0){
		    				if(itemId.equals("")){
		    					itemId+=custIdArray[j];
		    				}else{
			    				itemId+=","+custIdArray[j];
		    				}
		    				
		    			}
		    		}
		    		
		    		if(itemId.equals("")){//if
		    			//获取当前时间
						String evaluateDateNew=(new DateUtils().getCurrentDateTimeF().toString());
						for(int j=0;j<custIdArray.length;j++){
							//查询出历史风险等级
							SQL = "select *from  ACRM_F_CI_GRADE where cust_grade_type='01' and cust_id='"+custIdArray[j]+"'";
							 rs = stmt.executeQuery(SQL);
							AcrmAAntiChangeGradeHi ws=new AcrmAAntiChangeGradeHi();
							if (rs.next()){
								ws.setCustId((String)rs.getString("CUST_ID"));
								ws.setCustGradeType((String)rs.getString("CUST_GRADE_TYPE"));//调整前等级 
								
								ws.setCustGradeOld((String)rs.getString("CUST_GRADE"));//客户等级类型 
								
								ws.setEvaluateDateOld(rs.getString("EVALUATE_DATE").toString());// 取等级表等级评定时间 
								ws.setLastUpdateUserOld((String)rs.getString("LAST_UPDATE_USER"));	//取等级表等级评定人  
								
								ws.setCustGradeNew(fxqRiskLevel);	//调整后等级 
								ws.setEvaluateDateNew(evaluateDateNew);	//调整等级后等级评级时间
								ws.setLastUpdateUserNew(auth.getUserId());	//调整等级后等级评级人  
							}
							//添加修改记录
							SQL="insert into  ACRM_A_ANTI_CHANGE_GRADE_HIS("+
											" GRADE_HIS_ID,"+
											" CUST_ID,"+
											" CUST_GRADE_TYPE,"+
											" CUST_GRADE_OLD,"+
											" EVALUATE_DATE_OLD,"+
											" LAST_UPDATE_USER_OLD,"+
											" CUST_GRADE_NEW,"+
											" EVALUATE_DATE_NEW,"+
											" LAST_UPDATE_USER_NEW"+
											" )"+
											" values(" +
											"ID_INDEX_INSTRUCTION.Nextval,"+
											"'"+ws.getCustId()+"'" +
											",'"+ws.getCustGradeType()+"'" +
											",'"+ws.getCustGradeOld()+"'" +
											",'"+ws.getEvaluateDateOld()+"'" +
											",'"+ws.getLastUpdateUserOld()+"'" +
											",'"+fxqRiskLevel+"'" +
											",'"+evaluateDateNew+"'" +
											",'"+auth.getUserId()+"')";
					       stmt.execute(SQL);
					       SQL="update ACRM_F_CI_GRADE set cust_grade= '"+fxqRiskLevel+"',LAST_UPDATE_TM=SYSDATE,EFFECTIVE_DATE=SYSDATE,EVALUATE_DATE=SYSDATE,LAST_UPDATE_USER='"+auth.getUserId()+"'  where cust_id='"+custIdArray[j]+"' and cust_grade_type='01'";
					       stmt.executeUpdate(SQL);
					       i++;
						}//for
		    		}//if
		    		
					
			 }catch (SQLException e) {
	        		throw new BizException(1,2,"1002",e.getMessage());
		        }finally{
		        	JdbcUtil.close(rs, stmt, conn);
		        }
		
		
		        if(this.json!=null)
					this.json.clear();
				else 
					this.json = new HashMap<String,Object>();  
				
				this.json.put("number", String.valueOf(i));
				this.json.put("itemId", itemId);
				
				
				return new DefaultHttpHeaders("success").disableCaching();
    	
    }
}
