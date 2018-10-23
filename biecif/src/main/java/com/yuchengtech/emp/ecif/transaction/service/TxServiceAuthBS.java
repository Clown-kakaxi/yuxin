package com.yuchengtech.emp.ecif.transaction.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.transaction.entity.TxServiceAuth;

@Service
@Transactional(readOnly = true)
public class TxServiceAuthBS extends BaseBS<TxServiceAuth> {
	@SuppressWarnings("unchecked")
	public SearchResult<TxServiceAuth> getTxServiceAuthList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap,String clientAuthId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxServiceAuth from TxServiceAuth TxServiceAuth where 1=1");
		if (clientAuthId != null&&!"".equals(clientAuthId)){
			jql.append(" and " + "TxServiceAuth.clientAuthId='" + clientAuthId + "'");
		}
//		if (!StringUtils.isEmpty(orderBy)) {
//			jql.append(" order by TxClientAuth." + orderBy + " " + orderType);
//		}	
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxServiceAuth> TxServiceAuth = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxServiceAuth;
	}
	
	/**
	 * 获取信息, 用于生成下拉框
	 * @return
	 */
	public List<Map<String, String>> getComBoBox() {
		StringBuffer jql = new StringBuffer("select txCode, txName from TxDef  t");

		List<Object[]> objList = this.baseDAO.findWithNameParm(jql.toString(), null);
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		Map<String, String> harvMap;
		for(Object[] obj: objList) {
			harvMap = Maps.newHashMap();
			harvMap.put("id", obj[0] != null ? obj[0].toString() : "");
			harvMap.put("text", obj[1] != null ? obj[1].toString() : "");
			harvComboList.add(harvMap);
		}
		return harvComboList;
	}
	
	public boolean checkGroupName(Long clientAuthId, String txName) {

		String jql = "select t from TxServiceAuth t where t.clientAuthId=?0 and t.txName=?1 ";
//		//修改的时候，名字不修改的情况下进行验证
//		if (catalogId != null && (!"".equals(catalogId))) {
//			jql += " and cl.taskGrpId<>'" + taskGrpId + "'";
//		}
		List<TxServiceAuth> list = this.baseDAO.findWithIndexParam(jql,
				clientAuthId, txName);

		if (list != null && list.size() > 0)
			return false;
		return true;

	}
		
}
