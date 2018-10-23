package com.yuchengtech.bcrm.callReport.action;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.callReport.model.OcrmFSeCallreportBusi;
import com.yuchengtech.bcrm.callReport.service.OcrmFSeCallreportBusiService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/**
 * callReport
 * @author agile
 *
 */
@SuppressWarnings("serial")
@Action("/ocrmFSeCallreportBusi")
public class OcrmFSeCallreportBusiAction extends CommonAction{
	@Autowired
	private OcrmFSeCallreportBusiService service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	@Autowired
	public void init(){
		model = new OcrmFSeCallreportBusi();
		setCommonService(service);
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx
	             .get(ServletActionContext.HTTP_REQUEST);
		StringBuilder sb = new StringBuilder();
		String callId = request.getParameter("callId");
		String callReport_callId = request.getParameter("callReport_callId");//未结案商机列表
		//查询和导出商机漏斗商机展示列表所需参数
		String opporStage = request.getParameter("opporStage");//销售漏斗 展示商机列表
		String prodName = request.getParameter("prodName");
		String orgName = request.getParameter("orgName");
		String mgrName = request.getParameter("mgrName");
		String opporStartDt = request.getParameter("opporStartDt");
		String opporEndDt = request.getParameter("opporEndDt");
		sb.append("select t.* from OCRM_F_SE_CALLREPORT_BUSI t " +
//				" left join ocrm_f_pd_prod_info info on t.product_id = info.product_id " +
				" where 1=1 ");
		if(callId!=null && !callId.isEmpty()){
			sb.append(" and t.call_id = '"+callId+"'");
		}
		if(callReport_callId!=null && !callReport_callId.isEmpty()){
			sb.append(" and t.sales_stage <> '5' ").append(" and call_id = '"+callReport_callId+"' ");
		}
		
		
		if(opporStage!=null && !opporStage.isEmpty()){
			sb.setLength(0);
			sb.append("select c.cust_name,t.* from OCRM_F_SE_CALLREPORT_BUSI t" +
					" left join OCRM_F_SE_CALLREPORT c on c.call_id = t.call_id " +
					" left join OCRM_F_CI_BELONG_CUSTMGR e on c.cust_id=e.cust_id " +
					" where 1=1 ");
			sb.append(" and t.SALES_STAGE = '"+opporStage+"' ");
			if(prodName != null && !prodName.isEmpty()){
				sb.append(" and t.PRODUCT_ID = '"+prodName+"' ");
			}
			if(orgName != null && !orgName.isEmpty() && !"undefined".equals(orgName)){
				sb.append(" and e.institution='"+orgName+"' ");
			}
			if(mgrName != null && !mgrName.isEmpty() && !"undefined".equals(mgrName)){
				sb.append(" and e.mgr_name = '"+mgrName+"'");
			}
			if(opporStartDt!=null && !opporStartDt.isEmpty()){
				sb.append(" and t.last_update_tm >= to_date('"+ opporStartDt+ "','yyyy-mm-dd')");
			}
			if(opporEndDt!=null && !opporEndDt.isEmpty()){
				sb.append(" and t.last_update_tm <= to_date('"+ opporEndDt+ "','yyyy-mm-dd')");
			}
		}
		
		SQL = sb.toString();
		if(opporStage!=null && !opporStage.isEmpty()){
			setPrimaryKey(" t.call_id desc");
		}else{
			setPrimaryKey(" t.id desc");
		}
//		setPrimaryKey(" t.id desc");
		setPrimaryKey(" t.BUSI_NAME,t.PRODUCT_ID,t.SALES_STAGE");
		addOracleLookup("FILE_TYPE", "FILE_TYPE");
		addOracleLookup("SALES_STAGE", "CALLREPORT_SAVES_STAGE");
		datasource = ds;
	}	
	
