package com.yuchengtech.emp.biappframe.message.sender.email;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.yuchengtech.emp.biappframe.message.entity.BioneMsgAttachInfo;
import com.yuchengtech.emp.biappframe.message.entity.BioneMsgEmailInfo;
import com.yuchengtech.emp.bione.message.send.IBioneMessageSender;
import com.yuchengtech.emp.bione.util.MailUtils;

/**
 * <pre>
 * Title: 邮件发送服务的默认实现
 * Description: 邮件发送服务的默认实现
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
@Component
public class BioneEmailSenderImpl implements IBioneMessageSender<BioneMsgEmailInfo> {

	@SuppressWarnings("rawtypes")
	public void send(BioneMsgEmailInfo email, List addresses, List attachs)
			throws Exception {
		if (email == null) {
			throw new IllegalArgumentException("邮件实体不允许为空！");
		}
		// 发送格式
		String miniType = email.getMimeType();
		if (miniType.equals(BioneMsgEmailInfo.HTML_MIME)) {
			Map<String, String> attacheMap = new HashMap<String, String>();
			List<BioneMsgAttachInfo> attachArray = (List<BioneMsgAttachInfo>) attachs;
			if (attachs != null && !attachs.isEmpty()) {
				for (BioneMsgAttachInfo attach : attachArray) {
					attacheMap.put(attach.getAttachName(),
							attach.getAttachSrc());
				}
			}
			MailUtils.sendHtmlEmail(email.getSendTo(), email.getSendFrom(), email.getSubject(), email.getMailBody(), attacheMap);
		} else {
			MailUtils.sendTextEmail(email.getSendTo(), email.getSendFrom(), email.getSubject(), email.getMailBody());
		}
	}

}
