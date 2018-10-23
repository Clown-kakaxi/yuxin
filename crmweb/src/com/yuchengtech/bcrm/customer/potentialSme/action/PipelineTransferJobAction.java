package com.yuchengtech.bcrm.customer.potentialSme.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
import com.yuchengtech.crm.constance.JdbcUtil;

public class PipelineTransferJobAction extends EChainCallbackCommon {
	private static Logger log = Logger.getLogger(PipelineTaskJobAction.class);
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	/**
	 * 法金客户经理移交
	 * 业务数据移交监控
	 */
	public void pipelineTransfer() {
		log.info("处理任务开始");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql = " select t.id,t.apply_no,t.cust_id,t.cust_name,t.mgr_id,t.mgr_name,t.bus_data_id,t.effect_date,t.state,t.type,t.pipeline_step,t.pipeline_state " +
					" from ocrm_f_ci_trans_business t where t.state='1' order by t.apply_no ";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				String idStr = rs.getString("id");
				Long id = Long.parseLong(idStr);
				String applyNo = rs.getString("apply_no");
				String custId = rs.getString("cust_id");
				String dataId = rs.getString("bus_data_id");
				String type = rs.getString("type");
				String effectDate = rs.getString("effect_date");
				
				Date date = sdf.parse(effectDate);
				effectDate = fmt.format(date);
				String nowDate = fmt.format(new Date());
				Boolean state = getApplyState(applyNo,custId);
				if(state){
					if(nowDate.compareTo(effectDate)>=0){
						switch (Integer.valueOf(type)) {
						case 1:
							endB1(dataId,id);//电访
							break;
						case 2:
							endB2(dataId,id);//拜访
							break;
						case 3:
							endB3(dataId,id,applyNo);//pipeline
							break;
						default:
							break;
					}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, conn);
		}
		log.info("处理任务结束");
	}
	
	/**
	 * 电访信息
	 */
	public void endB1(String dataId,Long id){
		String sql = " insert into ocrm_f_ci_trans_business_his (id,cust_id,cust_name,pipeline_id,before_mgr_id,before_mgr_name,after_mgr_id,after_mgr_name,effect_date,type,before_step,before_state,create_date,creator)    "+
				" select ID_SEQUENCE.Nextval,bs.cust_id,bs.cust_name,bs.bus_data_id,bs.mgr_id,bs.mgr_name,a.t_mgr_id,a.t_mgr_name,bs.effect_date,bs.type,bs.pipeline_step,bs.pipeline_state,to_date(to_char(sysdate,'yyyy/mm/dd'),'yyyy/mm/dd'),bs.creator   "+
				" from ocrm_f_ci_trans_business bs left join ocrm_f_ci_trans_apply a   "+
				" on bs.apply_no = a.apply_no   "+
				" where 1=1    "+
				" and bs.state = '1'   "+
				" and bs.bus_data_id='"+dataId+"' ";
		executeUpSql(sql);
		
		//修改状态
		sql = " update OCRM_F_CI_TRANS_BUSINESS set STATE ='0' where id = "+id+" ";
		executeUpSql(sql);
	}
	
	/**
	 * 拜访信息
	 */
	public void endB2(String dataId,Long id){
		endB1(dataId,id);
	}
	
	/**
	 * pipeline信息
	 */
	public void endB3(String dataId,Long id,String applyNo){
		String sql = " insert into ocrm_f_ci_trans_business_his (id,cust_id,cust_name,pipeline_id,before_mgr_id,before_mgr_name,after_mgr_id,after_mgr_name,effect_date,type,before_step,before_state,create_date,creator)    "+
				" select ID_SEQUENCE.Nextval,bs.cust_id,bs.cust_name,bs.bus_data_id,bs.mgr_id,bs.mgr_name,a.t_mgr_id,a.t_mgr_name,bs.effect_date,bs.type,bs.pipeline_step,bs.pipeline_state,to_date(to_char(sysdate,'yyyy/mm/dd'),'yyyy/mm/dd'),bs.creator   "+
				" from ocrm_f_ci_trans_business bs left join ocrm_f_ci_trans_apply a   "+
				" on bs.apply_no = a.apply_no   "+
				" where 1=1    "+
				" and bs.state = '1'   "+
				" and bs.bus_data_id='"+dataId+"' ";
		executeUpSql(sql);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String tMgrId = "";
		String tMgrName = "";
		String orgId = "";
		String orgName = "";
		String areaId = "";
		String areaName = "";
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql = " select n.t_mgr_id,n.t_mgr_name,n.org_id,n.org_name,n.superunitid as area_id,t.org_name as area_name "+
					"   from (select m.t_mgr_id,m.t_mgr_name,m.org_id,m.org_name,UNIT.superunitid  "+
					"           from (select a.t_mgr_id, a.t_mgr_name, t2.org_id, t2.org_name  "+
					"                   from ocrm_f_ci_trans_business bs  "+
					"                   left join ocrm_f_ci_trans_apply a on bs.apply_no = a.apply_no  "+
					"                   left join ADMIN_AUTH_ACCOUNT t1  on t1.account_name = a.t_mgr_id  "+
					"                   left join ADMIN_AUTH_ORG t2  on t1.ORG_ID = t2.ORG_ID  "+
					"                  where 1 = 1  "+
					"                    and a.apply_no = '"+applyNo+"' and bs.state<>'99' and bs.bus_data_id = '"+dataId+"') m  "+
					"           left join SYS_UNITS UNIT  on UNIT.unitid = m.org_id  WHERE UNIT.UNITSEQ LIKE '500%') n  "+
					"   left join SYS_UNITS t  on t.unitid = n.superunitid ";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				tMgrId = rs.getString("t_mgr_id");
				tMgrName = rs.getString("t_mgr_name");
				orgId = rs.getString("org_id");
				orgName = rs.getString("org_name");
				areaId = rs.getString("area_id");
				areaName = rs.getString("area_name");
			}
			sql = " update OCRM_F_CI_MKT_PROSPECT_C c" +
					" set c.rm_id ='"+tMgrId+"', c.rm ='"+tMgrName+"',c.dept_id  ='"+orgId+"',c.dept_name ='"+orgName+"'," +
					"c.area_id='"+areaId+"',c.area_name='"+areaName+"' " +
					"  where c.pipeline_id = '"+dataId+"' ";
			executeUpSql(sql);
			
