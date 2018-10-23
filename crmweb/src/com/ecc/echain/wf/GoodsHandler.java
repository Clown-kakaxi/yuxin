package com.ecc.echain.wf;

import java.sql.SQLException;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
/***
 * 礼品流程处理
 * @author luyueyue
 *
 */
public class GoodsHandler extends EChainCallbackCommon{
	//通过处理
	public void endY(EVO vo){
		
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL = " update OCRM_F_SE_GOODS set GOODS_STATE = '6' where id = "+instanceids[1]+" ";
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
			SQL = " update OCRM_F_SE_GOODS set GOODS_STATE = '7' where id = "+instanceids[1]+" ";
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
		SQL = " update OCRM_F_SE_GOODS set GOODS_STATE ='2' where id = "+instanceids[1]+" ";
		
		execteSQL(vo);
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("执行SQL出错");
	}

	}

public void toQuYu(EVO vo){
	try {
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split("_");
		SQL = " update OCRM_F_SE_GOODS set GOODS_STATE ='3' where id = "+instanceids[1]+" ";
		
		execteSQL(vo);
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("执行SQL出错");
	}

	}	

public void toZongHangZY (EVO vo){
	try {
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split("_");
		SQL = " update OCRM_F_SE_GOODS set GOODS_STATE ='4' where id = "+instanceids[1]+" ";
		
		execteSQL(vo);
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("执行SQL出错");
	}
	

}	

public void toZongHangZG (EVO vo){
	try {
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split("_");
		SQL = " update OCRM_F_SE_GOODS set GOODS_STATE ='5' where id = "+instanceids[1]+" ";
		
		execteSQL(vo);
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("执行SQL出错");
	}
	

}	



}
