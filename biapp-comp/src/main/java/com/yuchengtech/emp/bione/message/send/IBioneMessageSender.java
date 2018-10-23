package com.yuchengtech.emp.bione.message.send;

import java.util.List;

/**
 * <pre>
 * Title: 通用的消息发送接口
 * Description: 通用的消息发送接口
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
public interface IBioneMessageSender<T> {

	/**
	 * Description: 消息发送 
	 * @param entity 要发送的消息实体
	 * @param dest   发送目标集合
	 * @param attachments 附件集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void send(T entity, List dest, List attachments) throws Exception;

	
}
