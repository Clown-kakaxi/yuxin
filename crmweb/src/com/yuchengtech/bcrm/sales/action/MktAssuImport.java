package com.yuchengtech.bcrm.sales.action;

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
public class MktAssuImport implements ImportInterface{
	private static Logger log = Logger.getLogger(QueryHelper.class);

	public void excute(Connection conn, String PKhead,AuthUser aUser) throws Exception {
		log.info("updateSQL: 【ProbuyImport has been evoke!】");
		Statement stm = null;
		try{
			stm = conn.createStatement();
			conn.setAutoCommit(false);
			String updateSQL="select * from OCRM_F_MM_TASK ";
//			select * from COPY_OF_OCRM_F_MM_TASK into  OCRM_F_MM_TASK    主键字段类型不一致
			stm.execute(updateSQL);
//			String deleteSQL = "DELETE FROM OCRM_F_MM_MKT_INDEX_TEMP";
//			stm.execute(deleteSQL);
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