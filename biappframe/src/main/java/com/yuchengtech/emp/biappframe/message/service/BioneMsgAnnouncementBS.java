package com.yuchengtech.emp.biappframe.message.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.message.MessageConstants;
import com.yuchengtech.emp.biappframe.message.entity.BioneMsgAnnouncementInfo;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.message.service.IBioneMessageService;

/**
 * <pre>
 * Title: 消息模块-公告服务
 * Description: 消息模块-公告服务
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
public class BioneMsgAnnouncementBS extends BaseBS<BioneMsgAnnouncementInfo> implements IBioneMessageService<BioneMsgAnnouncementInfo> {

	@SuppressWarnings("unchecked")
	public SearchResult<BioneMsgAnnouncementInfo> getMsgListWithPage(
			String logicSysNo, String userId, int firstResult, int pageSize, String orderBy,
			String orderType, Map<String, Object> conditionMap) {
		/* 
		 * 此处应该通过权限对列表进行过滤；暂略。
		 */
		StringBuffer jql = new StringBuffer();
		jql.append("select msgAnno from BioneMsgAnnouncementInfo msgAnno where 1=1 and msgAnno.logicSysNo = :logicSysNo ");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by msgAnno." + orderBy + " " + orderType);
		}
		Map<String, Object> values = (Map<String, Object>) conditionMap
				.get("params");
		values.put("logicSysNo", logicSysNo);
		SearchResult<BioneMsgAnnouncementInfo> msgAnnoList = this.baseDAO
				.findPageWithNameParam(firstResult, pageSize, jql.toString(),
						values);
		return msgAnnoList;
	}
	
	
	@SuppressWarnings("unchecked")
	public SearchResult<BioneMsgAnnouncementInfo> getMsgListWithPageForView(
			String logicSysNo, String userId, int firstResult, int pageSize, String orderBy,
			String orderType, Map<String, Object> conditionMap) {
		/* 
		 * 此处应该通过权限对列表进行过滤；暂略。
		 */
		StringBuffer jql = new StringBuffer();
		jql.append("SELECT msgAnno FROM BioneMsgAnnouncementInfo msgAnno WHERE 1=1 AND msgAnno.logicSysNo = :logicSysNo AND msgAnno.msgSts = :msgSts ");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by msgAnno." + orderBy + " " + orderType);
		}
		Map<String, Object> values = (Map<String, Object>) conditionMap
				.get("params");
		values.put("logicSysNo", logicSysNo);
		values.put("msgSts", MessageConstants.MESSAGE_ANNOUNCEMENT_STATUS_PUBLISHED);
		SearchResult<BioneMsgAnnouncementInfo> msgAnnoList = this.baseDAO
				.findPageWithNameParam(firstResult, pageSize, jql.toString(),
						values);
		return msgAnnoList;
	}
	

	@Transactional(readOnly = false)
	public void saveMsg(BioneMsgAnnouncementInfo entity) {
		this.saveEntity(entity);
	}

	public BioneMsgAnnouncementInfo queryMsg(String msgId) {
		return this.getEntityById(msgId);
	}

	public BioneMsgAnnouncementInfo updateMsg(BioneMsgAnnouncementInfo entity) {
		return this.updateEntity(entity);
	}

	@Transactional(readOnly = false)
	public void deleteMsg(String id) {
		this.removeEntityById(id);
	}

	@Transactional(readOnly = false)
	public void deleteBatch(String[] ids) {
		if(ids!=null && ids.length>0) {
			for(String id : ids) {
				this.removeEntityById(id);
			}
		}
	}
	
}
