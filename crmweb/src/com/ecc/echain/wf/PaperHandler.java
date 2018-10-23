package com.ecc.echain.wf;

import java.sql.SQLException;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
/***
 * 问卷复核流程处理
 * @author luyueyue
 *
 */
public class PaperHandler extends EChainCallbackCommon{
	//通过处理
	public void endY(EVO vo){
		
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL = " update OCRM_F_SM_PAPERS set AVAILABLE = '3' where id = "+instanceids[1]+" ";
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
		

	}



public void toShenHe(EVO vo){
	try {
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split("_");
		SQL = " update OCRM_F_SM_PAPERS set AVAILABLE ='2' where id = "+instanceids[1]+" ";
		
		execteSQL(vo);
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("执行SQL出错");
	}

	}



}
