package com.yuchengtech.emp.bione.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.yuchengtech.emp.utils.SpringContextHolder;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * <pre>
 * Title: 邮件发送工具类
 * Description: 用于发送email，支持纯文本、HTML和附件的发送
 * </pre>
 * 
 * @author songxf
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 * </pre>
 */
public class MailUtils {

	private static Logger logger = LoggerFactory.getLogger(MailUtils.class);
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static JavaMailSender mailSender;
	private static Configuration freemarkerConfiguration;

	static {

		mailSender = SpringContextHolder.getBean("mailSender");
		freemarkerConfiguration = SpringContextHolder.getBean("freemarkerConfiguration");
	}

	/**
	 * 发送纯文本邮件
	 * @param sendto
	 * 		 		接收者
	 * @param sendfrom
	 * 				发送者  可以任意定义，也可以为空
	 * @param subject
	 * 				邮件主题
	 * @param text
	 * 				邮件内容
	 * @return
	 * @throws MailException 
	 */
	public static void sendTextEmail(String sendto, String sendfrom, String subject, String text) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(sendto);
		if (sendfrom != null && !sendfrom.equals("")) {
			mail.setFrom(sendfrom);
		}
		mail.setSubject(subject);
		mail.setText(text);
		mailSender.send(mail);
	}

	/**
	 * 发送Html邮件
	 * @param sendto
	 * 		 		接收者
	 * @param sendfrom
	 * 				发送者  可以任意定义，也可以为空
	 * @param subject
	 * 				邮件主题
	 * @param htmltext
	 * 				邮件内容
	 * @param attachmentMap
	 * 				附件Map key为文件名称   value为文件全路径   如果没有附件传null
	 * @return
	 * @throws MailException ,MessagingException
	 */
	public static void sendHtmlEmail(String sendto, String sendfrom, String subject, String htmltext,
			Map<String, String> attachmentMap) throws MailException, MessagingException {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		MimeMessage mail = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper;
		messageHelper = new MimeMessageHelper(mail, true, "utf-8");
		messageHelper.setTo(sendto);
		if (sendfrom != null && !sendfrom.equals("")) {
			messageHelper.setFrom(sendfrom);
		}
		if (attachmentMap != null) {
			Set<String> key = attachmentMap.keySet();
			for (Iterator<String> it = key.iterator(); it.hasNext();) {
				String s = (String) it.next();
				String value = attachmentMap.get(s);
				messageHelper.addAttachment(s, new File(value));
			}
		}
		messageHelper.setSubject(subject);
		messageHelper.setText(htmltext);
		mailSender.send(mail);
	}

	/**
	 * 通过Html模板生成邮件内容，并发送Html邮件
	 * @param sendto
	 * 		 		接收者
	 * @param sendfrom
	 * 				发送者  可以任意定义，也可以为空
	 * @param subject
	 * 				邮件主题
	 * @param context
	 * 				内容变量Map  用于替换HTML模板中的变量
	 * @param templateName
	 *              模板名称
	 * @param attachmentMap
	 * 				附件Map key为文件名称   value为文件全路径   如果没有附件传null
	 * @return
	 * @throws MailException ,MessagingException
	 */
	public static void sendHtmlEmail(String sendto, String sendfrom, String subject, Map<String, ?> context,
			String templateName, Map<String, String> attachmentMap) throws MailException, MessagingException {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		MimeMessage mail = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mail, true, "utf-8");
		messageHelper.setTo(sendto);
		if (sendfrom != null && !sendfrom.equals("")) {
			messageHelper.setFrom(sendfrom);
		}
		if (attachmentMap != null) {
			Set<String> key = attachmentMap.keySet();
			for (Iterator<String> it = key.iterator(); it.hasNext();) {
				String s = (String) it.next();
				String value = attachmentMap.get(s);
				messageHelper.addAttachment(s, new File(value));
			}
		}
		String htmltext = generateContent(context, templateName);
		messageHelper.setSubject(subject);
		messageHelper.setText(htmltext, true);
		mailSender.send(mail);
	}

	/**
	 * 使用Freemarker生成html格式内容.
	 * @param context
	 * 				内容变量Map  用于替换HTML模板中的变量
	 * @param templateName
	 *              模板名称
	 * @return String
	 *              解析后的htmlText
	 */
	private static String generateContent(Map<String, ?> context, String templateName) throws MessagingException {
		Template template = getTemplate(templateName);
		try {
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
		} catch (IOException e) {
			logger.error("生成邮件内容失败, FreeMarker模板不存在", e);
			throw new MessagingException("FreeMarker模板不存在", e);
		} catch (TemplateException e) {
			logger.error("生成邮件内容失败, FreeMarker处理失败", e);
			throw new MessagingException("FreeMarker处理失败", e);
		}
	}

	/**
	 * 获取Html模板
	 * @param templateName
	 * 				模板名称
	 * @return  
	 */
	private static Template getTemplate(String templateName) {

		Template template = null;
		try {
			template = freemarkerConfiguration.getTemplate(templateName, DEFAULT_ENCODING);
		} catch (IOException e) {
			logger.error("获得模板失败,模板[" + templateName + "]不存在", e);
		}
		return template;

	}

}
