package com.ecc.echain.wf;

import java.sql.SQLException;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;

public class QsjVisit extends EChainCallbackCommon{
	//通过处理
	public void endY(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL =  "update Ocrm_f_Interview_Record t set  t.review_State= '3'  where t.task_number='"+instanceids[1]+"'";
			execteSQL(vo);//审批通过
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}
	/**
	 *拒绝
	 * @param vo
	 */
	public void endN(EVO vo){
		try{
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split("_");
		SQL =  "update Ocrm_f_Interview_Record t set  t.review_State= '4'  where t.task_number='"+instanceids[1]+"'";
		execteSQL(vo);
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}
	/**
	 * 撤办
	 * @param vo
	 */
	public void endCB(EVO vo){
		try{
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL =  "update Ocrm_f_Interview_Record t set  t.review_State= '1'  where t.task_number='"+instanceids[1]+"'";
			execteSQL(vo);
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
		
	}

}
