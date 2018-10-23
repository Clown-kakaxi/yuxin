package com.yuchengtech.emp.biappframe.message.sender.push.pushlet;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServlet;

import nl.justobjects.pushlet.core.Dispatcher;
import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.SessionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * Title: 消息单播
 * Description: 消息单播
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
public class PushUnicast extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7027442980371632485L;

	
	private static Logger logger = LoggerFactory.getLogger(PushUnicast.class);
	
	
	public void pushUnique(String message, String clientId,
			String joinListenId, String messageId) {
		if (SessionManager.getInstance().hasSession(clientId)) {
			Event event = Event.createDataEvent(joinListenId);
			event.setField(messageId, this.convertCharSet(message));
			//
			Dispatcher.getInstance().unicast(event, clientId);
			//
			logger.info("[PushUnicast#pushUnique] 消息单播成功！");
		} else {
			logger.error("[PushUnicast#pushUnique] 消息单播失败！客户端未注册！");
		}
	}

	public void pushUnique(List<String> msglist, String clientId,
			String joinListenId, String messageId) {
		if (SessionManager.getInstance().hasSession(clientId)) {
			if (msglist != null && !msglist.isEmpty()) {
				for (String message : msglist) {
					this.pushUnique(this.convertCharSet(message), clientId, joinListenId, messageId);
					logger.info("[PushUnicast#pushUnique] 消息单播成功！");
				}
			}
		} else {
			logger.error("[PushUnicast#pushUnique(List...)] 消息单播失败！客户端未注册！");
		}
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
