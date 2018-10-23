package com.ecc.echain.wf;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.client.TransClient;

/***
 * 客户移交流程处理
 * @author luyueyue
 * 注：可增加相应的拼装报文方法：
 * 1、删除归属机构、
 * 2、删除归属客户经理、
 * 3、插入归属机构、
 * 4、插入归属客户经理
 * 5、发送ECIF交易报文方法
 * 上述方法可用于，客户经理当前类及主管批量移交类
 */
public class TransHandler extends EChainCallbackCommon{
	
	/**
	 * 获取次月第一天
	 * @return yyyy-MM-dd
	 */
	public String getEffectDate(){
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		if(month == 11){
			month = 1;
			year = year +1;
		}else{
			month = month+2;
		}
		String toDate = year+"-"+(month>9?month:("0"+month))+"-01";
		return toDate;
	}
	
	/**
	 * 对私跨区域移交  通过处理
	 * @param vo
	 * @throws SQLException
	 */
	public void endY2(EVO vo) throws Exception{
		endY1(vo);
	}
	//日期比较
	public int compareDate(Date beginDate){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		String bfDate = fmt.format(beginDate);
		return fmt.format(new Date()).compareTo(bfDate) ;
	}
	
	/**
	 * 对私区域内移交   通过处理
	 * 代码中涉及：/////////////////////////////////注释的地方都是要增加与ECIF处理的地方
	 * 特别注意：报文先增加的一定要先执行，此报文有执行顺序上的关系
	 * @param vo
	 * @throws SQLException
	 */
	public void endY1(EVO vo) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		try {
			conn = vo.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			
			 SQL = "select c.cust_id,a.t_org_id,a.t_mgr_id,c.main_type,a.WORK_INTERFIX_DT " +
				" from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY  a on c.apply_no = a.apply_no where c.apply_no='"+instanceids[1]+"'";
			 Result result=querySQL(vo);
			 String custId = "";
			 String mgrId = "";
			 String orgId = "";
			 Date beginDate = null;
			 for (SortedMap item : result.getRows()){
				 custId = item.get("CUST_ID").toString();
				 mgrId = item.get("T_MGR_ID").toString();
				 orgId = item.get("T_ORG_ID").toString();
				 beginDate = (Date) item.get("WORK_INTERFIX_DT");
				 
				 //1 ： 如果 复核日期早于生效日期  这里不处理 待批处理  2：复核日期晚于于生效日期  现在生效
				 if(compareDate(beginDate) < 0){//暫不生效
					//修改状态
					sql = " update OCRM_F_CI_TRANS_CUST set STATE ='1' where APPLY_NO = "+instanceids[1]+"  AND CUST_ID = '"+custId+"'";
					stmt.execute(sql);
					continue;
				 }
				 
				 /***
				  * 客户归属客户经理
				  */
				 String responseXml = TranCrmToEcifMgr(custId,mgrId,null,vo);
				 boolean responseFlag;
				 responseFlag = doResXms(responseXml);
				 if(!responseFlag){
					 throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
				 }
				 /**
				  * 客户归属机构
				  */
				 responseXml = TranCrmToEcifOrg(custId,orgId,null,vo);
				 responseFlag = doResXms(responseXml);
				 if(!responseFlag){
					 throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
				 }
			 }
			 
			//修改状态
			sql = " update OCRM_F_CI_TRANS_APPLY set APPROVE_STAT ='2' where APPLY_NO = "+instanceids[1]+" ";
			stmt.execute(sql);
			
			//实现客户关系转移
			//1.删除原有关系
			////////////////////////////////////
			//循环增加删除归属客户经理报文
			////////////////////////////////////
			 if(compareDate(beginDate) >=0){
				 
				//修改状态
				sql = " update OCRM_F_CI_TRANS_CUST set STATE ='0' where APPLY_NO = "+instanceids[1]+" ";
				stmt.execute(sql);
				
				//复核日期晚于于生效日期，生效日期改为最新日期
				sql = " update OCRM_F_CI_TRANS_APPLY set work_interfix_dt =to_date(to_char(sysdate,'yyyy/MM/dd'),'yyyy/MM/dd') where APPLY_NO = "+instanceids[1]+" ";
				stmt.execute(sql);
				 
				sql = " delete from OCRM_F_CI_BELONG_CUSTMGR where CUST_ID in ( select CUST_ID from OCRM_F_CI_TRANS_CUST where APPLY_NO='"+instanceids[1]+"')";
				stmt.execute(sql);
				
				//2.新增归属关系 并写入历史表
				////////////////////////////////////
				//循环增加插入归属客户经理报文
				////////////////////////////////////
				sql = " insert into OCRM_F_CI_BELONG_CUSTMGR " +
						"(ID,CUST_ID,MGR_ID,MAIN_TYPE,MAINTAIN_RIGHT,CHECK_RIGHT,ASSIGN_USER,ASSIGN_USERNAME,ASSIGN_DATE,INSTITUTION,INSTITUTION_NAME,MGR_NAME,EFFECT_DATE) " +
						"select ID_SEQUENCE.NEXTVAL,c.cust_id,a.t_mgr_id,'','1','1',a.user_id," +
						"a.user_name,a.APPLY_DATE,a.t_org_id,a.t_org_name,a.t_mgr_name,to_date('"+getEffectDate()+"','yyyy-mm-dd') " +
						" from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY  a on c.apply_no = a.apply_no where c.apply_no='"+instanceids[1]+"'";
				stmt.execute(sql);
				
				sql = "insert into OCRM_F_CI_BELONG_HIST " +
						"(BEFORE_INST_CODE,AFTER_INST_CODE,BEFORE_MGR_ID,BEFORE_INST_NAME,AFTER_MGR_ID,ASSIGN_USER,AFTER_INST_NAME,ASSIGN_DATE,ID,BEFORE_MGR_NAME,AFTER_MGR_NAME,BEFORE_MAIN_TYPE,AFTER_MAIN_TYPE,ASSIGN_USERNAME,ETL_DATE,WORK_TRAN_REASON,WORK_TRAN_LEVEL,WORK_TRAN_DATE,CUST_ID) " +
						"select  c.INSTITUTION,a.t_org_id,c.mgr_id,c.INSTITUTION_NAME,a.t_mgr_id,a.user_id,a.t_org_name," +
						"a.apply_date,ID_SEQUENCE.NEXTVAL,c.mgr_name,a.t_mgr_name,'','',a.user_name,null,a.HAND_OVER_REASON," +
						"a.HAND_KIND,a.WORK_INTERFIX_DT,c.cust_id " +
						"from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY  a on c.apply_no = a.apply_no where c.apply_no='"+instanceids[1]+"'";
				stmt.execute(sql);
				
				//重新处理机构关系  删除原关系，建立新的关系
				//2.删除原关系
				////////////////////////////////////
				//循环增加删除归属客户机构报文
				////////////////////////////////////
				sql = " delete from OCRM_F_CI_BELONG_ORG where INSTITUTION_CODE in (select INSTITUTION from OCRM_F_CI_TRANS_CUST where apply_no='"+instanceids[1]+"') and cust_id in ( select cust_id from OCRM_F_CI_TRANS_CUST where apply_no='"+instanceids[1]+"')";
				stmt.execute(sql);
				//建立新的关系
				////////////////////////////////////
				//循环增加插入归属客户机构报文
				////////////////////////////////////
				sql = " insert into OCRM_F_CI_BELONG_ORG(ID,CUST_ID,INSTITUTION_CODE,INSTITUTION_NAME,MAIN_TYPE,ASSIGN_USER,ASSIGN_USERNAME,ASSIGN_DATE,ETL_DATE) " +
						"select ID_SEQUENCE.NEXTVAL,c.cust_id,a.t_org_id,a.t_org_name,'',a.user_id,a.user_name,a.apply_date,null " +
						"from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY  a on c.apply_no = a.apply_no where c.apply_no='"+instanceids[1]+"'";
				stmt.execute(sql);
			 }
			
			//获取拼装的所有报文、发起与ECIF的交易、根据交易状态返回结果、判定是否抛出异常
			//throw new BizException(1,0,"10001","与ECIF报文交易失败或超时,请稍后重试或联系管理员！");
			conn.commit();
			
		}catch(BizException e){
			conn.rollback();
			throw e;
		}catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			throw new BizException(1,0,"10001","工作流动态调用通过客户移交逻辑失败!");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	/**
	 * 对公支行内移交  通过处理--业务逻辑处理
	 * @param vo
	 * @throws SQLException
	 */
	public void endY3(EVO vo) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		try {
			conn = vo.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			
			SQL = "select c.cust_id,a.t_org_id,a.t_mgr_id,c.MAIN_TYPE_NEW,a.WORK_INTERFIX_DT " +
			" from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY  a on c.apply_no = a.apply_no where c.apply_no='"+instanceids[1]+"'";
		    Result result=querySQL(vo);
		    String custId = "";
		    String mgrId = "";
		    String mainType = "";
		    Date beginDate =null;
		    for (SortedMap item : result.getRows()){
			    custId = item.get("CUST_ID").toString();
			    mgrId = item.get("T_MGR_ID").toString();
			    mainType = item.get("MAIN_TYPE_NEW")!=null?item.get("MAIN_TYPE_NEW").toString():"1";
			    
			    beginDate = (Date) item.get("WORK_INTERFIX_DT");
			    //1 ： 如果 复核日期早于生效日期  这里不处理 待批处理  2：复核日期晚于于生效日期  现在生效
				if(compareDate(beginDate) < 0){//暫不生效
					//修改状态
					sql = " update OCRM_F_CI_TRANS_CUST set STATE ='1' where APPLY_NO = "+instanceids[1]+"  AND CUST_ID = '"+custId+"'";
					stmt.execute(sql);
					continue;
			    }
			    
			    /***
			     * 客户归属客户经理
			     */
			    String responseXml = TranCrmToEcifMgr(custId,mgrId,mainType,vo);
			    boolean responseFlag;
			    responseFlag = doResXms(responseXml);
			    if(!responseFlag){
			    	 throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
			    }
		    }
		 
			//修改状态
			sql = " update OCRM_F_CI_TRANS_APPLY set APPROVE_STAT ='2' where APPLY_NO = "+instanceids[1]+" ";
			stmt.execute(sql);
			
			//实现客户关系转移
			//1.1删除原有关系（转移主办权限的删除  ，转移协办权限的需要保留）
			////////////////////////////////////
			//循环增加删除归属客户经理报文
			////////////////////////////////////
			if(compareDate(beginDate) >= 0){//生效
				//修改状态
				sql = " update OCRM_F_CI_TRANS_CUST set STATE ='0' where APPLY_NO = "+instanceids[1]+" ";
				stmt.execute(sql);
				
				//复核日期晚于于生效日期，生效日期改为最新日期
				sql = " update OCRM_F_CI_TRANS_APPLY set work_interfix_dt =to_date(to_char(sysdate,'yyyy/MM/dd'),'yyyy/MM/dd') where APPLY_NO = "+instanceids[1]+" ";
				stmt.execute(sql);
				
				
				sql = " delete from OCRM_F_CI_BELONG_CUSTMGR where id in ( select RECORD_ID from OCRM_F_CI_TRANS_CUST where APPLY_NO='"+instanceids[1]+"' and MAIN_TYPE_NEW ='1')";
				stmt.execute(sql);
				
				//1.2删除目标客户经理和所选客户的原有协办关系  避免新的关系产生重复
				////////////////////////////////////
				//循环增加删除归属客户经理报文
				////////////////////////////////////
				sql = " delete from OCRM_F_CI_BELONG_CUSTMGR where CUST_ID in ( select cust_id from OCRM_F_CI_TRANS_CUST where apply_no='"+instanceids[1]+"') and MGR_ID=(select t_mgr_id from OCRM_F_CI_TRANS_APPLY where APPLY_NO = "+instanceids[1]+") and MAIN_TYPE='2'";
				stmt.execute(sql);
				
				//2.新增归属关系 并写入历史表
				////////////////////////////////////
				//循环增加插入归属客户经理报文
				////////////////////////////////////
				sql = " insert into OCRM_F_CI_BELONG_CUSTMGR " +
						"(ID,CUST_ID,MGR_ID,MAIN_TYPE,MAINTAIN_RIGHT,CHECK_RIGHT,ASSIGN_USER,ASSIGN_USERNAME,ASSIGN_DATE,INSTITUTION,INSTITUTION_NAME,MGR_NAME,EFFECT_DATE) " +
						"select ID_SEQUENCE.NEXTVAL,c.cust_id,a.t_mgr_id,c.MAIN_TYPE_NEW,decode(c.MAIN_TYPE_NEW,'1','1','2','0','1'),'1',a.user_id," +
						"a.user_name,a.APPLY_DATE,a.t_org_id,a.t_org_name,a.t_mgr_name,to_date('"+getEffectDate()+"','yyyy-mm-dd') " +
						" from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY  a on c.apply_no = a.apply_no where c.apply_no='"+instanceids[1]+"'";
				stmt.execute(sql);
				
				
				sql = "insert into OCRM_F_CI_BELONG_HIST " +
						"(BEFORE_INST_CODE,AFTER_INST_CODE,BEFORE_MGR_ID,BEFORE_INST_NAME,AFTER_MGR_ID,ASSIGN_USER,AFTER_INST_NAME,ASSIGN_DATE,ID,BEFORE_MGR_NAME,AFTER_MGR_NAME,BEFORE_MAIN_TYPE,AFTER_MAIN_TYPE,ASSIGN_USERNAME,ETL_DATE,WORK_TRAN_REASON,WORK_TRAN_LEVEL,WORK_TRAN_DATE,CUST_ID) " +
						"select  c.INSTITUTION,a.t_org_id,c.mgr_id,c.INSTITUTION_NAME,a.t_mgr_id,a.user_id,a.t_org_name," +
						"a.apply_date,ID_SEQUENCE.NEXTVAL,c.mgr_name,a.t_mgr_name,'1',c.MAIN_TYPE_NEW,a.user_name,null,a.HAND_OVER_REASON," +
						"a.HAND_KIND,a.WORK_INTERFIX_DT,c.cust_id " +
						"from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY  a on c.apply_no = a.apply_no where c.apply_no='"+instanceids[1]+"'";
				stmt.execute(sql);
			}
			//获取拼装的所有报文、发起与ECIF的交易、根据交易状态返回结果、判定是否抛出异常
			//throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
			
			//因为是同支行移交，所以不必处理机构关系
			conn.commit();
			
		}catch(BizException e){
			conn.rollback();
			throw e;
		}catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			throw new BizException(1,0,"10001","工作流动态调用通过客户移交逻辑失败!");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
	/**
	 * 对公跨区域移交   通过处理
	 * @param vo
	 * @throws SQLException
	 */
	public void endY5(EVO vo) throws Exception{
		endY4(vo);
	}
	
