package com.ecc.echain.wf;

import java.sql.SQLException;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;

public class OpenAccount extends EChainCallbackCommon{
	//通过处理
	public void endY(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			if("KTZH".equals(instanceids[0])){//开通账户信息
				SQL = " SELECT * FROM ACRM_F_CI_ACCOUNT_INFO_TEMP V WHERE V.CUST_ID='"+instanceids[1]+"'";
				Result result=querySQL(vo);
				for (SortedMap item : result.getRows()){
					SQL = " SELECT * FROM ACRM_F_CI_ACCOUNT_INFO V WHERE V.CUST_ID='"+instanceids[1]+"'";
					Result rst=querySQL(vo);
					if(rst.getRows()!=null){
						SQL =  "update ACRM_F_CI_ACCOUNT_INFO t " +
						" set  t.ACCOUNT_CONTENTS= '"+item.get("ACCOUNT_CONTENTS").toString()+"', " +
						"      t.IS_DOMESTIC_CUST = '"+item.get("IS_DOMESTIC_CUST").toString()+"', " +
						"      t.LAST_UPDATE_TM = to_date('"+item.get("LAST_UPDATE_TM").toString()+"','yyyy-mm-dd'), " +
						"      t.LAST_UPDATE_USER = '"+item.get("LAST_UPDATE_USER").toString()+"' " +
						" where t.cust_id='"+instanceids[1]+"'";
						execteSQL(vo);
					}else{
						SQL =  "insert into ACRM_F_CI_ACCOUNT_INFO o " +
							   "(select * from ACRM_F_CI_ACCOUNT_INFO_TEMP p where p.cust_id = '"+instanceids[1]+"') " ;
						execteSQL(vo);
					}
				}
				SQL =  "update ACRM_F_CI_ACCOUNT_INFO t set  t.state='2' where t.cust_id='"+instanceids[1]+"'";
				execteSQL(vo);
			}
			if("YHFW".equals(instanceids[0])){//银行服务信息
				SQL = " DELETE  FROM ACRM_F_CI_BANK_SERVICE V WHERE V.CUST_ID='"+instanceids[1]+"'";
				execteSQL(vo);
				
				SQL =  "insert into ACRM_F_CI_BANK_SERVICE o " +
				   "(select * from ACRM_F_CI_BANK_SERVICE_TEMP p where p.cust_id = '"+instanceids[1]+"') " ;
				execteSQL(vo);
					
				SQL =  "update ACRM_F_CI_BANK_SERVICE t set  t.state='2' where t.cust_id='"+instanceids[1]+"'";
				execteSQL(vo);
			}
			if("CPYY".equals(instanceids[0])){//客户产品意愿信息
				SQL = " SELECT * FROM ACRM_F_CI_PRODUCT_WILL_TEMP V WHERE V.CUST_ID='"+instanceids[1]+"'";
				Result result=querySQL(vo);
				for (SortedMap item : result.getRows()){
					SQL = " SELECT * FROM ACRM_F_CI_PRODUCT_WILL V WHERE V.CUST_ID='"+instanceids[1]+"'";
					Result rst=querySQL(vo);
					if(rst.getRows()!=null){
						SQL =  "update ACRM_F_CI_PRODUCT_WILL t " +
						" set  t.FINANCIAL_PRODUCTS= '"+item.get("FINANCIAL_PRODUCTS").toString()+"', " +
						"      t.COLLATERAL = '"+item.get("COLLATERAL").toString()+"', " +
						"      t.LOAN_TYPE = '"+item.get("LOAN_TYPE").toString()+"', " +
						"      t.PRODUCT_TYPE = '"+item.get("PRODUCT_TYPE").toString()+"', " +
						"      t.LAST_UPDATE_TM = to_date('"+item.get("LAST_UPDATE_TM").toString()+"','yyyy-mm-dd'), " +
						"      t.LAST_UPDATE_USER = '"+item.get("LAST_UPDATE_USER").toString()+"' " +
						" where t.cust_id='"+instanceids[1]+"'";
						execteSQL(vo);
					}else{
						SQL =  "insert into ACRM_F_CI_PRODUCT_WILL o " +
							   "(select * from ACRM_F_CI_PRODUCT_WILL_TEMP p where p.cust_id = '"+instanceids[1]+"') " ;
						execteSQL(vo);
					}
				}
				SQL =  "update ACRM_F_CI_PRODUCT_WILL t set  t.state='2' where t.cust_id='"+instanceids[1]+"'";
				execteSQL(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}
	
	public void endN(EVO vo){
		try{
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			if("KTZH".equals(instanceids[0])){//开通账户信息
				SQL =  "update ACRM_F_CI_ACCOUNT_INFO t set  t.state='0' where t.cust_id='"+instanceids[1]+"'";
				execteSQL(vo);
			}
			if("YHFW".equals(instanceids[0])){//银行服务信息
				SQL =  "update ACRM_F_CI_BANK_SERVICE t set  t.state='0' where t.cust_id='"+instanceids[1]+"'";
				execteSQL(vo);
			}
			if("CPYY".equals(instanceids[0])){//客户产品意愿信息
				SQL =  "update ACRM_F_CI_PRODUCT_WILL t set  t.state='0' where t.cust_id='"+instanceids[1]+"'";
				execteSQL(vo);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
		
	}

}
