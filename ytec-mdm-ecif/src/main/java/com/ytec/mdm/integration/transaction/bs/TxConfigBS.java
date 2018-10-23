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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.exception.BizException;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxColDef;
import com.ytec.mdm.domain.txp.TxDef;
import com.ytec.mdm.domain.txp.TxMsg;
import com.ytec.mdm.domain.txp.TxMsgNode;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.domain.txp.TxMsgNodeAttrCt;
import com.ytec.mdm.domain.txp.TxMsgNodeFilter;
import com.ytec.mdm.domain.txp.TxMsgNodeTabMap;
import com.ytec.mdm.domain.txp.TxMsgNodeTabsRel;
import com.ytec.mdm.domain.txp.TxTabDef;
import com.ytec.mdm.integration.transaction.model.TxModel;

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
public class TxConfigBS {

	protected static Logger log = LoggerFactory.getLogger(TxConfigBS.class);

	@PersistenceContext
	private EntityManager em;

	// private final AtomicBoolean daoInit = new
	// AtomicBoolean(false);//DAO是否已经初始化

	private JPABaseDAO<TxDef, Long> txDefDAO = null;// 交易配置查询
	private JPABaseDAO<TxMsg, Long> txMsgDAO = null;// 报文查询
	private JPABaseDAO<TxMsgNode, Long> txMsgNodeDAO = null;// 报文节点查询
	private JPABaseDAO<TxMsgNodeAttr, Long> txMsgNodeAttrDAO = null;// 报文节点属性查询
	private JPABaseDAO<TxMsgNodeTabMap, Long> txMsgNodeTabMapDAO = null;// 报文节点数据库表映射查询
	private JPABaseDAO<TxMsgNodeFilter, Long> txMsgNodeFilterDAO = null;// 报文节点数据库表过滤条件查询
	private JPABaseDAO<TxMsgNodeTabsRel, Long> txMsgNodeTabRelDAO = null;// 报文节点数据库表间关系查询
	private JPABaseDAO<TxMsgNodeAttrCt, Long> txMsgNodeAttrCtDAO = null;// 报文节点属性转换配置查询
	private JPABaseDAO<TxTabDef, Long> tabDefDAO = null;// 表定义查询
	private JPABaseDAO<TxColDef, Long> colDefDAO = null;// 字段定义查询

	public TxConfigBS() {

	}

