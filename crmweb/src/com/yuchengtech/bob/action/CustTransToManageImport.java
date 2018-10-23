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
 * 法金客户经理移转导入
 * @author xuyl
 *
 */
public class CustTransToManageImport implements ImportInterface{
	
	private static Logger log = Logger.getLogger(CustTransToManageImport.class);

	@Override
	public void excute(Connection conn, String PKhead, AuthUser aUser) throws Exception  {
		String userId = aUser.getUserId();
		log.info("updateSQL: 【CustTransByManagerImport has been evoke!】");
		Statement stm = null;
		Date now =new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time=dateFormat.format(now);
		try {
			StringBuffer sb = new StringBuffer();
			stm = conn.createStatement();
			log.info("清除历史数据。。。。。。");
			String deleteSQL = "DELETE FROM OCRM_F_CI_BELONG_CUSTMGR_TEMP t where t.CREATE_USER ='" + userId + "' ";//清除创建人为当前登陆人的数据
			stm.execute(deleteSQL);
			log.info("更新新导入的数据的导入人为当前登陆用户。。。。。。");
			String sql = "update OCRM_F_CI_BELONG_CUSTMGR_TEMP set CREATE_USER='"+userId+"',APPLY_DATE='"+time+"' where ID like '" + PKhead + "%'";
			log.info(sql);
			stm.execute(sql);
			log.info("通过核心客户号更新客户Id");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP a set a.cust_id =(select t.cust_id from ACRM_F_CI_CUSTOMER t  where t.core_no = a.core_no) where a.core_no = (select t.core_no from ACRM_F_CI_CUSTOMER t where t.core_no = a.core_no)");
			stm.execute(sb.toString());
			log.info("校验客户是否在流程中");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'该客户在流程中,'  ")
			.append("where CREATE_USER='"+userId+"' and cust_id in (")
			.append("	select t.cust_id  ")
			.append("	from OCRM_F_CI_TRANS_CUST t ")
			.append(" 	inner join OCRM_F_CI_TRANS_APPLY c on c.apply_no = t.apply_no and c.approve_stat = '1'")
			.append(" )");
			stm.execute(sb.toString());
			log.info("校验客户号是否正确");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'该客户核心客户号不存在,' ");
			sb.append(" where CREATE_USER='"+userId+"' and core_no not in (");
			sb.append(" select t.core_no");
			sb.append(" from ACRM_F_CI_CUSTOMER t where t.core_no is not null");
			sb.append(" )");
			stm.execute(sb.toString());
			log.info("校验是否有权限操作");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'没有权限操作此数据,' ");
			sb.append(" where CREATE_USER='"+userId+"' and '"+userId+"'not in (select t.MGR_ID from OCRM_F_CI_BELONG_CUSTMGR t,OCRM_F_CI_BELONG_CUSTMGR_TEMP c where t.cust_id=c.cust_id and c.CREATE_USER='"+userId+"') ");
			log.info(sb.toString());
			stm.execute(sb.toString());
			log.info("校验同一excel中只能有一个客户类型");
			sb = new StringBuffer();
			sb.append("select count(distinct t.cust_type) from OCRM_F_CI_BELONG_CUSTMGR_TEMP m,ACRM_F_CI_CUSTOMER t where m.cust_id=t.cust_id and m.CREATE_USER='"+userId+"'");
			ResultSet resultSet = stm.executeQuery(sb.toString());
			resultSet.next();
			int count=resultSet.getInt(1);
			if(count>1){
				sb=new StringBuffer();
				sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'该客户类型为企业,' ");
				sb.append(" where CREATE_USER='"+userId+"' and cust_id in (select t.cust_id from ACRM_F_CI_CUSTOMER t,OCRM_F_CI_BELONG_CUSTMGR_TEMP m where t.cust_id=m.cust_id and t.cust_type='1')");
				stm.execute(sb.toString());
				sb=new StringBuffer();
				sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'该客户类型为个人,' ");
				sb.append(" where CREATE_USER='"+userId+"' and cust_id in (select t.cust_id from ACRM_F_CI_CUSTOMER t,OCRM_F_CI_BELONG_CUSTMGR_TEMP m where t.cust_id=m.cust_id and t.cust_type='2')");
				stm.execute(sb.toString());
			}
			log.info("检查完毕，判断所有数据是否合法");
			sb = new StringBuffer("select count(1) FROM OCRM_F_CI_BELONG_CUSTMGR_TEMP t where t.create_user ='" + userId + "' and t.ID like '" + PKhead + "%' and t.imp_status='0' ");
			log.info(sb.toString());
			ResultSet rs = stm.executeQuery(sb.toString());
			rs.next();
			int errCount = rs.getInt(1);
			if(errCount>0){
				aUser.putAttribute(BACK_IMPORT_ERROR, "导入数据存在错误，请修改确认之后重新导入");												
				throw new BizException(1, 0, "10001", "导入数据存在错误，请修改确认之后重新导入");
			}
		} catch (Exception e) {
			e.printStackTrace();
			aUser.putAttribute(BACK_IMPORT_ERROR, "导入失败，失败原因："+e.getMessage());												
			throw e;
		}finally{
			JdbcUtil.close(null, stm, conn);
		}
	}

}
