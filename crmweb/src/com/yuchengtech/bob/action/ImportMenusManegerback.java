package com.yuchengtech.bob.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.yuchengtech.bob.importimpl.ImportInterface;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;
/**
 * 客户经理-名单下发
 * @author Administrator
 *
 */
public class ImportMenusManegerback implements ImportInterface{
	
	private static Logger log = Logger.getLogger(ImportMenusManegerback.class);
	
	@Override
	public void excute(Connection conn, String PKhead, AuthUser aUser)
			throws Exception {
		log.info("updateSQL:【ImportMenusManegerback has been evoke!】");
		String userId = aUser.getUserId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = sdf.format(new Date());
		
		Statement stm = null;
		try {
			//conn.setAutoCommit(false);
			stm = conn.createStatement();
			log.info("清除历史数据。。。。");
			String deleteSQL = "DELETE FROM OCRM_F_MK_MKT_MY_ACTI_TEMP T WHERE T.CREATE_USER = '"+userId+"'";
			stm.execute(deleteSQL);
			
			log.info("当导入数据库时，为null的字段替换为空。更新新导入的数据，导入人为当前登录用户。。。");
			StringBuilder replaceNullSql = new StringBuilder();
			replaceNullSql.append("update OCRM_F_MK_MKT_MY_ACTI_TEMP set ")
			.append(" CORE_NO = REPLACE(CORE_NO,'null',''),CUST_NAME = REPLACE(CUST_NAME,'null',''),")
			.append(" TEL_NO = REPLACE(TEL_NO,'null',''),MGR_NO = REPLACE(MGR_NO,'null',''),")
			.append(" CREATE_USER = '"+ userId +"',CREATE_DATE = TO_DATE('"+ nowDate +"','yyyy-MM-dd'),")
			.append(" UPDATE_USER = '"+ userId +"',UPDATE_DATE = TO_DATE('"+ nowDate +"','yyyy-MM-dd')")
			.append(" where ID like '"+ PKhead +"%'");
			stm.execute(replaceNullSql.toString());
			
			log.info("验证核心客户号是否存在");
			StringBuilder vaildCoreNoExistSql = new StringBuilder();
			vaildCoreNoExistSql.append("update OCRM_F_MK_MKT_MY_ACTI_TEMP T set IMP_STATUS = 0,IMP_MSG = IMP_MSG||'核心客户号不存在,'")
			.append(" where T.CREATE_USER = '"+userId+"' and T.CORE_NO IS NOT NULL AND ")
			.append(" NOT EXISTS (select T1.CORE_NO from ACRM_F_CI_CUSTOMER T1 WHERE T1.CORE_NO = T.CORE_NO)");
			log.info(vaildCoreNoExistSql);
			stm.execute(vaildCoreNoExistSql.toString());
			
			log.info("如果没有核心客户号，验证客户名称不能为空");
			StringBuilder vaildCustNameSql = new StringBuilder();
			vaildCustNameSql.append(" update  OCRM_F_MK_MKT_MY_ACTI_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'没有核心客户号时客户名称不能为空,'")
			.append(" where CREATE_USER = '"+userId+"' and CORE_NO is null and")
			.append(" CUST_NAME is null");
			stm.execute(vaildCustNameSql.toString());
			log.info(vaildCustNameSql);

			log.info("如果没有核心客户号，验证手机号不能为空");
			StringBuilder vaildTelNoSql = new StringBuilder();
			vaildTelNoSql.append("update OCRM_F_MK_MKT_MY_ACTI_TEMP set IMP_STATUS = 0,IMP_MSG = IMP_MSG||'没有核心客户号时手机号不能为空,'")
			.append(" where CREATE_USER = '"+ userId +"' and CORE_NO is null and ")
			.append(" TEL_NO is null ");
			log.info(vaildTelNoSql);
			stm.execute(vaildTelNoSql.toString());
			
			log.info("如果有核心客户号，验证核心客户号不能重复");
			StringBuilder vaildCoreMktNoRepeatSql = new StringBuilder();
			vaildCoreMktNoRepeatSql.append(" update OCRM_F_MK_MKT_MY_ACTI_TEMP T set IMP_STATUS = 0,IMP_MSG = IMP_MSG||'核心客户号不能重复,'")
			.append(" where T.CREATE_USER = '"+ userId +"' and T.CORE_NO is not null  and EXISTS ( ")
			.append(" SELECT T1.CORE_NO FROM OCRM_F_MK_MKT_MY_ACTI_TEMP T1")
			.append(" WHERE T.CORE_NO = T1.CORE_NO AND T1.CREATE_USER = '"+ userId +"' AND T1.CORE_NO IS NOT NULL  GROUP BY T1.CORE_NO HAVING COUNT(*) >1 )");
			log.info(vaildCoreMktNoRepeatSql);
			stm.execute(vaildCoreMktNoRepeatSql.toString());
			
			log.info("如果没有核心客户号，验证手机号不能重复");
			StringBuilder vaildTelNoRepeatSql = new StringBuilder();
			vaildTelNoRepeatSql.append(" update OCRM_F_MK_MKT_MY_ACTI_TEMP T set IMP_STATUS = 0,IMP_MSG = IMP_MSG||'手机号不能重复,'")
			.append(" where T.CREATE_USER = '"+ userId +"' and T.CORE_NO is null  and EXISTS (")
			.append(" SELECT T1.TEL_NO FROM OCRM_F_MK_MKT_MY_ACTI_TEMP T1")
			.append(" WHERE T.TEL_NO = T1.TEL_NO AND T1.CORE_NO  IS NULL AND T1.CREATE_USER = '"+ userId +"' GROUP BY T1.TEL_NO HAVING COUNT(*) >1 )");
			log.info(vaildTelNoRepeatSql);
			stm.execute(vaildTelNoRepeatSql.toString());
			
			log.info("验证归属客户经理是否存在");
			StringBuilder vaildMgrNoExistSql = new StringBuilder();
			vaildMgrNoExistSql.append("update OCRM_F_MK_MKT_MY_ACTI_TEMP T set IMP_STATUS = 0,IMP_MSG = IMP_MSG||'归属客户经理不存在,'")
			.append(" where T.CREATE_USER = '"+userId+"' and T.MGR_NO IS NOT NULL AND")
			.append(" NOT EXISTS ( select T1.ACCOUNT_NAME from ADMIN_AUTH_ACCOUNT T1 WHERE T1.ACCOUNT_NAME = T.MGR_NO)");
			log.info(vaildMgrNoExistSql);
			stm.execute(vaildMgrNoExistSql.toString());
			
			log.info("验证是否已经分配过归属客户经理，如果分配过，客户经理不能改变");
			StringBuilder vaildhasMgrNoSql = new StringBuilder();
			vaildhasMgrNoSql.append("UPDATE OCRM_F_MK_MKT_MY_ACTI_TEMP T1 SET IMP_STATUS = 0, IMP_MSG = IMP_MSG || '该客户已经分配过客户经理了,'")
			.append(" WHERE CREATE_USER = '"+userId+"' and  (EXISTS (SELECT 1 FROM OCRM_F_CI_BELONG_CUSTMGR T3 LEFT JOIN ACRM_F_CI_CUSTOMER T2 ON T2.CUST_ID = T3.CUST_ID ")
			.append(" WHERE T2.CORE_NO = T1.CORE_NO)  OR EXISTS (SELECT 1 FROM OCRM_F_MGR_CUS T4 WHERE 'YX' || '-' || TRIM(T1.TEL_NO) = T4.CUST_ID ")
			.append(" AND T4.MGR_STATUS = 'Y' AND T1.CORE_NO IS NULL))");
			log.info(vaildhasMgrNoSql);
			stm.execute(vaildhasMgrNoSql.toString());
			
			log.info("判断所有的数据是否合法。。。。");
			StringBuffer sb = new StringBuffer();
			sb.append("select count(1) from OCRM_F_MK_MKT_MY_ACTI_TEMP t where t.CREATE_USER = '"+ userId+"' and t.ID like '"+ PKhead+"%' and")
			.append(" t.IMP_STATUS = '0' ");
			ResultSet rs = stm.executeQuery(sb.toString());
			rs.next();
			int errCount = rs.getInt(1);
			if(errCount > 0){
				aUser.putAttribute(BACK_IMPORT_ERROR, "导入数据存在错误，请修改确认之后重新导入。。。");
				throw new BizException(1, 0, "10001", "导入数据存在错误，请修改确认之后重新导入。。。");
			}else{
				try {
					StringBuffer sb1 = new StringBuffer();
					sb1.append(" DELETE FROM OCRM_F_MGR_CUS T1 WHERE EXISTS ")
					.append(" (SELECT 1 FROM OCRM_F_MK_MKT_MY_ACTI_TEMP T2 WHERE T1.CUST_ID = ")
					.append(" DECODE(T2.CORE_NO,NULL,'YX'||'-'||TRIM(T2.TEL_NO), ")
					.append(" (SELECT T3.CUST_ID FROM acrm_f_ci_customer T3 WHERE T3.CORE_NO = T2.CORE_NO AND ROWNUM = 1))) ");
					stm.execute(sb1.toString());
					log.info("删除OCRM_F_MGR_CUS原有数据完毕。。。");
					sb1 = new StringBuffer();
					sb1.append(" INSERT INTO OCRM_F_MGR_CUS(CUST_ID,CUST_NAME,CORE_NO,TEL_NO,MGR_NO,MGR_STATUS,")
					.append(" CREATE_DATE,CREATE_USER,UPDATE_DATE,UPDATE_USER) ")
					.append(" SELECT ")
					.append(" DECODE(T1.CORE_NO,NULL,'YX'||'-'||TRIM(T1.TEL_NO),(SELECT T2.CUST_ID FROM ACRM_F_CI_CUSTOMER T2 WHERE T2.CORE_NO = T1.CORE_NO AND ROWNUM = 1)), ")
					.append(" T1.CUST_NAME,T1.CORE_NO,T1.TEL_NO,T1.MGR_NO,'',")
					.append(" SYSDATE,'"+userId+"',SYSDATE,'"+userId+"' ")
					.append(" FROM OCRM_F_MK_MKT_MY_ACTI_TEMP T1 ")
					.append(" WHERE T1.CREATE_USER = '"+ userId +"' AND T1.ID like '"+ PKhead +"%'");
					stm.execute(sb1.toString());
					log.info("插入OCRM_F_MGR_CUS数据完毕。。。");
					conn.commit();//提交事务
				} catch (Exception e) {
					conn.rollback();
					aUser.putAttribute("BACK_RUN_ERROR", true);//程序运行错误抛异常
					throw e;
				}
			}
		}catch(Exception e){
			//conn.rollback();
			e.printStackTrace();
			aUser.putAttribute(BACK_IMPORT_ERROR, "导入失败，失败原因："+e.getMessage());												
			throw e;
		}
		finally{
			JdbcUtil.close(null, stm, conn);
		}
		
	}

}
