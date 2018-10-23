package com.ecc.echain.wf;

import java.sql.SQLException;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.common.DateUtils;
import com.yuchengtech.bcrm.custmanager.model.AcrmAAntiChangeGradeHi;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
/***
 * 反洗钱风险等级审核处理流程  AntMoneyRisk   ==AntMoneyRiskLevel 流程图字符长度限制
 *
 * @author hujun
 * 2014-07-04
 */
public class AntMoneyRisk extends EChainCallbackCommon{
	
	//通过处理
	public void endY(EVO vo){		
		try {
    		//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息  0是标示，1是用户ID，2是当前风险等级，3是审核风险等级，4 客户类型（流程处理中显示客户信息分辨是个人还是企业） 5.流程发起人ID  6.是审核结束表中的数据总数 cust_type 
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			if(!"".equals(instanceids[1])&instanceids[1]!=null){
				
				//获取当前时间
				String evaluateDateNew=(new DateUtils().getCurrentDateTimeF().toString());
				//查询出历史风险等级
				SQL = "select *from  ACRM_F_CI_GRADE where cust_grade_type='01' and cust_id='"+instanceids[1]+"'";
				Result result=querySQL(vo);
				AcrmAAntiChangeGradeHi ws=new AcrmAAntiChangeGradeHi();
				for (SortedMap<?, ?> row  : result.getRows()){
					ws.setCustId((String)row.get("CUST_ID"));
					ws.setCustGradeType((String)row.get("CUST_GRADE_TYPE"));//调整前等级 
					
					ws.setCustGradeOld((String)row.get("CUST_GRADE"));//客户等级类型 
					
					ws.setEvaluateDateOld(row.get("EVALUATE_DATE").toString());// 取等级表等级评定时间 
					ws.setLastUpdateUserOld((String)row.get("LAST_UPDATE_USER"));	//取等级表等级评定人  
					
					ws.setCustGradeNew(instanceids[3]);	//调整后等级 
					ws.setEvaluateDateNew(evaluateDateNew);	//调整等级后等级评级时间
					ws.setLastUpdateUserNew(instanceids[5]);	//调整等级后等级评级人  
					break;
					
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
						" LAST_UPDATE_USER_NEW,"+
						" INSTANCE_ID," +//流程ID
						" INSTANCE_TYPE" +//流程类型,1：定期审核，2：等级调整，3：复评	
						" )"+
						" values(" +
						"ID_INDEX_INSTRUCTION.Nextval,"+
						"'"+ws.getCustId()+"'" +
						",'"+ws.getCustGradeType()+"'" +
						",'"+ws.getCustGradeOld()+"'" +
						",'"+ws.getEvaluateDateOld()+"'" +
						",'"+ws.getLastUpdateUserOld()+"'" +
						",'"+instanceids[3]+"'" +
						",'"+evaluateDateNew+"'" +
						",'"+instanceids[5]+"'" +
						",'"+instanceid+"'"+
						",'1')";
				execteSQL(vo);
				//修改等级操作
				SQL="update ACRM_F_CI_GRADE set cust_grade= '"+instanceids[3]+"',LAST_UPDATE_TM=SYSDATE,EFFECTIVE_DATE=SYSDATE,EVALUATE_DATE=SYSDATE,LAST_UPDATE_USER='"+instanceids[5]+"'  where cust_id='"+instanceids[1]+"' and cust_grade_type='01'";
				execteSQL(vo);//通过是审批修改客户等级表
				//审核结果0 表示通过， 1表示否决
				SQL="update ACRM_F_CI_GRADE_DQ set check_result='0',check_end_date=sysdate,CHECK_STATUS='2' where cust_id='"+instanceids[1]+"' and instanceid='"+instanceid+"'"; 
				execteSQL(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
		
		
	}
	//拒绝处理
	public void endN(EVO vo){		
		//不做任何操作
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			if(!"".equals(instanceids[1])&instanceids[1]!=null){
				SQL="update ACRM_F_CI_GRADE_DQ set check_result='1',check_end_date=sysdate,CHECK_STATUS='2' where cust_id='"+instanceids[1]+"' and instanceid='"+instanceid+"'"; 
				execteSQL(vo);//审批修改客户等级表--否决
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}
	



}
