package com.yuchengtech.bcrm.sales.message.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailClient{
	
	private static Logger log = LoggerFactory.getLogger(MailClient.class);
	
	private static String port = "192.168.2.97";
	
	public static MailClient getInstance() {
		return new MailClient();
	}

	public static void main(String[] args) throws Exception {
		MailClient.getInstance().sendMsg("xuhf@yuchengtech.com", "测试邮件", "后飞你好");
	}
	
	public String sendMsg(String to ,String subject,String body) throws Exception{
		try{	
			Mail mail = new Mail(port);
			mail.setFrom("fubon.crm@fubonchina.com");
			mail.setTo(to);
			StringBuffer sb = new StringBuffer();
			sb.append(body+"\r\n<br>");
//			sb.append("-------------------------\r\n<br>");
//			sb.append("富邦华一银行\r\n<br>"); 
//			sb.append("上海市世纪大道1168号东方金融广场A座18F \r\n<br>");
//	        sb.append("\r\n\n<br>");
//			sb.append("电话：(+86) 21 2061 9888*9733\r\n<br>");
//			sb.append("传真：(+86) 21 5888 2966 \r\n<br>");
//			sb.append("邮箱：willson.chen@fsbankonline.com<mailto:willson.chen@fsbankonline.com> \r\n<br>");
			mail.setSubject(subject);
			mail.setBody(sb.toString());
			mail.sendOut();
		}catch(Exception e){
			e.printStackTrace();
			log.error("邮件发送失败:"+e.getMessage());
		}
		return null;
	}

}
