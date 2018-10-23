package com.yuchengtech.bcrm.sales.message.action;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mail {


	 private static Logger log = LoggerFactory.getLogger((Mail.class));
	
	 //邮件内容
	 private MimeMessage mimeMsg;

	 private Session session;

	 private Properties props;
    //用户名
	 private String username;
    //密码
	 private String password;

	 private Multipart mp;

	 public Mail(String smtp) {
	     setSmtpHost(smtp);
	     createMimeMessage();
	 }

	 public void setSmtpHost(String hostName){
	     log.info("设置系统属性：mail.smtp.host=" + hostName);
	     if (props == null) {
	         props = System.getProperties();
	     }
	     props.put("mail.smtp.host", hostName);
	     props.put("mail.smtp.auth", "true");
	 }

	 public boolean createMimeMessage() {
	     try {
	         log.info("准备获取邮件会话对象！");
	         session = Session.getDefaultInstance(props, null);
	     } catch (Exception e) {
	    	 e.printStackTrace();
	    	 log.error("获取邮件会话错误！" + e.getMessage());
	         return false;
	     }
	     log.info("准备创建MIME邮件对象！");
	     try {
	         mimeMsg = new MimeMessage(session);
	         mp = new MimeMultipart();
	         return true;
	     } catch (Exception e) {
	    	 e.printStackTrace();
	         log.error("创建MIME邮件对象失败！" + e.getMessage());
	         return false;
	     }
	 }
	 
	 /**
	  * 定义SMTP是否需要验证
	  */
	 public void setNeedAuth(boolean need) throws Exception{
	     log.info("设置smtp身份认证：mail.smtp.auth = " + need);
	     if (props == null)
	         props = System.getProperties();
	     if (need) {
	         props.put("mail.smtp.auth", "true");
	     } else {
	         props.put("mail.smtp.auth", "false");
	     }

	 }

	 public void setNamePass(String name, String pass) {
	     username = name;
	     password = pass;
	 }



	 /**
	  * 定义邮件主题
	  */
	 public boolean setSubject(String mailSubject)throws Exception {
	     log.info("定义邮件主题！");
	     try {
	         mimeMsg.setSubject(mailSubject);
	         return true;
	     } catch (Exception e) {
            e.printStackTrace();
	         log.error("定义邮件主题发生错误！"+e.getMessage());
	         return false;
	     }
	 }

	 /**
	  * 定义邮件正文
	  */
	 public boolean setBody(String mailBody) throws Exception {
	     try {
	         BodyPart bp = new MimeBodyPart();
	         bp.setContent("" + mailBody, "text/html;charset=GBK");
	         mp.addBodyPart(bp);
	         return true;
	     } catch (Exception e) {
	    	 e.printStackTrace();
	         log.error("定义邮件正文时发生错误！" + e.getMessage());
	         return false;
	     }
	 }
	 
	 /**
	  * 设置发信人
	  */
	 public boolean setFrom(String from) throws Exception {
	     log.info("设置发信人！");
	     try {
	         mimeMsg.setFrom(new InternetAddress(from)); //发信人
	         return true;
	     } catch (Exception e) {
	    	 e.printStackTrace();
	    	 log.error("获取发信人失败："+e.getMessage());
	         return false;
	     }
	 }

	 /**
	  * 定义收信人
	  */
	 public boolean setTo(String to) throws Exception{
	     if (to == null)
	         return false;
	        log.equals("定义收信人！");
	     try {
	         mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	         return true;
	     } catch (Exception e) {
	    	 e.printStackTrace();
	    	 log.error("获取收信人失败："+e.getMessage());
	         return false;
	     }
	 }

	 /**
	  * 定义抄送人
	  */
	 public boolean setCopyTo(String copyto) throws Exception {
	     if (copyto == null)
	         return false;
	     try {
	         mimeMsg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(copyto));
	         return true;
	     } catch (Exception e) {
	    	 log.error("获取抄送人失败："+e.getMessage());
	         return false;
	     }

	 }

	 /**
	  * 发送邮件模块
	  */
	 public boolean sendOut() {
	     try {
	         mimeMsg.setContent(mp);
	         mimeMsg.saveChanges();
	         log.info("邮件发送中....");
	         Session mailSession = Session.getInstance(props, null);
	         Transport transport = mailSession.getTransport("smtp");
	         transport.connect((String) props.get("mail.smtp.host"), "fuboncrm", "ABab12");
	         transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
	         log.info("发送成功！");
	         transport.close();
	         return true;
	     } catch (Exception e) {
	    	 e.printStackTrace();
	         log.error("邮件失败！" + e.getMessage());
	         return false;
	     }

	 }

	 /**
	  * 调用sendOut方法完成发送
	  */
	 public static boolean sendAndCc(String smtp, String from, String to, String copyto,
	     String subject, String content, String username, String password) throws Exception {
	     Mail theMail = new Mail(smtp);
	     theMail.setNeedAuth(true); // 验证
	     if (!theMail.setSubject(subject))
	         return false;
	     if (!theMail.setBody(content))
	         return false;
	     if (!theMail.setTo(to))
	         return false;
	     if (!theMail.setCopyTo(copyto))
	         return false;
	     if (!theMail.setFrom(from))
	         return false;
	         theMail.setNamePass(username, password);
	     if (!theMail.sendOut())
	         return false;
	     return true;
	    }
   
}
