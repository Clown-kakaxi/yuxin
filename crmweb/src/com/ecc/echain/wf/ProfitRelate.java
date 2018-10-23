package com.ecc.echain.wf;

import java.sql.SQLException;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;

public class ProfitRelate extends EChainCallbackCommon {

	//同意
	public void endY(EVO vo) throws Exception{
		try{
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split("_");
		if(instanceids[4].equals("3")){
			 SQL = " DELETE FROM ACRM_A_CI_PROF_RELATION  R  WHERE R.ID ='"+instanceids[5]+"'";
		}else{
		   SQL = " UPDATE ACRM_A_CI_PROF_RELATION  R SET R.R_STATE='0' WHERE R.CREATE_TIMES ='"+instanceids[1]+"'";
		}
		execteSQL(vo);
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}
	
	//拒绝
	public void endN(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			if(instanceids[4].equals("3")){
				SQL = " UPDATE ACRM_A_CI_PROF_RELATION  R SET R.R_STATE='2'  WHERE R.ID ='"+instanceids[5]+"'";
			}else if(instanceids[4].equals("1")){
				SQL = " DELETE FROM ACRM_A_CI_PROF_RELATION R WHERE R.CREATE_TIMES ='"+instanceids[1]+"'";
			}else{
			    SQL = " UPDATE ACRM_A_CI_PROF_RELATION  R SET R.R_STATE='2' WHERE R.CREATE_TIMES ='"+instanceids[1]+"'";
			}
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}
	
	//撤销
		public void endCX(EVO vo){
			try {
				String instanceid = vo.getInstanceID();
				String instanceids[] = instanceid.split("_");
				if(instanceids[4].equals("1")){
					SQL = " DELETE FROM ACRM_A_CI_PROF_RELATION R WHERE R.CREATE_TIMES ='"+instanceids[1]+"'";
				}else{
				    SQL = " UPDATE ACRM_A_CI_PROF_RELATION  R SET R.R_STATE='3' WHERE R.CREATE_TIMES ='"+instanceids[1]+"'";
				}
				execteSQL(vo);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("执行SQL出错");
			}
		}
}
