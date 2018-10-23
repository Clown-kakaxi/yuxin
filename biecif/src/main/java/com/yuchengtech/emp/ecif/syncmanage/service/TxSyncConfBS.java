package com.yuchengtech.emp.ecif.syncmanage.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.syncmanage.entity.TxSyncConf;

/**
 * <pre>
 * Title: 数据同步配置
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
public class TxSyncConfBS extends BaseBS<TxSyncConf> {

	/**
	 * 配置列表
	 * 
	 * @param firstResult
	 * @param maxResult
	 * @param condition
	 * @return
	 */
	public SearchResult<TxSyncConf> getSearchResult(int firstResult,
			int maxResult, Map<String, Object> condition) {
		@SuppressWarnings("unchecked")
		Map<String, Object> values = (Map<String, Object>) condition
				.get("params");
		String param = condition.get("jql") != null ? condition.get("jql")
				.toString() : "";
		StringBuffer jql = new StringBuffer(500);
		jql.append("select t from TxSyncConf t where 1=1 ");
		if (!StringUtils.isEmpty(param)) {
			jql.append(" and ").append(param);
		}
		jql.append(" order by t.createTime, t.updateTime desc");
		SearchResult<TxSyncConf> result = baseDAO.findPageWithNameParam(
				firstResult, maxResult, jql.toString(), values);
		return result;
	}
	
	/**
	 * 批量删除
	 * 
	 * @param idLst id列表
	 * @throws Exception
	 */
	@Transactional
	public void batchRemove(List<Long> idLst) throws Exception {
		if (idLst == null || idLst.size() == 0) {
			throw new Exception("参数为空");
		}
		String jql = "delete from TxSyncConf t where t.syncConfId in ?0";
		baseDAO.batchExecuteWithIndexParam(jql, idLst);
	}
}