	/**
	 * 根据交易码初始化交易模型对象
	 * 
	 * @param txCode
	 * @return
	 */
	public TxModel getTxModel(String txCode) {
		log.debug("根据交易编号:{}构造交易配置信息.", txCode);
		TxModel txModel = new TxModel();
		// 交易信息
		TxDef txDef = txDefDAO.findUniqueByProperty("txCode", txCode);

		if (txDef == null) {
			log.error("交易编号:{} 对应的交易配置信息不存在,请检查", txCode);
			throw new BizException("交易" + txCode + "不存在");
		}

		txModel.setTxDef(txDef);

		// 请求、响应报文
		List<TxMsg> txMsgList = txMsgDAO.findByProperty("txId", txDef.getTxId());

		for (int i = 0; i < txMsgList.size(); i++) {

			TxMsg txMsg = txMsgList.get(i);

			if (MdmConstants.MSG_TYPE_REQ.equals(txMsg.getMsgTp())) {// 请求报文

				txModel.setReqTxMsg(txMsg);

			} else if (MdmConstants.MSG_TYPE_RES.equals(txMsg.getMsgTp())) {// 响应报文

				txModel.setResTxMsg(txMsg);
			}
		}

		Map<Long, List<TxMsgNodeAttr>> txMsgNodeAttrMap = new HashMap<Long, List<TxMsgNodeAttr>>();
		Map<Long, List<TxMsgNodeAttrCt>> txMsgNodeAttrCtMap = new HashMap<Long, List<TxMsgNodeAttrCt>>();
		Map<Long, List<TxMsgNodeTabMap>> txMsgNodeTabMapMap = new HashMap<Long, List<TxMsgNodeTabMap>>();
		Map<Long, List<TxMsgNodeTabsRel>> txMsgNodeTabRelMap = new HashMap<Long, List<TxMsgNodeTabsRel>>();
		Map<Long, List<TxMsgNodeFilter>> txMsgNodeFilterMap = new HashMap<Long, List<TxMsgNodeFilter>>();

		txModel.setTxMsgNodeAttrMap(txMsgNodeAttrMap);
		txModel.setTxMsgNodeAttrCtMap(txMsgNodeAttrCtMap);
		txModel.setTxMsgNodeTabsRelMap(txMsgNodeTabRelMap);
		txModel.setTxMsgNodeTabMapMap(txMsgNodeTabMapMap);
		txModel.setTxMsgNodeFilterMap(txMsgNodeFilterMap);

		TxMsgNode txMsgNode = null;
		// 请求报文节点
		TxMsg reqTxMsg = txModel.getReqTxMsg();
		if (null != reqTxMsg) {
			List<TxMsgNode> reqTxMsgNodeList = txMsgNodeDAO.findByPropertyAndOrder("msgId", txModel.getReqTxMsg()
					.getMsgId(), "nodeSeq", false);
			txModel.setReqTxMsgNodeList(reqTxMsgNodeList);

			// 报文节点属性

			for (int i = 0; i < reqTxMsgNodeList.size(); i++) {

				txMsgNode = reqTxMsgNodeList.get(i);
				/** 设置请求报文根结点 **/
				if (txMsgNode.getUpNodeId() == -1 && txModel.getReqTxMsg() != null) {
					txModel.getReqTxMsg().setMainMsgRoot(txMsgNode.getNodeCode());
				}

				findNodeInfo(txMsgNodeAttrMap, txMsgNodeAttrCtMap, txMsgNodeTabMapMap, txMsgNodeTabRelMap,
						txMsgNodeFilterMap, txMsgNode);

			}
		}

		// 响应节点
		TxMsg resTxMsg = txModel.getResTxMsg();
		if (null != resTxMsg) {
			List<TxMsgNode> resTxMsgNodeList = txMsgNodeDAO.findByPropertyAndOrder("msgId", txModel.getResTxMsg()
					.getMsgId(), "nodeSeq", false);
			txModel.setResTxMsgNodeList(resTxMsgNodeList);

			// 报文节点属性
			for (int i = 0; i < resTxMsgNodeList.size(); i++) {

				txMsgNode = resTxMsgNodeList.get(i);
				/** 设置请求报文根结点 **/
				if (txMsgNode.getUpNodeId() == -1 && txModel.getResTxMsg() != null) {
					txModel.getResTxMsg().setMainMsgRoot(txMsgNode.getNodeCode());
				}
				findNodeInfo(txMsgNodeAttrMap, txMsgNodeAttrCtMap, txMsgNodeTabMapMap, txMsgNodeTabRelMap,
						txMsgNodeFilterMap, txMsgNode);

			}
		}

		log.debug("根据交易编号:{}构造交易配置信息完成.", txCode);

		return txModel;
	}

	/**
	 * 初始化DAO
	 */
	@PostConstruct
	public void initDAO() {

		log.info("初始化交易配置处理类");
		// if(this.daoInit.compareAndSet(false, true)){
		txDefDAO = new JPABaseDAO<TxDef, Long>(em, TxDef.class);
		txMsgDAO = new JPABaseDAO<TxMsg, Long>(em, TxMsg.class);
		txMsgNodeDAO = new JPABaseDAO<TxMsgNode, Long>(em, TxMsgNode.class);
		txMsgNodeAttrDAO = new JPABaseDAO<TxMsgNodeAttr, Long>(em, TxMsgNodeAttr.class);
		txMsgNodeAttrCtDAO = new JPABaseDAO<TxMsgNodeAttrCt, Long>(em, TxMsgNodeAttrCt.class);
		txMsgNodeTabMapDAO = new JPABaseDAO<TxMsgNodeTabMap, Long>(em, TxMsgNodeTabMap.class);
		txMsgNodeFilterDAO = new JPABaseDAO<TxMsgNodeFilter, Long>(em, TxMsgNodeFilter.class);
		txMsgNodeTabRelDAO = new JPABaseDAO<TxMsgNodeTabsRel, Long>(em, TxMsgNodeTabsRel.class);
		tabDefDAO = new JPABaseDAO<TxTabDef, Long>(em, TxTabDef.class);
		colDefDAO = new JPABaseDAO<TxColDef, Long>(em, TxColDef.class);
		// }
	}

