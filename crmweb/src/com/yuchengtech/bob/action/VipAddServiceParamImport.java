package com.yuchengtech.bob.action;

import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.importimpl.ImportInterface;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;


//@Action("/marketing-news")
//@Results({
//    @Result(name="success", type="redirectAction", params = {"actionName" , "marketing-news"})
//})
public class VipAddServiceParamImport implements ImportInterface{
	private static Logger log = Logger.getLogger(QueryHelper.class);

	public void excute(Connection conn, String PKhead,AuthUser aUser) throws Exception {
		log.info("updateSQL: 【VipAddServiceParamImport has been evoke!】");
		Statement stm = null;
		Date now=new Date();
		  SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
		try{
			stm = conn.createStatement();
			conn.setAutoCommit(false);
			String updateSQL="INSERT INTO OCRM_F_CI_VIPADDPARAM_SET(" +
					"ID,VIP_CARD_LEVEL" +
					",CREATE_DATE,CREATE_USER" +
					",CREATE_ORG,PROVIDER_NAME" +
					",ADD_SERVICE_NAME,ADD_SERVICE_IDENTIFY" +
					",RANGE_APPLY,SERVICE_CONTENT" +
					",REMARK)" +
					" select ID_SEQUENCE.nextval," +
					"t.VIP_CARD_LEVEL,to_date('"+f.format(now)+"','yyyy-MM-dd')" +
					",'"+aUser.getUnitId()+"'"+aUser.getUnituid()+"'" +
					",t.PROVIDER_NAME,t.ADD_SERVICE_NAME" +
					",t.ADD_SERVICE_IDENTIFY,t.RANGE_APPLY" +
					",t.SERVICE_CONTENT,t.REMARK" +
					" FROM OCRM_F_CI_VIPADDPARAM_TEMP t where t.ID like '"+PKhead+"%'";
			stm.execute(updateSQL);
			String deleteSQL = "DELETE FROM OCRM_F_CI_VIPADDPARAM_TEMP t where t.ID like '"+PKhead+"%'";
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