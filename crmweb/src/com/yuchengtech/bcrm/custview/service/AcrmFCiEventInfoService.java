package com.yuchengtech.bcrm.custview.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiEvent;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bcrm.system.model.AdminAuthAccount;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;
/**
 * 客户视图大事件信息
 * @author agile
 *
 */
@Service
public class AcrmFCiEventInfoService extends CommonService {
	
	public AcrmFCiEventInfoService(){
		JPABaseDAO<AcrmFCiEvent,Long> baseDao = new JPABaseDAO<AcrmFCiEvent,Long>(AcrmFCiEvent.class);
		super.setBaseDAO(baseDao);
	}
	public Object save(Object obj){
		AcrmFCiEvent event = (AcrmFCiEvent)obj;
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String customerId = request.getParameter("custId");
		String flag = request.getParameter("flag");//flag = 0 对私 flag = 1 对公
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//根据Id是否为空判断是新增还是 更改
		if(event.getEventId()==null){
			event.setWhdt(new Date());
			event.setWhry(auth.getUsername());
			
			if("1,2".equals(event.getRemindPpl()) || "1".equals(event.getRemindPpl())){
				event.setRemindPplCm(auth.getUserId());
			}
			super.save(event);
			if("1".equals(event.getWarnFlg())){//1是0否===是否提醒
				saveRemind(event,true,flag);
			}
		}else{
			event.setWhdt(new Date());
			event.setWhry(auth.getUsername());
			
			if("1,2".equals(event.getRemindPpl()) || "1".equals(event.getRemindPpl())){
				event.setRemindPplCm(auth.getUserId());
			}
			super.save(event);
			if("1".equals(event.getWarnFlg())){//1是0否===是否提醒
				saveRemind(event,false,flag);
			}
		}
		return event;
	}
	/***
	 * 信息提醒
	 */
	public void saveRemind(AcrmFCiEvent event,boolean isNew,String flag){//isNew 新增 flag 对公 对私
        //isNew 是否新增
    	Connection conn = null ;
    	Statement stmt = null;
    	ResultSet rs = null;
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	String date = format.format(new Date());
		try {
			conn=JdbcUtil.getConnection();
			stmt = conn.createStatement();
			//托管
			List<String> ship = super.findByJql("select p.deadLine from OcrmFCiBelongTrusteeship p " +
					" where p.custId = '"+event.getCustId()+"'", null);
			StringBuffer sb = new StringBuffer();
			//查询事件提醒的客户名称
			List<String> customer = this.findByJql("select a.custName from AcrmFCiCustomer a " +
					" where a.custId = '"+event.getCustId()+"'", null);
		    String custName = "";
			if(!customer.isEmpty()){
				custName = customer.get(0);
			}
			if(!ship.isEmpty()){//是托管客户
				String deadLine = ship.get(0).toString();//托管有效期
				java.text.DateFormat df=new java.text.SimpleDateFormat("yyyy-MM-dd"); 
				java.util.Calendar c1=java.util.Calendar.getInstance(); 
				java.util.Calendar c2=java.util.Calendar.getInstance(); 
				c1.setTime(df.parse(deadLine)); 
				c2.setTime(df.parse(date));
				int result=c1.compareTo(c2); 
				if(result>=0){//托管在有效期内
					if("1".equals(flag)){//对公
						//获取对公客户发送提醒人信息
						String publicPer = getPublicPer(event.getCustId());
						if(publicPer == null){
							return;
						}
						String[] strs = publicPer.split(",");
						for(String s : strs){
							sb.setLength(0);
							sb.append("insert into OCRM_F_WP_REMIND(INFO_ID,RULE_CODE,CUST_ID,CUST_NAME,USER_ID,MSG_END_DATE,MSG_CRT_DATE,REMIND_REMARK)")
							.append(" values ((SELECT max(INFO_ID)+1 FROM OCRM_F_WP_REMIND ),")
							.append(" '1001',")
							.append(" '"+event.getCustId()+"',");
							if(!customer.isEmpty()){
								sb.append(" '"+customer.get(0)+"',");
							}else{
								sb.append("'',");
							}
							sb.append(" "+s+",");
							if(event.getRemindTime()!=null){
								sb.append(" to_date('"+format.format(event.getRemindTime())+"','yyyy-mm-dd'),");
							}else{
								sb.append(" to_date('"+date+"','yyyy-mm-dd'),");
							}
							sb.append(" to_date('"+date+"','yyyy-MM-dd'),");
							if(isNew){
								sb.append("'对公客户新增事件："+date+"，客户"+custName+" 新增大事件，事件名称 ："+event.getEventName()+"，事件内容："+event.getEventDesc()+"')");
							}else{
								sb.append("'对公客户修改事件："+date+"，客户"+custName+" 修改大事件，事件名称 ："+event.getEventName()+"，事件内容："+event.getEventDesc()+"')");
							}
							stmt.addBatch(sb.toString());
						}
						stmt.executeBatch();
					}else if("0".equals(flag)){//对私
						//获取对私客户发送提醒人信息
						String privatePer = getPrivatePer(event);
						if(privatePer ==null){
							return;
						}
						String[] strs = privatePer.split(",");
						for(String s : strs){
							sb.setLength(0);
							sb.append("insert into OCRM_F_WP_REMIND(INFO_ID,RULE_CODE,CUST_ID,CUST_NAME,USER_ID,MSG_END_DATE,MSG_CRT_DATE,REMIND_REMARK)")
							.append(" values ((SELECT max(INFO_ID)+1 FROM OCRM_F_WP_REMIND ),")
							.append(" '1001',")
							.append(" '"+event.getCustId()+"',");
							if(!customer.isEmpty()){
								sb.append(" '"+customer.get(0)+"',");
							}else{
								sb.append("'',");
							}
							sb.append(" "+s+",");
							if(event.getRemindTime()!=null){
								sb.append(" to_date('"+format.format(event.getRemindTime())+"','yyyy-mm-dd'),");
							}else{
								sb.append(" to_date('"+date+"','yyyy-mm-dd'),");
							}
							sb.append(" to_date('"+date+"','yyyy-MM-dd'),");
							if(isNew){
								sb.append("'零售客户新增事件："+date+"，客户"+custName+" 新增大事件，事件名称 ："+event.getEventName()+"，事件内容："+event.getEventDesc()+"')");
							}else{
								sb.append("'零售客户修改事件："+date+"，客户"+custName+" 修改大事件，事件名称 ："+event.getEventName()+"，事件内容："+event.getEventDesc()+"')");
							}
							stmt.addBatch(sb.toString());
						}
						stmt.executeBatch();
					}
				}
			}else{//不是托管客户
				if("1".equals(flag)){//对公
					//获取对公客户发送提醒人信息
					String publicPer = getPublicPer(event.getCustId());
					if(publicPer == null){
						return;
					}
					String[] strs = publicPer.split(",");
					for(String s : strs){
						sb.setLength(0);
						sb.append("insert into OCRM_F_WP_REMIND(INFO_ID,RULE_CODE,CUST_ID,CUST_NAME,USER_ID,MSG_END_DATE,MSG_CRT_DATE,REMIND_REMARK)")
						.append(" values ((SELECT max(INFO_ID)+1 FROM OCRM_F_WP_REMIND ),")
						.append(" '1001',")
						.append(" '"+event.getCustId()+"',");
						if(!customer.isEmpty()){
							sb.append(" '"+customer.get(0)+"',");
						}else{
							sb.append("'',");
						}
						sb.append(" "+s+",");
						if(event.getRemindTime()!=null){
							sb.append(" to_date('"+format.format(event.getRemindTime())+"','yyyy-mm-dd'),");
						}else{
							sb.append(" to_date('"+date+"','yyyy-mm-dd'),");
						}
						sb.append(" to_date('"+date+"','yyyy-MM-dd'),");
						if(isNew){
							sb.append("'对公客户新增事件："+date+"，客户"+custName+" 新增大事件，事件名称 ："+event.getEventName()+"，事件内容："+event.getEventDesc()+"')");
						}else{
							sb.append("'对公客户修改事件："+date+"，客户"+custName+" 修改大事件，事件名称 ："+event.getEventName()+"，事件内容："+event.getEventDesc()+"')");
						}
						stmt.addBatch(sb.toString());
					}
					stmt.executeBatch();
				}else if("0".equals(flag)){
					//获取对私客户发送提醒人信息
					String privatePer = getPrivatePer(event);
					if(privatePer ==null){
						return;
					}
					String[] strs = privatePer.split(",");
					for(String s : strs){
						sb.setLength(0);
						sb.append("insert into OCRM_F_WP_REMIND(INFO_ID,RULE_CODE,CUST_ID,CUST_NAME,USER_ID,MSG_END_DATE,MSG_CRT_DATE,REMIND_REMARK)")
						.append(" values ((SELECT max(INFO_ID)+1 FROM OCRM_F_WP_REMIND ),")
						.append(" '1001',")
						.append(" '"+event.getCustId()+"',");
						if(!customer.isEmpty()){
							sb.append(" '"+customer.get(0)+"',");
						}else{
							sb.append("'',");
						}
						sb.append(" "+s+",");
						if(event.getRemindTime()!=null){
							sb.append(" to_date('"+format.format(event.getRemindTime())+"','yyyy-mm-dd'),");
						}else{
							sb.append(" to_date('"+date+"','yyyy-mm-dd'),");
						}
						sb.append(" to_date('"+date+"','yyyy-MM-dd'),");
						if(isNew){
							sb.append("'零售客户新增事件："+date+"，客户"+custName+" 新增大事件，事件名称 ："+event.getEventName()+"，事件内容："+event.getEventDesc()+"')");
						}else{
							sb.append("'零售客户修改事件："+date+"，客户"+custName+" 修改大事件，事件名称："+event.getEventName()+"，事件内容："+event.getEventDesc()+"')");
						}
						stmt.addBatch(sb.toString());
					}
					stmt.executeBatch();
				}
			}
		}catch (Exception e) {
			throw new BizException(1,2,"1002",e.getMessage());
        }finally{
        	JdbcUtil.close(rs, stmt, conn);
        }
	}
	
