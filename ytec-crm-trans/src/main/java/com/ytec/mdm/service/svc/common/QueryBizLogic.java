package com.ytec.mdm.service.svc.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.txp.TxNode4CRM;
import com.ytec.mdm.domain.txp.TxTabsRel4CRM;
import com.ytec.mdm.integration.transaction.core.TxModelHolder;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.integration.transaction.model.TxModel4CRM;

@Service
public class QueryBizLogic implements IEcifBizLogic {
	private static Logger log = LoggerFactory.getLogger(QueryBizLogic.class);
	private JPABaseDAO baseDAO;
	private TxModel4CRM txModel;

	@Override
	public void process(EcifData data) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");

		this.txModel = TxModelHolder.getTxModel(data.getTxCode()); // 构造查询JQL
		if (!buildQuerySql(data)) {
			log.error(data.getRepStateCd());
			return;
		}

		Map paraMap = data.getParameterMap();
		Set key_s = paraMap.keySet();
		Iterator itr = key_s.iterator();

		// 根据数据对象中参数查询数据，并做结果封装
		// 存放方式为List,List中为一个Map：key=报文中节点名，value=JQL查询结果集
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		while (itr.hasNext()) {
			List<Object> result;
			String name = (String) itr.next();
			String jql = (String) paraMap.get(name);
			try {
				result = baseDAO.findWithIndexParam(jql, null);

				if (result == null || result.size() == 0) {
					continue;
				} else {
					resultMap.put(name, result);
				}
			} catch (Exception e) {
				log.error("{}:::{}", name, jql);
				log.error("{}", e);
				String err = String.format("查询(%s)失败", name);
				data.setSuccess(false);
				data.setStatus(ErrorCode.ERR_ECIF_DATA_TOO_MANY.getCode(), err);
				return;
			}
		}
		if (resultMap == null || resultMap.isEmpty()) {
			resultList = null;
		} else {
			resultList.add(resultMap);
		}
		data.getQueryModelObj().setResulList(resultList == null || resultList.size() == 0 ? null : resultList);
	}

	/**
	 * 根据请求对象，组装JQL查询语句，存储于Map中，key:报文中节点名称，value:jql
	 * 
	 * @param data
	 * @throws Exception
	 */
	private boolean buildQuerySql(EcifData data) throws Exception {
		Document rootDoc = data.getPrimalDoc();

		List<TxNode4CRM> node_s = this.txModel.getTxNodeList();
		Map<String,List<TxTabsRel4CRM>> relMap_s = this.txModel.getTxTabsRelMap();
		Map<String, String> queryJqlMap = new HashMap<String, String>();
		Map<String, String> queryConditionMap = new HashMap<String, String>();

		String BLACK = " ";
		int tabIdx = 0;

		// 构造节点对应主表JQL语句
		for (TxNode4CRM node : node_s) {
			tabIdx++;
			StringBuffer sb = new StringBuffer();
			String name = node.getName();
			String tab = node.getTable();
			// 获取JAP简单实体名，所以要去掉包名，否则在JQL查询中会报错
			String[] tmp = tab.split("\\.");
			tab = tmp[tmp.length - 1];
			String filter = node.getFilter();
			String condition = node.getCondition();
			String condXpath;
			String cond[] = null;
			String condXpath_s[];
			if (!StringUtils.isEmpty(condition)) {
				condXpath = node.getConditionValue();
				cond = condition.split(",");
				condXpath_s = condXpath.split(",");
				int len = cond.length;
				for (int idx = 0; idx < len; idx++) {
					Element reqDataNode = (Element) rootDoc.selectSingleNode(condXpath_s[idx]);
					if (reqDataNode == null) {
						String err = String.format("获取请求报文中参数失败，无法构造节点(%s)查询条件，对应节点(%s)不存在", name, condXpath_s[idx]);
						data.setSuccess(false);
						data.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), err);
						return false;
					}
					String conditionValue = reqDataNode.getText();
					queryConditionMap.put(cond[idx], conditionValue);
				}

			}
			sb.append("select t" + tabIdx + " From " + tab + BLACK + "t" + tabIdx + BLACK);
			sb.append("where 1=1" + BLACK);
			if (!StringUtils.isEmpty(filter)) {
				sb.append("and" + BLACK + filter + BLACK);
			}
			if (relMap_s == null || relMap_s.size() == 0||relMap_s.get(name)==null || relMap_s.get(name).size()==0) {
				if (!StringUtils.isEmpty(condition)) {
					int len = cond.length;
					for (int idx = 0; idx < len; idx++) {
						if (!StringUtils.isEmpty(queryConditionMap.get(cond[idx]))) {
							sb.append("and" + BLACK + cond[idx] + "='" + queryConditionMap.get(cond[idx]) + "'" + BLACK);
						} else {
							log.warn("查询条件({})值为空，忽略该筛选条件", cond[idx]);
						}
					}
				}
			} else {
				// 构造节点对应表所需关联的表JQL语句
				String mainTab = "t" + tabIdx;// 记录主表别名，用于后续关联表查询
				List<TxTabsRel4CRM> rel_s = relMap_s.get(name);
				for (TxTabsRel4CRM rel : rel_s) {
					tabIdx++;
					StringBuffer sb_rel = new StringBuffer();
					String nodeName = rel.getNodeName();//
					if (!name.equals(nodeName)) {
						continue;
					}
					String table = rel.getTable();
					String[] tmp_r = table.split("\\.");
					table = tmp_r[tmp_r.length - 1];
					String tabAlias = "t" + tabIdx;
					String conditionRel_s = rel.getCondition();

					String[] keys = rel.getKey_s();

					for (String key : keys) {
						sb_rel.append("select " + BLACK + tabAlias + "." + key + BLACK + "From" + BLACK + table + BLACK + tabAlias + BLACK);
						sb_rel.append("where 1=1" + BLACK);

						if (!StringUtils.isEmpty(conditionRel_s)) {
							String conditionRel[] = conditionRel_s.split(",");
							int len = conditionRel.length;
							for (int idx = 0; idx < len; idx++) {
								if (!StringUtils.isEmpty(queryConditionMap.get(conditionRel[idx]))) {
									try{
									String codiCode = conditionRel[idx];
									String codiVal = queryConditionMap.get(conditionRel[idx]);
									sb_rel.append("and" + BLACK + codiCode + "='" + codiVal + "'" + BLACK);
									}catch(Exception e){
										log.error("{}@{}", cond,idx);
										e.printStackTrace();
										break;
									}
								} else {
									log.warn("查询条件({})值为空，忽略该筛选条件", conditionRel[idx]);
								}
							}
						}

						sb.append("and" + BLACK + mainTab + "." + key + BLACK + " in ( " + BLACK + sb_rel.toString() + ")");
					}
				}
			}

			log.info("构造出查询表({})对应jql({})", tab, sb.toString());
			queryJqlMap.put(name, sb.toString());
		}
		data.setParameterMap(queryJqlMap);
		return true;
	}
}
