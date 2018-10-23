package com.yuchengtech.emp.ecif.transaction.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.ecif.transaction.entity.TxClientAuth;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;

@Service
@Transactional(readOnly = true)
public class TxClientAuthBS extends BaseBS<TxClientAuth> {
	@SuppressWarnings("unchecked")
	public SearchResult<TxClientAuth> getTxClientAuthList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxClientAuth from TxClientAuth TxClientAuth where 1=1");
//		if (!conditionMap.get("jql").equals("")) {
//			jql.append(" and " + conditionMap.get("jql"));
//		}
//		if (!StringUtils.isEmpty(orderBy)) {
//			jql.append(" order by TxClientAuth." + orderBy + " " + orderType);
//		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxClientAuth> TxClientAuthList = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxClientAuthList;
	}
	
	/**
	 * 获取信息, 用于生成下拉框
	 * @return
	 */
	public List<Map<String, String>> getComBoBox() {
		StringBuffer jql = new StringBuffer("select srcSysCd, srcSysNm from SrcSystem  t");

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
		
	
	public String getSrcSystem(String srcSysCdNm) {
		
		String srcSysCdStr ="";
		StringBuffer jql = new StringBuffer("select srcSysCd, srcSysNm from SrcSystem  t where srcSysNm ='" +srcSysCdNm+"'");
 
		List<Object[]> objList = this.baseDAO.findWithNameParm(jql.toString(), null);
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		for(Object[] obj: objList) {
			srcSysCdStr = obj[0] != null ? (String)obj[0] : null;
		}
		return srcSysCdStr;
	}	
	
}
