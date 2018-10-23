package com.yuchengtech.bcrm.customer.belong.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.belong.service.OcrmFCiTransBusinessService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;

@Action("/ocrmFCiTransBusiness")
public class OcrmFCiTransBusinessAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
	@Autowired
	private OcrmFCiTransBusinessService service;
	
	private static Logger log = Logger.getLogger(OcrmFCiTransBusinessAction.class);
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	@Autowired
	public void init() {
		
	}
	
	/**
	 * pipeline营销概念SME中
	 * 检索pipeline移交历史数据
	 */
	public void queryHistory(){
		log.info("OcrmFCiTransBusinessAction:queryHistory() start");
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		try {
			String sb =" select hs.id,hs.cust_id,hs.cust_name,hs.pipeline_id,hs.before_mgr_id,hs.before_mgr_name,hs.after_mgr_id,hs.after_mgr_name," +
					"to_char(hs.effect_date,'yyyy-MM-dd') effect_date,hs.before_step,hs.before_state,to_char(hs.create_date,'yyyy-MM-dd') as create_date  " +
					"from ocrm_f_ci_trans_business_his hs  where 1 = 1  and hs.type = '3' " +
					" and hs.before_mgr_id in  (SELECT A1.ACCOUNT_NAME   FROM ADMIN_AUTH_ACCOUNT A1  WHERE A1.ACCOUNT_NAME = '"+auth.getUserId()+"'  OR A1.BELONG_TEAM_HEAD = '"+auth.getUserId()+"') " +
					" order by hs.create_date desc,hs.cust_id,hs.before_step   ";
			log.info("OcrmFCiTransBusinessAction:queryHistory()--->sql:"+sb);
			List<Object[]> list = service.getBaseDAO().findByNativeSQLWithNameParam(sb, null);
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Map<String, Object> result = new HashMap<String, Object>();
					Object[] objects = list.get(i);
					result.put("ID", objects[0]+"");
					result.put("CUST_ID", objects[1]+"");
					result.put("CUST_NAME", objects[2]+"");//案名
					result.put("PIPELINE_ID", objects[3]+"");
					result.put("BEFORE_MGR_ID", objects[4]+"");
					result.put("BEFORE_MGR_NAME", objects[5]+"");//移转前客户经理
					result.put("AFTER_MGR_ID", objects[6]+"");
					result.put("AFTER_MGR_NAME", objects[7]+"");//移转后客户经理
					result.put("EFFECT_DATE", objects[8]+"");//生效时间
					result.put("BEFORE_STEP", objects[9]+"");//移转前所属阶段
					result.put("BEFORE_STATE", objects[10]+"");//移转前状态
					result.put("CREATE_DATE", objects[11]+"");//移转时间
					
					Long pipelineId = Long.parseLong(objects[3]+"");
					String affterMgrId = (String) objects[6];
					//根据pipelineId和移转后所属经理获取pipeline移转后所属阶段以及状态
					Map<String, String> stepMap = getStepMap(pipelineId,affterMgrId);
					String affterStep = stepMap.get("AFTER_STEP");
					String affterState = stepMap.get("AFTER_STATE");
					
					result.put("AFTER_STEP",affterStep);//移转后所属阶段
					result.put("AFTER_STATE", affterState);//移转后状态
					listMap.add(result);
				}
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("data", listMap);
			if(this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String,Object>(); 
			this.json.put("json",data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("OcrmFCiTransBusinessAction:queryHistory() end");
	}
	
	/**
	 * 获取pipeline移转后所属的阶段以及状态
	 * @param pipelineId
	 * @param affterMgrId 移转后客户经理
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getStepMap(Long pipelineId,String affterMgrId){
		Map<String, String> stepMap = new HashMap<String, String>();
		String affterStep = "";
		String affterState = "";
		// 检索数据是否再次移转给其他经理
		String sb = "  select hs.*  from ocrm_f_ci_trans_business_his hs" +
				"   where 1 = 1  and hs.pipeline_id = '"+pipelineId+"'  and hs.before_mgr_id = '"+affterMgrId+"'  ";
		List<Object[]> list = service.getBaseDAO().findByNativeSQLWithNameParam(sb, null);
		if(list.size()>0){//再次移转给其他经理，历史表中再次移转前所属阶段和状态及本次移转后的最终所属阶段及状态
			Object[] object = list.get(0);
			affterStep = (String) object[9];
			affterState = (String) object[10];
		}else{//未再次移转,查询数据现在所属阶段及状态
			List<Object[]> list2 = new ArrayList<Object[]>();
			sb = " select c.* from OCRM_F_CI_MKT_APPROVED_C c where 1=1 and ( c.last_send_step <>'99'  or c.last_send_step is null) and c.pipeline_id='"+pipelineId+"' ";
			list2 = service.getBaseDAO().findByNativeSQLWithNameParam(sb, null);
			if(list2.size()>0){//已核批动拨阶段
				affterStep = "6";
				String sb2 = " select nvl(sum(t.db_amt), 0) as db_amts from OCRM_F_CI_MKT_APPROVED_DB t where t.pipeline_id='"+pipelineId+"' ";
				List<Object> list3 = service.getBaseDAO().findByNativeSQLWithNameParam(sb2, null);
				if(list3.size()>0){
					int object = Integer.parseInt(list3.get(0)+"");
					if(object > 0){
						affterState = "已核批动拨";
					}else{
						affterState = "已核批未动拨";
					}
				}else{
					affterState = "已核批未动拨";
				}
			}else{
				sb = " select c.IF_FIFTH_STEP,c.* from OCRM_F_CI_MKT_APPROVL_C c where 1=1 and (c.IF_FIFTH_STEP not in ('1', '5', '99') or(c.IF_FIFTH_STEP is null) or  (c.if_fifth_step = '1' and c.if_sure = '0')) and c.pipeline_id='"+pipelineId+"' ";
				list2 = service.getBaseDAO().findByNativeSQLWithNameParam(sb, null);
				if(list2.size()>0){//核批阶段
					Object[] objects = list2.get(0);
					affterStep = "5";
					affterState = getValue(objects[0]+"", "IF_FIFTH_STEP");
				}else{
					sb = " select c.IF_FOURTH_STEP,c.* from OCRM_F_CI_MKT_CHECK_C c where 1=1 and (c.IF_FOURTH_STEP not in ('1', '5', '99') or c.IF_FOURTH_STEP is null) and c.pipeline_id='"+pipelineId+"' ";
					list2 = service.getBaseDAO().findByNativeSQLWithNameParam(sb, null);
					if(list2.size()>0){//信用审查阶段
						Object[] objects = list2.get(0);
						affterStep = "4";
						affterState = getValue(objects[0]+"", "IF_FOURTH_STEP");
						
					}else{
						sb = " select c.IF_THIRD_STEP,c.* from OCRM_F_CI_MKT_CA_C c where 1=1 and ((c.IF_THIRD_STEP not like '1' and c.IF_THIRD_STEP not like '99') or c.IF_THIRD_STEP is null) and c.pipeline_id='"+pipelineId+"' ";
						list2 = service.getBaseDAO().findByNativeSQLWithNameParam(sb, null);
						if(list2.size()>0){//CA准备阶段
							affterStep = "3";
							Object[] objects = list2.get(0);
							affterState = getValue(objects[0]+"", "IF_THIRD_STEP_SME");
						}else{
							sb = " select c.IF_SECOND_STEP,c.* from OCRM_F_CI_MKT_INTENT_C c where 1=1 and ((c.if_second_step is null or c.if_second_step = '0' or  c.if_second_step = '2') or (c.GRADE_PERSECT = '6')) and c.pipeline_id='"+pipelineId+"' ";
							list2 = service.getBaseDAO().findByNativeSQLWithNameParam(sb, null);
							if(list2.size()>0){//合作意向阶段
								affterStep = "2";
								Object[] objects = list2.get(0);
								affterState = getValue(objects[0]+"", "IF_FLAG_HZ");
							}else{
								sb = " select c.if_pipeline,c.* from OCRM_F_CI_MKT_PROSPECT_C c where 1=1 and (IF_PIPELINE = '0' or IF_PIPELINE = '2') and c.pipeline_id='"+pipelineId+"' ";
								list2 = service.getBaseDAO().findByNativeSQLWithNameParam(sb, null);
								if(list2.size()>0){//prospect阶段
									affterStep = "1";
									Object[] objects = list2.get(0);
									affterState = getValue(objects[0]+"", "IF_PIPELINE");
								}
							}
						}
					}
				}
			}
		}
		
		stepMap.put("AFTER_STEP",affterStep);//移转后所属阶段
		stepMap.put("AFTER_STATE", affterState);//移转后状态
		return stepMap;
	}
	
	/**
	 * 根据key和lookUpId获取数字字典的值
	 * @param key
	 * @param lookUpId
	 * @return
	 */
	public String getValue(String key,String lookUpId){
		String value = "";
		String sb = "  select F_ID, F_VALUE, F_CODE, F_COMMENT, F_LOOKUP_ID  from ocrm_sys_lookup_item  " +
				"   where 1 > 0 and F_LOOKUP_ID = '"+lookUpId+"' ORDER BY F_CODE ";
		List<Object[]> list = service.getBaseDAO().findByNativeSQLWithNameParam(sb, null);
		if(list.size()>0 && StringUtils.isNotEmpty(key)){
			for(int i=0;i<list.size();i++){
				Object[] objects = list.get(i);
				if(key.equals(objects[2])){
					value = objects[1]+"";
					break;
				}
			}
		}
		return value;
	}
	
	/**
	 * 法金客户经理移交中
	 * 检索电访信息
	 */
	public void queryCall(){
		log.info("OcrmFCiTransBusinessAction:queryCall() start");
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custIdStr = request.getParameter("custId");
    	String mgrId = request.getParameter("mgrId");
    	StringBuffer sb = new StringBuffer();
    	try {
	    	sb.append( " select c.id,c.cust_id,c.cust_name,c.call_date,c.mgr_name,c.cust_type,c.review_state,c.mgr_id,c.VISIT_DATE,c.RECAL_DATE,c.org_id,null as effect_date "+
    				 "   from (select t1.id as id,t1.cust_id,t1.cust_name,t1.call_date,t1.mgr_name,t1.mgr_id,t1.review_state,'1' as cust_type,t1.VISIT_DATE,t1.RECAL_DATE,ac.org_id "+
    				 "         from ocrm_f_call_new_record t1   left join admin_auth_account ac     on ac.account_name = t1.mgr_id "+
    				 "      union "+
    				 "      select t2.id as id,t2.cust_id,t2.cust_name,t2.call_date,t2.mgr_name,t2.mgr_id,t2.review_state,'2' as cust_type,t2.VISIT_DATE,t2.VISIT_DATE as RECAL_DATE,ac2.org_id "+
    				 "        from ocrm_f_call_old_record t2   left join admin_auth_account ac2     on ac2.account_name = t2.mgr_id) c  " +
	    			 "  where 1 = 1  " );
			 if(StringUtils.isNotEmpty(mgrId)){
				 sb.append(" AND ( c.MGR_ID IN (SELECT A1.ACCOUNT_NAME   FROM ADMIN_AUTH_ACCOUNT A1  WHERE A1.ACCOUNT_NAME = '"+mgrId+"'  OR A1.BELONG_TEAM_HEAD = '"+mgrId+"')  ");
				 sb.append("       or c.id in  (SELECT HS.PIPELINE_ID  FROM OCRM_F_CI_TRANS_BUSINESS_HIS HS LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR R  ON HS.CUST_ID = R.CUST_ID  LEFT JOIN ACRM_F_CI_POT_CUS_COM R ON HS.CUST_ID = R.cus_id " +
				 		"         WHERE HS.TYPE = '1'  AND ( HS.AFTER_MGR_ID = R.MGR_ID or HS.AFTER_MGR_ID = R.CUST_MGR) AND HS.AFTER_MGR_ID IN (SELECT A1.ACCOUNT_NAME  FROM ADMIN_AUTH_ACCOUNT A1 WHERE A1.ACCOUNT_NAME = '"+mgrId+"' OR A1.BELONG_TEAM_HEAD = '"+mgrId+"')) ) ");
			 }
			 if(StringUtils.isNotEmpty(custIdStr)){
				 if(custIdStr.indexOf(",") != -1 ){
					 String[] custId = custIdStr.split(",");
					 if(custId.length == 1){
						 sb.append(" AND c.cust_id ='"+custId[0]+"' ");
					 }else if(custId.length > 1){
						 for(int i=0;i<custId.length;i++){
							 if(i==0){
								 sb.append(" AND c.cust_id ='"+custId[0]+"' ");
							 }else{
								 sb.append(" or c.cust_id ='"+custId[i]+"' ");
							 }
						 }
					 }
				 }else{
					 sb.append(" AND c.cust_id ='"+custIdStr+"' ");
				 }
			 }
	    	sb.append("   ORDER BY c.cust_id desc ");
		    log.info("OcrmFCiTransBusinessAction:queryCall()--->sql:"+sb);
    		QueryHelper query;
    		query = new QueryHelper(sb.toString(), ds.getConnection());
			Map<String, Object> result = query.getJSON();
			if(this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String,Object>(); 
			this.json.put("json",result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	log.info("OcrmFCiTransBusinessAction:queryCall() end");
	}
	
	/**
	 * 法金客户经理移交中
	 * 检索拜访信息
	 */
	public void queryVisit(){
		log.info("OcrmFCiTransBusinessAction:queryVisit() start");
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custIdStr = request.getParameter("custId");
    	String mgrId = request.getParameter("mgrId");
    	StringBuffer sb = new StringBuffer();
    	try {
    		sb.append(" select c.*,null as effect_date  ");
    		sb.append("   from (select c.id,c.cust_id,c.cust_name,c.mgr_id,c.mgr_name,c.task_type,to_date(to_char(c.visit_time, 'yyyy/mm/dd'), 'yyyy/mm/dd') as visit_time,  ");
    		sb.append("                c.VISIT_START_TIME,c.VISIT_END_TIME,c.create_time,c.remark,c.review_state,c.visit_type,c.task_number,c.new_record_id,c.flag,ac.org_id,m.conclusion  ");
    		sb.append("           from Ocrm_f_Interview_Record c  ");
    		sb.append("           left join ocrm_f_interview_new_record a  on c.task_number = a.task_number  ");
    		sb.append("           left join ocrm_f_interview_old_record b  on c.task_number = b.task_number  ");
    		sb.append("           left join ocrm_f_interview_old_purpose p on c.task_number = p.task_number  ");
    		sb.append("           left join acrm_f_ci_pot_cus_com m  on c.cust_id = m.cus_id  ");
    		sb.append("           left join admin_auth_account ac on ac.account_name = c.mgr_id  ");
    		sb.append("          where 1 = 1  and (c.task_type = '1' or(c.task_type = '0' and c.visit_type = '2'))  ");
    		sb.append("         union  ");
    		sb.append("         select c.id,c.cust_id,c.cust_name,c.mgr_id,c.mgr_name,c.task_type,to_date(to_char(c.visit_time, 'yyyy/mm/dd'), 'yyyy/mm/dd') as visit_time,  ");
    		sb.append("                c.VISIT_START_TIME,c.VISIT_END_TIME,c.create_time,c.remark,c.review_state,c.visit_type,c.task_number,c.new_record_id,c.flag,ac2.org_id,m.conclusion  ");
    		sb.append("           from Ocrm_f_Interview_Record c  ");
    		sb.append("           left join ocrm_f_interview_new_record a on c.task_number = a.task_number  ");
    		sb.append("           left join ocrm_f_interview_old_record b  on c.task_number = b.task_number  ");
    		sb.append("           left join ocrm_f_interview_old_purpose p  on c.task_number = p.task_number  ");
    		sb.append("           left join acrm_f_ci_pot_cus_com m  on c.cust_id = m.cus_id  ");
    		sb.append("           left join admin_auth_account ac2  on ac2.account_name = c.mgr_id  ");
    		sb.append("          where 1 = 1  and c.task_type = '0'  and (c.visit_type <> '2' or c.visit_type is null)) c  ");
    		sb.append("  where 1 = 1  ");
			 if(StringUtils.isNotEmpty(mgrId)){
				 sb.append(" AND ( c.MGR_ID IN (SELECT A1.ACCOUNT_NAME   FROM ADMIN_AUTH_ACCOUNT A1  WHERE A1.ACCOUNT_NAME = '"+mgrId+"'  OR A1.BELONG_TEAM_HEAD = '"+mgrId+"')  ");
				 sb.append("   or c.task_number IN (SELECT HS.PIPELINE_ID  FROM OCRM_F_CI_TRANS_BUSINESS_HIS HS LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR R  ON HS.CUST_ID = R.CUST_ID LEFT JOIN ACRM_F_CI_POT_CUS_COM R ON HS.CUST_ID = R.cus_id " +
				 		"         WHERE HS.TYPE = '2'  AND ( HS.AFTER_MGR_ID = R.MGR_ID or HS.AFTER_MGR_ID = R.CUST_MGR) AND HS.AFTER_MGR_ID IN (SELECT A1.ACCOUNT_NAME  FROM ADMIN_AUTH_ACCOUNT A1 WHERE A1.ACCOUNT_NAME = '"+mgrId+"' OR A1.BELONG_TEAM_HEAD = '"+mgrId+"')) ) ");
			 }
			 if(StringUtils.isNotEmpty(custIdStr)){
				 if(custIdStr.indexOf(",") != -1 ){
					 String[] custId = custIdStr.split(",");
					 if(custId.length == 1){
						 sb.append(" AND c.cust_id ='"+custId[0]+"' ");
					 }else if(custId.length > 1){
						 for(int i=0;i<custId.length;i++){
							 if(i==0){
								 sb.append(" AND c.cust_id ='"+custId[0]+"' ");
							 }else{
								 sb.append(" or c.cust_id ='"+custId[i]+"' ");
							 }
						 }
					 }
				 }else{
					 sb.append(" AND c.cust_id ='"+custIdStr+"' ");
				 }
			 }
		    sb.append("  ORDER BY c.cust_id asc  ");
		    log.info("OcrmFCiTransBusinessAction:queryVisit()---->sql:"+sb);
    		QueryHelper query;
    		query = new QueryHelper(sb.toString(), ds.getConnection());
			Map<String, Object> result = query.getJSON();
			if(this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String,Object>(); 
			this.json.put("json",result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	log.info("OcrmFCiTransBusinessAction:queryVisit() end");
	}
	
	/**
	 * 法金客户经理移交中
	 * 检索拜访信息
	 */
	public void queryPipline(){
		log.info("OcrmFCiTransBusinessAction:queryPipline() start");
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custIdStr = request.getParameter("custId");
    	String mgrId = request.getParameter("mgrId");
    	StringBuffer sb = new StringBuffer();
    	try {
    		sb.append(" SELECT C.ID,C.USER_ID,C.PIPELINE_ID,C.AREA_NAME,C.AREA_ID,C.DEPT_NAME,C.DEPT_ID,C.RM,C.RM_ID,C.CUST_NAME,C.CUST_ID,  "+
    				"       C.CASE_TYPE,C.APPLY_AMT,C.INSURE_AMT,C.DB_AMTS,C.STATE,C.HIDE_STEP,C.STEP,C.TREAMENT_DAYS,C.TREAMENT_ALLDAYS,  "+
    				"       (SELECT COUNT(R.CUST_ID)  "+
    				"          FROM OCRM_F_INTERVIEW_RECORD R  "+
    				"         WHERE R.CUST_ID = C.CUST_ID  "+
    				"           and (r.review_state = '3' or r.review_state = '03')) VISIT_COUNT,  "+
    				"           null as effect_date  "+
    				"  FROM (SELECT ID,USER_ID,PIPELINE_ID, AREA_NAME,AREA_ID, DEPT_NAME,DEPT_ID,RM,RM_ID,CUST_NAME,CUST_ID,CASE_TYPE,APPLY_AMT,  "+
    				"               INSURE_AMT,DB_AMTS,STATE, HIDE_STEP,STEP,TREAMENT_DAYS,TREAMENT_ALLDAYS,ROW_NUMBER() OVER(PARTITION BY PIPELINE_ID ORDER BY STEP DESC) NN  "+
    				"          FROM (SELECT pc.ID,pc.USER_ID,pc.PIPELINE_ID,pc.AREA_NAME,pc.AREA_ID,pc.DEPT_NAME,pc.DEPT_ID,pc.RM,pc.RM_ID,pc.CUST_NAME,pc.CUST_ID,  "+
    				"                       '' CASE_TYPE,NULL APPLY_AMT,NULL INSURE_AMT,NULL DB_AMTS,t.f_value STATE,'1' HIDE_STEP, '1' STEP,  "+
    				"                       (trunc(sysdate, 'DD') - pc.record_date) TREAMENT_DAYS,(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS  "+
    				"                  FROM OCRM_F_CI_MKT_PROSPECT_C pc  "+
    				"                  left join (select *  from ocrm_sys_lookup_item where F_LOOKUP_ID = 'IF_PIPELINE') t  "+
    				"                    on pc.IF_PIPELINE = t.f_code  "+
    				"                  left join (select pr.pipeline_id,min(pr.record_date) as first_record_date  "+
    				"                              from (select p.cust_name, p.record_date, p.pipeline_id  "+
    				"                                      from OCRM_F_CI_MKT_PROSPECT_C p  "+
    				"                                    union all  "+
    				"                                    select i.cust_name, i.record_date,i.pipeline_id  "+
    				"                                      from OCRM_F_CI_MKT_INTENT_C i) pr  "+
    				"                             group by pr.pipeline_id) pro  "+
    				"                    on pc.PIPELINE_ID = pro.pipeline_id  "+
    				"                 where (pc.IF_PIPELINE = '0' or pc.IF_PIPELINE = '2')  "+
    				"                UNION ALL  "+
    				"                SELECT ic.ID,ic.USER_ID,ic.PIPELINE_ID,ic.AREA_NAME,ic.AREA_ID,ic.DEPT_NAME,ic.DEPT_ID,ic.RM,ic.RM_ID,ic.CUST_NAME,ic.CUST_ID,  "+
    				"                       ic.CASE_TYPE,ic.APPLY_AMT,NULL INSURE_AMT, NULL DB_AMTS,t.f_value STATE,'2' HIDE_STEP,'2' STEP,  "+
    				"                       (trunc(sysdate, 'DD') - ic.record_date) TREAMENT_DAYS,(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS  "+
    				"                  FROM OCRM_F_CI_MKT_INTENT_C ic  "+
    				"                  left join (select * from ocrm_sys_lookup_item where F_LOOKUP_ID = 'IF_FLAG_HZ') t  on ic.IF_SECOND_STEP = t.f_code  "+
    				"                  left join (select pr.pipeline_id, min(pr.record_date) as first_record_date  "+
    				"                              from (select p.cust_name, p.record_date,p.pipeline_id   from OCRM_F_CI_MKT_PROSPECT_C p  "+
    				"                                    union all  "+
    				"                                    select i.cust_name, i.record_date, i.pipeline_id  from OCRM_F_CI_MKT_INTENT_C i) pr  "+
    				"                             group by pr.pipeline_id) pro  "+
    				"                    on ic.PIPELINE_ID = pro.pipeline_id  "+
    				"                  where (ic.IF_SECOND_STEP is null or ic.IF_SECOND_STEP = '0' or ic.IF_SECOND_STEP = '2')  "+
    				"                UNION ALL  "+
    				"                SELECT cc.ID,cc.USER_ID,cc.PIPELINE_ID,cc.AREA_NAME,cc.AREA_ID,cc.DEPT_NAME,cc.DEPT_ID,cc.RM,cc.RM_ID,cc.CUST_NAME,cc.CUST_ID,  "+
    				"                       cc.CASE_TYPE,cc.APPLY_AMT,NULL INSURE_AMT, NULL DB_AMTS,t.f_value STATE,'3' HIDE_STEP,'3' STEP,  "+
    				"                       (trunc(sysdate, 'DD') - cc.record_date) TREAMENT_DAYS, (trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS  "+
    				"                  FROM OCRM_F_CI_MKT_CA_C cc  "+
    				"                  left join (select *  from ocrm_sys_lookup_item where F_LOOKUP_ID = 'IF_THIRD_STEP') t on cc.IF_THIRD_STEP = t.f_code  "+
    				"                  left join (select pr.pipeline_id,min(pr.record_date) as first_record_date  "+
    				"                              from (select p.cust_name,p.record_date, p.pipeline_id  from OCRM_F_CI_MKT_PROSPECT_C p  "+
    				"                                    union all  "+
    				"                                    select i.cust_name,i.record_date, i.pipeline_id  from OCRM_F_CI_MKT_INTENT_C i) pr  "+
    				"                             group by pr.pipeline_id) pro  "+
    				"                    on cc.PIPELINE_ID = pro.pipeline_id  "+
    				"                  where ((cc.IF_THIRD_STEP not like '1' and cc.IF_THIRD_STEP not like '99') or cc.IF_THIRD_STEP is null) "+
    				"                   and ((cc.IF_SUMBIT_CO not like '1' and  cc.IF_SUMBIT_CO not like '3') or cc.IF_SUMBIT_CO is null) "+
    				"                UNION ALL  "+
    				"                SELECT ac.ID,ac.USER_ID,ac.PIPELINE_ID,ac.AREA_NAME,ac.AREA_ID,ac.DEPT_NAME,ac.DEPT_ID,ac.RM,ac.RM_ID,ac.CUST_NAME,ac.CUST_ID,  "+
    				"                       ac.CASE_TYPE,ac.APPLY_AMT,NULL INSURE_AMT,NULL DB_AMTS,t.f_value STATE,'4' HIDE_STEP,'3' STEP,  "+
    				"                       (trunc(sysdate, 'DD') - ac.record_date) TREAMENT_DAYS, (trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS  "+
    				"                  FROM OCRM_F_CI_MKT_CA_C ac  "+
    				"                  left join (select * from ocrm_sys_lookup_item  where F_LOOKUP_ID = 'IF_THIRD_STEP') t on ac.IF_THIRD_STEP = t.f_code  "+
    				"                  left join (select pr.pipeline_id,min(pr.record_date) as first_record_date  "+
    				"                              from (select p.cust_name, p.record_date,p.pipeline_id  from OCRM_F_CI_MKT_PROSPECT_C p  "+
    				"                                    union all  "+
    				"                                    select i.cust_name,i.record_date,i.pipeline_id  from OCRM_F_CI_MKT_INTENT_C i) pr  "+
    				"                             group by pr.pipeline_id) pro  "+
    				"                    on ac.PIPELINE_ID = pro.pipeline_id  "+
    				"                 where ((ac.IF_THIRD_STEP not like '1' and ac.IF_THIRD_STEP not like '99') or ac.IF_THIRD_STEP is null)  "+
    				"                   and ac.CASE_TYPE = '16'  and ac.IF_SUMBIT_CO = '1'  "+
    				"                UNION ALL  "+
    				"                SELECT hc.ID,hc.USER_ID,hc.PIPELINE_ID,hc.AREA_NAME,hc.AREA_ID,hc.DEPT_NAME,hc.DEPT_ID,hc.RM,hc.RM_ID,hc.CUST_NAME,hc.CUST_ID,  "+
    				"                       hc.CASE_TYPE,hc.APPLY_AMT,NULL INSURE_AMT, NULL DB_AMTS,t.f_value STATE,'5' HIDE_STEP,'4' STEP,  "+
    				"                       (trunc(sysdate, 'DD') - hc.record_date) TREAMENT_DAYS,(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS  "+
    				"                  FROM OCRM_F_CI_MKT_CHECK_C hc  "+
    				"                  left join (select *  from ocrm_sys_lookup_item where F_LOOKUP_ID = 'IF_FOURTH_STEP') t  on hc.IF_FOURTH_STEP = t.f_code  "+
    				"                  left join (select pr.pipeline_id,min(pr.record_date) as first_record_date  "+
    				"                              from (select p.cust_name,p.record_date,p.pipeline_id   from OCRM_F_CI_MKT_PROSPECT_C p  "+
    				"                                    union all  "+
    				"                                    select i.cust_name,i.record_date,i.pipeline_id  from OCRM_F_CI_MKT_INTENT_C i) pr  "+
    				"                             group by pr.pipeline_id) pro  "+
    				"                    on hc.PIPELINE_ID = pro.pipeline_id  "+
    				"                 where (hc.IF_FOURTH_STEP not in ('1', '5', '99') or hc.IF_FOURTH_STEP is null)  "+
    				"                UNION ALL  "+
    				"                SELECT lc.ID,lc.USER_ID,lc.PIPELINE_ID,lc.AREA_NAME,lc.AREA_ID,lc.DEPT_NAME,lc.DEPT_ID,lc.RM,lc.RM_ID,lc.CUST_NAME,lc.CUST_ID,  "+
    				"                       lc.CASE_TYPE,lc.APPLY_AMT,lc.INSURE_AMT,NULL DB_AMTS,t.f_value STATE,'6' HIDE_STEP,'5' STEP,  "+
    				"                       (trunc(sysdate, 'DD') - lc.record_date) TREAMENT_DAYS,(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS  "+
    				"                  FROM OCRM_F_CI_MKT_APPROVL_C lc  "+
    				"                  left join (select *  "+
    				"                              from ocrm_sys_lookup_item  "+
    				"                             where F_LOOKUP_ID = 'IF_FIFTH_STEP') t  "+
    				"                    on lc.IF_FIFTH_STEP = t.f_code  "+
    				"                  left join (select pr.pipeline_id,  "+
    				"                                   min(pr.record_date) as first_record_date  "+
    				"                              from (select p.cust_name,  "+
    				"                                           p.record_date,  "+
    				"                                           p.pipeline_id  "+
    				"                                      from OCRM_F_CI_MKT_PROSPECT_C p  "+
    				"                                    union all  "+
    				"                                    select i.cust_name,  "+
    				"                                           i.record_date,  "+
    				"                                           i.pipeline_id  "+
    				"                                      from OCRM_F_CI_MKT_INTENT_C i) pr  "+
    				"                             group by pr.pipeline_id) pro  "+
    				"                    on lc.PIPELINE_ID = pro.pipeline_id  "+
    				"                 where (lc.IF_FIFTH_STEP not in ('1', '5', '99') or  "+
    				"                       (lc.IF_FIFTH_STEP is null) or  "+
    				"                       (lc.IF_FIFTH_STEP = '1' and lc.IF_SURE = '0'))  "+
    				"                UNION ALL  "+
    				"                select td.ID,td.USER_ID,td.PIPELINE_ID,td.AREA_NAME,td.AREA_ID,td.DEPT_NAME,td.DEPT_ID,td.RM,td.RM_ID,td.CUST_NAME,td.CUST_ID,  "+
    				"                       td.CASE_TYPE,td.APPLY_AMT,td.INSURE_AMT,td.DB_AMTS,td.STATE,td.HIDE_STEP,td.STEP,td.TREAMENT_DAYS,td.TREAMENT_ALLDAYS  "+
    				"                  from (SELECT dc.ID,dc.USER_ID,dc.PIPELINE_ID,dc.AREA_NAME,dc.AREA_ID,dc.DEPT_NAME,dc.DEPT_ID,dc.RM,dc.RM_ID,dc.CUST_NAME,dc.CUST_ID,  "+
    				"                               dc.CASE_TYPE,dc.APPLY_AMT,dc.INSURE_AMT,nvl(r.db_amts, 0) db_amts,'已核批未动拨' STATE,'7' HIDE_STEP,'6' STEP,  "+
    				"                               (trunc(sysdate, 'DD') - dc.record_date) TREAMENT_DAYS, (trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS  "+
    				"                          FROM OCRM_F_CI_MKT_APPROVED_C dc  "+
    				"                          left join (select t.pipeline_id,  "+
    				"                                           sum(t.db_amt) as db_amts  "+
    				"                                      from OCRM_F_CI_MKT_APPROVED_DB t  "+
    				"                                     group by t.pipeline_id) r  "+
    				"                            on dc.pipeline_id = r.pipeline_id  "+
    				"                          left join (select pr.pipeline_id,  "+
    				"                                           min(pr.record_date) as first_record_date  "+
    				"                                      from (select p.cust_name,  "+
    				"                                                   p.record_date,  "+
    				"                                                   p.pipeline_id  "+
    				"                                              from OCRM_F_CI_MKT_PROSPECT_C p  "+
    				"                                            union all  "+
    				"                                            select i.cust_name,  "+
    				"                                                   i.record_date,  "+
    				"                                                   i.pipeline_id  "+
    				"                                              from OCRM_F_CI_MKT_INTENT_C i) pr  "+
    				"                                     group by pr.pipeline_id) pro  "+
    				"                            on dc.PIPELINE_ID = pro.pipeline_id) td  "+
    				"                 where td.db_amts = 0  "+
    				"                UNION ALL  "+
    				"                select te.ID,te.USER_ID,te.PIPELINE_ID,te.AREA_NAME,te.AREA_ID,te.DEPT_NAME,te.DEPT_ID,te.RM,te.RM_ID,te.CUST_NAME,te.CUST_ID,  "+
    				"                       te.CASE_TYPE,te.APPLY_AMT,te.INSURE_AMT,te.DB_AMTS,te.STATE,te.HIDE_STEP,te.STEP,te.TREAMENT_DAYS,te.TREAMENT_ALLDAYS  "+
    				"                  from (SELECT dc.ID,dc.USER_ID,dc.PIPELINE_ID,dc.AREA_NAME,dc.AREA_ID,dc.DEPT_NAME,dc.DEPT_ID,dc.RM,dc.RM_ID,dc.CUST_NAME,dc.CUST_ID,  "+
    				"                              dc.CASE_TYPE,dc.APPLY_AMT,dc.INSURE_AMT, nvl(r.db_amts, 0) db_amts,'已核批动拨' STATE,'7' HIDE_STEP, '6' STEP,  "+
    				"                               (trunc(sysdate, 'DD') - dc.record_date) TREAMENT_DAYS,(trunc(sysdate, 'DD') - pro.first_record_date) TREAMENT_ALLDAYS  "+
    				"                          FROM OCRM_F_CI_MKT_APPROVED_C dc  "+
    				"                          left join (select t.pipeline_id,  "+
    				"                                           sum(t.db_amt) as db_amts  "+
    				"                                      from OCRM_F_CI_MKT_APPROVED_DB t  "+
    				"                                     group by t.pipeline_id) r  "+
    				"                            on dc.pipeline_id = r.pipeline_id  "+
    				"                          left join (select pr.pipeline_id,  "+
    				"                                           min(pr.record_date) as first_record_date  "+
    				"                                      from (select p.cust_name,  "+
    				"                                                   p.record_date,  "+
    				"                                                   p.pipeline_id  "+
    				"                                              from OCRM_F_CI_MKT_PROSPECT_C p  "+
    				"                                            union all  "+
    				"                                            select i.cust_name,  "+
    				"                                                   i.record_date,  "+
    				"                                                   i.pipeline_id  "+
    				"                                              from OCRM_F_CI_MKT_INTENT_C i) pr  "+
    				"                                     group by pr.pipeline_id) pro  "+
    				"                            on dc.PIPELINE_ID = pro.pipeline_id) te  "+
    				"                 where te.db_amts > 0)) C  "+
    				"  left join admin_auth_account a  "+
    				"    on C.USER_ID = a.account_name  "+
    				" where C.NN = '1'  "+
    				"   and (a.belong_busi_line = '5' or a.belong_busi_line = '0')  "+
    				"   and (C.STATE not like '是' or C.STATE is null)  "+
    				"   and (C.CASE_TYPE = '1' or C.CASE_TYPE = '16' or C.CASE_TYPE = '17' or C.CASE_TYPE = '18' or C.CASE_TYPE is null)   ");
			 if(StringUtils.isNotEmpty(mgrId)){
				 sb.append(" AND ((C.RM_ID IN (SELECT A1.ACCOUNT_NAME  FROM ADMIN_AUTH_ACCOUNT A1 WHERE A1.ACCOUNT_NAME = '"+mgrId+"'  OR A1.BELONG_TEAM_HEAD = '"+mgrId+"'))) ");
			 }
			 if(StringUtils.isNotEmpty(custIdStr)){
				 if(custIdStr.indexOf(",") != -1 ){
					 String[] custId = custIdStr.split(",");
					 if(custId.length == 1){
						 sb.append(" AND c.CUST_ID ='"+custId[0]+"' ");
					 }else if(custId.length > 1){
						 for(int i=0;i<custId.length;i++){
							 if(i==0){
								 sb.append(" AND ( c.CUST_ID ='"+custId[0]+"' ");
							 }else if(i==custId.length-1){
								 sb.append(" or c.CUST_ID ='"+custId[i]+"' ) ");
							 }else{
								 sb.append(" or c.CUST_ID ='"+custId[i]+"'  ");
							 }
						 }
					 }
				 }else{
					 sb.append(" AND c.CUST_ID ='"+custIdStr+"' ");
				 }
			 }
	    	sb.append("   ORDER BY c.CUST_ID,c.step asc, c.state desc  ");
	    	log.info("OcrmFCiTransBusinessAction:queryPipline()---->sql:"+sb);
    		QueryHelper query;
    		query = new QueryHelper(sb.toString(), ds.getConnection());
			Map<String, Object> result = query.getJSON();
			if(this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String,Object>(); 
			this.json.put("json",result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	log.info("OcrmFCiTransBusinessAction:queryPipline() end");
	}

}
