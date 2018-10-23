package com.ecc.echain.wf;

import java.sql.SQLException;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;

public class depositHand extends EChainCallbackCommon {

	public void endY(EVO vo) {
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			String sql1 = " update OCRM_F_MK_DEPOSIT_DRAWING t set t.approve_state='3' where t.apply_no='"//审批同意
					+ instanceids[1] + "'";
			SQLS.add(sql1);
			super.executeBatch(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}

	// 拒绝处理
	public void endN(EVO vo) {
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			String sql1 =" update OCRM_F_MK_DEPOSIT_DRAWING t set t.approve_state='4' where t.apply_no='"+instanceids[1]+"'";//审批拒绝
			SQLS.add(sql1);
			super.executeBatch(vo);
		} catch (SQLException e) {
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
			SQL =  "UPDATE OCRM_F_MK_DEPOSIT_DRAWING T SET  T.APPROVE_STATE='1' WHERE T.APPLY_NO='"+instanceids[1]+"'";
			super.execteSQL(vo);
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
		
	}
}
