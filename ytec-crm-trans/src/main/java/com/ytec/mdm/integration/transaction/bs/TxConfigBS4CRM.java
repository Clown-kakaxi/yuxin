/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.bs
 * @文件名：TxConfigBS.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:04:57
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.integration.transaction.bs;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.domain.txp.TxDef4CRM;
import com.ytec.mdm.domain.txp.TxNode4CRM;
import com.ytec.mdm.domain.txp.TxTabsRel4CRM;
import com.ytec.mdm.integration.transaction.model.TxModel4CRM;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：TxConfigBS
 * @类描述：交易配置相关的业务处理类
 * @功能描述:交易配置相关的业务处理类
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:04:57
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:04:57
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Service
public class TxConfigBS4CRM {

	protected static Logger log = LoggerFactory.getLogger(TxConfigBS4CRM.class);
	protected static String defautCfg = "transConf.xml";
	protected static String cfgPath;
	protected static Map<String, TxModel4CRM> txModelMap;
	protected static Map<String, String> bizKeyMap;

	public TxConfigBS4CRM() {

	}

	public static boolean initConfigBS(String cfgPath_) {
		txModelMap = new HashMap<String, TxModel4CRM>();
		bizKeyMap = new HashMap<String, String>();
		Document transCfg = null;
		SAXReader saxReader = new SAXReader();
		if (!cfgPath_.endsWith("/")) {
			cfgPath_ = cfgPath_ + "/";
		}
		cfgPath = cfgPath_;
		File cfgFile = new File(cfgPath + defautCfg);
		if (!cfgFile.exists()) {
			/**
			 * 如果文件不存在，就调用类的资源加载器
			 */
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(defautCfg);
			if (is != null) {
				try {
					/**
					 * 解析配置文件
					 */
					transCfg = saxReader.read(is);
					saxReader = null;
				} catch (DocumentException e) {
					log.error("解析配置文件失败:", e);
					return false;
				}
			} else {
				log.error("配置文件{}/{}不存在", cfgPath, defautCfg);
				return false;
			}
		} else {

			try {
				transCfg = saxReader.read(cfgFile);
			} catch (DocumentException e) {
				// log.error("读取交易配置信息错误：", e);
				System.out.printf("读取交易配置信息错误：", e);
				return false;
			}
		}

		/**
		 * 获取交易涉及表中业务主键信息
		 */
		List<Element> keyDef_s = (List<Element>) transCfg.selectNodes("//conf/biz_key_def/biz_key");
		for (Element keyDef : keyDef_s) {
			String simpleName = keyDef.attribute("name").getText();
			String bizKey = keyDef.attribute("key").getText();
			if (StringUtils.isEmpty(bizKey)) {
				log.info("读取配置参数，表({})业务主键信息时为空", simpleName);
				bizKeyMap.put(simpleName + "BusinessKey", null);
				continue;
			}
			log.info("读取配置参数，表({})业务主键信息({})", simpleName, bizKey);
			bizKeyMap.put(simpleName + "BusinessKey", bizKey);
		}

		/**
		 * 获取交易配置信息，包含交易定义、交易节点、交易涉及表、交易涉及表中表间关联关系、请求报文参数与交易关系
		 */
		List<Element> trans = (List<Element>) transCfg.selectNodes("//conf/trans_def/trans");
		// List<Element> txDefNode_s = trans.elements();
		for (Element txDefNode : trans) {
			String code = txDefNode.attribute("code").getText();
			String name = txDefNode.attribute("name").getText();
			String cnName = txDefNode.attribute("cn_name").getText();
			String dealClass = txDefNode.attribute("deal_class").getText();
			String dealEngine = txDefNode.attribute("deal_engine").getText();
			String txCfgTp = txDefNode.attribute("tx_cfg_tp").getText();
			String txType = txDefNode.attributeValue("tx_type");

			TxModel4CRM model = new TxModel4CRM();
			TxDef4CRM def = new TxDef4CRM();
			def.setCode(code);
			def.setName(name);
			def.setCnName(cnName);
			def.setDealClass(dealClass);
			def.setDealEngine(dealEngine);
			def.setCfgTp(txCfgTp);
			def.setTxType(txType);

			model.setTxDef(def);

			List<Element> txNode_s = txDefNode.elements("node");
			List<TxNode4CRM> nodeList = new ArrayList<TxNode4CRM>();
			List<TxTabsRel4CRM> tabRelList = new ArrayList<TxTabsRel4CRM>();
			Map<String,List<TxTabsRel4CRM>> txTabsRelMap = new HashMap<String,List<TxTabsRel4CRM>>();
			for (Element txNode : txNode_s) {
				String nodeName = txNode.attribute("name").getText();
				String table = txNode.attribute("table").getText();
				String[] tmp = table.split("\\.");
				String filter = txNode.attribute("filter").getText();
				String condition = txNode.attribute("condition").getText();
				String conditionValue = txNode.attribute("conditionValue").getText();
				String list = txNode.attribute("list").getText();
				String xpath = txNode.attributeValue("xpath");

				Object listNode4Resp = txNode.selectNodes("//conf/trans_def/trans[@code='" + code + "']/node[@name='" + nodeName + "']/resp/resp_attr");

				List<String> respNode_s = new ArrayList<String>();
				if (listNode4Resp != null) {
					List<Element> respAttr_s = (List<Element>) listNode4Resp;
					for (Element respAttr : respAttr_s) {
						respNode_s.add(respAttr.getText());
					}

				}

				Object tabRelNode = txNode.selectNodes("//conf/trans_def/trans[@code='" + code + "']/node[@name='" + nodeName + "']/tab_rel");
				if (tabRelNode != null) {
					List<Element> tabRel_s = (List<Element>) tabRelNode;
					for (Element tabRel : tabRel_s) {
						String relatedTab = tabRel.attributeValue("table");
						String relatedCondition = tabRel.attributeValue("condition");
						String ralatedKey = tabRel.attributeValue("key_map");
						TxTabsRel4CRM rel = new TxTabsRel4CRM(nodeName, relatedTab, relatedCondition, ralatedKey.split(","));
						
						tabRelList.add(rel);
						log.info("添加关联表信息, 主表：{}，关联表及配置：{}",nodeName, rel);
					}
					txTabsRelMap.put(nodeName,tabRelList);
					model.setTxTabsRelMap(txTabsRelMap);
				}

				TxNode4CRM node = new TxNode4CRM();

				node.setName(nodeName);
				node.setTable(table);
				node.setFilter(filter);
				node.setCondition(condition);
				node.setConditionValue(conditionValue);
				node.setList("true".equals(list) ? true : false);
				node.setXpath(xpath);
				node.setRespNodes(respNode_s);

				nodeList.add(node);
			}
			model.setTxNodeList(nodeList);

			txModelMap.put(code, model);
		}
		return true;

	}

