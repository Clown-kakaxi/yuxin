package com.yuchengtech.bcrm.custmanager.action;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.sql.Result;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.common.DateUtils;
import com.yuchengtech.bcrm.custmanager.model.AcrmAAntiDqshInfo;
import com.yuchengtech.bcrm.custmanager.model.AcrmAAntiTargetFact;
import com.yuchengtech.bcrm.custmanager.model.AcrmACustFxqIndex;
import com.yuchengtech.bcrm.custmanager.service.AcrmACustFxqIndexService;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiGrade;
import com.yuchengtech.bcrm.sales.model.OcrmFMkMktActivity;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
/**
 * 
 * @ClassName: CustomerAntMoneyQueryAction
 * @Description: 客户复评Action
 *    客户查询去除数据权限的代码控制，改为由后台数据过滤器配置 by :sujm 20140821
 * @author luyy
 * @date 2014-7-17  
 *
 */
@SuppressWarnings("serial")
@Action("customerReviewQuery")
public class CustomerReviewQueryAction extends CommonAction {

	@Autowired
    private  AcrmACustFxqIndexService  service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
    @Autowired
	public void init(){
	  	model = new OcrmFMkMktActivity(); 
		setCommonService(service);
	}
	
	public void prepare(){
		/*
		StringBuffer sb=new StringBuffer("SELECT DISTINCT " + 
			"C.CUST_ID, C.CORE_NO, C.CUST_NAME, C.IDENT_TYPE, C.IDENT_NO, C.CUST_TYPE, C.CREATE_DATE, C.CUST_STAT, " +
			"M.INSTITUTION_NAME AS ORG_NAME, M.MGR_NAME, T.BELONG_TEAM_HEAD_NAME, td.DATE_DIFF, " +
			"P.CITIZENSHIP, P.CAREER_TYPE, P.BIRTHDAY, G.CUST_GRADE, " +
			"O.BUILD_DATE, O.NATION_CODE, " +
			"t9.ident_value IDENT_TYPE1, t9.ident_no INDENT_NO1, t9.IDENT_EXPIRED_DATE IDENT_EXPIRED_DATE1, " +
			"t10.ident_value IDENT_TYPE2, t10.ident_no INDENT_NO2, t10.IDENT_EXPIRED_DATE IDENT_EXPIRED_DATE2, " +
			"OCT.FLAG_AGENT," +//客户是否为代理开户
			//客户是否为代理开户 更改为：ocrm_f_ci_agent_tmp 字段：FLAG_AGENT 
			//"FXQ.FXQ006," +
			" FXQ.FXQ008, FXQ.FXQ009, FXQ.FXQ007, FXQ.FXQ021, FXQ.FXQ022, FXQ.FXQ023, FXQ.FXQ024, FXQ.FXQ025," +
			"AGE.AGENT_NAME, AGE.AGENT_NATION_CODE, AGE.IDENT_TYPE AGE_IDENT_TYPE, AGE.IDENT_NO AGE_IDENT_NO, AGE.TEL, fc.FLAG,fc.STAT_FP, " +
			" CASE " +
            " WHEN O.ENT_SCALE_CK IS NULL THEN " +
            " nvl(trim(O.ENT_SCALE_RH), 'CS04') " + //--企业规模（人行） 
            " ELSE "+
             " NVL(trim(O.ENT_SCALE_CK), 'CS04') " + //--企业规模（存款） 
             " END  AS ENT_SCALE_CK," +//--企业规模
             " FUN_CHANGE_CODE(o.In_Cll_Type,'FXQ020') IN_CLL_TYPE, " +//--行业分类
			" P.if_org_sub_type as IF_ORG_SUB_TYPE_PER, " +
			" O.IF_ORG_SUB_TYPE AS IF_ORG_SUB_TYPE_ORG "+
			" from ACRM_F_CI_CUSTOMER C " +
			" LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR M ON M.CUST_ID = C.CUST_ID " +
			" LEFT JOIN OCRM_F_CM_CUST_MGR_INFO T ON T.CUST_MANAGER_ID = M.MGR_ID " +
			//添加自贸区
			"LEFT JOIN ACRM_F_CI_PERSON P ON P.CUST_ID=C.CUST_ID " +
			"LEFT JOIN ACRM_F_CI_ORG O ON O.CUST_ID=C.CUST_ID " +
			"left join (" +
			"      select t.cust_id,t.IDENT_no,t.IDENT_EXPIRED_DATE,t1.f_value ident_value " +
			"      from acrm_f_ci_cust_identifier t " +
			"      left join (select * from ocrm_sys_lookup_item where f_lookup_id='XD000040') t1 " +
			"      on t.IDENT_TYPE=t1.F_CODE where IS_OPEN_ACC_IDENT='Y'" +
			"      ) t9 on C.cust_id=t9.cust_id " +
			"left join (" +
			"     select t.cust_id,t.IDENT_no,t.IDENT_EXPIRED_DATE,t1.f_value ident_value " +
			"     from acrm_f_ci_cust_identifier t " +
			"     left join (select * from ocrm_sys_lookup_item where f_lookup_id='XD000040') t1 " +
			"      on t.IDENT_TYPE=t1.F_CODE where (IS_OPEN_ACC_IDENT <> 'Y' OR IS_OPEN_ACC_IDENT IS NULL) " +
			"      AND ident_type NOT IN ('V','15X','W','Y')" +
			"      ) t10 on C.cust_id=t10.cust_id " +
			"left join ACRM_F_SYS_CUST_FXQ_INDEX FXQ on FXQ.CUST_ID=C.CUST_ID " +
			"LEFT JOIN ACRM_F_CI_AGENTINFO AGE ON AGE.CUST_ID=C.CUST_ID " +
			"left JOIN ACRM_F_CI_GRADE G ON G.CUST_ID=C.CUST_ID and G.cust_grade_type='01' " +
			"INNER JOIN ACRM_A_FACT_FXQ_CUSTOMER fc ON fc.CUST_ID = C.CUST_ID " +//判断客户是否是新老客户
			"LEFT join (select cust_id, case when diff > 5 and diff <= 10 then '黄色预警' " +
			"			                        when diff > 10 then '红色预警' end DATE_DIFF " +
			
			
			"			            from (select ROUND(TO_NUMBER(sysdate - create_date)) as diff, cust_id from ACRM_F_CI_CUSTOMER)) td on td.cust_id=c.cust_id " +
			" LEFT JOIN ocrm_f_ci_agent_tmp OCT ON OCT.CUST_ID=C.CUST_ID "+    //客户是否为代理开户 
			" where 1=1 ");
		*/
			/*" AND ((M.MGR_ID IN "+
			       " (SELECT A1.ACCOUNT_NAME "+
			           " FROM ADMIN_AUTH_ACCOUNT A1 "+
			         "  WHERE A1.ACCOUNT_NAME = '"+auth.getUserId()+"' "+
			             " OR A1.BELONG_TEAM_HEAD = '"+auth.getUserId()+"'))) "
				);*/
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT C.CUST_ID,C.CORE_NO,C.CUST_NAME,C.IDENT_TYPE,C.IDENT_NO,C.CUST_TYPE,C.CREATE_DATE,C.CUST_STAT, ")
		.append("M.INSTITUTION_NAME AS ORG_NAME, ")
		.append("M.MGR_NAME, ")
		.append("T1.USER_NAME BELONG_TEAM_HEAD_NAME, ")
		.append("CASE ")
		.append("	WHEN ROUND(TO_NUMBER(SYSDATE - C.CREATE_DATE)) > 5 AND ROUND(TO_NUMBER(SYSDATE - C.CREATE_DATE)) <= 10 THEN ")
		.append("	'黄色预警' ")
		.append("	WHEN ROUND(TO_NUMBER(SYSDATE - C.CREATE_DATE)) > 10 THEN ")
		.append("	'红色预警' END DATE_DIFF, ")
		.append("P.CITIZENSHIP,P.CAREER_TYPE,P.BIRTHDAY, ")
		.append("G.CUST_GRADE, ")
		.append("O.BUILD_DATE, ")
		.append("O.NATION_CODE, ")
		.append("LI.F_VALUE IDENT_TYPE1, ")
		.append("IDF1.IDENT_NO INDENT_NO1, ")
		.append("IDF1.IDENT_EXPIRED_DATE IDENT_EXPIRED_DATE1, ")
		.append("LI1.F_VALUE IDENT_TYPE2, ")
		.append("IDF2.IDENT_NO INDENT_NO2, ")
		.append("IDF2.IDENT_EXPIRED_DATE IDENT_EXPIRED_DATE2, ")
		.append("CASE WHEN AGE.CUST_ID IS NOT NULL THEN '1' ELSE '0' END FLAG_AGENT, ")
		.append("FXQ.FXQ008,FXQ.FXQ009,FXQ.FXQ007,FXQ.FXQ021,FXQ.FXQ022,FXQ.FXQ023,FXQ.FXQ024,FXQ.FXQ025, ")
		.append("AGE.AGENT_NAME,AGE.AGENT_NATION_CODE,AGE.IDENT_TYPE AGE_IDENT_TYPE,AGE.IDENT_NO AGE_IDENT_NO,AGE.TEL, ")
		.append("FC.FLAG,FC.STAT_FP, ")
		.append("CASE WHEN O.ENT_SCALE_CK IS NULL THEN NVL(O.ENT_SCALE_RH, 'CS04') ELSE NVL(O.ENT_SCALE_CK, 'CS04') END AS ENT_SCALE_CK, ")
		.append("FUN_CHANGE_CODE(O.IN_CLL_TYPE, 'FXQ020') IN_CLL_TYPE, ")
		.append("P.IF_ORG_SUB_TYPE AS IF_ORG_SUB_TYPE_PER, ")
		.append("O.IF_ORG_SUB_TYPE AS IF_ORG_SUB_TYPE_ORG ")
		.append("FROM ACRM_F_CI_CUSTOMER C ")
		.append("LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR M ON M.CUST_ID = C.CUST_ID ")
		.append("LEFT JOIN ADMIN_AUTH_ACCOUNT T ON T.ACCOUNT_NAME = M.MGR_ID ")
		.append("LEFT JOIN ADMIN_AUTH_ACCOUNT T1 ON T.BELONG_TEAM_HEAD = T1.ACCOUNT_NAME ")
		.append("LEFT JOIN ACRM_F_CI_PERSON P ON P.CUST_ID = C.CUST_ID ")
		.append("LEFT JOIN ACRM_F_CI_ORG O ON O.CUST_ID = C.CUST_ID ")
		.append("LEFT JOIN ACRM_F_CI_CUST_IDENTIFIER IDF1 ON C.CUST_ID = IDF1.CUST_ID AND IDF1.IS_OPEN_ACC_IDENT = 'Y' ")
		.append("LEFT JOIN OCRM_SYS_LOOKUP_ITEM LI ON IDF1.IDENT_TYPE = LI.F_CODE AND LI.F_LOOKUP_ID = 'XD000040' ")
		.append("LEFT JOIN ACRM_F_CI_CUST_IDENTIFIER IDF2 ON C.CUST_ID = IDF2.CUST_ID AND (IDF2.IS_OPEN_ACC_IDENT <> 'Y' OR IDF2.IS_OPEN_ACC_IDENT IS NULL) AND IDF2.IDENT_TYPE NOT IN ('V', '15X', 'W', 'Y') ")
		.append("LEFT JOIN OCRM_SYS_LOOKUP_ITEM LI1 ON IDF2.IDENT_TYPE = LI1.F_CODE AND LI1.F_LOOKUP_ID = 'XD000040' ")
		.append("LEFT JOIN ACRM_F_SYS_CUST_FXQ_INDEX FXQ ON FXQ.CUST_ID = C.CUST_ID ")
		.append("LEFT JOIN ACRM_F_CI_AGENTINFO AGE ON AGE.CUST_ID = C.CUST_ID ")
		.append("LEFT JOIN ACRM_F_CI_GRADE G ON G.CUST_ID = C.CUST_ID AND G.CUST_GRADE_TYPE = '01' ")
		.append("INNER JOIN ACRM_A_FACT_FXQ_CUSTOMER FC ON FC.CUST_ID = C.CUST_ID ")
		.append("WHERE FC.STAT_FP IN ('0', '1') ");
		
		for(String key:this.getJson().keySet()){
			if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
				if("ACCT_NO".equals(key)){
					sb.append(" and c.cust_id in (select cust_id from ACRM_F_CI_LOAN_ACT where ACCOUNT like '"+this.json.get(key)+"%')");
				}
				if("ORG_NAME".equals(key)){ 
					sb.append(" and (m.INSTITUTION in (SELECT unitid FROM SYS_UNITS  WHERE UNITSEQ LIKE '%"+this.json.get(key)+"%'))");
				}
				if("BL_NAME".equals(key)&&!"归属业务条线".equals(this.json.get(key))){
					sb.append(" and  (o.org_biz_cust_type in (select distinct bl_ID from ACRM_F_CI_BUSI_LINE t START   WITH bl_ID='"+this.json.get(key)+"' CONNECT BY PRIOR BL_ID=PARENT_ID))");
				}
			}
		}
		
		
		