	/**
	 * 对公区域内移交   通过处理
	 * @param vo
	 * @throws SQLException
	 */
	public void endY4(EVO vo) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		try {
			conn = vo.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			
			 SQL = "select c.CUST_ID,a.t_org_id,a.t_mgr_id,c.MAIN_TYPE_NEW,a.WORK_INTERFIX_DT " +
				" from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY  a on c.apply_no = a.apply_no where c.apply_no='"+instanceids[1]+"'";
			 Result result=querySQL(vo);
			 Date beginDate = null;
			 for (SortedMap item : result.getRows()){
					beginDate = (Date) item.get("WORK_INTERFIX_DT");
			 }
			if(compareDate(beginDate) < 0){//暫不生效
				//修改状态
				sql = " update OCRM_F_CI_TRANS_CUST set STATE ='1' where APPLY_NO = "+instanceids[1]+" ";
				stmt.execute(sql);
				
				//修改状态
				sql = " update OCRM_F_CI_TRANS_APPLY set APPROVE_STAT ='2' where APPLY_NO = "+instanceids[1]+" ";
				stmt.execute(sql);
		    }else{
			 
			 for (SortedMap item : result.getRows()){				
				 /***
				  * 客户归属客户经理
				  */
				 String responseXml = TranCrmToEcifMgr(item.get("CUST_ID").toString(),item.get("T_MGR_ID").toString(),item.get("MAIN_TYPE_NEW")!=null?item.get("MAIN_TYPE_NEW").toString():"1",vo);
				 boolean responseFlag;
				 responseFlag = doResXms(responseXml);
				 if(!responseFlag){
					 throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
				 }
			 }
			//修改状态
			sql = " update OCRM_F_CI_TRANS_CUST set STATE ='0' where APPLY_NO = "+instanceids[1]+" ";
			stmt.execute(sql);
			
			//复核日期晚于于生效日期，生效日期改为最新日期
			sql = " update OCRM_F_CI_TRANS_APPLY set work_interfix_dt =to_date(to_char(sysdate,'yyyy/MM/dd'),'yyyy/MM/dd') where APPLY_NO = "+instanceids[1]+" ";
			stmt.execute(sql);
			 
			//修改状态
			sql = " update OCRM_F_CI_TRANS_APPLY set APPROVE_STAT ='2' where APPLY_NO = "+instanceids[1]+" ";
			stmt.execute(sql);
			
			//实现客户关系转移
			//1.1删除原有关系（转移主办权限的删除  ，转移协办权限的需要保留）
			/**
			 * 发起ECIF交易或者拼接交易报文,注此处的只能拼装报文处理,因为整个退回属于一个事务交易,必须保证所有操作成功
			 */
			////////////////////////////////////
			//循环增加删除归属客户经理报文
			////////////////////////////////////
			sql = " delete from OCRM_F_CI_BELONG_CUSTMGR where id in ( select RECORD_ID from OCRM_F_CI_TRANS_CUST where APPLY_NO='"+instanceids[1]+"' and MAIN_TYPE_NEW ='1')";
			stmt.execute(sql);
			
			
			//1.2删除目标客户经理和所选客户的原有协办关系  避免新的关系产生重复
			////////////////////////////////////
			//循环增加删除归属客户经理报文
			////////////////////////////////////
			sql = " delete from OCRM_F_CI_BELONG_CUSTMGR where CUST_ID in ( select cust_id from OCRM_F_CI_TRANS_CUST where apply_no='"+instanceids[1]+"') and MGR_ID=(select t_mgr_id from OCRM_F_CI_TRANS_APPLY where APPLY_NO = "+instanceids[1]+") and  MAIN_TYPE='2'";
			stmt.execute(sql);
			
			//2.新增归属关系 并写入历史表
			////////////////////////////////////
			//循环增加插入归属客户经理报文
			////////////////////////////////////
			sql = " insert into OCRM_F_CI_BELONG_CUSTMGR " +
					"(ID,CUST_ID,MGR_ID,MAIN_TYPE,MAINTAIN_RIGHT,CHECK_RIGHT,ASSIGN_USER,ASSIGN_USERNAME,ASSIGN_DATE,INSTITUTION,INSTITUTION_NAME,MGR_NAME,EFFECT_DATE) " +
					"select ID_SEQUENCE.NEXTVAL,c.cust_id,a.t_mgr_id,c.MAIN_TYPE_NEW,decode(c.MAIN_TYPE_NEW,'1','1','2','0','1'),'1',a.user_id," +
					"a.user_name,a.APPLY_DATE,a.t_org_id,a.t_org_name,a.t_mgr_name,to_date('"+getEffectDate()+"','yyyy-mm-dd') " +
					" from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY  a on c.apply_no = a.apply_no where c.apply_no='"+instanceids[1]+"'";
			stmt.execute(sql);
			
			
			sql = "insert into OCRM_F_CI_BELONG_HIST " +
					"(BEFORE_INST_CODE,AFTER_INST_CODE,BEFORE_MGR_ID,BEFORE_INST_NAME,AFTER_MGR_ID,ASSIGN_USER,AFTER_INST_NAME,ASSIGN_DATE,ID,BEFORE_MGR_NAME,AFTER_MGR_NAME,BEFORE_MAIN_TYPE,AFTER_MAIN_TYPE,ASSIGN_USERNAME,ETL_DATE,WORK_TRAN_REASON,WORK_TRAN_LEVEL,WORK_TRAN_DATE,CUST_ID) " +
					"select  c.INSTITUTION,a.t_org_id,c.mgr_id,c.INSTITUTION_NAME,a.t_mgr_id,a.user_id,a.t_org_name," +
					"a.apply_date,ID_SEQUENCE.NEXTVAL,c.mgr_name,a.t_mgr_name,'1',c.MAIN_TYPE_NEW,a.user_name,null,a.HAND_OVER_REASON," +
					"a.HAND_KIND,a.WORK_INTERFIX_DT,c.cust_id " +
					"from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY  a on c.apply_no = a.apply_no where c.apply_no='"+instanceids[1]+"'";
			stmt.execute(sql);
			
			//处理机构关系  删除原有机构关系  通过OCRM_F_CI_BELONG_CUSTMGR的数据确定新的机构关系
			//1.删除所选客户与目标机构的归属关系
			////////////////////////////////////
			//循环增加删除归属机构报文
			////////////////////////////////////
			sql = " delete from OCRM_F_CI_BELONG_ORG where INSTITUTION_CODE in (select t_org_id from OCRM_F_CI_TRANS_APPLY where apply_no='"+instanceids[1]+"') and cust_id in ( select cust_id from OCRM_F_CI_TRANS_CUST where apply_no='"+instanceids[1]+"')";
			stmt.execute(sql);
			
			//2.删除所选客户的机构归属关系
			////////////////////////////////////
			//循环增加删除归属机构报文
			////////////////////////////////////
			sql = " delete from OCRM_F_CI_BELONG_ORG where INSTITUTION_CODE in (select INSTITUTION from OCRM_F_CI_TRANS_CUST where apply_no='"+instanceids[1]+"') and cust_id in ( select cust_id from OCRM_F_CI_TRANS_CUST where apply_no='"+instanceids[1]+"')";
			stmt.execute(sql);
			
			//循环所选客户，通过OCRM_F_CI_BELONG_CUSTMGR的数据确定新的机构关系
			SQL = " select * from OCRM_F_CI_TRANS_APPLY where apply_no='"+instanceids[1]+"'";
			Result rs1 = querySQL(vo);
			String orgNew = (String) rs1.getRows()[0].get("T_ORG_ID");
			String orgNameNew = (String) rs1.getRows()[0].get("T_ORG_NAME");
			
			SQL = " select * from OCRM_F_CI_TRANS_CUST where apply_no='"+instanceids[1]+"'";
			Result rs = querySQL(vo);
			for (SortedMap<?, ?> item : rs.getRows()){
				String oldOrg = item.get("INSTITUTION").toString();
				String custId = item.get("CUST_ID").toString();
				String id = item.get("ID").toString();
				//处理和原机构关系
				SQL = "  select * from OCRM_F_CI_BELONG_CUSTMGR where INSTITUTION='"+oldOrg+"' and CUST_ID='"+custId+"' and MAIN_TYPE='1' ";
				Result rs2 = querySQL(vo);
				if(rs2.getRowCount()>0){//主办关系
					////////////////////////////////////
					 String responseXml = TranCrmToEcifOrg(custId,oldOrg,"1",vo);
					 boolean responseFlag;
					 responseFlag = doResXms(responseXml);
					 if(!responseFlag){
						 throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
					 }
					////////////////////////////////////
					sql = " insert into OCRM_F_CI_BELONG_ORG(ID,CUST_ID,INSTITUTION_CODE,INSTITUTION_NAME,MAIN_TYPE,ASSIGN_USER,ASSIGN_USERNAME,ASSIGN_DATE,ETL_DATE) " +
					"select ID_SEQUENCE.NEXTVAL,c.cust_id,c.INSTITUTION,c.INSTITUTION_NAME,'1',a.user_id,a.user_name,a.apply_date,null " +
					"from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY  a on c.apply_no = a.apply_no where c.apply_no='"+instanceids[1]+"' and c.id='"+id+"'";
					stmt.execute(sql);
				}else{
					SQL = "  select * from OCRM_F_CI_BELONG_CUSTMGR where INSTITUTION='"+oldOrg+"' and CUST_ID='"+custId+"' and MAIN_TYPE='2' ";
					Result rs3 = querySQL(vo);
					if(rs3.getRowCount()>0){//协办关系
						////////////////////////////////////
						String responseXml = TranCrmToEcifOrg(custId,oldOrg,"2",vo);
						 boolean responseFlag;
						 responseFlag = doResXms(responseXml);
						 if(!responseFlag){
							 throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
						 }
						////////////////////////////////////
						sql = " insert into OCRM_F_CI_BELONG_ORG(ID,CUST_ID,INSTITUTION_CODE,INSTITUTION_NAME,MAIN_TYPE,ASSIGN_USER,ASSIGN_USERNAME,ASSIGN_DATE,ETL_DATE) " +
						"select ID_SEQUENCE.NEXTVAL,c.cust_id,c.INSTITUTION,c.INSTITUTION_NAME,'2',a.user_id,a.user_name,a.apply_date,null " +
						"from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY  a on c.apply_no = a.apply_no where c.apply_no='"+instanceids[1]+"' and c.id='"+id+"'";
						stmt.execute(sql);
					}
				}
				//处理和新机构关系
				SQL = "  select * from OCRM_F_CI_BELONG_CUSTMGR where INSTITUTION='"+orgNew+"' and CUST_ID='"+custId+"' and MAIN_TYPE='1' ";
				Result rs4 = querySQL(vo);
				if(rs4.getRowCount()>0){
					////////////////////////////////////
					String responseXml = TranCrmToEcifOrg(custId,orgNew,"1",vo);
					 boolean responseFlag;
					 responseFlag = doResXms(responseXml);
					 if(!responseFlag){
						 throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
					 }
					////////////////////////////////////
					sql = " insert into OCRM_F_CI_BELONG_ORG(ID,CUST_ID,INSTITUTION_CODE,INSTITUTION_NAME,MAIN_TYPE,ASSIGN_USER,ASSIGN_USERNAME,ASSIGN_DATE,ETL_DATE) " +
					"select ID_SEQUENCE.NEXTVAL,c.cust_id,'"+orgNew+"','"+orgNameNew+"','1',a.user_id,a.user_name,a.apply_date,null " +
					"from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY  a on c.apply_no = a.apply_no where c.apply_no='"+instanceids[1]+"' and c.id='"+id+"'";
					stmt.execute(sql);
				}else{
					SQL = "  select * from OCRM_F_CI_BELONG_CUSTMGR where INSTITUTION='"+orgNew+"' and CUST_ID='"+custId+"' and MAIN_TYPE='2' ";
					Result rs5 = querySQL(vo);
					if(rs5.getRowCount()>0){
						////////////////////////////////////
						String responseXml = TranCrmToEcifOrg(custId,orgNew,"2",vo);
						 boolean responseFlag;
						 responseFlag = doResXms(responseXml);
						 if(!responseFlag){
							 throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
						 }
						////////////////////////////////////
						sql = " insert into OCRM_F_CI_BELONG_ORG(ID,CUST_ID,INSTITUTION_CODE,INSTITUTION_NAME,MAIN_TYPE,ASSIGN_USER,ASSIGN_USERNAME,ASSIGN_DATE,ETL_DATE) " +
						"select ID_SEQUENCE.NEXTVAL,c.cust_id,'"+orgNew+"','"+orgNameNew+"','2',a.user_id,a.user_name,a.apply_date,null " +
						"from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY  a on c.apply_no = a.apply_no where c.apply_no='"+instanceids[1]+"' and c.id='"+id+"'";
						stmt.execute(sql);
					}
				}
			}
			//获取拼装的所有报文、发起与ECIF的交易、根据交易状态返回结果、判定是否抛出异常
			//throw new BizException(1,0,"10001","与ECIF报文交易失败或超时,请稍后重试或联系管理员！");
		    }
			conn.commit();
		}catch(BizException e){
			conn.rollback();
			throw e;
		}catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			throw new BizException(1,0,"10001","工作流动态调用通过客户移交逻辑失败!");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
	//否决移交
	public void endN(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL = " update OCRM_F_CI_TRANS_APPLY set APPROVE_STAT ='3' where APPLY_NO = "+instanceids[1]+" ";
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}
	
