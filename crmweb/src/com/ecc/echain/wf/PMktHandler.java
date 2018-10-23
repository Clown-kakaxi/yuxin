package com.ecc.echain.wf;

import java.sql.SQLException;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
/***
 * 潜在客户营销流程处理
 * @author luyueyue
 * 2014-07-23
 * @MODIFY
 *
 */
public class PMktHandler extends EChainCallbackCommon{
	//通过处理
	public void endCall(EVO vo){
		
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			String type = instanceids[2];
			String isNew=instanceids[3];
			if("2".equals(type)){//企商金
				if("1".equals(isNew))//新客户
				SQL = " update ocrm_f_call_new_record set review_state = '3' where id = "+instanceids[1]+" ";
				if("2".equals(isNew))//既有客户
				SQL = " update ocrm_f_call_old_record set review_state = '3' where id = "+instanceids[1]+" ";
			}
			if("1".equals(type))//个金
				SQL = " update OCRM_F_CI_MKT_CALL_P set CHECK_STAT = '3' where id = "+instanceids[1]+" ";
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
		

	}
	
    //电访拒绝处理
	public void endCallN(EVO vo) {

		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			String type = instanceids[2];
			String isNew = instanceids[3];
			if ("2".equals(type)) {// 企商金
				if ("1".equals(isNew))// 新客户
					SQL = " update ocrm_f_call_new_record set review_state = '4' where id = "
							+ instanceids[1] + " ";
				if ("2".equals(isNew))// 既有客户
					SQL = " update ocrm_f_call_old_record set review_state = '4' where id = "
							+ instanceids[1] + " ";
			}
			if ("1".equals(type))// 个金
				SQL = " update OCRM_F_CI_MKT_CALL_P set CHECK_STAT = '4' where id = "
						+ instanceids[1] + " ";
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}

	}

public void endVisit(EVO vo){
		
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			String type = instanceids[2];
			if("2".equals(type))//企商金
				SQL = " update OCRM_F_CI_MKT_VISIT_C set CHECK_STAT = '3' where id = "+instanceids[1]+" ";
			if("1".equals(type))//个金
				SQL = " update OCRM_F_CI_MKT_VISIT_P set CHECK_STAT = '3' where id = "+instanceids[1]+" ";
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
		

	}

