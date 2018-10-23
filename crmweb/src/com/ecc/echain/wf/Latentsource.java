package com.ecc.echain.wf;

import java.sql.SQLException;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
/***
 * 来源渠道维护复核
 * @author mamusa
 * 2016-01-28
 */
public class Latentsource extends EChainCallbackCommon {
	//通过处理
	public void endY(EVO vo){
		
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL = " update OCRM_F_MK_MKT_ACTIVITY set MKT_APP_STATE = '3' where MKT_ACTI_ID = '"+instanceids[1]+"' ";
			execteSQL(vo);
			
			SQL = "SELECT M.MKT_ACTI_CODE,M.MKT_ACTI_NAME,M.ACTI_REMARK FROM OCRM_F_MK_MKT_ACTIVITY M WHERE MKT_APP_STATE = '3' AND MKT_ACTI_ID='"+instanceids[1]+"'";
			Result result1=querySQL(vo);
			for (SortedMap item2 : result1.getRows()){
				String s2= item2.get("MKT_ACTI_CODE")!=null?item2.get("MKT_ACTI_CODE").toString():"";
				String s3= item2.get("MKT_ACTI_NAME")!=null?item2.get("MKT_ACTI_NAME").toString():"";
				String s4= item2.get("ACTI_REMARK")!=null?item2.get("ACTI_REMARK").toString():"";
				
				 SQL =" insert into ocrm_sys_lookup_item(f_id,f_code,F_VALUE,f_comment,f_lookup_id) values('"+instanceids[1]+"','"+s2+"','"+s3+"','"+s4+"','XD000353')";
				 execteSQL(vo);
				
				
			}
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
			SQL = " update OCRM_F_MK_MKT_ACTIVITY set MKT_APP_STATE = '4'  where MKT_ACTI_ID = '"+instanceids[1]+"' ";
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}

	}
	
	/**
	 * 撤销办理
	 * @param vo
	 */
		public void endCB(EVO vo){
			try {
				String instanceid = vo.getInstanceID();
				String instanceids[] = instanceid.split("_");
				SQL = " delete from OCRM_F_MK_MKT_ACTIVITY   where MKT_ACTI_ID = '"+instanceids[1]+"' ";
				execteSQL(vo);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("执行SQL出错");
			}

		}
}
