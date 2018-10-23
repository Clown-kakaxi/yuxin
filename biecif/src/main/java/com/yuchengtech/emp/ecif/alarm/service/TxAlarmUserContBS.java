package com.yuchengtech.emp.ecif.alarm.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmUserCont;

@Service
@Transactional(readOnly = true)
public class TxAlarmUserContBS extends BaseBS<TxAlarmUserCont> {
	@SuppressWarnings("unchecked")
	public SearchResult<TxAlarmUserCont> getTxAlarmUserContList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap,String userId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxAlarmUserCont from TxAlarmUserCont TxAlarmUserCont where 1=1");
		if (userId != null&&!"".equals(userId)){
			jql.append(" and " + "TxAlarmUserCont.userId='" + userId + "'");
		}
//		if (!StringUtils.isEmpty(orderBy)) {
//			jql.append(" order by TxClientAuth." + orderBy + " " + orderType);
//		}	
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxAlarmUserCont> TxAlarmUserCont = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxAlarmUserCont;
	}
	
	
	public boolean checkGroupName(Long userId, String txName) {

		String jql = "select t from TxAlarmUserCont t where t.userId=?0 and t.txName=?1 ";
//		//修改的时候，名字不修改的情况下进行验证
//		if (catalogId != null && (!"".equals(catalogId))) {
//			jql += " and cl.taskGrpId<>'" + taskGrpId + "'";
//		}
		List<TxAlarmUserCont> list = this.baseDAO.findWithIndexParam(jql,
				userId, txName);

		if (list != null && list.size() > 0)
			return false;
		return true;

	}
		
}
