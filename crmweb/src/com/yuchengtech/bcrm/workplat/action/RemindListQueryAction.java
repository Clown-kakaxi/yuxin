package com.yuchengtech.bcrm.workplat.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.sales.message.action.MailClient;
import com.yuchengtech.bcrm.sales.message.action.MsgClient;
import com.yuchengtech.bcrm.workplat.model.OcrmFWpRemindMsg1;
import com.yuchengtech.bcrm.workplat.model.OcrmFWpRemindRead;
import com.yuchengtech.bcrm.workplat.service.OcrmFWpRemindMsgService;
import com.yuchengtech.bcrm.workplat.service.OcrmFWpRemindReadService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * 
 * 信息提醒处理类
 * @author hujun
 * @since 2014-7-10
 */

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/remindListQueryAction", 
results = { @Result(name = "success", type = "json")})
public class RemindListQueryAction extends CommonAction {
	
	private static Logger log =LoggerFactory.getLogger(RemindListQueryAction.class);
	
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
   
	 @Autowired
	 private  OcrmFWpRemindReadService  ocrmFWpRemindReadService;
	 @Autowired
	 private OcrmFWpRemindMsgService  ocrmFWpRemindMsgService;
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	 @Autowired
		public void init(){
		  	model = new OcrmFWpRemindRead(); 
			setCommonService(ocrmFWpRemindReadService);
			//新增修改删除记录是否记录日志,默认为false，不记录日志
			needLog=true;
		}
	/**
	 *信息查询SQL
	 */
	public void prepare() {
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 String custId = request.getParameter("callReport_custId");//callReport 事件信息查询显示
    	 StringBuilder sb = new StringBuilder("select * from (select tt.info_id,tt.rule_id,tt.rule_code,tt.cust_id,tt.cust_name "
			+" ,tt.msg_crt_date,tt.msg_end_date,tt.MSG_START_DATE,case when ROUND(NVL((tt.msg_end_date-SYSDATE),0)) < 0 then 0 else ROUND(NVL((tt.msg_end_date-SYSDATE),0)) end  AS LAST_DATE,tt.remind_remark "
			+" ,tt.user_id,a.user_name"
    	 	+ ",decode(rr.id,'','0', null,'0','1') as read " 
			//+ " ,TT.CHANGE_AMT,TT.HAPPENED_DATE HAPPENED_DATE1,TT.HAPPENED_DATE HAPPENED_DATE2,TT.HAPPENED_DATE HAPPENED_DATE3,TT.BIRTHDAY_S,TT.DUE_AMT DUE_AMT1,TT.DUE_AMT DUE_AMT2 " +
    	 	+ " ,TT.CHANGE_AMT,TT.CHANGE_AMT CHANGE_AMT2,TT.HAPPENED_DATE HAPPENED_DATE,TT.HAPPENED_DATE HAPPENED_DATE2,TT.BIRTHDAY_S,TT.DUE_AMT DUE_AMT,TT.DUE_AMT DUE_AMT2,TT.PRODUCT_NAME,to_char(info.CALLREPORT_INFO) CALLREPORT_INFO  " +
    	 		"from OCRM_F_WP_REMIND tt " +
    	 		"left join ADMIN_AUTH_ACCOUNT A on A.ACCOUNT_NAME = tt.user_id " +
    	 		"left join (select cust_id,callreport_info from (SELECT T.*,row_number() over(partition by t.cust_id order by t.create_tm desc) rn FROM OCRM_F_CI_CALLREPORT_INFO T) where rn=1) info on tt.CUST_ID = info.CUST_ID "+
    	 		"left join OCRM_F_WP_REMIND_READ rr on rr.remind_id=tt.info_id)  R where "+
    	 		" exists(select * from ocrm_sys_lookup_item t1 where t1.f_lookup_id='REMIND_TYPE_QUERY' and t1.f_code=r.rule_code) "+
    	 		//" RULE_CODE IN ('300', '301', '306', '307', '308', '309', '310', '311') "+
    	 		" and r.user_id = '"+auth.getUserId()+"'");
    	if(custId != null && !custId.isEmpty()){
    		sb.append(" and cust_id = '"+custId+"'");
    	}
    	for(String key:this.getJson().keySet()){
             if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
            	 if(null!=key&&key.equals("READ")){
            		 sb.append("  and  READ = "+this.getJson().get(key));
            	 }/*else if(null!=key&&key.equals("MSG_END_DATE")){
            		 sb.append("  and  MSG_END_DATE = to_date('"+this.getJson().get(key)+"','yyyy-mm-dd')");
            	 }else if(null!=key&&key.equals("MSG_CRT_DATE")){
            		 sb.append("  and  MSG_CRT_DATE = to_date('"+this.getJson().get(key)+"','yyyy-mm-dd')");
            	 }*/
             }
         }
   	