	/**
	 * 根据交易码初始化交易模型对象
	 * 
	 * @param txCode
	 * @return
	 */
	public TxModel4CRM getTxModel(String txCode) {
		// log.debug("根据交易编号:{}构造交易配置信息.", txCode);
		System.out.printf("根据交易编号:{%s}构造交易配置信息.\n", txCode);
		return txModelMap.get(txCode);
	}

	public static String[] getBizKey(String simpleName) {
		return bizKeyMap.get(simpleName) == null ? null : bizKeyMap.get(simpleName).split(",");
	}

	public static void main(String[] args) {
		TxConfigBS4CRM bs = new TxConfigBS4CRM();
		Document rootDoc = null;
		try {
			rootDoc = DocumentHelper.parseText("<?xml version=\"1.0\" encoding=\"GB18030\"?><TransBody><RequestBody><custMgr>1002</custMgr><conclusion></conclusion></RequestBody></TransBody>");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bs.initConfigBS("D:/workspace/YTEC-ECIF/FUBON-ECIF-CODE/ytec-crm-trans/conf/src");
		TxModel4CRM model = bs.getTxModel("querySpecifiedCust");
		System.out.println(model);
		List<TxNode4CRM> node_s = model.getTxNodeList();
		Map<String, String> queryJqlMap = new HashMap<String, String>();
		String BLACK = " ";
		for (TxNode4CRM node : node_s) {
			StringBuffer sb = new StringBuffer();
			String tab = node.getTable();
			String[] tmp = tab.split("\\.");
			tab = tmp[tmp.length - 1];
			String filter = node.getFilter();
			String condition = node.getCondition();

			sb.append("From " + tab + BLACK);
			sb.append("where 1=1" + BLACK);
			if (!StringUtils.isEmpty(filter)) {
				sb.append("and" + BLACK + filter + BLACK);
			}
			if (!StringUtils.isEmpty(condition)) {
				String condXpath = node.getConditionValue();
				String cond[] = condition.split(",");
				String condXpath_s[] = condXpath.split(",");
				int len = cond.length;
				for (int idx = 0; idx < len; idx++) {
					System.out.printf("condXpath_s[%d]:%s\n", idx, condXpath_s[idx]);
					String conditionValue = rootDoc.selectSingleNode(condXpath_s[idx]).getText();
					if (!StringUtils.isEmpty(conditionValue)) {
						sb.append("and" + BLACK + cond[idx] + "='" + conditionValue + "'" + BLACK);
					} else {
						System.out.printf("查询条件%s值为空，忽略该筛选条件\n", cond[idx]);
					}
				}
			}
			System.out.println(sb.toString());
		}
	}
}