	/**
	 * 查询节点的相关信息
	 * 
	 * @param txMsgNodeAttrMap
	 * @param txMsgNodeAttrCtMap
	 * @param txMsgNodeTabMapMap
	 * @param txMsgNodeTabRelMap
	 * @param txMsgNodeFilterMap
	 * @param nodeId
	 */
	private void findNodeInfo(Map<Long, List<TxMsgNodeAttr>> txMsgNodeAttrMap,
			Map<Long, List<TxMsgNodeAttrCt>> txMsgNodeAttrCtMap, Map<Long, List<TxMsgNodeTabMap>> txMsgNodeTabMapMap,
			Map<Long, List<TxMsgNodeTabsRel>> txMsgNodeTabRelMap, Map<Long, List<TxMsgNodeFilter>> txMsgNodeFilterMap,
			TxMsgNode txMsgNode) {

		Long nodeId = txMsgNode.getNodeId();

		Map<String, String> tabNameMap = new HashMap<String, String>();// 数据库表
																		// 代码-名称映射关系
		Map<String, TxColDef> colNameMap = new HashMap<String, TxColDef>();// 数据库字段
																			// 代码-名称映射关系

		// 查询节点对应表
		List<TxMsgNodeTabMap> txMsgNodeTabMapList = txMsgNodeTabMapDAO.findByProperty("nodeId", nodeId);

		if (txMsgNodeTabMapList != null && txMsgNode.getNodeTp().equals(MdmConstants.NODE_TP_T)) {

			// 根据元数据找出对应的表的物理名称
			for (TxMsgNodeTabMap txMsgNodeTabMap : txMsgNodeTabMapList) {

				TxTabDef tabDef = this.tabDefDAO.findUniqueByProperty("tabId", txMsgNodeTabMap.getTabId());
				if (tabDef == null) {

					log.error("TxMsgNodeTabMap,找不到id为:{} 对应的元数据表信息！", txMsgNodeTabMap.getTabId());
					throw new BizException("查找元数据表信息失败");
				} else {

					tabNameMap.put(tabDef.getTabId().toString(), tabDef.getTabName());
					txMsgNodeTabMap.setTabName(tabDef.getTabName());
					txMsgNodeTabMap.setObjName(tabDef.getObjName());
					// 查找表的字段定义信息
					List<TxColDef> colDefList = this.colDefDAO.findByProperty("id.tabId", tabDef.getTabId());
					if (colDefList != null) {
						for (TxColDef colDef : colDefList) {
							colNameMap.put(tabDef.getTabId().toString() + colDef.getId().getColId().toString(), colDef);
						}
					}
				}

			}
		}

		txMsgNodeTabMapMap.put(nodeId, txMsgNodeTabMapList);

		// 查询节点表间的关联关系
		List<TxMsgNodeTabsRel> txMsgNodeTabRelList = txMsgNodeTabRelDAO.findByProperty("nodeId", nodeId);

		if (txMsgNodeTabRelList != null && txMsgNode.getNodeTp().equals(MdmConstants.NODE_TP_T)) {
			TxColDef temp = null;
			String colName1 = null;
			String colName2 = null;
			for (TxMsgNodeTabsRel txMsgNodeTabsRel : txMsgNodeTabRelList) {

				txMsgNodeTabsRel.setTabName1(tabNameMap.get(txMsgNodeTabsRel.getTabId1().toString()));
				txMsgNodeTabsRel.setTabName2(tabNameMap.get(txMsgNodeTabsRel.getTabId2().toString()));
				if ((temp = colNameMap.get(txMsgNodeTabsRel.getTabId1().toString()
						+ txMsgNodeTabsRel.getColId1().toString())) != null) {
					colName1 = temp.getColName();
				} else {
					colName1 = null;
				}

				if ((temp = colNameMap.get(txMsgNodeTabsRel.getTabId2().toString()
						+ txMsgNodeTabsRel.getColId2().toString())) != null) {
					colName2 = temp.getColName();
				} else {
					colName2 = null;
				}
				if (colName1 == null || colName2 == null) {
					log.error("TxMsgNodeTabsRel,找不到id为:{}或者{} 对应的元数据表字段信息！", txMsgNodeTabsRel.getColId1(),
							txMsgNodeTabsRel.getColId2());
					throw new BizException("查找元数据表字段信息失败");
				}

				txMsgNodeTabsRel.setColName1(colName1);
				txMsgNodeTabsRel.setColName2(colName2);

			}
		}
		txMsgNodeTabRelMap.put(nodeId, txMsgNodeTabRelList);
		// 查询节点表的过滤条件
		List<TxMsgNodeFilter> txMsgNodeFilterList = txMsgNodeFilterDAO.findByProperty("nodeId", nodeId);

		if (txMsgNodeFilterList != null) {
			TxColDef temp = null;
			String colName = null;
			for (TxMsgNodeFilter txMsgNodeFilter : txMsgNodeFilterList) {// ------------------------------
				txMsgNodeFilter.setTabName(tabNameMap.get(txMsgNodeFilter.getTabId().toString()));
				
				if ((temp = colNameMap.get(txMsgNodeFilter.getTabId().toString()
						+ txMsgNodeFilter.getColId().toString())) != null) {
					colName = temp.getColName();
				} else {
					colName = null;
				}
				if (colName == null) {
					log.error("TxMsgNodeFilter,找不到表id:{},字段id:{} 对应的元数据表字段信息！", txMsgNodeFilter.getTabId(),
							txMsgNodeFilter.getColId());
					throw new BizException("查找元数据表字段信息失败");
				}
				txMsgNodeFilter.setColName(colName);
				if (txMsgNodeFilter.getAttrId() != null && txMsgNodeFilter.getAttrId() != 0) {
					TxMsgNodeAttr reqTxMsgNodeAttr = this.txMsgNodeAttrDAO.get(txMsgNodeFilter.getAttrId());
					if (reqTxMsgNodeAttr == null || txMsgNodeAttrMap.get(reqTxMsgNodeAttr.getNodeId()) == null) {
						log.error("TxMsgNodeFilter,过滤对应结点属性错误");
						throw new BizException("TxMsgNodeFilter,过滤对应结点属性错误");
					}
					txMsgNodeFilter.setReqAttrCode(reqTxMsgNodeAttr.getAttrCode());
					txMsgNodeFilter.setReqAttrDataType(reqTxMsgNodeAttr.getDataType());
					txMsgNodeFilter.setReqAttrDataFmt(reqTxMsgNodeAttr.getDataFmt());
					txMsgNodeFilter.setReqAttrNullAble(reqTxMsgNodeAttr.getNulls());
					txMsgNodeFilter.setDefaultVal(reqTxMsgNodeAttr.getDefaultVal());
				} else if (!StringUtil.isEmpty(txMsgNodeFilter.getFilterConditions())) {
					txMsgNodeFilter.setReqAttrCode(txMsgNodeFilter.getTabId().toString()
							+ txMsgNodeFilter.getColId().toString());
					String DataType = convertColDatatype2AttrDatatype(temp.getDataType(), temp.getDataPrec());
					if (StringUtil.isEmpty(DataType)) {
						log.error("TxMsgNodeFilter,对应数据类型不确定");
						throw new BizException("TxMsgNodeFilter,对应数据类型不确定");
					}
					txMsgNodeFilter.setReqAttrDataType(DataType);
					txMsgNodeFilter.setReqAttrNullAble("N");
				} else {
					log.error("TxMsgNodeFilter,没有配置条件对应的过滤数据");
					throw new BizException("TxMsgNodeFilter,没有配置条件对应的过滤数据");
				}
			}
		}

		txMsgNodeFilterMap.put(nodeId, txMsgNodeFilterList);

		// 查询节点属性
		List<TxMsgNodeAttr> txMsgNodeAtrrList = txMsgNodeAttrDAO.findByProperty("nodeId", nodeId);

		List<Long> attrIdList = new ArrayList<Long>();// 节点属性ID集合，用于后面功能查询

		// 只有当节点是基于数据库表时，才查找节点对于的数据库字段
		if (txMsgNodeAtrrList != null && txMsgNode.getNodeTp().equals(MdmConstants.NODE_TP_T)) {
			TxColDef temp = null;
			String colName = null;
			for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAtrrList) {

				// attrIdList.add(txMsgNodeAttr.getAttrId());
				// 对于请求报文而言，如果节点属性不基于数据库表，则不处理
				if (txMsgNodeAttr.getColId() == null || txMsgNodeAttr.getTabId() == null
						|| txMsgNodeAttr.getTabId() == 0) {
					continue;
				}

				if (txMsgNodeAttr.getFkAttrId() != null && txMsgNodeAttr.getFkAttrId() != 0) {

					TxMsgNodeAttr fkTxMsgNodeAttr = txMsgNodeAttrDAO.get(txMsgNodeAttr.getFkAttrId());
					if (fkTxMsgNodeAttr == null || !txMsgNode.getUpNodeId().equals(fkTxMsgNodeAttr.getNodeId())) {
						log.error("TxMsgNodeAttr,父节点对应错误");
						throw new BizException("TxMsgNodeAttr,父节点对应错误");
					}
					txMsgNodeAttr.setFkNodeId(fkTxMsgNodeAttr.getNodeId());
					txMsgNodeAttr.setFkAttrCode(fkTxMsgNodeAttr.getAttrCode());
				}

				txMsgNodeAttr.setTabName(tabNameMap.get(txMsgNodeAttr.getTabId().toString()));

				if ((temp = colNameMap.get(txMsgNodeAttr.getTabId().toString() + txMsgNodeAttr.getColId().toString())) != null) {
					colName = temp.getColName();
				} else {
					colName = null;
				}
				if (colName == null) {
					log.error("TxMsgNodeAttr,找不到id:{} 对应的元数据表字段信息！", txMsgNodeAttr.getColId());
					throw new BizException("查找元数据表字段信息失败");
				}

				txMsgNodeAttr.setColName(colName);
			}
		}