public void endMkt(EVO vo){
	
	try {
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split("_");
		String type = instanceids[2];
		String table = "";
		if("11".equals(type)){//个金prospect
			table = "OCRM_F_CI_MKT_PROSPECT_P";
			//如果进入PIPELINE,则记录插入OCRM_F_CI_MKT_INTENT_P	个金合作意向
			String sql2 = " insert into OCRM_F_CI_MKT_INTENT_P (id,CALL_ID,prospect_ID,CUST_ID,CUST_NAME,AREA_ID," +
					"AREA_NAME,DEPT_ID,DEPT_NAME,RM,PRODUCT_NEED,VISIT_DATE,CHECK_STAT,RECORD_DATE,user_id) " +
					"select ID_SEQUENCE.NEXTVAL,CALL_ID,id as prospect_ID,CUST_ID,CUST_NAME,AREA_ID,AREA_NAME," +
					"DEPT_ID,DEPT_NAME,RM,PRODUCT_NEED,VISIT_DATE,'1',to_date(to_char(sysdate,'YYYY-MM-DD'),'YYYY-MM-DD'),user_id  " +
					"  from OCRM_F_CI_MKT_PROSPECT_P where id = "+instanceids[1]+" and IF_PIPELINE='1' ";
			SQLS.add(sql2);
		}
		if("12".equals(type)){//个金合作意向
			table = "OCRM_F_CI_MKT_INTENT_P";
			//如果进入下一阶段  记录插入OCRM_F_CI_MKT_ADVISE_P
			String sql2 = " insert into OCRM_F_CI_MKT_ADVISE_P (ID,CALL_ID,INTENT_ID,CUST_ID,CUST_NAME,AREA_ID,AREA_NAME,DEPT_ID," +
					"DEPT_NAME,RM,PRODUCT_ID,PRODUCT_NAME,PRODUCT_CATL,USER_ID,CHECK_STAT,RECORD_DATE) select ID_SEQUENCE.NEXTVAL,a.CALL_ID," +
					"a.id as INTENT_ID,a.CUST_ID,a.CUST_NAME,a.AREA_ID,a.AREA_NAME,a.DEPT_ID,a.DEPT_NAME,a.RM,a.PRODUCT_ID,a.PRODUCT_NAME," +
					"p.CATL_CODE as PRODUCT_CATL,a.USER_ID,'1',to_date(to_char(sysdate,'YYYY-MM-DD'),'YYYY-MM-DD') from OCRM_F_CI_MKT_INTENT_P a left join " +
					"OCRM_F_PD_PROD_INFO p on a.PRODUCT_ID = p.PRODUCT_ID  where a.id = "+instanceids[1]+" and IF_SECOND_STEP='1' ";
			SQLS.add(sql2);
		}
		if("13".equals(type)){//个金产品建议书准备
			table = "OCRM_F_CI_MKT_ADVISE_P";
			//如果进入下一阶段  记录插入OCRM_F_CI_MKT_CHANGE_P
			String sql2 = " insert into OCRM_F_CI_MKT_CHANGE_P (ID,CALL_ID,PLAN_ID,CUST_ID,CUST_NAME,AREA_ID,AREA_NAME,DEPT_ID,DEPT_NAME," +
					"RM,PRODUCT_ID,PRODUCT_NAME,SALE_AMT,PRODUCT_CATL,RISK_LEVEL,USER_ID,CHECK_STAT,RECORD_DATE) select ID_SEQUENCE.NEXTVAL,CALL_ID," +
					"id as PLAN_ID,CUST_ID,CUST_NAME,AREA_ID,AREA_NAME,DEPT_ID,DEPT_NAME,RM,PRODUCT_ID,PRODUCT_NAME,SALE_AMT,PRODUCT_CATL," +
					"RISK_LEVEL,USER_ID,'1',to_date(to_char(sysdate,'YYYY-MM-DD'),'YYYY-MM-DD') from OCRM_F_CI_MKT_ADVISE_P where " +
					 "id = "+instanceids[1]+" and IF_THIRD_STEP='1' ";
			SQLS.add(sql2);
		}
		if("14".equals(type)){//个金修正产品准备书
			table = "OCRM_F_CI_MKT_CHANGE_P";
			//如果进入下一阶段  记录插入OCRM_F_CI_MKT_END_P
			String sql2 = "insert into OCRM_F_CI_MKT_END_P (ID,CALL_ID,PLAN_CHANGE_ID,CUST_ID,CUST_NAME,AREA_ID,AREA_NAME,DEPT_ID,DEPT_NAME,RM,PRODUCT_ID," +
					"PRODUCT_NAME, USER_ID,RECORD_DATE,CHECK_STAT)select ID_SEQUENCE.NEXTVAL,CALL_ID,id as PLAN_CHANGE_ID,CUST_ID,CUST_NAME,AREA_ID,AREA_NAME,DEPT_ID," +
					"DEPT_NAME,RM,PRODUCT_ID,PRODUCT_NAME,USER_ID,to_date(to_char(sysdate,'YYYY-MM-DD'),'YYYY-MM-DD'),'1' from OCRM_F_CI_MKT_CHANGE_P " +
					"where  id = "+instanceids[1]+" and IF_FOURTH_STEP='1' ";
			SQLS.add(sql2);
		}
		if("15".equals(type)){//个金结案信息
			table = "OCRM_F_CI_MKT_END_P";
			//OCRM_F_CI_MKT_PIPELINE_P
			String sql2 = "insert into OCRM_F_CI_MKT_PIPELINE_P (ID,CALL_ID,END_ID,CUST_ID,CUST_NAME,AREA_ID,AREA_NAME,DEPT_ID,DEPT_NAME, " +
					"RM,PRODUCT_ID,PRODUCT_NAME,IF_DEAL,BUY_AMT,VISIT_DATE,RISK_LEVEL_PERSECT,HARD_INFO,SALE_AMT,PRODUCT_CATL,RISK_LEVEL, " +
					"DEAL_DATE,ACCOUNT_DATE,REFUSE_REASON,USER_ID,RECORD_DATE) select ID_SEQUENCE.NEXTVAL,a1.CALL_ID,a1.id as END_ID,a1.CUST_ID, " +
					"a1.CUST_NAME,a1.AREA_ID,a1.AREA_NAME,a1.DEPT_ID,a1.DEPT_NAME,a1.RM,a1.PRODUCT_ID,a1.PRODUCT_NAME,a1.IF_DEAL,a1.BUY_AMT, " +
					"a2.VISIT_DATE,a2.RISK_LEVEL_PERSECT,a2.HARD_INFO,a3.SALE_AMT,a3.PRODUCT_CATL,a3.RISK_LEVEL,a1.DEAL_DATE,a1.ACCOUNT_DATE, " +
					"a1.REFUSE_REASON,a1.user_id,to_date(to_char(sysdate,'YYYY-MM-DD'),'YYYY-MM-DD') from OCRM_F_CI_MKT_END_P a1,OCRM_F_CI_MKT_INTENT_P a2," +
					"OCRM_F_CI_MKT_CHANGE_P a3 where a1.call_id = a2.call_id and a2.call_id=a3.call_id and a1.id = "+instanceids[1]+"";
			SQLS.add(sql2);
		}
		if("21".equals(type)){//企商金prospect表
			table = "OCRM_F_CI_MKT_PROSPECT_C";
			String sql2 = "insert into OCRM_F_CI_MKT_INTENT_C (ID,CALL_ID,prospect_ID,CUST_ID,CUST_NAME,GROUP_NAME,AREA_ID,AREA_NAME,DEPT_ID,DEPT_NAME, " +
					"RM,VISIT_DATE,USER_ID,CHECK_STAT,RECORD_DATE) select ID_SEQUENCE.NEXTVAL,call_id,id as prospect_ID, " +
					"cust_id,cust_name,group_name,AREA_ID,AREA_NAME,DEPT_ID,DEPT_NAME,RM,VISIT_DATE,USER_ID,'1',to_date(to_char(sysdate,'YYYY-MM-DD'),'YYYY-MM-DD') " +
					" from OCRM_F_CI_MKT_PROSPECT_C where id = "+instanceids[1]+" and IF_PIPELINE='1' ";
			SQLS.add(sql2);
			
		}
		if("22".equals(type)){//企商金合作意向
			table = "OCRM_F_CI_MKT_INTENT_C";
			String sql2 = "insert into OCRM_F_CI_MKT_CA_C(ID,CALL_ID,INTENT_ID,CUST_ID,CUST_NAME,GROUP_NAME,AREA_ID,AREA_NAME,DEPT_ID, " +
					"DEPT_NAME,RM,APPLY_AMT,CASE_TYPE,USER_ID,CHECK_STAT,RECORD_DATE) select ID_SEQUENCE.NEXTVAL,call_id,id as INTENT_ID, " +
					"CUST_ID,CUST_NAME,GROUP_NAME,AREA_ID,AREA_NAME,DEPT_ID,DEPT_NAME,RM,APPLY_AMT,CASE_TYPE,USER_ID,'1',to_date(to_char(sysdate,'YYYY-MM-DD'),'YYYY-MM-DD') " + 
					" from OCRM_F_CI_MKT_INTENT_C where id = "+instanceids[1]+" and IF_SECOND_STEP = '1'";
			SQLS.add(sql2);
		}
		if("23".equals(type)){//企商金CA准备
			table = "OCRM_F_CI_MKT_CA_C";
			String sql2 = " insert into OCRM_F_CI_MKT_CHECK_C (ID,CALL_ID,CA_ID,CUST_ID,CUST_NAME,GROUP_NAME,AREA_ID,AREA_NAME, " +
					"DEPT_ID,DEPT_NAME,RM,APPLY_AMT,COMP_TYPE,GRADE_LEVEL,CASE_TYPE,IF_ADD,ADD_AMT,USER_ID,CHECK_STAT,RECORD_DATE) " +
					"select ID_SEQUENCE.NEXTVAL,a1.CALL_ID,a1.id as CA_ID,a1.CUST_ID,a1.CUST_NAME,a1.GROUP_NAME,a1.AREA_ID,a1.AREA_NAME, " +
					"a1.DEPT_ID,a1.DEPT_NAME,a1.RM,a1.APPLY_AMT,a2.COMP_TYPE,a1.GRADE_LEVEL,a1.CASE_TYPE,a1.IF_ADD,a1.ADD_AMT,a1.USER_ID," +
					"'1',to_date(to_char(sysdate,'YYYY-MM-DD'),'YYYY-MM-DD')  " +
					" from OCRM_F_CI_MKT_CA_C a1,OCRM_F_CI_MKT_INTENT_C a2 where a2.id = a1.intent_id and a1.id = "+instanceids[1]+" and a1.IF_THIRD_STEP = '1'";
			SQLS.add(sql2);
		}
		if("24".equals(type)){//信用审查
			table = "OCRM_F_CI_MKT_CHECK_C";
			String sql2 = "insert into OCRM_F_CI_MKT_APPROVL_C (ID,CALL_ID,SC_ID,CUST_ID,CUST_NAME,GROUP_NAME,AREA_ID,AREA_NAME,DEPT_ID,DEPT_NAME,RM,APPLY_AMT, " +
				"COMP_TYPE,GRADE_LEVEL,CASE_TYPE,IF_ADD,ADD_AMT,SP_LEVEL,USER_ID,CHECK_STAT,RECORD_DATE) " +
				"select ID_SEQUENCE.NEXTVAL,CALL_ID,id as SC_ID,CUST_ID,CUST_NAME,GROUP_NAME,AREA_ID,AREA_NAME,DEPT_ID,DEPT_NAME,RM,APPLY_AMT, " +
				"COMP_TYPE,GRADE_LEVEL,CASE_TYPE,IF_ADD,ADD_AMT,SP_LEVEL,USER_ID,'1',to_date(to_char(sysdate,'YYYY-MM-DD'),'YYYY-MM-DD')  " +
				"from OCRM_F_CI_MKT_CHECK_C where id = "+instanceids[1]+" and IF_FOURTH_STEP = '1'";
			SQLS.add(sql2);
		}
		if("25".equals(type)){//核批阶段
			table = "OCRM_F_CI_MKT_APPROVL_C";
			String sql2 = "insert into OCRM_F_CI_MKT_APPROVED_C (ID,CALL_ID,HP_ID,CUST_ID,CUST_NAME,GROUP_NAME,AREA_ID,AREA_NAME,DEPT_ID,DEPT_NAME,RM,APPLY_AMT, " +
				"COMP_TYPE,GRADE_LEVEL,CASE_TYPE,IF_ADD,ADD_AMT,USE_DATE_P,USER_ID,RECORD_DATE,CHECK_STAT) " +
				"select ID_SEQUENCE.NEXTVAL,CALL_ID,id as HP_ID,CUST_ID,CUST_NAME,GROUP_NAME,AREA_ID,AREA_NAME,DEPT_ID,DEPT_NAME,RM,APPLY_AMT, " +
				"COMP_TYPE,GRADE_LEVEL,CASE_TYPE,IF_ADD,ADD_AMT,USE_DATE_P,USER_ID,to_date(to_char(sysdate,'YYYY-MM-DD'),'YYYY-MM-DD'),'1'  " + 
				"from OCRM_F_CI_MKT_APPROVL_C where id = "+instanceids[1]+" and IF_FIFTH_STEP = '1'";
			SQLS.add(sql2);
		}
		if("26".equals(type)){//已核批动拨
			table = "OCRM_F_CI_MKT_APPROVED_C";
			String sql2 = "insert into OCRM_F_CI_MKT_PIPELINE_C (ID,CALL_ID,AD_ID,CUST_ID,CUST_NAME,GROUP_NAME,AREA_ID,AREA_NAME,DEPT_ID, " +
				"DEPT_NAME,RM,CASE_TYPE,APPLY_AMT,VISIT_DATE,MAIN_INSURE,MAIN_AMT,COMBY_AMT,GRADE_PERSECT,COMP_TYPE,HARD_INFO, " +
				"CP_HARD_INFO,DD_DATE,SX_DATE,GRADE_LEVEL,COCO_DATE,COCO_INFO,XD_CA_DATE,CA_FORM,QA_DATE,RM_DATE,CC_DATE,XS_CC_DATE, " +
				"RM_C_DATE,CO,XZ_CA_DATE,XZ_CA_FORM,USE_DATE_P,SP_LEVEL,CC_OPEN_DATE,IF_SURE,INSURE_AMT,INSURE_FORM,DELINE_REASON, " +
				"XD_HZ_DATE,IF_ACCEPT,NOACCEPT_REASON,CTR_C_DATE,CTR_S_DATE,MORTGAGE_DATE,FILE_UP_DATE,SX_CTR_DATE,CTR_PROBLEM,PROBLEM_DATE, " +
				"AMT_USE_DATE,ACCOUNT_DATE,PAY_DATE) " +
				"select ID_SEQUENCE.NEXTVAL,a1.CALL_ID,a1.id as AD_ID,a1.CUST_ID,a1.CUST_NAME,a1.GROUP_NAME,a1.AREA_ID,a1.AREA_NAME,a1.DEPT_ID, " +
				"a1.DEPT_NAME,a1.RM,a1.CASE_TYPE,a1.APPLY_AMT,a2.VISIT_DATE,a2.MAIN_INSURE,a2.MAIN_AMT,a2.COMBY_AMT,a2.GRADE_PERSECT,a2.COMP_TYPE,a2.HARD_INFO,a2.CP_HARD_INFO " +
				",a3.DD_DATE,a3.SX_DATE,a3.GRADE_LEVEL,a3.COCO_DATE,a3.COCO_INFO,a4.XD_CA_DATE,a4.CA_FORM,a4.QA_DATE,a4.RM_DATE,a4.CC_DATE,a4.XS_CC_DATE,a4.RM_C_DATE,a4.CO " +
				",a5.XZ_CA_DATE,a5.XZ_CA_FORM,a5.USE_DATE_P,a5.SP_LEVEL,a5.CC_OPEN_DATE,a5.IF_SURE,a5.INSURE_AMT,a5.INSURE_FORM,a5.DELINE_REASON " +
				",a1.XD_HZ_DATE,a1.IF_ACCEPT,a1.NOACCEPT_REASON,a1.CTR_C_DATE,a1.CTR_S_DATE,a1.MORTGAGE_DATE,a1.FILE_UP_DATE,a1.SX_CTR_DATE, " +
				"a1.CTR_PROBLEM,a1.PROBLEM_DATE,a1.AMT_USE_DATE,a1.ACCOUNT_DATE,a1.PAY_DATE   " + 
				"from OCRM_F_CI_MKT_APPROVED_C a1,OCRM_F_CI_MKT_INTENT_C a2,OCRM_F_CI_MKT_CA_C a3,OCRM_F_CI_MKT_CHECK_C a4 ,OCRM_F_CI_MKT_APPROVL_C a5 " +
				" where a1.call_id=a2.call_id and a2.call_id = a3.call_id and a3.call_id=a4.call_id and a4.call_id=a5.call_id and a1.id = "+instanceids[1]+" ";
			SQLS.add(sql2);
		}
		String sql1 =  " update "+table+" set CHECK_STAT = '3' where id = "+instanceids[1]+" ";
		SQLS.add(sql1);
		executeBatch(vo);
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("执行SQL出错");
	}
	

}

