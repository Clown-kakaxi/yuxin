package com.yuchengtech.emp.biappframe.server.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.server.entity.BioneServerInfo;
import com.yuchengtech.emp.bione.dao.SearchResult;

/**
 * <pre>
 * Title: 
 * Description:
 * </pre>
 * 
 * @author kangligong kanglg@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class ServerInfoBS extends BaseBS<BioneServerInfo> {
	public SearchResult<BioneServerInfo> getSearch(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer(1000);
		jql.append("select s from BioneServerInfo s where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by s." + orderBy + " " + orderType);
		}
		@SuppressWarnings("unchecked")
		Map<String, String> values = (Map<String, String>) conditionMap
				.get("params");
		SearchResult<BioneServerInfo> result = this.baseDAO
				.findPageWithNameParam(firstResult, pageSize, jql.toString(),
						values);
		return result;
	}

	@Transactional(readOnly = false)
	public void deleteItems(String ids) {
		String[] idArray = StringUtils.split(ids, ",");
		for (String string : idArray) {
			this.removeEntityById(string);
		}
		this.baseDAO.flush();
	}

	public boolean isServerNoExist(String serverNo) {
		String jql = "select s from BioneServerInfo s where s.serverNo=?0";
		List<BioneServerInfo> list = this.baseDAO.findWithIndexParam(jql,
				serverNo);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
}
