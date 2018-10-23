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
public class VipSosPickUpServiceImport implements ImportInterface{
	private static Logger log = Logger.getLogger(QueryHelper.class);

	public void excute(Connection conn, String PKhead, AuthUser aUser) throws Exception {
		log.info("updateSQL: 【VipSosPickUpServiceImport has been evoke!】");
		Statement stm = null;
		try{
			stm = conn.createStatement();
			conn.setAutoCommit(false);
			String updateSQL="INSERT INTO ACRM_F_SOS_SERVICE(" +
					"ID,CUST_CORE_ID" +
					",CUST_NAME,SERVICE_DAY" +
					",SERVICE_TIMES,SERVICE_REMNANT" +
					",SERVICE_STARTTIME,SERVICE_ENDTIME)" +
					" select ID_SEQUENCE.nextval," +
					"t.CUST_CORE_ID" +
					",t.CUST_NAME,to_date(t.SERVICE_DAY,'yyyy-MM-dd')" +
					",t.SERVICE_TIMES,t.SERVICE_REMNANT" +
					",to_date(t.SERVICE_STARTTIME,'yyyy-MM-dd'),to_date(t.SERVICE_ENDTIME,'yyyy-MM-dd')" +
					" FROM ACRM_F_SOS_SERVICE_TEMP t where t.ID like '"+PKhead+"%'";
			stm.execute(updateSQL);
			String deleteSQL = "DELETE FROM ACRM_F_SOS_SERVICE_TEMP t where t.ID like '"+PKhead+"%'";
			stm.execute(deleteSQL);
			log.info("updateSQL: 【" + updateSQL + "】");
			//}
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			String deleteSQL2 = "DELETE FROM ACRM_F_SOS_SERVICE_TEMP t where t.ID like '"+PKhead+"%'";//插入失败 删除临时表数据
			stm.execute(deleteSQL2);
			JdbcUtil.close(null, stm, conn);
		}
	}

	

}