    	setPrimaryKey(" read,MSG_END_DATE asc");
//    	addOracleLookup("RULE_CODE", "REMIND_TYPE");
		SQL=sb.toString();
		datasource = ds;
//		configCondition("READ","=","READ",DataType.String);
     	configCondition("RULE_CODE","like","RULE_CODE",DataType.String);
     	configCondition("MSG_END_DATE","=","MSG_END_DATE",DataType.Date);
     	configCondition("MSG_CRT_DATE","=","MSG_CRT_DATE",DataType.Date);
//     	configCondition("CUST_NAME","like","CUST_NAME",DataType.String);
	}
	/**
	 * 未读设为已读
	 */
	public void read(){
		ActionContext ctx = ActionContext.getContext();
   	 	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	 	String id = request.getParameter("id");//数据id
   	 	String read = request.getParameter("read");//是否已读，0为未读，1为已读
   	 	if(read.equals("0")){
	   	 	OcrmFWpRemindRead info = new OcrmFWpRemindRead();
	   	 	info.setReadTime(new Date());
	   	 	info.setRemindId(BigDecimal.valueOf(Long.parseLong(id)));
	   	 	info.setUserId(auth.getUserId());
	   	    ocrmFWpRemindReadService.save(info);
   	 	}
	}
	/**
	 * 点击发送短信处理
	 */
	public void send(){
		try{
		ActionContext ctx = ActionContext.getContext();
   	 	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	 	String ifChannel = request.getParameter("ifChannel");
   	 	String id = request.getParameter("id");
   	    String custId = request.getParameter("custId");
   	    String custName = request.getParameter("custName");
   	    String messageRemark = request.getParameter("messageRemark");
   	    String number = request.getParameter("number");
   	    String weixin = request.getParameter("weixin");
   	    String email = request.getParameter("email");
   	    String weixinRemark=request.getParameter("weixinRe");
   	    String emailRemark=request.getParameter("emailre");
   	    //String qudao=request.getParameter("sendChannel");
   	    String[] s=ifChannel.split(",");
   	    if(s.length>0){
   	    	for(int i=0;s.length>i;i++){
   	    		if(s[i].equals("1")){//发送短信
	    			if(number!=null && !number.trim().equals("")){
	    				MsgClient.getInstance().process(number, messageRemark, "");
	    				//添加到发送记录
	    				OcrmFWpRemindMsg1 msg = new OcrmFWpRemindMsg1();
	    				
	    				msg.setRemindId(BigDecimal.valueOf(Long.parseLong(id)));
	    				msg.setCtrDate(new Date());
	    				msg.setSendDate(new Date());
	    				msg.setMessageRemark(messageRemark);
	    				msg.setCellNumber(number);
	    				msg.setCustId(custId);
	    				msg.setCustName(custName);
	    				msg.setIfSend("1");
	    				msg.setUserId(auth.getUserId());
	    				
	    				ocrmFWpRemindMsgService.save(msg);
	    				//修改发送状态
	    				String jql = "update  OcrmFWpRemindLast p set p.ifMessage=:ifMessage where p.infoId ='"+id+"'";
	    				Map<String,Object> values = new HashMap<String,Object>();
	    				values.put("ifMessage", "1");
	    				ocrmFWpRemindReadService.batchUpdateByName(jql, values);
	    			}else{
	    				throw new BizException(1, 0, "10001", "客户手机号码为空,短信发送失败!");
	    			}
   	    		}
   	    		if(s[i].equals("2")){//发送邮件
      	    	     if(email!=null && !email.trim().equals("")){
      	    	    	 MailClient.getInstance().sendMsg(email, "富邦华一银行事件提醒", emailRemark);
      	    	    	 //添加到发送记录
      	    	    	 OcrmFWpRemindMsg1 msg = new OcrmFWpRemindMsg1();
      	    	    	 
      	    	    	 msg.setRemindId(BigDecimal.valueOf(Long.parseLong(id)));
      	    	    	 msg.setCtrDate(new Date());
      	    	    	 msg.setSendDate(new Date());
      	    	    	 msg.setMessageRemark(emailRemark);
      	    	    	 msg.setMailAddr(email);
      	    	    	 msg.setCustId(custId);
      	    	    	 msg.setCustName(custName);
      	    	    	 msg.setIfSend("2");
      	    	    	 msg.setUserId(auth.getUserId());
      	    	    	 
      	    	    	 ocrmFWpRemindMsgService.save(msg);
      	    	    	 //修改发送状态
      	    	    	 String jql = "update  OcrmFWpRemindLast p set p.ifMail=:ifMail where p.infoId ='"+id+"'";
      	    	    	 Map<String,Object> values = new HashMap<String,Object>();
      	    	    	 values.put("ifMail", "1");
      	    	    	 ocrmFWpRemindReadService.batchUpdateByName(jql, values);
      	    	    	 
      	    	     }else{
 	    				throw new BizException(1, 0, "10001", "客户邮件地址为空,邮件发送失败!");
 	    			}
     	    		}
   	    		//if(s[i].equals("3")){//发送微信
//   	    		//修改发送状态
//      	    	   	 String jql = "update  OcrmFWpRemindLast p set p.ifMicro=:ifMicro where p.infoId ='"+id+"'";
//      	    	     Map<String,Object> values = new HashMap<String,Object>();
//      	    	     values.put("ifMicro", "1");
//      	    	     ocrmFWpRemindReadService.batchUpdateByName(jql, values);
//      	    	     
//      	       	 	//添加到发送记录
//      	    	     OcrmFWpRemindMsg1 msg = new OcrmFWpRemindMsg1();
//      	    	     
//      	    	     msg.setRemindId(BigDecimal.valueOf(Long.parseLong(id)));
//      	    	     msg.setCtrDate(new Date());
//      	    	     msg.setMessageRemark(weixinRemark);
//      	    	     msg.setMicroNumber(weixin);
//      	    	     msg.setCustId(custId);
//      	    	     msg.setCustName(custName);
//      	    	     msg.setIfSend("0");
//      	    	     msg.setUserId(auth.getUserId());
//      	    	     
//      	    	     ocrmFWpRemindMsgService.save(msg);
//   	    		}
   	    	}
   	    }
		}catch(Exception e){
			e.printStackTrace();
			log.error("信息提醒发送失败："+e.getMessage());
			throw new BizException(1, 0, "10000",e.getMessage());
		}
	}
	/**
	 * 点击致电处理
	 */
	public void call(){
		ActionContext ctx = ActionContext.getContext();
   	 	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	 	String id = request.getParameter("id");
	   	 String jql = "update  OcrmFWpRemindLast p set p.ifCall=:ifCall where p.infoId ='"+id+"'";
	     Map<String,Object> values = new HashMap<String,Object>();
	     values.put("ifCall", "1");
	     ocrmFWpRemindReadService.batchUpdateByName(jql, values);
	     
	}
	/**
	 * 首页提醒磁铁
	 */
	public void remindList(){
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 StringBuilder sb = new StringBuilder("select it.f_value as rule_code,count(1) as totalNo from OCRM_F_WP_REMIND tt" +
    	 		" left join ocrm_sys_lookup_item it on tt.rule_code = it.f_code" +
    	 		" and F_LOOKUP_ID like '%REMIND_TYPE%'  where tt.user_id = '"+auth.getUserId()+"'" +
    	 		" and not exists (select *from OCRM_F_WP_REMIND_READ rr  where rr.remind_id = tt.info_id)");
    	 sb.append("group by it.f_value");
    	 SQL=sb.toString();
 		 datasource = ds;
	}
}


