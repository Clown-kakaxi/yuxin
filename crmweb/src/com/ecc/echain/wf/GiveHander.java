package com.ecc.echain.wf;

import java.sql.SQLException;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
/***
 * 礼品领取流程处理
 * @author luyueyue
 *
 */
public class GiveHander extends EChainCallbackCommon{
	//通过处理
	public void endY(EVO vo){
		
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			String sql1 = " update OCRM_F_SE_SCORE set " +
							"SCORE_TOTAL = SCORE_TOTAL -(select NEED_SCORE from OCRM_F_SE_GOODS_HIS where id='"+ instanceids[1] +"' ) " +
							",SCORE_TODEL = SCORE_TODEL -(select NEED_SCORE from OCRM_F_SE_GOODS_HIS where id='"+ instanceids[1] +"' )," +
							"SCORE_USED = decode(SCORE_USED,null,0,'',0,SCORE_USED)+(select NEED_SCORE from OCRM_F_SE_GOODS_HIS where id='"+ instanceids[1] +"' )," +
							"CUST_CUM_COST=decode(CUST_CUM_COST,null,0,'',0,CUST_CUM_COST)+(select NEED_SCORE from OCRM_F_SE_GOODS_HIS where id='"+ instanceids[1] +"' )," +
							"CUST_COST_SUM =decode(CUST_COST_SUM,null,0,'',0,CUST_COST_SUM)+(select NEED_SCORE from OCRM_F_SE_GOODS_HIS where id='"+ instanceids[1] +"' ) " +
							"where CUST_ID = (select CUST_ID from OCRM_F_SE_GOODS_HIS where id='"+ instanceids[1] +"' ) ";
			String sql3 = "update OCRM_F_SE_GOODS set GOODS_NUMBER = GOODS_NUMBER - (select GIVE_NUMBER from OCRM_F_SE_GOODS_HIS where id='"+ instanceids[1] +"' ) where " +
						  " ID = (select GOODS_ID from OCRM_F_SE_GOODS_HIS where id='"+ instanceids[1] +"' ) ";
			String sql2 = " update OCRM_F_SE_GOODS_HIS set GIVE_STATE='4' where id='"+ instanceids[1] +"'";
			SQLS.add(sql1);
			SQLS.add(sql3);
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
			String sql1 = " update OCRM_F_SE_SCORE set " +
							"SCORE_TODEL = SCORE_TODEL -(select NEED_SCORE from OCRM_F_SE_GOODS_HIS where id='"+ instanceids[1] +"' ) " +
									"where CUST_ID = (select CUST_ID from OCRM_F_SE_GOODS_HIS where id='"+ instanceids[1] +"' ) ";
			String sql2 = " update OCRM_F_SE_GOODS_HIS set GIVE_STATE='5' where id='"+ instanceids[1] +"'";
			SQLS.add(sql1);
			SQLS.add(sql2);
			executeBatch(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}

	}
	



public void toKuGuan(EVO vo){
	try {
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split("_");
		SQL = " update OCRM_F_SE_GOODS_HIS set GIVE_STATE ='2' where id = "+instanceids[1]+" ";
		
		execteSQL(vo);
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("执行SQL出错");
	}

	}

public void toZhuGuan(EVO vo){
	try {
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split("_");
		SQL = " update OCRM_F_SE_GOODS_HIS set GIVE_STATE ='3' where id = "+instanceids[1]+" ";
		
		execteSQL(vo);
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("执行SQL出错");
	}

	}	


}
