package com.yuchengtech.emp.biappframe.message.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.message.entity.BioneMsgAttachInfo;

/**
 * <pre>
 * Title: 消息模块-附件服务
 * Description: 消息模块-附件服务
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
@Service
@Transactional(readOnly = true)
public class BioneMsgAttachmentBS extends BaseBS<BioneMsgAttachInfo> {

	/**
	 * 查询与某一条消息相关的附件
	 * 
	 * @param msgId
	 *            消息ID
	 * @return
	 */
	public List<BioneMsgAttachInfo> getAttachList(String msgId) {
		List<BioneMsgAttachInfo> msgAttachList = this.baseDAO
				.findByProperty(BioneMsgAttachInfo.class, "msgId", msgId);
		return msgAttachList;
	}

	/**
	 * 批量删除附件
	 * 
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void deleteBatch(String[] ids) {
		for (String id : ids) {
			removeEntityById(id);
		}
	}

}