    // 删除
	public String batchDestroy() {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			String idStr = request.getParameter("idStr");
			String jql = "delete from OcrmFSeCallreportBusi c where c.id in ("
					+ idStr + ")";
			Map<String, Object> values = new HashMap<String, Object>();
			service.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
			return "success";
	}
	public void saveBusi(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		OcrmFSeCallreportBusi busi = new OcrmFSeCallreportBusi();
		String callId = request.getParameter("callId");
		String busiName = request.getParameter("busiName");
		String productId = request.getParameter("productId");
		String salesStage = request.getParameter("salesStage");
		String amount = request.getParameter("amount");
		String estimatedTime = request.getParameter("estimatedTime");
		//格式化预计成交时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date d = null;
	    try {
			d = df.parse(estimatedTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//格式化最后更新时间
	    Date dt = new Date();   
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
	    String  temp_str=sdf.format(dt); 
	    Date d1 = null;
		try {
			d1 = sdf.parse(temp_str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		busi.setCallId(new BigDecimal(callId));
		busi.setBusiName(busiName);
		busi.setProductId(productId);
		busi.setSalesStage(salesStage);
		busi.setAmount(new BigDecimal(amount));
		busi.setEstimatedTime(d);
		busi.setLastUpdateUser(auth.getUserId());
		busi.setLastUpdateTm(d1);
		
		if(checkBusi(busiName,callId)==0){
			service.saveBusi(busi);
		}else{
			throw new BizException(1, 0, "1002", "该商机已存在");
		}
		
		
	}
	/**
	 * 核对商机信息，判断商机是否已存在
	 * @param businame
	 * @param callid
	 * @return
	 */
	public int checkBusi(String businame,String callid){
		StringBuffer sb = new StringBuffer(" select * from OCRM_F_SE_CALLREPORT_BUSI where BUSI_NAME = '"+businame+"' and trim(to_char(CALL_ID)) = '"+callid+"'");
		QueryHelper query;
		List<HashMap<String, Object>> busi = null ;
		try {
			query = new QueryHelper(sb.toString(), ds.getConnection());
			busi = (List<HashMap<String, Object>>) query.getJSON().get("data");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int is_busi = busi.size();
		return is_busi;
	}
	/**
	 * 点击下一步，判断商机阶段是否符合往下进行
	 * @return
	 */
	public void checkBusiStage(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String callId = request.getParameter("callId");
		String busiNa = request.getParameter("busiName");
		String saleSt = request.getParameter("salesStage");
		StringBuffer sb = new StringBuffer(" select a.ID,a.CALL_ID,a.PRODUCT_ID, b.SALES_STAGE as NUM, " +
									       " case "+
									       "   when trim(a.SALES_STAGE)=1 then '商机产生' "+
									       "   when trim(a.SALES_STAGE)=2 then '联络沟通' "+
									       "   when trim(a.SALES_STAGE)=3 then '预约商谈' "+
									       "   when trim(a.SALES_STAGE)=4 then '提出方案' "+
									       "   when trim(a.SALES_STAGE)=5 then '成交' "+
									       "   when trim(a.SALES_STAGE)=6 then '失败' "+
									       "  else '暂无' "+
									       " end SALES_STAGE "+
				                           " from OCRM_F_SE_CALLREPORT_BUSI a" +
									       " inner join OCRM_F_SE_CALLREPORT_BUSI b on a.ID = b.ID "+
				                           " where a.BUSI_NAME = '"+busiNa+"' " +
				                           " and trim(to_char(a.CALL_ID)) = '"+callId+"' " +
				                           " and b.SALES_STAGE > '"+saleSt+"' order by NUM ");
		QueryHelper query;
		List<HashMap<String, Object>> busi = null ;
		try {
			query = new QueryHelper(sb.toString(), ds.getConnection());
			busi = (List<HashMap<String, Object>>) query.getJSON().get("data");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if("5".equals(saleSt) || "6".equals(saleSt)){
			throw new BizException(1, 0, "1002", "该商机已到最终阶段，不能进行下一步操作");
		}
		if(busi.size()!=0){
			if("成交".equals((String)busi.get(busi.size()-1).get("SALES_STAGE")) || 
			   "失败".equals((String)busi.get(busi.size()-1).get("SALES_STAGE"))){
				throw new BizException(1, 0, "1002", "该商机已到最终阶段，不能进行下一步操作");
			}else{
				throw new BizException(1, 0, "1002", "该商机已进行到"+(String)busi.get(busi.size()-1).get("SALES_STAGE")+"阶段，请选择该商机的"+(String)busi.get(busi.size()-1).get("SALES_STAGE")+"阶段进行下一步操作");
			}
		}
	}
	/**
	 * callReport点击下一步保存时，商机阶段不可逆
	 */
	public void busiNoBack(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String salesstagePro = request.getParameter("salesstagePro");
		String salesstageOff = request.getParameter("salesstageOff");

		if(Integer.parseInt(salesstagePro) > Integer.parseInt(salesstageOff)){
			throw new BizException(1, 0, "1002", "商机阶段不可逆，请重新选择阶段");
		}else if(Integer.parseInt(salesstagePro) == Integer.parseInt(salesstageOff)){
			throw new BizException(1, 0, "1002", "该商机阶段已存在，不能保存");
		}
	}
}