		if (txMsgNodeAtrrList != null) {
			for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAtrrList) {
				attrIdList.add(txMsgNodeAttr.getAttrId());
			}
		}

		txMsgNodeAttrMap.put(nodeId, txMsgNodeAtrrList);

		// 查找节点属性校验、转换规则配置
		// 为了减少数据库查询的次数，先一次查询该节点所有的配置，然后分到各属性上
		// 查询节点属性
		List<TxMsgNodeAttrCt> txMsgNodeAtrrCtList = txMsgNodeAttrCtDAO.findByPropertyAndOrderWithParams("attrId",
				attrIdList, "ctRule", false);

		if (txMsgNodeAtrrCtList != null) {

			for (TxMsgNodeAttrCt txMsgNodeAttrCt : txMsgNodeAtrrCtList) {

				List<TxMsgNodeAttrCt> txMsgNodeAttrCtList = txMsgNodeAttrCtMap.get(txMsgNodeAttrCt.getAttrId());
				if (txMsgNodeAttrCtList == null) {
					txMsgNodeAttrCtList = new ArrayList<TxMsgNodeAttrCt>();
					txMsgNodeAttrCtMap.put(txMsgNodeAttrCt.getAttrId(), txMsgNodeAttrCtList);
				}

				txMsgNodeAttrCtList.add(txMsgNodeAttrCt);
			}
		}

	}

	/**
	 * 字符型(固定长度):'10' 字符型(可变长度):'20' 整型 :'30' 浮点型 :'40' 日期型 :'50' 时间型 :'60' 时间戳型
	 * :'70' 大文本型 :'80'
	 */
	private String convertColDatatype2AttrDatatype(String datatype, Long dataprec) {
		if (StringUtil.isEmpty(datatype)) {
			return datatype;
		} else if (datatype.equals("VARCHAR") || datatype.equals("VARCHAR2")) {
			return "20";
		} else if (datatype.equals("BIGINT")) {
			return "30";
		} else if (datatype.equals("INT")) {
			return "30";
		} else if (datatype.equals("INTEGER")) {
			return "30";
		} else if (datatype.startsWith("NUMBER")) {
			if (dataprec == null || dataprec.intValue() == 0) { // 精度为0
				return "30";
			} else { // 精度不为0
				return "40";
			}
			// }else if(datatype.startsWith("TIME")){
			// return "60";
		} else if (datatype.equals("CHARACTER")) {
			return "10";
		} else if (datatype.equals("CHAR")) {
			return "10";
		} else if (datatype.equals("CLOB")) {
			return "80";
		} else if (datatype.equals("DATE")) {
			return "50";
		} else if (datatype.equals("DECIMAL")) {
			return "40";
		} else if (datatype.equals("BIGDECIMAL")) {
			return "40";
		} else if (datatype.startsWith("TIMESTAMP")) {
			return "70";
		} else {
			return null;
		}
	}

	/**
	 * 根据交易码获取交易配置信息
	 * 
	 * @param txCode
	 * @return
	 */
	public TxDef getTxDef(String txCode) {

		JPABaseDAO<TxDef, Long> txDefDAO = new JPABaseDAO<TxDef, Long>(em, TxDef.class);
		return txDefDAO.findUniqueByProperty("txCode", txCode);
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
