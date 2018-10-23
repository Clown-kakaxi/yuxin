package com.ecc.echain.wf;

import java.sql.SQLException;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;

public class MktTaskHand extends EChainCallbackCommon {

	public void endY(EVO vo) {

		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			String sql1 =" update OCRM_F_MM_TASK_TARGET t set t.ACHIEVE_VALUE_STATE='2' where t.TASK_ID IN"
					+ "( select substr(field1,"
              +" instr(field1, ',', 1, rownum) + 1,"
 
              +" instr(field1, ',', 1, rownum + 1) -"
 
              +" instr(field1, ',', 1, rownum) - 1) as field2"
 
  			  +" from (select ',' || get_parent_task_id('"+instanceids[1]+"') || ',' as field1 from dual)"
 
			  +" connect by instr(field1, ',', 2, rownum) > 0)";
			SQLS.add(sql1);

			executeBatch(vo);
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
			String sql1 =" update OCRM_F_MM_TASK_TARGET t set t.ACHIEVE_VALUE_STATE='3' where t.TASK_ID='"+instanceids[1]+"'";
			SQLS.add(sql1);
			executeBatch(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}

	}

}