	/**
	 * 报文处理
	 * 客户归属经理 
	 * @throws  
	 */
	public String TranCrmToEcifMgr(String custId,String mgrId,String mainType,EVO vo){
		 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		 String req = "";
		//注：工作流动态调用类中,是无法获取Session用户数据的
    	try {
			 com.yuchengtech.trans.bo.RequestHeader header = new com.yuchengtech.trans.bo.RequestHeader();
			 header.setReqSysCd("CRM");
			 header.setReqSeqNo(format.format(new Date()));//交易流水号
			 header.setReqDt(new SimpleDateFormat("yyyyMMdd").format(new Date()));//请求日期
			 header.setReqTm(new SimpleDateFormat("HHmmssSS").format(new Date()));//请求时间
			 
			 header.setDestSysCd("ECIF");
			 header.setChnlNo("82");
			 header.setBrchNo("503");
			 header.setBizLine("209");
			 header.setTrmNo("TRM10010");
			 header.setTrmIP("127.0.0.1");
			 header.setTlrNo(auth.getUserId());//用户编号
			 
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
			 if(mainType!=null){
				 sb.append("<mainType>"+mainType+"</mainType>");
			 }else{
				 sb.append("<mainType></mainType>");
			 }
			 sb.append("<startDate></startDate>");
			 sb.append("<endDate></endDate>");
			 sb.append("<custManagerNo>"+mgrId+"</custManagerNo>"); //客户经理编号
			 sb.append("</belongManager>");
			 sb.append("</RequestBody>");
			 String Xml = new String(sb.toString().getBytes());
			 req  = TransClient.process(header, Xml);
		}catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
		}
		return req;
	}
	
	/**
	 * 报文处理
	 * 客户归属机构
	 */
	public String TranCrmToEcifOrg(String custId,String orgId,String mainType,EVO vo){
		 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String req = "";
    	//注：工作流动态调用类中,是无法获取Session用户数据的
    	try {
			 com.yuchengtech.trans.bo.RequestHeader header = new com.yuchengtech.trans.bo.RequestHeader();
			 header.setReqSysCd("CRM");
			 header.setReqSeqNo(format.format(new Date()));//交易流水号
			 header.setReqDt(new SimpleDateFormat("yyyyMMdd").format(new Date()));//请求日期
			 header.setReqTm(new SimpleDateFormat("HHmmssSS").format(new Date()));//请求时间
			 
			 header.setDestSysCd("ECIF");
			 header.setChnlNo("82");
			 header.setBrchNo("503");
			 header.setBizLine("209");
			 header.setTrmNo("TRM10010");
			 header.setTrmIP("127.0.0.1");
			 header.setTlrNo(auth.getUserId());//用户编号
			 
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
			 sb.append("<belongBranchNo>"+orgId+"</belongBranchNo>"); //机构编号
			 if(mainType!=null){
				 sb.append("<mainType>"+mainType+"</mainType>"); 
			 }else{
				 sb.append("<mainType></mainType>"); 
			 }
			 sb.append("</belongBranch>");
			 sb.append("</RequestBody>");
			 String Xml = new String(sb.toString().getBytes());
			 req  = TransClient.process(header, Xml);
		}catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
		}
		return req;
	}
	
	/**
	 * 处理返回报文
	 * @param xml
	 * @return
	 */
	public boolean doResXms(String xml) throws Exception{
		try{
			xml=xml.substring(8);
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			String TxStatCode = root.element("ResponseTail").element("TxStatCode").getTextTrim();
			if(TxStatCode!=null && !TxStatCode.trim().equals("") && (TxStatCode.trim().equals("000000"))){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
