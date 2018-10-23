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
 * Title: 消息广播
 * Description: 消息广播
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
public class PushBrodcust extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7027442980371632485L;


	private static Logger logger = LoggerFactory.getLogger(PushUnicast.class);
	
	
	/**
	 * 广播
	 * 
	 * @param msglist
	 *            消息列表集合
	 * @param clientId
	 *            客户端列表
	 */
	public void pushBrodcast(String message, String messageId) {
		Event event = Event.createDataEvent(""); // 向所有的事件推送，不要求和这儿的joinListenId名称匹配
		event.setField(messageId, this.convertCharSet(message));
		Dispatcher.getInstance().broadcast(event);
		
		logger.info("[PushBrodcust#pushBrodcast] 消息广播成功！");
	}

	/**
	 * 广播
	 * 
	 * @param msglist
	 *            消息列表集合
	 * @param clientId
	 *            客户端列表
	 */
	public void pushBrodcast(List<String> msglist, String messageId) {
		// log multi start
		if (msglist != null && !msglist.isEmpty()) {
			for (String message : msglist) {
				this.pushBrodcast(this.convertCharSet(message), messageId);
				logger.info("[PushBrodcust#pushBrodcast(List...)] 消息广播成功！");
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
