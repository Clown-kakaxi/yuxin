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

		this.txModel = TxModelHolder.getTxModel(data.getTxCode()); // �����ѯJQL
		if (!buildQuerySql(data)) {
			log.error(data.getRepStateCd());
			return;
		}

		Map paraMap = data.getParameterMap();
		Set key_s = paraMap.keySet();
		Iterator itr = key_s.iterator();

		// �������ݶ����в�����ѯ���ݣ����������װ
		// ��ŷ�ʽΪList,List��Ϊһ��Map��key=�����нڵ�����value=JQL��ѯ�����
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
				String err = String.format("��ѯ(%s)ʧ��", name);
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
	 * �������������װJQL��ѯ��䣬�洢��Map�У�key:�����нڵ����ƣ�value:jql
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

		// ����ڵ��Ӧ����JQL���
		for (TxNode4CRM node : node_s) {
			tabIdx++;
			StringBuffer sb = new StringBuffer();
			String name = node.getName();
			String tab = node.getTable();
			// ��ȡJAP��ʵ����������Ҫȥ��������������JQL��ѯ�лᱨ��
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
						String err = String.format("��ȡ�������в���ʧ�ܣ��޷�����ڵ�(%s)��ѯ��������Ӧ�ڵ�(%s)������", name, condXpath_s[idx]);
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
							log.warn("��ѯ����({})ֵΪ�գ����Ը�ɸѡ����", cond[idx]);
						}
					}
				}
			} else {
				// ����ڵ��Ӧ����������ı�JQL���
				String mainTab = "t" + tabIdx;// ��¼������������ں����������ѯ
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
									log.warn("��ѯ����({})ֵΪ�գ����Ը�ɸѡ����", conditionRel[idx]);
								}
							}
						}

						sb.append("and" + BLACK + mainTab + "." + key + BLACK + " in ( " + BLACK + sb_rel.toString() + ")");
					}
				}
			}

			log.info("�������ѯ��({})��Ӧjql({})", tab, sb.toString());
			queryJqlMap.put(name, sb.toString());
		}
		data.setParameterMap(queryJqlMap);
		return true;
	}
}