	/**
	 * 查询事件提醒人员（对私）
	 * @param customerId
	 * @return
	 */
	public String getPrivatePer(AcrmFCiEvent event){
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(event == null){
			return null;
		}
		//人员在event对象中有保存 不需要查询
		StringBuffer bf = new StringBuffer();
		if(event.getRemindPplCm()!=null && event.getRemindPplCm()!=""){//客户经理本人
			bf.append(",'").append(event.getRemindPplCm()).append("'");
		}
		if(event.getRemindPplDrc()!=null && event.getRemindPplDrc()!=""){//机构主管
			String[] sts = event.getRemindPplDrc().split(",");
			for(String s : sts){
				bf.append(",'").append(s).append("'");
			}
		}
		if(bf!=null&& bf.length()>0){
			String jql="select t from AdminAuthAccount t where 1=1 " +
					" and t.accountName in ("+bf.toString().substring(1)+")";
			List<AdminAuthAccount> accounts = super.findByJql(jql, null);
			StringBuffer sb = new StringBuffer();
			for(AdminAuthAccount ac : accounts){
				sb.append(",'");
				sb.append(ac.getAccountName());
				sb.append("'");
			}
			return sb.toString().substring(1);
		}else{
			return null;
		}
	}
	
	/**
	 * 查询事件提醒人员（对公）
	 * @param customerId
	 * @return
	 */
	public String getPublicPer(String customerId){
		if(customerId == null || customerId == "" ){
			return null;
		}
		String jql="select C FROM OcrmFCiBelongCustmgr C WHERE C.custId = '"+customerId+"'";
		List<OcrmFCiBelongCustmgr> ocrmFCiBelongCustmgrs = super.findByJql(jql, null);
		StringBuffer sb = new StringBuffer();
		for(OcrmFCiBelongCustmgr mg : ocrmFCiBelongCustmgrs){
			sb.append(",'");
			sb.append(mg.getMgrId());
			sb.append("'");
		}
		if(sb.length()>0 && sb!=null){
			return sb.toString().substring(1);
		}
		else{
			return null;
		}
	}
	
}
