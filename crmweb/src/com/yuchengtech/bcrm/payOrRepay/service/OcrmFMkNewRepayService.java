package com.yuchengtech.bcrm.payOrRepay.service;

import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Statement;


import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sun.jmx.snmp.Timestamp;
import com.yuchengtech.bcrm.payOrRepay.model.OcrmFMkNewRepay;
import com.yuchengtech.bcrm.system.model.AdminAuthAccount;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;



/**
 * @author sunjing5
 * 预估波动还款
 *
 */
@SuppressWarnings("deprecation")
@Service
public class OcrmFMkNewRepayService extends CommonService{
	private static Logger log;

	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;// 定义数据源属性

	private Object json;
	
	public OcrmFMkNewRepayService() {
		JPABaseDAO<OcrmFMkNewRepay, Long> baseDAO = new JPABaseDAO<OcrmFMkNewRepay, Long>(
				OcrmFMkNewRepay.class);
		super.setBaseDAO(baseDAO);
		  if (null == log) {
	            log = Logger.getLogger(OcrmFMkNewRepayService.class);
	        }
	}
	
	/**
	 * @description:保存方法：
	 */
	public void save(OcrmFMkNewRepay newRepay) {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if ("".equals(newRepay.getId()) || newRepay.getId() == null) {
			newRepay.setApproveState("1");//暂存
			newRepay.setCreateTm(new Date());
			newRepay.setCreateOrg(auth.getUnitId());
			newRepay.setCreateUser(auth.getUserId());
			newRepay.setLastUpdateTm(new Date());
			newRepay.setLastUpdateOrg(auth.getUnitId());
			newRepay.setLastUpdateUser(auth.getUserId());
			
		} else {// 修改
			newRepay.setLastUpdateTm(new Date());
			newRepay.setLastUpdateOrg(auth.getUnitId());
			newRepay.setLastUpdateUser(auth.getUserId());
			updateApply1(newRepay.getId().toString());  		//修改狀態
		}
		String amount=newRepay.getAmount().toString();
		String discount=newRepay.getDiscountOccurAmt().toString();
		if(amount != null && !amount.equals("")){
			newRepay.setAmount(new BigDecimal(Double.parseDouble(amount)*1000));
		}else{
			newRepay.setAmount(new BigDecimal(0));
		}
		if(discount != null && !discount.equals("")){
			newRepay.setDiscountOccurAmt(new BigDecimal(Double.parseDouble(discount)*1000));
		}else{
			newRepay.setDiscountOccurAmt(new BigDecimal(0));
		}
		super.save(newRepay);
	}
	
	/**
	 * 删除：批量删除
	 * @param ids
	 */
		public void batchDestroy(String ids){
			Connection conn = null;//数据连接
	        Statement stat = null;//将 SQL 语句发送到数据库中
	        
	        try {
				conn = ds.getConnection();
				stat = conn.createStatement();
				
				String id[] = ids.split(",");
				for(int i=0;i<id.length;i++){
					StringBuilder sql = new StringBuilder();
					sql.append(" DELETE FROM OCRM_F_MK_NEW_REPAY A WHERE A.ID = '");
					sql.append(id[i]);
					sql.append("'");
					stat.addBatch(sql.toString());
				}
				stat.executeBatch();
			} catch (SQLException e) {
				throw new BizException(1, 2, "1002", e.getMessage());
			}finally{
				JdbcUtil.close(null, stat, conn);
			}
		}
		/**
		 * 查出序列，返回做apply_no,作为流程的拼接字符之一
		 * @param id
		 * @return
		 */
	public String selectSequence(String id){

		Connection conn = null;//数据连接
        Statement stat = null;//将 SQL 语句发送到数据库中
        String applyNo="";
        try {
			conn = ds.getConnection();
			stat = conn.createStatement();

				StringBuilder sql = new StringBuilder();
				StringBuilder sb = new StringBuilder("");
				sb.append(" select  DKXZJHK.nextval  as APPLYNO from dual  "
						+ " WHERE 1>0 ");// 查询序列
				// 对应的root为data
				String querySql = sb.toString();
				this.json = new QueryHelper(querySql, ds.getConnection()).getJSON();// 把查询语句赋给json
				List<?> list = (List<?>) ((Map<String,Object>) json).get("data");// 赋给list
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) list.get(0);// 获取第一个，即把查询出的数量赋值给MAP
				applyNo = (String) map.get("APPLYNO");
			stat.executeBatch();
		} catch (SQLException e) {
			throw new BizException(1, 2, "1002", e.getMessage());
		}finally{
			JdbcUtil.close(null, stat, conn);
		}
        return applyNo;
	}
	/**
	 * 修改后，更改状态
	 * @param id
	 */
	public void updateApply1(String id){
		Connection conn = null;//数据连接
        Statement stat = null;//将 SQL 语句发送到数据库中
        
        try {
			conn = ds.getConnection();
			stat = conn.createStatement();
			
				StringBuilder sql = new StringBuilder();
				sql.append(" update OCRM_F_MK_NEW_REPAY A  set approve_state='1' WHERE A.ID = '"+id+"'");
				stat.addBatch(sql.toString());
			
			stat.executeBatch();
		} catch (SQLException e) {
			throw new BizException(1, 2, "1002", e.getMessage());
		}finally{
			JdbcUtil.close(null, stat, conn);
		}
	}
	/**
	 * 流程发起后，更改状态
	 * @param applyno
	 * @param id
	 */
	public void updateApply(String applyno,String id){
		Connection conn = null;//数据连接
        Statement stat = null;//将 SQL 语句发送到数据库中
        
        try {
			conn = ds.getConnection();
			stat = conn.createStatement();
			
				StringBuilder sql = new StringBuilder();
				sql.append(" update OCRM_F_MK_NEW_REPAY A  set a.apply_no='"+applyno+"',approve_state='2' WHERE A.ID = '"+id+"'");
				stat.addBatch(sql.toString());
			
			stat.executeBatch();
		} catch (SQLException e) {
			throw new BizException(1, 2, "1002", e.getMessage());
		}finally{
			JdbcUtil.close(null, stat, conn);
		}
	}
	
	 /**
	  * 获取teamhead
	  * @return
	  */
	 @SuppressWarnings("unchecked")
	public String getTeamhead(String mgrId){
		String teamHead = "";
  	    List<AdminAuthAccount> ts = this.findByJql("select c from AdminAuthAccount c where c.accountName = '"+mgrId+"'", null);
  	    for(AdminAuthAccount t:ts){
  	    	teamHead = t.getBelongTeamHead();
  	    }
		 return teamHead;
	 }
}