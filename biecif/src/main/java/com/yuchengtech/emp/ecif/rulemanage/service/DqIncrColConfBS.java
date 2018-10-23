package com.yuchengtech.emp.ecif.rulemanage.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.rulemanage.entity.DqIncrColConf;

/**
 * <pre>
 * Description: 通用覆盖规则管理BS 
 * </pre>	
 * @author pengsenlin pengsl@yuchengtech.com
 *
 */
@Service
@Transactional(readOnly = true)
public class DqIncrColConfBS extends BaseBS<DqIncrColConf> {
	
	/**
	 * 通过条件查询通用覆盖规则字段列表信息
	 * @param firstResult 当前数据开始行索引
	 * @param pageSize 每页显示行数
	 * @param orderBy 排序字段
	 * @param orderType 排序类型
	 * @param conditionMap 过滤条件
	 * @return dqIncrTabConfList 通用覆盖规则字段信息列表
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<DqIncrColConf> getDqIncrColConfList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, String tid) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from DqIncrColConf obj where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if(tid != null){
			jql.append(" and tid = '" + tid+"'");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<DqIncrColConf> dqIncrColConfList = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return dqIncrColConfList;
	}
}
