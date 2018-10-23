package com.ecc.echain.wf;

import org.apache.log4j.Logger;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;

/**
 * @describtion: 风险参数调整审批动态调用类
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年6月25日 下午2:21:49
 */
public class RiskParam extends EChainCallbackCommon{
	/**
	 * log4g日志输出
	 */
	private static Logger log = Logger.getLogger(RiskParam.class);
	
	/**
	 * 风险参数设置审批同意,结束处理
	 * @param vo
	 */
	public void agree(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String apprid = instanceid.split("_")[1];
			//更新申请表状态 //APPR_STATUS：01审批中，02审批通过，03审批拒绝
			String sql1 = "UPDATE OCRM_F_FIN_RISK_PARAM_APPR T SET T.APPR_STATUS = '02',T.APPROVE_DATE = SYSDATE,T.APPROVE_USER = '"+vo.getCurrentUserID()+"' WHERE ID = '"+apprid+"'";
			//清除原有参数设置
			String sql2 = "DELETE FROM OCRM_F_FIN_RISK_PARAM";
			//插入新的参数设置	//IS_BEFORE：01调整前，02调整后
			String sql3 = "INSERT INTO OCRM_F_FIN_RISK_PARAM(ID,RISK_CHARACT,LOWER_VALUE,UPPER_VALUE,REMARK) "
					+ "SELECT ID,RISK_CHARACT,LOWER_VALUE,UPPER_VALUE,REMARK FROM OCRM_F_FIN_RISK_PARAM_HIS WHERE APPR_ID = '"+apprid+"' AND IS_BEFORE = '02' ORDER BY LOWER_VALUE";
			//注：对添加SQL的执行顺序有要求
			SQLS.add(sql1);
			SQLS.add(sql2);
			SQLS.add(sql3);
			executeBatch(vo);
		} catch (Exception e) {
			log.error("执行SQL报错,请注意检查风险参数流程");
			e.printStackTrace();
		}
	}
	
	/**
	 * 风险参数设置审批拒绝,结束处理
	 * @param vo
	 */
	public void disAgree(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String apprid = instanceid.split("_")[1];
			//更新申请表状态 //APPR_STATUS：01审批中，02审批通过，03审批拒绝
			SQL = "UPDATE OCRM_F_FIN_RISK_PARAM_APPR T SET T.APPR_STATUS = '03' WHERE ID = '"+apprid+"'";
			execteSQL(vo);
		} catch (Exception e) {
			log.error("执行SQL报错,请注意检查风险参数流程");
			e.printStackTrace();
		}
	}
}
