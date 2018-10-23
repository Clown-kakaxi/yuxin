package com.yuchengtech.bcrm.customer.potentialSme.action;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.core.PagingInfo;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.SystemConstance;
import com.yuchengtech.crm.exception.BizException;

/**
 * 中小企客户营销流程--Pipeline营销概览
 * @author denghj
 * @since 2015-09-07
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action("/smePipelineAll")
public class SmePipelineAllAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;//声明数据源
	
	private HttpServletRequest request;//接受浏览器发出的请求
	
	private static Logger log = Logger.getLogger(CommonAction.class);
	
	 AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	/**
	 * 设置查询SQL并为父类相关属性赋值
	 */
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String sql1 = "SELECT C.ID," +
    			"C.USER_ID," +
    			"C.PIPELINE_ID," +
    			"C.AREA_NAME," +
    			"C.AREA_ID," +
    			"C.DEPT_NAME," +
    			"C.DEPT_ID," +
    			"C.RM," +
    			"C.RM_ID," +
    			"C.CUST_NAME," +
    			"C.CUST_ID," +
    			"C.CASE_TYPE," +
    			"C.APPLY_AMT," +
    			"C.INSURE_AMT," +
    			"C.DB_AMTS," +   			
    			"C.STATE," +
    			"C.HIDE_STEP," +
    			"C.STEP," +
    			"C.TREAMENT_DAYS," +
    			"C.TREAMENT_ALLDAYS," +
    			"(SELECT COUNT(R.CUST_ID) " +
    			"FROM OCRM_F_INTERVIEW_RECORD R " +
    			"WHERE R.CUST_ID = C.CUST_ID " +
    			"and (r.review_state = '3' or r.review_state = '03')) VISIT_COUNT " +
    			"FROM (SELECT ID," +
    			"USER_ID," +
    			"PIPELINE_ID," +
    			"AREA_NAME," +
    			"AREA_ID," +
    			"DEPT_NAME," +
    			"DEPT_ID," +
    			"RM," +
    			"RM_ID," +
    			"CUST_NAME," +   		
    			"CUST_ID," +
    			"CASE_TYPE," +
    			"APPLY_AMT," +
    			"INSURE_AMT," +
    			"DB_AMTS," +
    			"STATE," +
    			"HIDE_STEP," +
    			"STEP," +
    			"TREAMENT_DAYS," +
    			"TREAMENT_ALLDAYS," +
    			"ROW_NUMBER() OVER(PARTITION BY PIPELINE_ID ORDER BY STEP DESC) NN " +
    			"FROM (SELECT pc.ID," +
    			"pc.USER_ID," +
    			"pc.PIPELINE_ID," +
    			"pc.AREA_NAME," +
    			"pc.AREA_ID," +
    			"pc.DEPT_NAME," +
    			"pc.DEPT_ID," +
    			"pc.RM," +
    			"pc.RM_ID," +
    			"pc.CUST_NAME," +
    			"pc.CUST_ID," +
    			"'' CASE_TYPE," +
    			"NULL APPLY_AMT," +
    			"NULL INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'1' HIDE_STEP," +
    			"'1' STEP," +    
    			"(trunc(sysdate, 'DD') - pc.record_date) TREAMENT_DAYS," +
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_PROSPECT_C pc " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_PIPELINE') t " +
    			"on pc.IF_PIPELINE = t.f_code " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on pc.PIPELINE_ID = pro.pipeline_id " +
    			"where (pc.IF_PIPELINE = '0' or pc.IF_PIPELINE = '2') " +
    			"UNION ALL " +
    			"SELECT ic.ID," +
    			"ic.USER_ID," +
    			"ic.PIPELINE_ID," +
    			"ic.AREA_NAME," +
    			"ic.AREA_ID," +
    			"ic.DEPT_NAME," +
    			"ic.DEPT_ID," +
    			"ic.RM," +
    			"ic.RM_ID," +
    			"ic.CUST_NAME," +
    			"ic.CUST_ID," +
    			"ic.CASE_TYPE," +
    			"ic.APPLY_AMT," +
    			"NULL INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'2' HIDE_STEP," +
    			"'2' STEP," + 
    			"(trunc(sysdate, 'DD') - ic.record_date) TREAMENT_DAYS,"+
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_INTENT_C ic " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_FLAG_HZ') t " +
    			"on ic.IF_SECOND_STEP = t.f_code " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on ic.PIPELINE_ID = pro.pipeline_id " +
    			"where (ic.IF_SECOND_STEP is null or ic.IF_SECOND_STEP = '0' or " +
    			"ic.IF_SECOND_STEP = '2') " +
    			"UNION ALL " +
    			"SELECT cc.ID," +
    			"cc.USER_ID," +
    			"cc.PIPELINE_ID," +
    			"cc.AREA_NAME," +
    			"cc.AREA_ID," +
    			"cc.DEPT_NAME," +
    			"cc.DEPT_ID," +
    			"cc.RM," +
    			"cc.RM_ID," +
    			"cc.CUST_NAME," +
    			"cc.CUST_ID," +
    			"cc.CASE_TYPE," +
    			"cc.APPLY_AMT," +
    			"NULL INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'3' HIDE_STEP," +
    			"'3' STEP," + 
    			"(trunc(sysdate, 'DD') - cc.record_date) TREAMENT_DAYS,"+
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_CA_C cc " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_THIRD_STEP') t " +
    			"on cc.IF_THIRD_STEP = t.f_code " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on cc.PIPELINE_ID = pro.pipeline_id " +
    			"where ((cc.IF_THIRD_STEP not like '1' and " +
    			"cc.IF_THIRD_STEP not like '99') or cc.IF_THIRD_STEP is null) " +
    			"and ((cc.IF_SUMBIT_CO not like '1' and " +
    			"cc.IF_SUMBIT_CO not like '3') or cc.IF_SUMBIT_CO is null) " +
    			"UNION ALL " +
    			"SELECT ac.ID," +
    			"ac.USER_ID," +
    			"ac.PIPELINE_ID," +
    			"ac.AREA_NAME," +
    			"ac.AREA_ID," +
    			"ac.DEPT_NAME," +
    			"ac.DEPT_ID," +
    			"ac.RM," +
    			"ac.RM_ID," +
    			"ac.CUST_NAME," +
    			"ac.CUST_ID," +
    			"ac.CASE_TYPE," +
    			"ac.APPLY_AMT," +
    			"NULL INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'4' HIDE_STEP," +
    			"'3' STEP," + 
    			"(trunc(sysdate, 'DD') - ac.record_date) TREAMENT_DAYS,"+
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_CA_C ac " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_THIRD_STEP') t " +
    			"on ac.IF_THIRD_STEP = t.f_code " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on ac.PIPELINE_ID = pro.pipeline_id " +
    			"where ((ac.IF_THIRD_STEP not like '1' and " +
    			"ac.IF_THIRD_STEP not like '99') or ac.IF_THIRD_STEP is null) " +
    			"and ac.CASE_TYPE = '16' " +
    			"and ac.IF_SUMBIT_CO = '1' " +
    			"UNION ALL " +
    			"SELECT hc.ID," +
    			"hc.USER_ID," +
    			"hc.PIPELINE_ID," +
    			"hc.AREA_NAME," +
    			"hc.AREA_ID," +
    			"hc.DEPT_NAME," +
    			"hc.DEPT_ID," +
    			"hc.RM," +
    			"hc.RM_ID," +
    			"hc.CUST_NAME," +
    			"hc.CUST_ID," +
    			"hc.CASE_TYPE," +
    			"hc.APPLY_AMT," +
    			"NULL INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'5' HIDE_STEP," +
    			"'4' STEP," + 
    			"(trunc(sysdate, 'DD') - hc.record_date) TREAMENT_DAYS,"+
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_CHECK_C hc " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_FOURTH_STEP') t " +
    			"on hc.IF_FOURTH_STEP = t.f_code " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on hc.PIPELINE_ID = pro.pipeline_id " +
    			"where (hc.IF_FOURTH_STEP not in ('1', '5', '99') or " +
    			"hc.IF_FOURTH_STEP is null) " +
    			"UNION ALL " +
    			"SELECT lc.ID," +
    			"lc.USER_ID," +
    			"lc.PIPELINE_ID," +
    			"lc.AREA_NAME," +
    			"lc.AREA_ID," +
    			"lc.DEPT_NAME," +
    			"lc.DEPT_ID," +
    			"lc.RM," +
    			"lc.RM_ID," +
    			"lc.CUST_NAME," +
    			"lc.CUST_ID," +
    			"lc.CASE_TYPE," +
    			"lc.APPLY_AMT," +
    			"lc.INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'6' HIDE_STEP," +
    			"'5' STEP," + 
    			"(trunc(sysdate, 'DD') - lc.record_date) TREAMENT_DAYS,"+
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_APPROVL_C lc " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_FIFTH_STEP') t " +
    			"on lc.IF_FIFTH_STEP = t.f_code " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on lc.PIPELINE_ID = pro.pipeline_id " +
    			"where (lc.IF_FIFTH_STEP not in ('1', '5', '99') or " +
    			"(lc.IF_FIFTH_STEP is null) or " +
    			"(lc.IF_FIFTH_STEP = '1' and lc.IF_SURE = '0')) " +
    			"UNION ALL " +
    			"select td.ID," +
    			"td.USER_ID," +
    			"td.PIPELINE_ID," +
    			"td.AREA_NAME," +
    			"td.AREA_ID," +
    			"td.DEPT_NAME," +
    			"td.DEPT_ID," +
    			"td.RM," +
    			"td.RM_ID," +
    			"td.CUST_NAME," +
    			"td.CUST_ID," +
    			"td.CASE_TYPE," +
    			"td.APPLY_AMT," +
    			"td.INSURE_AMT," +
    			"td.DB_AMTS," +
    			"td.STATE," +
    			"td.HIDE_STEP," +
    			"td.STEP," +
    			"td.TREAMENT_DAYS," +
    			"td.TREAMENT_ALLDAYS " +
    			"from (SELECT dc.ID," +
    			"dc.USER_ID," +
    			"dc.PIPELINE_ID," +
    			"dc.AREA_NAME," +
    			"dc.AREA_ID," +
    			"dc.DEPT_NAME," +
    			"dc.DEPT_ID," +
    			"dc.RM," +
    			"dc.RM_ID," +
    			"dc.CUST_NAME," +
    			"dc.CUST_ID," +
    			"dc.CASE_TYPE," +
    			"dc.APPLY_AMT," +
    			"dc.INSURE_AMT," +
    			"nvl(r.db_amts, 0) db_amts," +
    			"'已核批未动拨' STATE," +
    			"'7' HIDE_STEP," +
    			"'6' STEP," + 
    			"(trunc(sysdate, 'DD') - dc.record_date) TREAMENT_DAYS," +
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_APPROVED_C dc " +
    			"left join (select t.pipeline_id," +
    			"sum(t.db_amt) as db_amts " +
    			"from OCRM_F_CI_MKT_APPROVED_DB t " +
    			"group by t.pipeline_id) r " +
    			"on dc.pipeline_id = r.pipeline_id " +
    			"left join (select pr.pipeline_id," +
    			"min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name," +
    			"p.record_date," +
    			"p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name," +
    			"i.record_date," +
    			"i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on dc.PIPELINE_ID = pro.pipeline_id) td " +
    			" where td.db_amts = 0 "+                                                                     
    			"UNION ALL " +
    			"select te.ID," +
    			"te.USER_ID," +
    			"te.PIPELINE_ID," +
    			"te.AREA_NAME," +
    			"te.AREA_ID," +
    			"te.DEPT_NAME," +
    			"te.DEPT_ID," +
    			"te.RM," +
    			"te.RM_ID," +
    			"te.CUST_NAME," +
    			"te.CUST_ID," +
    			"te.CASE_TYPE," +
    			"te.APPLY_AMT," +
    			"te.INSURE_AMT," +
    			"te.DB_AMTS," +
    			"te.STATE," +
    			"te.HIDE_STEP," +
    			"te.STEP," +
    			"te.TREAMENT_DAYS," +
    			"te.TREAMENT_ALLDAYS " +
    			"from (SELECT dc.ID," +
    			"dc.USER_ID," +
    			"dc.PIPELINE_ID," +
    			"dc.AREA_NAME," +
    			"dc.AREA_ID," +
    			"dc.DEPT_NAME," +
    			"dc.DEPT_ID," +
    			"dc.RM," +
    			"dc.RM_ID," +
    			"dc.CUST_NAME," +
    			"dc.CUST_ID," +
    			"dc.CASE_TYPE," +
    			"dc.APPLY_AMT," +
    			"dc.INSURE_AMT," +
    			"nvl(r.db_amts, 0) db_amts," +
    			"'已核批动拨' STATE," +
    			"'7' HIDE_STEP," +
    			"'6' STEP," + 
    			"(trunc(sysdate, 'DD') - dc.record_date) TREAMENT_DAYS," +
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_APPROVED_C dc " +
    			"left join (select t.pipeline_id," +
    			"sum(t.db_amt) as db_amts " +
    			"from OCRM_F_CI_MKT_APPROVED_DB t " +
    			"group by t.pipeline_id) r " +
    			"on dc.pipeline_id = r.pipeline_id " +
    			"left join (select pr.pipeline_id," +
    			"min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name," +
    			"p.record_date," +
    			"p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name," +
    			"i.record_date," +
    			"i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on dc.PIPELINE_ID = pro.pipeline_id) te " +
    			"where te.db_amts > 0 "+  
    			")) C " +
    			"left join admin_auth_account a " +
    			"on C.USER_ID = a.account_name " +
    			"where C.NN = '1' " +
    			"and (a.belong_busi_line = '5' or a.belong_busi_line = '0')";
    	
    	String sql2 = "SELECT C.ID," +
    			"C.USER_ID," +
    			"C.PIPELINE_ID," +
    			"C.AREA_NAME," +
    			"C.AREA_ID," +
    			"C.DEPT_NAME," +
    			"C.DEPT_ID," +
    			"C.RM," +
    			"C.RM_ID," +
    			"C.CUST_NAME," +
    			"C.CUST_ID," +
    			"C.CASE_TYPE," +
    			"C.APPLY_AMT," +
    			"C.INSURE_AMT," +
    			"C.DB_AMTS," +   	
    			"C.STATE," +
    			"C.HIDE_STEP," +
    			"C.STEP," +
    			"C.TREAMENT_DAYS," +
    			"C.TREAMENT_ALLDAYS," +
    			"(SELECT COUNT(R.CUST_ID)" +
    			"FROM OCRM_F_INTERVIEW_RECORD R " +
    			"WHERE R.CUST_ID = C.CUST_ID " +
    			"and (r.review_state = '3' or r.review_state = '03')) VISIT_COUNT " +
    			"FROM (" +
    			"SELECT ID," +
    			"USER_ID," +
    			"PIPELINE_ID," +
    			"AREA_NAME," +
    			"AREA_ID," +
    			"DEPT_NAME," +
    			"DEPT_ID," +
    			"RM," +
    			"RM_ID," +
    			"CUST_NAME," +
    			"CUST_ID," +
    			"CASE_TYPE," +
    			"APPLY_AMT," +
    			"INSURE_AMT," +
    			"DB_AMTS," +
    			"STATE," +
    			"HIDE_STEP," +
    			"STEP," +
    			"TREAMENT_DAYS," +
    			"TREAMENT_ALLDAYS," +
    			"ROW_NUMBER() OVER(PARTITION BY PIPELINE_ID ORDER BY HIDE_STEP DESC) NN " +
    			"FROM (" +
    			"SELECT ac.ID," +
    			"ac.USER_ID," +
    			"ac.PIPELINE_ID," +
    			"ac.AREA_NAME," +
    			"ac.AREA_ID," +
    			"ac.DEPT_NAME," +
    			"ac.DEPT_ID," +
    			"ac.RM," +
    			"ac.RM_ID," +
    			"ac.CUST_NAME," +
    			"ac.CUST_ID," +
    			"ac.CASE_TYPE," +
    			"ac.APPLY_AMT," +
    			"NULL INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'4' HIDE_STEP," +
    			"'3' STEP," + 
    			"(trunc(sysdate, 'DD') - ac.record_date) TREAMENT_DAYS,"+
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_CA_C ac " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_THIRD_STEP') t " +
    			"on ac.IF_THIRD_STEP = t.f_code " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on ac.PIPELINE_ID = pro.pipeline_id " +
    			"where ((ac.IF_THIRD_STEP not like '1' and " +
    			"ac.IF_THIRD_STEP not like '99') or ac.IF_THIRD_STEP is null) " +
    			"and ac.CASE_TYPE = '16' " +
    			"and ac.IF_SUMBIT_CO = '1' " +
    			"UNION ALL " +
    			"SELECT hc.ID," +
    			"hc.USER_ID," +
    			"hc.PIPELINE_ID," +
    			"hc.AREA_NAME," +
    			"hc.AREA_ID," +
    			"hc.DEPT_NAME," +
    			"hc.DEPT_ID," +
    			"hc.RM," +
    			"hc.RM_ID," +
    			"hc.CUST_NAME," +
    			"hc.CUST_ID," +
    			"hc.CASE_TYPE," +
    			"hc.APPLY_AMT," +
    			"NULL INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'5' HIDE_STEP," +
    			"'4' STEP," + 
    			"(trunc(sysdate, 'DD') - hc.record_date) TREAMENT_DAYS,"+
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_CHECK_C hc " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_FOURTH_STEP') t " +
    			"on hc.IF_FOURTH_STEP = t.f_code " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on hc.PIPELINE_ID = pro.pipeline_id " +
    			"where (hc.IF_FOURTH_STEP not in ('1', '5', '99') or " +
    			"hc.IF_FOURTH_STEP is null) " +
    			"UNION ALL " +
    			"SELECT lc.ID," +
    			"lc.USER_ID," +
    			"lc.PIPELINE_ID," +
    			"lc.AREA_NAME," +
    			"lc.AREA_ID," +
    			"lc.DEPT_NAME," +
    			"lc.DEPT_ID," +
    			"lc.RM," +
    			"lc.RM_ID," +
    			"lc.CUST_NAME," +
    			"lc.CUST_ID," +
    			"lc.CASE_TYPE," +
    			"lc.APPLY_AMT," +
    			"lc.INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'6' HIDE_STEP," +
    			"'5' STEP," + 
    			"(trunc(sysdate, 'DD') - lc.record_date) TREAMENT_DAYS,"+
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_APPROVL_C lc " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_FIFTH_STEP') t " +
    			"on lc.IF_FIFTH_STEP = t.f_code " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on lc.PIPELINE_ID = pro.pipeline_id " +
    			"where (lc.IF_FIFTH_STEP not in ('1', '5', '99') or " +
    			"(lc.IF_FIFTH_STEP is null) or " +
    			"(lc.IF_FIFTH_STEP = '1' and lc.IF_SURE = '0')) " +
    			"UNION ALL " +
    			"select td.ID," +
    			"td.USER_ID," +
    			"td.PIPELINE_ID," +
    			"td.AREA_NAME," +
    			"td.AREA_ID," +
    			"td.DEPT_NAME," +
    			"td.DEPT_ID," +
    			"td.RM," +
    			"td.RM_ID," +
    			"td.CUST_NAME," +
    			"td.CUST_ID," +
    			"td.CASE_TYPE," +
    			"td.APPLY_AMT," +
    			"td.INSURE_AMT," +
    			"td.DB_AMTS," +
    			"td.STATE," +
    			"td.HIDE_STEP," +
    			"td.STEP," +
    			"td.TREAMENT_DAYS," +
    			"td.TREAMENT_ALLDAYS " +
    			"from (SELECT dc.ID," +
    			"dc.USER_ID," +
    			"dc.PIPELINE_ID," +
    			"dc.AREA_NAME," +
    			"dc.AREA_ID," +
    			"dc.DEPT_NAME," +
    			"dc.DEPT_ID," +
    			"dc.RM," +
    			"dc.RM_ID," +
    			"dc.CUST_NAME," +
    			"dc.CUST_ID," +
    			"dc.CASE_TYPE," +
    			"dc.APPLY_AMT," +
    			"dc.INSURE_AMT," +
    			"nvl(r.db_amts, 0) db_amts," +
    			"'已核批未动拨' STATE," +
    			"'7' HIDE_STEP," +
    			"'6' STEP," + 
    			"(trunc(sysdate, 'DD') - dc.record_date) TREAMENT_DAYS," +
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_APPROVED_C dc " +
    			"left join (select t.pipeline_id," +
    			"sum(t.db_amt) as db_amts " +
    			"from OCRM_F_CI_MKT_APPROVED_DB t " +
    			"group by t.pipeline_id) r " +
    			"on dc.pipeline_id = r.pipeline_id " +
    			"left join (select pr.pipeline_id," +
    			"min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name," +
    			"p.record_date," +
    			"p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name," +
    			"i.record_date," +
    			"i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on dc.PIPELINE_ID = pro.pipeline_id) td " +
    			" where td.db_amts = 0 "+                                                                     
    			"UNION ALL " +
    			"select te.ID," +
    			"te.USER_ID," +
    			"te.PIPELINE_ID," +
    			"te.AREA_NAME," +
    			"te.AREA_ID," +
    			"te.DEPT_NAME," +
    			"te.DEPT_ID," +
    			"te.RM," +
    			"te.RM_ID," +
    			"te.CUST_NAME," +
    			"te.CUST_ID," +
    			"te.CASE_TYPE," +
    			"te.APPLY_AMT," +
    			"te.INSURE_AMT," +
    			"te.DB_AMTS," +
    			"te.STATE," +
    			"te.HIDE_STEP," +
    			"te.STEP," +
    			"te.TREAMENT_DAYS," +
    			"te.TREAMENT_ALLDAYS " +
    			"from (SELECT dc.ID," +
    			"dc.USER_ID," +
    			"dc.PIPELINE_ID," +
    			"dc.AREA_NAME," +
    			"dc.AREA_ID," +
    			"dc.DEPT_NAME," +
    			"dc.DEPT_ID," +
    			"dc.RM," +
    			"dc.RM_ID," +
    			"dc.CUST_NAME," +
    			"dc.CUST_ID," +
    			"dc.CASE_TYPE," +
    			"dc.APPLY_AMT," +
    			"dc.INSURE_AMT," +
    			"nvl(r.db_amts, 0) db_amts," +
    			"'已核批动拨' STATE," +
    			"'7' HIDE_STEP," +
    			"'6' STEP," + 
    			"(trunc(sysdate, 'DD') - dc.record_date) TREAMENT_DAYS," +
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_APPROVED_C dc " +
    			"left join (select t.pipeline_id," +
    			"sum(t.db_amt) as db_amts " +
    			"from OCRM_F_CI_MKT_APPROVED_DB t " +
    			"group by t.pipeline_id) r " +
    			"on dc.pipeline_id = r.pipeline_id " +
    			"left join (select pr.pipeline_id," +
    			"min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name," +
    			"p.record_date," +
    			"p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name," +
    			"i.record_date," +
    			"i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on dc.PIPELINE_ID = pro.pipeline_id) te " +
    			"where te.db_amts > 0 "+  
    			")) C " +
    			"left join admin_auth_account a " +
    			"on C.USER_ID = a.account_name " +
    			"where C.NN = '1' " +
    			"and (a.belong_busi_line = '5' or a.belong_busi_line = '0')";

    	StringBuffer sb = null;
    	List<?> list = auth.getRolesInfo();
    	for(Object m : list){//判断用户角色是否为CO
    		Map<?, ?> map = (Map<?, ?>) m;
    		if("R121".equals(map.get("ROLE_CODE"))){//CO角色
    			sb = new StringBuffer(sql2);
    			continue;
    		}else{
    			sb = new StringBuffer(sql1);
    			continue;
    		}
    	}
    	String id = request.getParameter("id");
    	if(id != null && !"".equals(id)){
    		sb.append("and c.id = '"+ id+"'");
    	}else{
    		sb.append("and (C.STATE not like '是' or C.STATE is null)");
    		setPrimaryKey("c.id desc");
    	}
    	sb.append("and (C.CASE_TYPE = '1' or C.CASE_TYPE = '16' or C.CASE_TYPE = '17' or C.CASE_TYPE = '18' or C.CASE_TYPE is null)");
    	SQL=sb.toString();
    	datasource = ds;
    	setPrimaryKey("c.step asc,c.state desc");
    	configCondition("CUST_ID","=","CUST_ID",DataType.String);
		configCondition("CUST_NAME","like","CUST_NAME",DataType.String);
		configCondition("CASE_TYPE","=","CASE_TYPE",DataType.String);
		configCondition("STATE","like","STATE",DataType.String);
		configCondition("HIDE_STEP","=","HIDE_STEP",DataType.String);
		configCondition("STEP","=","STEP",DataType.String);
		configCondition("AREA_NAME","like","AREA_NAME",DataType.String);
		configCondition("DEPT_NAME","like","DEPT_NAME",DataType.String);
		configCondition("RM","=","like",DataType.String);
	}
	
	/**
	 * 设置SQL查询拒绝及退案撤案汇总
	 */
	public String refusedCollect(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String sql1 = "SELECT C.ID," +
    			"C.USER_ID," +
    			"C.PIPELINE_ID," +
    			"C.AREA_NAME," +
    			"C.AREA_ID," +
    			"C.DEPT_NAME," +
    			"C.DEPT_ID," +
    			"C.RM," +
    			"C.RM_ID," +
    			"C.CUST_NAME," +
    			"C.CUST_ID," +
    			"C.CASE_TYPE," +
    			"C.CASE_TYPE_NAME," +
    			"C.UPDATE_DATE," +
    			"to_char(C.APPLY_AMT,'L999,999.99') APPLY_AMT," +
    			"to_char(C.INSURE_AMT,'L999,999.99') INSURE_AMT," +
    			"to_char(c.db_amts,'L999,999.99') DB_AMTS," +   			
    			"C.STATE," +
    			"C.HIDE_STEP," +
    			"C.STEP_NAME," +
    			"C.TREAMENT_DAYS," +
    			"C.TREAMENT_ALLDAYS," +
    			"(SELECT COUNT(R.CUST_ID) " +
    			"FROM OCRM_F_INTERVIEW_RECORD R " +
    			"WHERE R.CUST_ID = C.CUST_ID " +
    			"and (r.review_state = '3' or r.review_state = '03')) VISIT_COUNT " +
    			"FROM (SELECT ID," +
    			"USER_ID," +
    			"PIPELINE_ID," +
    			"AREA_NAME," +
    			"AREA_ID," +
    			"DEPT_NAME," +
    			"DEPT_ID," +
    			"RM," +
    			"RM_ID," +
    			"CUST_NAME," +   		
    			"CUST_ID," +
    			"CASE_TYPE," +
    			"CASE_TYPE_NAME," +
    			"UPDATE_DATE," +
    			"APPLY_AMT," +
    			"INSURE_AMT," +
    			"DB_AMTS," +
    			"STATE," +
    			"HIDE_STEP," +
    			"STEP_NAME," +
    			"TREAMENT_DAYS," +
    			"TREAMENT_ALLDAYS," +
    			"ROW_NUMBER() OVER(PARTITION BY PIPELINE_ID ORDER BY HIDE_STEP DESC) NN " +
    			"FROM (SELECT pc.ID," +
    			"pc.USER_ID," +
    			"pc.PIPELINE_ID," +
    			"pc.AREA_NAME," +
    			"pc.AREA_ID," +
    			"pc.DEPT_NAME," +
    			"pc.DEPT_ID," +
    			"pc.RM," +
    			"pc.RM_ID," +
    			"pc.CUST_NAME," +
    			"pc.CUST_ID," +
    			"'' CASE_TYPE," +
    			"'' CASE_TYPE_NAME," +
    			"pc.UPDATE_DATE," +
    			"NULL APPLY_AMT," +
    			"NULL INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'1' HIDE_STEP," +
    			"s.f_value STEP_NAME," +    
    			"(trunc(sysdate, 'DD') - pc.record_date) TREAMENT_DAYS," +
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_PROSPECT_C pc " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_PIPELINE') t " +
    			"on pc.IF_PIPELINE = t.f_code " +
    			"left join (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'PIPELINE_STEP') s " +
    			"on s.f_code = '1' " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on pc.PIPELINE_ID = pro.pipeline_id " +
    			"where (pc.IF_PIPELINE = '0' or pc.IF_PIPELINE = '2') " +
    			"UNION ALL " +
    			"SELECT ic.ID," +
    			"ic.USER_ID," +
    			"ic.PIPELINE_ID," +
    			"ic.AREA_NAME," +
    			"ic.AREA_ID," +
    			"ic.DEPT_NAME," +
    			"ic.DEPT_ID," +
    			"ic.RM," +
    			"ic.RM_ID," +
    			"ic.CUST_NAME," +
    			"ic.CUST_ID," +
    			"ic.CASE_TYPE," +
    			"c.f_value CASE_TYPE_NAME," +
    			"ic.UPDATE_DATE," +
    			"ic.APPLY_AMT," +
    			"NULL INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'2' HIDE_STEP," +
    			"s.f_value STEP_NAME," + 
    			"(trunc(sysdate, 'DD') - ic.record_date) TREAMENT_DAYS,"+
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_INTENT_C ic " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_FLAG_HZ') t " +
    			"on ic.IF_SECOND_STEP = t.f_code " +
    			"left join  (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'CASE_TYPE_SME') c " +
    			"on ic.case_type = c.f_code " +
    			"left join (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'PIPELINE_STEP') s " +
    			"on s.f_code = '2' " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on ic.PIPELINE_ID = pro.pipeline_id " +
    			"where (ic.IF_SECOND_STEP is null or ic.IF_SECOND_STEP = '0' or " +
    			"ic.IF_SECOND_STEP = '2') " +
    			"UNION ALL " +
    			"SELECT cc.ID," +
    			"cc.USER_ID," +
    			"cc.PIPELINE_ID," +
    			"cc.AREA_NAME," +
    			"cc.AREA_ID," +
    			"cc.DEPT_NAME," +
    			"cc.DEPT_ID," +
    			"cc.RM," +
    			"cc.RM_ID," +
    			"cc.CUST_NAME," +
    			"cc.CUST_ID," +
    			"cc.CASE_TYPE," +
    			"c.f_value CASE_TYPE_NAME," +
    			"cc.UPDATE_DATE," +
    			"cc.APPLY_AMT," +
    			"NULL INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'3' HIDE_STEP," +
    			"s.f_value STEP_NAME," + 
    			"(trunc(sysdate, 'DD') - cc.record_date) TREAMENT_DAYS,"+
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_CA_C cc " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_THIRD_STEP') t " +
    			"on cc.IF_THIRD_STEP = t.f_code " +
    			"left join  (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'CASE_TYPE_SME') c " +
    			"on cc.case_type = c.f_code " +
    			"left join (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'PIPELINE_STEP') s " +
    			"on s.f_code = '3' " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on cc.PIPELINE_ID = pro.pipeline_id " +
    			"where ((cc.IF_THIRD_STEP not like '1' and " +
    			"cc.IF_THIRD_STEP not like '99') or cc.IF_THIRD_STEP is null) " +
    			"and ((cc.IF_SUMBIT_CO not like '1' and " +
    			"cc.IF_SUMBIT_CO not like '3') or cc.IF_SUMBIT_CO is null) " +
    			"UNION ALL " +
    			"SELECT ac.ID," +
    			"ac.USER_ID," +
    			"ac.PIPELINE_ID," +
    			"ac.AREA_NAME," +
    			"ac.AREA_ID," +
    			"ac.DEPT_NAME," +
    			"ac.DEPT_ID," +
    			"ac.RM," +
    			"ac.RM_ID," +
    			"ac.CUST_NAME," +
    			"ac.CUST_ID," +
    			"ac.CASE_TYPE," +
    			"c.f_value CASE_TYPE_NAME," +
    			"ac.UPDATE_DATE," +
    			"ac.APPLY_AMT," +
    			"NULL INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'4' HIDE_STEP," +
    			"s.f_value STEP_NAME," + 
    			"(trunc(sysdate, 'DD') - ac.record_date) TREAMENT_DAYS,"+
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_CA_C ac " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_THIRD_STEP') t " +
    			"on ac.IF_THIRD_STEP = t.f_code " +
    			"left join  (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'CASE_TYPE_SME') c " +
    			"on ac.case_type = c.f_code " +
    			"left join (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'PIPELINE_STEP') s " +
    			"on s.f_code = '3' " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on ac.PIPELINE_ID = pro.pipeline_id " +
    			"where ((ac.IF_THIRD_STEP not like '1' and " +
    			"ac.IF_THIRD_STEP not like '99') or ac.IF_THIRD_STEP is null) " +
    			"and ac.CASE_TYPE = '16' " +
    			"and ac.IF_SUMBIT_CO = '1' " +
    			"UNION ALL " +
    			"SELECT hc.ID," +
    			"hc.USER_ID," +
    			"hc.PIPELINE_ID," +
    			"hc.AREA_NAME," +
    			"hc.AREA_ID," +
    			"hc.DEPT_NAME," +
    			"hc.DEPT_ID," +
    			"hc.RM," +
    			"hc.RM_ID," +
    			"hc.CUST_NAME," +
    			"hc.CUST_ID," +
    			"hc.CASE_TYPE," +
    			"c.f_value CASE_TYPE_NAME," +
    			"hc.UPDATE_DATE," +
    			"hc.APPLY_AMT," +
    			"NULL INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'5' HIDE_STEP," +
    			"s.f_value STEP_NAME," + 
    			"(trunc(sysdate, 'DD') - hc.record_date) TREAMENT_DAYS,"+
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_CHECK_C hc " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_FOURTH_STEP') t " +
    			"on hc.IF_FOURTH_STEP = t.f_code " +
    			"left join  (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'CASE_TYPE_SME') c " +
    			"on hc.case_type = c.f_code " +
    			"left join (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'PIPELINE_STEP') s " +
    			"on s.f_code = '4' " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on hc.PIPELINE_ID = pro.pipeline_id " +
    			"where (hc.IF_FOURTH_STEP not in ('1', '5', '99') or " +
    			"hc.IF_FOURTH_STEP is null) " +
    			"UNION ALL " +
    			"SELECT lc.ID," +
    			"lc.USER_ID," +
    			"lc.PIPELINE_ID," +
    			"lc.AREA_NAME," +
    			"lc.AREA_ID," +
    			"lc.DEPT_NAME," +
    			"lc.DEPT_ID," +
    			"lc.RM," +
    			"lc.RM_ID," +
    			"lc.CUST_NAME," +
    			"lc.CUST_ID," +
    			"lc.CASE_TYPE," +
    			"c.f_value CASE_TYPE_NAME," +
    			"lc.UPDATE_DATE," +
    			"lc.APPLY_AMT," +
    			"lc.INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'6' HIDE_STEP," +
    			"s.f_value STEP_NAME," + 
    			"(trunc(sysdate, 'DD') - lc.record_date) TREAMENT_DAYS,"+
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_APPROVL_C lc " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_FIFTH_STEP') t " +
    			"on lc.IF_FIFTH_STEP = t.f_code " +
    			"left join  (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'CASE_TYPE_SME') c " +
    			"on lc.case_type = c.f_code " +
    			"left join (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'PIPELINE_STEP') s " +
    			"on s.f_code = '5' " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on lc.PIPELINE_ID = pro.pipeline_id " +
    			"where (lc.IF_FIFTH_STEP not in ('1', '5', '99') or " +
    			"(lc.IF_FIFTH_STEP is null) or " +
    			"(lc.IF_FIFTH_STEP = '1' and lc.IF_SURE = '0')) " +
    			"UNION ALL " +
    			"select td.ID," +
    			"td.USER_ID," +
    			"td.PIPELINE_ID," +
    			"td.AREA_NAME," +
    			"td.AREA_ID," +
    			"td.DEPT_NAME," +
    			"td.DEPT_ID," +
    			"td.RM," +
    			"td.RM_ID," +
    			"td.CUST_NAME," +
    			"td.CUST_ID," +
    			"td.CASE_TYPE," +
    			"td.CASE_TYPE_NAME," +
    			"td.UPDATE_DATE," +
    			"td.APPLY_AMT," +
    			"td.INSURE_AMT," +
    			"td.DB_AMTS," +
    			"td.STATE," +
    			"td.HIDE_STEP," +
    			"td.STEP_NAME," +
    			"td.TREAMENT_DAYS," +
    			"td.TREAMENT_ALLDAYS " +
    			"from (SELECT dc.ID," +
    			"dc.USER_ID," +
    			"dc.PIPELINE_ID," +
    			"dc.AREA_NAME," +
    			"dc.AREA_ID," +
    			"dc.DEPT_NAME," +
    			"dc.DEPT_ID," +
    			"dc.RM," +
    			"dc.RM_ID," +
    			"dc.CUST_NAME," +
    			"dc.CUST_ID," +
    			"dc.CASE_TYPE," +
    			"c.f_value CASE_TYPE_NAME," +
    			"dc.UPDATE_DATE," +
    			"dc.APPLY_AMT," +
    			"dc.INSURE_AMT," +
    			"nvl(r.db_amts, 0) db_amts," +
    			"'已核批未动拨' STATE," +
    			"'7' HIDE_STEP," +
    			"s.f_value STEP_NAME," + 
    			"(trunc(sysdate, 'DD') - dc.record_date) TREAMENT_DAYS," +
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_APPROVED_C dc " +
    			"left join  (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'CASE_TYPE_SME') c " +
    			"on dc.case_type = c.f_code " +
    			"left join (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'PIPELINE_STEP') s " +
    			"on s.f_code = '6' " +
    			"left join (select t.pipeline_id," +
    			"sum(t.db_amt) as db_amts " +
    			"from OCRM_F_CI_MKT_APPROVED_DB t " +
    			"group by t.pipeline_id) r " +
    			"on dc.pipeline_id = r.pipeline_id " +
    			"left join (select pr.pipeline_id," +
    			"min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name," +
    			"p.record_date," +
    			"p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name," +
    			"i.record_date," +
    			"i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on dc.PIPELINE_ID = pro.pipeline_id) td " +
    			" where td.db_amts = 0 "+                                                                     
    			"UNION ALL " +
    			"select te.ID," +
    			"te.USER_ID," +
    			"te.PIPELINE_ID," +
    			"te.AREA_NAME," +
    			"te.AREA_ID," +
    			"te.DEPT_NAME," +
    			"te.DEPT_ID," +
    			"te.RM," +
    			"te.RM_ID," +
    			"te.CUST_NAME," +
    			"te.CUST_ID," +
    			"te.CASE_TYPE," +
    			"te.CASE_TYPE_NAME," +
    			"te.UPDATE_DATE," +
    			"te.APPLY_AMT," +
    			"te.INSURE_AMT," +
    			"te.DB_AMTS," +
    			"te.STATE," +
    			"te.HIDE_STEP," +
    			"te.STEP_NAME," +
    			"te.TREAMENT_DAYS," +
    			"te.TREAMENT_ALLDAYS " +
    			"from (SELECT dc.ID," +
    			"dc.USER_ID," +
    			"dc.PIPELINE_ID," +
    			"dc.AREA_NAME," +
    			"dc.AREA_ID," +
    			"dc.DEPT_NAME," +
    			"dc.DEPT_ID," +
    			"dc.RM," +
    			"dc.RM_ID," +
    			"dc.CUST_NAME," +
    			"dc.CUST_ID," +
    			"dc.CASE_TYPE," +
    			"c.f_value CASE_TYPE_NAME," +
    			"dc.UPDATE_DATE," +
    			"dc.APPLY_AMT," +
    			"dc.INSURE_AMT," +
    			"nvl(r.db_amts, 0) db_amts," +
    			"'已核批动拨' STATE," +
    			"'7' HIDE_STEP," +
    			"s.f_value STEP_NAME," + 
    			"(trunc(sysdate, 'DD') - dc.record_date) TREAMENT_DAYS," +
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_APPROVED_C dc " +
    			"left join  (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'CASE_TYPE_SME') c " +
    			"on dc.case_type = c.f_code " +
    			"left join (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'PIPELINE_STEP') s " +
    			"on s.f_code = '6' " +
    			"left join (select t.pipeline_id," +
    			"sum(t.db_amt) as db_amts " +
    			"from OCRM_F_CI_MKT_APPROVED_DB t " +
    			"group by t.pipeline_id) r " +
    			"on dc.pipeline_id = r.pipeline_id " +
    			"left join (select pr.pipeline_id," +
    			"min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name," +
    			"p.record_date," +
    			"p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name," +
    			"i.record_date," +
    			"i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on dc.PIPELINE_ID = pro.pipeline_id) te " +
    			"where te.db_amts > 0 "+  
    			")) C " +
    			"left join admin_auth_account a " +
    			"on C.USER_ID = a.account_name " +
    			"where C.NN = '1' " +
    			"and (a.belong_busi_line = '5' or a.belong_busi_line = '0')";
    	
    	String sql2 = "SELECT C.ID," +
    			"C.USER_ID," +
    			"C.PIPELINE_ID," +
    			"C.AREA_NAME," +
    			"C.AREA_ID," +
    			"C.DEPT_NAME," +
    			"C.DEPT_ID," +
    			"C.RM," +
    			"C.RM_ID," +
    			"C.CUST_NAME," +
    			"C.CUST_ID," +
    			"C.CASE_TYPE," +
    			"C.CASE_TYPE_NAME," +
    			"C.UPDATE_DATE," +
    			"to_char(C.APPLY_AMT,'L999,999.99') APPLY_AMT," +
    			"to_char(C.INSURE_AMT,'L999,999.99') INSURE_AMT," +
    			"to_char(c.db_amts,'L999,999.99') DB_AMTS," +   	
    			"C.STATE," +
    			"C.HIDE_STEP," +
    			"C.STEP_NAME," +
    			"C.TREAMENT_DAYS," +
    			"C.TREAMENT_ALLDAYS," +
    			"(SELECT COUNT(R.CUST_ID)" +
    			"FROM OCRM_F_INTERVIEW_RECORD R " +
    			"WHERE R.CUST_ID = C.CUST_ID " +
    			"and (r.review_state = '3' or r.review_state = '03')) VISIT_COUNT " +
    			"FROM (" +
    			"SELECT ID," +
    			"USER_ID," +
    			"PIPELINE_ID," +
    			"AREA_NAME," +
    			"AREA_ID," +
    			"DEPT_NAME," +
    			"DEPT_ID," +
    			"RM," +
    			"RM_ID," +
    			"CUST_NAME," +
    			"CUST_ID," +
    			"CASE_TYPE," +
    			"CASE_TYPE_NAME," +
    			"UPDATE_DATE," +
    			"APPLY_AMT," +
    			"INSURE_AMT," +
    			"DB_AMTS," +
    			"STATE," +
    			"HIDE_STEP," +
    			"STEP_NAME," +
    			"TREAMENT_DAYS," +
    			"TREAMENT_ALLDAYS," +
    			"ROW_NUMBER() OVER(PARTITION BY PIPELINE_ID ORDER BY HIDE_STEP DESC) NN " +
    			"FROM (" +
    			"SELECT ac.ID," +
    			"ac.USER_ID," +
    			"ac.PIPELINE_ID," +
    			"ac.AREA_NAME," +
    			"ac.AREA_ID," +
    			"ac.DEPT_NAME," +
    			"ac.DEPT_ID," +
    			"ac.RM," +
    			"ac.RM_ID," +
    			"ac.CUST_NAME," +
    			"ac.CUST_ID," +
    			"ac.CASE_TYPE," +
    			"c.f_value CASE_TYPE_NAME," +
    			"ac.UPDATE_DATE," +
    			"ac.APPLY_AMT," +
    			"NULL INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'4' HIDE_STEP," +
    			"s.f_value STEP_NAME," + 
    			"(trunc(sysdate, 'DD') - ac.record_date) TREAMENT_DAYS,"+
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_CA_C ac " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_THIRD_STEP') t " +
    			"on ac.IF_THIRD_STEP = t.f_code " +
    			"left join  (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'CASE_TYPE_SME') c " +
    			"on ac.case_type = c.f_code " +
    			"left join (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'PIPELINE_STEP') s " +
    			"on s.f_code = '3' " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on ac.PIPELINE_ID = pro.pipeline_id " +
    			"where ((ac.IF_THIRD_STEP not like '1' and " +
    			"ac.IF_THIRD_STEP not like '99') or ac.IF_THIRD_STEP is null) " +
    			"and ac.CASE_TYPE = '16' " +
    			"and ac.IF_SUMBIT_CO = '1' " +
    			"UNION ALL " +
    			"SELECT hc.ID," +
    			"hc.USER_ID," +
    			"hc.PIPELINE_ID," +
    			"hc.AREA_NAME," +
    			"hc.AREA_ID," +
    			"hc.DEPT_NAME," +
    			"hc.DEPT_ID," +
    			"hc.RM," +
    			"hc.RM_ID," +
    			"hc.CUST_NAME," +
    			"hc.CUST_ID," +
    			"hc.CASE_TYPE," +
    			"c.f_value CASE_TYPE_NAME," +
    			"hc.UPDATE_DATE," +
    			"hc.APPLY_AMT," +
    			"NULL INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'5' HIDE_STEP," +
    			"s.f_value STEP_NAME," + 
    			"(trunc(sysdate, 'DD') - hc.record_date) TREAMENT_DAYS,"+
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_CHECK_C hc " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_FOURTH_STEP') t " +
    			"on hc.IF_FOURTH_STEP = t.f_code " +
    			"left join  (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'CASE_TYPE_SME') c " +
    			"on hc.case_type = c.f_code " +
    			"left join (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'PIPELINE_STEP') s " +
    			"on s.f_code = '4' " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on hc.PIPELINE_ID = pro.pipeline_id " +
    			"where (hc.IF_FOURTH_STEP not in ('1', '5', '99') or " +
    			"hc.IF_FOURTH_STEP is null) " +
    			"UNION ALL " +
    			"SELECT lc.ID," +
    			"lc.USER_ID," +
    			"lc.PIPELINE_ID," +
    			"lc.AREA_NAME," +
    			"lc.AREA_ID," +
    			"lc.DEPT_NAME," +
    			"lc.DEPT_ID," +
    			"lc.RM," +
    			"lc.RM_ID," +
    			"lc.CUST_NAME," +
    			"lc.CUST_ID," +
    			"lc.CASE_TYPE," +
    			"c.f_value CASE_TYPE_NAME," +
    			"lc.UPDATE_DATE," +
    			"lc.APPLY_AMT," +
    			"lc.INSURE_AMT," +
    			"NULL DB_AMTS," +
    			"t.f_value STATE," +
    			"'6' HIDE_STEP," +
    			"s.f_value STEP_NAME," + 
    			"(trunc(sysdate, 'DD') - lc.record_date) TREAMENT_DAYS,"+
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_APPROVL_C lc " +
    			"left join (select * " +
    			"from ocrm_sys_lookup_item " +
    			"where F_LOOKUP_ID = 'IF_FIFTH_STEP') t " +
    			"on lc.IF_FIFTH_STEP = t.f_code " +
    			"left join  (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'CASE_TYPE_SME') c " +
    			"on lc.case_type = c.f_code " +
    			"left join (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'PIPELINE_STEP') s " +
    			"on s.f_code = '5' " +
    			"left join (select pr.pipeline_id, min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name, p.record_date, p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name, i.record_date, i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on lc.PIPELINE_ID = pro.pipeline_id " +
    			"where (lc.IF_FIFTH_STEP not in ('1', '5', '99') or " +
    			"(lc.IF_FIFTH_STEP is null) or " +
    			"(lc.IF_FIFTH_STEP = '1' and lc.IF_SURE = '0')) " +
    			"UNION ALL " +
    			"select td.ID," +
    			"td.USER_ID," +
    			"td.PIPELINE_ID," +
    			"td.AREA_NAME," +
    			"td.AREA_ID," +
    			"td.DEPT_NAME," +
    			"td.DEPT_ID," +
    			"td.RM," +
    			"td.RM_ID," +
    			"td.CUST_NAME," +
    			"td.CUST_ID," +
    			"td.CASE_TYPE," +
    			"td.CASE_TYPE_NAME," +
    			"td.UPDATE_DATE," +
    			"td.APPLY_AMT," +
    			"td.INSURE_AMT," +
    			"td.DB_AMTS," +
    			"td.STATE," +
    			"td.HIDE_STEP," +
    			"td.STEP_NAME," +
    			"td.TREAMENT_DAYS," +
    			"td.TREAMENT_ALLDAYS " +
    			"from (SELECT dc.ID," +
    			"dc.USER_ID," +
    			"dc.PIPELINE_ID," +
    			"dc.AREA_NAME," +
    			"dc.AREA_ID," +
    			"dc.DEPT_NAME," +
    			"dc.DEPT_ID," +
    			"dc.RM," +
    			"dc.RM_ID," +
    			"dc.CUST_NAME," +
    			"dc.CUST_ID," +
    			"dc.CASE_TYPE," +
    			"c.f_value CASE_TYPE_NAME," +
    			"dc.UPDATE_DATE," +
    			"dc.APPLY_AMT," +
    			"dc.INSURE_AMT," +
    			"nvl(r.db_amts, 0) db_amts," +
    			"'已核批未动拨' STATE," +
    			"'7' HIDE_STEP," +
    			"s.f_value STEP_NAME," + 
    			"(trunc(sysdate, 'DD') - dc.record_date) TREAMENT_DAYS," +
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_APPROVED_C dc " +
    			"left join  (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'CASE_TYPE_SME') c " +
    			"on dc.case_type = c.f_code " +
    			"left join (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'PIPELINE_STEP') s " +
    			"on s.f_code = '6' " +
    			"left join (select t.pipeline_id," +
    			"sum(t.db_amt) as db_amts " +
    			"from OCRM_F_CI_MKT_APPROVED_DB t " +
    			"group by t.pipeline_id) r " +
    			"on dc.pipeline_id = r.pipeline_id " +
    			"left join (select pr.pipeline_id," +
    			"min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name," +
    			"p.record_date," +
    			"p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name," +
    			"i.record_date," +
    			"i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on dc.PIPELINE_ID = pro.pipeline_id) td " +
    			" where td.db_amts = 0 "+                                                                     
    			"UNION ALL " +
    			"select te.ID," +
    			"te.USER_ID," +
    			"te.PIPELINE_ID," +
    			"te.AREA_NAME," +
    			"te.AREA_ID," +
    			"te.DEPT_NAME," +
    			"te.DEPT_ID," +
    			"te.RM," +
    			"te.RM_ID," +
    			"te.CUST_NAME," +
    			"te.CUST_ID," +
    			"te.CASE_TYPE," +
    			"te.CASE_TYPE_NAME," +
    			"te.UPDATE_DATE," +
    			"te.APPLY_AMT," +
    			"te.INSURE_AMT," +
    			"te.DB_AMTS," +
    			"te.STATE," +
    			"te.HIDE_STEP," +
    			"te.STEP_NAME," +
    			"te.TREAMENT_DAYS," +
    			"te.TREAMENT_ALLDAYS " +
    			"from (SELECT dc.ID," +
    			"dc.USER_ID," +
    			"dc.PIPELINE_ID," +
    			"dc.AREA_NAME," +
    			"dc.AREA_ID," +
    			"dc.DEPT_NAME," +
    			"dc.DEPT_ID," +
    			"dc.RM," +
    			"dc.RM_ID," +
    			"dc.CUST_NAME," +
    			"dc.CUST_ID," +
    			"dc.CASE_TYPE," +
    			"c.f_value CASE_TYPE_NAME," +
    			"dc.UPDATE_DATE," +
    			"dc.APPLY_AMT," +
    			"dc.INSURE_AMT," +
    			"nvl(r.db_amts, 0) db_amts," +
    			"'已核批动拨' STATE," +
    			"'7' HIDE_STEP," +
    			"s.f_value STEP_NAME," + 
    			"(trunc(sysdate, 'DD') - dc.record_date) TREAMENT_DAYS," +
    			"(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS " +
    			"FROM OCRM_F_CI_MKT_APPROVED_C dc " +
    			"left join  (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'CASE_TYPE_SME') c " +
    			"on dc.case_type = c.f_code " +
    			"left join (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'PIPELINE_STEP') s " +
    			"on s.f_code = '6' " +
    			"left join (select t.pipeline_id," +
    			"sum(t.db_amt) as db_amts " +
    			"from OCRM_F_CI_MKT_APPROVED_DB t " +
    			"group by t.pipeline_id) r " +
    			"on dc.pipeline_id = r.pipeline_id " +
    			"left join (select pr.pipeline_id," +
    			"min(pr.record_date) as first_record_date " +
    			"from (select p.cust_name," +
    			"p.record_date," +
    			"p.pipeline_id " +
    			"from OCRM_F_CI_MKT_PROSPECT_C p " +
    			"union all " +
    			"select i.cust_name," +
    			"i.record_date," +
    			"i.pipeline_id " +
    			"from OCRM_F_CI_MKT_INTENT_C i) pr " +
    			"group by pr.pipeline_id) pro " +
    			"on dc.PIPELINE_ID = pro.pipeline_id) te " +
    			"where te.db_amts > 0 "+  
    			")) C " +
    			"left join admin_auth_account a " +
    			"on C.USER_ID = a.account_name " +
    			"where C.NN = '1' " +
    			"and (a.belong_busi_line = '5' or a.belong_busi_line = '0')";
    	
    	StringBuffer sb = null;
    	List<?> list = auth.getRolesInfo();
    	for(Object m : list){//判断用户角色是否为CO
    		Map<?, ?> map = (Map<?, ?>) m;
    		if("R121".equals(map.get("ROLE_CODE"))){//CO角色
    			sb = new StringBuffer(sql2);
    			continue;
    		}else{
    			sb = new StringBuffer(sql1);
    			continue;
    		}
    	}
    	String id = request.getParameter("id");
    	if(id != null && !"".equals(id)){
    		sb.append("and c.id = '"+ id+"'");
    	}else{
    		sb.append("and (C.STATE not like '是' or C.STATE is null)");
    		setPrimaryKey("c.id desc");
    	}
    	sb.append("and (C.CASE_TYPE = '1' or C.CASE_TYPE = '16' or C.CASE_TYPE = '17' or C.CASE_TYPE = '18' or C.CASE_TYPE is null)");
    	sb.append("and (C.STATE like '否决' or C.STATE like '退案' or C.STATE like '撤案' )");
    	SQL=sb.toString();
    	datasource = ds;
    	setPrimaryKey("c.hide_step asc,c.state desc");
    	
    	this.processSQL();//构造最终的查询语句，包括数据权限
    	try{
    	log.info("QUERY SQL: "+SQL);
        if(request.getParameter("start")!=null)
        	start=Integer.parseInt(request.getParameter("start"));
        if(request.getParameter("limit")!=null)
        	limit=Integer.parseInt(request.getParameter("limit"));
        int currentPage = start / limit + 1;
        PagingInfo pi=null;
        if(request.getParameter("start")!=null&&request.getParameter("limit")!=null){
        	pi = new PagingInfo(limit, currentPage);
        }
        Connection conn = datasource.getConnection();
        QueryHelper query;
        if("DB2".equals(SystemConstance.DB_TYPE) && withSQL != null) {
        	query = new QueryHelper(SQL, withSQL, conn, pi);
        } else {
        	query = new QueryHelper(SQL, conn, pi);
        }
        if (!"ID".equals(primaryKey)) {
            query.setPrimaryKey(primaryKey);
        }
        for (Entry<String, String> entry : oracleMapping.entrySet()) {
            query.addOracleLookup(entry.getKey(), entry.getValue());
        }
        	if(this.json!=null)
        		this.json.clear();
        	else 
        		this.json = new HashMap<String,Object>(); 
        	this.json.put("json",query.getJSON(this.transNames));

        	if(chart!=null){
        		chart.setJsonData(JSONObject.fromObject(this.json));
        		this.json = chart.getJson();
        	}
    	}catch(Exception e){
        	e.printStackTrace();
        	/*
 			  异常抛出的详细接口说明    
 			      参数一：direct：0是输出到错误页，1是协议输出
			      参数二：level：  0信息，1警告，2错误
			      参数三：code:自定义代码，四位格式，自定义代码省掉为标准事件，如404错误不需要再有自定代码,
			      	目前已定义 的码值在extendpoint-ytec-bi-exception.xml中定义,如下：
		          	<entry key="404" value="文件{0}不存在"></entry>
					<entry key="500-1000" value="环境配置错误"></entry>
					<entry key="500-1001" value="系统启动参数配置错误"></entry>
					<entry key="500-1002" value="数据库错误"></entry>
					<entry key="500-1003" value="访问接口错误"></entry>
					<entry key="500-1004" value="方法的参数错误"></entry>
					<entry key="500-1005" value="文件访问错误"></entry>
					<entry key="500-1006" value="空指针错误"></entry>
					<entry key="500-1007" value="数组下标越界"></entry>
					<entry key="500-1008" value="访问Servlet错误"></entry>
			      参数四：msg:错误原因或业务约束校验提示内容
        	 */
        	throw new BizException(1,2,"1002",e.getMessage());
        }
    	return "success";
	}
	
}