			sql = " update OCRM_F_CI_MKT_INTENT_C c" +
					" set c.rm_id ='"+tMgrId+"', c.rm ='"+tMgrName+"',c.dept_id  ='"+orgId+"',c.dept_name ='"+orgName+"'," +
					"c.area_id='"+areaId+"',c.area_name='"+areaName+"' " +
					"  where c.pipeline_id = '"+dataId+"' ";
			executeUpSql(sql);
			
			sql = " update OCRM_F_CI_MKT_CA_C c" +
					" set c.rm_id ='"+tMgrId+"', c.rm ='"+tMgrName+"',c.dept_id  ='"+orgId+"',c.dept_name ='"+orgName+"'," +
					"c.area_id='"+areaId+"',c.area_name='"+areaName+"' " +
					"  where c.pipeline_id = '"+dataId+"' ";
			executeUpSql(sql);
			
			sql = " update OCRM_F_CI_MKT_CHECK_C c" +
					" set c.rm_id ='"+tMgrId+"', c.rm ='"+tMgrName+"',c.dept_id  ='"+orgId+"',c.dept_name ='"+orgName+"'," +
					"c.area_id='"+areaId+"',c.area_name='"+areaName+"' " +
					"  where c.pipeline_id = '"+dataId+"' ";
			executeUpSql(sql);
			
			sql = " update OCRM_F_CI_MKT_APPROVL_C c" +
					" set c.rm_id ='"+tMgrId+"', c.rm ='"+tMgrName+"',c.dept_id  ='"+orgId+"',c.dept_name ='"+orgName+"'," +
					"c.area_id='"+areaId+"',c.area_name='"+areaName+"' " +
					"  where c.pipeline_id = '"+dataId+"' ";
			executeUpSql(sql);
			
			sql = " update OCRM_F_CI_MKT_APPROVED_C c" +
					" set c.rm_id ='"+tMgrId+"', c.rm ='"+tMgrName+"',c.dept_id  ='"+orgId+"',c.dept_name ='"+orgName+"'," +
					"c.area_id='"+areaId+"',c.area_name='"+areaName+"' " +
					"  where c.pipeline_id = '"+dataId+"' ";
			executeUpSql(sql);
			
			//修改状态
			sql = " update OCRM_F_CI_TRANS_BUSINESS set STATE ='0' where id = "+id+" ";
			executeUpSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, conn);
		}
	}

	public Boolean getApplyState(String applyNo,String custId){
		log.info("getApplyState()  start");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		Boolean state = false;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql = " SELECT Y.APPLY_NO,Y.APPROVE_STAT,T.STATE  FROM OCRM_F_CI_TRANS_APPLY Y LEFT JOIN OCRM_F_CI_TRANS_CUST T ON Y.APPLY_NO = T.APPLY_NO" +
					" where Y.APPLY_NO='"+applyNo+"' and T.cust_id = '"+custId+"' ";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				String applyState = rs.getString("approve_stat");
				String custState = rs.getString("STATE");
				if(StringUtils.isNotEmpty(applyState) && StringUtils.isNotEmpty(custState)){
					if(applyState.equals("2") && custState.equals("0")){
						state = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, conn);
		}
		log.info("getApplyState()  end");
		return state;
	}
	
	/**
	 * 执行修改sql文
	 * @param sql
	 */
	public void executeUpSql(String sql){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt.execute(sql);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, conn);
		}
    }

}
