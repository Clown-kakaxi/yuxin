package com.yuchengtech.emp.biappframe.message.sender.push.pushlet;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServlet;

import nl.justobjects.pushlet.core.Dispatcher;
import nl.justobjects.pushlet.core.Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * Title: 消息组播
 * Description: 消息组播
 * </pre>
 * 
 * @author liucheng2@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Repository
public class PushMulticast extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7027442980371632485L;


	private static Logger logger = LoggerFactory.getLogger(PushUnicast.class);
	
	
	/**
	 * 消息推送
	 * @param msglist 消息列表
	 * @param clientId 客户端ID
	 */
	public void pushMulti(String message, String joinListenId, String messageId) {
		Event event = Event.createDataEvent(joinListenId);
		event.setField(messageId, this.convertCharSet(message));
		Dispatcher.getInstance().multicast(event); // 向所有和joinListenId名称匹配的事件推送
		
		logger.info("[PushMulticast#pushMulti] 消息组播成功！");
	}
	
	
	/**
	 * 多人批量推送消息列表
	 * @param msglist  消息列表
	 * @param clientId 客户端列表
	 */
	public void pushMulti(List<String> msglist, String joinListenId, String messageId) {
		// log multi start
		if (msglist != null && !msglist.isEmpty()) {
			for (String message : msglist) {
				this.pushMulti(this.convertCharSet(message), joinListenId, messageId);
				logger.info("[PushMulticast#pushMulti(List...)] 消息组播成功！");
			}
		}
		// log multi end
	}


	private String convertCharSet(String msg) {
		try {
			return URLEncoder.encode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("消息编码转换时发生异常！", e);
			return msg;
		}
	}
}
