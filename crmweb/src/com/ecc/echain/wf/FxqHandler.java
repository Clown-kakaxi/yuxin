package com.ecc.echain.wf;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
import com.yuchengtech.bob.vo.AuthUser;
/***
 * 反洗钱复核流程处理
 * @author luyueyue
 *
 */
public class FxqHandler extends EChainCallbackCommon{
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	private static Logger log = Logger.getLogger(FxqHandler.class);
	/**
	 * 审核通过
	 * @param vo
	 */
	public void endY(EVO vo){
		
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			//1.修改申请状态
			String sql1 = " update OCRM_F_CI_ANTI_INDEX_APPLY set APPLY_STATE ='2' where id = "+instanceids[1]+" ";
			//2.修改指标表复核状态
			String sql2 = " update OCRM_F_CI_ANTI_MONEY_INDEX set VERIFLER_STAT='2',LAST_VERIFIER='"+auth.getUserId()+"'," +
					"LAST_VERIFY_TM = to_date(to_char(sysdate,'YYYY-MM-DD'),'YYYY-MM-DD') where INDEX_ID=(select INDEX_ID from OCRM_F_CI_ANTI_INDEX_APPLY where id = "+instanceids[1]+")";
			//3.删除原指标子项
			String sql3 = " delete from OCRM_F_CI_ANTI_MONEY_INDEX_VAR where  INDEX_ID=(select INDEX_ID from OCRM_F_CI_ANTI_INDEX_APPLY where id = "+instanceids[1]+")";
			//4.添加新的指标子项
			String sql4 = " insert into OCRM_F_CI_ANTI_MONEY_INDEX_VAR (ID,INDEX_ID,INDEX_CODE,INDEX_NAME,INDEX_VALUE,INDEX_SCORE,INDEX_RIGHT,HIGH_FLAG) SELECT " +
					"ID_SEQUENCE.NEXTVAL,A.INDEX_ID,I.INDEX_CODE,I.INDEX_NAME,I.INDEX_VALUE,I.INDEX_SCORE,I.INDEX_RIGHT,I.HIGH_FLAG " +
					"FROM OCRM_F_CI_ANTI_INDEX_APPLY A,OCRM_F_CI_ANTI_INDEX_INFO I WHERE A.ID=I.APPLY_ID AND A.id = "+instanceids[1]+" ";
			SQLS.add(sql1);
			SQLS.add(sql2);
			SQLS.add(sql3);
			SQLS.add(sql4);
			
			executeBatch(vo);
			
			//刷新客户界面录入的反洗钱指标得分，
			freshFact(vo);
			
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
			//1.修改申请状态
			String sql1 = " update OCRM_F_CI_ANTI_INDEX_APPLY set APPLY_STATE ='2' where id = "+instanceids[1]+" ";
			//2.修改指标表复核状态
			String sql2 = " update OCRM_F_CI_ANTI_MONEY_INDEX set VERIFLER_STAT='3',LAST_VERIFIER= null," +
					"LAST_VERIFY_TM = null where INDEX_ID=(select INDEX_ID from OCRM_F_CI_ANTI_INDEX_APPLY where id = "+instanceids[1]+")";
			SQLS.add(sql1);
			SQLS.add(sql2);
			executeBatch(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}
	/**
	 * 审核拒绝
	 * @param vo
	 */
	public void endN (EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			//1.修改申请状态
			String sql1 = " update OCRM_F_CI_ANTI_INDEX_APPLY set APPLY_STATE ='2' where id = "+instanceids[1]+" ";
			//2.修改指标表复核状态
			String sql2 = " update OCRM_F_CI_ANTI_MONEY_INDEX set VERIFLER_STAT='4',LAST_VERIFIER='"+auth.getUserId()+"'," +
					"LAST_VERIFY_TM = to_date(to_char(sysdate,'YYYY-MM-DD'),'YYYY-MM-DD') where INDEX_ID=(select INDEX_ID from OCRM_F_CI_ANTI_INDEX_APPLY where id = "+instanceids[1]+")";
			SQLS.add(sql1);
			SQLS.add(sql2);
			executeBatch(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}
	
	public Map<String, String> getMap(){
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("FXQ11006", "FXQ006");map.put("FXQ12006", "FXQ006");
		
		map.put("FXQ11007", "FXQ007");map.put("FXQ12007", "FXQ007");
		
		map.put("FXQ11008", "FXQ008");map.put("FXQ21012", "FXQ008");map.put("FXQ12008", "FXQ008");map.put("FXQ22012", "FXQ008");
		
		map.put("FXQ11009", "FXQ009");map.put("FXQ12009", "FXQ009");map.put("FXQ21010", "FXQ009");map.put("FXQ22010", "FXQ009");
		
		map.put("FXQ12010", "FXQ010");map.put("FXQ22013", "FXQ010");
		
		map.put("FXQ12011", "FXQ011");map.put("FXQ22014", "FXQ011");
		
		map.put("FXQ12012", "FXQ012");map.put("FXQ22015", "FXQ012");
		
		map.put("FXQ12013", "FXQ013");map.put("FXQ22016", "FXQ013");
		
		map.put("FXQ12014", "FXQ014");map.put("FXQ22017", "FXQ014");
		
		map.put("FXQ12015", "FXQ015");map.put("FXQ22018", "FXQ015");
		
		map.put("FXQ12016", "FXQ016");map.put("FXQ22019", "FXQ016");
		
		map.put("FXQ21006", "FXQ021");map.put("FXQ22006", "FXQ021");
		
		map.put("FXQ21007", "FXQ022");map.put("FXQ22007", "FXQ022");
		
		map.put("FXQ21008", "FXQ023");map.put("FXQ22008", "FXQ023");
		
		map.put("FXQ21009", "FXQ024");map.put("FXQ22009", "FXQ024");
		
		map.put("FXQ21011", "FXQ025");map.put("FXQ22011", "FXQ025");
		
		return map;
	}
	/**
	 * 刷新ACRM_A_ANTI_TARGET_FACT表(仅限页面录入客户反洗钱指标部分)
	 * vo
	 */
	public void freshFact(EVO vo){
		String instanceid = vo.getInstanceID();
		String instanceids[] = instanceid.split("_");
		try {
			SQL = "SELECT * FROM OCRM_F_CI_ANTI_INDEX_INFO T WHERE T.APPLY_ID = '"+instanceids[1]+"' and rownum <= 1";
			Result result = querySQL(vo);
			String indexCode="";
			for (SortedMap item : result.getRows()){
				indexCode = item.get("INDEX_CODE").toString();
			}
			Map<String, String> map = getMap();
			for(String key:map.keySet()){
				//刷新ACRM_A_ANTI_TARGET_FACT表
				if(key.equals(indexCode)){
					SQL = "SELECT * FROM ACRM_F_SYS_CUST_FXQ_INDEX T WHERE T."+map.get(key).trim()+" is not null " +
							" AND EXISTS (SELECT 1 FROM ACRM_A_ANTI_TARGET_FACT AT WHERE AT.CUST_ID = T.CUST_ID)";
					result = querySQL(vo);
					String indexValue="";
					String custId = "";
					String highFalg = "";
					String amount = "";
					StringBuffer sb = new StringBuffer();
					
					for (SortedMap item : result.getRows()){
						indexValue = item.get(map.get(key).trim()).toString();
						String[] str = indexValue.split(",");
						for(String s:str){
							sb.setLength(0);
							sb.append(",'").append(s.toString()).append("'");
						}
						custId = item.get("CUST_ID").toString();
						
						SQL = " select decode(sum((decode(v.high_flag,'1',1,0))),null,0,sum((decode(v.high_flag,'1',1,0)))) as high_flag," +
						" decode(sum(v.index_score*v.index_right*0.01),null,0,sum(v.index_score*v.index_right*0.01)) as amount"+
						" from OCRM_F_CI_ANTI_MONEY_INDEX_VAR v" +
						" where v.index_code ='"+indexCode+"' and v.index_value in ("+sb.toString().substring(1)+")";
						Result rst = querySQL(vo);
						for (SortedMap it : rst.getRows()){
							highFalg  = it.get("HIGH_FLAG").toString();
							amount = it.get("AMOUNT").toString();
						}
						
						SQL = "update ACRM_A_ANTI_TARGET_FACT t set t.index_value = '"+amount+"'," +
						" t.high_flag = '"+highFalg+"'" +
						" where t.cust_id = '"+custId+"'  and t.index_code = '"+indexCode+"' ";
						log.info("执行SQL:----->>>>>[" + SQL + "]");
						
						execteSQL(vo);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}
}
