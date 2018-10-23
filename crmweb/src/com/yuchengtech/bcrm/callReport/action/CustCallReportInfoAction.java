package com.yuchengtech.bcrm.callReport.action;

import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.LogService;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.crm.exception.BizException;

@SuppressWarnings("serial")
@Action("/custcallreportinfo")
public class CustCallReportInfoAction extends CommonAction {
	
    private DataSource ds=LogService.dsOracle ;
	
	public List<HashMap<String, Object>> sourchCallReport(String custid){
		StringBuffer sb = new StringBuffer(" select a.CALL_ID,a.CUST_ID,a.VISIT_DATE,"+
										      " case "+
										      "   when trim(a.VISIT_WAY)=1 then '电访' "+
										      "   when trim(a.VISIT_WAY)=2 then '亲访' "+
										      "   when trim(a.VISIT_WAY)=3 then '来行约谈' "+
										      "   when trim(a.VISIT_WAY)=4 then '客户活动' "+
										      "   else '其他' "+
										      " end VISIT_WAY, "+
										     " a.BEGIN_DATE, "+
										     " a.END_DATE, "+
				                             " a.MKT_BAK_NOTE,"+
										      " case "+
										      "   when trim(b.SALES_STAGE)=1 then '商机产生' "+
										      "   when trim(b.SALES_STAGE)=2 then '联络沟通' "+
										      "   when trim(b.SALES_STAGE)=3 then '预约商谈' "+
										      "   when trim(b.SALES_STAGE)=4 then '提出方案' "+
										      "   when trim(b.SALES_STAGE)=5 then '成交' "+
										      "   when trim(b.SALES_STAGE)=6 then '失败' "+
										      "   else '暂无' "+
										      " end SALES_STAGE, "+
				                             " a.NEXT_VISIT_DATE "+
				                           " from OCRM_F_SE_CALLREPORT a "+
                                           " left join OCRM_F_SE_CALLREPORT_BUSI b on a.call_id = b.call_id ");
		sb.append(" where a.CUST_ID ='" +custid+"'");
		sb.append(" and rownum < 6 ");
		sb.append(" order by a.VISIT_DATE desc,a.BEGIN_DATE desc ");
		
		
		List<HashMap<String, Object>> callreport = null;
		QueryHelper query = null;
		try {
			query = new QueryHelper(sb.toString(), ds.getConnection());
			callreport = (List<HashMap<String, Object>>) query.getJSON().get("data");
			
				return callreport;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
	}
}