	/*	for(String key:this.getJson().keySet()){
			if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
				if("ACCT_NO".equals(key)){
					sb.append(" and c.cust_id in (select cust_id from ACRM_F_CI_LOAN_ACT where ACCOUNT like '"+this.json.get(key)+"%')");
				}
				if("ORG_NAME".equals(key)){ 
					sb.append(" and (m.INSTITUTION in (SELECT unitid FROM SYS_UNITS  WHERE UNITSEQ LIKE '%"+this.json.get(key)+"%'))");
				}
				if("BL_NAME".equals(key)&&!"归属业务条线".equals(this.json.get(key))){
					sb.append(" and  (o.org_biz_cust_type in (select distinct bl_ID from ACRM_F_CI_BUSI_LINE t START   WITH bl_ID='"+this.json.get(key)+"' CONNECT BY PRIOR BL_ID=PARENT_ID))");
				}
			}
		}*/
		//添加证件类型与证件号码查询,取证件表上的数据进行查询
		if(null!=this.getJson().get("IDENT_TYPE")&&!"".equals(this.getJson().get("IDENT_TYPE"))
			&& null!=this.getJson().get("IDENT_NO")&&!"".equals(this.getJson().get("IDENT_NO"))){
			sb.append(" and c.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_TYPE = '"+this.json.get("IDENT_TYPE")+"' AND I.IDENT_NO like '%"+this.json.get("IDENT_NO")+"%')");
		}else if(null!=this.getJson().get("IDENT_TYPE")&&!"".equals(this.getJson().get("IDENT_TYPE"))){
			sb.append(" and c.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_TYPE = '"+this.json.get("IDENT_TYPE")+"')");
		}else if(null!=this.getJson().get("IDENT_NO")&&!"".equals(this.getJson().get("IDENT_NO"))){
			sb.append(" and c.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_NO like '%"+this.json.get("IDENT_NO")+"%')");
		}
			
