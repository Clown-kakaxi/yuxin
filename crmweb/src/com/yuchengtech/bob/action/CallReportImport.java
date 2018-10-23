package com.yuchengtech.bob.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.importimpl.ImportInterface;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;

/**
 * CallReport批量导入
 * @author lianghe
 *
 */
public class CallReportImport implements ImportInterface{
	private static Logger log = Logger.getLogger(QueryHelper.class);
	
	public void excute(Connection conn, String PKhead, AuthUser aUser) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
		String userId = aUser.getUserId();
		String date = sf.format(new Date());
		log.info("updateSQL: 【CallReportImport has been evoke!】");
		Statement stm = null;
		try {
			stm = conn.createStatement();
			conn.setAutoCommit(false);
			String sql="update OCRM_F_SE_CALLREPORT_TEMP set " +
//					" CUST_TYPE= substr(CUST_TYPE, 0,instr(CUST_TYPE,'-')-1)," +
//					" CUST_CHANNEL= substr(CUST_CHANNEL, 0,instr(CUST_CHANNEL,'-')-1)," +
					" VISIT_WAY= substr(VISIT_WAY, 0,instr(VISIT_WAY,'-')-1)," +
					" VISIT_CONTENT= substr(VISIT_CONTENT, 0,instr(VISIT_CONTENT,'-')-1)," +
					" NEXT_VISIT_WAY= substr(NEXT_VISIT_WAY, 0,instr(NEXT_VISIT_WAY,'-')-1) " +
					" where ID like '" + PKhead + "%'";
			stm.execute(sql);
			//数据校验
			boolean flag = dataCheck(conn,PKhead,aUser);
			if(flag){
				
				String updateSQL = " insert into OCRM_F_SE_CALLREPORT(CALL_ID,CUST_ID,CUST_TYPE,VISIT_DATE,LINK_PHONE,VISIT_WAY,"+
						" CUST_CHANNEL,RECOMMEND_CUST_ID,VISIT_CONTENT,LAST_UPDATE_USER,LAST_UPDATE_TM,CUST_NAME,BEGIN_DATE,"+
						" END_DATE,NEXT_VISIT_DATE,NEXT_VISIT_WAY,CREATE_USER,MKT_BAK_NOTE)"+
						" select id_sequence.nextval,                                  "+
						" replace(t.CUST_ID,'null',''),                                "+
						" replace(t.POTENTIAL_FLAG,'null',''),                              "+
						" to_date(replace(t.VISIT_DATE,'null',''),'yyyy-MM-dd'),       "+
						" replace(t.LINK_PHONE,'null',''),                             "+
						" replace(t.VISIT_WAY,'null',''),                              "+
						" replace(t.CUST_CHANNEL,'null',''),                           "+
						" replace(t.RECOMMEND_CUST_ID,'null',''),                      "+
						" replace(t.VISIT_CONTENT,'null',''),                          "+
						" '"+userId+"',"+
						" to_date('2016-4-11', 'yyyy-MM-dd'),                          "+
						" replace(t.CUST_NAME,'null',''),                              "+
						" replace(t.BEGIN_DATE,'null',''),                             "+
						" replace(t.END_DATE,'null',''),                               "+
						" to_date(replace(t.NEXT_VISIT_DATE,'null',''),'yyyy-MM-dd'),  "+
						" replace(t.NEXT_VISIT_WAY,'null',''),                         "+
						" '"+userId+"',"+
						" replace(t.MKT_BAK_NOTE,'null','')                            "+
						" from(  select m.*,'1' as POTENTIAL_FLAG,c.cust_name,c.linkman_tel as LINK_PHONE,c.source_channel as CUST_CHANNEL         "+
						"          from OCRM_F_SE_CALLREPORT_TEMP m                                                                      "+
						"         inner join ACRM_F_CI_CUSTOMER c on m.cust_id = c.cust_id                                               "+
						"         union                                                                                                  "+
						"        select m.*,'2' as POTENTIAL_FLAG,p.cus_name as cust_name,p.call_no as LINK_PHONE,p.source_channel as CUST_CHANNEL "+
						"          from  OCRM_F_SE_CALLREPORT_TEMP m                                                                     "+
						"         inner join ACRM_F_CI_POT_CUS_COM p on m.cust_id = p.cus_id) t                                          "+
						" where ID like '" + PKhead + "%'      ";
				stm.execute(updateSQL);
				log.info("updateSQL: 【" + updateSQL + "】");
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			try {
				String deleteSQL = "DELETE FROM OCRM_F_SE_CALLREPORT_TEMP t where t.ID like '" + PKhead + "%'";//插入失败删除原先的数据
				stm.execute(deleteSQL);
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			JdbcUtil.close(null, stm, conn);
		}
	}
	
	/**
	 * 数据校验
	 * 验证填写的数据是否正确
	 * @return
	 */
	public boolean dataCheck(Connection conn, String PKhead,AuthUser aUser){
		boolean flag = true;
		log.info("dataCheck: [CallReportImport has been evoke!]");
		Statement stmt=null;
    	ResultSet rs=null;
		try {
			stmt = conn.createStatement();
			String sql = " select t.ID,t.CUST_ID,t.VISIT_DATE,t.BEGIN_DATE,"
					+" t.END_DATE,t.VISIT_WAY,t.RECOMMEND_CUST_ID,t.VISIT_CONTENT,t.NEXT_VISIT_DATE,t.NEXT_VISIT_WAY,t.MKT_BAK_NOTE "
					+" from OCRM_F_SE_CALLREPORT_TEMP t where t.id like '" + PKhead + "%'   ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String idStr = rs.getString("ID");
				String custId = rs.getString("CUST_ID");
//				String custName = rs.getString("CUST_NAME");
//				String custType = rs.getString("CUST_TYPE"); //客户类型
//				String custChannel = rs.getString("CUST_CHANNEL");
				String visitDate = rs.getString("VISIT_DATE");//拜访日期
				String beginDate = rs.getString("BEGIN_DATE");//开始时间
				String endDate = rs.getString("END_DATE");//结束时间
				String visitWay = rs.getString("VISIT_WAY");
				String visitContent = rs.getString("VISIT_CONTENT");//访谈结果
				String mktBakNote = rs.getString("MKT_BAK_NOTE");//访谈内容
				String nextVisitDate = rs.getString("NEXT_VISIT_DATE");
				String nextVisitWay = rs.getString("NEXT_VISIT_WAY");
				String[] str = idStr.split("_");
				int row = Integer.parseInt(str[str.length-1])+1;
				if((!"null".equals(custId)) && StringUtils.isNotEmpty(custId)){
					String sql1 = "select c.cust_id from acrm_f_ci_customer c where c.cust_id='"+custId+"' ";
					List<Object> list = executeSql(conn, sql1);
					if(list.size()==0){
						sql1 = "select c.cus_id from ACRM_F_CI_POT_CUS_COM c where c.cus_id='"+custId+"' ";
						list = executeSql(conn, sql1);
						if(list.size()==0){
							flag = false;
							aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+row+"行客户编号错误，请修改确认之后重新导入");												
							throw new BizException(1, 0, "10001", "导入数据错误");
						}
					}
				}else{
					flag = false;
					aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+row+"行客户编号为空，请修改确认之后重新导入");												
					throw new BizException(1, 0, "10001", "导入数据错误");
				}
//				String custTypeValue = getLookupValue(conn, "CALLREPORT_CUST_TYPE", custType);
//				if(StringUtils.isEmpty(custTypeValue)){
//					flag = false;
//					aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+row+"行客户类型为空或填写错误，请修改确认之后重新导入");												
//					throw new BizException(1, 0, "10001", "导入数据错误");
//				}
//				if(StringUtils.isNotEmpty(custChannel)){
//					String custChannelValue = getLookupValue(conn, "XD000353", custChannel);
//					if(StringUtils.isEmpty(custChannelValue)){
//						flag = false;
//						aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+row+"行客户渠道填写错误，请修改确认之后重新导入");												
//						throw new BizException(1, 0, "10001", "导入数据错误");
//					}
//				}
				if("null".equals(visitDate) || StringUtils.isEmpty(visitDate)){
					flag = false;
					aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+row+"行拜访日期为空，日期格式为2016/1/1或者2016-1-1，请修改确认之后重新导入");												
					throw new BizException(1, 0, "10001", "导入数据错误");
				}else{
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date vsDate = sf.parse(visitDate);
					} catch (Exception e) {
						SimpleDateFormat sf2 = new SimpleDateFormat("yyyy/MM/dd");
						try {
							Date vsDate2 = sf2.parse(visitDate);
						} catch (Exception e2) {
							e.printStackTrace();
							flag = false;
							aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+row+"行拜访日期填写错误，日期格式为2016/1/1或者2016-1-1，请修改确认之后重新导入");												
							throw new BizException(1, 0, "10001", "导入数据错误");
						}
					}
				}
				if("null".equals(beginDate)|| StringUtils.isEmpty(beginDate)){
					flag = false;
					aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+row+"行起始时间为空，请修改确认之后重新导入");												
					throw new BizException(1, 0, "10001", "导入数据错误");
				}
				if("null".equals(endDate)|| StringUtils.isEmpty(endDate)){
					flag = false;
					aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+row+"行结束时间为空，请修改确认之后重新导入");												
					throw new BizException(1, 0, "10001", "导入数据错误");
				}else{
					String[] bD = beginDate.split(":");
					int h1 = Integer.parseInt(bD[0]);
					int s1 = Integer.parseInt(bD[1]);
					
					String[] eD = endDate.split(":");
					int h2 = Integer.parseInt(eD[0]);
					int s2 = Integer.parseInt(eD[1]);
					if(h1>h2){
						flag = false;
						aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+row+"行结束时间不能小于起始时间，请修改确认之后重新导入");												
						throw new BizException(1, 0, "10001", "导入数据错误");
					}else if(h1 == h2){
						if(s1 > s2){
							flag = false;
							aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+row+"行结束时间不能小于起始时间，请修改确认之后重新导入");												
							throw new BizException(1, 0, "10001", "导入数据错误");
						}
					}
				}
				String visitWayValue = getLookupValue(conn, "CALLREPORT_VISIT_TYPE", visitWay);
				if(StringUtils.isEmpty(visitWayValue)){
					flag = false;
					aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+row+"行拜访方式为空或填写错误，请修改确认之后重新导入");												
					throw new BizException(1, 0, "10001", "导入数据错误");
				}
				String visitContentValue = getLookupValue(conn, "INTERVIEW_RESULTS", visitContent);
				if(StringUtils.isEmpty(visitContentValue)){
					flag = false;
					aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+row+"行访谈结果为空或填写错误，请修改确认之后重新导入");												
					throw new BizException(1, 0, "10001", "导入数据错误");
				}
				if("null".equals(mktBakNote) || StringUtils.isEmpty(mktBakNote)){
					flag = false;
					aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+row+"行访谈内容为空，请修改确认之后重新导入");												
					throw new BizException(1, 0, "10001", "导入数据错误");
				}
				if(!"null".equals(nextVisitDate)){
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date nVsDate = sf.parse(nextVisitDate);
					} catch (Exception e) {
						SimpleDateFormat sf2 = new SimpleDateFormat("yyyy/MM/dd");
						try {
							Date vsDate2 = sf2.parse(nextVisitDate);
						} catch (Exception e2) {
							e.printStackTrace();
							flag = false;
							aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+row+"行下次拜访日期填写错误，日期格式为2016/1/1或者2016-1-1，请修改确认之后重新导入");												
							throw new BizException(1, 0, "10001", "导入数据错误");
						}
					}
				}
				if(StringUtils.isNotEmpty(nextVisitWay)){
					String nextVisitWayValue = getLookupValue(conn, "CALLREPORT_VISIT_TYPE", nextVisitWay);
					if(StringUtils.isEmpty(nextVisitWayValue)){
						flag = false;
						aUser.putAttribute(BACK_IMPORT_ERROR, "EXCEL中第"+row+"行下次拜访方式填写错误，请修改确认之后重新导入");												
						throw new BizException(1, 0, "10001", "导入数据错误");
					}
				}
			}
		}catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close(); // 关闭结果集
				}
				if (stmt != null) {
					stmt.close(); // 关闭Statement
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		return flag;
	}
	
	public String getLookupValue(Connection conn,String lookupId,String code){
    	Statement stmt=null;
    	ResultSet result=null;
    	String value="";
    	try{
    		stmt = conn.createStatement();
    		String sql = "select F_CODE from ocrm_sys_lookup_item  where 1 > 0 and F_LOOKUP_ID like '%"+lookupId+"%' and F_CODE = '"+code+"' ";
    		result = stmt.executeQuery(sql);
		    while (result.next()){
		    	value = result.getString(1);
		    }
   		}catch(Exception e){
   			e.printStackTrace();
   		} finally {
			try {
				if (result != null) {
					result.close(); // 关闭结果集
				}
				if (stmt != null) {
					stmt.close(); // 关闭Statement
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
    	return value;
       }
	
	public List<Object> executeSql(Connection conn,String sql){
    	log.info(""+sql);
    	List<Object> List = new ArrayList<Object>();
    	Statement stmt=null;
    	ResultSet result=null;
    	try{
    		stmt = conn.createStatement();
    		result = stmt.executeQuery(sql);
			String custId="";
		    while (result.next()){
		    	custId = result.getString(1);
		    	List.add(custId);
		    }
   			log.info("validationNewLatentCustomer: "+List.toString());
   			return List;
   		}catch(Exception e){
   			e.printStackTrace();
   		} finally {
			try {
				if (result != null) {
					result.close(); // 关闭结果集
				}
				if (stmt != null) {
					stmt.close(); // 关闭Statement
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		return null;
       }

}
