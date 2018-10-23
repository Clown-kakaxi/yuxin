package com.ecc.echain.wf;

import java.sql.SQLException;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
/***
 * 个金潜在客户删除复核
 * @author mamusa
 * 2016-01-15
 */
public class LatentCusDel extends EChainCallbackCommon {
	//通过处理
			public void endY(EVO vo){
				
				try {
					String instanceid = vo.getInstanceID();
					String instanceids[] = instanceid.split("_");
					SQL =" update ACRM_F_CI_POT_CUS_COM set delete_cust_state='2',state='1',OPERATE_TIME =systimestamp where CUS_ID in ('"+instanceids[1]+"')";
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
					SQL =" update ACRM_F_CI_POT_CUS_COM set state='0',DELETE_CUST_STATE='2',OPERATE_TIME =systimestamp where CUS_ID in ('"+instanceids[1]+"')";
					execteSQL(vo);
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("执行SQL出错");
				}

			}
			
			/**
			 * 撤办
			 * @param vo
			 */
             public void endCB(EVO vo){
				
				try {
					String instanceid = vo.getInstanceID();
					String instanceids[] = instanceid.split("_");
					SQL =" update ACRM_F_CI_POT_CUS_COM set delete_cust_state='2',state='0',OPERATE_TIME =systimestamp where CUS_ID in ('"+instanceids[1]+"')";
					execteSQL(vo);
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("执行SQL出错");
				}
             }
			
}
