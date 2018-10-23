package com.ecc.echain.wf;

import java.sql.SQLException;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
/***
 * 联盟商流程处理
 * @author hujun
 * 2014-06-24
 */
public class AlianceProg extends EChainCallbackCommon{
	//通过处理
	public void endY(EVO vo){
		
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL = " update OCRM_F_CI_ALIANCE_PROGRAM set STATE = '03' where id = "+instanceids[1]+" ";
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
			SQL = " update OCRM_F_CI_ALIANCE_PROGRAM set STATE = '04' where id = "+instanceids[1]+" ";
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}

	}
	



}
