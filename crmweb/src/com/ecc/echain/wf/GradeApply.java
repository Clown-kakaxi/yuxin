package com.ecc.echain.wf;
/**
 * 客户评级工作流审批调用类
 * 2013-12-27
 */
import java.sql.SQLException;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;

public class GradeApply extends EChainCallbackCommon{

	public void agree(EVO vo){
		
		try {
			String instanceid = vo.getInstanceID();
			String id = instanceid.split("_")[1];
			SQL = " update OCRM_F_CI_GRADE_APPLY set STATUS = '审批通过' where id = '"+id+"' ";
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
		

	}
	
public void deny(EVO vo){
	try {
		String instanceid = vo.getInstanceID();
		String id = instanceid.split("_")[1];
		String nodeString = vo.getNodeID();
		if (nodeString.equals("12_a3")) { //当前节点是支行行长，打回
			SQL = " update OCRM_F_CI_GRADE_APPLY set STATUS = '支行行长退回审批' where id = '"+id+"' ";
			execteSQL(vo);
		}else if (nodeString.equals("12_a4")) { //当前节点总行，打回
			SQL = " update OCRM_F_CI_GRADE_APPLY set STATUS = '总行退回审批' where id = '"+id+"' ";
			execteSQL(vo);
		}
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("执行SQL出错");
	}
	}	
}
