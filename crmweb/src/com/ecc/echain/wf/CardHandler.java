package com.ecc.echain.wf;

import java.sql.SQLException;

import javax.servlet.jsp.jstl.sql.Result;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
/***
 * 客户发卡申请
 * @author luyueyue
 *
 */
public class CardHandler extends EChainCallbackCommon{
	// 通过处理
	public void endY(EVO vo){
		
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			String sql1 = " update ACRM_A_CI_CARD_APPLY set CARD_APP_STATUS = '2' where id = "+instanceids[1]+" ";
			
			SQL = " select id,decode(CARD_APP_VALIDATE,null,'no','','no','yes') as ifDate  from ACRM_A_CI_CARD_APPLY where id = "+instanceids[1]+" ";
			Result rs = querySQL(vo);
			String ifDate = (String) rs.getRows()[0].get("IFDATE");
			if("no".equals(ifDate)){//如果没有设置有效期，在系统参数表中查询有效期
				SQL = "SELECT d.id,d.prop_value  FROM FW_SYS_PROP D where d.prop_name='CARD_EFFECT_DATE'";
				Result rs1 = querySQL(vo);
				if(rs1.getRowCount() == 0){//参数缺失，直接 +90天
					String sql2 = " update ACRM_A_CI_CARD_APPLY set CARD_APP_VALIDATE =sysdate+90  where id = "+instanceids[1]+" ";
					SQLS.add(sql2);
				}else {
					String days = (String) rs1.getRows()[0].get("PROP_VALUE");
					String sql2 = " update ACRM_A_CI_CARD_APPLY set CARD_APP_VALIDATE =sysdate+"+days+"  where id = "+instanceids[1]+" ";
					SQLS.add(sql2);
				}
			}
			SQLS.add(sql1);
			executeBatch(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}

	}
	
}
