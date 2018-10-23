package com.ecc.echain.wf;

import java.sql.SQLException;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.common.DateUtils;
import com.yuchengtech.bcrm.custmanager.model.AcrmAAntiChangeGradeHi;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;

/**
 * 
 * @description : 反洗钱复评处理流程[动态调用类]  AMReview   ==AntMoneyReview 流程图字符长度限制
 *
 * @author : zhaolong
 * @date : 2016-2-2 下午3:54:06
 */
public class AMReview extends EChainCallbackCommon{
	
	//通过处理
	public void endY(EVO vo){		
		try {
    		//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息  0是标示，1是用户ID，2是当前风险等级，3是审核风险等级，4 客户类型（流程处理中显示客户信息分辨是个人还是企业） 5.流程发起人ID  6.是审核结束表中的数据总数 cust_type 
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			
			
			if(!"".equals(instanceids[1])&instanceids[1]!=null){
				//比对预评等级，若系统风险等级较高，以系统为准；系统风险等级较低，以预评等级为准，同时指标状态变更为“已复评” 不牵扯到高
				//boolean flag="H".equals(instanceids[2])||("M".equals(instanceids[2])&&"L".equals(instanceids[3]));
				if("H".equals(instanceids[2])||"H".equals(instanceids[3])||!("M".equals(instanceids[2])&&"L".equals(instanceids[3])))
				{
					SQL = "select *from  ACRM_F_CI_GRADE where cust_grade_type='01' and cust_id='"+instanceids[1]+"'";
					Result result=querySQL(vo);
					AcrmAAntiChangeGradeHi ws=new AcrmAAntiChangeGradeHi();
					for (SortedMap<?, ?> row  : result.getRows()){
						ws.setCustId((String)row.get("CUST_ID"));
						ws.setCustGradeType((String)row.get("CUST_GRADE_TYPE"));//调整前等级 
						
						ws.setCustGradeOld((String)row.get("CUST_GRADE"));//客户等级类型 
						
						ws.setEvaluateDateOld(row.get("EVALUATE_DATE").toString());//客户等级类型 
						ws.setLastUpdateUserOld((String)row.get("LAST_UPDATE_USER"));	//调整等级前等级评级人 
						
						ws.setCustGradeNew(instanceids[3]);	//调整后等级 
						ws.setEvaluateDateNew(new DateUtils().getCurrentDateTimeF().toString());	//调整等级后等级评级时间
						ws.setLastUpdateUserNew(instanceids[5]);	//调整等级后等级评级人  
						break;
						
					}
					String evaluateDateNew=(new DateUtils().getCurrentDateTimeF().toString());
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
									",'3')";
					execteSQL(vo);
					SQL="update ACRM_F_CI_GRADE set cust_grade= '"+instanceids[3]+"',LAST_UPDATE_TM=SYSDATE,EFFECTIVE_DATE=SYSDATE,EVALUATE_DATE=SYSDATE,LAST_UPDATE_USER='"+instanceids[5]+"'  where cust_id='"+instanceids[1]+"' and cust_grade_type='01'";
					execteSQL(vo);//通过是审批修改客户等级表
				}else{
					
				}
				
			
					SQL="update  ACRM_A_FACT_FXQ_CUSTOMER SET "+ 
							" stat_fp='2' "+
							" ,MGR_ID_FP='"+instanceids[5]+"' "+
							" ,TIME_FP =SYSDATE "+
							" ,FLAG_FP='2' "+
							" ,CUST_GRADE_FP='"+instanceids[3]+"' "+
							" WHERE  CUST_ID='"+instanceids[1]+"' ";
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
			/*
			 * 
serialVersionUID===-7327196638774938338
SIGN_SUCCESS===0
SIGN_FAIL===1
SIGN_SUCCESSWITHEXCEPTION===2
SIGN_PARTSUCCESS===3
SIGN_OTHER===4
SIGN_SUCCESS_END===5
InstanceID===ANTMONEY3_210000079047_M_H_2_502N0321_1
bizseqno===bizseqno_ANTMONEY3_210000079047_M_H_2_502N0321_1
WFID===135
WFName===反洗钱复评
WFSign===AntMoneyReview
IsWFSet===2
WFMainForm===xypj_apply
WFAdmin===admin
AppID===xypj
AppName===信用评级
jobName===反洗钱复评_AKIYO KANEKO
nodeID===135_a4
nodeName===OP主管
nodeStatus===0
SPStatus===0
allProcessor===502N0321;
currentNodeUser===600N0686;
currentNodeUsers===600N0686;
author===502N0321
WFStartTime===2016-09-01 18:33:27
fieldID===135_128
fieldContent===WFBEGIN;135_a3;135_a4;135_e9
CurrentUserID===600N0686
commentVO===com.ecc.echain.workflow.model.CommentVO@487c8c05
nextNodeID===135_e9
sign===0
sysid===echaindefault
orgid===502
paramMap==={role=R303}
UserObject===com.ecc.echain.workflow.model.VO_wf_node_property@4d7c58fe
fromRow===0
toRow===0
totalRow===0
formdata===role=R303
exi===0
exf===0.0
exb===false
entrustModel===0
callBackModel===0
connection===oracle.jdbc.driver.T4CConnection@1eb6b370

			 * */
			
			//nodeID===135_a4
			
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			if(!"".equals(instanceids[1])&instanceids[1]!=null){
				//修改复评状态
				String nodeID=vo.getNodeID();
				if(nodeID.equals("135_a4")||nodeID.equals("135_a16")){
					SQL="update  ACRM_A_FACT_FXQ_CUSTOMER SET "+ 
							" stat_fp='0' "+
							" ,FLAG_FP='0' "+
							" WHERE  CUST_ID='"+instanceids[1]+"' ";
				}else{
					SQL="update  ACRM_A_FACT_FXQ_CUSTOMER SET "+ 
							" stat_fp='2' "+
							" ,MGR_ID_FP='"+instanceids[5]+"' "+
							" ,TIME_FP =SYSDATE "+
							" ,FLAG_FP='2' "+
							" ,CUST_GRADE_FP='"+instanceids[3]+"' "+
							" WHERE  CUST_ID='"+instanceids[1]+"' ";
				}
				
				execteSQL(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}
	//撤办 20160310没启用 内容还没更改需要重写 
		public void endN2(EVO vo){		
			//不做任何操作
			Object a=vo;
			try {
				String instanceid = vo.getInstanceID();
				String instanceids[] = instanceid.split("_");
				
				if(!"".equals(instanceids[1])&instanceids[1]!=null){
				
					//修改复评状态
					
					SQL="update  ACRM_A_FACT_FXQ_CUSTOMER SET "+ 
							" stat_fp='0' "+
							" ,MGR_ID_FP='"+instanceids[5]+"' "+
							" ,TIME_FP =SYSDATE "+
							" ,FLAG_FP='0' "+
							" ,CUST_GRADE_FP='"+instanceids[3]+"' "+
							" WHERE  CUST_ID='"+instanceids[1]+"' ";
					execteSQL(vo);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("执行SQL出错");
			}
		}
	



}
