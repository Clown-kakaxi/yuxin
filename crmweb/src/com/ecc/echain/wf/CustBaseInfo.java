package com.ecc.echain.wf;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
import com.yuchengtech.trans.bo.ecif.RequestBody4UpdatePerCust;
import com.yuchengtech.trans.client.TransClient;

public class CustBaseInfo extends EChainCallbackCommon{
	
	//同意处理
	public void endY(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL = " SELECT * FROM ACRM_F_CI_CUSTOMER_REVIEW V WHERE V.ID='"+instanceids[1]+"'";//查询客户经理信息审批申请表
			Result result=querySQL(vo);
			String enName=null;
			String syncCustId = null;
			for (SortedMap item : result.getRows()){
				enName = item.get("EN_NAME").toString();    
				syncCustId = item.get("CUST_ID").toString();
			}
			com.yuchengtech.trans.bo.RequestHeader header = new com.yuchengtech.trans.bo.RequestHeader();
			header.setReqSysCd("CRM");
			header.setReqSeqNo("20140907161456");//交易流水号
			header.setReqDt("20140907");//请求日期
			header.setReqTm("161456");//请求时间
			header.setDestSysCd("ECIF");
			header.setChnlNo("82");//业务渠道
			header.setBrchNo("503");//机构号
			header.setBizLine("209");//业务条线
			header.setTrmNo("TRM10010");//终端号,可以为空
			header.setTrmIP("10.18.250.71");//客户端IP
			header.setTlrNo("6101");//用户编号
			String txCode = "updateOrgCustInfo";// char(4) 交易代码
			
			RequestBody4UpdatePerCust requestBody = new RequestBody4UpdatePerCust();
			AcrmFCiCustomer syncCustomer = new AcrmFCiCustomer();
			syncCustomer.setBlankFlag("1");
			syncCustomer.setCustId(syncCustId);
			syncCustomer.setCreateDate((new Date()));
			syncCustomer.setCurrentAum(BigDecimal.valueOf(12423523.234));
			requestBody.setCustomer(syncCustomer);
			requestBody.setTxCode(txCode);
			requestBody.setCustNo(syncCustId);
//			TransClient.init();
			// String resultXml = TransClient.process(header, requestBody);
			TransClient.process(header, requestBody);
			
			
			SQL =  "update ACRM_F_CI_CUSTOMER t set  t.EN_NAME= '"+enName+"'  where t.cust_id='"+instanceids[2]+"'";
			execteSQL(vo);//审批通过，修改客户经理信息表
			SQL =  "delete from  ACRM_F_CI_CUSTOMER_REVIEW t   where id='"+instanceids[1]+"'";//删除客户经理信息审批申请表数据
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//拒绝处理
	public void endN(EVO vo){
		
	}
	
	

}