		SQL=sb.toString();
		datasource=ds;
		
		setPrimaryKey("c.CUST_ID desc ");
		
		configCondition("c.CUST_ID","like","CUST_ID",DataType.String);
		configCondition("c.CUST_NAME","like","CUST_NAME",DataType.String);
		configCondition("c.CUST_TYPE","=","CUST_TYPE",DataType.String);
		configCondition("c.CUST_STAT","=","CUST_STAT",DataType.String);
		configCondition("m.MGR_NAME","=","MGR_NAME",DataType.String);
		
		
		configCondition("t9.ident_value","=","IDENT_TYPE1",DataType.String);
		configCondition("t10.ident_value","=","IDENT_TYPE2",DataType.String);
		configCondition("t9.ident_no","=","INDENT_NO1",DataType.String);
		configCondition("t10.ident_no","=","INDENT_NO2",DataType.String);
		configCondition("t9.IDENT_EXPIRED_DATE","=","IDENT_EXPIRED_DATE1",DataType.String);
		configCondition("t10.IDENT_EXPIRED_DATE","=","IDENT_EXPIRED_DATE2",DataType.String);
				
		configCondition("c.FAXTRADE_NOREC_NUM","=","FAXTRADE_NOREC_NUM",DataType.Number);
		configCondition("t.BELONG_TEAM_HEAD","=","MGR_ID1",DataType.String);
		configCondition("t.BELONG_TEAM_HEAD_NAME","=","BELONG_TEAM_HEAD_NAME",DataType.String);
		configCondition("C.CORE_NO","=","CORE_NO",DataType.String);
		configCondition("G.CUST_GRADE","=","CUST_GRADE",DataType.String);
	}
	
	/**
	 * 保存客户反洗钱设置
	 * @throws Exception 
	 */
	 public	String savefp() throws Exception {
			ActionContext ctx = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);

			
