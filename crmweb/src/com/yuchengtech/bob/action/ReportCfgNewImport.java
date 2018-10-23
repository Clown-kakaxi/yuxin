package com.yuchengtech.bob.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.yuchengtech.bob.importimpl.ImportInterface;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;


/**
 * 个金目标值导入： 2014-7-17shikunhao
 */
public class ReportCfgNewImport implements ImportInterface {
	private static Logger log = Logger.getLogger(ReportCfgNewImport.class);
	public void excute(Connection conn, String PKhead, AuthUser aUser)throws Exception {
		log.info("updateSQL: [ReportCfgNewImport has been evoke!]");		
		Statement stm = null;
		Statement delstm = null;
		Date now = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		List<?> list = aUser.getRolesInfo();
		try {
            conn.setAutoCommit(false);
			String tempSql = "select t.* from ACRM_M_TARGET_VAL_TEMP  t where t.order_id like '"+ PKhead + "%'";
			List dataList=read(conn,tempSql);
			System.out.println("导入的临时数据------------->"+dataList.size());			
			stm = conn.createStatement();	
			String insertDate=
				"insert into ACRM_M_TARGET_VAL" +
				"  select report_id," + 
				"         report_na," + 
				"         org_id," + 
				"         org_na," + 
				"         branch_id," + 
				"         branch_na," + 
				"         stat_na," + 
				"         stat_id," + 
				"         project," + 
				"         index_val," + 
				"         decode(updatetime,'null',' ',updatetime) updatetime " + 
				"    from ACRM_M_TARGET_VAL_TEMP where 1=1 and order_id like '"+ PKhead + "%'";
			System.out.println(insertDate);
			stm.execute(insertDate);			
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			try {
				String delSql = "delete from ACRM_M_TARGET_VAL_TEMP t where t.order_id like '"+ PKhead + "%'";
				stm.execute(delSql);
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			JdbcUtil.close(null, stm, conn);
		}
	}

	public void save(Connection conn, AuthUser aUser, String tempCustId,
			String custZhName, String custTyp, String linkUser, String zipcode,
			String custEnName, String certType, String linkPhone,
			String contmethInfo, String addr, String custStat,
			String otherName, String certNum, String jobType, String industType) {
		
					String rep="";
					String	sql = "insert into ACRM_F_CI_CUSTOMER(cust_id,cust_name,cust_type,linkman_name,en_name,ident_type,cust_stat,short_name,ident_no,create_teller_no,create_date,create_branch_no,POTENTIAL_FLAG,"
								+ "JOB_TYPE,indust_Type)" + " values('"
								+ rep
								+ "','"
								+ custZhName
								+ "','"
								+ custTyp
								+ "','"
								+ linkUser
								+ "','"
								+ custEnName
								+ "','"
								+ certType
								+ "','"
								+ custStat
								+ "','"
								+ otherName
								+ "','"
								+ certNum
//								+ "','"
//								+ currenUserId
//								+ "',"
//								+ date
//								+ ",'"
//								+ tempUnitId
								+ "','1',"
								+ "'"
								+ jobType
								+ "','"
								+ industType + "')";
					
						Statement insertStmtsql=null;
						try {
							insertStmtsql = conn.createStatement();
							insertStmtsql.execute(sql);
							insertStmtsql.close();
						} catch (SQLException e) {							
							throw new BizException(1, 0, "10000", "数据异常请检查数据格式！");
						}finally{
							JdbcUtil.close(null, insertStmtsql, null);//conn在导入的execute方法中关闭
						}
						
					}

	// 通过结果集元数据封装List结果集
	public static List<Map<String, Object>> read(Connection conn, String sql) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = ps.getMetaData();
			// 取得结果集列数
			int columnCount = rsmd.getColumnCount();
			List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
			Map<String, Object> data = null;
			// 循环结果集
			while (rs.next()) {
				data = new HashMap<String, Object>();
				// 每循环一条将列名和列值存入Map
				for (int i = 1; i < columnCount; i++) {
					data.put(rsmd.getColumnLabel(i),
							rs.getObject(rsmd.getColumnLabel(i)));
				}
				// 将整条数据的Map存入到List中
				datas.add(data);
			}
			return datas;
		} catch (Exception e) {
			throw new RuntimeException();
		} finally {
			JdbcUtil.close(rs, ps, null);//conn在导入的execute方法中关闭
		}
	}

}