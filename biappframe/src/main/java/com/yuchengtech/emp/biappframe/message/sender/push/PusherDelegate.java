package com.yuchengtech.emp.biappframe.message.sender.push;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuchengtech.emp.biappframe.message.sender.push.pushlet.PushBrodcust;
import com.yuchengtech.emp.biappframe.message.sender.push.pushlet.PushMulticast;
import com.yuchengtech.emp.biappframe.message.sender.push.pushlet.PushUnicast;

/**
 * <pre>
 * Title: 消息推送默认实现
 * Description: 消息推送默认实现，用于公告信息或站内私信；
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
public class PusherDelegate implements Pusher {

	
	/** 单播 */
	@Autowired
	private PushUnicast uniCast ;

	/** 组播 */
	@Autowired
	private PushMulticast multiCast ;

	/** 广播 */
	@Autowired
	private PushBrodcust brodCast ;

	public void push(Map<String, List<String>> pushlist,
			String joinListenId, String messageId) {
		if (pushlist==null || pushlist.isEmpty()) {
			return ;
		}
		for (Map.Entry<String, List<String>> entry : pushlist.entrySet()) {
			String message = entry.getKey();
			List<String> clientIds = entry.getValue();
			this.push(message, clientIds, joinListenId, messageId);
		}
	}
	
	public void push(String message, List<String> clientIds,
			String joinListenId, String messageId) {
		if (clientIds == null || clientIds.isEmpty()) {
			return;
		}
		for (String clientId : clientIds) {
			this.pushUnique(message, clientId, joinListenId, messageId);
		}
	}
	
	public void pushUnique(String message, String clientId,
			String joinListenId, String messageId) {
		this.uniCast.pushUnique(message, clientId, joinListenId, messageId);
	}

	public void pushUnique(List<String> msglist, String clientId,
			String joinListenId, String messageId) {
		this.uniCast.pushUnique(msglist, clientId, joinListenId, messageId);
	}

	public void pushMulti(String message, String joinListenId, String messageId) {
		this.multiCast.pushMulti(message, joinListenId, messageId);
	}

	public void pushMulti(List<String> msglist, String joinListenId,
			String messageId) {
		this.multiCast.pushMulti(msglist, joinListenId, messageId);
	}

	public void pushBrodcast(String message, String messageId) {
		this.brodCast.pushBrodcast(message, messageId);
	}

	public void pushBrodcast(List<String> msglist, String messageId) {
		this.brodCast.pushBrodcast(msglist, messageId);
	}


}
