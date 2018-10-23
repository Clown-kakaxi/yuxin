package com.yuchengtech.bcrm.callReport.action;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.callReport.model.OcrmFSeCallreportN;
import com.yuchengtech.bcrm.callReport.service.OcrmFSeCallreportNService;
import com.yuchengtech.bob.common.CommonAction;
/**
 * callReport
 * @author agile
 *
 */
@SuppressWarnings("serial")
@Action("/ocrmFSeCallreportN")
public class OcrmFSeCallreportNAction extends CommonAction{
	@Autowired
	private OcrmFSeCallreportNService service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init(){
		model = new OcrmFSeCallreportN();
		setCommonService(service);
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx
	             .get(ServletActionContext.HTTP_REQUEST);
		StringBuilder sb = new StringBuilder();
		String callReport_custId = request.getParameter("callReport_custId");
		sb.append("select t.* from(select to_date(p.HAPPENED_DATE,'yyyy-mm-dd') END_DATE1,p.DUE_AMT as AMOUNT,p.CUST_ID,n.id,p.product_no as product_id,p.PRODUCT_NAME,n.SEQUEL_STAGE," +
				" n.SEQUEL_AMOUNT,n.OUT_AMOUNT,n.FAIL_REASON,n.CASE_STAGE," +
				" n.REMARK,n.LAST_UPDATE_USER,n.LAST_UPDATE_TM " +
				" from OCRM_F_WP_REMIND P " +
				" left join OCRM_F_SE_CALLREPORT_N N ON N.CUST_ID = p.CUST_ID " +
				" where 1=1 and ( n.CASE_STAGE  is null or  n.CASE_STAGE <> '1')  ");
	     
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());   
        c.add(c.MONTH, 1);
        Date temp_month = c.getTime(); 
        
		if(callReport_custId!=null && !callReport_custId.isEmpty()){
			sb.append(" and P.CUST_ID = '"+callReport_custId+"'");
//			sb.append(" and ( p.HAPPENED_DATE <= to_date('"+ new SimpleDateFormat("yyyy-MM-dd").format(temp_month)+"','yyyy-mm-dd') or " +
//					" (to_date(to_char(p.HAPPENED_DATE,'yyyy-mm-dd'),'yyyy-mm-dd') - to_date('"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"','yyyy-mm-dd'))>=3 )");
			sb.append(" and p.rule_code in ('202','203')");//202 定期存款到期 203理财产品到期
		}
		sb.append(" ORDER BY END_DATE1  desc )t  where ROWNUM<=10");
		SQL = sb.toString();
//		setPrimaryKey(" N.id desc");
		datasource = ds;
	}	
	
    // 删除
	public String batchDestroy() {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			String idStr = request.getParameter("idStr");
			String jql = "delete from OcrmFSeCallreportN c where c.id in ("
					+ idStr + ")";
			Map<String, Object> values = new HashMap<String, Object>();
			service.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
			return "success";
	}
}
