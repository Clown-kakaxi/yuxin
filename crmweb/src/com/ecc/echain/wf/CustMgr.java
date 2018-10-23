package com.ecc.echain.wf;

import java.sql.SQLException;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;

public class CustMgr extends EChainCallbackCommon{
	//通过处理
	public void endY(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split(",");
			SQL = " SELECT * FROM OCRM_F_CM_CUST_MGR_INFO_REVIEW V WHERE V.ID='"+instanceids[1]+"'";//查询客户经理信息审批申请表
			Result result=querySQL(vo);
			String CERTIFICATE=null;
			for (SortedMap item : result.getRows()){
				CERTIFICATE = item.get("CERTIFICATE").toString();     
			}
			SQL =  "update ocrm_f_cm_cust_mgr_info t set  t.certificate= '"+CERTIFICATE+"'  where t.cust_manager_id='"+instanceids[2]+"'";
			execteSQL(vo);//审批通过，修改客户经理信息表
			SQL =  "delete from  ocrm_f_cm_cust_mgr_info_review t   where id='"+instanceids[1]+"'";//删除客户经理信息审批申请表数据
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}
	
	public void endN(EVO vo){
		try{
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split(",");
		SQL = " SELECT * FROM OCRM_F_CM_CUST_MGR_INFO_REVIEW V WHERE V.ID='"+instanceids[1]+"'";//查询客户经理信息审批申请表
		Result result=querySQL(vo);
		String CERTIFICATE=null;
		for (SortedMap item : result.getRows()){
			CERTIFICATE = item.get("CERTIFICATE").toString();
		}
		SQL =  "delete from  ocrm_f_cm_cust_mgr_info_review t   where t.cust_manager_id='"+instanceids[2]+"'";//删除客户经理信息审批申请表数据
		execteSQL(vo);
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
		
	}

}
