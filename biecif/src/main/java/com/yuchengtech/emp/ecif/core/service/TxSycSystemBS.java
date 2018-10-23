package com.yuchengtech.emp.ecif.core.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.ecif.core.entity.SrcSystem;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;

@Service
@Transactional(readOnly = true)
public class TxSycSystemBS extends BaseBS<SrcSystem> {
	@SuppressWarnings("unchecked")
	public SearchResult<SrcSystem> getSrcSystemList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select SrcSystem from SrcSystem SrcSystem where 1=1");

		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<SrcSystem> SrcSystemList = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return SrcSystemList;
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
