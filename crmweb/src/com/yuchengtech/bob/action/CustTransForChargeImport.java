package com.yuchengtech.bob.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.yuchengtech.bcrm.customer.service.CustomerTransService;
import com.yuchengtech.bcrm.trade.model.TxLog;
import com.yuchengtech.bcrm.util.SpringContextUtils;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.importimpl.ImportInterface;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.bo.RequestHeader;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.client.TransClient;
import com.yuchengtech.trans.impl.custTransfer.CustTransferSyncLNTransaction;
import com.yuchengtech.trans.impl.ecif.EcifTransaction;
import com.yuchengtech.trans.inf.Transaction;

/**
 * 主管直接移交
 * @author xuyl
 *
 */
public class CustTransForChargeImport implements ImportInterface{
	
	private static Logger log = Logger.getLogger(CustTransForChargeImport.class);

	@Override
	public void excute(Connection conn, String PKhead, AuthUser aUser) throws Exception {
		String userId = aUser.getUserId();
		String userName = aUser.getUsername();
		String userOrgId = aUser.getUnitId();
		
		CommonService commonService =  (CommonService)SpringContextUtils.getBean(CustomerTransService.class);
		
		log.info("updateSQL: 【CustTransForChargeImport has been evoke!】");
		Statement stm = null;
		Date now =new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time=dateFormat.format(now);
		try {
			StringBuffer sb = new StringBuffer();
			stm = conn.createStatement();
			log.info("清除历史数据。。。。。。");
			String deleteSQL = "DELETE FROM OCRM_F_CI_MANAGE_TRANS_TEMP t where t.ASSIGN_USER ='" + userId + "'  ";//清除创建人为当前登陆人的数据
			stm.executeUpdate(deleteSQL);
			log.info("更新新导入的数据的创建人为当前登陆用户，创建日期为当前日期。。。。。。");
			String sql = "update OCRM_F_CI_MANAGE_TRANS_TEMP set ASSIGN_USER='"+userId+"',ASSIGN_USERNAME='"+userName+"',ASSIGN_DATE='"+time+"' where ID like '" + PKhead + "%'";
			stm.executeUpdate(sql);
			log.info("当导入数据库时，为null的字段替换为空");
			String replacenullsql = "update OCRM_F_CI_MANAGE_TRANS_TEMP set CUST_ID=replace(CUST_ID,'null',''),CUST_NAME=replace(CUST_NAME,'null',''),CUST_TYPE=replace(CUST_TYPE,'null',''),"
					+ "MGR_ID=replace(MGR_ID,'null',''),MGR_NAME=replace(MGR_NAME,'null',''),T_MGR_ID=replace(T_MGR_ID,'null',''),T_MGR_NAME=replace(T_MGR_NAME,'null',''),"
					+ "HAND_KIND=replace(HAND_KIND,'null',''),HAND_OVER_REASON=replace(HAND_OVER_REASON,'null',''),"
					+ "WORK_INTERFIX_DT=replace(WORK_INTERFIX_DT,'null','')"
					+ " where ASSIGN_USER ='"+userId+"' and ID like '" + PKhead + "%'";
			log.info(replacenullsql);
			stm.executeUpdate(replacenullsql);
			log.info("通过核心客户号更新客户Id");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_MANAGE_TRANS_TEMP a set a.cust_id =(select t.cust_id from ACRM_F_CI_CUSTOMER t  where t.core_no = a.core_no) where a.core_no = (select t.core_no from ACRM_F_CI_CUSTOMER t where t.core_no = a.core_no)");
			stm.executeUpdate(sb.toString());
			log.info("更新导出数据的主协办类型");
			String sql4="update OCRM_F_CI_MANAGE_TRANS_TEMP t set t.MAIN_TYPE='1' WHERE t.CUST_TYPE='1'";
			log.info(sql4);
			stm.executeUpdate(sql4);
			log.info("更新导入数据的移交类型");
			//对私移交
			String sql1="update OCRM_F_CI_MANAGE_TRANS_TEMP set APPLY_TYPE='6' WHERE ASSIGN_USER='" + userId + "' and CUST_TYPE='2'";
			stm.executeUpdate(sql1);
			//对公移交主办类型
			String sql2="update OCRM_F_CI_MANAGE_TRANS_TEMP set APPLY_TYPE='7' WHERE ASSIGN_USER='" + userId + "' and CUST_TYPE='1' and MAIN_TYPE='1'";
			stm.executeUpdate(sql2);
			//查出移交类型
			sb=new StringBuffer("select t.apply_type from OCRM_F_CI_MANAGE_TRANS_TEMP t where t.ASSIGN_USER ='" + userId + "' and t.ID like '" + PKhead + "%'");
			ResultSet resultSet1 = stm.executeQuery(sb.toString());
			resultSet1.next();
			String applyType = resultSet1.getString(1);
			log.info("校验客户号是否为空");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_MANAGE_TRANS_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'核心客户号不能为空,'");
			sb.append(" where ASSIGN_USER='" + userId + "' and (core_no is null or core_no='' or core_no='null')");
			stm.executeUpdate(sb.toString());
			log.info("校验客户号是否正确");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_MANAGE_TRANS_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'该客户核心客户号不存在,' ");
			sb.append(" where ASSIGN_USER='"+userId+"' and core_no not in (");
			sb.append(" select t.core_no");
			sb.append(" from ACRM_F_CI_CUSTOMER t where t.core_no is not null");
			sb.append(" )");
			stm.executeUpdate(sb.toString());
			log.info("校验客户类型不能为空");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_MANAGE_TRANS_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户类型不能为空,' ");
			sb.append(" where ASSIGN_USER='"+userId+"' and (cust_type is null or cust_type='' or cust_type='null') ");
			stm.executeUpdate(sb.toString());
			log.info("校验客户类型是否正确");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_MANAGE_TRANS_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户类型不正确,' ");
			sb.append(" where ASSIGN_USER='"+userId+"' and (cust_type!='1' and cust_type!='2')");
			stm.executeUpdate(sb.toString());
			log.info("校验客户经理编号不能为空");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_MANAGE_TRANS_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户经理编号不能为空,' ");
			sb.append(" where ASSIGN_USER='"+userId+"' and (mgr_id is null or mgr_id='' or mgr_id='null') ");
			stm.executeUpdate(sb.toString());
			log.info("校验客户经理编号是否正确");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_MANAGE_TRANS_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户经理编号不存在,' ");
			sb.append(" where ASSIGN_USER='"+userId+"' and mgr_id not in (");
			sb.append(" select t.mgr_id");
			sb.append(" from OCRM_F_CI_BELONG_CUSTMGR t where t.mgr_id is not null");
			sb.append(" )");
			stm.executeUpdate(sb.toString());
			log.info("校验接受客户经理编号不能为空");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_MANAGE_TRANS_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'接受客户经理编号不能为空,' ");
			sb.append(" where ASSIGN_USER='"+userId+"' and (t_mgr_id is null or t_mgr_id='' or t_mgr_id='null') ");
			stm.executeUpdate(sb.toString());
			log.info("校验接受客户经理编号是否正确");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_MANAGE_TRANS_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'接受客户经理编号不存在,' ");
			sb.append(" where ASSIGN_USER='"+userId+"' and t_mgr_id not in (");
			sb.append(" select t.mgr_id");
			sb.append(" from OCRM_F_CI_BELONG_CUSTMGR t where t.mgr_id is not null");
			sb.append(" )");
			stm.executeUpdate(sb.toString());
			log.info("校验工作交接日期是否为空");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_MANAGE_TRANS_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'工作交接日期不能为空,' ");
			sb.append(" where ASSIGN_USER='"+userId+"' and (WORK_INTERFIX_DT is null or WORK_INTERFIX_DT='' or WORK_INTERFIX_DT='null')");
			stm.executeUpdate(sb.toString());
			log.info("校验工作交接日期正则表达式");
			String eL="^[0-9]{4}(\\-)[0-9]{2}(\\-)[0-9]{2}";
			sb = new StringBuffer();
			sb.append("select t.WORK_INTERFIX_DT from OCRM_F_CI_MANAGE_TRANS_TEMP t where t.ASSIGN_USER ='" + userId + "' and t.ID like '" + PKhead + "%' ");
			ResultSet rs1 = stm.executeQuery(sb.toString());
			rs1.next();
			String workDt = rs1.getString(1);
			Pattern pattern = Pattern.compile(eL);
			Matcher matcher = pattern.matcher(workDt);
			boolean b  = matcher.matches();
			if(b==false){
				sb = new StringBuffer();
				sb.append("update OCRM_F_CI_MANAGE_TRANS_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'工作交接日期格式不正确,' ");
				sb.append("  where ASSIGN_USER='"+userId+"' and  WORK_INTERFIX_DT='"+workDt+"' ");
				stm.executeUpdate(sb.toString());
			}
			log.info("校验客户移交类别不能为空");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_MANAGE_TRANS_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户移交类别不能为空,' ");
			sb.append(" where ASSIGN_USER='"+userId+"' and (hand_kind is null or hand_kind='' or hand_kind='null') ");
			stm.executeUpdate(sb.toString());
			log.info("校验客户移交类别是否正确");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_MANAGE_TRANS_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户移交类别不正确,' ");
			sb.append(" where ASSIGN_USER='"+userId+"' and (hand_kind!='1' and hand_kind!='2')");
			stm.executeUpdate(sb.toString());
			log.info("校验同一excel中只能有一个客户类型");
			sb = new StringBuffer();
			sb.append("select count(distinct cust_type) from OCRM_F_CI_MANAGE_TRANS_TEMP where ASSIGN_USER='"+userId+"'");
			ResultSet rscustType = stm.executeQuery(sb.toString());
			rscustType.next();
			int count=rscustType.getInt(1);
			if(count>1){
				sb=new StringBuffer();
				sb.append("update OCRM_F_CI_MANAGE_TRANS_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户类型需要相同,' ");
				sb.append(" where ASSIGN_USER='"+userId+"'");
				stm.executeUpdate(sb.toString());
			}
			log.info("校验工作移交原因是否为空");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_MANAGE_TRANS_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户移交原因不能为空,' ");
			sb.append(" where ASSIGN_USER='"+userId+"' and (hand_over_reason is null or hand_over_reason='' or hand_over_reason='null') ");
			stm.executeUpdate(sb.toString());
			log.info("校验客户与客户经理是否匹配");
			sb = new StringBuffer();
			sb.append("update OCRM_F_CI_MANAGE_TRANS_TEMP set IMP_STATUS=0,IMP_MSG=IMP_MSG||'客户不属于这个客户经理,' ");
			sb.append(" where ASSIGN_USER='"+userId+"' and cust_id not in (");
			sb.append(" select t1.cust_id");
			sb.append(" from OCRM_F_CI_BELONG_CUSTMGR t1,OCRM_F_CI_MANAGE_TRANS_TEMP t2 where t1.cust_id=t2.cust_id and t1.mgr_name=t2.mgr_name ");
			sb.append(" )");
			stm.executeUpdate(sb.toString());
			log.info("检查完毕，判断所有数据是否合法");
			sb = new StringBuffer("select count(1) FROM OCRM_F_CI_MANAGE_TRANS_TEMP t where t.ASSIGN_USER ='" + userId + "' and t.ID like '" + PKhead + "%' and t.imp_status='0' ");
			log.info(sb.toString());
			ResultSet rs = stm.executeQuery(sb.toString());
			rs.next();
			int errCount = rs.getInt(1);
			if(errCount>0){
				aUser.putAttribute(BACK_IMPORT_ERROR, "导入数据存在错误，请修改确认之后重新导入");												
				throw new BizException(1, 0, "10001", "导入数据存在错误，请修改确认之后重新导入");
			}else{
				Statement statement  = conn.createStatement();
				Statement statement1  = conn.createStatement();
				Statement statement2  = conn.createStatement();
				if(applyType.equals("6")){
					try {
						//被移交客户与接受客户经理对应关系信息
						sb = new StringBuffer(
								"select distinct t.t_mgr_id,a.org_id,b.id,t.cust_id "
								+ " from OCRM_F_CI_MANAGE_TRANS_TEMP t "
								+ " left join admin_auth_account a on t.t_mgr_id = a.account_name"
								+ " left join OCRM_F_CI_BELONG_CUSTMGR b on t.cust_id=b.cust_id "
								+ " where t.ASSIGN_USER ='" + userId + "' and t.ID like '" + PKhead + "%'");
						ResultSet result1 = statement1.executeQuery(sb.toString());
						List<String[]> list = new ArrayList<String[]>();
						while(result1.next()){
							String[] a={result1.getString(1),result1.getString(2),result1.getString(3),result1.getString(4)};
							list.add(a);
						}
						//更新ECIF客户归属客户经理信息
						for(int i = 0;i<list.size();i++){
							String[] data = list.get(i);
							String reqMsg = TranCrmToEcifMgr(data[3], data[0], data[2], null,userId);
							
							TxData txData = new TxData();
							txData.setReqMsg(reqMsg);
							txData.setTxCnName("修改客户归属信息");
							txData.setTxCode("updateBelong");
							Transaction trans = new EcifTransaction(txData);
							txData = trans.process();
							TxLog txLog = trans.getTxLog();
							commonService.save(txLog);
							
							String status = txData.getStatus();
							if (!"success".equals(status)) {
								throw new BizException(1, 0, "0000", txData.getMsg(), new Object[0]);
							}
						}
						//更新ECIF客户归属机构信息
						for(int i = 0;i<list.size();i++){
							String[] data = list.get(i);
							String reqMsg=TranCrmToEcifOrg(data[3], data[1], data[2], null,userId);
							TxData txData = new TxData();
							txData.setReqMsg(reqMsg);
							txData.setTxCnName("修改客户归属信息");
							txData.setTxCode("updateBelong");
							Transaction trans = new EcifTransaction(txData);
							txData = trans.process();
							TxLog txLog = trans.getTxLog();
							commonService.save(txLog);
							
							String status = txData.getStatus();
							if (!"success".equals(status)) {
								throw new BizException(1, 0, "0000", txData.getMsg(), new Object[0]);
							}
						}
						conn.setAutoCommit(false);
						log.info("查找此次导入的接收客户经理ID(去重)");
						sb = new StringBuffer(
								"select distinct t.t_mgr_id,a.user_name,a.org_id,b.org_name,t.HAND_KIND,t.HAND_OVER_REASON,t.APPLY_TYPE,t.WORK_INTERFIX_DT"
								+ " from OCRM_F_CI_MANAGE_TRANS_TEMP t "
								+ " left outer join admin_auth_account a on t.t_mgr_id = a.account_name"
								+ " left outer join admin_auth_org b on a.org_id = b.org_id and b.app_id = 62"
								+ " where t.ASSIGN_USER ='" + userId + "' and t.ID like '" + PKhead + "%'");
						ResultSet result = statement.executeQuery(sb.toString());
						while(result.next()){
							log.info("向客户移交申请表（OCRM_F_CI_TRANS_APPLY）中插入申请信息");
							String insertApplySql = " insert into OCRM_F_CI_TRANS_APPLY(APPLY_NO,USER_ID,USER_NAME,T_MGR_ID,T_MGR_NAME,T_ORG_ID,T_ORG_NAME,APPLY_DATE,HAND_KIND,HAND_OVER_REASON,APPROVE_STAT,APPLY_TYPE,WORK_INTERFIX_DT,TYPE)"
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
									+ " '3' ,"
									+ " '"+result.getString(7)+"',"
									+ " to_date('"+result.getString(8)+"','yyyy-MM-dd'),"
									+ " '0' "
									+ " from dual";
								log.info("insertApplySql: 【" + insertApplySql + "】");
								stm.executeUpdate(insertApplySql);
								log.info("查找刚插入客户移交申请表（OCRM_F_CI_TRANS_APPLY）中的申请编号和接收机构号等信息");
								sb = new StringBuffer("select t.APPLY_NO,t.T_MGR_ID,t.APPLY_TYPE,to_char(t.WORK_INTERFIX_DT,'yyyy-MM-dd'),t.HAND_KIND,t.HAND_OVER_REASON,t.T_MGR_NAME,t.T_ORG_ID,t.T_ORG_NAME from OCRM_F_CI_TRANS_APPLY t where t.USER_ID ='" + userId + "' and t.APPLY_DATE=TRUNC(SYSDATE) order by t.APPLY_NO desc");
								ResultSet resultSet = stm.executeQuery(sb.toString());
								resultSet.next();
								String applyNo = resultSet.getString(1);
								String tMgrId = resultSet.getString(2);
//								String applyType = resultSet.getString(3);
								String workInterFixDT = resultSet.getString(4);
								String handKind = resultSet.getString(5);
								String handOverReason = resultSet.getString(6);
								String tMgrName = resultSet.getString(7);
								String tOgrId = resultSet.getString(8);
								String tOgrName = resultSet.getString(9);
								/*sb = new StringBuffer("select m.CUST_ID,c.CUST_NAME,m.MGR_ID,a.USER_NAME,a.ORG_ID,b.ORG_NAME,d.ID,m.MAIN_TYPE,e.institution_code "
										+ " from OCRM_F_CI_MANAGE_TRANS_TEMP m,ADMIN_AUTH_ACCOUNT a,admin_auth_org b,ACRM_F_CI_CUSTOMER c,OCRM_F_CI_BELONG_CUSTMGR d,OCRM_F_CI_BELONG_ORG e "
										+ " where m.CUST_ID=c.CUST_ID and m.CUST_ID=d.CUST_ID and a.org_id = b.org_id and m.MGR_ID=a.ACCOUNT_NAME and e.cust_id=m.cust_id and m.ID like '" + PKhead + "%' and m.T_MGR_ID='"+tMgrId+"' and m.ASSIGN_USER='"+userId+"' and m.WORK_INTERFIX_DT='"+workInterFixDT+"' and m.HAND_KIND='"+handKind+"' and m.HAND_OVER_REASON='"+handOverReason+"' and m.APPLY_TYPE='"+applyType+"'");
								*/
								//查询每个接收客户经理与待接收客户之间的关系信息
								sb = new StringBuffer(
										"select m.CUST_ID,c.CUST_NAME,m.MGR_ID,a.USER_NAME,a.ORG_ID,b.ORG_NAME,d.ID,m.MAIN_TYPE,e.institution_code "
										+ " FROM OCRM_F_CI_MANAGE_TRANS_TEMP M "
										+ " LEFT JOIN ADMIN_AUTH_ACCOUNT          A ON M.MGR_ID = A.ACCOUNT_NAME"
										+ " LEFT JOIN ADMIN_AUTH_ORG              B ON A.ORG_ID = B.ORG_ID"
										+ " LEFT JOIN ACRM_F_CI_CUSTOMER          C ON M.CUST_ID = C.CUST_ID"
										+ " LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR    D ON M.CUST_ID = D.CUST_ID"
										+ " LEFT JOIN OCRM_F_CI_BELONG_ORG        E ON E.CUST_ID = M.CUST_ID"
										+ " where m.ID like '" + PKhead + "%' and m.T_MGR_ID='"+tMgrId+"' and m.ASSIGN_USER='"+userId+"' and m.WORK_INTERFIX_DT='"+workInterFixDT+"' and m.HAND_KIND='"+handKind+"' and m.HAND_OVER_REASON='"+handOverReason+"' and m.APPLY_TYPE='"+applyType+"'"
								);
								ResultSet resultSet2=statement2.executeQuery(sb.toString());
								while(resultSet2.next()){
									String custId=resultSet2.getString(1);
									String custName=resultSet2.getString(2);
									String mgrId=resultSet2.getString(3);
									String mgrName=resultSet2.getString(4);
									String orgId=resultSet2.getString(5);
									String orgName=resultSet2.getString(6);
									String recordId=resultSet2.getString(7);
									String mainType=resultSet2.getString(8);
									String custOrgId=resultSet2.getString(9); 
									log.info("向移交客户列表（OCRM_F_CI_TRANS_CUST）插入信息");
									String insertCustSql = " insert into OCRM_F_CI_TRANS_CUST(ID,APPLY_NO,RECORD_ID,CUST_ID,CUST_NAME,MGR_ID,MGR_NAME,MAIN_TYPE,INSTITUTION,INSTITUTION_NAME)"
										+ " select id_sequence.nextval,"
										+ " '"+applyNo+"',"
										+ " '"+recordId+"',"
										+ " '"+custId+"',"
										+ " '"+custName+"',"
										+ " '"+mgrId+"',"
										+ " '"+mgrName+"',"
										+ " '"+mainType+"',"
										+ " '"+orgId+"',"
										+ " '"+orgName+"'"
										+ " FROM dual";
									log.info("insertCustSql: 【" + insertCustSql + "】");
									stm.executeUpdate(insertCustSql);
									log.info("在原归属客户经理表（OCRM_F_CI_BELONG_CUSTMGR）中删除原归属人记录");
									
//									conn.setAutoCommit(false);
									String deleteCustMgr="delete from OCRM_F_CI_BELONG_CUSTMGR t where t.ID='"+recordId+"'";
									log.info(deleteCustMgr);
									stm.executeUpdate(deleteCustMgr);
									log.info("向客户归属表（OCRM_F_CI_BELONG_CUSTMGR）中插入信息");
									String insertCustMgrSql = "insert into OCRM_F_CI_BELONG_CUSTMGR(ID,Cust_Id,Mgr_Id,Mgr_Name,Check_Right,Maintain_Right,Assign_User,Assign_Username,Assign_Date,Institution,Institution_Name,Effect_Date)"
											+ " select commonsequnce.nextval,"
											+ " '"+custId+"',"
											+ " '"+tMgrId+"',"
											+ " '"+tMgrName+"',"
											+ " '1',"
											+ " '1',"
											+ " '"+userId+"',"
											+ " '"+userName+"',"
											+ " TRUNC(SYSDATE),"
											+ " '"+tOgrId+"',"
											+ " '"+tOgrName+"',"
											+ " TRUNC(SYSDATE)"
											+ " from dual";
									log.info("insertCustMgrSql: 【" + insertCustMgrSql + "】");
									stm.executeUpdate(insertCustMgrSql);
									log.info("向归属调整历史表（OCRM_F_CI_BELONG_HIST）中插入信息");
									String insertHistSql = "insert into OCRM_F_CI_BELONG_HIST(ID,Before_Inst_Code,After_Inst_Code,Before_Mgr_Id,Before_Inst_Name,After_Mgr_Name,Assign_User,Assign_Date,Assign_Username,Before_Mgr_Name,Work_Tran_Date,Work_Tran_Level,Work_Tran_Reason,Cust_Id,After_Mgr_Id,After_Inst_Name)"
											+ " select id_sequence.nextval,"
											+ " '"+orgId+"',"
											+ " '"+tOgrId+"',"
											+ " '"+mgrId+"',"
											+ " '"+orgName+"',"
											+ " '"+tMgrName+"',"
											+ " '"+userId+"',"
											+ " TRUNC(SYSDATE),"
											+ " '"+userName+"',"
											+ " '"+mgrName+"',"
											+ " to_date('"+workInterFixDT+"','yyyy-MM-dd'),"
											+ " '"+handKind+"',"
											+ " '"+handOverReason+"',"
											+ " '"+custId+"',"
											+ " '"+tMgrId+"',"
											+ " '"+tOgrName+"'"
											+ " from dual";
									log.info("insertHistSql: 【" + insertHistSql + "】");
									stm.executeUpdate(insertHistSql);
									log.info("在归属机构表中（OCRM_F_CI_BELONG_ORG）删除原有信息");
									String deleteOrg="delete from OCRM_F_CI_BELONG_ORG t where t.institution_Code='"+custOrgId+"' and t.cust_Id='"+custId+"'";
									log.info(deleteOrg);
									stm.executeUpdate(deleteOrg);
									log.info("向归属机构表中（OCRM_F_CI_BELONG_ORG）插入信息");
									String insertOrgSql="insert into OCRM_F_CI_BELONG_ORG(ID,Cust_Id,Institution_Code,Institution_Name,Assign_User,Assign_Date,Assign_Username)"
											+ " select commonsequnce.nextval,"
											+ " '"+custId+"',"
											+ " '"+tOgrId+"',"
											+ " '"+tOgrName+"',"
											+ " '"+userId+"',"
											+ " TRUNC(SYSDATE),"
											+ " '"+userName+"'"
											+ " from dual";
									log.info("insertOrgSql: 【" + insertOrgSql + "】");
									stm.executeUpdate(insertOrgSql);
								}
						}
						conn.commit();
					} catch (Exception e) {
						conn.rollback();
						aUser.putAttribute("BACK_RUN_ERROR", true);//程序运行错误抛异常
						throw e;
					}
				}else if(applyType.equals("7")){
					try {
							sb = new StringBuffer(
									"select distinct t.t_mgr_id,a.org_id,b.id,t.cust_id,t.mgr_id "
									+ " from OCRM_F_CI_MANAGE_TRANS_TEMP t "
									+ " left join admin_auth_account a on t.t_mgr_id = a.account_name"
									+ " left join OCRM_F_CI_BELONG_CUSTMGR b on t.cust_id=b.cust_id "
									+ " where t.ASSIGN_USER ='" + userId + "' and t.ID like '" + PKhead + "%'");
							ResultSet result1 = statement1.executeQuery(sb.toString());
							List<String[]> list = new ArrayList<String[]>();
							while(result1.next()){
								String[] a={result1.getString(1),result1.getString(2),result1.getString(3),result1.getString(4),result1.getString(5)};
								list.add(a);
							}
							//更新ECIF客户主办客户经理信息
							for(int i = 0;i<list.size();i++){
								String[] data = list.get(i);
								String reqMsg=TranCrmToEcifMgr(data[3], data[0], data[2], "1",userId);
								// 先处理主办关系
								TxData txData = new TxData();
								txData.setReqMsg(reqMsg);
								txData.setTxCnName("修改客户归属信息");
								txData.setTxCode("updateBelong");
								Transaction trans = new EcifTransaction(txData);
								txData = trans.process();
								TxLog txLog = trans.getTxLog();
								commonService.save(txLog);
								
								String status = txData.getStatus();
								if (!"success".equals(status)) {
									throw new BizException(1, 0, "0000", txData.getMsg(), new Object[0]);
								}
							}
							//更新ECIF客户归属机构信息
							for(int i = 0;i<list.size();i++){
								String[] data = list.get(i);
								String reqMsg=TranCrmToEcifOrg(data[3], data[1], data[2], null,userId);
								TxData txData = new TxData();
								txData.setReqMsg(reqMsg);
								txData.setTxCnName("修改客户归属信息");
								txData.setTxCode("updateBelong");
								Transaction trans = new EcifTransaction(txData);
								txData = trans.process();
								TxLog txLog = trans.getTxLog();
								commonService.save(txLog);
								
								String status = txData.getStatus();
								if (!"success".equals(status)) {
									throw new BizException(1, 0, "0000", txData.getMsg(), new Object[0]);
								}
							}
							conn.setAutoCommit(false);
							log.info("查找此次导入的接收客户经理ID(去重)");
							sb = new StringBuffer(
									"select distinct t.t_mgr_id,a.user_name,a.org_id,b.org_name,t.HAND_KIND,t.HAND_OVER_REASON,t.APPLY_TYPE,t.WORK_INTERFIX_DT"
									+ " from OCRM_F_CI_MANAGE_TRANS_TEMP t "
									+ " left outer join admin_auth_account a on t.t_mgr_id = a.account_name"
									+ " left outer join admin_auth_org b on a.org_id = b.org_id and b.app_id = 62"
									+ " where t.ASSIGN_USER ='" + userId + "' and t.ID like '" + PKhead + "%'");
							ResultSet result = statement.executeQuery(sb.toString());
							while(result.next()){
								log.info("向客户移交申请表（OCRM_F_CI_TRANS_APPLY）中插入申请信息");
								String insertApplySql = " insert into OCRM_F_CI_TRANS_APPLY(APPLY_NO,USER_ID,USER_NAME,T_MGR_ID,T_MGR_NAME,T_ORG_ID,T_ORG_NAME,APPLY_DATE,HAND_KIND,HAND_OVER_REASON,APPROVE_STAT,APPLY_TYPE,WORK_INTERFIX_DT)"
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
										+ " '3' ,"
										+ " '"+result.getString(7)+"',"
										+ " to_date('"+result.getString(8)+"','yyyy-MM-dd')"
										+ " from dual";
								log.info("insertApplySql: 【" + insertApplySql + "】");
								stm.executeUpdate(insertApplySql);
								log.info("查找刚插入客户移交申请表（OCRM_F_CI_TRANS_APPLY）中的申请编号和接收机构号等信息");
								sb = new StringBuffer("select t.APPLY_NO,t.T_MGR_ID,t.APPLY_TYPE,to_char(t.WORK_INTERFIX_DT,'yyyy-MM-dd'),t.HAND_KIND,t.HAND_OVER_REASON,t.T_MGR_NAME,t.T_ORG_ID,t.T_ORG_NAME from OCRM_F_CI_TRANS_APPLY t where t.USER_ID ='" + userId + "' and t.APPLY_DATE=TRUNC(SYSDATE) order by t.APPLY_NO desc");
								ResultSet resultSet = stm.executeQuery(sb.toString());
								resultSet.next();
								String applyNo = resultSet.getString(1);
								String tMgrId = resultSet.getString(2);
//								String applyType = resultSet.getString(3);
								String workInterFixDT = resultSet.getString(4);
								String handKind = resultSet.getString(5);
								String handOverReason = resultSet.getString(6);
								String tMgrName = resultSet.getString(7);
								String tOgrId = resultSet.getString(8);
								String tOgrName = resultSet.getString(9);
								/*sb = new StringBuffer("select m.CUST_ID,c.CUST_NAME,m.MGR_ID,a.USER_NAME,a.ORG_ID,b.ORG_NAME,d.ID,m.MAIN_TYPE,e.institution_code "
										+ " from OCRM_F_CI_MANAGE_TRANS_TEMP m,ADMIN_AUTH_ACCOUNT a,admin_auth_org b,ACRM_F_CI_CUSTOMER c,OCRM_F_CI_BELONG_CUSTMGR d,OCRM_F_CI_BELONG_ORG e"
										+ " where m.CUST_ID=c.CUST_ID and m.CUST_ID=d.CUST_ID and a.org_id = b.org_id and m.MGR_ID=a.ACCOUNT_NAME and e.cust_id=m.cust_id and m.ID like '" + PKhead + "%' and m.T_MGR_ID='"+tMgrId+"' and m.ASSIGN_USER='"+userId+"' and m.WORK_INTERFIX_DT='"+workInterFixDT+"' and m.HAND_KIND='"+handKind+"' and m.HAND_OVER_REASON='"+handOverReason+"' and m.APPLY_TYPE='"+applyType+"'");
								*/
								//查找本次导入数据里接受客户经理为当前循环的客户经理的所有客户信息
								sb = new StringBuffer(
										"select m.CUST_ID,c.CUST_NAME,m.MGR_ID,a.USER_NAME,a.ORG_ID,b.ORG_NAME,d.ID,m.MAIN_TYPE,e.institution_code "
										+ " FROM OCRM_F_CI_MANAGE_TRANS_TEMP M "
										+ " LEFT JOIN ADMIN_AUTH_ACCOUNT          A ON M.MGR_ID = A.ACCOUNT_NAME"
										+ " LEFT JOIN ADMIN_AUTH_ORG              B ON A.ORG_ID = B.ORG_ID"
										+ " LEFT JOIN ACRM_F_CI_CUSTOMER          C ON M.CUST_ID = C.CUST_ID"
										+ " LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR    D ON M.CUST_ID = D.CUST_ID"
										+ " LEFT JOIN OCRM_F_CI_BELONG_ORG        E ON E.CUST_ID = M.CUST_ID"
										+ " where m.ID like '" + PKhead + "%' and m.T_MGR_ID='"+tMgrId+"' and m.ASSIGN_USER='"+userId+"' and m.WORK_INTERFIX_DT='"+workInterFixDT+"' and m.HAND_KIND='"+handKind+"' and m.HAND_OVER_REASON='"+handOverReason+"' and m.APPLY_TYPE='"+applyType+"'"
								);
								log.info(sb.toString());
								ResultSet resultSet2=statement2.executeQuery(sb.toString());
								while(resultSet2.next()){
									String custId=resultSet2.getString(1);
									String custName=resultSet2.getString(2);
									String mgrId=resultSet2.getString(3);
									String mgrName=resultSet2.getString(4);
									String orgId=resultSet2.getString(5);
									String orgName=resultSet2.getString(6);
									String recordId=resultSet2.getString(7);
									String mainType=resultSet2.getString(8);
									String custOrgId=resultSet2.getString(9);
									log.info("向移交客户列表（OCRM_F_CI_TRANS_CUST）插入信息");
									String insertCustSql = " insert into OCRM_F_CI_TRANS_CUST(ID,APPLY_NO,RECORD_ID,CUST_ID,CUST_NAME,MGR_ID,MGR_NAME,MAIN_TYPE,INSTITUTION,INSTITUTION_NAME)"
										+ " select id_sequence.nextval,"
										+ " '"+applyNo+"',"
										+ " '"+recordId+"',"
										+ " '"+custId+"',"
										+ " '"+custName+"',"
										+ " '"+mgrId+"',"
										+ " '"+mgrName+"',"
										+ " '"+mainType+"',"
										+ " '"+orgId+"',"
										+ " '"+orgName+"'"
										+ " FROM dual";
									log.info("insertCustSql: 【" + insertCustSql + "】");
									stm.executeUpdate(insertCustSql);
//									conn.setAutoCommit(false);
									log.info("在客户经理归属表（OCRM_F_CI_BELONG_CUSTMGR）中删除原有数据");
									String deleteCustMgr="delete from OCRM_F_CI_BELONG_CUSTMGR t where t.ID='"+recordId+"'";
									log.info(deleteCustMgr);
									stm.executeUpdate(deleteCustMgr);
									log.info("向客户归属表（OCRM_F_CI_BELONG_CUSTMGR）中插入信息");
									String insertCustMgrSql = "insert into OCRM_F_CI_BELONG_CUSTMGR(ID,Cust_Id,Mgr_Id,Mgr_Name,Main_Type,Check_Right,Maintain_Right,Assign_User,Assign_Username,Assign_Date,Institution,Institution_Name,Effect_Date)"
											+ " select commonsequnce.nextval,"
											+ " '"+custId+"',"
											+ " '"+tMgrId+"',"
											+ " '"+tMgrName+"',"
											+ " '1',"
											+ " '1',"
											+ " '1',"
											+ " '"+userId+"',"
											+ " '"+userName+"',"
											+ " TRUNC(SYSDATE),"
											+ " '"+tOgrId+"',"
											+ " '"+tOgrName+"',"
											+ " TRUNC(SYSDATE)"
											+ " from dual";
									log.info("insertCustMgrSql: 【" + insertCustMgrSql + "】");
									stm.executeUpdate(insertCustMgrSql);
									log.info("向归属调整历史表（OCRM_F_CI_BELONG_HIST）中插入信息");
									String insertHistSql = "insert into OCRM_F_CI_BELONG_HIST(ID,Before_Inst_Code,After_Inst_Code,Before_Mgr_Id,Before_Inst_Name,After_Mgr_Name,Assign_User,Assign_Date,Assign_Username,Before_Mgr_Name,Work_Tran_Date,Work_Tran_Level,Work_Tran_Reason,Cust_Id,Before_Main_Type,After_Main_Type,After_Mgr_Id,After_Inst_Name)"
											+ " select id_sequence.nextval,"
											+ " '"+orgId+"',"
											+ " '"+tOgrId+"',"
											+ " '"+mgrId+"',"
											+ " '"+orgName+"',"
											+ " '"+tMgrName+"',"
											+ " '"+userId+"',"
											+ " TRUNC(SYSDATE),"
											+ " '"+userName+"',"
											+ " '"+mgrName+"',"
											+ " to_date('"+workInterFixDT+"','yyyy-MM-dd'),"
											+ " '"+handKind+"',"
											+ " '"+handOverReason+"',"
											+ " '"+custId+"',"
											+ " '"+mainType+"',"
											+ " '1',"
											+ " '"+tMgrId+"',"
											+ " '"+tOgrName+"'"
											+ " from dual";
									log.info("insertHistSql: 【" + insertHistSql + "】");
									stm.executeUpdate(insertHistSql);
									//重新处理与机构的关系(新的关系统一通过OCRM_F_CI_BELONG_CUSTMGR表确定)
									
									log.info("删除归属机构表中原有的数据");
									String deleteOrgSql = "delete from OCRM_F_CI_BELONG_ORG o where o.CUST_ID='"+custId+"' and o.INSTITUTION_CODE='" + custOrgId + "'";
									log.info(deleteOrgSql);
									stm.executeUpdate(deleteOrgSql);
									/*for(int i = 0;i<list.size();i++){
										//处理与原机构关系
										String[] data = list.get(i);
										String reqMsg=TranCrmToEcifOrg(data[3], data[4], data[2], "1",userId);
										TxData txData = new TxData();
										txData.setReqMsg(reqMsg);
										txData.setTxCnName("修改客户归属信息");
										txData.setTxCode("updateBelong");
										Transaction trans = new EcifTransaction(txData);
										txData = trans.process();
										TxLog txLog = trans.getTxLog();
										commonService.save(txLog);
										
										String status = txData.getStatus();
										if (!"success".equals(status)) {
											throw new BizException(1, 0, "0000", txData.getMsg(), new Object[0]);
										}
										//处理与目标机构主办关系
										String reqMsg2=TranCrmToEcifOrg(data[3], tOgrId, data[2], "1",userId);
										TxData txData2 = new TxData();
										txData2.setReqMsg(reqMsg2);
										txData2.setTxCnName("修改客户归属信息");
										txData2.setTxCode("updateBelong");
										Transaction trans2 = new EcifTransaction(txData2);
										txData2 = trans2.process();
										TxLog txLog2 = trans2.getTxLog();
										commonService.save(txLog2);
										
										String status2 = txData2.getStatus();
										if (!"success".equals(status2)) {
											throw new BizException(1, 0, "0000", txData2.getMsg(), new Object[0]);
										}
									}*/
									//处理客户归属机构业务逻辑
									String insertOrgSql="insert into OCRM_F_CI_BELONG_ORG(ID,Main_Type,Cust_Id,Institution_Code,Institution_Name,Assign_User,Assign_Date,Assign_Username)"
											+ " select commonsequnce.nextval,"
											+ " '1',"
											+ " '"+custId+"',"
											+ " '"+tOgrId+"',"
											+ " '"+tOgrName+"',"
											+ " '"+userId+"',"
											+ " TRUNC(SYSDATE),"
											+ " '"+userName+"'"
											+ " from dual";
									log.info(insertOrgSql);
									stm.executeUpdate(insertOrgSql);
									// 客户移交同步信贷
									// 判断是否是信贷客户
									Statement stmtLN = null;
									ResultSet rsLN = null;
									String sqlLN = null;
									//boolean retMsg = false;
									//String retM = "";
									try {
										stmtLN = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
										sqlLN = "SELECT 1 FROM ACRM_F_CI_CROSSINDEX C WHERE C.CUST_ID ='" + custId + "' AND C.SRC_SYS_NO='LN'";
										rsLN = stmtLN.executeQuery(sqlLN);
										rsLN.last();
										System.out.println("length:" + rsLN.getRow());
										if (rsLN != null && rsLN.getRow() > 0) {
											String reqMsg = TranCrmToLN("",custId,userId,userOrgId,tMgrId,tOgrId,workInterFixDT, handOverReason,mgrId,orgId);
											
											TxData txData = new TxData();
											txData.setReqMsg(reqMsg);
											Transaction trans = new CustTransferSyncLNTransaction(txData);
											txData = trans.process();
											TxLog txLog = trans.getTxLog();
											commonService.save(txLog);
											
											String status = txData.getStatus();
											System.out.println("**********************"+status);
											if (!"success".equals(status)) {
												throw new BizException(1, 0, "0000", txData.getMsg()+">>>>>[客户编号:" + custId + "]", new Object[0]);
											}
										}
									} catch (Exception e) {
										e.printStackTrace();
										if (e instanceof BizException) {
											throw e;
										} else {
											throw new BizException(1, 0, "0000", "主管直接移交失败,程序出错:"+e.getMessage());
										}
									}finally{
										rsLN.close();
										stmtLN.close();
									}
								}
						}
						conn.commit();
					} catch (Exception e) {
						conn.rollback();
						aUser.putAttribute("BACK_RUN_ERROR", true);//程序运行错误抛异常
						throw e;
					}
				}
				statement.close();
				statement1.close();
				statement2.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			aUser.putAttribute(BACK_IMPORT_ERROR, "导入失败，失败原因："+e.getMessage());												
			throw e;
		}finally{
			JdbcUtil.close(null, stm, conn);
		}
	}
	
	public String TranCrmToEcifMgr(String custId, String mgrId, String recordId, String mainType,String userId) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String reqMsg = "";
		try {
			RequestHeader header = new RequestHeader();
			header.setReqSysCd("CRM");
			header.setReqSeqNo(format.format(new Date()));
			header.setReqDt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			header.setReqTm(new SimpleDateFormat("HHmmssSS").format(new Date()));
			header.setDestSysCd("ECIF");
			header.setChnlNo("82");
			header.setBrchNo("503");
			header.setBizLine("209");
			header.setTrmNo("TRM10010");
			header.setTrmIP("127.0.0.1");
			header.setTlrNo(userId);
			StringBuffer sb = new StringBuffer();
			sb.append("<RequestBody>");
			sb.append("<txCode>updateBelong</txCode>");
			sb.append("<txName>修改客户归属信息</txName>");
			sb.append("<authType>1</authType>");
			sb.append("<authCode>1010</authCode>");
			sb.append("<custNo>" + custId + "</custNo>");
			sb.append("<belongBranch></belongBranch>");
			sb.append("<belongManager>");
			sb.append("<custManagerType></custManagerType>");
			sb.append("<validFlag></validFlag>");
			if (mainType != null)
				sb.append("<mainType>" + mainType + "</mainType>");
			else {
				sb.append("<mainType></mainType>");
			}
			sb.append("<startDate></startDate>");
			sb.append("<endDate></endDate>");
			sb.append("<custManagerNo>" + mgrId + "</custManagerNo>");
			sb.append("</belongManager>");
			sb.append("</RequestBody>");
			String Xml = new String(sb.toString().getBytes());
			//req = TransClient.process(header, Xml);
			
			reqMsg = TransClient.parseObject2Xml(header, Xml);
			reqMsg = String.format("%08d", reqMsg.getBytes("GBK").length) + reqMsg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 0, "0000", "拼接报文失败，失败原因："+e.getMessage(), new Object[0]);
		}
		return reqMsg;
	}
	
	public String TranCrmToEcifOrg(String custId, String orgId, String recordId, String mainType,String userId) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String reqMsg = "";
		try {
			RequestHeader header = new RequestHeader();
			header.setReqSysCd("CRM");
			header.setReqSeqNo(format.format(new Date()));
			header.setReqDt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			header.setReqTm(new SimpleDateFormat("HHmmssSS").format(new Date()));
			header.setDestSysCd("ECIF");
			header.setChnlNo("82");
			header.setBrchNo("503");
			header.setBizLine("209");
			header.setTrmNo("TRM10010");
			header.setTrmIP("127.0.0.1");
			header.setTlrNo(userId);
			StringBuffer sb = new StringBuffer();
			sb.append("<RequestBody>");
			sb.append("<txCode>updateBelong</txCode>");
			sb.append("<txName>修改客户归属信息</txName>");
			sb.append("<authType>1</authType>");
			sb.append("<authCode>1010</authCode>");
			sb.append("<custNo>" + custId + "</custNo>");
			sb.append("<belongManager></belongManager>");
			sb.append("<belongBranch>");
			sb.append("<belongBranchType></belongBranchType>");
			sb.append("<validFlag></validFlag>");
			sb.append("<startDate></startDate>");
			sb.append("<endDate></endDate>");
			sb.append("<belongBranchNo>" + orgId + "</belongBranchNo>");
			if (mainType != null)
				sb.append("<mainType>" + mainType + "</mainType>");
			else {
				sb.append("<mainType></mainType>");
			}
			sb.append("</belongBranch>");
			sb.append("</RequestBody>");
			String Xml = new String(sb.toString().getBytes());
			//req = TransClient.process(header, Xml);
			reqMsg = TransClient.parseObject2Xml(header, Xml);
			reqMsg = String.format("%08d", reqMsg.getBytes("GBK").length) + reqMsg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 0, "0000", "拼接报文失败，失败原因："+e.getMessage(), new Object[0]);
		}
		return reqMsg;
	}
	
	/*public Date getEffectDate() throws ParseException {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(2);
		int year = cal.get(1);
		if (month == 11) {
			month = 1;
			year++;
		} else {
			month += 2;
		}
		String toDate = year + "-" + (month > 9 ? Integer.valueOf(month) : new StringBuilder("0").append(month).toString()) + "-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(toDate);
		return date;
	}*/
	
	/**
	 * 客户移交同步信贷
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String TranCrmToLN(String appleType, String custId, String userId, String userOrg, String mgrId, String orgId, String applyDate, String handOverReason, String sMgrId, String sOrgId) {
		String reqMsg = "";
		try {
			StringBuffer custInfoList = new StringBuffer("");// 待移交的客户信息集合
			custInfoList.append("                 <CusInfoList>\n");
			custInfoList.append("                    <CusInfo>\n");
			if (handOverReason == null || handOverReason.equals("")) {
				handOverReason = "无";
			}
			custInfoList.append("                       <cus_id>" + custId + "</cus_id>\n");
			custInfoList.append("                       <handover_type>10</handover_type>\n");// 业务类型 默认为：10 客户资料，其他类型尚未使用
			custInfoList.append("                    </CusInfo>\n");
			custInfoList.append("                 </CusInfoList>\n");
			// CRM系统与信贷系统用户编码规则不一致，需要转换。CRM:511N1456,信贷：5111456
			String lnInputId = chargeUserIdForLN(userId);
			String lnHandoverId = chargeUserIdForLN(sMgrId);
			String lnMgrId = chargeUserIdForLN(mgrId);
			String orgType = "";
			if (appleType != null && appleType.equals("3")) {
				orgType = "10";
			} else {
				orgType = "21";
			}
			// 组装请求报文
			StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
			sb.append("<TransBody>\n");
			sb.append("  <RequestHeader>\n");
			sb.append("      <DestSysCd>LN</DestSysCd>\n");
			sb.append("  </RequestHeader>\n");
			sb.append("  <RequestBody>\n");
			sb.append("      <Packet>\n");
			sb.append("         <Data>\n");
			sb.append("           <Req>\n");
			sb.append("                 <area_code>1</area_code>\n");// 移交方式（必填） 1：移出客户(默认)；2：转入客户;
			sb.append("                 <org_type>" + orgType + "</org_type>\n");// 移交范围(按机构)（必填） 10：支行内移交；21：跨支行移交
			sb.append("                 <handover_mode>2</handover_mode>\n");// 移交内容（必填） 2：客户与业务移交（默认）
			sb.append("                 <handover_scope>1</handover_scope>\n");// 移交范围(按客户经理)（必填） 1：单个客户移交（指定客户,个数有限制不能超出报文长度）；2：按客户经理所有客户
			sb.append(custInfoList.toString());// 待移交客户信息
			sb.append("                 <handover_br_id>" + sOrgId + "</handover_br_id>\n");// 被移出客户经理机构（必填）
			sb.append("                 <handover_id>" + lnHandoverId + "</handover_id>\n");// 被移出客户经理编号（必填）
			sb.append("                 <receiver_br_id>" + orgId + "</receiver_br_id>\n");// 接收人客户经理机构（必填）
			sb.append("                 <receiver_id>" + lnMgrId + "</receiver_id>\n");// 接收人客户经理编号（必填）
			sb.append("                 <supervise_br_id></supervise_br_id>\n");// 监交机构（非必填）
			sb.append("                 <supervise_id></supervise_id>\n");// 监交人（非必填）
			sb.append("                 <handover_detail>" + handOverReason + "</handover_detail>\n");// 移交说明（必填）
			sb.append("                 <input_id>" + lnInputId + "</input_id>\n");// 申请人编号（必填）
			sb.append("                 <input_br_id>" + userOrg + "</input_br_id>\n");// 申请人机构（必填）
			sb.append("                 <input_date>" + applyDate + "</input_date>\n");// 申请日期（必填）
			sb.append("           </Req>\n");
			sb.append("           <Pub>\n");
			sb.append("               <prcscd>CusHandoverByCrm</prcscd>\n");
			sb.append("           </Pub>\n");
			sb.append("         </Data>\n");
			sb.append("     </Packet>\n");
			sb.append("   </RequestBody>\n");
			sb.append("</TransBody>\n");
			StringBuffer sbReq = new StringBuffer();
			sbReq.append(String.format("%08d", sb.toString().getBytes("GBK").length));
			sbReq.append(sb.toString());
			System.out.println("requestToLN:" + sbReq.toString());
			// 调用信贷客户移交 接口,得到返回报文。
			//req = TransClient.processLN(sbReq.toString());// 调用信贷
			reqMsg = sbReq.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 0, "0000", "拼接报文失败，失败原因："+e.getMessage(), new Object[0]);
		}
		return reqMsg;
	}
			
	// CRM系统与信贷系统用户编码规则不一致
	public String chargeUserIdForLN(String userid) {
		String useridForLN = "";
		if (userid.toUpperCase().equals("ADMIN")) {
			useridForLN = userid;
		} else {
			useridForLN = userid.substring(0, 3) + userid.substring(4, 8);
		}
		return useridForLN;
	}
}
