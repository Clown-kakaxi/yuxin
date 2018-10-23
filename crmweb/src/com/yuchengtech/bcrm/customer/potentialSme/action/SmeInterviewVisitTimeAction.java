package com.yuchengtech.bcrm.customer.potentialSme.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
/**
 * SME--prospect阶段查询拜访日期
 * @author denghj
 * @since 2015-09-14
 */
@SuppressWarnings("serial")
//@Action(value="/smeInterviewVisitTime",results={@Result(name="success", type="json")})
@Action("/smeInterviewVisitTime")
public class SmeInterviewVisitTimeAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
	/*
	 * 设置查询visit_timeSQL
	 */
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
//    	String sql = "select re.id," +
//                "case when (select AC.cust_id from ACRM_F_CI_CROSSINDEX ac where ac.SRC_SYS_NO = 'DMS' and ac.SRC_SYS_CUST_NO = re.cust_id) is null then re.cust_id  else (select AC.cust_id from ACRM_F_CI_CROSSINDEX ac where ac.SRC_SYS_NO = 'DMS' and ac.SRC_SYS_CUST_NO = re.cust_id) end  cust_id," +
//    			"re.cust_name," +
//    			"re.mgr_id," +
//    			"re.mgr_name," +
//    			"to_date(to_char(re.visit_time, 'yyyy/mm/dd'), 'yyyy/mm/dd') as visit_time," +
//    			"re.visit_type," +
//    			"reco.call_time," +
//    			"reco.interviewee_name," +
//    			"reco.interviewee_phone," +
//    			"reco.join_person," +
//    			"re.review_state," +
//    			"reco.res_custsource " +    			
//    			"from ocrm_f_interview_record re " +
//    			"left join (select oldre.task_number," +
//    			"to_date(to_char(oldre.call_time, 'yyyy/mm/dd'),'yyyy/mm/dd') as call_time," +
//    			"oldre.interviewee_name," +
//    			"oldre.interviewee_post," +
//    			"oldre.interviewee_phone," +
//    			"oldre.join_person," +
//    			"null res_custsource " +
//    			"from ocrm_f_interview_old_record oldre " +
//    			"union all " +
//    			"select newre.task_number," +
//    			"to_date(to_char(newre.call_time, 'yyyy/mm/dd'),'yyyy/mm/dd') as call_time," +
//    			"newre.interviewee_name," +
//    			"newre.interviewee_post," +
//    			"newre.interviewee_phone," +
//    			"newre.join_person," +    			
//    			"newre.res_custsource " +
//    			"from ocrm_f_interview_new_record newre) reco " +
//    			"on re.task_number = reco.task_number where 1=1 " 
//    			//add by yangyue3 20170607
//    			+"and ((trunc(sysdate,'DD')-trunc(to_date(to_char(reco.call_time, 'yyyy/mm/dd'), 'yyyy/mm/dd'),'DD')) < 90)";
//    	StringBuffer sb = new StringBuffer(sql);
//    	String custId = request.getParameter("custId");
//    	sb.append(" and (re.cust_id = '"+custId+"' or re.cust_id in (select AC.SRC_SYS_CUST_NO from ACRM_F_CI_CROSSINDEX ac where ac.SRC_SYS_NO = 'DMS' AND AC.CUST_ID = '"+custId+"'))");
//    	sb.append(" and (re.review_state = '3' or re.review_state = '03') ");
    	
    	String sql = " SELECT RE.ID, " + 
    			//"RE.CUST_ID,AC.CUST_ID," +
                " CASE WHEN AC.CUST_ID IS NULL THEN RE.CUST_ID ELSE AC.CUST_ID END CUST_ID," +
    			" RE.CUST_NAME,RE.MGR_ID,RE.MGR_NAME," +
    			" TO_DATE(TO_CHAR(RE.VISIT_TIME, 'yyyy/mm/dd'), 'yyyy/mm/dd') AS VISIT_TIME," +
    			" RE.VISIT_TYPE,CAST(NVL(OLDRE.OLDRE.CALL_TIME, NEWRE.CALL_TIME) AS DATE) CALL_TIME," +
    			" NVL(OLDRE.INTERVIEWEE_NAME, NEWRE.INTERVIEWEE_NAME) INTERVIEWEE_NAME," +
    			" NVL(OLDRE.INTERVIEWEE_PHONE, NEWRE.INTERVIEWEE_PHONE) INTERVIEWEE_PHONE," +
    			" NVL(OLDRE.JOIN_PERSON, NEWRE.JOIN_PERSON) JOIN_PERSON," +
    			" RE.REVIEW_STATE,NEWRE.RES_CUSTSOURCE RES_CUSTSOURCE " +
    			" FROM OCRM_F_INTERVIEW_RECORD RE " +
    			" LEFT JOIN OCRM_F_INTERVIEW_OLD_RECORD OLDRE " +
    			" ON RE.TASK_NUMBER = OLDRE.TASK_NUMBER " +
    			" LEFT JOIN OCRM_F_INTERVIEW_NEW_RECORD NEWRE " +    			
    			" ON RE.TASK_NUMBER = NEWRE.TASK_NUMBER " +
    			" LEFT JOIN ACRM_F_CI_CROSSINDEX AC " +
    			" ON RE.CUST_ID = AC.SRC_SYS_CUST_NO " +
    			" AND AC.SRC_SYS_NO = 'DMS' " +
    			" WHERE 1 = 1  AND ((TRUNC(SYSDATE, 'DD') -  CAST(NVL(OLDRE.OLDRE.CALL_TIME, NEWRE.CALL_TIME) AS DATE)) <= 91)" ;
    	StringBuffer sb = new StringBuffer(sql);
    	String custId = request.getParameter("custId");
    	sb.append(" AND (re.cust_id = '"+custId+"' OR AC.CUST_ID IS NOT NULL)");
    	sb.append(" AND RE.REVIEW_STATE IN ('3', '03')");

    	addOracleLookup("VISIT_TYPE","VISIT_TYPE_ALL");
    	SQL = sb.toString();
    	datasource = ds;
    	setPrimaryKey("re.cust_id desc, visit_time asc");
    	configCondition("VISIT_TYPE","=","VISIT_TYPE",DataType.String);
		configCondition("NVL(OLDRE.INTERVIEWEE_NAME, NEWRE.INTERVIEWEE_NAME)","like","INTERVIEWEE_NAME",DataType.String);
		configCondition("CAST(NVL(OLDRE.OLDRE.CALL_TIME, NEWRE.CALL_TIME) AS DATE)","=","CALL_TIME",DataType.Date);
		configCondition("NVL(OLDRE.JOIN_PERSON, NEWRE.JOIN_PERSON)","like","JOIN_PERSON",DataType.String);
	}
}
