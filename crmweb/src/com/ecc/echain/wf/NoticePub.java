package com.ecc.echain.wf;

import java.sql.SQLException;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
import com.yuchengtech.crm.exception.BizException;
/***
 *公告发布复核结果
 * @author agile
 */
public class NoticePub extends EChainCallbackCommon{
	/**
	 * 审批通过
	 * @param vo
	 */
	public void endY(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			
//			SQL = "SELECT * FROM ocrm_f_wp_notice_temp T WHERE T.notice_id = '"+instanceids[1]+"'";//公告ID
//			Result result = querySQL(vo);
//			for (SortedMap item : result.getRows()){
//				String isTop = item.get("IS_TOP").toString();
				if("1".equals(instanceids[2])){//未发布 审核通过
					SQL =  "update ocrm_f_wp_notice t  set " +
						   " T.PUBLISHED = 'pub001',t.status = '3'," +
						   " t.publish_Time = sysdate" +
						   " where T.NOTICE_ID = '"+instanceids[1]+"'";
					execteSQL(vo);//审批通过
//					//公告内容
//					SQL = " update OCRM_SYS_RICHEDIT_INFO info set info.content = "+
//						  " (select t.content from OCRM_SYS_RICHEDIT_INFO_temp t where t.rel_id = '"+instanceids[1]+"')"+
//						  " where info.rel_id = '"+instanceids[1]+"'";
//					execteSQL(vo);
					
//					//附件信息
//					updateApproval(instanceids[1],vo);
					
				}
				if("2".equals(instanceids[2])){//修改审核通过  关联 公告内容临时表
					SQL =  "update ocrm_f_wp_notice t  set " +
					   " T.PUBLISHED = 'pub001', t.status = '3', " +
					   " t.publish_Time = sysdate" +
					   " where T.NOTICE_ID = '"+instanceids[1]+"'";
					execteSQL(vo);//审批通过
					
//					//公告内容
//					SQL = " update OCRM_SYS_RICHEDIT_INFO info set info.content = "+
//						  " (select t.content from OCRM_SYS_RICHEDIT_INFO_temp t where t.rel_id = '"+instanceids[1]+"')"+
//						  " where info.rel_id = '"+instanceids[1]+"'";
//					execteSQL(vo);
//					
//					//附件信息
//					updateApproval(instanceids[1],vo);
					
				}
			
//			}
		} catch (SQLException e) {
			System.out.println("执行SQL出错");
			throw new BizException(1, 0, "1002", e.getMessage());
		}
	}
	/**
	 * 审批拒绝
	 * @param vo
	 */
	public void endN(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			
//			SQL = "SELECT * FROM ocrm_f_wp_notice_temp T WHERE T.notice_id = '"+instanceids[1]+"'";//公告ID
//			Result result = querySQL(vo);
//			for (SortedMap item : result.getRows()){
				if("1".equals(instanceids[2])){//新增审核拒绝
					SQL =  "update ocrm_f_wp_notice t  set " +
						   " T.PUBLISHED = 'pub002',t.status = '3'" +
						   " where T.NOTICE_ID = '"+instanceids[1]+"'";
				}
				if("2".equals(instanceids[2])){//修改审核拒绝  关联 公告内容临时表
					SQL =  "update ocrm_f_wp_notice t  set " +
					   " T.PUBLISHED = 'pub002',t.status = '3' " +
					   " where T.NOTICE_ID = '"+instanceids[1]+"'";
				}
				execteSQL(vo);//审批拒绝
//			}
		} catch (SQLException e) {
			System.out.println("执行SQL出错");
			throw new BizException(0, 1, "1002", e.getMessage());
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
			
//			SQL = "SELECT * FROM ocrm_f_wp_notice_temp T WHERE T.notice_id = '"+instanceids[1]+"'";//公告ID
//			Result result = querySQL(vo);
//			for (SortedMap item : result.getRows()){
				if("1".equals(instanceids[2])){//新增审核拒绝
					SQL =  "update ocrm_f_wp_notice t  set " +
						   " T.PUBLISHED = 'pub002',t.status = '1'" +
						   " where T.NOTICE_ID = '"+instanceids[1]+"'";
				}
				if("2".equals(instanceids[2])){//修改审核拒绝  关联 公告内容临时表
					SQL =  "update ocrm_f_wp_notice t  set " +
					   " T.PUBLISHED = 'pub002',t.status = '1 " +
					   " where T.NOTICE_ID = '"+instanceids[1]+"'";
				}
				execteSQL(vo);//审批拒绝
//			}
		} catch (SQLException e) {
			System.out.println("执行SQL出错");
			throw new BizException(0, 1, "1002", e.getMessage());
	   }
    }
	public String getString(Object object){
		return object != null ? object.toString():"";
	}
//	/**
//	 * 更新附件信息
//	 * @param id
//	 * @throws SQLException 
//	 */
//	public void updateApproval(String id,EVO vo) throws SQLException{
//		SQL = "SELECT * FROM OCRM_F_WP_ANNEXE_temp T WHERE T.RELATION_INFO = '"+id+"'";
//		Result result = querySQL(vo);
//		for (SortedMap item : result.getRows()){
//			String createTime = getString(item.get("CREATE_TIME"));
//			String annexeSize = getString(item.get("ANNEXE_SIZE")) ; 
//			String annexeName = getString(item.get("ANNEXE_NAME"));   
//			String physicalAddress = getString(item.get("PHYSICAL_ADDRESS"));
//			String relatioinMod = getString(item.get("RELATIOIN_MOD"));
//			String nextId = "";
//			SQL = "SELECT ID_SEQUENCE.NEXTVAL as NEXTVAL  FROM DUAL ";
//			result = querySQL(vo);
//			for (SortedMap i : result.getRows()){
//				nextId = i.get("NEXTVAL").toString();
//			}
//			SQL = "INSERT INTO OCRM_F_WP_ANNEXE (ANNEXE_ID,CREATE_TIME,ANNEXE_SIZE,RELATION_INFO,LOAD_COUNT,ANNEXE_NAME,PHYSICAL_ADDRESS,RELATIOIN_MOD) " +
//				  " values('"+nextId+"',TO_DATE('"+createTime+"','YYYY-MM-DD')," +
//				  " '"+annexeSize+"','"+id+"','"+0+"','"+annexeName+"','"+physicalAddress+"','"+relatioinMod+"')";
//			execteSQL(vo);
//		}
//	}
}
