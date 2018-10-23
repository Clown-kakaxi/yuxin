package com.ecc.echain.wf;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
import com.yuchengtech.bcrm.sales.message.action.MailClient;
import com.yuchengtech.bcrm.sales.message.action.MsgClient;


/**
 * 渠道自动营销复核
 * @author geyu
 * 2014-8-1
 */

public class MktMessage extends EChainCallbackCommon{
	
	private static Logger log = LoggerFactory.getLogger(MktMessage.class);
	
	//通过处理
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void endY(EVO vo){
		List custIdList = new ArrayList();
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL = "select * from OCRM_F_MM_SYS_MSG where msgsendtime='"+instanceids[1]+"'";
			Result result=querySQL(vo);
			for (SortedMap item : result.getRows()){
				custIdList.add(item);     
			}
			boolean flag = sendMessage(custIdList,vo);
			if(flag){
				SQL = "update OCRM_F_MM_SYS_MSG t set t.approve_state='1' where t.msgsendtime ='"+instanceids[1]+"'";
				execteSQL(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("执行SQL出错"+e.getMessage());
		}
		

	}
	/**
	 * 向客户推送短信或邮件
	 * @param custlist
	 */
	@SuppressWarnings("unchecked")
	public boolean sendMessage(List<Map<String,String>> custlist,EVO vo){
		 try{
		   	 if(custlist!=null && custlist.size()>0){
               for(int i=0;i<custlist.size();i++){   		 
	   				 Map<String,String> map = custlist.get(i);
	   				 String channel = map.get("model_type");
	   				 if(channel!=null && !channel.trim().equals("")){
	   					 if(channel.contains(",")){
	   						 String[] channels = channel.split(",");
	   						 for(int j=0;j<channels.length;j++){
	   							 String msgChannel =channels[j];
	   							 sendMsgToPerson(msgChannel,custlist,vo);
	   						 } 
	   					 }else{
	   						 sendMsgToPerson(channel,custlist,vo); 
	   					 }
	   					 return true;
	   				 }
		   		 }
		   	 }
	   	 }catch(Exception e){
	   		e.printStackTrace();
	   		log.error("推送营销信息失败："+e.getMessage());
	   	 }
		 return false;
	}
	/**
	 * 实时推送短信或邮件
	 * @param msgChannel
	 * @param list
	 * @throws Exception
	 */
	public void sendMsgToPerson(String msgChannel,List<Map<String,String>> list,EVO vo) throws Exception{
		 if(msgChannel.trim().equals("2")){
			 if(list!=null){
				for(int j=0;j<list.size();j++){
					Map<String,String> map = list.get(j);
					String revicer = map.get("mail_address");
					if(revicer!=null && !revicer.trim().equals("")){
						String sendTitle = "富邦华一银行"+map.get("prod_name")+"推荐";
						String sendContent = map.get("message_remark");
						MailClient.getInstance().sendMsg(revicer, sendTitle, sendContent);//收件人，标题，内容
						//保存邮件模板
						 String mail_addr=revicer;
						 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");  
						 String crtDate=sdf.format(map.get("CRT_DATE"));
			        	 String SendDate = sdf.format(new Date());
			        	 String ifSend="2";
			        	 String prod_id=map.get("PROD_ID")==null?"":map.get("PROD_ID");
			        	 String ProdName = map.get("PROD_NAME")==null?"":map.get("PROD_NAME");
			        	 String modelId = map.get("MODEL_ID")==null?"":map.get("MODEL_ID");
			        	 String messageRemark = map.get("MESSAGE_REMARK")==null?"":map.get("MESSAGE_REMARK");
			        	 String custId=map.get("CUST_ID")==null?"":map.get("CUST_ID");
			        	 String custName=map.get("CUST_NAME")==null?"":map.get("CUST_NAME");
			        	 String userId=map.get("USER_ID")==null?"":map.get("USER_ID");
			        	 SQL = "  insert into " +
				        	 		"OCRM_F_WP_REMIND_MSG" +
				        	 		"(id,Message_Remark,cust_id,cust_name,if_send,ctr_date,send_date,cell_number,prod_id,prod_name,model_id,user_id,org_id,micro_number,mail_addr) " +
				        	 		"values(ID_SEQUENCE.NEXTVAL,'"+messageRemark+"','"+custId+"','"+custName+"','"+ifSend+"',to_date('"+crtDate+"','yyyy/MM/dd'),to_date('"+SendDate+"','yyyy/MM/dd'),'','"+prod_id+"','"+ProdName+"','"+modelId+"','"+userId+"','','','"+mail_addr+"')";
				        	 execteSQL(vo);
					}
				}  
			 }
		 }else if(msgChannel.trim().equals("1")){
			 if(list!=null){
				for(int j=0;j<list.size();j++){
					Map<String,String> map = list.get(j);
					String telephone = map.get("cell_number");
					if(telephone!=null && !telephone.trim().equals("")){
						String phoneContent = map.get("message_remark");
						String phoneSendTime ="";//map.get("");
						MsgClient.getInstance().process(telephone, phoneContent, phoneSendTime);//电话号码，内容，推动时间	
						//保存短信模板
						 String cellNumber=telephone;
				         SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");  
						 String crtDate=sdf.format(map.get("CRT_DATE"));
			        	 String SendDate = sdf.format(new Date());
			        	 String ifSend="1";
			        	 String prod_id=map.get("PROD_ID")==null?"":map.get("PROD_ID");
			        	 String ProdName = map.get("PROD_NAME")==null?"":map.get("PROD_NAME");
			        	 String modelId = map.get("MODEL_ID")==null?"":map.get("MODEL_ID");
			        	 String messageRemark = map.get("MESSAGE_REMARK")==null?"":map.get("MESSAGE_REMARK");
			        	 String custId=map.get("CUST_ID")==null?"":map.get("CUST_ID");
			        	 String custName=map.get("CUST_NAME")==null?"":map.get("CUST_NAME");
			        	 String userId=map.get("USER_ID")==null?"":map.get("USER_ID");
			        	 SQL = "  insert into " +
				        	 		"OCRM_F_WP_REMIND_MSG" +
				        	 		"(id,Message_Remark,cust_id,cust_name,if_send,ctr_date,send_date,cell_number,prod_id,prod_name,model_id,user_id,org_id,micro_number,mail_addr) " +
				        	 		"values(ID_SEQUENCE.NEXTVAL,'"+messageRemark+"','"+custId+"','"+custName+"','"+ifSend+"',to_date('"+crtDate+"','yyyy/MM/dd'),to_date('"+SendDate+"','yyyy/MM/dd'),'"+cellNumber+"','"+prod_id+"','"+ProdName+"','"+modelId+"','"+userId+"','','','')";
				        	 execteSQL(vo);
					}
				} 
			 }
		}
	}
	
//     /**
//      * 获取客户推送的信息
//      * @param custId
//      * @param vo
//      * @return
//      * @throws Exception
//      */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public List getMessage(String custId,EVO vo) throws Exception{
//		 List<Map<String,String>> list =new ArrayList<Map<String,String>>();
//		 try{
//			 SQL="select * from OCRM_F_MM_SYS_MSG where cust_id = '"+custId+"' and  if_approve = 'Y' and approve_state='0' ";
//			 Result result=querySQL(vo);
//			 for (SortedMap item : result.getRows()){
//					list.add(item);
//			 }
//		 }catch(Exception e){
//			 e.printStackTrace();
//			 log.error("查询推送信息失败："+e.getMessage());
//		 }
//		return list;
//	}
	
	//拒绝处理
	public void endN(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL = "update OCRM_F_MM_SYS_MSG t set t.approve_state='1' where t.msgsendtime ='"+instanceids[1]+"'";
			execteSQL(vo);
//			if("MK".equals(instanceids[0])){
//				SQL = "update OCRM_F_MM_SYS_MSG t set t.approve_state='2' where t.id='"+instanceids[1]+"' ";
//			}else{
//				SQL=" SELECT CUST_IDS FROM OCRM_GROUPMSG_REVIEW WHERE ID='"+instanceid+"'";
//				Result result=querySQL(vo);
//				 String ids_q="";
//					for (SortedMap item : result.getRows()){
//						ids_q = item.get("CUST_IDS").toString();     
//					}
//				  StringBuffer buffer=new StringBuffer();
//					String[] ids=ids_q.split("_");
//					for(int i=0;i<ids.length;i++){
//						buffer.append("'"+ids[i]+"',");
//					}
//					String ids_r=buffer.substring(0,buffer.length()-1);
//					SQL = "update OCRM_F_MM_SYS_MSG t set t.approve_state='2' where t.id in ("+ids_r+")";
//			}
//			
//			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("执行SQL出错"+e.getMessage());
		}

	}
	

}
