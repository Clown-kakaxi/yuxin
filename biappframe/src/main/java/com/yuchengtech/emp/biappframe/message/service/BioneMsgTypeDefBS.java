package com.yuchengtech.emp.biappframe.message.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.message.entity.BioneMsgTypeDef;
import com.yuchengtech.emp.bione.dao.SearchResult;

/**
 * <pre>
 * Title: 消息模块-消息类型定义服务
 * Description: 消息模块-消息类型定义服务
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
public class BioneMsgTypeDefBS extends BaseBS<BioneMsgTypeDef> {

	
	@SuppressWarnings("unchecked")
	public SearchResult<BioneMsgTypeDef> getMessageDefList(int firstResult, 
			int pageSize, String orderBy, String orderType, Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select msgDef from BioneMsgTypeDef msgDef where 1=1 ");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by msgDef." + orderBy + " " + orderType);
		}
		Map<String, Object> values = (Map<String, Object>) conditionMap.get("params");
		SearchResult<BioneMsgTypeDef> msgDefList = this.baseDAO
				.findPageWithNameParam(firstResult, pageSize, jql.toString(),
						values);
		
		return msgDefList;
	}
	
	/**
	 * 批量删除
	 * 
	 * @param ids
	 *            记录ID号
	 */
	@Transactional(readOnly = false)
	public void deleteBatch(String[] ids) {
		for (String id : ids) {
			removeEntityById(id);
		}
	}
	
	/**
	 * 校验合法性
	 * @param varNo
	 * @param logicSysNo
	 * @return
	 */
	public boolean checkVarNo(String msgTypeNo) {
		String jql = "SELECT msgDef FROM BioneMsgTypeDef msgDef WHERE msgDef.msgTypeNo = ?0 ";
		List<BioneMsgTypeDef> msgDefList = this.baseDAO.findWithIndexParam(jql, msgTypeNo);
		if (msgDefList != null && msgDefList.size() > 0) {
			return false;
		}
		return true;
	}
	
	
	public BioneMsgTypeDef findUniqueEntityByProperty(final String propertyName, final Object value) {
		return this.baseDAO.findUniqueByProperty(BioneMsgTypeDef.class, propertyName, value);
	}
}
