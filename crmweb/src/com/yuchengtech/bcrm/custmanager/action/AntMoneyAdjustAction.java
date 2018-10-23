package com.yuchengtech.bcrm.custmanager.action;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;


import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
/**
 * 
 * @description :反洗钱风险等级调整--流程客户显示页面action
 *
 * @author : zhaolong
 * @date : 2016-1-18 下午2:32:08
 */
@SuppressWarnings("serial")
@Action("/antMoneyAdjust")
public class AntMoneyAdjustAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	public void prepare() {


		    	ActionContext ctx = ActionContext.getContext();
				request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
				String custGrade = request.getParameter("CUST_GRADE");
				String instanceid =request.getParameter("instanceid");
				String custId = request.getParameter("CUST_ID");
				//0是标示，1是用户ID，2是当前风险等级，3是审核风险等级，4 客户类型（流程处理中显示客户信息分辨是个人还是企业） 5.流程发起人ID  6.是审核结束表中的数据总数
				String[] instanceids =instanceid.split("_");
				SimpleDateFormat sdf = new SimpleDateFormat("MM");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
				String month = sdf.format(new Date());
				int year = Integer.valueOf((sdf2.format(new Date())));
				StringBuffer sb=new StringBuffer("SELECT DISTINCT " + 
						"C.CUST_ID, C.CORE_NO, C.CUST_NAME, C.IDENT_TYPE, C.IDENT_NO, C.CUST_TYPE, C.CREATE_DATE, C.CUST_STAT, " +
						"M.INSTITUTION_NAME AS ORG_NAME, M.MGR_NAME, T.BELONG_TEAM_HEAD_NAME, td.DATE_DIFF, " +
						"P.CITIZENSHIP, P.CAREER_TYPE, P.BIRTHDAY, " +
						" decode(G.CUST_GRADE,'H','"+instanceids[2]+"','"+instanceids[2]+"') CUST_GRADE," +
						" decode(G.CUST_GRADE,'H','"+instanceids[3]+"','"+instanceids[3]+"') CUST_GRADE_FP," +
						" CGT.INSTRUCTION,"+
						"O.BUILD_DATE, O.NATION_CODE, " +
						"t9.ident_value IDENT_TYPE1, t9.ident_no INDENT_NO1, t9.IDENT_EXPIRED_DATE IDENT_EXPIRED_DATE1, " +
						"t10.ident_value IDENT_TYPE2, t10.ident_no INDENT_NO2, t10.IDENT_EXPIRED_DATE IDENT_EXPIRED_DATE2, " +
						//"FXQ.FXQ006," +
						"OCT.FLAG_AGENT," +//是否为代理开户
						" FXQ.FXQ008, FXQ.FXQ009, FXQ.FXQ007, FXQ.FXQ021, FXQ.FXQ022, FXQ.FXQ023, FXQ.FXQ024, FXQ.FXQ025," +
						"AGE.AGENT_NAME, AGE.AGENT_NATION_CODE, AGE.IDENT_TYPE AGE_IDENT_TYPE, AGE.IDENT_NO AGE_IDENT_NO, AGE.TEL, fc.FLAG,fc.STAT_FP, " +
						
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
						"	LEFT join OCRM_F_CHANGE_GRADE_TEMP CGT ON CGT.CUST_ID=C.CUST_ID " +
						"	LEFT JOIN ocrm_f_ci_agent_tmp OCT ON OCT.CUST_ID=C.CUST_ID "+
						" where 1=1 "+
						" AND C.CUST_ID='"+custId+"' AND CGT.INSTANCEID='"+instanceid+"'"
							);
				SQL=sb.toString();//赋值给sql
				datasource=  ds;//赋值数据源
				setPrimaryKey("C.CUST_ID desc ");//排序	    
	
	}
	
	
	  /**
	   * 反洗钱模块     打印预览页面  获取审批状态
	   */
	   public void getspstatus(){
	   	try{
   			ActionContext ctx = ActionContext.getContext();
   			request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
   			String instanceid = request.getParameter("instanceid");
   			StringBuilder sb = new StringBuilder();
			sb.append("select spstatus from ( select spstatus,instanceid from WF_INSTANCE_END  where  instanceid='"+instanceid+"' union select spstatus,instanceid from WF_WORKLIST where  instanceid='"+instanceid+"') ");
   			QueryHelper queryHelper = new QueryHelper(sb.toString(), ds.getConnection());
   			if(this.json!=null){
           		this.json.clear();
   			}else {
           		this.json = new HashMap<String,Object>(); 
           	}
   			this.json.put("json",queryHelper.getJSON());
   		}catch(Exception e){
	   		e.printStackTrace();
	   	}
	   }
	   

}
