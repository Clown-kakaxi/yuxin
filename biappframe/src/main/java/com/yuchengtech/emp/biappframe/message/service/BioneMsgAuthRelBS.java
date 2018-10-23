package com.yuchengtech.emp.biappframe.message.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.message.entity.BioneMsgAuthRel;

/**
 * <pre>
 * Title: 消息模块-消息权限服务
 * Description: 消息模块-消息权限服务
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
public class BioneMsgAuthRelBS extends BaseBS<BioneMsgAuthRel> {

	public List<BioneMsgAuthRel> getMsgAuthRelByMsgId(String logicSysNo, String msgId, String msgTypeNo) {
		String jql = "SELECT rel FROM BioneMsgAuthRel rel WHERE rel.id.logicSysNo=?0 AND rel.id.msgId=?1 AND rel.id.msgTypeNo=?2 ";
		return this.baseDAO.findWithIndexParam(jql, logicSysNo, msgId, msgTypeNo);
	}

	/**
	 * 根据消息的ID查询该消息要发送到哪些人；
	 */
	public List<String> getReceiverByMsgId(String logicSysNo, String msgId, String msgTypeNo) {
		if (msgId == null || msgId.trim().length()<=0) {
			return null;
		}
		List<String> allUsers = new ArrayList<String>(); // 所有的相关人 
		List<BioneMsgAuthRel> authRels = this.getMsgAuthRelByMsgId(logicSysNo, msgId, msgTypeNo);
		if (authRels!=null && !authRels.isEmpty()) {
			for (BioneMsgAuthRel authRel : authRels) {
				String objNo = authRel.getId().getObjDefNo();
				String objId = authRel.getId().getObjId();
				// 
				List<String> users = this.getReceiverByAuthObjDefNo(logicSysNo, objNo, objId);
				allUsers.addAll(users);
			}
		}
		return allUsers;
	}
	
	/**
	 * 根据授权对象的定义，查询系统内相关的User；
	 */
	private List<String> getReceiverByAuthObjDefNo(String logicSysNo, String objDefNo, String objId) {
		String jql = "SELECT objUser.userId FROM BioneAuthObjUserRel objUser WHERE logicSysNo=?0 AND objUser.id.objDefNo=?1 AND objUser.id.objId=?2 ";
		List<String> users = this.baseDAO.findWithIndexParam(jql, logicSysNo, objDefNo, objId);
		return users;
	}
	
	
	
	@Transactional(readOnly = false)
	public void updateRelBatch(List<BioneMsgAuthRel> oldRels,
			List<BioneMsgAuthRel> newRels) {
		if (oldRels == null || newRels == null) {
			return;
		}
		// 先删除旧关系
		for (int i = 0; i < oldRels.size(); i++) {
			this.removeEntity(oldRels.get(i));
			if (i % 20 == 0) {
				this.baseDAO.flush();
			}
		}
		// 维护新关系
		for (int j = 0; j < newRels.size(); j++) {
			this.updateEntity(newRels.get(j));
			if (j % 20 == 0) {
				this.baseDAO.flush();
			}
		}
	}
	

}
