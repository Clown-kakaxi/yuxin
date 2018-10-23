package com.yuchengtech.bob.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ecc.echain.workflow.engine.EVO;
import com.ecc.echain.workflow.engine.WfEngine;
import com.yuchengtech.bcrm.customer.belong.service.OcrmFCiTransApplyService;
import com.yuchengtech.bcrm.custview.action.accountQueryAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.importimpl.ImportInterface;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;

/**
 * 客户经理移交批量导入
 * @author xuyl
 *
 */
public class CustTransByManagerImport implements ImportInterface{
	
	private static Logger log = Logger.getLogger(CustTransByManagerImport.class);
	
	@Autowired
	private OcrmFCiTransApplyService ocrmFCiTransApplyService;

	@Override
	public void excute(Connection conn, String PKhead, AuthUser aUser) throws Exception {
		String userId = aUser.getUserId();
		String userName = aUser.getUsername();
		String orgId = aUser.getUnitId();
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
			log.info("校验核心客户号是否正确");
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
	/*public void excute(Connection conn, String PKhead, AuthUser aUser) throws Exception {
		String userId = aUser.getUserId();
		String userName = aUser.getUsername();
		String orgId = aUser.getUnitId();
		log.info("updateSQL: 【CustTransByManagerImport has been evoke!】");
		Statement stm = null;
		Date now =new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time=dateFormat.format(now);
		try {
			StringBuffer sb = new StringBuffer();
			stm = conn.createStatement();
			//conn.setAutoCommit(false);
			log.info("清除历史数据。。。。。。");
			String deleteSQL = "DELETE FROM OCRM_F_CI_BELONG_CUSTMGR_TEMP t where t.CREATE_USER ='" + userId + "' ";//清除创建人为当前登陆人的数据
			stm.execute(deleteSQL);
			log.info("更新新导入的数据的导入人为当前登陆用户。。。。。。");
			String sql = "update OCRM_F_CI_BELONG_CUSTMGR_TEMP set CREATE_USER='"+userId+"',APPLY_DATE='"+time+"',TRANSCONTENT=replace(TRANSCONTENT,'，',',') where ID like '" + PKhead + "%'";
			log.info(sql);
			stm.execute(sql);
			log.info("当导入数据库时，为null的字段替换为空");
			String replacenullsql = "update OCRM_F_CI_BELONG_CUSTMGR_TEMP set CUST_ID=replace(CUST_ID,'null',''),CUST_NAME=replace(CUST_NAME,'null',''),CUST_TYPE=replace(CUST_TYPE,'null',''),"
					+ "MGR_ID=replace(MGR_ID,'null',''),MGR_NAME=replace(MGR_NAME,'null',''),TMGR_ID=replace(TMGR_ID,'null',''),TMGR_NAME=replace(TMGR_NAME,'null',''),"
					+ "HAND_KIND=replace(HAND_KIND,'null',''),HAND_OVER_REASON=replace(HAND_OVER_REASON,'null',''),APPLY_TYPE=replace(APPLY_TYPE,'null',''),"
					+ "WORK_INTERFIX_DT=replace(WORK_INTERFIX_DT,'null',''),OLD_AUM=replace(OLD_AUM,'null',''),OLD_CREDIT=replace(OLD_CREDIT,'null',''),"
					+ "TRANSCONTENT=replace(TRANSCONTENT,'null',''),NEW_AUM=replace(NEW_AUM,'null',''),NEW_CREDIT=replace(NEW_CREDIT,'null',''),"
					+ "TRANSOTHER=replace(TRANSOTHER,'null','') where create_user ='"+userId+"' and ID like '" + PKhead + "%'";
			log.info(replacenullsql);
			stm.execute(replacenullsql);
			log.info("更新新导入的数据的移交类型。。。。。。");
			//个人区域内移交
			String sql1 = "update OCRM_F_CI_BELONG_CUSTMGR_TEMP set APPLY_TYPE='1' where create_user ='"+userId+"' and ID like '" + PKhead + "%' and CUST_TYPE='2'"
					+ " and id in (select t1.id from OCRM_F_CI_BELONG_CUSTMGR_TEMP t1 left join  admin_auth_account t2 on  t2.ACCOUNT_NAME=t1.CREATE_USER"
					+ " LEFT JOIN admin_auth_account T3 ON T3.ACCOUNT_NAME=T1.TMGR_ID left join admin_auth_org t5 on t2.org_id=t5.org_id where t3.org_id  in"
					+ " (select t4.org_id from admin_auth_org t4 start with t4.org_id = t5.up_org_id connect by t4.up_org_id = prior t4.org_id))";
			stm.execute(sql1);
			//个人跨区域移交
			String sql2 = "update OCRM_F_CI_BELONG_CUSTMGR_TEMP set APPLY_TYPE='2' where create_user ='"+userId+"' and ID like '" + PKhead + "%' and CUST_TYPE='2' and APPLY_TYPE is null";
			stm.execute(sql2);
			//企业支行内移交
			String sql3 = "update OCRM_F_CI_BELONG_CUSTMGR_TEMP set APPLY_TYPE='3' where create_user ='"+userId+"' and ID like '" + PKhead + "%' and CUST_TYPE='1'"
					+ " and id in (select t1.id from OCRM_F_CI_BELONG_CUSTMGR_TEMP t1 left join admin_auth_account t2 on t2.ACCOUNT_NAME = t1.CREATE_USER"
					+ " LEFT JOIN admin_auth_account T3 ON T3.ACCOUNT_NAME = T1.TMGR_ID where t2.org_id = t3.org_id)";
			stm.execute(sql3);
			//企业分行/区域内移交
			String sql4 = "update OCRM_F_CI_BELONG_CUSTMGR_TEMP set APPLY_TYPE='4' where create_user ='"+userId+"' and ID like '" + PKhead + "%' and CUST_TYPE='1' and APPLY_TYPE is null"
					+ " and id in (select t1.id from OCRM_F_CI_BELONG_CUSTMGR_TEMP t1 left join admin_auth_account t2 on t2.ACCOUNT_NAME = t1.CREATE_USER LEFT JOIN admin_auth_account T3"
					+ " ON T3.ACCOUNT_NAME = T1.TMGR_ID left join admin_auth_org t5 on t2.org_id = t5.org_id where t3.org_id in (select t4.org_id from admin_auth_org t4"
					+ " where t4.org_id <> t2.org_id start with t4.org_id = t5.up_org_id connect by t4.up_org_id = prior t4.org_id))";
			stm.execute(sql4);
			//企业跨分行/区域移交
			String sql5 = "update OCRM_F_CI_BELONG_CUSTMGR_TEMP set APPLY_TYPE='5' where create_user ='"+userId+"' and ID like '" + PKhead + "%' and CUST_TYPE='1' and APPLY_TYPE is null"
					+ " and id in(select t1.id from OCRM_F_CI_BELONG_CUSTMGR_TEMP t1 left join admin_auth_account t2 on t2.ACCOUNT_NAME = t1.CREATE_USER  LEFT JOIN admin_auth_account T3"
					+ " ON T3.ACCOUNT_NAME = T1.TMGR_ID where t3.org_id not in (select t4.org_id from admin_auth_org t4 start with t4.org_id = t2.org_id  connect by t4.up_org_id = prior t4.org_id))";
			stm.execute(sql5);
			//个人移交还是企业移交
			sb = new StringBuffer();
			sb.append("select t.cust_type from OCRM_F_CI_BELONG_CUSTMGR_TEMP t where t.CREATE_USER ='" + userId + "' and t.ID like '" + PKhead + "%' ");
			ResultSet resultSet1 = stm.executeQuery(sb.toString());
			resultSet1.next();
			String custType = resultSet1.getString(1);
			log.info("校验客户号不能为空");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户号不能为空,' ");
			sb.append(" where CREATE_USER='"+userId+"' and (cust_id is null or cust_id='' or cust_id='null') ");
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
			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'该客户ID不存在,' ");
			sb.append(" where CREATE_USER='"+userId+"' and cust_id not in (");
			sb.append(" select t.cust_id");
			sb.append(" from ACRM_F_CI_CUSTOMER t where t.cust_id is not null");
			sb.append(" )");
			stm.execute(sb.toString());
//			log.info("校验客户名称和客户号是否匹配");
//			sb = new StringBuffer();
//			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'该客户ID与客户名称不匹配,' ");
//			sb.append(" where CREATE_USER='"+userId+"' and cust_id not in(");
//			sb.append(" select t1.cust_id");
//			sb.append(" from ACRM_F_CI_CUSTOMER t1,OCRM_F_CI_BELONG_CUSTMGR_TEMP t2 where t1.cust_id=t2.cust_id and t1.cust_name=t2.cust_name ");
//			sb.append(" )");
//			stm.execute(sb.toString());
//			log.info("校验申请客户经理ID和名称是否匹配");
//			sb = new StringBuffer();
//			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'该客户经理ID与客户经理名称不匹配,' ");
//			sb.append(" where CREATE_USER='"+userId+"' and mgr_id not in(");
//			sb.append(" select t1.ACCOUNT_NAME");
//			sb.append(" from ADMIN_AUTH_ACCOUNT t1,OCRM_F_CI_BELONG_CUSTMGR_TEMP t2 where t1.ACCOUNT_NAME=t2.mgr_id and t1.USER_NAME=t2.mgr_name ");
//			sb.append(" )");
//			stm.execute(sb.toString());
//			log.info("校验接收客户经理ID和名称是否匹配");
//			sb = new StringBuffer();
//			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'接收客户经理ID与接收客户经理名称不匹配,' ");
//			sb.append(" where CREATE_USER='"+userId+"' and tmgr_id not in(");
//			sb.append(" select t1.ACCOUNT_NAME");
//			sb.append(" from ADMIN_AUTH_ACCOUNT t1,OCRM_F_CI_BELONG_CUSTMGR_TEMP t2 where t1.ACCOUNT_NAME=t2.tmgr_id and t1.USER_NAME=t2.tmgr_name ");
//			sb.append(" )");
//			stm.execute(sb.toString());
			log.info("校验客户类型不能为空");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户类型不能为空,' ");
			sb.append(" where CREATE_USER='"+userId+"' and (cust_type is null or cust_type='' or cust_type='null') ");
			stm.execute(sb.toString());
			log.info("校验客户类型是否正确");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户类型不正确,' ");
			sb.append(" where CREATE_USER='"+userId+"' and (cust_type!='1' and cust_type!='2')");
			stm.execute(sb.toString());
			log.info("校验客户经理编号不能为空");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户经理编号不能为空,' ");
			sb.append(" where CREATE_USER='"+userId+"' and (mgr_id is null or mgr_id='' or mgr_id='null') ");
			stm.execute(sb.toString());
			log.info("校验客户经理编号是否正确");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户经理编号不存在,' ");
			sb.append(" where CREATE_USER='"+userId+"' and mgr_id not in (");
			sb.append(" select t.mgr_id");
			sb.append(" from OCRM_F_CI_BELONG_CUSTMGR t where t.mgr_id is not null");
			sb.append(" )");
			stm.execute(sb.toString());
			log.info("校验接受客户经理编号不能为空");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'接受客户经理编号不能为空,' ");
			sb.append(" where CREATE_USER='"+userId+"' and (tmgr_id is null or tmgr_id='' or tmgr_id='null') ");
			stm.execute(sb.toString());
			log.info("校验接受客户经理编号是否正确");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'接受客户经理编号不存在,' ");
			sb.append(" where CREATE_USER='"+userId+"' and tmgr_id not in (");
			sb.append(" select t.mgr_id");
			sb.append(" from OCRM_F_CI_BELONG_CUSTMGR t where t.mgr_id is not null");
			sb.append(" )");
			stm.execute(sb.toString());
			log.info("校验工作交接日期是否为空");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'工作交接日期不能为空,' ");
			sb.append(" where CREATE_USER='"+userId+"' and (WORK_INTERFIX_DT is null or WORK_INTERFIX_DT='' or WORK_INTERFIX_DT='null')");
			stm.execute(sb.toString());
			log.info("校验是否有权限操作");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'没有权限操作此数据,' ");
			sb.append(" where CREATE_USER='"+userId+"' and (mgr_id!='"+userId+"' or mgr_id!=(select t.MGR_ID from OCRM_F_CI_BELONG_CUSTMGR t,OCRM_F_CI_BELONG_CUSTMGR_TEMP c where t.cust_id=c.cust_id and c.CREATE_USER='"+userId+"')) ");
			stm.execute(sb.toString());
			log.info("校验工作交接日期正则表达式");
			String eL="^[0-9]{4}(\\-)[0-9]{2}(\\-)[0-9]{2}";
			sb = new StringBuffer();
			sb.append("select t.WORK_INTERFIX_DT from OCRM_F_CI_BELONG_CUSTMGR_TEMP t where t.CREATE_USER ='" + userId + "' and t.ID like '" + PKhead + "%' ");
			ResultSet rs1 = stm.executeQuery(sb.toString());
			rs1.next();
			String workDt = rs1.getString(1);
			Pattern pattern = Pattern.compile(eL);
			Matcher matcher = pattern.matcher(workDt);
			boolean b  = matcher.matches();
			if(b==false){
				sb = new StringBuffer();
				sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'工作交接日期格式不正确,' ");
				sb.append("  where CREATE_USER='"+userId+"' and  WORK_INTERFIX_DT='"+workDt+"' ");
				stm.execute(sb.toString());
			}
			if(custType.equals("1")){//企业
				log.info("校验客户移交类别不能为空");
				sb = new StringBuffer();
				sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户移交类别不能为空,' ");
				sb.append(" where CREATE_USER='"+userId+"' and (hand_kind is null or hand_kind='' or hand_kind='null') ");
				stm.execute(sb.toString());
				log.info("校验客户移交类别是否正确");
				sb = new StringBuffer();
				sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户移交类别不正确,' ");
				sb.append(" where CREATE_USER='"+userId+"' and (hand_kind!='1' and hand_kind!='2')");
				stm.execute(sb.toString());
				log.info("校验工作移交原因是否为空");
				sb = new StringBuffer();
				sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户移交原因不能为空,' ");
				sb.append(" where CREATE_USER='"+userId+"' and (hand_over_reason is null or hand_over_reason='' or hand_over_reason='null') ");
				stm.execute(sb.toString());
			}else{//个人
				log.info("校验移转内容是否为空");
				sb = new StringBuffer();
				sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户移转内容不能为空,'");
				sb.append(" where CREATE_USER='"+userId+"' and (TRANSCONTENT is null or TRANSCONTENT='' or TRANSCONTENT='null')");
				stm.execute(sb.toString());
				log.info("校验移转内容是否正确");
				sb = new StringBuffer();
				sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户移转内容不正确,'");
				sb.append(" where CREATE_USER='"+userId+"' and (TRANSCONTENT!='1' and TRANSCONTENT!='2' and TRANSCONTENT!='3' and TRANSCONTENT!='1,2' and TRANSCONTENT!='1,3' and TRANSCONTENT!='2,3' and TRANSCONTENT!='1,2,3')");
				stm.execute(sb.toString());
				log.info("校验移转内容(其它)是否未空");
				sb = new StringBuffer();
				sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户移转内容(其它)不能为空,'");
				sb.append(" where CREATE_USER='"+userId+"' and (TRANSOTHER is null or TRANSOTHER='' or TRANSOTHER='null')");
				stm.execute(sb.toString());
				log.info("校验移转原因是否未空");
				sb = new StringBuffer();
				sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户移转原因不能为空,'");
				sb.append(" where CREATE_USER='"+userId+"' and (HAND_KIND is null or HAND_KIND='' or HAND_KIND='null')");
				stm.execute(sb.toString());
				log.info("校验移转原因是否正确");
				sb = new StringBuffer();
				sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户移交类别不正确,' ");
				sb.append(" where CREATE_USER='"+userId+"' and (hand_kind!='1' and hand_kind!='2' and hand_kind!='3' and hand_kind!='4' and hand_kind!='5')");
				stm.execute(sb.toString());
				String num="^([1-9][0-9]*)+(.[0-9]{1,2})?$";
				sb = new StringBuffer();
				sb.append("select t.OLD_AUM,t.NEW_AUM,t.OLD_CREDIT,t.NEW_CREDIT from OCRM_F_CI_BELONG_CUSTMGR_TEMP t where t.CREATE_USER ='" + userId + "' and t.ID like '" + PKhead + "%'");
				ResultSet resultSet2 = stm.executeQuery(sb.toString());
				resultSet2.next();
				String oldAum = resultSet2.getString(1);
				String newAum = resultSet2.getString(2);
				String oldCredit = resultSet2.getString(3);
				String newCredit = resultSet2.getString(4);
				Pattern pattern1 = Pattern.compile(num);
				Matcher matcher1 = pattern1.matcher(oldAum);
				Matcher matcher2 = pattern1.matcher(newAum);
				Matcher matcher3 = pattern1.matcher(oldCredit);
				Matcher matcher4 = pattern1.matcher(newCredit);
				boolean b1  = matcher1.matches();
				boolean b2  = matcher2.matches();
				boolean b3  = matcher3.matches();
				boolean b4  = matcher4.matches();
				if(b1==false){//对原AUM金额进行校验
					log.info("对原AUM金额进行正则校验");
					sb = new StringBuffer();
					sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'原AUM金额格式不正确,' ");
					sb.append("  where CREATE_USER='"+userId+"' and  OLD_AUM='"+oldAum+"' ");
					stm.execute(sb.toString());
				}
				if(b2==false){//对新AUM金额进行校验
					log.info("对新AUM金额进行正则校验");
					sb = new StringBuffer();
					sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'新AUM金额格式不正确,' ");
					sb.append("  where CREATE_USER='"+userId+"' and  NEW_AUM='"+newAum+"' ");
					stm.execute(sb.toString());
				}
				if(b3==false){//对原授信金额进行校验
					log.info("对原授信金额进行正则校验");
					sb = new StringBuffer();
					sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'原授信金额格式不正确,' ");
					sb.append("  where CREATE_USER='"+userId+"' and  OLD_CREDIT='"+oldCredit+"' ");
					stm.execute(sb.toString());
				}
				if(b4==false){//对新授信金额进行校验
					log.info("对新授信金额进行正则校验");
					sb = new StringBuffer();
					sb.append("update OCRM_F_CI_BELONG_CUSTMGR_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'新授信金额格式不正确,' ");
					sb.append("  where CREATE_USER='"+userId+"' and  NEW_CREDIT='"+newCredit+"' ");
					stm.execute(sb.toString());
				}
			}
			log.info("检查完毕，判断所有数据是否合法");
			sb = new StringBuffer("select count(1) FROM OCRM_F_CI_BELONG_CUSTMGR_TEMP t where t.CREATE_USER ='" + userId + "' and ID like '" + PKhead + "%' and t.imp_status='0' ");
			log.info(sb.toString());
			ResultSet rs = stm.executeQuery(sb.toString());
			rs.next();
			int errCount = rs.getInt(1);
			if(errCount>0){
//				String delete = "DELETE FROM OCRM_F_CI_BELONG_CUSTMGR_TEMP t where t.CREATE_USER ='" + userId + "' and t.ID like '" + PKhead + "%'";//插入失败删除原先的数据
//				stm.execute(delete);
				aUser.putAttribute(BACK_IMPORT_ERROR, "导入数据存在错误，请修改确认之后重新导入");												
				throw new BizException(1, 0, "10001", "导入数据存在错误，请修改确认之后重新导入");
			}else{
				Statement statement  = conn.createStatement();
				if(custType.equals("1")){
					try {
						log.info("客户类型：企业");
						log.info("查找此次导入的接收客户经理ID(去重)，有多少个ID就有多少个流程");
						sb = new StringBuffer("select distinct t.tmgr_id,a.user_name,a.org_id,b.org_name,t.HAND_KIND,t.HAND_OVER_REASON,t.APPLY_TYPE,t.WORK_INTERFIX_DT"
								+ " from OCRM_F_CI_BELONG_CUSTMGR_TEMP t left outer join admin_auth_account a on t.tmgr_id = a.account_name"
								+ " left outer join admin_auth_org b on a.org_id = b.org_id and b.app_id = 62"
								+ " where t.CREATE_USER ='" + userId + "' and t.ID like '" + PKhead + "%'");
						ResultSet result = statement.executeQuery(sb.toString());
						while(result.next()){
							log.info("向客户移交申请表（OCRM_F_CI_TRANS_APPLY）中插入申请信息");
							String insertSql = " insert into OCRM_F_CI_TRANS_APPLY(APPLY_NO,USER_ID,USER_NAME,T_MGR_ID,T_MGR_NAME,T_ORG_ID,T_ORG_NAME,APPLY_DATE,HAND_KIND,HAND_OVER_REASON,APPROVE_STAT,APPLY_TYPE,WORK_INTERFIX_DT,TYPE)"
								+ " select id_sequence.nextval,"
								+ " '"+userId+"',"
								+ " '"+userName+"',"
								+ " '"+result.getString(1)+"',"
								+ " '"+result.getString(2)+"',"
								+ " '"+result.getString(3)+"',"
								+ " '"+result.getString(4)+"',"
								+ " TRUNC(SYSDATE),"
								+ " '"+result.getString(5)+"',"
								+ " '"+result.getString(6)+"',"
								+ " '1' ,"
								+ " '"+result.getString(7)+"',"
								+ " to_date('"+result.getString(8)+"','yyyy-MM-dd'),"
								+ " '0' "
								+ " from dual";
							log.info("insertSql: 【" + insertSql + "】");
							stm.execute(insertSql);
							log.info("查找刚插入客户移交申请表（OCRM_F_CI_TRANS_APPLY）中的申请编号和接收机构号等信息");
							sb = new StringBuffer("select t.APPLY_NO,t.T_MGR_ID,t.APPLY_TYPE,to_char(t.WORK_INTERFIX_DT,'yyyy-MM-dd'),t.HAND_KIND,t.HAND_OVER_REASON,t.T_MGR_NAME from OCRM_F_CI_TRANS_APPLY t where t.USER_ID ='" + userId + "' and t.APPLY_DATE=TRUNC(SYSDATE) order by t.APPLY_NO desc");
							ResultSet resultSet = stm.executeQuery(sb.toString());
							resultSet.next();
							String applyNo = resultSet.getString(1);
							String tMgrId = resultSet.getString(2);
							String applyType = resultSet.getString(3);
							String workInterFixDT = resultSet.getString(4);
							String handKind = resultSet.getString(5);
							String handOverReason = resultSet.getString(6);
							String tMgrName = resultSet.getString(7);
							log.info("向移交客户列表（OCRM_F_CI_TRANS_CUST）插入信息");
							String insertSql2 = " insert into OCRM_F_CI_TRANS_CUST(ID,APPLY_NO,RECORD_ID,CUST_ID,CUST_NAME,MGR_ID,MGR_NAME,MAIN_TYPE_NEW,STATE,MAIN_TYPE,INSTITUTION,INSTITUTION_NAME)"
								+ " select id_sequence.nextval,"
								+ " '"+applyNo+"',"
								+ " replace(t.ID,'null',''),"
								+ " replace(t.CUST_ID,'null',''),"
								+ " replace(t.CUST_NAME,'null',''),"
								+ " replace(t.MGR_ID,'null',''),"
								+ " replace(t.USER_NAME,'null',''),"
								+ " '1',"
								+ " '1',"
								+ " '1',"
								+ " replace(t.ORG_ID,'null',''),"
								+ " replace(t.ORG_NAME,'null','')"
								+ " from (select m.CUST_ID,c.CUST_NAME,m.MGR_ID,a.USER_NAME,a.ORG_ID,b.ORG_NAME,d.ID"
								+ " from OCRM_F_CI_BELONG_CUSTMGR_TEMP m,ADMIN_AUTH_ACCOUNT a,admin_auth_org b,ACRM_F_CI_CUSTOMER c,OCRM_F_CI_BELONG_CUSTMGR d where m.CUST_ID=c.CUST_ID and m.CUST_ID=d.CUST_ID and a.org_id = b.org_id and m.MGR_ID=a.ACCOUNT_NAME and m.ID like '" + PKhead + "%' and m.TMGR_ID='"+tMgrId+"' and m.create_user='"+userId+"' and m.WORK_INTERFIX_DT='"+workInterFixDT+"' and m.HAND_KIND='"+handKind+"' and m.HAND_OVER_REASON='"+handOverReason+"' and m.APPLY_TYPE='"+applyType+"') t ";
							log.info("insertSql: 【" + insertSql2 + "】");
							stm.execute(insertSql2);
							String instanceid = "YJ_" + applyNo;
							String jobName = "客户移交_" + tMgrName;
							
							Map paramMap = new HashMap();
							paramMap.put("type", applyType);
						    
						    WfEngine we = WfEngine.getInstance();
							EVO vo = new EVO();
							
							try {
								vo.setWFID("28");
								vo.setCurrentUserID(userId);
								vo.setOrgid(orgId);
								if ((instanceid != null) && (instanceid.length() > 0))
									vo.setInstanceID(instanceid);
								if ((jobName != null) && (jobName.length() > 0))
									vo.setJobName(jobName);
								if (paramMap != null)
									vo.paramMap.putAll(paramMap);
								System.out.println(vo);
								vo = we.initializeWFWholeDocUNID(vo);
								
								String nextNode = "28_a4";
							    switch (Integer.valueOf(applyType).intValue()) {
							    case 3:
							      nextNode = "28_a12";
							      break;
							    case 4:
							      nextNode = "28_a16";
							      break;
							    case 5:
							      nextNode = "28_a21";
							    }

							    Map map = new HashMap();
							    map.put("instanceid", instanceid);
							    map.put("currNode", "28_a3");
							    map.put("nextNode", nextNode);
							    
							    Map<String, Object> json=map;
							} catch (Exception e) {
								aUser.putAttribute("BACK_RUN_ERROR", true);//流程异常报错
								throw e;
							}
						}
					} catch (Exception e) {
						aUser.putAttribute("BACK_RUN_ERROR", true);//程序运行错误抛异常
						throw e;
					}
				}else if(custType.equals("2")){
					log.info("客户类型：个人");
					log.info("查找此次导入的接收客户经理ID(去重)，有多少个ID就有多少个流程");
					sb = new StringBuffer();
					sb.append("select distinct t.TMGR_ID,a.USER_NAME,a.ORG_ID,b.ORG_NAME,t.APPLY_TYPE,t.HAND_KIND,t.WORK_INTERFIX_DT,t.OLD_AUM,t.NEW_AUM,t.OLD_CREDIT,t.NEW_CREDIT,t.TRANSCONTENT,t.TRANSOTHER "
							+ " from OCRM_F_CI_BELONG_CUSTMGR_TEMP t left outer join admin_auth_account a on t.tmgr_id = a.account_name"
							+ " left outer join admin_auth_org b on a.org_id = b.org_id and b.app_id = 62"
							+ " where t.CREATE_USER ='" + userId + "' and t.ID like '" + PKhead + "%'");
					ResultSet result1 = statement.executeQuery(sb.toString());
					while(result1.next()){
						log.info("向客户移交申请表（OCRM_F_CI_TRANS_APPLY）中插入申请信息");
						String insertSql = "insert into OCRM_F_CI_TRANS_APPLY(APPLY_NO,USER_ID,USER_NAME,T_MGR_ID,T_MGR_NAME,T_ORG_ID,T_ORG_NAME,APPLY_DATE,APPLY_TYPE,HAND_KIND,WORK_INTERFIX_DT,OLD_AUM,NEW_AUM,OLD_CREDIT,NEW_CREDIT,TRANS_CONTENT,TRANS_OTHER,APPROVE_STAT,TYPE)"
								+ " select id_sequence.nextval,"
								+ " '"+userId+"',"
								+ " '"+userName+"',"
								+ " '"+result1.getString(1)+"',"
								+ " '"+result1.getString(2)+"',"
								+ " '"+result1.getString(3)+"',"
								+ " '"+result1.getString(4)+"',"
								+ " TRUNC(SYSDATE),"
								+ " '"+result1.getString(5)+"',"
								+ " '"+result1.getString(6)+"',"
								+ " to_date('"+result1.getString(7)+"','yyyy-MM-dd'),"
								+ " '"+result1.getString(8)+"',"
								+ " '"+result1.getString(9)+"',"
								+ " '"+result1.getString(10)+"',"
								+ " '"+result1.getString(11)+"',"
								+ " '"+result1.getString(12)+"',"
								+ " '"+result1.getString(13)+"',"
								+ " '1',"
								+ " '0'"
								+ " from dual";
						log.info("insertSql: 【" + insertSql + "】");
						stm.execute(insertSql);
						log.info("查找刚插入客户移交申请表（OCRM_F_CI_TRANS_APPLY）中的申请编号和接收机构号等信息");
						sb = new StringBuffer("select t.APPLY_NO,t.T_MGR_ID,t.APPLY_TYPE,to_char(t.WORK_INTERFIX_DT,'yyyy-MM-dd'),t.HAND_KIND,t.T_MGR_NAME,t.OLD_AUM,t.NEW_AUM,t.OLD_CREDIT,t.NEW_CREDIT,t.TRANS_CONTENT,t.TRANS_OTHER from OCRM_F_CI_TRANS_APPLY t where t.USER_ID ='" + userId + "' and t.APPLY_DATE=TRUNC(SYSDATE) order by t.APPLY_NO desc");
						ResultSet resultSet = stm.executeQuery(sb.toString());
						resultSet.next();
						String applyNo=resultSet.getString(1);
						String tmgrId=resultSet.getString(2);
						String applyType=resultSet.getString(3);
						String workInterfixDT=resultSet.getString(4);
						String handKind=resultSet.getString(5);
						String tmgrName=resultSet.getString(6);
						String oldAUM=resultSet.getString(7);
						String newAUM=resultSet.getString(8);
						String oldCredit=resultSet.getString(9);
						String newCredit=resultSet.getString(10);
						String transContent=resultSet.getString(11);
						String transOther=resultSet.getString(12);
						log.info("向移交客户列表（OCRM_F_CI_TRANS_CUST）插入信息");
						String insertSql2 = " insert into OCRM_F_CI_TRANS_CUST(ID,APPLY_NO,RECORD_ID,CUST_ID,CUST_NAME,MGR_ID,MGR_NAME,MAIN_TYPE_NEW,STATE,MAIN_TYPE,INSTITUTION,INSTITUTION_NAME)"
								+ " select id_sequence.nextval,"
								+ " '"+applyNo+"',"
								+ " replace(t.ID,'null',''),"
								+ " replace(t.CUST_ID,'null',''),"
								+ " replace(t.CUST_NAME,'null',''),"
								+ " replace(t.MGR_ID,'null',''),"
								+ " replace(t.USER_NAME,'null',''),"
								+ " '1',"
								+ " '1',"
								+ " '1',"
								+ " replace(t.ORG_ID,'null',''),"
								+ " replace(t.ORG_NAME,'null','')"
								+ " from (select m.CUST_ID,c.CUST_NAME,m.MGR_ID,a.USER_NAME,a.ORG_ID,b.ORG_NAME"
								+ " from OCRM_F_CI_BELONG_CUSTMGR_TEMP m,ADMIN_AUTH_ACCOUNT a,admin_auth_org b,ACRM_F_CI_CUSTOMER c,OCRM_F_CI_BELONG_CUSTMGR d where m.CUST_ID=c.CUST_ID and m.CUST_ID=d.CUST_ID and a.org_id = b.org_id and m.MGR_ID=a.ACCOUNT_NAME and m.ID like '" + PKhead + "%' and m.TMGR_ID='"+tmgrId+"' and m.create_user='"+userId+"' and m.WORK_INTERFIX_DT='"+workInterfixDT+"' and m.HAND_KIND='"+handKind+"' and m.APPLY_TYPE='"+applyType+"' and m.OLD_AUM='"+oldAUM+"' and m.NEW_AUM='"+newAUM+"' and m.OLD_CREDIT='"+oldCredit+"' and m.NEW_CREDIT='"+newCredit+"' and m.TRANSCONTENT='"+transContent+"' and m.TRANSOTHER='"+transOther+"') t ";
							log.info("insertSql: 【" + insertSql2 + "】");
							stm.execute(insertSql2);
							String instanceid = "YJ_" + applyNo;
							String jobName = "客户移交_" + tmgrName;
							
							Map paramMap = new HashMap();
							paramMap.put("type", applyType);
						    
						    WfEngine we = WfEngine.getInstance();
							EVO vo = new EVO();
							
							try {
								vo.setWFID("28");
								vo.setCurrentUserID(userId);
								vo.setOrgid(orgId);
								if ((instanceid != null) && (instanceid.length() > 0))
									vo.setInstanceID(instanceid);
								if ((jobName != null) && (jobName.length() > 0))
									vo.setJobName(jobName);
								if (paramMap != null)
									vo.paramMap.putAll(paramMap);
								System.out.println(vo);
								vo = we.initializeWFWholeDocUNID(vo);
								
								String nextNode = "28_a4";
							    switch (Integer.valueOf(applyType).intValue()) {
							    case 1:
							      nextNode = "28_a4";
							      break;
							    case 2:
							      nextNode = "28_a8";
							      break;
							    }

							    Map map = new HashMap();
							    map.put("instanceid", instanceid);
							    map.put("currNode", "28_a3");
							    map.put("nextNode", nextNode);
							    
							    Map<String, Object> json=map;
							} catch (Exception e) {
								aUser.putAttribute("BACK_RUN_ERROR", true);//流程异常报错
								throw e;
							}
					}
				}
//				String delete = "DELETE FROM OCRM_F_CI_BELONG_CUSTMGR_TEMP t where t.CREATE_USER ='" + userId + "' and t.ID like '" + PKhead + "%'";//插入失败删除原先的数据
//				stm.execute(delete);
				statement.close();
				stm.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			aUser.putAttribute(BACK_IMPORT_ERROR, "导入失败，失败原因："+e.getMessage());												
			throw e;
			//conn.rollback();
		} finally{
//			String deleteSQL = "DELETE FROM OCRM_F_CI_BELONG_CUSTMGR_TEMP t where t.ID like '" + PKhead + "%'";//插入失败删除原先的数据
//			stm.execute(deleteSQL);
			//conn.commit();
//			try {
//				String deleteSQL = "DELETE FROM OCRM_F_CI_BELONG_CUSTMGR_TEMP t where t.ID like '" + PKhead + "%'";//插入失败删除原先的数据
//				stm.execute(deleteSQL);
//				conn.commit();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			JdbcUtil.close(null, stm, conn);
		}
	}*/
	

}
