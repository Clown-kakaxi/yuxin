package com.ecc.echain.wf;

import java.sql.SQLException;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
/***
 * 营销活动流程处理
 * @author hujun
 * 2014-07-04
 */
public class MktActivity extends EChainCallbackCommon{
	//通过处理
	public void endY(EVO vo){
		
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL = " update OCRM_F_MK_MKT_ACTIVITY set MKT_APP_STATE = '3' where MKT_ACTI_ID = '"+instanceids[1]+"' ";
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
		

	}
	//拒绝处理
	public void endN(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL = " update OCRM_F_MK_MKT_ACTIVITY set MKT_APP_STATE = '4',MKT_ACTI_STAT='6' where MKT_ACTI_ID = '"+instanceids[1]+"' ";
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}

	}
	



}
