package com.yuchengtech.bob.action;

import java.sql.Connection;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.importimpl.ImportInterface;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;


//@Action("/marketing-news")
//@Results({
//    @Result(name="success", type="redirectAction", params = {"actionName" , "marketing-news"})
//})
public class VipEnjoyServiceRecordImport implements ImportInterface{
	private static Logger log = Logger.getLogger(QueryHelper.class);

	public void excute(Connection conn, String PKhead,AuthUser aUser) throws Exception {
		log.info("updateSQL: 【VipEnjoyServiceRecordImport has been evoke!】");
		Statement stm = null;
		try{
			stm = conn.createStatement();
			conn.setAutoCommit(false);
			String updateSQL="INSERT INTO OCRM_F_CI_VIP_ENJOY_SERVICE(" +
					"ID,CUST_ID" +
					",CUST_NAME,ALIANCE_PROGRAM_ID" +
					",ALIANCE_PROGRAM_NAME,ADD_SERVICE_IDENTIFY" +
					",SERVICE_CONTENT,ENJOY_SERVICE_DATE)" +
					" select ID_SEQUENCE.nextval," +
					"t.CUST_ID" +
					",t.CUST_NAME,t.ALIANCE_PROGRAM_ID" +
					",t.ALIANCE_PROGRAM_NAME,t.ADD_SERVICE_IDENTIFY" +
					",t.SERVICE_CONTENT,to_date('t.ENJOY_SERVICE_DATE','yyyy-MM-dd')" +
					" FROM OCRM_F_CI_VIP_ENJOY_TEMP t where t.ID like '"+PKhead+"%'";
			stm.execute(updateSQL);
			String deleteSQL = "DELETE FROM OCRM_F_CI_VIP_ENJOY_TEMP t where t.ID like '"+PKhead+"%'";
			stm.execute(deleteSQL);
			log.info("updateSQL: 【" + updateSQL + "】");
			//}
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			JdbcUtil.close(null, stm, conn);
		}
	}

	

}