//		    String createDate = request.getParameter("createDate");//新旧客户
		    String flag = request.getParameter("flag");//3 新客户 1,2 老客户
		    String custId = request.getParameter("custId");
		    String custName = request.getParameter("custName");
		    String custType = request.getParameter("custType");//2对私，1对公
		    Connection conn=null;
    		Statement statement=null;
    		ResultSet rs = null;
		    //手动预平等级
		    String custGradeFp=request.getParameter("custGradeFp");
		    String custGrade=request.getParameter("custGrade");
			
	 //   if("H".equals(custGrade)||"H".equals(custGradeFp)){
	    	//触发流程
	    	
			int times = 0;
	   		
	   		//list办理中流程，list2已办结流程
	   		List list1 = service.getBaseDAO().findByNativeSQLWithIndexParam(" select * from WF_MAIN_RECORD where instanceid like '%ANTMONEY%"+custId+"%'");
	   		List list2 = service.getBaseDAO().findByNativeSQLWithIndexParam(" select * from WF_MAIN_RECORDEND where instanceid like '%ANTMONEY3_"+custId+"%'");
	   		Map<String, Object> map1 = new HashMap<String, Object>();
	   		if(list1!= null && list1.size() > 0){
	   			//如果有办理中流程，不让再提交
	   			//map1.put("existTask", "existTask");
	   			//如果有办理中流程，不让再提交
	   	    	response.getWriter().write("{\"existTask\":\"existTask\"}");
	   	    	response.getWriter().flush(); 	
	   		}else{
	   			if(list2!= null && list2.size() > 0){
	   				times = list2.size();
	   			}
	   				Map<String, Object> paramMap = new HashMap<String, Object>();
	   				List<?> list101 = auth.getRolesInfo();
	   				for (Object m : list101) {
	   					Map<?, ?> map = (Map<?, ?>) m;// map自m引自list，ROLE_CODE为键, R000为值
	   					paramMap.put("role", map.get("ROLE_CODE"));
	   				}
	   	   			//acrmAAntiDqshInfo.getCustGrade() 为原始风险等级
	   				//acrmAAntiDqshInfo.getCustGradeCheck() 审核风险等级
		    		//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息  0是标示，1是用户ID，2是当前风险等级，3复评等级，4 客户类型（流程处理中显示客户信息分辨是个人还是企业） 5.流程发起人ID  6.是审核结束表中的数据总数 cust_type 
		    		String instanceid = "ANTMONEY3_"+custId+"_"+custGrade+"_"+custGradeFp+"_"+custType+"_"+auth.getUserId()+"_"+times;
		       		String name =  custName;
		    		String jobName = "反洗钱复评_"+name;//自定义流程名称
		    		service.initWorkflowByWfidAndInstanceid("135", jobName, paramMap,
		    				instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		    		response.getWriter().write("{\"instanceid\":\""+instanceid+"\"}");
	   	    		response.getWriter().flush();
	   	    		//修改复评状态
	   	    		try {
						conn = ds.getConnection();
						statement = conn.createStatement();
						// 修改复评状态
						String sql = "update  ACRM_A_FACT_FXQ_CUSTOMER SET "
								+ " stat_fp='1' " + " ,MGR_ID_FP='"
								+ auth.getUserId() + "' " + " ,TIME_FP =SYSDATE "
								+ " ,FLAG_FP='1' " + " ,CUST_GRADE_FP='"
								+ custGradeFp + "' " + " WHERE  CUST_ID='" + custId
								+ "' ";
						int count = statement.executeUpdate(sql);
					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("/crmweb/src/com/yuchengtech/bcrm/custmanager/action/CustomerReviewQueryAction.java----出错2");
					} finally {
						JdbcUtil.close(rs, statement, conn);
					}
	   			
	   		}
	    	
	    	
	    	
		   //注释内容为 不提交流程保存方案	
		   //}else if("M".equals(custGrade)||"L".equals(custGrade)){
		    	/*//如果
		    	//不触发流程直接修改
				if(custGradeFp!=null){
					try{
						
						AuthUser auth= (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();//获取当前用户信息
						conn = ds.getConnection();
						statement = conn.createStatement();
						//等级有调整的话就保存调整记录  
						if(("L".equals(custGrade)&&"M".equals(custGradeFp))){
							SQL = "select *from  ACRM_F_CI_GRADE where cust_grade_type='01' and cust_id='"+custId+"'";
							rs = statement.executeQuery(SQL);
							String luateDateOld="";
							String LastUpdateUserOld="";
							SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
							if(rs.next()){
								 luateDateOld=sdf.format(rs.getDate("LAST_UPDATE_TM")) ;//获取上次更新的时间
								 LastUpdateUserOld=rs.getString("LAST_UPDATE_USER");//获取最后更新人
							}
							
							String sql2="insert into  ACRM_A_ANTI_CHANGE_GRADE_HIS("+
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
									"'"+custId+"'" +
									",'01'" +
									",'"+custGrade+"'" +
									",'"+luateDateOld+"'" +//评级时间
									",'"+LastUpdateUserOld+"'" +//评级人
									",'"+custGradeFp+"'" +
									",'"+(new DateUtils().getCurrentDateTimeF().toString())+"'" +
									",'"+auth.getUserId()+"')";
							
							int count = statement.executeUpdate(sql2);	
							//String sql3="update ACRM_F_CI_GRADE set cust_grade= '"+custGradeFp+"',LAST_UPDATE_TM= SYSDATE,LAST_UPDATE_USER='"+auth.getUserId()+"'  where cust_id='"+custId+"' and cust_grade_type='01'";
							String evaluateDateNew=(new DateUtils().getCurrentDateTimeF().toString());
							
							String sql3="update ACRM_F_CI_GRADE set cust_grade= '"+custGradeFp+"',LAST_UPDATE_TM=SYSDATE,EFFECTIVE_DATE=SYSDATE,EVALUATE_DATE=SYSDATE,LAST_UPDATE_USER='"+auth.getUserId()+"'  where cust_id='"+custId+"' and cust_grade_type='01'";
							int count3 = statement.executeUpdate(sql3);;//通过是审批修改客户等级表
							
						}
						
						//修改复评状态
						String sql="update  ACRM_A_FACT_FXQ_CUSTOMER SET "+ 
								" stat_fp='2' "+
								" ,MGR_ID_FP='"+auth.getUserId()+"' "+
								" ,TIME_FP =SYSDATE "+
								" ,FLAG_FP='2' "+
								" ,CUST_GRADE_FP='"+custGradeFp+"' "+
								" WHERE  CUST_ID='"+custId+"' ";
						int count = statement.executeUpdate(sql);
						
						
					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("/crmweb/src/com/yuchengtech/bcrm/custmanager/action/CustomerReviewQueryAction.java----出错" );
					}finally{
						JdbcUtil.close(rs, statement, conn);
					}
				
			}
			*/
		    //	}

		
			
			  
		    // 是1否0
		    Map<String, String> map = new HashMap<String, String>();
		    //客户是否为代理开户 更改为：ocrm_f_ci_agent_tmp 字段：FLAG_AGENT 
		    //map.put("fxq006", request.getParameter("FXQ006"));
		    map.put("fxq007", request.getParameter("FXQ007"));
		    map.put("fxq008", request.getParameter("FXQ008"));
		    map.put("fxq009", request.getParameter("FXQ009"));
		    map.put("fxq010", request.getParameter("FXQ010"));
		    
		    map.put("fxq011", request.getParameter("FXQ011"));
		    map.put("fxq012", request.getParameter("FXQ012"));
		    map.put("fxq013", request.getParameter("FXQ013"));
		    map.put("fxq014", request.getParameter("FXQ014"));
		    map.put("fxq015", request.getParameter("FXQ015"));
		    map.put("fxq016", request.getParameter("FXQ016"));
		    
		    map.put("fxq021", request.getParameter("FXQ021"));
		    map.put("fxq022", request.getParameter("FXQ022"));
		    map.put("fxq023", request.getParameter("FXQ023"));
		    map.put("fxq024", request.getParameter("FXQ024"));
		    map.put("fxq025", request.getParameter("FXQ025"));
		    AcrmACustFxqIndex index = new AcrmACustFxqIndex();
		    // 删除原有AcrmACustFxqIndex
	    	service.batchUpdateByName(" delete from AcrmACustFxqIndex s where s.custId='"+custId+"'", null);
		    //添加AcrmACustFxqIndex
	    	index.setCustId(custId);
	    	//客户是否为代理开户 更改为：ocrm_f_ci_agent_tmp 字段：FLAG_AGENT 
	    	//index.setFxq006(request.getParameter("FXQ006"));
	    	index.setFxq007(request.getParameter("FXQ007"));
	    	index.setFxq008(request.getParameter("FXQ008"));
	    	index.setFxq009(request.getParameter("FXQ009"));
	    	index.setFxq010(request.getParameter("FXQ010"));
	    	index.setFxq011(request.getParameter("FXQ011"));
	    	index.setFxq012(request.getParameter("FXQ012"));
	    	index.setFxq013(request.getParameter("FXQ013"));
	    	index.setFxq014(request.getParameter("FXQ014"));
	    	index.setFxq015(request.getParameter("FXQ015"));
	    	index.setFxq016(request.getParameter("FXQ016"));
	    	index.setFxq021(request.getParameter("FXQ021"));
	    	index.setFxq022(request.getParameter("FXQ022"));
	    	index.setFxq023(request.getParameter("FXQ023"));
	    	index.setFxq024(request.getParameter("FXQ024"));
	    	index.setFxq025(request.getParameter("FXQ025"));
	    	service.save(index);
		    
		    
    		String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		    for(String key:map.keySet()){
		    	if(null!=map.get(key)&&!map.get(key).equals("")){
		    		try {
			    		conn = ds.getConnection();
						statement = conn.createStatement();
						
			    		// 删除客户原有客户反洗钱指标
//				    	service.batchUpdateByName(" delete from AcrmATargetFact s where s.custId='"+custId+"'", null);
				    	//添加新的客户反洗钱指标
				    	String sql = "select * from OCRM_F_CI_BELONG_ORG o where o.cust_id='"+custId+"'"; 
				    	rs = statement.executeQuery(sql);
				    	String orgId = "";
				    	if(rs.next()){
				    		orgId = rs.getString("INSTITUTION_CODE");
				    	}
				    	AcrmAAntiTargetFact list = new AcrmAAntiTargetFact();
				    	list.setCustId(custId);
				    	list.setEtlDate(new Date());
				    	/**
				    	 * 客户是否为代理开户
				    	 */
				    	//客户是否为代理开户 更改为：ocrm_f_ci_agent_tmp 字段：FLAG_AGENT 
				    	//
				    	/*if("fxq006".equals(key)){
				    		if("3".equals(flag)){//新客户
				    			saveList(list,"FXQ11006",rs,statement,custId,orgId,map.get(key),false);
					    	}else{
					    		saveList(list,"FXQ12006",rs,statement,custId,orgId,map.get(key),false);
					    	}
				    	}*/
				    	/**
				    	 * 客户办理的业务FXQ007(对私),FXQ025(对公)
				    	 */
				    	if("fxq007".equals(key) || "fxq025".equals(key)){
				    		//新客户
				    		if("3".equals(flag)){
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11007",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21011",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    		//老客户	
					    	}else{
					    		if("2".equals(custType)){//自然人老客户
					    			saveList(list,"FXQ12007",rs,statement,custId,orgId,map.get(key),false);
					    		}
					    		if("1".equals(custType)){//非自然人老客户
					    			saveList(list,"FXQ22011",rs,statement,custId,orgId,map.get(key),false);
					    		}
					    	}
				    	}
				    	/**
				    	 * 是否涉及风险提示信息或权威媒体报道信息  FXQ008
				    	 */
				    	if("fxq008".equals(key)){
				    		//新客户
				    		if("3".equals(flag)){
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11008",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21012",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    		//老客户	
					    	}else{
					    		if("2".equals(custType)){//自然人老客户
					    			saveList(list,"FXQ12008",rs,statement,custId,orgId,map.get(key),false);
					    		}
					    		if("1".equals(custType)){//非自然人老客户
					    			saveList(list,"FXQ22012",rs,statement,custId,orgId,map.get(key),false);
					    		}
					    	}
				    	}
				    	/**
				    	 * 客户或其亲属、关系密切人等是否属于外国政要  FXQ009
				    	 */
				    	if("fxq009".equals(key)){
//				    		if("1".equals(flag)){//新客户
//				    			saveList(list,"FXQ11009",rs,statement,custId,orgId,map.get(key),false);
//					    	}else{
//					    		saveList(list,"FXQ12009",rs,statement,custId,orgId,map.get(key),false);
//					    	}
				    		//新客户
				    		if("3".equals(flag)){
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11009",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21010",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    		//老客户	
					    	}else{
					    		if("2".equals(custType)){//自然人老客户
					    			saveList(list,"FXQ12009",rs,statement,custId,orgId,map.get(key),false);
					    		}
					    		if("1".equals(custType)){//非自然人老客户
					    			saveList(list,"FXQ22010",rs,statement,custId,orgId,map.get(key),false);
					    		}
					    	}
				    	}
				    	/**
				    	 * 反洗钱交易监测记录 FXQ010
				    	 */
				    	if("fxq010".equals(key)){
				    		if(!"3".equals(flag)){//老客户
				    			if("2".equals(custType)){//自然人老客户
				    				saveList(list,"FXQ12010",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人老客户
				    				saveList(list,"FXQ22013",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    		}else if("3".equals(flag)){//新客户
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11010",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21013",rs,statement,custId,orgId,map.get(key),true);
				    			}				    			
				    		}
				    	}
				    	/**
				    	 * 是否被列入中国发布或承认的应实施反洗钱监控措施的名单 FXQ011
				    	 */
				    	if("fxq011".equals(key)){
				    		if(!"3".equals(flag)){//老客户
				    			if("2".equals(custType)){//自然人老客户
				    				saveList(list,"FXQ12011",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人老客户
				    				saveList(list,"FXQ22014",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    		}else if("3".equals(flag)){//新客户
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11011",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21014",rs,statement,custId,orgId,map.get(key),true);
				    			}				    			
				    		}
				    	}
				    	/**
				    	 * 是否发生具有异常特征的大额现金交易 FXQ012
				    	 */
				    	if("fxq012".equals(key)){
				    		if(!"3".equals(flag)){//老客户
				    			if("2".equals(custType)){//自然人老客户
				    				saveList(list,"FXQ12012",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人老客户
				    				saveList(list,"FXQ22015",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    		}else if("3".equals(flag)){//新客户
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11012",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21015",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    		}
				    	}
				    	/**
				    	 * 是否发生具有异常特征的非面对面交易FXQ013
				    	 */
				    	if("fxq013".equals(key)){
				    		if(!"3".equals(flag)){//老客户
				    			if("2".equals(custType)){//自然人老客户
				    				saveList(list,"FXQ12013",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人老客户
				    				saveList(list,"FXQ22016",rs,statement,custId,orgId,map.get(key),true);
				    		    }  
				    		}else if("3".equals(flag)){//新客户
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11013",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21016",rs,statement,custId,orgId,map.get(key),true);
				    		    }  
				    		}	
				    	}
				    	/**
				    	 * 是否存在多次涉及跨境异常交易报告FXQ014
				    	 */
				    	if("fxq014".equals(key)){
				    		if(!"3".equals(flag)){//老客户
					    		if("2".equals(custType)){//自然人老客户
					    				saveList(list,"FXQ12014",rs,statement,custId,orgId,map.get(key),true);
					    		   }
					    		if("1".equals(custType)){//非自然人老客户
					    			saveList(list,"FXQ22017",rs,statement,custId,orgId,map.get(key),true);
					    		 }
				    		}else if("3".equals(flag)){//新客户
					    		if("2".equals(custType)){//自然人新客户
					    				saveList(list,"FXQ11014",rs,statement,custId,orgId,map.get(key),true);
					    		   }
					    		if("1".equals(custType)){//非自然人新客户
					    			saveList(list,"FXQ21017",rs,statement,custId,orgId,map.get(key),true);
					    		 }
				    		}
				    	}
				    	
				    	/**
				    	 * 代办业务是否存在异常情况FXQ015
				    	 */
				    	if("fxq015".equals(key)){
				    		if(!"3".equals(flag)){//老客户
					    		if("2".equals(custType)){//自然人老客户
					    			saveList(list,"FXQ12015",rs,statement,custId,orgId,map.get(key),true);
					    		}
					    		if("1".equals(custType)){//非自然人老客户
					    			saveList(list,"FXQ22018",rs,statement,custId,orgId,map.get(key),true);
					    		}
				    		}else if("3".equals(flag)){//新客户
					    		if("2".equals(custType)){//自然人新客户
					    			saveList(list,"FXQ11015",rs,statement,custId,orgId,map.get(key),true);
					    		}
					    		if("1".equals(custType)){//非自然人新客户
					    			saveList(list,"FXQ21018",rs,statement,custId,orgId,map.get(key),true);
					    		}
				    		}
				       }
				    	
				    	/**
				    	 * 是否频繁进行异常交易 FXQ016
				    	 */
				    	if("fxq016".equals(key)){
				    		if(!"3".equals(flag)){//老客户
				    			if("2".equals(custType)){//自然人老客户
				    				saveList(list,"FXQ12016",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人老客户
				    				saveList(list,"FXQ22019",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    		}else if("3".equals(flag)){//新客户
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11016",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21019",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    		}
				    	}
				    	
				    	/**
				    	 * 与客户建立业务关系的渠道FXQ021
				    	 */
				    	if("fxq021".equals(key)){
				    		if("1".equals(custType)){//非自然人
				    			if("3".equals(flag)){//非自然人新客户
				    				saveList(list,"FXQ21006",rs,statement,custId,orgId,map.get(key),false);
				    			}else{//非自然人老客户
				    				saveList(list,"FXQ22006",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    		}
				    	}
				    	/**
				    	 * 是否在规范证券市场上市FXQ022
				    	 */
				    	if("fxq022".equals(key)){
				    		if("1".equals(custType)){//非自然人
				    			if("3".equals(flag)){//非自然人新客户
				    				saveList(list,"FXQ21007",rs,statement,custId,orgId,map.get(key),false);
				    			}else{//非自然人老客户
				    				saveList(list,"FXQ22007",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    		}
				    	}
				    	
				    	/**
				    	 * 客户的股权或控制权结构FXQ023
				    	 */
				    	if("fxq023".equals(key)){
				    		if("1".equals(custType)){//非自然人
				    			if("3".equals(flag)){//非自然人新客户
				    				saveList(list,"FXQ21008",rs,statement,custId,orgId,map.get(key),false);
				    			}else{//非自然人老客户
				    				saveList(list,"FXQ22008",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    		}
				    	}
				    	
				    	/**
				    	 * 客户是否存在隐名股东或匿名股东FXQ024
				    	 */
				    	if("fxq024".equals(key)){
				    		if("1".equals(custType)){//非自然人
				    			if("3".equals(flag)){//非自然人新客户
				    				saveList(list,"FXQ21009",rs,statement,custId,orgId,map.get(key),false);
				    			}else{//非自然人老客户
				    				saveList(list,"FXQ22009",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    		}
				    	}
		    		} catch (SQLException e) {
						e.printStackTrace();
					}finally{
						JdbcUtil.close(rs, statement, conn);
					}
		    	}
		    }
			return "success";
		 }
	 /**
	  * 客户反洗钱指标页面设值保存到ACRM_A_ANTI_TARGET_FACT
	  * @param list
	  * @param indexCode
	  * @param rs
	  * @param statement
	  * @param custId
	  * @param orgId
	  * @throws SQLException
	  */
	 public void saveList(AcrmAAntiTargetFact list,String indexCode,ResultSet rs,Statement statement,String custId,String orgId,String key,boolean flag) throws SQLException{

		 list.setIndexCode(indexCode);
			list.setIndexId(indexCode+new SimpleDateFormat("yyyyMMdd").format(new Date())+custId);
			list.setOrgId(orgId);
//			if(flag){//合规处指标不需要flag标记
//				list.setFlag(null);
//			}else{
				list.setFlag(key);
//			}
			String[] str = key.split(",");
			StringBuffer sb = new StringBuffer();
			for(String s:str){
				sb.append(",'").append(s.toString()).append("'");
			}
			String sql = " select decode(sum((decode(v.high_flag,'1',1,0))),null,0,sum((decode(v.high_flag,'1',1,0)))) as high_flag," +
					" decode(sum(v.index_score*v.index_right*0.01),null,0,sum(v.index_score*v.index_right*0.01)) as amount"+
					" from OCRM_F_CI_ANTI_MONEY_INDEX_VAR v" +
					" where v.index_code ='"+indexCode+"' and v.index_value in ("+sb.toString().substring(1)+")";
			rs = statement.executeQuery(sql);
			if(rs.next()){
				list.setHighFlag(BigDecimal.valueOf(Double.parseDouble(rs.getString("HIGH_FLAG").toString())));
				list.setIndexValue(BigDecimal.valueOf(Double.parseDouble(rs.getString("AMOUNT").toString())));
			}
			// 删除客户原有客户反洗钱指标
	    	service.batchUpdateByName(" delete from AcrmAAntiTargetFact s where s.custId='"+custId+"' and s.indexCode = '"+indexCode+"'", null);
			
	    	service.save(list);
	 }
	
	 
	 

}