public void MktNo(EVO vo){
	
	try {
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split("_");
		String type = instanceids[2];
		String table = "";
		if("11".equals(type)){//个金prospect
			table = "OCRM_F_CI_MKT_PROSPECT_P";
		}
		if("12".equals(type)){//个金合作意向
			table = "OCRM_F_CI_MKT_INTENT_P";
		}
		if("13".equals(type)){//个金产品建议书准备
			table = "OCRM_F_CI_MKT_ADVISE_P";
		}
		if("14".equals(type)){//个金修正产品准备书
			table = "OCRM_F_CI_MKT_CHANGE_P";
		}
		if("15".equals(type)){//个金结案信息
			table = "OCRM_F_CI_MKT_END_P";
		}
		if("21".equals(type)){//企商金prospect表
			table = "OCRM_F_CI_MKT_PROSPECT_C";
		}
		if("22".equals(type)){//企商金合作意向
			table = "OCRM_F_CI_MKT_INTENT_C";
		}
		if("23".equals(type)){//企商金CA准备
			table = "OCRM_F_CI_MKT_CA_C";
		}
		if("24".equals(type)){//信用审查
			table = "OCRM_F_CI_MKT_CHECK_C";
		}
		if("25".equals(type)){//核批阶段
			table = "OCRM_F_CI_MKT_APPROVL_C";
		}
		if("26".equals(type)){//已核批动拨
			table = "OCRM_F_CI_MKT_APPROVED_C";
		}
		SQL = " update "+table+" set CHECK_STAT = '4' where id = "+instanceids[1]+" ";
		execteSQL(vo);
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("执行SQL出错");
	}
	

}


}
