/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.model
 * @文件名：TxModel.java
 * @版本信息：1.0.0
 * @日期：2014-6-10-10:56:55
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.transaction.model;

import java.util.List;
import java.util.Map;

import com.ytec.mdm.domain.txp.TxDef;
import com.ytec.mdm.domain.txp.TxMsg;
import com.ytec.mdm.domain.txp.TxMsgNode;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.domain.txp.TxMsgNodeAttrCt;
import com.ytec.mdm.domain.txp.TxMsgNodeFilter;
import com.ytec.mdm.domain.txp.TxMsgNodeTabMap;
import com.ytec.mdm.domain.txp.TxMsgNodeTabsRel;

/**
 * The Class TxModel.
 * 
 * @项目名称：ytec-mdm-ecif
 * @类名称：TxModel
 * @类描述：交易对象模型
 * @功能描述:交易对象模型，用于在内存中缓存整个交易配置信息
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:30:18
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:30:18
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class TxModel {

	/**
	 * The tx def.
	 * 
	 * @属性描述: 交易定义
	 */
	private TxDef txDef;
	
	/**
	 * The req tx msg.
	 * 
	 * @属性描述:请求报文
	 */
	private TxMsg reqTxMsg;
	
	/**
	 * The res tx msg.
	 * 
	 * @属性描述:响应报文
	 */
	private TxMsg resTxMsg;

	/**
	 * The req tx msg node list.
	 * 
	 * @属性描述:请求报文节点
	 */
	private List<TxMsgNode> reqTxMsgNodeList;
	
	/**
	 * The res tx msg node list.
	 * 
	 * @属性描述:响应报文节点
	 */
	private List<TxMsgNode> resTxMsgNodeList;

	/**
	 * The tx msg node attr map.
	 * 
	 * @属性描述:报文节点属性
	 */
	private Map<Long, List<TxMsgNodeAttr>> txMsgNodeAttrMap;
	
	/**
	 * The tx msg node tab map map.
	 * 
	 * @属性描述:报文节点对应的数据库表
	 */
	private Map<Long, List<TxMsgNodeTabMap>> txMsgNodeTabMapMap;
	
	/**
	 * The tx msg node filter map.
	 * 
	 * @属性描述:报文节点数据库表过滤条件
	 */
	private Map<Long, List<TxMsgNodeFilter>> txMsgNodeFilterMap;
	
	/**
	 * The tx msg node tabs rel map.
	 * 
	 * @属性描述:报文节点数据库表关联关系
	 */
	private Map<Long, List<TxMsgNodeTabsRel>> txMsgNodeTabsRelMap;
	
	/**
	 * The tx msg node attr ct map.
	 * 
	 * @属性描述:报文节点属性转换校验配置
	 */
	private Map<Long, List<TxMsgNodeAttrCt>>  txMsgNodeAttrCtMap;

	/**
	 * Gets the tx def.
	 * 
	 * @return the tx def
	 */
	public TxDef getTxDef() {
		return txDef;
	}

	/**
	 * Sets the tx def.
	 * 
	 * @param txDef
	 *            the new tx def
	 */
	public void setTxDef(TxDef txDef) {
		this.txDef = txDef;
	}

	/**
	 * Gets the req tx msg.
	 * 
	 * @return the req tx msg
	 */
	public TxMsg getReqTxMsg() {
		return reqTxMsg;
	}

	/**
	 * Sets the req tx msg.
	 * 
	 * @param reqTxMsg
	 *            the new req tx msg
	 */
	public void setReqTxMsg(TxMsg reqTxMsg) {
		this.reqTxMsg = reqTxMsg;
	}

	/**
	 * Gets the res tx msg.
	 * 
	 * @return the res tx msg
	 */
	public TxMsg getResTxMsg() {
		return resTxMsg;
	}

	/**
	 * Sets the res tx msg.
	 * 
	 * @param resTxMsg
	 *            the new res tx msg
	 */
	public void setResTxMsg(TxMsg resTxMsg) {
		this.resTxMsg = resTxMsg;
	}

	/**
	 * Gets the req tx msg node list.
	 * 
	 * @return the req tx msg node list
	 */
	public List<TxMsgNode> getReqTxMsgNodeList() {
		return reqTxMsgNodeList;
	}

	/**
	 * Sets the req tx msg node list.
	 * 
	 * @param reqTxMsgNodeList
	 *            the new req tx msg node list
	 */
	public void setReqTxMsgNodeList(List<TxMsgNode> reqTxMsgNodeList) {
		this.reqTxMsgNodeList = reqTxMsgNodeList;
	}

	/**
	 * Gets the res tx msg node list.
	 * 
	 * @return the res tx msg node list
	 */
	public List<TxMsgNode> getResTxMsgNodeList() {
		return resTxMsgNodeList;
	}

	/**
	 * Sets the res tx msg node list.
	 * 
	 * @param resTxMsgNodeList
	 *            the new res tx msg node list
	 */
	public void setResTxMsgNodeList(List<TxMsgNode> resTxMsgNodeList) {
		this.resTxMsgNodeList = resTxMsgNodeList;
	}

	/**
	 * Gets the tx msg node attr map.
	 * 
	 * @return the tx msg node attr map
	 */
	public Map<Long, List<TxMsgNodeAttr>> getTxMsgNodeAttrMap() {
		return txMsgNodeAttrMap;
	}

	/**
	 * Sets the tx msg node attr map.
	 * 
	 * @param txMsgNodeAttrMap
	 *            the tx msg node attr map
	 * @函数名称:void setTxMsgNodeAttrMap(Map<Long,List<TxMsgNodeAttr>>
	 *            txMsgNodeAttrMap)
	 * @函数描述:
	 * @参数与返回说明: void setTxMsgNodeAttrMap(Map<Long,List<TxMsgNodeAttr>>
	 *           txMsgNodeAttrMap)
	 * @算法描述:
	 */
	public void setTxMsgNodeAttrMap(
			Map<Long, List<TxMsgNodeAttr>> txMsgNodeAttrMap) {
		this.txMsgNodeAttrMap = txMsgNodeAttrMap;
	}

	/**
	 * Gets the tx msg node tab map map.
	 * 
	 * @return the tx msg node tab map map
	 */
	public Map<Long, List<TxMsgNodeTabMap>> getTxMsgNodeTabMapMap() {
		return txMsgNodeTabMapMap;
	}

	/**
	 * Sets the tx msg node tab map map.
	 * 
	 * @param txMsgNodeTabMapMap
	 *            the tx msg node tab map map
	 * @函数名称:void setTxMsgNodeTabMapMap(Map<Long,List<TxMsgNodeTabMap>>
	 *            txMsgNodeTabMapMap)
	 * @函数描述:
	 * @参数与返回说明: void setTxMsgNodeTabMapMap(Map<Long,List<TxMsgNodeTabMap>>
	 *           txMsgNodeTabMapMap)
	 * @算法描述:
	 */
	public void setTxMsgNodeTabMapMap(
			Map<Long, List<TxMsgNodeTabMap>> txMsgNodeTabMapMap) {
		this.txMsgNodeTabMapMap = txMsgNodeTabMapMap;
	}

	/**
	 * Gets the tx msg node filter map.
	 * 
	 * @return the tx msg node filter map
	 */
	public Map<Long, List<TxMsgNodeFilter>> getTxMsgNodeFilterMap() {
		return txMsgNodeFilterMap;
	}

	/**
	 * Sets the tx msg node filter map.
	 * 
	 * @param txMsgNodeFilterMap
	 *            the tx msg node filter map
	 * @函数名称:void setTxMsgNodeFilterMap(Map<Long,List<TxMsgNodeFilter>>
	 *            txMsgNodeFilterMap)
	 * @函数描述:
	 * @参数与返回说明: void setTxMsgNodeFilterMap(Map<Long,List<TxMsgNodeFilter>>
	 *           txMsgNodeFilterMap)
	 * @算法描述:
	 */
	public void setTxMsgNodeFilterMap(
			Map<Long, List<TxMsgNodeFilter>> txMsgNodeFilterMap) {
		this.txMsgNodeFilterMap = txMsgNodeFilterMap;
	}

	/**
	 * Gets the tx msg node tabs rel map.
	 * 
	 * @return the tx msg node tabs rel map
	 */
	public Map<Long, List<TxMsgNodeTabsRel>> getTxMsgNodeTabsRelMap() {
		return txMsgNodeTabsRelMap;
	}

	/**
	 * Sets the tx msg node tabs rel map.
	 * 
	 * @param txMsgNodeTabRelMap
	 *            the tx msg node tab rel map
	 * @函数名称:void setTxMsgNodeTabsRelMap(Map<Long,List<TxMsgNodeTabsRel>>
	 *            txMsgNodeTabRelMap)
	 * @函数描述:
	 * @参数与返回说明: void setTxMsgNodeTabsRelMap(Map<Long,List<TxMsgNodeTabsRel>>
	 *           txMsgNodeTabRelMap)
	 * @算法描述:
	 */
	public void setTxMsgNodeTabsRelMap(
			Map<Long, List<TxMsgNodeTabsRel>> txMsgNodeTabRelMap) {
		this.txMsgNodeTabsRelMap = txMsgNodeTabRelMap;
	}

	/**
	 * Gets the tx msg node attr ct map.
	 * 
	 * @return the tx msg node attr ct map
	 */
	public Map<Long, List<TxMsgNodeAttrCt>> getTxMsgNodeAttrCtMap() {
		return txMsgNodeAttrCtMap;
	}

	/**
	 * Sets the tx msg node attr ct map.
	 * 
	 * @param txMsgNodeAttrCtMap
	 *            the tx msg node attr ct map
	 * @函数名称:void setTxMsgNodeAttrCtMap(Map<Long,List<TxMsgNodeAttrCt>>
	 *            txMsgNodeAttrCtMap)
	 * @函数描述:
	 * @参数与返回说明: void setTxMsgNodeAttrCtMap(Map<Long,List<TxMsgNodeAttrCt>>
	 *           txMsgNodeAttrCtMap)
	 * @算法描述:
	 */
	public void setTxMsgNodeAttrCtMap(
			Map<Long, List<TxMsgNodeAttrCt>> txMsgNodeAttrCtMap) {
		this.txMsgNodeAttrCtMap = txMsgNodeAttrCtMap;
	}

}
