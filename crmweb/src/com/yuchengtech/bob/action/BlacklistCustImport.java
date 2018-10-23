package com.yuchengtech.bob.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.yuchengtech.bob.importimpl.ImportInterface;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;

/**
 * @date 2014-09-24
 * @author wzy
 * @desc 客户群管理功能模块中的黑名单客户导入功能处理类
 */
public class BlacklistCustImport implements ImportInterface {
	private static final Logger LOG = Logger
			.getLogger(BlacklistCustImport.class);// 日志对象

	// 执行导入操作
	// 逻辑处理:
	// 1、将导入客户信息放入客户信息表ACRM_F_CI_CUSTOMER（POTENTIAL_FLAG-潜在客户标志：置成1）
	// 2、建立客户群和客户之间关系，在表OCRM_F_CI_RELATE_CUST_BASE中插入数据
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void excute(Connection conn, String PKhead, AuthUser aUser)
			throws Exception {
		LOG.debug("===客户群管理-->黑名单客户导入-->开始......");
		Statement st = null;
		ResultSet rs = null;
		ResultSet rt3 = null;
		ResultSetMetaData rsmd2 = null;
		int colCount = -1;
		Date now = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		List<?> li = aUser.getRolesInfo();// 获取当前登陆人的角色
		boolean ifkejingl = false;// 判断当前登陆人是否是客户经理角色
		try {
			st = conn.createStatement();
			conn.setAutoCommit(false);// 不自动提交
			// 更新客户信息临时表：从Excel导入的客户信息存放在临时表中，将证件类型、客户类型，由“编码-中文”更新成编“编码”的格式
			String upCodeSql = "update acrm_f_ci_customer_temp t ";
			upCodeSql += "set t.ident_type = substr(t.ident_type, 0,instr(t.ident_type,'-')-1),";
			upCodeSql += "t.cust_type = substr(t.cust_type, 0, instr(t.cust_type,'-')-1)";
			st.execute(upCodeSql);
			// 根据客户三要素进行判断，如果重复，将临时表数据中对应数据删除
			String sql = "select t.cust_name,t.ident_type,t.ident_no ";
			sql += " from acrm_f_ci_customer_temp t ";
			sql += " where t.cust_id like '" + PKhead + "%'";
			rs = st.executeQuery(sql);
			rsmd2 = rs.getMetaData();
			colCount = rsmd2.getColumnCount();
			while (rs.next()) {
				Map paraMap = new HashMap();
				for (int i = 1; i <= colCount; i++) {
					paraMap.put(rsmd2.getColumnName(i),
							rs.getObject(rsmd2.getColumnName(i)));
				}
				String serSql = "select t.* from acrm_f_ci_customer t ";
				serSql += " where t.cust_name ='" + paraMap.get("CUST_NAME");
				serSql += "' and t.ident_type ='" + paraMap.get("IDENT_TYPE");
				serSql += "' and t.ident_no ='" + paraMap.get("IDENT_NO") + "'";
				Statement stm2 = null;
				ResultSet rt2 = null;
				try {
					stm2 = conn.createStatement();
					rt2 = stm2.executeQuery(serSql);
					if (rt2.next()) {
						Statement stm3 = null;
						try {
							stm3 = conn.createStatement();
							String delSql2 = "delete from ACRM_F_CI_CUSTOMER_TEMP t where t.cust_name = '"
									+ rt2.getString("cust_name")
									+ "' and t.IDENT_NO ='"
									+ rt2.getString("IDENT_NO")
									+ "'";
							stm3.execute(delSql2);
						} finally{
							JdbcUtil.close(null, stm3, null);
						}
					}
				} finally{
					JdbcUtil.close(rt2, stm2, null);
				}
			}
			Random r = new Random();
			String custId = "crm" + r.nextInt();
			String updateSQL = "INSERT INTO ACRM_F_CI_CUSTOMER(CUST_ID,CUST_NAME,CUST_TYPE,LINKMAN_NAME,EN_NAME,IDENT_TYPE,LINKMAN_TEL,CUST_STAT,SHORT_NAME,IDENT_NO,POTENTIAL_FLAG,SOURCE_CHANNEL)"
					+ " SELECT ID_SEQUENCE.NEXTVAL,T.CUST_NAME,T.CUST_TYPE,T.LINKMAN_NAME,T.EN_NAME,T.IDENT_TYPE,T.LINKMAN_TEL,'2',T.SHORT_NAME,T.IDENT_NO, '1'"
					+ ",T.SOURCE_CHANNEL"
					+ " FROM ACRM_F_CI_CUSTOMER_TEMP T WHERE T.CUST_ID LIKE '"
					+ PKhead + "%'";
			st.execute(updateSQL);
			LOG.info("updateSQL: 【" + updateSQL + "】");
			String sql3 = "select t.CUST_NAME,t.IDENT_TYPE,t.IDENT_NO from ACRM_F_CI_CUSTOMER_TEMP t where 1=1 and t.CUST_ID like '"
					+ PKhead + "%'";
			rt3 = st.executeQuery(sql3);
			ResultSetMetaData rsmd3 = rt3.getMetaData();
			int columnCount3 = rsmd3.getColumnCount();
			while (rt3.next()) {
				Map map2 = new HashMap();
				for (int i = 1; i <= columnCount3; i++) {
					map2.put(rsmd3.getColumnName(i),
							rt3.getObject(rsmd3.getColumnName(i)));
				}
				String dosSql = "select t.* from ACRM_F_CI_CUSTOMER t where 1=1 and t.cust_name ='"
						+ map2.get("CUST_NAME")
						+ "' and t.ident_type ='"
						+ map2.get("IDENT_TYPE")
						+ "' and t.ident_no ='"
						+ map2.get("IDENT_NO") + "'";
				Statement stm4 = null;
				ResultSet rt4 = null;
				try {
					stm4 = conn.createStatement();
					rt4 = stm4.executeQuery(dosSql);
					if (rt4.next()) {
						Statement stm6 = null;
						Statement stm7 = null;
						try {
							stm6 = conn.createStatement();
							stm7 = conn.createStatement();
							String delSql3 = "";
							String delSql4 = "";
							for (Object m : li) {
								Map map123 = (Map) m;
								// 个金ARM或法金ARM为客户经理
								if ("R302".equals(map123.get("ROLE_CODE"))
										|| "R304".equals(map123.get("ROLE_CODE"))) {
									ifkejingl = true;
									break;
								}
							}
							if (ifkejingl) {// 是客户经理时
								delSql3 = " INSERT INTO OCRM_F_CI_BELONG_CUSTMGR (ID,CUST_ID,MGR_ID,MAIN_TYPE,MAINTAIN_RIGHT,"
										+ // 把导入的潜在客户归属到当前登录的人
										"CHECK_RIGHT,ASSIGN_USER,ASSIGN_USERNAME,ASSIGN_DATE,INSTITUTION,INSTITUTION_NAME,MGR_NAME)"
										+ " VALUES(ID_SEQUENCE.nextval,'"
										+ rt4.getString("CUST_ID")
										+ "','"
										+ aUser.getUserId()
										+ "','1',"
										+ "'1','1','"
										+ aUser.getUserId()
										+ "','"
										+ aUser.getUsername()
										+ "',to_date('"
										+ f.format(now)
										+ "','yyyy-MM-dd'),'"
										+ aUser.getUnitId()
										+ "',"
										+ "'"
										+ aUser.getUnitName()
										+ "','"
										+ aUser.getUsername() + "')";

							} else {// 不是客户经理时
								delSql3 = " INSERT INTO OCRM_F_CI_BELONG_CUSTMGR (ID,CUST_ID,MGR_ID,MAIN_TYPE,MAINTAIN_RIGHT,"
										+ // 把导入的潜在客户归属到当前登录的人
										"CHECK_RIGHT,ASSIGN_USER,ASSIGN_USERNAME,ASSIGN_DATE,INSTITUTION,INSTITUTION_NAME,MGR_NAME)"
										+ " VALUES(ID_SEQUENCE.nextval,'"
										+ rt4.getString("CUST_ID")
										+ "','"
										+ "VM"
										+ aUser.getUserId()
										+ "','1',"
										+ "'1','1','"
										+ aUser.getUserId()
										+ "','"
										+ aUser.getUsername()
										+ "',to_date('"
										+ f.format(now)
										+ "','yyyy-MM-dd'),'"
										+ aUser.getUnitId()
										+ "',"
										+ "'"
										+ aUser.getUnitName()
										+ "','"
										+ aUser.getUnitName() + "虚拟客户经理" + "')";
							}
							// 归属机构保存
							delSql4 = " INSERT INTO OCRM_F_CI_BELONG_ORG (ID,CUST_ID,INSTITUTION_CODE,INSTITUTION_NAME,MAIN_TYPE,"
									+ // 把导入的潜在客户归属到当前登录的人
									"ASSIGN_USER,ASSIGN_USERNAME,ASSIGN_DATE)"
									+ " VALUES(ID_SEQUENCE.nextval,'"
									+ rt4.getString("CUST_ID")
									+ "','"
									+ aUser.getUnitId()
									+ "','"
									+ aUser.getUnitName()
									+ "',"
									+ "'1','"
									+ aUser.getUserId()
									+ "','"
									+ aUser.getUsername()
									+ "',to_date('"
									+ f.format(now) + "','yyyy-MM-dd'))";
							stm6.execute(delSql3);
							stm7.execute(delSql4);
						} finally{
							JdbcUtil.closeStatement(stm6);
							JdbcUtil.closeStatement(stm7);
						}
					}
				} finally{
					JdbcUtil.close(rt4, stm4, null);
				}
			}
			String delSql = "delete from ACRM_F_CI_CUSTOMER_TEMP t where t.CUST_ID like '"
					+ PKhead + "%'";
			st.execute(delSql);
			conn.commit();// 提交事务
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();// 事务回滚
		} finally {
			try {
				String delSql = "delete from ACRM_F_CI_CUSTOMER_TEMP t where t.CUST_ID like '"
						+ PKhead + "%'";
				st.execute(delSql);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			JdbcUtil.closeResultSet(rt3);
			JdbcUtil.close(rs, st, conn);
		}
		LOG.debug("===客户群管理-->黑名单客户导入-->结束......");
	}

}
