package com.yuchengtech.bcrm.custview.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ibm.icu.text.SimpleDateFormat;
import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.belong.model.OcrmFCiBelongTrusteeship;
import com.yuchengtech.bcrm.custview.model.AcrmFCiEvent;
import com.yuchengtech.bcrm.custview.service.AcrmFCiEventInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.service.CommonQueryService;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/**
 * 客户视图大事件信息
 * @author agile
 *
 */
@SuppressWarnings("serial")
@Action("/acrmFCiEventInfo")
public class AcrmFCiEventInfoAction extends CommonAction{
	@Autowired
	private AcrmFCiEventInfoService service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private CommonQueryService cqs;
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	@Autowired
	public void init(){
		model = new AcrmFCiEvent();
		setCommonService(service);
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx
	             .get(ServletActionContext.HTTP_REQUEST);
	    String customerId = request.getParameter("custId");
        
		StringBuilder sb = new StringBuilder("");
		if(customerId != null){
		sb.append(" select at.user_name as REMIND_PPL_CM_NAME," +
				"  at1.user_name as REMIND_PPL_DRC_NAME," +
				" t.* from ACRM_F_CI_EVENT t" +
				" left join admin_auth_account at on t.REMIND_PPL_CM = at.account_name" +
				" left join admin_auth_account at1 on t.REMIND_PPL_DRC = at1.account_name  where 1=1 ");
			sb.append(" and t.cust_id = '"+customerId+"'");
		}
		
		for (String key : this.getJson().keySet()) {
	        if (null != this.getJson().get(key) && !this.getJson().get(key).equals("")) {
	            if (key.equals("EVENT_NAME"))
	                sb.append(" and t." + key + " like '%"+ this.getJson().get(key) + "%'");
	            else if (key.equals("EVENT_TYP"))
	                sb.append(" and t." + key + "= '" + this.getJson().get(key)+ "'");
	        }
		} 
		SQL = sb.toString();
		setPrimaryKey(" t.EVENT_ID desc");
		addOracleLookup("EVENT_TYP", "EVENT_TYP");
		addOracleLookup("WARN_FLG", "IF_FLAG");
		addOracleLookup("REMIND_PPL", "REMIND_OBJ_TYPE");
		datasource = ds;
	}	
	
	/**
     * 查询模板信息字典 提醒对象
     */
    public String searchRemindPpl(){
	    ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String custId = request.getParameter("custId");
		String userId = "";
	    String sql = "";
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		OcrmFCiBelongTrusteeship ship = (OcrmFCiBelongTrusteeship)service.findByJql("select * from OcrmFCiBelongTrusteeship p " +
				" where p.custId = '"+custId+"'", null);
		try {
			if(ship!=null){//托管客户
				String deadLine = ship.getDeadLine().toString();//托管有效期
				java.text.DateFormat df=new java.text.SimpleDateFormat("yyyy-MM-dd"); 
				java.util.Calendar c1=java.util.Calendar.getInstance(); 
				java.util.Calendar c2=java.util.Calendar.getInstance(); 
				c1.setTime(df.parse(deadLine)); 
				c2.setTime(df.parse(date));
				int result=c1.compareTo(c2); 
				if(result>=0){//托管在有效期内
					userId = ship.getTrustMgrId();
				}
			}
			sql ="select u.userid as key,u.userName as value,units.unitname,r.role_name from sys_users u "+
			    " LEFT JOIN SYS_UNITS units on u.unitid = units.id "+
			    " LEFT JOIN ADMIN_AUTH_ACCOUNT_ROLE AR ON AR.ACCOUNT_ID = U.id "+
			    " LEFT JOIN ADMIN_AUTH_ROLE R ON  R.ID = AR.ROLE_ID "+ 
			    " where ((units.unitid in ('"+auth.getUnitId()+"') "+ 
			    " AND R.Role_Name like '%主管%') or u.userid = '"+userId !=""?userId:auth.getUserId()+"')";
			map = cqs.excuteQuery(sql.toString(),0,200);
		    this.json = map;
			
		} catch (Exception e) {
			throw new BizException(0, 1, "1002", e.getMessage());
		}
		return "success";
    }
    // 删除
	public String batchDestroy() {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			long idStr = Long.parseLong(request.getParameter("messageId"));
			String jql = "delete from AcrmFCiEvent c where c.eventId in ("
					+ idStr + ")";
			Map<String, Object> values = new HashMap<String, Object>();
			service.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
			return "success";
	}
}
