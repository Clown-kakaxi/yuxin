package com.yuchengtech.bcrm.workplat.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 
 * 提醒查询
 * 首页磁贴显示查询
 * @author lianghe
 * @since 2016-3-14
 */

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/remindListQueryNewAction", 
results = { @Result(name = "success", type = "json")})
public class RemindListQueryNewAction extends CommonAction {

	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
   
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	/**
	 *信息查询SQL
	 */
	public void prepare() {
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 StringBuilder sb = new StringBuilder("select it.f_value as rule_code,'('||count(1)||')' as totalNo from OCRM_F_WP_REMIND tt" +
    	 		" inner join ocrm_sys_lookup_item it on tt.rule_code = it.f_code" +
    	 		//" and F_LOOKUP_ID like '%REMIND_TYPE%'  where tt.user_id = '"+auth.getUserId()+"' and F_CODE IN ('300', '301', '306', '307', '308', '309', '310', '311')" +
    	 		" and it.F_LOOKUP_ID = 'REMIND_TYPE_QUERY'  where tt.user_id = '"+auth.getUserId()+"' " +
    	 		" and tt.MSG_START_DATE<=trunc(SYSDATE)"+
    	 		" and not exists (select *from OCRM_F_WP_REMIND_READ rr  where rr.remind_id = tt.info_id)");
    	 sb.append("group by it.f_value");
//    	 StringBuilder sb = new StringBuilder("select * from (select "
//    	 	+" tt.info_id,tt.rule_id,it.f_value as rule_code,tt.cust_id,tt.cust_name,tt.birthday_m,tt.birthday_s,tt.change_account,tt.change_amt "
//			+" ,tt.account_amt,tt.due_amt,tt.product_amt,tt.fund_amt,tt.new_mgr,tt.old_mgr,tt.operate_mgr,tt.product_no,tt.product_name,tt.happened_date "
//			+" ,tt.before_level,tt.after_level,tt.score_change,tt.acore_amt,tt.active_name,tt.msg_crt_date,tt.msg_end_date,case when ROUND(NVL((tt.msg_end_date-SYSDATE),0)) < 0 then 0 else ROUND(NVL((tt.msg_end_date-SYSDATE),0)) end  AS LAST_DATE,tt.remind_remark "
//			+" ,tt.message_remark,tt.if_message,tt.if_call,tt.user_id,a.user_name,tt.mail_remark,tt.micro_remark,tt.if_mail,tt.if_micro"
//    	 	+ ",ue.message_to_cust_channel as SEND_MANTHED,decode(rr.id,'','0', null,'0','1') as read, " +
//    	 		"cc.mobile_phone,cc.weixin,cc.email  from OCRM_F_WP_REMIND tt  " +
//    	 		"left join ACRM_F_CI_PERSON cc on tt.cust_id = cc.cust_id " +
//    	 		"left join OCRM_F_WP_REMIND_RULE ue on ue.rule_code = tt.rule_code " +
//    	 		"left join ADMIN_AUTH_ACCOUNT A on A.ACCOUNT_NAME = tt.user_id " +
//    	 		"left join OCRM_F_WP_REMIND_READ rr on rr.remind_id=tt.info_id " +
//    	 		"left join ocrm_sys_lookup_item it on tt.rule_code = it.f_code where 1 > 0 and( it.F_LOOKUP_ID like '%REMIND_TYPE%' or tt.rule_code is null) "+
//    	 		"  )  R where 1=1 and r.read = '0' and r.user_id = '"+auth.getUserId()+"'");
//    	 sb.append(" order by r.MSG_CRT_DATE asc ");
    	
		SQL=sb.toString();
		datasource = ds;
	}
}
