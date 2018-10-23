package com.yuchengtech.emp.biappframe.message.sender.push;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Title: 消息推送接口
 * Description: 消息推送接口，用于公告信息或站内私信；
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
public interface Pusher {

	
	/**
	 * 消息推送
	 * 
	 * @param message
	 *            消息内容
	 * @param clientId
	 *            客户端ID
	 */
	public void push(Map<String, List<String>> pshlist,
			String joinListenId, String messageId);
	
	/**
	 * 消息推送
	 * 
	 * @param message
	 *            消息内容
	 * @param clientId
	 *            客户端ID
	 */
	public void push(String message, List<String> clientIds,
			String joinListenId, String messageId);
			
	/**
	 * 消息推送
	 * 
	 * @param message
	 *            消息内容
	 * @param clientId
	 *            客户端ID
	 */
	public void pushUnique(String message, String clientId,
			String joinListenId, String messageId);

	/**
	 * 消息推送
	 * 
	 * @param msglist
	 *            消息列表
	 * @param clientId
	 *            客户端ID
	 */
	public void pushUnique(List<String> msglist, String clientId,
			String joinListenId, String messageId);

	/**
	 * 消息推送
	 * 
	 * @param msglist
	 *            消息列表
	 * @param clientId
	 *            客户端ID
	 */
	public void pushMulti(String message, String joinListenId, String messageId);

	/**
	 * 多人批量推送消息列表
	 * 
	 * @param msglist
	 *            消息列表
	 * @param clientId
	 *            客户端列表
	 */
	public void pushMulti(List<String> msglist, String joinListenId,
			String messageId);

	/**
	 * 广播
	 * 
	 * @param msglist
	 *            消息列表集合
	 * @param clientId
	 *            客户端列表
	 */
	public void pushBrodcast(String msglist, String messageId);

	/**
	 * 广播
	 * 
	 * @param msglist
	 *            消息列表集合
	 * @param clientId
	 *            客户端列表
	 */
	public void pushBrodcast(List<String> msglist, String messageId);
}
