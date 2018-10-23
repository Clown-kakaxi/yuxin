package com.ecc.echain.wf;

import java.sql.SQLException;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
/***
 * 积分流程处理
 * @author luyueyue
 *
 */
public class CustScore extends EChainCallbackCommon{
	//通过处理
	public void endY(EVO vo){
		
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			String sql1 = " update OCRM_F_SE_ADD set STATE = '2' where id = "+instanceids[1]+" ";
			String sql2 = " update OCRM_F_SE_SCORE set SCORE_TOTAL=SCORE_TOTAL+SCORE_ADD,COUNT_NUM=COUNT_NUM+SCORE_ADD" +
					"CUST_CUM_COUNT=CUST_CUM_COUNT+SCORE_ADD,ADD_STATE='6',add_id='' where add_id='"+ instanceids[1] +"'";
			SQLS.add(sql1);
			SQLS.add(sql2);
			
			executeBatch(vo);
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
			String sql1 = " update OCRM_F_SE_ADD set STATE = '3' where id = "+instanceids[1]+" ";
			String sql2 = " update OCRM_F_SE_SCORE set ADD_STATE='7',add_id='' where add_id='"+ instanceids[1] +"'";
			SQLS.add(sql1);
			SQLS.add(sql2);
			
			executeBatch(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
		

	}
	



public void toZhuguan(EVO vo){
	try {
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split("_");
		SQL = " update OCRM_F_SE_SCORE set ADD_STATE='2' where add_id='"+ instanceids[1] +"'";
		
		execteSQL(vo);
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("执行SQL出错");
	}

	}

public void toZhiHang(EVO vo){
	try {
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split("_");
		SQL = " update OCRM_F_SE_SCORE set ADD_STATE='3' where add_id='"+ instanceids[1] +"'";
		
		execteSQL(vo);
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("执行SQL出错");
	}

	}	

public void toQuYu (EVO vo){
	try {
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split("_");
		SQL = " update OCRM_F_SE_SCORE set ADD_STATE='4' where add_id='"+ instanceids[1] +"'";
		
		execteSQL(vo);
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("执行SQL出错");
	}
	

}	

public void toZongHang(EVO vo){
	try {
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split("_");
		SQL = " update OCRM_F_SE_SCORE set ADD_STATE='5' where add_id='"+ instanceids[1] +"'";
		
		execteSQL(vo);
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("执行SQL出错");
	}


}

}
